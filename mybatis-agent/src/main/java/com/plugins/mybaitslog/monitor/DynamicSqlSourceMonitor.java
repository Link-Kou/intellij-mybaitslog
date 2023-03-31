package com.plugins.mybaitslog.monitor;

import com.linkkou.mybatis.log.LogInterceptor;
import com.plugins.mybaitslog.IClassFileTransformer;
import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import com.linkkou.mybatis.log.SubInterceptorChain;

/**
 * A <code>DynamicSqlSourceMonitor</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/3/28 16:51</b></p>
 */
public class DynamicSqlSourceMonitor implements IClassFileTransformer {

    public final String injectedClassName = "org.apache.ibatis.plugin.InterceptorChain";

    @Override
    public void transform() {
        try {
            ClassPool classPool = ClassPool.getDefault();
            CtClass ctClass = null;
            classPool.insertClassPath(new ClassClassPath(this.getClass()));
            //获取类
            ctClass = classPool.get(injectedClassName);
            //删除类
            final CtMethod pluginAll = ctClass.getDeclaredMethod("pluginAll");
            pluginAll.insertBefore("{new com.linkkou.mybatis.log.SubInterceptorChain($0.interceptors);}");
            //写入
            //ctClass.writeFile();
            //加载该类的字节码（不能少）
            ctClass.toClass(LogInterceptor.class.getClassLoader(), LogInterceptor.class.getProtectionDomain());
            ctClass.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
