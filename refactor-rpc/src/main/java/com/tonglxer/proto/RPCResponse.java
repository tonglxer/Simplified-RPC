package com.tonglxer.proto;

/**
 * RPC 响应
 *
 * @Author Tong LinXing
 * @date 2020/12/18
 */
public class RPCResponse {
    // RPC基本信息
    private CommonMessage rpcInfo;

    // 响应码
    private int code;

    // 响应信息
    private String message;

    // 返回的数据
    private Object data;
}
