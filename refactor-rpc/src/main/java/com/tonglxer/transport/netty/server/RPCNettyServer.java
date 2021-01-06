package com.tonglxer.transport.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

/**
 * Netty服务端
 *
 * @Author Tong LinXing
 * @date 2021/1/6
 */
@Data
@AllArgsConstructor
@Slf4j
public class RPCNettyServer {
    // Netty服务端绑定的端口
    private final int port;

    public void run() {
        // boss:用于处理客户端的 TCP 连接请求。
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        // worker:负责每一条连接的具体读写数据的处理逻辑，真正负责 I/O 读写操作，交由对应的 Handler 处理。
        // 默认值就是 CPU 核心数/2
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new RPCServerHandler());
                        }
                    });
            // 绑定端口并启动
            ChannelFuture future = b.bind().sync();
            System.out.println("在：" + future.channel().localAddress() + "开启监听");
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("RPCNettyServer connect or close operation was interrupted.");
        }
        finally {
            try {
                // 优雅的关闭线程组
                bossGroup.shutdownGracefully().sync();
                workerGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                log.error("Netty Server Interrupt of shut down EventLoopGroup.");
            }
        }
    }

}
