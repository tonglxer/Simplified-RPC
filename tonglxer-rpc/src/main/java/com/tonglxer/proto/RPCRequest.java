package com.tonglxer.proto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RPC的请求信息
 *
 * @Author Tong LinXing
 * @date 2020/12/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RPCRequest {
    private CommonInfo commonInfo;
    private ServiceDescriptor service;
    private Object[] args;
}
