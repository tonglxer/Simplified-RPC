package com.tonglxer.transport;

import com.tonglxer.proto.EndPoint;

import java.io.InputStream;

/**
 * 客户端：
 *
 * 1. 创建连接
 * 2. 发送数据，并且等待响应
 * 3. 关闭连接
 *
 * @Author Tong LinXing
 * @date 2020/12/13
 */
public interface TransportClient {
    /**
     * 创建连接
     *
     * @param endPoint 网络端点信息（ip + port）
     * */
    void connect(EndPoint endPoint);

    /**
     * 发送数据
     *
     * @param data 数据流
     * @return 响应流
     * */
    InputStream write(InputStream data);

    void close();


}
