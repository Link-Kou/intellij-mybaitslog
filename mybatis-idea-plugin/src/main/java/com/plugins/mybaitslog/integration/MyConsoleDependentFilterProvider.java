package com.plugins.mybaitslog.integration;

import com.intellij.execution.filters.ConsoleDependentFilterProvider;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import org.jetbrains.annotations.NotNull;

/**
 * A <code>MyConsoleActionsPostProcessor</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/3/30 16:10</b></p>
 */
public class MyConsoleDependentFilterProvider extends ConsoleDependentFilterProvider {

    @Override
    public Filter @NotNull [] getDefaultFilters(@NotNull Project project) {
        return super.getDefaultFilters(project);
    }

    @Override
    public Filter @NotNull [] getDefaultFilters(@NotNull ConsoleView consoleView, @NotNull Project project, @NotNull GlobalSearchScope globalSearchScope) {
        Filter filter = new MyBatisLogFilter(project);
        return new Filter[]{filter};
    }
}
