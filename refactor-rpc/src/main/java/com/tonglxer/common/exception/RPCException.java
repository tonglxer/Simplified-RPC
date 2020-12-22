package com.tonglxer.common.exception;

/**
 * RPC异常
 *
 * @Author Tong LinXing
 * @date 2020/12/22
 */
public class RPCException extends RuntimeException {

    public RPCException(String message) {
        super(message);
    }
}
