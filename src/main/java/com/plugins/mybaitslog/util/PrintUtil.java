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
public class PrintUtil {

    /**
     * 多项目控制台独立性
     */
    public static Map<String, ConsoleView> consoleViewMap = new ConcurrentHashMap<>();


    public static void setConsoleView(Project project, ConsoleView consoleView) {
        consoleViewMap.put(project.getBasePath(), consoleView);
    }

    /**
     *
     * @param project 项目
     * @param line 行数据
     * @param consoleViewContentType 输出颜色
     */
    public static void println(Project project, String line, ConsoleViewContentType consoleViewContentType) {
        ConsoleView consoleView = consoleViewMap.get(project.getBasePath());
        if (consoleView != null) {
            consoleView.print(line + "\n", consoleViewContentType);
        }
    }

    /**
     * 增删改sql改用蓝色标识
     *
     * @param line 行数据
     */
    public static void println(Project project, String line) {
        if (StringUtils.isNotBlank(line)) {
            String lowerLine = line.toLowerCase().trim();
            if (lowerLine.startsWith("insert") || lowerLine.startsWith("update")) {
                println(project, line, ConsoleViewContentType.SYSTEM_OUTPUT);
            } else if (lowerLine.startsWith("delete")) {
                println(project, line, new ConsoleViewContentType("styleName", new TextAttributes(Color.RED, null, null, null, Font.PLAIN)));
            } else {
                println(project, line, ConsoleViewContentType.ERROR_OUTPUT);
            }
        }
    }
}
