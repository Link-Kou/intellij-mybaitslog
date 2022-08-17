package com.plugins.mybaitslog;

import com.intellij.execution.filters.ConsoleDependentInputFilterProvider;
import com.intellij.execution.filters.InputFilter;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.project.Project;
import com.intellij.psi.search.GlobalSearchScope;
import com.plugins.mybaitslog.filter.MyBatisLogInputFilter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MyBatisLogInputFilterProvider extends ConsoleDependentInputFilterProvider {

    @Override
    public @NotNull List<InputFilter> getDefaultFilters(@NotNull ConsoleView consoleView, @NotNull Project project, @NotNull GlobalSearchScope globalSearchScope) {
        return List.of(new MyBatisLogInputFilter(project));
    }


}
