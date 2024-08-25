package com.leehao.nio.c4.selectorModel;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
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

        // 1. 创建selector， 管理多个channel
        // 他怎样管理哪些channel呢，channel有多个 ServerSocketChannel、SocketChannel，需要建立channel与Selector的联系
        Selector selector = Selector.open();

        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        // 2.建立channel与Selector的联系 （把Channel 注册到 selector上面）
        // 注册完毕之后，将来发生的事件怎么知道，register有一个返回值，叫做 SelectionKey
        // SelectionKey 就是将来事件发生后，通过它可以知道事件是那个channel的事件
        // SelectionKey 相当于管理员，管的scc,  只关注accept 事件, 0 不关注任何事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // ssckey 只关注 accept 事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key:{}", sscKey);

        ssc.bind(new InetSocketAddress(8080));

        while (true) {
            // 要怎么知道有没有发生事件呢
            // 3.select 方法。解决前面非阻塞问题。
            // 默认工作方式：没有事件发生，就会让线程阻塞，将来有事件，线程才会恢复运行
            selector.select();

            // 4.处理事件 selectedKeys 内部包含了所有发生的事件，set集合，集合的遍历时候删除元素，要用迭代器遍历
            // 首先拿到事件集合， 包括可读，可写，可连接的集合
            // 这里直接拿到selectedKeys的迭代器
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                // key 包含 sscKey
                SelectionKey key = iter.next();
                log.debug("key: {}", key);
                // 可以通过key 拿到Channel建立连接
                // 相当于上面的ssc
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                // 建立连接
                SocketChannel sc = channel.accept();
                log.debug("{}", sc);
            }
        }
    }
}
