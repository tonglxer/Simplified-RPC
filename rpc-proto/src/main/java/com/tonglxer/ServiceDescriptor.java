package com.tonglxer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务信息
 *
 * @Author Tong LinXing
 * @date 2020/12/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDescriptor {
    private String clazz;
    private String method;
    private String returnType;
    private String[] parameterTypes;
}
