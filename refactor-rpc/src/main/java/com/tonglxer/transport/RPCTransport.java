package com.tonglxer.transport;

import com.tonglxer.proto.RPCRequest;

/**
 * @Author Tong LinXing
 * @date 2020/12/20
 */
public interface RPCTransport {
    /**
     * 发送RPC请求并获取结果
     *
     * @param rpcRequest message body
     * @return data from server
     */
    Object sendRpcRequest(RPCRequest rpcRequest);
}
