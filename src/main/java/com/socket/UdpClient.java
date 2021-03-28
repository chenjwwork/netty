package com.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @Author
 * @Date 2020/4/9 9:16 下午
 * @text 只要学不死就往死里学
 */

//先写服务端在写客户端
public class UdpClient {
    public static void main(String[] args) throws IOException {
        System.out.println("udp客户端启动连接。。。。");
        //不传入端口号：作为客户端 创建一个socket客户端
        DatagramSocket ds = new DatagramSocket();
        String st = "陳佳伟";
        byte[] strByte = st.getBytes();
        DatagramPacket dp = new DatagramPacket(strByte, strByte.length, InetAddress.getByName("127.0.0.1"), 8080);
        ds.send(dp);
        ds.close();

    }

}
