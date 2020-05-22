package com.plugins.mybaitslog.filter;

import com.intellij.execution.filters.Filter;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import com.plugins.mybaitslog.util.ConfigUtil;
import com.plugins.mybaitslog.util.PrintUtil;
import com.plugins.mybaitslog.util.RestoreSqlUtil;
import com.plugins.mybaitslog.util.StringConst;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.Nullable;

/**
 * 语句过滤器
 *
 * @author ob
 */
public class MyBatisLogFilter implements Filter {

    private final Project project;
    private static String preparingLine = "";
    private static String parametersLine = "";
    private static boolean isEnd = false;

    public MyBatisLogFilter(Project project) {
        this.project = project;
    }

    @Nullable
    @Override
    public Result applyFilter(final String currentLine, int endPoint) {
        if (this.project == null) {
            return null;
        }
        if (currentLine != null) {
            prints(currentLine, endPoint);
        }
        return null;
    }

    private Result prints(final String currentLine, int endPoint) {
        //过滤不显示的语句
        String[] filters = PropertiesComponent.getInstance(project).getValues(StringConst.FILTER_KEY);
        if (filters != null && filters.length > 0 && StringUtils.isNotBlank(currentLine)) {
            for (String filter : filters) {
                if (StringUtils.isNotBlank(filter) && currentLine.toLowerCase().contains(filter.trim().toLowerCase())) {
                    return null;
                }
            }
        }
        if (currentLine.contains(ConfigUtil.getPreparing(project))) {
            preparingLine = currentLine;
            return null;
        }
        if (StringUtils.isEmpty(preparingLine)) {
            return null;
        }
        parametersLine = currentLine.contains(ConfigUtil.getParameters(project)) ? currentLine : parametersLine + currentLine;
        if (!parametersLine.endsWith("Parameters: \n") && !parametersLine.endsWith("null\n") && !parametersLine.endsWith(")\n")) {
            return null;
        } else {
            isEnd = true;
        }
        if (StringUtils.isNotEmpty(preparingLine) && StringUtils.isNotEmpty(parametersLine) && isEnd) {
            int indexNum = ConfigUtil.getIndexNum(project);
            //序号前缀字符串
            String preStr = "--  " + indexNum + "  " + parametersLine.split(ConfigUtil.getParameters(project))[0].trim();
            ConfigUtil.setIndexNum(project, ++indexNum);
            String restoreSql = RestoreSqlUtil.restoreSql(project, preparingLine, parametersLine);
            PrintUtil.println(project, preStr, ConsoleViewContentType.USER_INPUT);
            if (ConfigUtil.getSqlFormat(project)) {
                restoreSql = PrintUtil.format(restoreSql);
            }
            PrintUtil.println(project, restoreSql);
            PrintUtil.println(project, StringConst.SPLIT_LINE, ConsoleViewContentType.USER_INPUT);
            preparingLine = "";
            parametersLine = "";
            isEnd = false;
        }
        return null;
    }
}
