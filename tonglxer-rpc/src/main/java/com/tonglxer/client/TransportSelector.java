package com.tonglxer.client;

import com.tonglxer.proto.EndPoint;
import com.tonglxer.transport.TransportClient;

import java.util.List;

/**
 * 服务选择器
 *
 * @Author Tong LinXing
 * @date 2020/12/15
 */
public interface TransportSelector {

    /**
     * 选择器初始化
     *
     * @param endPoints 服务端点信息
     * @param count 建立的连接数
     * @param clazz 通信节点类
     * */
    void init(List<EndPoint> endPoints, int count,
              Class<? extends TransportClient> clazz);

    /**
     * 选择一个通信节点与server进行交互
     *
     * @return 网络client
     * */
    TransportClient select();

    /**
     * 释放通信节点资源
     *
     * @param client 通信客户节点
     * */
    void release(TransportClient client);

    void close();

}
