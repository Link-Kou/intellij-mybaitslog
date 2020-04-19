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

    public static void setIndexNum(Project project, int value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.INDEXNUMKEY, value, 1);
    }

    public static boolean getSqlFormat(Project project) {
        return PropertiesComponent.getInstance(project).getBoolean(StringConst.SQLFORMATKEY);
    }

    public static int getIndexNum(Project project) {
        return PropertiesComponent.getInstance(project).getInt(StringConst.INDEXNUMKEY, 1);
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
        return PropertiesComponent.getInstance(project).getValue(StringConst.PREPARING_KEY);
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
        return PropertiesComponent.getInstance(project).getValue(StringConst.PARAMETERS_KEY);
    }

    public static void setPreparing(Project project, String value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.PREPARING_KEY, value);
    }

    public static void setParameters(Project project, String value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.PARAMETERS_KEY, value);
    }

    public static void setStartup(Project project, int value) {
        PropertiesComponent.getInstance(project).setValue(StringConst.STARTUP_KEY, value, 1);
    }
}
