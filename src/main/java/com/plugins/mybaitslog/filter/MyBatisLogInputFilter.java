package com.plugins.mybaitslog.filter;

import com.intellij.execution.filters.InputFilter;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.plugins.mybaitslog.util.ConfigUtil;
import com.plugins.mybaitslog.util.PrintlnUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

/**
 * 语句过滤器
 *
 * @author lk
 * @version 1.0
 * @date 2020/4/10 22:00
 */
public class MyBatisLogInputFilter implements InputFilter {

    private final Project project;


    public MyBatisLogInputFilter(Project project) {
        this.project = project;
    }

    @Override
    public @Nullable List<Pair<String, ConsoleViewContentType>> applyFilter(@NotNull String currentLine, @NotNull ConsoleViewContentType consoleViewContentType) {
        if (ConfigUtil.getStartup()) {
            PrintlnUtil.prints(project, currentLine);
        }
        return Collections.singletonList(Pair.create(currentLine, consoleViewContentType));
    }
}
