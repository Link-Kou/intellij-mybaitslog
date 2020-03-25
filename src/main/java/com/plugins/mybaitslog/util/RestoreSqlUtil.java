package com.plugins.mybaitslog.util;

import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 用于SQL的还原
 *
 * @author ob
 */
public class RestoreSqlUtil {
    private static Set<String> needAssembledType = new HashSet<>();
    private static Set<String> unneedAssembledType = new HashSet<>();
    private static final String QUESTION_MARK = "?";
    private static final String REPLACE_MARK = "_o_?_b_";
    private static final String PARAM_TYPE_REGEX = "\\(String\\),{0,1}|\\(Timestamp\\),{0,1}|\\(Date\\),{0,1}|\\(Time\\),{0,1}|\\(LocalDate\\),{0,1}|\\(LocalTime\\),{0,1}|\\(LocalDateTime\\),{0,1}|\\(Byte\\),{0,1}|\\(Short\\),{0,1}|\\(Integer\\),{0,1}|\\(Long\\),{0,1}|\\(Float\\),{0,1}|\\(Double\\),{0,1}|\\(BigDecimal\\),{0,1}|\\(Boolean\\),{0,1}|\\(Null\\),{0,1}";
    private static final String PARAM_TYPE_REGEX2 = "(\\(String\\))|(\\(Timestamp\\))|(\\(Date\\))|(\\(Time\\))|(\\(LocalDate\\))|(\\(LocalTime\\))|(\\(LocalDateTime\\))|(\\(Byte\\))|(\\(Short\\))|(\\(Integer\\))|(\\(Long\\))|(\\(Float\\))|(\\(Double\\))|(\\(BigDecimal\\))|(\\(Boolean\\))|(\\(Null\\))";

    //参数格式类型，暂列下面几种
    static {
        needAssembledType.add("(String)");
        needAssembledType.add("(Timestamp)");
        needAssembledType.add("(Date)");
        needAssembledType.add("(Time)");
        needAssembledType.add("(LocalDate)");
        needAssembledType.add("(LocalTime)");
        needAssembledType.add("(LocalDateTime)");
    }

    static {
        unneedAssembledType.add("(Byte)");
        unneedAssembledType.add("(Short)");
        unneedAssembledType.add("(Integer)");
        unneedAssembledType.add("(Long)");
        unneedAssembledType.add("(Float)");
        unneedAssembledType.add("(Double)");
        unneedAssembledType.add("(BigDecimal)");
        unneedAssembledType.add("(Boolean)");
    }

    public static String match(String p, String str) {
        Pattern pattern = Pattern.compile(p);
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            return m.group(0);
        }
        return "";
    }

    /**
     * Sql语句还原，整个插件的核心就是该方法
     *
     * @param preparingLine
     * @param parametersLine
     * @return
     */
    public static String restoreSql(Project project, final String preparingLine, final String parametersLine) {
        String restoreSql = "";
        String preparingSql = "";
        String parametersSql = "";
        final String PREPARING = ConfigUtil.getPreparing(project);
        final String PARAMETERS = ConfigUtil.getParameters(project);
        try {
            if (preparingLine.contains(PREPARING)) {
                preparingSql = preparingLine.split(PREPARING)[1].trim();
            } else {
                preparingSql = preparingLine;
            }
            boolean hasParam = false;
            if (parametersLine.contains(PARAMETERS)) {
                if (parametersLine.split(PARAMETERS).length > 1) {
                    parametersSql = parametersLine.split(PARAMETERS)[1];
                    if (StringUtils.isNotBlank(parametersSql)) {
                        hasParam = true;
                    }
                }
            } else {
                parametersSql = parametersLine;
            }
            if (hasParam) {
                preparingSql = StringUtils.replace(preparingSql, QUESTION_MARK, REPLACE_MARK);
                preparingSql = StringUtils.removeEnd(preparingSql, "\n");
                parametersSql = StringUtils.removeEnd(parametersSql, "\n");
                int questionMarkCount = StringUtils.countMatches(preparingSql, REPLACE_MARK);
                String[] paramArray = parametersSql.split(PARAM_TYPE_REGEX);
                for (int i = 0; i < paramArray.length; ++i) {
                    if (questionMarkCount <= paramArray.length || parametersSql.indexOf("null") == -1) {
                        break;
                    } else {
                        if (parametersSql.contains(", null,")) {
                            //这个一定要用null,(带逗号)，否则多个null值分割会出错
                            parametersSql = parametersSql.replaceFirst(", null,", ", null(Null),");
                        } else {
                            //这个一定要用null,(带逗号)，否则多个null值分割会出错
                            parametersSql = parametersSql.replaceFirst("null,", "null(Null),");
                        }
                    }
                    paramArray = parametersSql.split(PARAM_TYPE_REGEX);
                }
                for (int i = 0; i < paramArray.length; ++i) {
                    paramArray[i] = StringUtils.removeStart(paramArray[i], " ");
                    parametersSql = StringUtils.replaceOnce(StringUtils.removeStart(parametersSql, " "), paramArray[i], "");
                    String paramType = match(PARAM_TYPE_REGEX2, parametersSql);
                    preparingSql = StringUtils.replaceOnce(preparingSql, REPLACE_MARK, assembledParamValue(paramArray[i], paramType));
                    paramType = paramType.replace("(", "\\(").replace(")", "\\)") + ", ";
                    parametersSql = parametersSql.replaceFirst(paramType, "");
                }
            }
            restoreSql = simpleFormat(preparingSql);
            if (!restoreSql.endsWith(";")) {
                restoreSql += ";";
            }
            if (restoreSql.contains(REPLACE_MARK)) {
                restoreSql = StringUtils.replace(restoreSql, REPLACE_MARK, "error");
                restoreSql += "\n---This is an error sql!---";
            }
        } catch (Exception e) {
            return "restore mybatis sql error!";
        }
        return restoreSql;
    }

    public static String assembledParamValue(String paramValue, String paramType) {
        if (needAssembledType.contains(paramType)) {
            paramValue = "'" + paramValue + "'";
        }
        return paramValue;
    }

    public static boolean endWithAssembledTypes(String parametersLine) {
        for (String str : needAssembledType) {
            if (parametersLine.endsWith(str + "\n")) {
                return true;
            }
        }
        for (String str : unneedAssembledType) {
            if (parametersLine.endsWith(str + "\n")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 简单的格式化
     *
     * @param sql
     * @return
     */
    public static String simpleFormat(String sql) {
        if (StringUtils.isNotBlank(sql)) {
            return sql.replaceAll("(?i)\\s+from\\s+", "\n FROM ")
                    .replaceAll("(?i)\\s+select\\s+", "\n SELECT ")
                    .replaceAll("(?i)\\s+where\\s+", "\n WHERE ")
                    .replaceAll("(?i)\\s+left join\\s+", "\n LEFT JOIN ")
                    .replaceAll("(?i)\\s+right join\\s+", "\n RIGHT JOIN ")
                    .replaceAll("(?i)\\s+inner join\\s+", "\n INNER JOIN ")
                    .replaceAll("(?i)\\s+limit\\s+", "\n LIMIT ")
                    .replaceAll("(?i)\\s+on\\s+", "\n ON ")
                    .replaceAll("(?i)\\s+union\\s+", "\n UNION ");
        }
        return "";
    }
}
