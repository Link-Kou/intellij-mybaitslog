package com.plugins.mybaitslog.util;

import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;


/**
 * 用于SQL的还原
 *
 * @author lk
 * @version 1.0
 * @date 2020/8/23 17:14
 */
public class SqlProUtil {

    private static final Gson gson = GsonBuild.getGson();

    /**
     * 获取Sql语句类型
     *
     * @param sql 语句
     * @return String
     */
    public static String getSqlType(String sql) {
        if (StringUtils.isNotBlank(sql)) {
            String lowerLine = sql.toLowerCase().trim();
            if (lowerLine.startsWith("insert")) {
                return "insert";
            }
            if (lowerLine.startsWith("update")) {
                return "update";
            }
            if (lowerLine.startsWith("delete")) {
                return "delete";
            }
            if (lowerLine.startsWith("select")) {
                return "select";
            }
        }
        return "";
    }

    /**
     * Sql语句还原，整个插件的核心就是该方法
     *
     * @param parametersLine 参数
     * @return
     */
    public static SqlVO restoreSql(final String parametersLine) {
        final String[] split = parametersLine.split(ConfigUtil.getParameters());
        if (split.length == 2) {
            final String s = split[1];
            return gson.fromJson(s, SqlVO.class);
        }
        return null;
    }

}
