package com.plugins.mybaitslog.util;

import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * 匹配 '
     */
    private final static Pattern PSingleQuotationMark = Pattern.compile("'");
    /**
     * 匹配 (String),
     */
    private final static String Separate = "(\\(.*?\\))?,\\s";
    private final static String Separate_Substring = "substring\\((.+)\\,('|\")(.+)('|\")\\)";
    private final static Pattern PSeparate = Pattern.compile(Separate);
    private final static Pattern PSubstring = Pattern.compile(Separate_Substring);

    public static Boolean Ellipsis = false;

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
    public static String[] restoreSql(final String preparingLine, final String parametersLine) {
        final String PREPARING = ConfigUtil.getPreparing();
        final String PARAMETERS = ConfigUtil.getParameters();
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
        final Object[] preparing = getPreparing(preparingLineSplit);
        final Object[] parameters = getParameters(parametersLineSplit);
        try {
            String sqlformatLike = ProcessLikeSymbol(String.format((String) preparing[1], parameters));
            final String sqlformat = ProcessSubstringSymbol(sqlformatLike, (HashMap<Object, Object>) preparing[2]);
            String result = sqlformat;
            if (!Ellipsis) {
                result = BASIC_FORMATTER.format(result);
            }
            return new String[]{(String) preparing[0], result};
        } catch (Exception e) {
            final String result = BASIC_FORMATTER.format((String) preparing[1]);
            return new String[]{(String) preparing[0], result};
        }
    }

    /**
     * 处理Like的%符号冲突String.format方法
     *
     * @return
     */
    private static String ProcessLikeSymbol(String preparing) {
        return preparing.replaceAll("∮∝‰#‰∝∮", "%");
    }

    /**
     * 处理Substring函数正则表达式，不兼容的问题
     *
     * @return
     */
    private static String ProcessSubstringSymbol(String preparing, HashMap<Object, Object> hashMap) {
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            String val = (String) entry.getValue();
            preparing = preparing.replaceAll(val, key);
        }
        return preparing;
    }


    /**
     * preparing 格式化
     *
     * @param preparingLineSplit 字符串
     * @return String[]
     */
    private static Object[] getPreparing(String[] preparingLineSplit) {
        String one = "";
        String tow = "";
        Map<Object, Object> d = null;
        if (preparingLineSplit.length == 2) {
            one = preparingLineSplit[0];
            tow = preparingLineSplit[1].replaceAll("%", "∮∝‰#‰∝∮");
            Object[] objects = ReplaceSubstringSymbol(tow);
            tow = ((String) objects[0]).replaceAll("\\?", "%s");
            d = (HashMap<Object, Object>) objects[1];
        }
        return new Object[]{one, tow, d};
    }

    /**
     * 处理Substring函数正则表达式，不兼容的问题
     *
     * @param preparing
     * @return
     */
    private static Object[] ReplaceSubstringSymbol(String preparing) {
        //substring\((.+)\,('|")(.+)('|")\)
        final Matcher matcher = PSubstring.matcher(preparing);
        HashMap<String, String> d = new HashMap<>();
        int i = 0;
        while (matcher.find()) {
            String group = matcher.group();
            String groupreplace = "∮∝‰Substring" + i + "‰∝∮";
            d.put(group, groupreplace);
            preparing = preparing.replace(group, groupreplace);
            i++;
        }
        return new Object[]{preparing, d};
    }

    /**
     * Parameters 替换参数
     *
     * @param parametersLineSplit 字符串
     * @return String[]
     */
    private static Object[] getParameters(String[] parametersLineSplit) {
        if (parametersLineSplit.length == 2) {
            //Mybatis的特性一定保证参数数量是一致的
            final String[] split = parametersLineSplit[1].split(Separate);
            final Matcher matcher = PSeparate.matcher(parametersLineSplit[1]);
            final List<String> params = new ArrayList<>();
            for (String item : split) {
                String group = "";
                if (matcher.find()) {
                    group = matcher.group().replaceAll(",", "").trim();
                }
                final String s = item.trim();
                final String[] parametersTypeOrValue = getParametersTypeOrValue(s + group);
                //final String[] specialTypesOfEscapeFormat = specialTypesOfEscapeFormat(parametersTypeOrValue);
                final String stringformat = quotationTypeFormat(parametersTypeOrValue);
                params.add(stringformat);
            }
            return params.toArray(new String[0]);
        }
        return new String[]{};
    }


    /**
     * 对特点类型进行格式化
     * 这里的String值由Mybatis输入的格式来解决决定的
     *
     * @return String
     */
    private static String quotationTypeFormat(String[] parametersTypeOrValue) {
        String[] d = {"String", "Timestamp", "Date", "Time", "LocalDate", "LocalTime", "LocalDateTime"};
        for (String s : d) {
            if (s.equals(parametersTypeOrValue[1])) {
                return String.format("'%s'", parametersTypeOrValue[0]);
            }
        }
        return parametersTypeOrValue[0];
    }

    /**
     * 特殊字符串类型进行转义
     * 由于一些JSON测试中只能对单引号进行处理，否者无法保证最后的值是能正确转义的
     * 占时保留代码,后续在考虑如何解决问题
     *
     * @return String
     */
    private static String[] specialTypesOfEscapeFormat(String[] parametersTypeOrValue) {
        final String type = parametersTypeOrValue[1];
        final String value = parametersTypeOrValue[0];
        String s = value;
        if ("String".equals(type)) {
            final Matcher matcher1 = PSingleQuotationMark.matcher(value);
            s = matcher1.replaceAll("\\\\'");
        }
        return new String[]{s, type};
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
            if (find >= 0) {
                final String val = s.substring(0, find);
                final String type = s.substring(find + 1, value.length - 1);
                return new String[]{val, type};
            }
        }
        return new String[]{s, ""};
    }
}
