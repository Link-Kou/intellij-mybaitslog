package com.plugins.mybaitslog;

import com.intellij.execution.Executor;
import com.intellij.execution.configurations.JavaParameters;
import com.intellij.execution.configurations.ParametersList;
import com.intellij.execution.configurations.RunConfiguration;
import com.intellij.execution.configurations.RunProfile;
import com.intellij.execution.runners.JavaProgramPatcher;
import com.intellij.openapi.projectRoots.JavaSdk;
import com.intellij.openapi.projectRoots.JavaSdkVersion;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.diagnostic.Logger;
import com.plugins.mybaitslog.unix.UnixServer;

import java.util.*;

public class PerRun extends JavaProgramPatcher {

    private Logger LOG = Logger.getInstance(PerRun.class);

    //com.intellij.execution.junit
    @Override
    public void patchJavaParameters(Executor executor, RunProfile configuration, JavaParameters javaParameters) {
        if (!Config.Idea.getStartup()) {
            return;
        }
        if (!(configuration instanceof RunConfiguration)) {
            PluginUtil.Notificat_AddConfiguration();
        }
        final String name = configuration.getClass().getPackage().getName();
        //默认关闭，做个冒泡提示
        Config.Idea.setPerRunMap(name, false, false);
        //IDEA中有大量的执行器。这里需要做排除和生效处理
        final Map<String, Boolean> perRunMap = Config.Idea.getPerRunMap();
        if (!perRunMap.get(name)) {
            return;
        }
        String id = null;
        if (configuration instanceof RunConfiguration) {
            final RunConfiguration runConfiguration = (RunConfiguration) configuration;
            id = UnixServer.getId(runConfiguration.getProject());
        }
        //
        Sdk jdk = javaParameters.getJdk();
        if (Objects.isNull(jdk)) {
            PluginUtil.Notificat_Error("JDK does not exist in the project");
            return;
        }
        JavaSdkVersion version = JavaSdk.getInstance().getVersion(jdk);
        if (Objects.isNull(version)) {
            return;
        }
        if (version.compareTo(JavaSdkVersion.JDK_1_8) < 0) {
            PluginUtil.Notificat_Error("JDK < 1.8");
            return;
        }
        String agentCoreJarPath = PluginUtil.getAgentCoreJarPath();
        if (null != agentCoreJarPath) {
            //RunConfiguration runConfiguration = (RunConfiguration) configuration;
            ParametersList vmParametersList = javaParameters.getVMParametersList();
            String agentParameter = "-javaagent:" + agentCoreJarPath;
            LOG.info("MyBatis Log EasyPlus" + agentParameter);
            //JDK17的改进
            final JavaSdkVersion javaSdkVersion = JavaSdkVersion.fromVersionString("17");
            if (null != javaSdkVersion && version.compareTo(javaSdkVersion) >= 0) {
                final ArrayList<String> addOpens = Config.Idea.getAddOpens();
                for (String opens : addOpens) {
                    if (!vmParametersList.hasParameter(opens)) {
                        vmParametersList.addParametersString(opens);
                    }
                }
            }
            if (!vmParametersList.hasParameter(agentParameter)) {
                if (null != id) {
                    agentParameter += "=" + id;
                }
                vmParametersList.prepend(agentParameter);
            }
            if (Config.Idea.getRunNotification()) {
                PluginUtil.Notificat_Success();
            }
            //vmParametersList.addParametersString("-javaagent:\"" + agentCoreJarPath + "\"");
            //vmParametersList.addNotEmptyProperty("guide-idea-plugin-probe.projectId", runConfiguration.getProject().getLocationHash());
        } else {
            PluginUtil.Notificat_Error("Agent Jar Path Error");
        }
    }

}
