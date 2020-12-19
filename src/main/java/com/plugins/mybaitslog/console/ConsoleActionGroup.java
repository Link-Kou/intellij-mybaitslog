package com.plugins.mybaitslog.console;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import com.intellij.openapi.project.DumbAware;
import com.plugins.mybaitslog.util.SqlProUtil;
import org.jetbrains.annotations.NotNull;

/**
 * 左侧工具栏
 *
 * @author lk
 * @version 1.0
 * @date 2020/4/10 22:00
 */
public class ConsoleActionGroup {

    private static Runnable myFilterAction;

    public static void withFilter(Runnable filter) {
        ConsoleActionGroup.myFilterAction = filter;
    }

    /**
     * 过滤按钮
     */
    public static class FilterAction extends AnAction implements DumbAware {


        public FilterAction() {
            super("Filter", "Filter", AllIcons.General.Filter);
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            ConsoleActionGroup.myFilterAction.run();
        }
    }


    /**
     * 开启过滤按钮
     */
    public static class FormatAction extends ToggleAction implements DumbAware {


        public FormatAction() {
            super("Ellipsis", "Ellipsis", AllIcons.General.Ellipsis);
        }

        @Override
        public boolean isSelected(@NotNull AnActionEvent anActionEvent) {
            return SqlProUtil.Ellipsis;
        }

        @Override
        public void setSelected(@NotNull AnActionEvent anActionEvent, boolean b) {
            SqlProUtil.Ellipsis = b;
        }
    }
}
