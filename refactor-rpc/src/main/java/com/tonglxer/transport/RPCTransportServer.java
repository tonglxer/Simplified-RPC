package com.tonglxer.transport;

/**
 * RPC传输服务端
 *
 * @Author Tong LinXing
 * @date 2020/12/26
 */
public interface RPCTransportServer {
    /**
     * RPC传输服务端启动
     * */
    void start();

    /**
     * RPC传输服务端关闭
     * */
    void close();

}
