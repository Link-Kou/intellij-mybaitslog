package com.plugins.mybaitslog.console;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.plugins.mybaitslog.Config;
import com.plugins.mybaitslog.VersionControl;
import com.plugins.mybaitslog.icons.Icons;
import com.plugins.mybaitslog.rmi.RmiServer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.UUID;

/**
 * 标签窗口实现
 *
 * @author lk
 * @version 1.0
 * @date 2020/4/10 21:46
 */
public class MyBatisLogToolWindow implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        RmiServer.boot(project);
        ConsolePanel consolePanel = new ConsolePanel();
        final JComponent jComponent = consolePanel.getConsolePanel(project);
        final ContentFactory contentFactory = VersionControl.getContentFactory();
        Content content = contentFactory.createContent(jComponent, "", false);
        toolWindow.setIcon(Icons.MyBatisIcon);
        toolWindow.getContentManager().addContent(content);
    }
}
