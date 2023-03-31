package com.plugins.mybaitslog;

import com.intellij.ide.util.PropertiesComponent;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * A <code>Config</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/3/30 18:08</b></p>
 */
public class Config {

    private static final String PROJECT_ID = "MyBatisLogPlugin";

    public final static String KEY_NAME = "==>  SQLStructure: ";

    public static final String SQL_START_LINE = "- -->  ";
    public static final String SQL_MIDDLE_LINE = "    >  ";
    public static final String SQL_END_LINE = "- --<  ";

    public static class Idea {

        /**
         * 存储的key名称
         */
        private static final String DB_PARAMETERS_KEY = PROJECT_ID + "SQLStructure";
        /**
         * 存储的key名称 是否允许运行
         */
        public static final String DB_STARTUP_KEY = PROJECT_ID + "startup";

        public static final String PARAMETERS = "SQLStructure:";

        public static final Map<String, String> ColorMap = new HashMap<String, String>() {{
            put("select", "42,199,180");
            put("update", "158,190,92");
            put("delect", "231,29,54");
            put("insert", "97,163,172");
            put("other", "252,255,253");
        }};

        /**
         * 获取关键字常量配置
         *
         * @return string
         */
        public static String getParameters() {
            return PropertiesComponent.getInstance().getValue(DB_PARAMETERS_KEY, PARAMETERS);
        }

        /**
         * 设置配置
         *
         * @param value        值
         * @param defaultValue 默认
         */
        public static void setParameters(String value, String defaultValue) {
            PropertiesComponent.getInstance().setValue(DB_PARAMETERS_KEY, null == value ? defaultValue : value);
        }

        /**
         * 设置启动过滤
         *
         * @param value 值
         */
        public static void setStartup(int value) {
            PropertiesComponent.getInstance().setValue(DB_STARTUP_KEY, value, 1);
        }


        /**
         * 设置配置
         *
         * @param type        值
         */
        public static Color getColor(String type) {
            final String s = ColorMap.get(type);
            final String value = PropertiesComponent.getInstance().getValue("color:" + type, s);
            final String[] split = value.split(",");
            return new Color(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        }

        /**
         * 设置启动过滤
         *
         * @param value 值
         */
        public static void setColor(String type, Color value) {
            final String color = String.format("%s,%s,%s", value.getRed(), value.getGreen(), value.getBlue());
            PropertiesComponent.getInstance().setValue("color:" + type, color);
        }


        /**
         * 获取启动过滤
         */
        public static boolean getStartup() {
            final int anInt = PropertiesComponent.getInstance().getInt(DB_STARTUP_KEY, 1);
            return anInt == 1;
        }
    }
}
