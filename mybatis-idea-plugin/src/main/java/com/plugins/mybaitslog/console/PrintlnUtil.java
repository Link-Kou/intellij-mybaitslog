package com.plugins.mybaitslog.console;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.plugins.mybaitslog.Config;
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


    private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss")
            //当Map的key为复杂对象时,需要开启该方法
            .enableComplexMapKeySerialization()
            //当字段值为空或null时，依然对该字段进行转换
            .serializeNulls()
            //防止特殊字符出现乱码
            .disableHtmlEscaping().create();

    /**
     * Sql语句还原，整个插件的核心就是该方法
     *
     * @param parametersLine 参数
     * @return
     */
    private static SqlVO restoreSql(final String parametersLine) {
        final String[] split = parametersLine.split(Config.Idea.getParameters());
        if (split.length == 2) {
            final String s = split[1];
            return gson.fromJson(s, SqlVO.class);
        }
        return null;
    }


    public static void printsInit(ConsoleView consoleView) {
        consoleView.print(" ============================================================================ " + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("    MyBatis Log EasyPlus" + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("    A mybatis javaagent framework :)" + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("    https://github.com/Link-Kou/intellij-mybaitslog" + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("\n", ConsoleViewContentType.NORMAL_OUTPUT);
        consoleView.print("  ============================================================================ " + "\n", ConsoleViewContentType.NORMAL_OUTPUT);
    }

    public static void prints(Project project, String currentLine) {
        final String parameters = Config.Idea.getParameters();
        if (currentLine.contains(parameters)) {
            //序号前缀字符串
            final SqlVO sqlVO = restoreSql(currentLine);
            if (null != sqlVO) {
                String completesql = sqlVO.getCompleteSql().replaceAll("\t|\r|\n", "");
                //final String originalSql = sqlVO.getOriginalSql().replaceAll("\t|\r|\n", "");
                final String id = sqlVO.getId();
                final String parameter = sqlVO.getParameter();
                final Integer total = sqlVO.getTotal();
                if (Config.Idea.getFormatSql()) {
                    completesql = new BasicFormatter().format(completesql);
                }
                //序号
                PrintlnUtil.println(project, Config.SQL_START_LINE + id + "\n", ConsoleViewContentType.USER_INPUT);
                PrintlnUtil.printlnSqlType(project, Config.SQL_MIDDLE_LINE, completesql + "\n");
                //PrintlnUtil.printlnSqlType(project, Config.SQL_MIDDLE_LINE, originalSql + "\n");
                PrintlnUtil.printlnSqlType(project, Config.SQL_MIDDLE_LINE, parameter + "\n");
                PrintlnUtil.printlnSqlType(project, Config.SQL_MIDDLE_LINE, "total:" + total + "\n");
                PrintlnUtil.println(project, Config.SQL_END_LINE + "\n", ConsoleViewContentType.USER_INPUT);
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

    public static void setConsoleView(Project project, ConsoleView consoleView) {
        consoleViewMap.put(project, consoleView);
    }

    public static ConsoleView getConsoleView(Project project) {
        return consoleViewMap.get(project);
    }

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
        return "other";
    }


    /**
     * SQL 输出语句
     *
     * @param rowLine 行数据
     */
    public static void printlnSqlType(Project project, String title, String rowLine) {
        final String sqlType = getSqlType(rowLine);
        final ConsoleViewContentType systemOutput = ConsoleViewContentType.SYSTEM_OUTPUT;
        final TextAttributes attributes = systemOutput.getAttributes();
        final ConsoleViewContentType styleName = new ConsoleViewContentType("styleName", new TextAttributes(attributes.getForegroundColor(), attributes.getBackgroundColor(), attributes.getEffectColor(), attributes.getEffectType(), attributes.getFontType()));
        switch (sqlType) {
            case "select":
            case "insert":
            case "update":
            case "delete":
                final Color color = Config.Idea.getColor(sqlType);
                styleName.getAttributes().setForegroundColor(color);
                println(project, title + rowLine, styleName);
                break;
            default:
                final Color color1 = Config.Idea.getColor("other");
                styleName.getAttributes().setForegroundColor(color1);
                println(project, title + rowLine, styleName);
        }
    }
}
