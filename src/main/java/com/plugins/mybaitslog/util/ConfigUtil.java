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

    public static final String PROJECT_ID = "MyBatisLogPlugin";

    public static final String PARAMETERS = "SQLStructure:";
    public static final String LINE = " ";

    public static final String SQL_Start_Line = "- ==>  ";
    /**
     * 存储的key名称
     */
    public static final String DB_PARAMETERS_KEY = PROJECT_ID + "SQLStructure";
    /**
     * 存储的key名称 是否允许运行
     */
    public static final String DB_STARTUP_KEY = PROJECT_ID + "startup";


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
     * @return string
     */
    public static String getParameters() {
        return PropertiesComponent.getInstance().getValue(ConfigUtil.DB_PARAMETERS_KEY, ConfigUtil.PARAMETERS);
    }

    /**
     * 设置配置
     *
     * @param value        值
     * @param defaultValue 默认
     */
    public static void setParameters(String value, String defaultValue) {
        PropertiesComponent.getInstance().setValue(ConfigUtil.DB_PARAMETERS_KEY, null == value ? defaultValue : value);
    }

    /**
     * 设置启动过滤
     *
     * @param value   值
     */
    public static void setStartup(int value) {
        PropertiesComponent.getInstance().setValue(ConfigUtil.DB_STARTUP_KEY, value, 1);
    }

    /**
     * 获取启动过滤
     *
     */
    public static boolean getStartup() {
        final int anInt = PropertiesComponent.getInstance().getInt(ConfigUtil.DB_STARTUP_KEY, 1);
        return anInt == 1;
    }
}
