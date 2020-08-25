package com.plugins.mybaitslog;

import com.plugins.mybaitslog.filter.MyBatisLogFilter;
import org.jetbrains.annotations.NotNull;

import com.intellij.execution.filters.ConsoleFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.openapi.project.Project;

/**
 * Console 输出监控
 * @author lk
 * @version 1.0
 * @date 2020/4/10 21:46
 */
public class MyBatisLogProvider implements ConsoleFilterProvider {
    @NotNull
    @Override
    public Filter[] getDefaultFilters(@NotNull Project project) {
        Filter filter = new MyBatisLogFilter(project);
        return new Filter[]{filter};
    }
}
