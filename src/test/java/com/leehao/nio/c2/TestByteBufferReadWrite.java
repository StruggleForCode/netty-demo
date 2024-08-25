package com.leehao.nio.c2;

import java.nio.ByteBuffer;

import static com.leehao.nio.c2.ByteBufferUtil.debugAll;

public class TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61); // 'a'
        debugAll(buffer);
        System.out.println();
        buffer.put(new byte[]{0x62, 0x63, 0x64}); // b  c  d
        debugAll(buffer);
        System.out.println();
//        System.out.println(buffer.get());
        buffer.flip();
        System.out.println(buffer.get());
        debugAll(buffer);
        System.out.println();
        buffer.compact();
        debugAll(buffer);
        System.out.println();
        buffer.put(new byte[]{0x65, 0x6f});
        debugAll(buffer);
        System.out.println();
    }
}
