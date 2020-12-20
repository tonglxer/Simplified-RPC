package com.tonglxer.common.exception;

/**
 * RPC 网络传输异常
 *
 * @Author Tong LinXing
 * @date 2020/12/20
 */
public class RPCTransportException extends RuntimeException {

    public RPCTransportException(String message) {
        super(message);
    }
}
