package com.plugins.mybaitslog.util;

/**
 * 字符串常量
 * @author lk
 * @version 1.0
 * @date 2020/8/23 17:14
 */
public class KeyNameUtil {
    public static final String PROJECT_ID = "MyBatisLogPlugin";
    public static final String PREPARING = "Preparing:";
    public static final String PARAMETERS = "Parameters:";
    public static final String LINE = " ";
    /**
     * 无法格式输出
     */
    public static final String SQL_NULL = "没有可以格式化内容";
    /**
     * 存储的key名称
     */
    public static final String DB_PREPARING_KEY = PROJECT_ID + "preparing";
    /**
     * 存储的key名称
     */
    public static final String DB_PARAMETERS_KEY = PROJECT_ID + "parameters";
    /**
     * 存储的key名称 是否允许运行
     */
    public static final String DB_STARTUP_KEY = PROJECT_ID + "startup";
}
