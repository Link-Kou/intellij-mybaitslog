package com.plugins.infotip.parsing.model;

import java.io.File;

/**
 * 目录对象结构
 */
public class ListTreeInfo {

    /**
     * 当前目录地址
     */
    private String path;

    /**
     * 配置文件
     */
    private String properties;

    /**
     * 目录树显示
     */
    private String title;

    /**
     * 提示内容显示
     */
    private String tooltip;

    /**
     * code 名称约束
     */
    private String code;

    /**
     * code 值约束
     */
    private String codeval;

    /**
     * msg约束
     */
    private String msg;

    public String getPath() {
        if (path != null) {
            if (!File.separator.equals("/")) {
                return path.replaceAll("/", "\\\\");
            }
        }
        return path;
    }

    public ListTreeInfo setPath(String path) {
        this.path = path;
        return this;
    }

    public String getProperties() {
        return properties;
    }

    public ListTreeInfo setProperties(String properties) {
        this.properties = properties;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public ListTreeInfo setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTooltip() {
        return tooltip;
    }

    public ListTreeInfo setTooltip(String tooltip) {
        this.tooltip = tooltip;
        return this;
    }

    public String getCode() {
        return code;
    }

    public ListTreeInfo setCode(String code) {
        this.code = code;
        return this;
    }

    public String getCodeval() {
        return codeval;
    }

    public ListTreeInfo setCodeval(String codeval) {
        this.codeval = codeval;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ListTreeInfo setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
