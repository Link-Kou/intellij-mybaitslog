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
import org.javatuples.Pair;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;


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

    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            //当Map的key为复杂对象时,需要开启该方法
            .enableComplexMapKeySerialization()
            //当字段值为空或null时，依然对该字段进行转换
            .serializeNulls()
            //防止特殊字符出现乱码
            .disableHtmlEscaping()
            .create();


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
                    String sql = getCompleteSql(configuration, boundSql, originalSql);
                    final SqlVO sqlVO = new SqlVO().setId(mappedStatement.getId())
                            .setCompleteSql(sql)
                            .setParameter(gson.toJson(parameter))
                            .setTotal(size)
                            .setOriginalSql(originalSql);
                    final String json = gson.toJson(sqlVO);
                    System.out.println("==>  SQLStructure: " + json);
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
    private String getCompleteSql(Configuration configuration, BoundSql boundSql, String originalSql) {
        Object parameterObject = boundSql.getParameterObject();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (parameterMappings.size() > 0 && parameterObject != null) {
            MetaObject metaObject = configuration.newMetaObject(parameterObject);
            for (ParameterMapping parameterMapping : parameterMappings) {
                String propertyName = parameterMapping.getProperty();
                if (metaObject.hasGetter(propertyName)) {
                    Object obj = metaObject.getValue(propertyName);
                    originalSql = originalSql.replaceFirst("#\\{" + propertyName + "}", getParameterValue(obj));
                } else if (boundSql.hasAdditionalParameter(propertyName)) {
                    Object obj = boundSql.getAdditionalParameter(propertyName);
                    originalSql = originalSql.replaceFirst("#\\{" + propertyName + "}", getParameterValue(obj));
                }
            }
        }
        return originalSql.replaceAll("[\\s]+", " ");
    }


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
     * @param obj 对象
     * @return String
     */
    private static String getParameterValue(Object obj) {
        String value;
        if (obj instanceof String) {
            value = "'" + obj + "'";
        } else if (obj instanceof Date) {
            DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.CHINA);
            value = "'" + formatter.format(obj) + "'";
        } else {
            if (obj != null) {
                value = obj.toString();
            } else {
                value = "''";
            }
        }
        return value;
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {}

}