package com.tonglxer.server.impl;

import com.tonglxer.common.utils.ReflectionUtils;
import com.tonglxer.proto.RPCRequest;
import com.tonglxer.proto.ServiceDescriptor;
import com.tonglxer.common.dto.ServiceInstance;
import com.tonglxer.server.ServiceManager;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注册服务和查找服务
 *
 * @Author Tong LinXing
 * @date 2020/12/14
 */
@NoArgsConstructor
@Slf4j
public class MapServiceManager implements ServiceManager {
    private Map<ServiceDescriptor, ServiceInstance> services = new ConcurrentHashMap<>();

    @Override
    public <T> void register(Class<T> clazz, T bean) {
        Method[] methods = ReflectionUtils.getPublicMethods(clazz);
        for (Method m : methods) {
            ServiceInstance instance = new ServiceInstance(bean, m);
            ServiceDescriptor descriptor = ServiceDescriptor.from(clazz, m);
            services.put(descriptor, instance);
            log.info("The service ({} : {}) is already registered.",
                    descriptor.getClazz(), descriptor.getMethod());
        }
    }

    @Override
    public ServiceInstance lookup(RPCRequest request) {
        ServiceDescriptor descriptor = request.getService();
        return services.get(descriptor);
    }
}
