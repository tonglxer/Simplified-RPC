package com.tonglxer.transport.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty服务端消息处理类
 *
 * @Author Tong LinXing
 * @date 2021/1/3
 */
@Slf4j
@ChannelHandler.Sharable
public class RPCServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf byteBuf = (ByteBuf)msg;
        System.out.println("接收到客户端：" + byteBuf.toString(CharsetUtil.UTF_8));
        ctx.channel().writeAndFlush(Unpooled.copiedBuffer("回复客户端：我已接收到你的消息", CharsetUtil.UTF_8));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Netty Server have some error.");
        // 异常时直接关闭通道
        ctx.close();
    }
}
