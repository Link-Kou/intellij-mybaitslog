package com.plugins.mybaitslog;


import com.plugins.mybaitslog.monitor.DynamicSqlSourceMonitor;
import org.javatuples.Pair;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

public class preAgent {


    //JVM 首先尝试在代理类上调用以下方法
    public static void premain(String agentArgs, Instrumentation inst) {
        final Pair<String, String> with = Pair.with("Start: ", "MyBatis Log EasyPlus");
        System.out.println(with.getValue0() + with.getValue1());
        final transformer transformer = new transformer(inst);
        transformer.transform();
    }

    //如果代理类没有成功，那么 JVM 将尝试调用该方法
    public static void premain(String agentArgs) {
    }

}
