package com.tonglxer.transport;

/**
 * 服务端：
 * 1. 启动并监听端口
 * 2. 接收请求
 * 3. 关闭监听
 *
 * @Author Tong LinXing
 * @date 2020/12/13
 */
public interface TransportServer {
    void init(int port, RequestHandler handler);

    void start();

    void stop();
}
