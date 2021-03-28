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
        while (true){
            //接受客户端请求   阻塞功能
            final Socket socket = serverSocket.accept();
            //创建一个线程与之通信
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    handler(socket);
                }
            });
        }

//        try {
////            while (true) {
//            //接受客户端请求   阻塞功能
//            //final Socket accept = serverSocket.accept();
//            executorService.execute(new Runnable() {
//                @Override
//                public void run() {
//
//                }
//            });
//
////            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            //serverSocket.close();
//        }
    }

    //编写一个handler，和客户端通信
    public static void handler(Socket socket){
        try {
            System.out.println("线程id为"+Thread.currentThread().getId()+"线程名字"+Thread.currentThread().getName());
            InputStream inputStream = socket.getInputStream();
            //将字节流转化成String类型
            byte[] bytes = new byte[1024];
            //循环读取客户端发送数据
            while (true){
                System.out.println("线程id为"+Thread.currentThread().getId()+"线程名字"+Thread.currentThread().getName());
                int len = inputStream.read(bytes);
                if(len!=-1){
                    System.out.println(new String(bytes,0,len));
                }else {
                    break;
                }
            }

            //String result = new String(bytes, 0, len);
            //System.out.println("服务器接受客户端内容：" + result);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("this is fuw".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
