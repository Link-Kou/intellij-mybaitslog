package com.plugins.mybaitslog.util;

/**
 * 字符串常量
 * @author lk
 * @version 1.0
 * @date 2020/8/23 17:14
 */
public class KeyNameUtil {
    public static final String PROJECT_ID = "MyBatisLogPlugin";

    public static final String PARAMETERS = "SQLStructure:";
    public static final String LINE = " ";
    /**
     * 无法格式输出
     */
    public static final String SQL_NULL = "没有可以格式化内容";
    public static final String SQL_Line = "--  ";
    /**
     * 存储的key名称
     */
    public static final String DB_PARAMETERS_KEY = PROJECT_ID + "SQLStructure";
    /**
     * 存储的key名称 是否允许运行
     */
    public static final String DB_STARTUP_KEY = PROJECT_ID + "startup";
}
