package com.plugins.mybaitslog.util;

import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用于SQL的还原
 *
 * @author lk
 * @version 1.0
 * @date 2020/8/23 17:14
 */
public class SqlProUtil {

    private final static BasicFormatter BASIC_FORMATTER = new BasicFormatter();

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
     * @param preparingLine  sql
     * @param parametersLine 参数
     * @return
     */
    public static String[] restoreSql(Project project, final String preparingLine, final String parametersLine) {
        final String PREPARING = ConfigUtil.getPreparing(project);
        final String PARAMETERS = ConfigUtil.getParameters(project);
        return restoreSql(PREPARING, PARAMETERS, preparingLine, parametersLine);
    }

    /**
     * Sql语句还原，整个插件的核心就是该方法
     *
     * @param preparingLine  sql
     * @param parametersLine 参数
     * @return
     */
    public static String[] restoreSql(String PREPARING, final String PARAMETERS, final String preparingLine, final String parametersLine) {
        final String[] preparingLineSplit = preparingLine.split(PREPARING);
        final String[] parametersLineSplit = parametersLine.split(PARAMETERS);
        final String[] preparing = getPreparing(preparingLineSplit);
        final Object[] parameters = getParameters(parametersLineSplit);
        try {
            final String sqlformat = String.format(preparing[1], parameters);
            final String result = BASIC_FORMATTER.format(sqlformat);
            return new String[]{preparing[0], result};
        } catch (Exception e) {
            final String result = BASIC_FORMATTER.format(preparing[1]);
            return new String[]{preparing[0], result};
        }
    }

    /**
     * preparing 格式化
     *
     * @param preparingLineSplit 字符串
     * @return String[]
     */
    private static String[] getPreparing(String[] preparingLineSplit) {
        String one = "";
        String tow = "";
        if (preparingLineSplit.length == 2) {
            one = preparingLineSplit[0];
            tow = preparingLineSplit[1].replace("?", "%s");
        }
        return new String[]{one, tow};
    }

    /**
     * Parameters 替换参数
     *
     * @param parametersLineSplit 字符串
     * @return String[]
     */
    private static Object[] getParameters(String[] parametersLineSplit) {
        if (parametersLineSplit.length == 2) {
            final String[] split = parametersLineSplit[1].split(",");
            final List<String> params = new ArrayList<>();
            for (String item : split) {
                final String s = item.trim();
                final String[] parametersTypeOrValue = getParametersTypeOrValue(s);
                final String stringformat = stringformat(parametersTypeOrValue);
                params.add(stringformat);
            }
            return params.toArray(new String[0]);
        }
        return new String[]{};
    }


    /**
     * 对特点类型进行格式化
     *
     * @return String
     */
    private static String stringformat(String[] parametersTypeOrValue) {
        String[] d = {"String", "Timestamp", "Date", "Time", "LocalDate", "LocalTime", "LocalDateTime"};
        for (String s : d) {
            if (s.equals(parametersTypeOrValue[1])) {
                return String.format("'%s'", parametersTypeOrValue[0]);
            }
        }
        return parametersTypeOrValue[0];
    }

    /**
     * 获取到参数值与类型
     *
     * @param s 参数
     * @return String[0]=值 String[1]=类型
     */
    private static String[] getParametersTypeOrValue(String s) {
        char c = '(';
        if (s.endsWith(")")) {
            final char[] value = s.toCharArray();
            int find = -1;
            for (int j = value.length - 1; j >= 0; j--) {
                if (value[j] == c) {
                    find = j;
                    break;
                }
            }
            if (find > 0) {
                final String val = s.substring(0, find);
                final String type = s.substring(find + 1, value.length - 1);
                return new String[]{val, type};
            }
        }
        return new String[]{s, ""};
    }

}
