package com.plugins.mybaitslog.console;

import com.intellij.execution.ConsoleFolding;
import com.intellij.openapi.project.Project;
import com.plugins.mybaitslog.util.ConfigUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ConsoleExceptionFolding extends ConsoleFolding {

    @Override
    public boolean shouldFoldLine(@NotNull Project project, @NotNull String line) {
        if (line.contains(ConfigUtil.SQL_Start_Line)) {
            return true;
        }
        return false;
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull Project project, @NotNull List<String> lines) {
        return " <" + lines.size() + " more...>";
    }
}