package com.plugins.mybaitslog.monitor;

import com.linkkou.mybatis.log.LogInterceptor;
import com.plugins.mybaitslog.IClassFileTransformer;
import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * A <code>RawSqlSourceMonitor</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/3/28 16:52</b></p>
 */
public class RawSqlSourceMonitor implements IClassFileTransformer {
    public final String injectedClassName = "org.apache.ibatis.scripting.defaults.RawSqlSource";

    @Override
    public void transform() {
        ClassPool classPool = ClassPool.getDefault();
        CtClass ctClass = null;
        try {
            classPool.insertClassPath(new ClassClassPath(this.getClass()));
            //获取类
            ctClass = classPool.get(injectedClassName);
            //添加新的字段
            CtField ctField = new CtField(classPool.getCtClass("java.lang.String"), "_rootSqlNode_", ctClass);
            ctField.setModifiers(Modifier.PUBLIC);
            ctField.setModifiers(Modifier.FINAL);
            ctClass.addField(ctField);
            //获取构造
            final CtConstructor declaredConstructor = ctClass.getDeclaredConstructor(new CtClass[]{classPool.getCtClass("org.apache.ibatis.session.Configuration"), classPool.getCtClass("java.lang.String"), classPool.getCtClass("java.lang.Class")});
            declaredConstructor.insertAfter("{$0._rootSqlNode_ = $2;}");
            //写入
            ctClass.writeFile();
            //加载该类的字节码（不能少）
            ctClass.toClass(LogInterceptor.class.getClassLoader(), LogInterceptor.class.getProtectionDomain());
            ctClass.detach();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
