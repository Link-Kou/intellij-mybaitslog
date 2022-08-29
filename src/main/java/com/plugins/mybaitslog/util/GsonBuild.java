package com.plugins.mybaitslog.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @author lk
 * @version 1.0
 * @date 2022/8/29 19:11
 */
public class GsonBuild {

    public static Gson gson = new GsonBuilder()
            .setDateFormat("yyyy-MM-dd HH:mm:ss")
            //当Map的key为复杂对象时,需要开启该方法
            .enableComplexMapKeySerialization()
            //当字段值为空或null时，依然对该字段进行转换
            .serializeNulls()
            //防止特殊字符出现乱码
            .disableHtmlEscaping()
            .create();


    public static Gson getGson() {
        return gson;
    }
}
