package com.leehao.nio.c4.block;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * ClassName: Client
 * Package: com.leehao.nio.c4.block
 * Description:
 *
 * @Author LeeHao
 * @Create 2024/8/25 19:47
 * @Version 1.0
 */
public class Client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("127.0.0.1", 8080));
         System.out.println("waiting...");
    }
}
