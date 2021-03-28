package com.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author
 * @Date 2020/4/11 1:06 下午
 * @text 只要学不死就往死里学
 */
public class FenSan {
    //分散读取，聚集写入
    public static void main(String[] args) throws IOException {
        //随机访问
        RandomAccessFile raf = new RandomAccessFile("test.txt", "rw");
        //获取通道
        FileChannel channel = raf.getChannel();
        //分配指定大小缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);
        //分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel.read(bufs);
        for (ByteBuffer byteBuffer : bufs) {
            // 切换成读模式
            byteBuffer.flip();
        }
        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));
        System.out.println("---------聚集读取-------");
        //随机访问
        RandomAccessFile raf2 = new RandomAccessFile("test2.txt", "rw");
        //获取通道
        FileChannel channel2 = raf2.getChannel();
        channel2.write(bufs);
        raf.close();
        raf2.close();


    }

}
