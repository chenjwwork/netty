package com.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @Author
 * @Date 2020/4/9 9:48 下午
 * @text 只要学不死就往死里学
 */
//udp服务器端
public class UdpServer {
    public static void main(String[] args) throws IOException {
        //1、IP地址+端口号
        System.out.println("upd服务器已经启动");
        //创建服务器端口号,默认使用本机IP地址
        DatagramSocket ds = new DatagramSocket(8080);
        //服务端接受客户端1024个字节
        byte[] bytes = new byte[1024];
        //定义数据包
        DatagramPacket dp = new DatagramPacket(bytes, 0, bytes.length);
        //接受客户端请求，将数据封装给数据包，如果客户端不往服务端发送请求，就一直阻塞
        ds.receive(dp);
        System.out.println("本季IP地址" + dp.getAddress() + "端口号" + dp.getPort());
        String result = new String(dp.getData(), 0, dp.getLength());
        System.out.println(result);
        ds.close();
    }
}