package com.linkkou.mybatis.log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicContext;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.scripting.xmltags.SqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;
import org.javatuples.Pair;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.*;


/**
 * 用于构建完整的SQL，彻底解决通过？替换的问题存在的不正确的情况
 *
 * @author lk
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}), @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}), @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}), @Signature(type = Executor.class, method = "queryCursor", args = {MappedStatement.class, Object.class, RowBounds.class})})
public class LogInterceptor implements Interceptor {

    /**
     * 对象名称
     */
    private static final String ROOTSQLNODE = "rootSqlNode";
    private static final String ROOTSQLNODE2 = "_rootSqlNode_";
    /**
     * 参数数量
     */
    private static final Integer ARGSNUMBER = 2;

    private final String id;

    public static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
            //当Map的key为复杂对象时,需要开启该方法
            .enableComplexMapKeySerialization()
            //当字段值为空或null时，依然对该字段进行转换
            .serializeNulls()
            //防止特殊字符出现乱码
            .disableHtmlEscaping().create();

    public LogInterceptor(String id) {
        this.id = id;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object proceed = invocation.proceed();
        int size = 0;
        if (proceed instanceof List) {
            size = ((List<?>) proceed).size();
        }
        if (invocation.getArgs().length >= ARGSNUMBER) {
            final Pair<MappedStatement, Object> args = getArgs(invocation);
            MappedStatement mappedStatement = args.getValue0();
            Object parameter = args.getValue1();
            try {
                final String originalSql = this.getOriginalSql(mappedStatement, parameter);
                if (originalSql != null) {
                    BoundSql boundSql = mappedStatement.getBoundSql(parameter);
                    Configuration configuration = mappedStatement.getConfiguration();
                    // 通过配置信息和BoundSql对象来生成带值得sql语句
                    final Pair<String, List<Map<String, ?>>> completeSql = getCompleteSql(configuration, boundSql, originalSql);
                    final SqlVO sqlVO = new SqlVO().setId(mappedStatement.getId())
                            .setCompleteSql(completeSql.getValue0())
                            .setParameter(gson.toJson(completeSql.getValue1()))
                            .setTotal(size)
                            .setOriginalSql(originalSql);
                    final String json = gson.toJson(sqlVO);
                    RmiLog.log("==>  SQLStructure: " + json, this.id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return proceed;
    }

    private Pair<MappedStatement, Object> getArgs(Invocation invocation) {
        final Object[] args = invocation.getArgs();
        return Pair.with((MappedStatement) args[0], args[1]);
    }

    /**
     * 生成对应的带有值得sql语句
     *
     * @param configuration 配置
     * @param boundSql      对象
     * @param originalSql   sql
     * @return String
     */
    private Pair<String, List<Map<String, ?>>> getCompleteSql(Configuration configuration, BoundSql boundSql, String originalSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        List<Map<String, ?>> keyvalue = new ArrayList<Map<String, ?>>();
        final int size = parameterMappings.size();
        if (size > 0 && parameterObject != null) {
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            for (ParameterMapping parameterMapping : parameterMappings) {
                String propertyName = parameterMapping.getProperty();
                final JdbcType jdbcType = parameterMapping.getJdbcType();
                final TypeHandler<?> typeHandler = parameterMapping.getTypeHandler();
                final HashMap<String, Object> stringObjectHashMap = new HashMap<>();
                if (metaObject.hasGetter(propertyName)) {
                    Object obj = metaObject.getValue(propertyName);
                    final String parameterValue = getParameterTypeHandler(configuration, typeHandler, obj, jdbcType);
                    stringObjectHashMap.put(propertyName, parameterValue);
                    keyvalue.add(stringObjectHashMap);
                    originalSql = replaceFirst(originalSql, propertyName, parameterValue);
                } else if (boundSql.hasAdditionalParameter(propertyName)) {
                    Object obj = boundSql.getAdditionalParameter(propertyName);
                    final String parameterValue = getParameterTypeHandler(configuration, typeHandler, obj, jdbcType);
                    stringObjectHashMap.put(propertyName, parameterValue);
                    keyvalue.add(stringObjectHashMap);
                    originalSql = replaceFirst(originalSql, propertyName, parameterValue);
                } else if (!(parameterObject instanceof Map)) {
                    //单个参数默认组合
                    final String parameterValue = getParameterTypeHandler(configuration, typeHandler, metaObject.getOriginalObject(), jdbcType);
                    stringObjectHashMap.put(propertyName, parameterValue);
                    keyvalue.add(stringObjectHashMap);
                    originalSql = replaceFirst(originalSql, propertyName, parameterValue);
                }
            }
        }
        return Pair.with(originalSql.replaceAll("[\\s]+", " "), keyvalue);
    }

    /**
     * 参数替换
     *
     * @param originalSql    SQL
     * @param propertyName   名称
     * @param parameterValue 值
     * @return String
     */
    private String replaceFirst(String originalSql, String propertyName, String parameterValue) {
        //(\$|#)\{\s*epNo2((?!\{).)*}
        return originalSql.replaceFirst("(\\$|#)\\{\\s*" + propertyName + "((?!\\{).)*}", parameterValue);
    }

    /**
     * 获取原始的SQL
     *
     * @param mappedStatement 对象
     * @param parameter       对象
     * @return String
     */
    private String getOriginalSql(MappedStatement mappedStatement, Object parameter) {
        final SqlSource sqlSource = mappedStatement.getSqlSource();
        if (sqlSource instanceof DynamicSqlSource) {
            return getDynamicSqlSource(mappedStatement, parameter);
        }
        if (sqlSource instanceof RawSqlSource) {
            return getRawSqlSource(sqlSource);
        }
        return null;
    }


    private String getDynamicSqlSource(MappedStatement mappedStatement, Object parameter) {
        final DynamicContext dynamicContext = new DynamicContext(mappedStatement.getConfiguration(), parameter);
        final SqlNode sqlNode = getSqlNode(mappedStatement.getSqlSource());
        if (sqlNode != null && sqlNode.apply(dynamicContext)) {
            return dynamicContext.getSql();
        }
        return null;
    }

    private String getRawSqlSource(SqlSource sqlSource) {
        final RawSqlSource dynamicSqlSource = (RawSqlSource) sqlSource;
        Field[] declaredFields = dynamicSqlSource.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (ROOTSQLNODE2.equals(declaredField.getName())) {
                try {
                    declaredField.setAccessible(true);
                    return (String) declaredField.get(dynamicSqlSource);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }

    /**
     * 构建 SqlNode
     *
     * @param sqlSource 对象
     * @return SqlNode
     */
    private SqlNode getSqlNode(SqlSource sqlSource) {
        if (sqlSource instanceof DynamicSqlSource) {
            final DynamicSqlSource dynamicSqlSource = (DynamicSqlSource) sqlSource;
            Field[] declaredFields = dynamicSqlSource.getClass().getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (ROOTSQLNODE.equals(declaredField.getName())) {
                    try {
                        declaredField.setAccessible(true);
                        return (SqlNode) declaredField.get(dynamicSqlSource);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        return null;
    }

    /**
     * 如果是字符串对象则加上单引号返回，如果是日期则也需要转换成字符串形式，如果是其他则直接转换成字符串返回。
     *
     * @param configuration 对象
     * @param jdbcType      对象
     * @return String
     */
    private static String getParameterTypeHandler(Configuration configuration, TypeHandler<?> typeHandler, Object value, JdbcType jdbcType) {
        try {
            TypeHandler<Object> _typeHandler = (TypeHandler<Object>) typeHandler;
            final IPreparedStatement iPreparedStatement = new IPreparedStatement();
            _typeHandler.setParameter(iPreparedStatement, 1, value, jdbcType);
            return iPreparedStatement.getValue();
        } catch (Exception e) {
            //e.printStackTrace();
        }
        return getParameterValue(value);
    }


    /**
     * 如果是字符串对象则加上单引号返回，如果是日期则也需要转换成字符串形式，如果是其他则直接转换成字符串返回。
     * todo 需要改进为MyBatis内置的数据解析能力
     *
     * @param obj 对象
     * @return String
     */
    private static String getParameterValue(Object obj) {
        String value = "";
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            }
        }
        return value;
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

}