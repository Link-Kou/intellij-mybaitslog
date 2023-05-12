package com.linkkou.mybatis.log;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.CountDownLatch;

/**
 * A <code>RmiLog</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/5/12 19:28</b></p>
 */
public class RmiLog {

    public static void log(String log, String id) {
        if (null != id) {
            System.out.println(log);
        } else {
            System.setProperty("sun.rmi.transport.tcp.responseTimeout", "2000");
            System.setProperty("sun.rmi.transport.tcp.readTimeout", "2000");
            System.setProperty("sun.rmi.transport.connectionTimeout", "2000");
            System.setProperty("sun.rmi.transport.proxy.connectTimeout", "2000");
            System.setProperty("sun.rmi.transport.tcp.handshakeTimeout", "2000");
            try {
                MyBatisLogRmi access = (MyBatisLogRmi) Naming.lookup("rmi://localhost:1900/" + id);
                access.log(log);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
