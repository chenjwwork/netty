package com.netty;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelFuture;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;
import org.jboss.netty.handler.codec.string.StringDecoder;
import org.jboss.netty.handler.codec.string.StringEncoder;

//netty客户端
public class NettyClient {
    public static void main(String[] args) {
        //1、创建服务对象
        ClientBootstrap clientrBootstrap = new ClientBootstrap();
        //2、创建两个线程池  作用：监听端口号  nio监听
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService wook = Executors.newCachedThreadPool();
        //3、将线程池放入工程中
        clientrBootstrap.setFactory(new NioClientSocketChannelFactory(boss, wook));
        //4、设置管道工程
        clientrBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            //设置管道
            public ChannelPipeline getPipeline() throws Exception {
                ChannelPipeline pipeline = org.jboss.netty.channel.Channels.pipeline();
                //传输数据的时候直接为String类型
                pipeline.addLast("decoder", new StringDecoder());
                pipeline.addLast("encoder", new StringEncoder());
                //设置管道监听事件
                pipeline.addLast("clientHanlder", new ClientHanlder());
                return pipeline;
            }
        });
        //绑定端口号
        ChannelFuture connet = clientrBootstrap.connect(new InetSocketAddress("127.0.0.1", 9999));
        Channel channel = connet.getChannel();
        System.out.println("client start....");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("请输入内容:");
            channel.write(scanner.next());
        }
    }
}

class ClientHanlder extends SimpleChannelHandler {
    //通道被关闭的时候会触发
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        // TODO Auto-generated method stub
        super.channelClosed(ctx, e);
        System.out.println("channelClosed");
    }

    //必须要建立连接，关闭通道的时候会触发
    @Override
    public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
        // TODO Auto-generated method stub
        super.channelDisconnected(ctx, e);
        System.out.println("channelDisconnected");
    }

    //接受端出现异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
        // TODO Auto-generated method stub
        super.exceptionCaught(ctx, e);
        System.out.println("exceptionCaught");
    }

    //接受服务端数据
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        super.messageReceived(ctx, e);
        System.out.println("messageReceived");
        System.out.println("服务器向客户端回复的内容:" + e.getMessage());
        ctx.getChannel().write("我不好");
    }
}