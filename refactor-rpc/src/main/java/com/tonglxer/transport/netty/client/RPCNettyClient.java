package com.tonglxer.transport.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Netty客户端
 *
 * @Author Tong LinXing
 * @date 2021/1/3
 */
@Data
@AllArgsConstructor
@Slf4j
public class RPCNettyClient {
    // Netty服务端IP
    private final String host;
    // Netty服务端端口
    private final int port;

    /**
     * 启动Netty客户端实例并发送消息
     * */
    public void run() {
        // Netty的IO线程池
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            // Netty客户端辅助实例
            Bootstrap bs = new Bootstrap();
            bs.group(group)
                    .channel(NioSocketChannel.class) // 设定Channel类型
                    .remoteAddress(new InetSocketAddress(host, port)) // 配置远端服务器信息
                    .handler(new ChannelInitializer<SocketChannel>() { // 添加客户端处理器
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new RPCClientHandler());
                        }
                    });
            // 连接Netty服务端
            ChannelFuture future = bs.connect().sync();
            // 发送数据
            future.channel().writeAndFlush(Unpooled.copiedBuffer("Hello tonglxer", CharsetUtil.UTF_8));
            // 关闭连接（阻塞操作）
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("RPCNettyClient connect or close operation was interrupted.");
        } finally {
            try {
                // 关闭IO线程池
                group.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                log.error("Interrupt of shut down EventLoopGroup.");
            }
        }
    }

}
