package com.tonglxer.common.constant;

/**
 * RPC框架涉及的常量
 *
 * @Author Tong LinXing
 * @date 2020/12/18
 */
public class RPCConstant {
    /**
     * RPC消息标识
     * */
    public static final int RPC_MARK = 315;

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

    /**
     * fastjson序列化标识
     * */
    public static final int FASTJSON_SERIALIZE = 1;

    /**
     * Kyro序列化标识
     * */
    public static final int KYRO_SERIALIZE = 2;

    /**
     * protostuff序列化标识
     * */
    public static final int PROTOSTUFF_SERIALIZE = 3;

    /**
     * http传输标识（Jetty）
     * */
    public static final int HTTP_TRANSPORT = 1;

    /**
     * socket传输标识
     * */
    public static final int SOCKET_TRANSPORT = 2;

    /**
     * netty传输标识(基于NIO)
     * */
    public static final int NETTY_TRANSPORT = 3;

    /**
     * 默认的zookeeper地址
     * */
    private static final String DEFAULT_ZOOKEEPER_ADDRESS = "127.0.0.1:2181";

}
