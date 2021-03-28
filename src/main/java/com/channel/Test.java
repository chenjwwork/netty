package com.channel;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @Author
 * @Date 2020/4/11 11:20 上午
 * @text 只要学不死就往死里学
 */
public class Test {
    //非直接缓冲区
    public void test1() throws IOException {
        //jdk1.7之后才出来的
        long startTime = System.currentTimeMillis();
        System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
        //读入流
        FileInputStream fileInputStream = new FileInputStream("touxiang.png");
        //写入流
        FileOutputStream fileOutputStream = new FileOutputStream("1.png");
        //创建通道
        //读入通道
        FileChannel inChannel = fileInputStream.getChannel();
        //写入流通道
        FileChannel outChannel = fileOutputStream.getChannel();
        //分配指定大小缓冲区
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        while (inChannel.read(byteBuffer) != -1) {
            //开启读取模式
            byteBuffer.flip();
            //将数据写入到通道中
            outChannel.write(byteBuffer);
            byteBuffer.clear();
        }
        //关闭公道，关闭连接
        inChannel.close();
        outChannel.close();
        fileInputStream.close();
        fileOutputStream.close();
        long endTime = System.currentTimeMillis();
        System.out.println("操作非直接缓区完毕耗时时间：" + (endTime - startTime));
    }

    //直接缓冲区
    public void test2() throws IOException {
        long startTime = System.currentTimeMillis();
        //创建管道
        FileChannel inChannel = FileChannel.open(Paths.get("1.png"), StandardOpenOption.READ);//第二个参数是权限写
        //第二个权限写，第三个权限是如果没有这个文件，就查创建一个文件
        FileChannel outChannel = FileChannel.open(Paths.get("2.png"), StandardOpenOption.READ, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
        //定义映射文件
        MappedByteBuffer inMappedByte = inChannel.map(MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappenByte = outChannel.map(MapMode.READ_WRITE, 0, inChannel.size());
        //直接对缓冲区操作
        byte[] bytes = new byte[inMappedByte.limit()];
        inMappedByte.get(bytes);
        outMappenByte.put(bytes);
        inChannel.close();
        outChannel.close();
        long endTime = System.currentTimeMillis();
        System.out.println("操作直接缓区完毕耗时时间：" + (endTime - startTime));

    }

}

