package com.tonglxer.proto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

/**
 * 服务信息
 *
 * @Author Tong LinXing
 * @date 2020/12/12
 */

// 已包含@EqualsAndHashCode，若有父类，则需要拆分并加上callSuper=true
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDescriptor {
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypes;

    /**
     * 根据参数生成ServiceDescriptor
     *
     * @param clazz 目标类字节码
     * @param method 需要调用的方法
     * @return 生成的服务描述类
     * */
    public static ServiceDescriptor from(Class clazz, Method method) {
        ServiceDescriptor descriptor = new ServiceDescriptor();
        descriptor.setClazz(clazz.getName());
        descriptor.setMethod(method.getName());
        descriptor.setReturnType(method.getReturnType().getName());

        Class[] argsClasses = method.getParameterTypes();
        String[] argsTypes = new String[argsClasses.length];
        for (int i=0; i<argsClasses.length; i++) {
            argsTypes[i] = argsClasses[i].getName();
        }
        descriptor.setParameterTypes(argsTypes);

        return descriptor;
    }
}
