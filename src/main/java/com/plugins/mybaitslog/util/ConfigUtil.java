package com.plugins.mybaitslog.util;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

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
        final String value = PropertiesComponent.getInstance(project).getValue(StringConst.PREPARING_KEY);
        if (value == null) {
            return StringConst.PREPARING;
        }
        return value;
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
        final String value = PropertiesComponent.getInstance(project).getValue(StringConst.PARAMETERS_KEY);
        if (value == null) {
            return StringConst.PARAMETERS;
        }
        return value;
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
