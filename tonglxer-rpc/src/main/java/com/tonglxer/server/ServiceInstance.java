package com.tonglxer.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 服务实例
 *
 * @Author Tong LinXing
 * @date 2020/12/14
 */
@Data
@AllArgsConstructor
public class ServiceInstance {
    // 目标类
    private Object target;
    // 所调用的方法
    private Method method;
}
