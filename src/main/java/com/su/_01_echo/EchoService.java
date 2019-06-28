package com.su._01_echo;

import com.su._01_echo.handler.EchoServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * this class is for echo server
 */
public class EchoService {
    private static Integer port = 8080;
    public static void main(String[] args) throws InterruptedException {
        if(args.length > 0){
            port = Integer.parseInt(args[0]);
        }
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sbs = new ServerBootstrap();
            sbs.group(bossGroup,workGroup).channel(NioServerSocketChannel.class)
                                            .childHandler(new ChannelInitializer<SocketChannel>() {
                                                @Override
                                                protected void initChannel(SocketChannel ch) throws Exception {
                                                    ch.pipeline().addLast(new EchoServerHandler());
                                                }
                                            });
            System.out.println("Echo 服务器启动ing");
            //绑定端口,同步等待成功
            ChannelFuture channelFuture = sbs.bind(port).sync();
            //等待服务端监听端口关闭
            channelFuture.channel().closeFuture().sync();
        } finally {
            //优雅关闭
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}
