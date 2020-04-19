package com.plugins.mybaitslog.util;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.util.ui.UIUtil;
import com.plugins.mybaitslog.hibernate.BasicFormatterImpl;
import com.plugins.mybaitslog.hibernate.Formatter;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 打印简单工具类
 *
 * @author ob
 */
public class PrintUtil {

    public static Map<String, ConsoleView> consoleViewMap = new ConcurrentHashMap<>();


    public static void setConsoleView(Project project, ConsoleView consoleView) {
        consoleViewMap.put(project.getBasePath(), consoleView);
    }

    public static ConsoleViewContentType getOutputAttributes(@Nullable Color foregroundColor, @Nullable Color backgroundColor) {
        //@Nullable Color foregroundColor, @Nullable Color backgroundColor, @Nullable Color effectColor, EffectType effectType, @FontStyle int fontType
        //针对Darcula主题，背景颜色调整
        if (UIUtil.isUnderDarcula() && backgroundColor != null) {
            backgroundColor = null;
            foregroundColor = Color.YELLOW;
        }
        return new ConsoleViewContentType("styleName", new TextAttributes(foregroundColor, backgroundColor, null, null, Font.PLAIN));
    }

    public static void println(Project project, String line, ConsoleViewContentType consoleViewContentType) {
        ConsoleView consoleView = consoleViewMap.get(project.getBasePath());
        if (consoleView != null) {
            consoleView.print(line + "\n", consoleViewContentType);
        }
    }

    /**
     * 增删改sql改用蓝色标识
     *
     * @param line
     */
    public static void println(Project project, String line) {
        if (StringUtils.isNotBlank(line)) {
            String lowerLine = line.toLowerCase().trim();
            if (lowerLine.startsWith("insert") || lowerLine.startsWith("update")) {
                println(project, line, ConsoleViewContentType.SYSTEM_OUTPUT);
            } else if (lowerLine.startsWith("delete")) {
                println(project, line, getOutputAttributes(Color.RED, null));
            } else {
                println(project, line, ConsoleViewContentType.ERROR_OUTPUT);
            }
        }
    }

    /**
     * format sql statements
     *
     * @param sql
     * @return
     */
    public static String format(String sql) {
        Formatter formatter = new BasicFormatterImpl();
        return formatter.format(sql);
    }
}
