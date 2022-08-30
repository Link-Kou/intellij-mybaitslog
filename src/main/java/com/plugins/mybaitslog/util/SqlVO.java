package com.plugins.mybaitslog.util;

/**
 * A <code>SqlVO</code> Class
 *
 * @author lk
 * @version 1.0
 * @date 2022/8/29 19:11
 */
public class SqlVO {

    private String id;

    private String originalSql;

    private String completeSql;

    private String parameter;

    private Integer total;

    //region GetSet
    public String getId() {
        return id;
    }

    public SqlVO setId(String id) {
        this.id = id;
        return this;
    }

    public String getOriginalSql() {
        return originalSql;
    }

    public SqlVO setOriginalSql(String originalSql) {
        this.originalSql = originalSql;
        return this;
    }

    public String getCompleteSql() {
        return completeSql;
    }

    public SqlVO setCompleteSql(String completeSql) {
        this.completeSql = completeSql;
        return this;
    }

    public String getParameter() {
        return parameter;
    }

    public SqlVO setParameter(String parameter) {
        this.parameter = parameter;
        return this;
    }

    public Integer getTotal() {
        return total;
    }

    public SqlVO setTotal(Integer total) {
        this.total = total;
        return this;
    }

    //endregion
}
