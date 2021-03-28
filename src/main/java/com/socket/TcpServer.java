package com.socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author
 * @Date 2020/4/9 10:29 下午
 * @text 只要学不死就往死里学
 */
public class TcpServer {
    //线程池
    private Executors executors;

    public static void main(String[] args) throws IOException {
        //只支持一个http请求
//        System.out.println("tcp协议服务器启动");
//        //创建服务器连接
//        ServerSocket serverSocket = new ServerSocket(8008);
//        //接受客户端请求   阻塞功能
//        Socket accept = serverSocket.accept();
//        InputStream inputStream = accept.getInputStream();
//        //将字节流转化成String类型
//        byte[] bytes = new byte[1024];
//        int len =inputStream.read(bytes);
//        String result = new String(bytes, 0, len);
//        String str = bytes.toString();
//        System.out.println("服务器接受客户端内容："+result);
//        System.out.println(str);
//        serverSocket.close();


        //支持多个请求
        System.out.println("tcp协议服务器启动");
        //线程池
        ExecutorService executorService = Executors.newCachedThreadPool();
        //创建服务器连接
        ServerSocket serverSocket = new ServerSocket(8008);
        try {
//            while (true) {
            //接受客户端请求   阻塞功能
            final Socket accept = serverSocket.accept();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        InputStream inputStream = accept.getInputStream();
                        //将字节流转化成String类型
                        byte[] bytes = new byte[1024];
                        int len = inputStream.read(bytes);
                        String result = new String(bytes, 0, len);
                        System.out.println("服务器接受客户端内容：" + result);
                        OutputStream outputStream = accept.getOutputStream();
                        outputStream.write("this is fuw".getBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {

                    }
                }
            });

//            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //serverSocket.close();
        }
    }

}
