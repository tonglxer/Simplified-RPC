package com.tonglxer.common.constant;

/**
 * RPC框架涉及的常量
 *
 * @Author Tong LinXing
 * @date 2020/12/18
 */
public class RPCConstant {
    /**
     * 魔数：设定为RPC消息的标识
     * */
    public static final int RPC_MAGIC_NUMBER = 315;

    /**
     * 成功响应码
     * */
    public static final int SUCCESS_CODE = 1;

    /**
     * 失败响应码
     * */
    public static final int FAIL_CODE = 2;

    /**
     * 成功响应消息
     * */
    public static final String SUCCESS_MESSAGE = "RPC service call succeeded. ";
}
