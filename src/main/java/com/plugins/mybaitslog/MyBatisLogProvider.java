package com.plugins.mybaitslog;

import com.intellij.execution.filters.ConsoleDependentFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.plugins.mybaitslog.filter.MyBatisLogFilter;
import org.jetbrains.annotations.NotNull;

/**
 * Console 输出监控
 *
 * @author lk
 * @version 1.0
 * @date 2020/4/10 21:46
 */
public class MyBatisLogProvider extends ConsoleDependentFilterProvider {
    @Override
    public Filter @NotNull [] getDefaultFilters(@NotNull ConsoleView consoleView, @NotNull Project project, @NotNull GlobalSearchScope globalSearchScope) {
        return new Filter[]{new MyBatisLogFilter(consoleView, project)};
    }

}
