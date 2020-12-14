package com.tonglxer.server;

import com.tonglxer.proto.RPCRequest;

/**
 *
 *
 * @Author Tong LinXing
 * @date 2020/12/14
 */
public interface ServiceManager {
    <T> void register(Class<T> interfaceClass, T bean);
    ServiceInstance lookup(RPCRequest request);
}
