package com.plugins.mybaitslog;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import com.plugins.mybaitslog.action.ShowLogInConsoleAction;
import com.plugins.mybaitslog.util.StringConst;
import org.jetbrains.annotations.NotNull;

/**
 * 初始化 Console 的面板
 */
public class MyBatisLogStartupActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        if (null == project) {
            return;
        }
        int startup = PropertiesComponent.getInstance(project).getInt(StringConst.STARTUP_KEY, 1);
        if (startup == 1) {
            new ShowLogInConsoleAction(project).showLogInConsole(project);
        }
    }
}
