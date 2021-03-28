package com.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * @Author
 * @Date 2020/4/11 4:47 下午
 * @text 只要学不死就往死里学
 */
//Nio服务器端
public class NioServer {
    public static void main(String[] args) throws IOException {
        System.out.println("服务器已经启动。。。。。");
        //1、创建服务通道
        ServerSocketChannel sc = ServerSocketChannel.open();
        //2、设置为异步读取
        sc.configureBlocking(false);//jdk1。7以后才有
        //3、绑定连接
        sc.bind(new InetSocketAddress(1111));
        //4、获取选择器
        Selector se = Selector.open();
        //5、将通道注册到选择器中，并且监听已经接受到的事件
        sc.register(se, SelectionKey.OP_ACCEPT);
        //6、轮巡获取"已经准备就绪的"事件
        while (se.select() > 0) {
            //7、获取当前选择器 有注册已经监听的事件
            Iterator<SelectionKey> it = se.selectedKeys().iterator();
            while (it.hasNext()) {
                //8、获取准备就绪事件
                SelectionKey sk = it.next();
                //9、判断事件准备就绪
                if (sk.isAcceptable()) {
                    //10、若接受就绪，获取客户端连接
                    SocketChannel so = sc.accept();
                    //11、设置为阻塞事件
                    so.configureBlocking(false);//异步非阻塞IO
                    //12、将该通道注册到服务器上
                    so.register(se, SelectionKey.OP_READ);
                } else if (sk.isReadable()) {
                    //13、获取当前选择器"就绪状态"的通道
                    SocketChannel socketchannel = (SocketChannel) sk.channel();
                    //14、读取数据
                    int len = 0;
                    ByteBuffer bytes = ByteBuffer.allocate(1024);
                    while ((len = socketchannel.read(bytes)) > 0) {
                        bytes.flip();
                        System.out.println(new String(bytes.array(), 0, len));
                        bytes.clear();
                    }

                }
                it.remove();

            }
        }
    }
}
