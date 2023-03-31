package com.plugins.mybaitslog;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.ui.content.ContentFactory;

import java.lang.reflect.Method;

/**
 * A <code>versionControl</code> Class
 *
 * @author lk
 * @version 1.0
 * @date 2022/8/30 10:53
 */
public class VersionControl {

    private static final int _2022DOT1 = 221;

    private static final int BaselineVersion = ApplicationInfo.getInstance().getBuild().getBaselineVersion();

    public static ContentFactory getContentFactory() {
        return  ContentFactory.SERVICE.getInstance();
    }
}
