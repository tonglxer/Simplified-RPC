package com.tonglxer.proto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RPC 请求
 *
 * @Author Tong LinXing
 * @date 2020/12/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RPCRequest {
    // RPC基本信息
    private CommonMessage rpcInfo;

    // 调用的服务接口全类名
    private String interfaceName;

    // 方法名
    private String methodName;

    // 方法返回类型
    private String returnType;

    // 方法参数类型列表
    private String[] parameterTypes;

    // 方法参数列表
    private Object[] args;

}
