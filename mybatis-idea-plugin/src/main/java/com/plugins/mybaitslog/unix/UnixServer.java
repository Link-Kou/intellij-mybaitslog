package com.plugins.mybaitslog.unix;

import com.intellij.openapi.project.Project;
import com.plugins.mybaitslog.Config;
import com.plugins.mybaitslog.console.PrintlnUtil;
import org.jetbrains.annotations.NotNull;

import java.net.StandardProtocolFamily;
import java.net.UnixDomainSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A <code>RmiServer</code> Class
 *
 * @author lk
 * @version 1.0
 * <p><b>date: 2023/5/12 19:20</b></p>
 */
public class UnixServer {

    public static Map<Project, String> RMISERVERID = new ConcurrentHashMap<Project, String>();

    public static String getId(@NotNull Project project) {
        return RMISERVERID.get(project);
    }

    public static void boot(@NotNull Project project) {
        String id = UUID.randomUUID().toString().replace("-", "");
        RMISERVERID.put(project, id);
        //无法支持到1.8后续再想办法
        /*
        final boolean runRmi = Config.Idea.getRunRmi();
        if (runRmi) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String id = UUID.randomUUID().toString().replace("-", "");
                        RMISERVERID.put(project, id);
                        Path socketPath = Path
                                .of(System.getProperty("java.io.tmpdir"))
                                .resolve(id + ".socket");
                        Files.deleteIfExists(socketPath);
                        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(StandardProtocolFamily.UNIX);
                        UnixDomainSocketAddress of = UnixDomainSocketAddress.of(socketPath);
                        serverSocketChannel.bind(of);
                        while (true) {
                            SocketChannel socketChannel = serverSocketChannel.accept();
                            ByteBuffer buf = ByteBuffer.allocate(1024);
                            int bytesRead = socketChannel.read(buf);
                            while (bytesRead > 0) {
                                buf.flip();
                                StringBuffer stringBuffer = new StringBuffer();
                                while (buf.hasRemaining()) {
                                    stringBuffer.append((char) buf.get());
                                }
                                buf.clear();
                                PrintlnUtil.prints(project, stringBuffer.toString());
                                bytesRead = socketChannel.read(buf);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }*/
    }
}
