package com.su._01_echo.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * ChannelInboundHandlerAdapter is deprecated use ChannelHandleAdapter instead
 * override 3 method to useSelf operate when connect read , readComplete and throw exception
 */
public class EchoServerHandler extends ChannelHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf data = (ByteBuf) msg;
        System.out.println("my netty read data:  " + msg);
        ctx.writeAndFlush(data);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("my netty echo server read completed");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("my netty echo server exception occur");
        cause.printStackTrace();
        ctx.close();
    }
}
