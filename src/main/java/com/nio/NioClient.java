package com.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Date;

/**
 * @Author
 * @Date 2020/4/11 4:37 下午
 * @text 只要学不死就往死里学
 */
//nio客户端
public class NioClient {

    public static void main(String[] args) throws IOException {
        System.out.println("客户端已经启动。。。。。。。。");
        //1、创建socket通道
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("localhost", 1111));
        //2、切换为异步非阻塞
        sc.configureBlocking(false);//1.7之后
        //3、指定缓冲区大小
        ByteBuffer buff = ByteBuffer.allocate(1024);
        buff.put(new Date().toString().getBytes());
        //4、切换到读取模式
        buff.flip();
        sc.write(buff);
        //5、关闭通道
        sc.close();
    }
}
