package com.linkkou.mybatis.log;

import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.InterceptorChain;

import java.util.Collections;
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
        int index = 0;
        int size = interceptors.size();
        for (Interceptor interceptor : interceptors) {
            if (LogInterceptor.class.getName().equals(interceptor.getClass().getName())) {
                addLogInterceptor = false;
            }
            index += 1;
        }
        //保证插件作为最后执行
        if (addLogInterceptor) {
            interceptors.add(size, new LogInterceptor(val));
        } else {
            if (index < size) {
                // 交换位置
                Collections.swap(interceptors, index, size - 1);
            }
        }
    }
}
