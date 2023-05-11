package com.plugins.mybaitslog;

import com.intellij.openapi.externalSystem.model.ExternalSystemException;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskId;
import com.intellij.openapi.externalSystem.model.task.ExternalSystemTaskNotificationListener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.gradle.service.task.GradleTaskManager;
import org.jetbrains.plugins.gradle.service.task.GradleTaskManagerExtension;
import org.jetbrains.plugins.gradle.settings.GradleExecutionSettings;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PerRunGradle implements GradleTaskManagerExtension {

    private static final String TASKNAME = "org.jetbrains.plugins.gradle";

    @Override
    public boolean executeTasks(@NotNull ExternalSystemTaskId id, @NotNull List<String> taskNames, @NotNull String projectPath, @Nullable GradleExecutionSettings settings, @Nullable String jvmParametersSetup, @NotNull ExternalSystemTaskNotificationListener listener) throws ExternalSystemException {
        if (Config.Idea.getStartup()) {
            final Map<String, Boolean> perRunMap = Config.Idea.getPerRunMap();
            if (perRunMap.get(TASKNAME)) {
                final List<String> jvmArguments = settings.getJvmArguments();
                String agentCoreJarPath = PluginUtil.getAgentCoreJarPath();
                String agentParameter = "-javaagent:\"" + agentCoreJarPath + "\"";
                if (null != agentCoreJarPath) {
                    final long count = jvmArguments.stream().filter(agentParameter::equals).count();
                    if (count <= 0) {
                        settings.withVmOptions(agentParameter);
                    }
                    //todo 暂未解决如何获取JDK版本问题，暂时先添加。
                    final ArrayList<String> addOpens = Config.Idea.getAddOpens();
                    for (String opens : addOpens) {
                        boolean add = false;
                        for (String jvmArgument : jvmArguments) {
                            if (opens.equals(jvmArgument)) {
                                add = true;
                                break;
                            }
                        }
                        if (!add) {
                            settings.withVmOptions(opens);
                        }
                    }
                }
            }
        }
        return GradleTaskManagerExtension.super.executeTasks(id, taskNames, projectPath, settings, jvmParametersSetup, listener);
    }

    @Override
    public boolean cancelTask(@NotNull ExternalSystemTaskId externalSystemTaskId, @NotNull ExternalSystemTaskNotificationListener externalSystemTaskNotificationListener) throws ExternalSystemException {
        return false;
    }
}
