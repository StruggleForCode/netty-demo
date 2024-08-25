package com.leehao.nio.c2;


import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class TestByteBufferString {
    public static void main(String[] args) {
        // 1. 字符串转为 ByteBuffer
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes());
        System.out.println("字符串转为 ByteBuffer:");
        ByteBufferUtil.debugAll(buffer1);

        // 2. Charset  自动切换为读模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        System.out.println("Charset  自动切换为读模式:");
        ByteBufferUtil.debugAll(buffer2);

        // 3. wrap(包装) 自动切换为读模式
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
        System.out.println("wrap(包装) 自动切换为读模式:");
        ByteBufferUtil.debugAll(buffer3);

        // 4. 转为字符串 2,3都是读模式，可以直接转
        String str1 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println("buffer2 转为字符串:");
        System.out.println(str1);

        // 1是还是写模式，需手动搞到读模式
        buffer1.flip();
        String str2 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println("buffer1 转为字符串:");
        System.out.println(str2);
    }
}
