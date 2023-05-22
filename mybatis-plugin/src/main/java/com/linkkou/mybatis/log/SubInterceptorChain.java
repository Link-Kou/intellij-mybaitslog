package com.linkkou.mybatis.log;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.InterceptorChain;

import java.util.List;

/**
 * A <code>SubInterceptorChain</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/3/29 12:40</b></p>
 */
public class SubInterceptorChain extends InterceptorChain {

    public synchronized static void Check(final List<Interceptor> interceptors, String val) {
        boolean addLogInterceptor = true;
        for (Interceptor interceptor : interceptors) {
            if (LogInterceptor.class.getName().equals(interceptor.getClass().getName())) {
                addLogInterceptor = false;
            }
        }
        if (addLogInterceptor) {
            interceptors.add(new LogInterceptor(val));
        }
    }
}
