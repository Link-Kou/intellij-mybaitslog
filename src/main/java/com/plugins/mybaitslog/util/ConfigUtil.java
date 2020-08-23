package com.plugins.mybaitslog.util;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowManager;

/**
 * 配置项参数,设置持久化
 *
 * @author lk
 * @version 1.0
 * @date 2020/8/23 17:14
 */
public class ConfigUtil {

    /**
     * 显示窗口
     *
     * @param project
     */
    public static void setShowMyBatisLog(Project project) {
        //查看配置文件 plugin.xml 中 toolWindow 的id
        ToolWindow toolWindow = ToolWindowManager.getInstance(project).getToolWindow("MyBatis Log");
        if (toolWindow != null) {
            if (!toolWindow.isVisible()) {
                toolWindow.show(() -> {
                });
            }
            if (!toolWindow.isActive()) {
                //激活Restore Sql tab
                toolWindow.activate(() -> {
                });
            }
        }
    }

    /**
     * 获取关键字常量配置
     *
     * @param project 项目
     * @return string
     */
    public static String getPreparing(Project project) {
        if (project == null) {
            return StringConst.PREPARING;
        }
        return PropertiesComponent.getInstance(project).getValue(StringConst.PREPARING_KEY, StringConst.PREPARING);
    }

    /**
     * 获取关键字常量配置
     *
     * @param project 项目
     * @return string
     */
    public static String getParameters(Project project) {
        if (project == null) {
            return StringConst.PARAMETERS;
        }
        return PropertiesComponent.getInstance(project).getValue(StringConst.PARAMETERS_KEY, StringConst.PARAMETERS);
    }

    /**
     * 获取配置
     *
     * @param project 项目
     * @param value   值
     */
    public static void setPreparing(Project project, String value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.PREPARING_KEY, value);
    }

    /**
     * 设置配置
     *
     * @param project 项目
     * @param value   值
     */
    public static void setParameters(Project project, String value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.PARAMETERS_KEY, value);
    }

    /**
     * 设置启动过滤
     *
     * @param project 项目
     * @param value   值
     */
    public static void setStartup(Project project, int value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.STARTUP_KEY, value, 1);
    }

    /**
     * 获取启动过滤
     *
     * @param project 项目
     */
    public static boolean getStartup(Project project) {
        final int anInt = PropertiesComponent.getInstance(project).getInt(StringConst.STARTUP_KEY, 1);
        return anInt == 1;
    }
}
