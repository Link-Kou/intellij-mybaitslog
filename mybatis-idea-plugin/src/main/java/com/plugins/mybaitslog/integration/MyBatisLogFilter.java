package com.plugins.mybaitslog.integration;

import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import com.intellij.execution.filters.Filter;
import com.plugins.mybaitslog.Config;
import com.plugins.mybaitslog.console.PrintlnUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import com.intellij.openapi.diagnostic.Logger;

import java.awt.*;

import static com.plugins.mybaitslog.Config.KEY_ERROR_NAME;
import static com.plugins.mybaitslog.Config.KEY_NAME;


/**
 * A <code>MyBatisLogFilter</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/3/30 16:20</b></p>
 */
public class MyBatisLogFilter implements Filter {
    private static final Logger LOG = Logger.getInstance(MyBatisLogFilter.class);

    private final Project project;

    public MyBatisLogFilter(Project project) {
        this.project = project;
    }

    @Override
    public @Nullable Result applyFilter(@NotNull String s, int entireLength) {
        //计算偏移
        int offset = entireLength;
        if (s != null) {
            offset = entireLength - s.length();
        }
        if (s.contains(KEY_NAME)) {
            final TextAttributes textAttributes = new TextAttributes();
            textAttributes.setForegroundColor(Color.BLACK);
            PrintlnUtil.prints(project, s);
            return new Result(offset, entireLength, null, textAttributes);
        }
        if (s.contains(KEY_ERROR_NAME)) {
            PrintlnUtil.println(project, Config.KEY_ERROR_NAME + s +"\n", ConsoleViewContentType.ERROR_OUTPUT);
        }
        return null;
    }
}
