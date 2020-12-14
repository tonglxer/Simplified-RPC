package com.tonglxer.proto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * RPC的请求信息
 *
 * @Author Tong LinXing
 * @date 2020/12/12
 */
@Data
@AllArgsConstructor
public class RPCRequest {
    private ServiceDescriptor service;
    private Object[] args;
}
