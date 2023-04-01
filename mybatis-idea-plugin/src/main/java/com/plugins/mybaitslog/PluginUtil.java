package com.plugins.mybaitslog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class PluginUtil {

    /**
     * 获取核心Jar路径
     *
     * @return String
     */
    public static String getAgentCoreJarPath() {
        return getJarPathByStartWith();
    }

    /**
     * 根据jar包的前缀名称获路径
     *
     * @return String
     */
    private static String getJarPathByStartWith() {
        //向下兼容
        String defaultBaseDir = System.getProperty("java.io.tmpdir");
        String d = defaultBaseDir + "mybatis-agent-1.0.18-all.jar";
        File file = new File(d);
        if (file.exists()) {
            return d;
        }
        int length;
        byte[] b = new byte[1024];
        try (FileOutputStream fos = new FileOutputStream(d)) {
            try (InputStream resourceAsStream = PluginUtil.class.getResourceAsStream("/mybatis-agent-1.0.18-all.jar");) {
                while ((length = resourceAsStream.read(b)) != -1) {
                    fos.write(b, 0, length);// 写入数据
                    fos.flush();
                }
            }
            return d;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
