package com.linkkou.mybatis.log;

import com.google.gson.internal.JavaVersion;

import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A <code>RmiLog</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/5/12 19:28</b></p>
 */
public class PrintlnLog {

    private static SocketChannel socketChannel;
    private static boolean connect = false;

    private static SocketChannel client(String id) {
        return null;
        //目前无法支持到1.8
        /*if (null == id) {
            return null;
        }
        if (socketChannel != null && connect) {
            return socketChannel;
        }
        Lock lock = new ReentrantLock();    //注意这个地方
        lock.lock();
        try {
            Path socketPath = Path.of(System.getProperty("java.io.tmpdir")).resolve(id + ".socket");
            socketChannel = SocketChannel.open(StandardProtocolFamily.UNIX);
            UnixDomainSocketAddress of = UnixDomainSocketAddress.of(socketPath);
            connect = socketChannel.connect(of);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return socketChannel;*/
    }

    public static void log(String log, String id) {
        final SocketChannel client = client(id);
        if (!connect) {
            System.out.println(log);
        } else {
            try {
                ByteBuffer buf = ByteBuffer.allocate(log.length());
                buf.clear();
                buf.put(log.getBytes());
                buf.flip();
                while (buf.hasRemaining()) {
                    client.write(buf);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
