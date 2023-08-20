package com.plugins.mybaitslog.monitor;

import com.linkkou.mybatis.log.LogInterceptor;
import com.plugins.mybaitslog.IClassFileTransformer;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

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
    public void transform(String val) {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = null;
        classPool.insertClassPath(new ClassClassPath(this.getClass()));
        boolean isMybitas = true;
        //获取类
        try {
            ctClass = classPool.get(injectedClassName);
            if (null == ctClass) {
                return;
            }
        } catch (Exception e) {
            System.out.println("==>  SQLStructureError: " + e.toString());
            isMybitas = false;
        }
        if (isMybitas) {
            try {
                //删除类
                final CtMethod pluginAll = ctClass.getDeclaredMethod("pluginAll");
                String strings = "\"" + val + "\"";
                pluginAll.insertBefore("{com.linkkou.mybatis.log.SubInterceptorChain.Check($0.interceptors," + strings + ");}");
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
}
