package com.tonglxer.server;

import com.tonglxer.common.utils.ReflectionUtils;
import com.tonglxer.proto.RPCRequest;
import com.tonglxer.server.dto.ServiceInstance;

/**
 * 调用具体服务
 *
 * @Author Tong LinXing
 * @date 2020/12/14
 */
public class ServiceInvoker {
    // TODO: 2020/12/14 重构为jdk动态代理
    public Object invoke(ServiceInstance service, RPCRequest request) {
        return ReflectionUtils.invoke(service.getTarget(),
                service.getMethod(),
                request.getArgs());
    }
}
