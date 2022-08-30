package com.plugins.mybaitslog.util;

import com.google.gson.Gson;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 打印简单工具类
 *
 * @author lk
 * @version 1.0
 * @date 2020/8/23 17:14
 */
public class PrintlnUtil {

    /**
     * 多项目控制台独立性
     */
    public static Map<Project, ConsoleView> consoleViewMap = new ConcurrentHashMap<>(16);


    private static final Gson gson = GsonBuild.getGson();

    /**
     * 获取Sql语句类型
     *
     * @param sql 语句
     * @return String
     */
    private static String getSqlType(String sql) {
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
    private static SqlVO restoreSql(final String parametersLine) {
        final String[] split = parametersLine.split(ConfigUtil.getParameters());
        if (split.length == 2) {
            final String s = split[1];
            return gson.fromJson(s, SqlVO.class);
        }
        return null;
    }


    public static void setConsoleView(Project project, ConsoleView consoleView) {
        consoleViewMap.put(project, consoleView);
    }

    public static ConsoleView getConsoleView(Project project) {
        return consoleViewMap.get(project);
    }


    public static void prints(Project project, String currentLine) {
        final String parameters = ConfigUtil.getParameters();
        if (currentLine.contains(parameters)) {
            //序号前缀字符串
            final SqlVO sqlVO = restoreSql(currentLine);
            if (null != sqlVO) {
                final String completesql = sqlVO.getCompleteSql().replaceAll("\t|\r|\n", "");
                final String id = sqlVO.getId();
                final String parameter = sqlVO.getParameter();
                final Integer total = sqlVO.getTotal();
                //序号
                PrintlnUtil.printlnSqlType(project, "\n");
                PrintlnUtil.println(project, ConfigUtil.SQL_Start_Line + id + "\n", ConsoleViewContentType.USER_INPUT);
                PrintlnUtil.printlnSqlType(project, ConfigUtil.SQL_Start_Line + completesql + "\n");
                PrintlnUtil.printlnSqlType(project, ConfigUtil.SQL_Start_Line + parameter + "\n");
                PrintlnUtil.printlnSqlType(project, ConfigUtil.SQL_Start_Line + total + "\n");
            }
        }
    }


    /**
     * 输出语句
     *
     * @param project                项目
     * @param rowLine                行数据
     * @param consoleViewContentType 输出颜色
     */
    public static void println(Project project, String rowLine, ConsoleViewContentType consoleViewContentType) {
        ConsoleView consoleView = consoleViewMap.get(project);
        if (consoleView != null) {
            consoleView.print(rowLine, consoleViewContentType);
        }
    }


    /**
     * SQL 输出语句
     *
     * @param rowLine 行数据
     */
    public static void printlnSqlType(Project project, String rowLine) {
        final String sqlType = getSqlType(rowLine);
        switch (sqlType) {
            case "insert":
            case "update":
                println(project, rowLine, ConsoleViewContentType.SYSTEM_OUTPUT);
                break;
            case "delete":
                println(project, rowLine, new ConsoleViewContentType("styleName", new TextAttributes(Color.RED, null, null, null, Font.PLAIN)));
                break;
            default:
                println(project, rowLine, ConsoleViewContentType.ERROR_OUTPUT);
        }
    }
}
