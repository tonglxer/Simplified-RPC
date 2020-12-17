package com.tonglxer.common.exception;

/**
 * 自定义RPC异常
 *
 * @Author Tong LinXing
 * @date 2020/12/17
 */
public class RPCException extends RuntimeException{
    public RPCException(String message) {
        super(message);
    }
}
