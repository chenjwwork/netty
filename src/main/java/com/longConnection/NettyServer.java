package com.longConnection;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author
 * @Date 2020/4/11 10:35 下午
 * @text 只要学不死就往死里学
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("服务器端启动。。。。");
        //1、创建2个线程池，一个负责接受客户端，一个进行传输
        NioEventLoopGroup pGroup = new NioEventLoopGroup();
        NioEventLoopGroup cGroup = new NioEventLoopGroup();
        //2、创建辅助类
        ServerBootstrap b = new ServerBootstrap();
        b.group(pGroup, cGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 1024)
                //3、设置缓冲区与发送区大小
                .option(ChannelOption.SO_SNDBUF, 32 * 1024).option(ChannelOption.SO_RCVBUF, 32 * 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //拆包，以shuai拆分
//                ByteBuf buf = Unpooled.copiedBuffer("shuai".getBytes());
//                socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
//                //达到10个我就发
//                socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(10));

                        //设置返回结果是String
                        socketChannel.pipeline().addLast(new StringDecoder());
                        socketChannel.pipeline().addLast(new ServerHandler());
                    }
                });
        //启动
        ChannelFuture cf = b.bind(8888).sync();
        //关闭
        cf.channel().closeFuture().sync();
        pGroup.shutdownGracefully();
        cGroup.shutdownGracefully();
    }
}

class ServerHandler extends ChannelHandlerAdapter {
    //当通道被调用是，执行方法（拿到数据）
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //
        String value = (String) msg;
        System.out.println("服务器收到客户端msg：" + value);
        //回复客户端
        ctx.writeAndFlush("好的");
    }
}
