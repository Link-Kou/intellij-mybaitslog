package com.plugins.mybaitslog.filter;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.plugins.mybaitslog.util.*;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

/**
 * 语句过滤器
 *
 * @author lk
 * @version 1.0
 * @date 2020/4/10 22:00
 */
public class MyBatisLogFilter implements Filter {

    private final Project project;
    private static String preparingLine = "";
    private static String parametersLine = "";

    public MyBatisLogFilter(Project project) {
        this.project = project;
    }

    @Nullable
    @Override
    public Result applyFilter(final String currentLine, int endPoint) {
        if (this.project == null) {
            return null;
        }
        final boolean startup = ConfigUtil.getStartup(project);
        if (startup && currentLine != null) {
            prints(currentLine, endPoint);
        }
        return null;
    }

    private Result prints(final String currentLine, int endPoint) {
        final String PREPARING = ConfigUtil.getPreparing(project);
        final String PARAMETERS = ConfigUtil.getParameters(project);
        if (currentLine.contains(PREPARING)) {
            preparingLine = currentLine;
        }
        if (!StringUtils.isEmpty(preparingLine) && currentLine.contains(PARAMETERS)) {
            parametersLine = currentLine;
        }
        if (StringUtils.isNotEmpty(preparingLine) && StringUtils.isNotEmpty(parametersLine)) {
            //序号前缀字符串
            String[] restoreSql = SqlProUtil.restoreSql(project, preparingLine, parametersLine);
            PrintlnUtil.println(project, "--" + restoreSql[0], ConsoleViewContentType.USER_INPUT);
            PrintlnUtil.printlnSqlType(project, restoreSql[1]);
            preparingLine = "";
            parametersLine = "";
        }
        return null;
    }
}
