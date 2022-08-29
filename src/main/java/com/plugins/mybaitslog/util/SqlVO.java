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
    //endregion
}
