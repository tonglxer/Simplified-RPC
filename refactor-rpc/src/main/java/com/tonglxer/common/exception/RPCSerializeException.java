package com.tonglxer.common.exception;

/**
 * RPC 序列化异常
 *
 * @Author Tong LinXing
 * @date 2020/12/20
 */
public class RPCSerializeException extends RuntimeException {

    public RPCSerializeException(String message) {
        super(message);
    }
}
