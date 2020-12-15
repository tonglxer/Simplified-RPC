package com.tonglxer.server;

import com.tonglxer.proto.RPCRequest;
import com.tonglxer.server.dto.ServiceInstance;

/**
 *
 *
 * @Author Tong LinXing
 * @date 2020/12/14
 */
public interface ServiceManager {
    // TODO: 2020/12/14 增加更新等方法
    <T> void register(Class<T> interfaceClass, T bean);
    ServiceInstance lookup(RPCRequest request);
}
