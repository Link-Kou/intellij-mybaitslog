package com.linkkou.mybatislog.sharedmemory;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RMISocketFactory;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CountDownLatch;

/**
 * A <code>index</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/5/12 13:35</b></p>
 */
public class index {

    public static class ServerOperation extends UnicastRemoteObject implements MyBatisLogRmi {

        private static final long serialVersionUID = 1L;

        protected ServerOperation() throws RemoteException {
            super();
        }

        @Override
        public String log(String name) throws RemoteException {
            System.out.println("RMI-" + name);
            return "返回-成功";
        }

    }

    @Test
    public void run() throws InterruptedException, MalformedURLException, NotBoundException, RemoteException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        // 实例化一个WorldClock:
        ServerOperation worldClock = new ServerOperation();


        // 将此服务转换为远程服务接口:
        //ServerOperation skeleton = (ServerOperation) UnicastRemoteObject.exportObject(worldClock, 0);
        // 将RMI服务注册到1099端口:
        Registry registry = LocateRegistry.createRegistry(1099);
        Registry registry2 = LocateRegistry.createRegistry(1098);
        // 注册此服务，服务名为"WorldClock":
        registry.rebind("WorldClock", worldClock);
        registry2.rebind("WorldClock2", worldClock);


        // 连接到服务器localhost，端口1099:
        Registry registry3 = LocateRegistry.getRegistry(1099);
        // 查找名称为"WorldClock"的服务并强制转型为WorldClock接口:
        MyBatisLogRmi worldClock2 = (MyBatisLogRmi) registry3.lookup("WorldClock");
        // 正常调用接口方法:
        final String reponse = worldClock2.log("测试");
        // 打印调用结果:
        System.out.println(reponse);


        countDownLatch.await();
    }


    @Test
    public void run2() throws InterruptedException, IOException, NotBoundException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        System.setProperty("sun.rmi.transport.tcp.responseTimeout", "2000");
        System.setProperty("sun.rmi.transport.tcp.readTimeout", "2000");
        System.setProperty("sun.rmi.transport.connectionTimeout", "2000");
        System.setProperty("sun.rmi.transport.proxy.connectTimeout", "2000");
        System.setProperty("sun.rmi.transport.tcp.handshakeTimeout", "2000");
       /* RMISocketFactory.setSocketFactory(new RMISocketFactory() {
            public Socket createSocket(String host, int port) throws IOException {
                Socket socket = new Socket();
                socket.setSoTimeout(5000);
                socket.setSoLinger(false, 0);
                socket.connect(new InetSocketAddress(host, port), 5000);
                return socket;
            }

            public ServerSocket createServerSocket(int port) throws IOException {
                return new ServerSocket(port);
            }
        });*/
        // rmiregistry within the server JVM with
        // port number 1900
        try {
            LocateRegistry.createRegistry(1900);
            LocateRegistry.createRegistry(1900);
        } catch (Exception e) {

        }
        // 实例化一个WorldClock:
        ServerOperation worldClock = new ServerOperation();
        Naming.rebind("rmi://localhost:1900" + "/geeksforgeeks", worldClock);
        countDownLatch.await();
    }

    @Test
    public void run3() throws InterruptedException, IOException, NotBoundException {
        System.setProperty("sun.rmi.transport.tcp.responseTimeout", "2000");
        System.setProperty("sun.rmi.transport.tcp.readTimeout", "2000");
        System.setProperty("sun.rmi.transport.connectionTimeout", "2000");
        System.setProperty("sun.rmi.transport.proxy.connectTimeout", "2000");
        System.setProperty("sun.rmi.transport.tcp.handshakeTimeout", "2000");

        CountDownLatch countDownLatch = new CountDownLatch(1);
        MyBatisLogRmi access = (MyBatisLogRmi) Naming.lookup("rmi://localhost:1900" + "/geeksforgeeks");
        final String asdsad = access.log("asdsad");
        System.out.println(asdsad);
        countDownLatch.await();
    }

    @Test
    public void run4() throws InterruptedException, IOException, NotBoundException {
        System.setProperty("sun.rmi.transport.tcp.responseTimeout", "2000");
        System.setProperty("sun.rmi.transport.tcp.readTimeout", "2000");
        System.setProperty("sun.rmi.transport.connectionTimeout", "2000");
        System.setProperty("sun.rmi.transport.proxy.connectTimeout", "2000");
        System.setProperty("sun.rmi.transport.tcp.handshakeTimeout", "2000");

        CountDownLatch countDownLatch = new CountDownLatch(1);
        final Registry registry = LocateRegistry.createRegistry(1900);
        com.linkkou.mybatis.log.MyBatisLogRmi access = (com.linkkou.mybatis.log.MyBatisLogRmi) registry.lookup("geeksforgeeks");
        final String asdsad = access.log("asdsad");
        System.out.println(asdsad);
        countDownLatch.await();
    }

    @Test
    public void run5(){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        System.setProperty("sun.rmi.transport.tcp.responseTimeout", "8000");
        System.setProperty("sun.rmi.transport.tcp.readTimeout", "8000");
        System.setProperty("sun.rmi.transport.connectionTimeout", "8000");
        System.setProperty("sun.rmi.transport.proxy.connectTimeout", "8000");
        System.setProperty("sun.rmi.transport.tcp.handshakeTimeout", "8000");
        try {
            final Registry registry = LocateRegistry.createRegistry(1900);
            // 实例化一个WorldClock:
            ServerOperation worldClock = new ServerOperation();
            registry.rebind("geeksforgeeks", worldClock);
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void run6(){
        CountDownLatch countDownLatch = new CountDownLatch(1);
        System.setProperty("sun.rmi.transport.tcp.responseTimeout", "8000");
        System.setProperty("sun.rmi.transport.tcp.readTimeout", "8000");
        System.setProperty("sun.rmi.transport.connectionTimeout", "8000");
        System.setProperty("sun.rmi.transport.proxy.connectTimeout", "8000");
        System.setProperty("sun.rmi.transport.tcp.handshakeTimeout", "8000");
        try {
            MyBatisLogRmi accessw = (MyBatisLogRmi) Naming.lookup("rmi://localhost:1900" + "/geeksforgeeks");

            final Registry registry = LocateRegistry.createRegistry(1900);
            // 实例化一个WorldClock:
            MyBatisLogRmi access = (MyBatisLogRmi) registry.lookup("geeksforgeeks");
            countDownLatch.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
