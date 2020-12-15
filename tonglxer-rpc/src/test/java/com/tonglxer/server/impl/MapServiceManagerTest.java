package com.tonglxer.server.impl;

import com.tonglxer.common.utils.ReflectionUtils;
import com.tonglxer.proto.RPCRequest;
import com.tonglxer.proto.ServiceDescriptor;
import com.tonglxer.server.dto.ServiceInstance;
import com.tonglxer.server.ServiceManager;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * @Author Tong LinXing
 * @date 2020/12/14
 */
public class MapServiceManagerTest {
    ServiceManager manager;

    @Before
    public void init() {
        manager = new MapServiceManager();
        TestInterface bean = new TestClass();
        manager.register(TestInterface.class, bean);
    }

    @Test
    public void register() {
        TestInterface bean = new TestClass();
        manager.register(TestInterface.class, bean);
    }

    @Test
    public void lookup() {
        Method method = ReflectionUtils.getPublicMethods(TestInterface.class)[0];
        ServiceDescriptor descriptor = ServiceDescriptor.from(TestInterface.class, method);
        RPCRequest request = new RPCRequest();
        request.setService(descriptor);

        ServiceInstance instance = manager.lookup(request);
        System.out.println(instance.getMethod().getName());
    }
}