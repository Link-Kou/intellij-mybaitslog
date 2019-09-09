package com.plugins.infotip.parsing.model;

import com.intellij.openapi.project.Project;
import com.plugins.infotip.parsing.ParsingConfigureXML;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ProjectInfo {

    /**
     * 解析XML存储的对象
     */
    public static List<ListTreeInfo> listTreeInfos = new CopyOnWriteArrayList<>();


    /**
     * 读取工程项目中的Directory，解析XML配置文件
     */
    public static void getParsingConfigureXML(Project project) {
        new ParsingConfigureXML().Parsing(project, "Directory.xml");
    }

    /**
     * 获取配置文件
     * //TODO 存在一个配置文件被重复使用的问题！可以暂时忽略
     *
     * @param filepath 文件路径
     * @return
     */
    public static ListTreeInfo getProperties(String filepath) {
        for (ListTreeInfo listTreeInfo : listTreeInfos) {
            if (filepath.equals(listTreeInfo.getProperties())) {
                return listTreeInfo;
            }
        }
        return null;
    }
}
