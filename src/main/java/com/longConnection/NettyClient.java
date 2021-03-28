package com.longConnection;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Author
 * @Date 2020/4/11 11:05 下午
 * @text 只要学不死就往死里学
 */

//客户端
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("客户端已经启动。。。。");
        //创建负责接受客户端连接
        NioEventLoopGroup pGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(pGroup).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                //拆包，以shuai拆分
//                ByteBuf buf = Unpooled.copiedBuffer("shuai".getBytes());
//                socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,buf));
                //达到10个我就发
                socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(10));
                socketChannel.pipeline().addLast(new StringDecoder());
                socketChannel.pipeline().addLast(new ClientHandler());
            }
        });
        ChannelFuture dc = bootstrap.connect("127.0.0.1", 8888).sync();
        dc.channel().writeAndFlush(Unpooled.wrappedBuffer("chenjiaweishuai".getBytes()));
        dc.channel().writeAndFlush(Unpooled.wrappedBuffer("chenjiaweishuai".getBytes()));
        dc.channel().writeAndFlush(Unpooled.wrappedBuffer("chenjiaweishuai".getBytes()));
        dc.channel().writeAndFlush(Unpooled.wrappedBuffer("chenjiaweishuai".getBytes()));
        dc.channel().writeAndFlush(Unpooled.wrappedBuffer("chenjiaweishuai".getBytes()));
        dc.channel().writeAndFlush(Unpooled.wrappedBuffer("chenjiaweishuai".getBytes()));
        //等待客户端端口号关闭
        dc.channel().closeFuture().sync();
        pGroup.shutdownGracefully();

    }

}

class ClientHandler extends ChannelHandlerAdapter {
    //当通道被调用，执行该方法

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //接受数据
        String value = (String) msg;
        System.out.println("clent msg:" + value);
    }
}
