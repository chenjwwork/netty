package com.buffer;

import java.nio.ByteBuffer;

/**
 * @Author
 * @Date 2020/4/10 11:12 下午
 * @text 只要学不死就往死里学
 */
public class Test1 {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);//非直接缓冲区
        //ByteBuffer byteBuffer1 = ByteBuffer.allocateDirect(10);//直接缓冲区

        String str = "abcd";
        byteBuffer.put(str.getBytes());


        //开启读模式
        byteBuffer.flip();
        System.out.println(byteBuffer.position());
        byteBuffer.mark();//在你需要的位置标记，此时指针是3
        byte[] bytes = new byte[byteBuffer.limit()];
        byteBuffer.get(bytes, 0, 2);
        //byteBuffer.mark();//在你需要的位置标记，此时指针是3
        System.out.println(byteBuffer.position());
        System.out.println(new String(bytes, 0, 2));
        System.out.println(byteBuffer.position() + "-" + byteBuffer.limit() + "-" + byteBuffer.capacity());
        System.out.println("----------------------");

        byteBuffer.reset();//还原到mark位置

        byteBuffer.get(bytes, 0, 2);
        System.out.println(new String(bytes, 0, 2));
        System.out.println(byteBuffer.position() + "-" + byteBuffer.limit() + "-" + byteBuffer.capacity());


    }
}
