/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.linkkou.mybatislog.mybatis.boot.plugins;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.PlainSelect;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 目前只有支持MySQL分页支持
 * 需要开启allowMultiQueries
 *
 * @author lk
 * @version 1.0.0
 */
@Intercepts(
        {
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
                @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class})
        }
)
//@SuppressWarnings()
public class QueryPaginatorInterceptor implements Interceptor {

    private final static String QUERY = "query";
    /**
     * FOUND_ROWS == true
     * COUNT(*)  == false
     */
    private boolean PAGINATORTYPE = false;

    /**
     * 是否支持多条语句执行
     */
    protected boolean ALLOWMULTIQUERIES = true;

    public void setPaginatortype(boolean paginator) {
        this.PAGINATORTYPE = paginator;
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final Object[] queryArgs = invocation.getArgs();
        if (invocation.getTarget() instanceof Executor && ALLOWMULTIQUERIES) {
            final MappedStatement mappedStatement = (MappedStatement) queryArgs[0];
            if (SqlCommandType.SELECT == mappedStatement.getSqlCommandType()) {
                if (QUERY.equals(invocation.getMethod().getName()) && queryArgs.length == 4) {
                    final Object MappedStatement = queryArgs[0];
                    final Object parameter = queryArgs[1];
                    final BoundSql boundSql = mappedStatement.getBoundSql(parameter);


                    MappedStatement newMs = ProxyResultSetHandler(mappedStatement, new BoundSqlSqlSource(getPagingSql(mappedStatement, boundSql)));
                    queryArgs[0] = newMs;
                }
            }
        }
        return invocation.proceed();
    }

    /**
     * 返回分页sql语句
     *
     * @param mappedStatement
     * @param boundSql
     * @return
     */
    private BoundSql getPagingSql(MappedStatement mappedStatement, BoundSql boundSql) throws JSQLParserException {
        return getSqlCountTrue(mappedStatement, boundSql);
    }

    /**
     * ALLOWMULTIQUERIES = true count(*) 模式
     * <p>
     * select rowid, fid
     * from t_sysconfig_carspec_config tscc
     * WHERE fcar_name = #{car}
     * LIMIT
     * #{offset},#{rows}
     * </p>
     *
     * @param mappedStatement
     * @param boundSql
     * @return
     */
    private BoundSql getSqlCountTrue(MappedStatement mappedStatement, BoundSql boundSql) throws JSQLParserException {
        String sql = boundSql.getSql();
        String newSql = String.format("select count(*) from (%s);", sql);
        List<ParameterMapping> newparameterMappings = new ArrayList<>();
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        newparameterMappings.addAll(parameterMappings);
        //Limit 去两个参数，所有参数以Mapper顺序为准
        for (int i = 0; i < parameterMappings.size() - 2; i++) {
            newparameterMappings.add(parameterMappings.get(i));
        }
        BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), newSql, newparameterMappings, boundSql.getParameterObject());
        return countBoundSql;
    }

    /**
     * 新加Mapper结构
     *
     * @param ms
     * @param newSqlSource
     * @return
     */
    private MappedStatement ProxyResultSetHandler(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(newResultMap(ms.getResultMaps()));
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    /**
     * 构建统一的返回值
     * 支持 allowMultiQueries=true的时候ResultMap会返回多值
     */
    protected List<ResultMap> newResultMap(List<ResultMap> lrm) {
        ResultMap resultMap = new ResultMap.Builder(null, lrm.size() > 0 ? lrm.get(0).getId() : "", Integer.class, new ArrayList<ResultMapping>()).build();
        List<ResultMap> list = new ArrayList<>();
        if (lrm.size() > 0) {
            list.add(lrm.get(0));
        }
        list.add(resultMap);
        return list;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
    }

    public static class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        @Override
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }

}
