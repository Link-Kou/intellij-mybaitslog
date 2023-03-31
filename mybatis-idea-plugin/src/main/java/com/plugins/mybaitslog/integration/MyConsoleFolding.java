package com.plugins.mybaitslog.integration;

import com.intellij.execution.ConsoleFolding;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static com.plugins.mybaitslog.Config.*;

/**
 * A <code>MyConsoleFolding</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/3/30 17:56</b></p>
 */
public class MyConsoleFolding extends ConsoleFolding {

    private boolean IS_SQL_START_LINE = false;

    @Override
    public boolean shouldFoldLine(@NotNull Project project, @NotNull String line) {
        if (line.contains(KEY_NAME)) {
            return true;
        }
        //region 折叠
        if (line.contains(SQL_START_LINE)) {
            this.IS_SQL_START_LINE = true;
            return false;
        }
        if (IS_SQL_START_LINE && line.contains(SQL_END_LINE)) {
            this.IS_SQL_START_LINE = false;
            return true;
        }
        if (IS_SQL_START_LINE) {
            return line.contains(SQL_MIDDLE_LINE);
        }
        //endregion
        return false;
    }

    @Override
    public @Nullable String getPlaceholderText(@NotNull Project project, @NotNull List<String> lines) {
        return "< SQLStructure >";
    }
}
