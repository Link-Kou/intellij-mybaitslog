package com.plugins.mybaitslog.console;

import com.intellij.execution.Executor;
import com.intellij.execution.ExecutorRegistry;
import com.intellij.icons.AllIcons;
import com.plugins.mybaitslog.icons.Icons;
import com.plugins.mybaitslog.util.StringConst;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * 控制台名称配置
 *
 * @author ob
 */
public class ConsoleRunExecutor extends Executor {

    public static final String TOOLWINDOWS_ID = "MyBatis Log";

    @Override
    @NotNull
    public String getStartActionText() {
        return TOOLWINDOWS_ID;
    }

    @Override
    public String getToolWindowId() {
        return TOOLWINDOWS_ID;
    }

    @Override
    public Icon getToolWindowIcon() {
        return Icons.MyBatisIcon;
    }

    @Override
    @NotNull
    public Icon getIcon() {
        return Icons.MyBatisIcon;
    }

    @Override
    public Icon getDisabledIcon() {
        return AllIcons.Plugins.Disabled;
    }

    @Override
    public String getDescription() {
        return null;
    }

    @Override
    @NotNull
    public String getActionName() {
        return TOOLWINDOWS_ID;
    }

    @Override
    @NotNull
    public String getId() {
        return StringConst.PLUGIN_ID;
    }

    @Override
    public String getContextActionId() {
        return "MyBatisLogActionId";
    }

    @Override
    public String getHelpId() {
        return null;
    }

    public static Executor getRunExecutorInstance() {
        return ExecutorRegistry.getInstance().getExecutorById(StringConst.PLUGIN_ID);
    }
}
