package com.tonglxer.common.constant;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author Tong LinXing
 * @date 2020/12/16
 */
@AllArgsConstructor
public enum RPCResponseEnum {
    SUCCESS(0, "RPC service call is succeeded."),
    FAIL(1, "RPC service call is failed: ");

    int code;
    String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
