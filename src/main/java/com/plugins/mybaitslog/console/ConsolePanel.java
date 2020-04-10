package com.plugins.mybaitslog.console;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.ActionToolbar;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.SimpleToolWindowPanel;
import com.plugins.mybaitslog.action.gui.FilterSetting;
import com.plugins.mybaitslog.util.PrintUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * console 描述
 */
public class ConsolePanel {

    private final List<Filter> myFilterList = new ArrayList<>();

    /**
     * 创建 输出面板
     *
     * @param project
     * @return
     */
    private ConsoleView createConsole(@NotNull Project project) {
        TextConsoleBuilder consoleBuilder = TextConsoleBuilderFactory.getInstance().createBuilder(project);
        consoleBuilder.filters(myFilterList);
        ConsoleView console = consoleBuilder.getConsole();
        PrintUtil.setConsoleView(console);
        return console;
    }


    private static JComponent createConsolePanel(ConsoleView view) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(view.getComponent(), BorderLayout.CENTER);
        return panel;
    }


    /**
     * 构建面板
     */
    public JComponent ConsolePanel(final Project myProject) {
        final ConsoleView consoleView = createConsole(myProject);
        //左边显示的控件
        final JComponent consolePanel = createConsolePanel(consoleView);
        final ActionToolbar actionToolbar = createActionToolbar(myProject, consolePanel, consoleView);
        actionToolbar.setTargetComponent(consolePanel);

        SimpleToolWindowPanel panel = new SimpleToolWindowPanel(false, true);
        panel.setContent(consolePanel);
        panel.setToolbar(actionToolbar.getComponent());

        DefaultActionGroup actions = new DefaultActionGroup();
        for (AnAction action : consoleView.createConsoleActions()) {
            actions.add(action);
        }
        final JComponent component = panel.getComponent();
        return component;
    }

    /**
     * 创建工具栏
     *
     * @param consolePanel
     * @param consoleView
     * @return
     */
    @NotNull
    private ActionToolbar createActionToolbar(final Project project, JComponent consolePanel, ConsoleView consoleView) {
        final DefaultActionGroup actionGroup = new DefaultActionGroup();
        ConsoleActionGroup.withFilter(() -> {
            //启动filter配置
            FilterSetting dialog = new FilterSetting(project);
            dialog.pack();
            dialog.setSize(600, 400);//配置大小
            dialog.setLocationRelativeTo(null);//位置居中显示
            dialog.setVisible(true);
        });
        actionGroup.add(new ConsoleActionGroup.FilterAction());
        actionGroup.add(consoleView.createConsoleActions()[2]);
        actionGroup.add(consoleView.createConsoleActions()[3]);
        actionGroup.add(consoleView.createConsoleActions()[5]);
        return ActionManager.getInstance().createActionToolbar("EventLog", actionGroup, false);
    }
}
