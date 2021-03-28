package com.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @Author
 * @Date 2020/4/9 10:29 下午
 * @text 只要学不死就往死里学
 */
public class TcpClient {
    public static void main(String[] args) throws IOException {
        System.out.println("socket tcp客户端启动。。。。");
        //创建socket
        Socket socket = new Socket("localhost", 8008);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("我在测试tcp客户端".getBytes());
        socket.close();


    }
}
