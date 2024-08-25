package com.leehao.nio.c4.block;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.leehao.nio.c2.ByteBufferUtil.debugRead;

/**
 * ClassName: Server
 * Package: com.leehao.nio.c4.block
 * Description:
 *
 * @Author LeeHao
 * @Create 2024/8/25 19:01
 * @Version 1.0
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        // 使用 nio 来理解阻塞模式, 单线程来处理

        // 0. ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);

        // 1. 创建服务器
        ServerSocketChannel scc = ServerSocketChannel.open();

        // 2. 绑定监听端口
        scc.bind(new InetSocketAddress(8080));

        // 3. 连接集合
        List<SocketChannel> channels = new ArrayList<>();

        //  因为将来有很多客户端连接我们服务器，所有我们把建立客户端的连接放到while循环里，所以把accept放到while循环里，不断的接收连接
        while (true) {
            // 3. accept 建立与客户端的连接，SocketChannel 用来与客户端之间通信
            // accept方法回返回一个结果，叫SocketChannel, 将来建立连接，总要与客户端进行通信，进行数据的读写，
            // Channel是数据读写的通道，通过SocketChannel与客户端进行读写操作
            log.debug("connecting...");
            SocketChannel sc = scc.accept(); // 阻塞方法，线程停止运行了
            // 每次建立的连接都放到这个集合里面，这样建立的连接都管理起来了
            log.debug("connected... {}", sc);
            channels.add(sc);
            // 接下来客户端就要进行数据通信了
            // 为了处理所有客户端发送的数据，则需要遍历集合，一个一个处理
            for (SocketChannel channel : channels) {
                // 5. 接收客户端发送的数据
                // read 读取数据，读到缓冲区 write从缓冲区写出数据
                // 从channel读取的数据就存到buffer里面了
                log.debug("before read... {}", channel);
                channel.read(buffer); // 阻塞方法，线程停止运行
                // buffer切换到读模式
                buffer.flip();
                debugRead(buffer);
                // buffer切换为写模式  下次在处理其他channel，或者是第二次第三次的读取时，
                // 因为clean了，所以可以从头从channel读，buffer写了
                buffer.clear();
                log.debug("after read...{}", channel);
            }
        }
    }
}
