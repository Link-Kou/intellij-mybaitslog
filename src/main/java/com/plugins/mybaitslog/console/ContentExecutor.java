package com.plugins.mybaitslog.console;

import com.intellij.execution.ExecutionManager;
import com.intellij.execution.Executor;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.TextConsoleBuilder;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.RunContentDescriptor;
import com.intellij.execution.ui.RunnerLayoutUi;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Computable;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.plugins.mybaitslog.action.ShowLogInConsoleAction;
import com.plugins.mybaitslog.util.ConfigUtil;
import com.plugins.mybaitslog.util.StringConst;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * console 描述
 */
public class ContentExecutor implements Disposable {

    private final Project myProject;

    private final List<Filter> myFilterList = new ArrayList<>();

    private Runnable myFilterAction;

    private Runnable myRerunAction;

    private Runnable myStopAction;

    private Runnable myFormatAction;

    private Computable<Boolean> myStopEnabled;

    /**
     * 插件窗口标题
     */
    private String myTitle = "";

    private ConsoleView consoleView = null;

    private boolean myActivateToolWindow = true;

    public ContentExecutor(@NotNull Project project) {
        myProject = project;
        consoleView = createConsole(project);
        ConfigUtil.consoleViewMap.put(project.getBasePath(), consoleView);
    }

    public ContentExecutor withTitle(String title) {
        myTitle = title;
        return this;
    }

    public ContentExecutor withFilter(Runnable filter) {
        myFilterAction = filter;
        return this;
    }


    public ContentExecutor withRerun(Runnable rerun) {
        myRerunAction = rerun;
        return this;
    }

    public ContentExecutor withStop(@NotNull Runnable stop, @NotNull Computable<Boolean> stopEnabled) {
        myStopAction = stop;
        myStopEnabled = stopEnabled;
        return this;
    }

    public ContentExecutor withFormat(Runnable format) {
        myFormatAction = format;
        return this;
    }

    public ContentExecutor withActivateToolWindow(boolean activateToolWindow) {
        myActivateToolWindow = activateToolWindow;
        return this;
    }

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
    public void run() {
        if (myProject.isDisposed()) {
            return;
        }
        FileDocumentManager.getInstance().saveAllDocuments();
        Executor executor = ConsoleRunExecutor.getRunExecutorInstance();
        if (executor == null) {
            return;
        }
        DefaultActionGroup actions = new DefaultActionGroup();
        // Create runner UI layout
        final RunnerLayoutUi.Factory factory = RunnerLayoutUi.Factory.getInstance(myProject);
        final RunnerLayoutUi layoutUi = factory.create(" ", " ", " ", myProject);
        //Tab 控件
        RunContentDescriptor descriptor = new RunContentDescriptor(null, null, layoutUi.getComponent(), "Output Window", AllIcons.Debugger.ThreadRunning);
        descriptor.setExecutionId(System.nanoTime());
        //左边显示的控件
        final JComponent consolePanel = createConsolePanel(consoleView);
        final Content content = layoutUi.createContent(" ", consolePanel, " ", AllIcons.Debugger.Console, null);
        content.setCloseable(false);
        layoutUi.addContent(content);
        layoutUi.getOptions().setMinimizeActionEnabled(false);
        layoutUi.getOptions().setMoveToGridActionEnabled(false);
        //右边显示控件
        layoutUi.getOptions().setLeftToolbar(createActionToolbar(consolePanel, consoleView, layoutUi, executor), "RunnerToolbar");

        Disposer.register(descriptor, this);
        Disposer.register(content, consoleView);
        if (myStopAction != null) {
            Disposer.register(consoleView, () -> myStopAction.run());
        }
        for (AnAction action : consoleView.createConsoleActions()) {
            actions.add(action);
        }

        ExecutionManager.getInstance(myProject).getContentManager().showRunContent(executor, descriptor);
        if (myActivateToolWindow) {
            activateToolWindow();
        }
    }

    /**
     * 创建工具栏
     *
     * @param consolePanel
     * @param consoleView
     * @param myUi
     * @param runExecutorInstance
     * @return
     */
    @NotNull
    private ActionGroup createActionToolbar(JComponent consolePanel, ConsoleView consoleView, @NotNull final RunnerLayoutUi myUi, Executor runExecutorInstance) {
        final DefaultActionGroup actionGroup = new DefaultActionGroup();
        actionGroup.add(new FilterAction());
        actionGroup.add(new FormatAction());
        actionGroup.add(new RerunAction(consolePanel, consoleView));
        actionGroup.add(new StopAction());
        actionGroup.add(consoleView.createConsoleActions()[2]);
        actionGroup.add(consoleView.createConsoleActions()[3]);
        actionGroup.add(consoleView.createConsoleActions()[5]);
        return actionGroup;
    }

    /**
     * 激活窗口
     */
    public void activateToolWindow() {
        ApplicationManager.getApplication().invokeLater(() -> {
            final ToolWindow toolWindow = ToolWindowManager.getInstance(myProject).getToolWindow(StringConst.TOOLWINDOWS_ID);
            if (null != toolWindow) {
                if (!ConfigUtil.active || !toolWindow.isAvailable()) {
                    toolWindow.activate(null);
                }
            }
        });
    }


    @Override
    public void dispose() {
        Disposer.dispose(this);
    }

    /**
     * 重新运行
     */
    private class RerunAction extends AnAction implements DumbAware {
        private final ConsoleView consoleView;

        public RerunAction(JComponent consolePanel, ConsoleView consoleView) {
            super("Reset", "Reset", AllIcons.Actions.Restart);
            this.consoleView = consoleView;
            registerCustomShortcutSet(CommonShortcuts.getRerun(), consolePanel);
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            Disposer.dispose(consoleView);
            myRerunAction.run();
        }

        @Override
        public void update(AnActionEvent e) {
            e.getPresentation().setVisible(myRerunAction != null);
            e.getPresentation().setIcon(AllIcons.Actions.Restart);
        }
    }

    /**
     * 停止按钮
     */
    private class StopAction extends AnAction implements DumbAware {
        public StopAction() {
            super("Stop", "Stop", AllIcons.Actions.Suspend);
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            myStopAction.run();
        }

        @Override
        public void update(AnActionEvent e) {
            e.getPresentation().setVisible(myStopAction != null);
            e.getPresentation().setEnabled(myStopEnabled != null && myStopEnabled.compute());
        }
    }

    /**
     * 格式化 按钮
     */
    private class FormatAction extends ToggleAction implements DumbAware {
        public FormatAction() {
            super("Format Sql", "Format Sql", AllIcons.General.InspectionsEye);
        }

        @Override
        public boolean isSelected(AnActionEvent e) {
            return e.getProject() == null ? false : ConfigUtil.getSqlFormat(e.getProject());
        }

        @Override
        public void setSelected(AnActionEvent anActionEvent, boolean state) {
            myFormatAction.run();
        }
    }

    /**
     * 过滤按钮
     */
    private class FilterAction extends AnAction implements DumbAware {
        public FilterAction() {
            super("Filter", "Filter", AllIcons.General.Filter);
        }

        @Override
        public void actionPerformed(AnActionEvent e) {
            myFilterAction.run();
        }
    }
}
