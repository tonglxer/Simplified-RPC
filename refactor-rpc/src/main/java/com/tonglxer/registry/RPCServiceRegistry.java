package com.tonglxer.registry;

import java.net.InetSocketAddress;

/**
 * 注册中心：服务注册与查找
 *
 * @Author Tong LinXing
 * @date 2020/12/21
 */
public interface RPCServiceRegistry {
    /**
     * 服务注册
     *
     * @param serviceName 服务名称
     * @param endPoint 服务ip与端口信息
     * */
    void register(String serviceName, InetSocketAddress endPoint);

    /**
     * 通过服务名称查找服务实例
     *
     * @param serviceName 服务名称
     * @return 服务ip与端口
     */
    InetSocketAddress lookupService(String serviceName);
}
