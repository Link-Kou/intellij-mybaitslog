package com.plugins.mybaitslog.filter;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.plugins.mybaitslog.util.*;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 语句过滤器
 *
 * @author lk
 * @version 1.0
 * @date 2020/4/10 22:00
 */
public class MyBatisLogFilter implements Filter {

    private final Project project;
    private final ConsoleView consoleView;


    public MyBatisLogFilter(ConsoleView consoleView, Project project) {
        this.project = project;
        this.consoleView = consoleView;
    }

    @Nullable
    @Override
    public Result applyFilter(final @NotNull String currentLine, int endPoint) {
        return null;
        /*if (ConfigUtil.getStartup()) {
            if (PrintlnUtil.getConsoleView(project) != consoleView) {
                PrintlnUtil.prints(project, currentLine);
            }
        }
        return null;*/
    }

}
