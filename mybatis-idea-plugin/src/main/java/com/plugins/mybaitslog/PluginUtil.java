package com.plugins.mybaitslog;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.extensions.PluginId;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;

public class PluginUtil {

    private static final int _2019DOT1 = 201;

    private static final int BaselineVersion = ApplicationInfo.getInstance().getBuild().getBaselineVersion();


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
        PluginId pluginId = PluginId.getId("com.linkkou.plugin.intellij.assistant.mybaitslog");
        if (BaselineVersion < _2019DOT1) {
            //向下兼容
            String defaultBaseDir = System.getProperty("java.io.tmpdir");
            String d = defaultBaseDir + "mybatis-agent-1.0.18-all.jar";
            File file = new File(d);
            if (file.exists()) {
                return d;
            }
            byte[] b = new byte[1024];
            try (FileOutputStream fos = new FileOutputStream(d)) {
                try (InputStream resourceAsStream = PluginUtil.class.getResourceAsStream("mybatis-agent-1.0.18-all.jar");) {
                    while ((resourceAsStream.read(b)) != -1) {
                        fos.write(b);// 写入数据
                    }
                }
                return d;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            final File filePlugin = PluginManagerCore.getPlugin(pluginId).getPluginPath().toFile();
            final File[] fileslibs = filePlugin.listFiles();
            for (File listFile : fileslibs) {
                if ("lib".equals(listFile.getName())) {
                    final File[] fileslib = listFile.listFiles();
                    for (File file : fileslib) {
                        if ("mybatis-agent-1.0.18-all.jar".equals(file.getName())) {
                            return file.toPath().toString();
                        }
                    }
                }
            }
        }
        return null;
    }

}
