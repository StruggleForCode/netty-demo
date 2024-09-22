package com.leehao.nio.c4.selectorReadBoardModel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * ClassName: ReadBoardClient
 * Package: com.leehao.nio.c4.selectorReadBoardModel
 * Description:
 *
 * @Author LeeHao
 * @Create 2024/9/22 15:16
 * @Version 1.0
 */
public class ReadBoardClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        SocketAddress address = sc.getLocalAddress();
// sc.write(Charset.defaultCharset().encode("hello\nworld\n"));
        sc.write(Charset.defaultCharset().encode("0123\n456789abcdef"));
        sc.write(Charset.defaultCharset().encode("0123456\n789abcdef\n3333\n"));
        sc.write(Charset.defaultCharset().encode("你好\n"));
        System.in.read();
    }
}
