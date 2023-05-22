package com.plugins.mybaitslog.rmi;

import com.intellij.openapi.project.Project;
import com.linkkou.mybatis.log.MyBatisLogRmi;
import com.plugins.mybaitslog.console.PrintlnUtil;
import org.jetbrains.annotations.NotNull;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * A <code>RmiServer</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/5/12 19:20</b></p>
 */
public class RmiServer {

    public static class ServerOperation extends UnicastRemoteObject implements MyBatisLogRmi {

        private Project project;

        private static final long serialVersionUID = 1L;

        protected ServerOperation(@NotNull Project project) throws RemoteException {
            super();
            this.project = project;
        }

        @Override
        public String log(String name) throws RemoteException {
            PrintlnUtil.prints(project, name);
            return "success";
        }

    }

    public static Map<Project, String> RMISERVERID = new ConcurrentHashMap<Project, String>();

    public static String getId(@NotNull Project project) {
        return RMISERVERID.get(project);
    }

    public static void boot(@NotNull Project project, Boolean run) {
        if (run) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    CountDownLatch countDownLatch = new CountDownLatch(1);
                    try {
                        String id = UUID.randomUUID().toString().replace("-", "");
                        RMISERVERID.put(project, id);
                        new RmiServer().run(project, id);
                        countDownLatch.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    public void run(@NotNull Project project, String id) {
        System.setProperty("sun.rmi.transport.tcp.responseTimeout", "8000");
        System.setProperty("sun.rmi.transport.tcp.readTimeout", "8000");
        System.setProperty("sun.rmi.transport.connectionTimeout", "8000");
        System.setProperty("sun.rmi.transport.proxy.connectTimeout", "8000");
        System.setProperty("sun.rmi.transport.tcp.handshakeTimeout", "8000");
        try {
            final Registry registry = LocateRegistry.createRegistry(1900);
            // 实例化一个WorldClock:
            ServerOperation worldClock = new ServerOperation(project);
            registry.rebind(id, worldClock);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
