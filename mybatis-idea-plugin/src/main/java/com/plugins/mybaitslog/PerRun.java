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

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class PerRun extends JavaProgramPatcher {

    private Set<String> stringSet = new HashSet<String>() {{
        add("org.jetbrains.idea.maven.execution");
    }};
    //com.intellij.execution.junit
    @Override
    public void patchJavaParameters(Executor executor, RunProfile configuration, JavaParameters javaParameters) {
        if (!(configuration instanceof RunConfiguration)) {
            return;
        }
        if (stringSet.contains(configuration.getClass().getPackage().getName())) {
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
            vmParametersList.prepend("-javaagent:" + agentCoreJarPath);
            //vmParametersList.addParametersString("-javaagent:\"" + agentCoreJarPath + "\"");
            //vmParametersList.addNotEmptyProperty("guide-idea-plugin-probe.projectId", runConfiguration.getProject().getLocationHash());
        }
    }

}
