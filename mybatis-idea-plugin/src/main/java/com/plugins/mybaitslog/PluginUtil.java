package com.plugins.mybaitslog;

import com.intellij.ide.plugins.IdeaPluginDescriptor;
import com.intellij.ide.plugins.PluginManagerCore;
import com.intellij.notification.*;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.ui.MessageType;
import com.plugins.mybaitslog.gui.FilterSetting;
import org.jetbrains.annotations.NotNull;

import javax.swing.event.HyperlinkEvent;
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
                    if ("mybatis-agent-1.0.24-all.jar".equals(file.getName())) {
                        return file.toPath().toString();
                    }
                }
            }
        }
        return null;
    }

    public static void Notificat_AddConfiguration() {
        NotificationListener.Adapter notificationListener = new NotificationListener.Adapter() {
            @Override
            protected void hyperlinkActivated(@NotNull Notification notification, @NotNull HyperlinkEvent e) {
                // e.getDescription() 的值就是标签 a 中的 href 属性值
                //启动filter配置
                FilterSetting dialog = new FilterSetting();
                dialog.pack();
                dialog.setSize(520, 420);//配置大小
                dialog.setResizable(true);
                dialog.setLocationRelativeTo(null);//位置居中显示
                dialog.setVisible(true);
            }
        };
        String content = "There is a new unknown actuator, Please exclude the options for configuration.<a href=\"configuration\">Open the configuration window</a>";
        NotificationGroup notificationGroup = new NotificationGroup("Notification", NotificationDisplayType.BALLOON, false);
        Notification notification = notificationGroup.createNotification("MyBatis Log EasyPlus", "", content, NotificationType.WARNING, notificationListener);
        Notifications.Bus.notify(notification);
    }

    public static void Notificat_Success() {
        String content = "MyBatis Log EasyPlus Run";
        NotificationGroup notificationGroup = new NotificationGroup("Notification", NotificationDisplayType.BALLOON, false);
        Notification notification = notificationGroup.createNotification("MyBatis Log EasyPlus", "", content, NotificationType.INFORMATION);
        Notifications.Bus.notify(notification);
    }

    public static void Notificat_Error(String error) {
        String content = error + ", MyBatis Log EasyPlus Unable to Run";
        NotificationGroup notificationGroup = new NotificationGroup("Notification", NotificationDisplayType.BALLOON, false);
        Notification notification = notificationGroup.createNotification("MyBatis Log EasyPlus", "", content, NotificationType.ERROR);
        Notifications.Bus.notify(notification);
    }

}
