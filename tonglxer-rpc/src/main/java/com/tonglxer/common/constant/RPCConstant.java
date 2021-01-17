package com.tonglxer.common.constant;

/**
 * RPC涉及的一些常量
 *
 * @Author Tong LinXing
 * @date 2020/12/14
 */
public class RPCConstant {
    // 只有魔数开头的请求或响应才会进一步处理
    public static final String MagicNumber = "0315";

    // RPC请求标识
    public static final int REQUEST_TYPE = 1;

    // RPC响应标识
    public static final int RESPONSE_TYPE = 2;

}
