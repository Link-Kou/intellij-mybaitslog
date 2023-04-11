package com.plugins.mybaitslog;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;

import java.awt.*;
import java.util.*;
import java.util.List;

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


        public static final String FORMAT_SQL_1 = PROJECT_ID + "SQL_FORMAT";

        public static final String PARAMETERS = "SQLStructure:";

        public static final Map<String, String> ColorMap = new HashMap<String, String>() {{
            put("select", "42,199,180");
            put("update", "158,190,92");
            put("delete", "231,29,54");
            put("insert", "97,163,172");
            put("other", "252,255,253");
        }};

        public static final Map<String, Boolean> PerRunMap = new HashMap<String, Boolean>() {{
            put("org.jetbrains.idea.maven.execution", false);
            put("com.intellij.execution.junit", true);
            put("com.intellij.spring.boot.run", true);
        }};

        public static final ArrayList<String> AddOpens = new ArrayList<String>() {{
            add("--add-opens java.base/java.lang=ALL-UNNAMED");
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
            PropertiesComponent.getInstance().setValue(DB_STARTUP_KEY, Integer.toString(value), "1");
        }

        /**
         * 获取启动过滤
         */
        public static boolean getStartup() {
            final String value = PropertiesComponent.getInstance().getValue(DB_STARTUP_KEY, "1");
            return  Integer.parseInt(value) == 1;
        }

        /**
         * 设置格式化
         *
         * @param value 值
         */
        public static void setFormatSql(int value) {
            PropertiesComponent.getInstance().setValue(FORMAT_SQL_1, Integer.toString(value), "0");
        }

        /**
         * 设置格式化
         */
        public static boolean getFormatSql() {
            final String value = PropertiesComponent.getInstance().getValue(FORMAT_SQL_1, "0");
            return Integer.parseInt(value) == 1;
        }

        /**
         * 设置配置
         *
         * @param type 值
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

        public static ArrayList<String> getAddOpens() {
            final String value = PropertiesComponent.getInstance().getValue("addOpens:", String.join(";", AddOpens));
            return new ArrayList<String>(Arrays.asList(value.split(";")));
        }

        public static void setAddOpens(String opens) {
            final List<String> openlist = Arrays.asList(opens.split("\n"));
            PropertiesComponent.getInstance().setValue("addOpens:", String.join(";", openlist));
        }

        public static Map<String, Boolean> getPerRunMap() {
            final String value = PropertiesComponent.getInstance().getValue("perRunMap:");
            if (null == value) {
                return PerRunMap;
            }
            final String[] split1 = value.split(";");
            final List<String> strings = Arrays.asList(split1);
            strings.forEach(e -> {
                final String[] split2 = e.split("=");
                if (!PerRunMap.containsKey(split2[0])) {
                    PerRunMap.put(split2[0], Boolean.valueOf(split2[1]));
                }
            });
            return PerRunMap;
        }

        public static void setPerRunMap(String name, boolean run, boolean cover) {
            final Boolean aBoolean = PerRunMap.containsKey(name);
            if (cover) {
                PerRunMap.put(name, run);
            }
            if (!cover && !aBoolean) {
                PerRunMap.put(name, run);
            }
            ArrayList<String> value = new ArrayList<String>();
            for (Map.Entry<String, Boolean> next : PerRunMap.entrySet()) {
                final String format = String.format("%s=%s", next.getKey(), next.getValue());
                value.add(format);
            }
            PropertiesComponent.getInstance().setValue("perRunMap:", String.join(";", value));
        }


    }
}
