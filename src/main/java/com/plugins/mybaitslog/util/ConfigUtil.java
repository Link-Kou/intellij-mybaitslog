package com.plugins.mybaitslog.util;

import com.intellij.execution.ui.ConsoleView;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置项参数
 *
 * @author ob
 */
public class ConfigUtil {

    public static boolean active = false;

    public static Map<String, ConsoleView> consoleViewMap = new HashMap<>();

    public static void init(Project project) {
        if (project != null) {
            setRunning(project, false);
            setSqlFormat(project, false);
            setIndexNum(project, 1);
            setPreparing(project, StringConst.PREPARING);
            setParameters(project, StringConst.PARAMETERS);
        }
    }

    public static void setRunning(Project project, boolean value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.RUNNINGKEY, value);
    }

    public static void setSqlFormat(Project project, boolean value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.SQLFORMATKEY, value);
    }

    public static void setIndexNum(Project project, int value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.INDEXNUMKEY, value, 1);
    }

    /**
     * 获取运行Console
     *
     * @param project
     * @return
     */
    public static boolean getRunning(Project project) {
        return PropertiesComponent.getInstance(project).getBoolean(StringConst.RUNNINGKEY);
    }

    public static boolean getSqlFormat(Project project) {
        return PropertiesComponent.getInstance(project).getBoolean(StringConst.SQLFORMATKEY);
    }

    public static int getIndexNum(Project project) {
        return PropertiesComponent.getInstance(project).getInt(StringConst.INDEXNUMKEY, 1);
    }

    /**
     * 获取关键字常量配置
     * @param project 项目
     * @return string
     */
    public static String getPreparing(Project project) {
        if (project == null) {
            return StringConst.PREPARING;
        }
        return PropertiesComponent.getInstance(project).getValue(StringConst.PREPARING_KEY);
    }

    /**
     * 获取关键字常量配置
     * @param project 项目
     * @return string
     */
    public static String getParameters(Project project) {
        if (project == null) {
            return StringConst.PARAMETERS;
        }
        return PropertiesComponent.getInstance(project).getValue(StringConst.PARAMETERS_KEY);
    }

    public static void setPreparing(Project project, String value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.PREPARING_KEY, value);
    }

    public static void setParameters(Project project, String value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.PARAMETERS_KEY, value);
    }

    public static int getStartup(Project project) {
        return PropertiesComponent.getInstance(project).getInt(StringConst.STARTUP_KEY, 1);
    }

    public static void setStartup(Project project, int value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.STARTUP_KEY, value, 1);
    }
}
