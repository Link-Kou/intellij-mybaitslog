package com.plugins.mybaitslog;

import com.plugins.mybaitslog.monitor.DynamicSqlSourceMonitor;
import com.plugins.mybaitslog.monitor.RawSqlSourceMonitor;

import java.lang.instrument.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A <code>transformer</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/3/28 17:36</b></p>
 */
public class transformer implements IClassFileTransformer {

    Instrumentation inst;

    byte[] newclassfileBuffer;
    Class<?> newclassBeingRedefined;

    List<IClassFileTransformer> classFileTransformers = new ArrayList<IClassFileTransformer>() {{
        add(new DynamicSqlSourceMonitor());
        add(new RawSqlSourceMonitor());
    }};

    public transformer(Instrumentation inst) {
        this.inst = inst;
    }

    @Override
    public void transform() {
        for (IClassFileTransformer classFileTransformer : classFileTransformers) {
            classFileTransformer.transform();
        }
    }
}
