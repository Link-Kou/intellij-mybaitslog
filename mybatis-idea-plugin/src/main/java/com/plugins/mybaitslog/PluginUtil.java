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
        return null;
    }

}
