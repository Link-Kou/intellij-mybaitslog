package com.plugins.mybaitslog.util;

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
    public static Map<String, ConsoleView> consoleViewMap = new ConcurrentHashMap<>();


    public static void setConsoleView(Project project, ConsoleView consoleView) {
        consoleViewMap.put(project.getBasePath(), consoleView);
    }

    /**
     * 输出语句
     *
     * @param project                项目
     * @param rowLine                行数据
     * @param consoleViewContentType 输出颜色
     */
    public static void println(Project project, String rowLine, ConsoleViewContentType consoleViewContentType) {
        println(project, rowLine, consoleViewContentType, false);
    }

    /**
     * 输出语句
     *
     * @param project                项目
     * @param rowLine                行数据
     * @param consoleViewContentType 输出颜色
     */
    public static void println(Project project, String rowLine, ConsoleViewContentType consoleViewContentType, boolean lINE) {
        ConsoleView consoleView = consoleViewMap.get(project.getBasePath());
        if (consoleView != null) {
            consoleView.print(rowLine + "\n", consoleViewContentType);
            if (lINE) {
                consoleView.print(KeyNameUtil.LINE, ConsoleViewContentType.USER_INPUT);
            }
        }
    }

    /**
     * SQL 输出语句
     *
     * @param rowLine 行数据
     */
    public static void printlnSqlType(Project project, String rowLine) {
        final String sqlType = SqlProUtil.getSqlType(rowLine);
        switch (sqlType) {
            case "insert":
            case "update":
                println(project, rowLine, ConsoleViewContentType.SYSTEM_OUTPUT, true);
                break;
            case "delete":
                println(project,
                        rowLine,
                        new ConsoleViewContentType("styleName", new TextAttributes(Color.RED, null, null, null, Font.PLAIN)),
                        true);
                break;
            default:
                println(project, rowLine, ConsoleViewContentType.ERROR_OUTPUT, true);
        }
    }
}
