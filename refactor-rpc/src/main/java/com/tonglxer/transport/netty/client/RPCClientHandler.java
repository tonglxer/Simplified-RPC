package com.tonglxer.transport.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * Netty消息处理类
 *
 * @Author Tong LinXing
 * @date 2021/1/3
 */
@Slf4j
@ChannelHandler.Sharable
public class RPCClientHandler extends SimpleChannelInboundHandler<ByteBuf> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) {
        System.out.println("接收到服务端信息：" + byteBuf.toString(CharsetUtil.UTF_8));
        // 接收到一次消息后就关闭客户端
        channelHandlerContext.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("Netty Client have some error.");
        // 异常时直接关闭通道
        ctx.close();
    }
}
