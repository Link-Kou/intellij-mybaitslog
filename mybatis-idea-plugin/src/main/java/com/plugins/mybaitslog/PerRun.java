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

import java.util.*;

public class PerRun extends JavaProgramPatcher {


    //com.intellij.execution.junit
    @Override
    public void patchJavaParameters(Executor executor, RunProfile configuration, JavaParameters javaParameters) {
        if (!(configuration instanceof RunConfiguration)) {
            return;
        }
        final String name = configuration.getClass().getPackage().getName();
        Config.Idea.setPerRunMap(name, false, false);
        //IDEA中有大量的执行器。这里需要做排除和生效处理
        final Map<String, Boolean> perRunMap = Config.Idea.getPerRunMap();
        if (!perRunMap.get(name)) {
            return;
        }
        //
        Sdk jdk = javaParameters.getJdk();
        if (Objects.isNull(jdk)) {
            return;
        }
        JavaSdkVersion version = JavaSdk.getInstance().getVersion(jdk);
        if (Objects.isNull(version)) {
            return;
        }
        if (version.compareTo(JavaSdkVersion.JDK_1_8) < 0) {
            return;
        }
        String agentCoreJarPath = PluginUtil.getAgentCoreJarPath();
        if (null != agentCoreJarPath) {
            //RunConfiguration runConfiguration = (RunConfiguration) configuration;
            ParametersList vmParametersList = javaParameters.getVMParametersList();
            //JDK17的改进
            final JavaSdkVersion javaSdkVersion = JavaSdkVersion.fromVersionString("17");
            if (null != javaSdkVersion && version.compareTo(javaSdkVersion) >= 0) {
                final ArrayList<String> addOpens = Config.Idea.getAddOpens();
                vmParametersList.addAll(addOpens);
            }
            vmParametersList.prepend("-javaagent:" + agentCoreJarPath);
            //
            //vmParametersList.addParametersString("-javaagent:\"" + agentCoreJarPath + "\"");
            //vmParametersList.addNotEmptyProperty("guide-idea-plugin-probe.projectId", runConfiguration.getProject().getLocationHash());
        }
    }

}
