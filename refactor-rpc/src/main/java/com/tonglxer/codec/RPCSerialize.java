package com.tonglxer.codec;

/**
 * RPC序列化与反序列化
 *
 * @Author Tong LinXing
 * @date 2020/12/18
 */
public interface RPCSerialize {
    /**
     * 序列化
     *
     * @param object 需要序列化的对象
     * @return 序列化后的字节数组
     * */
    byte[] encode(Object object);

    /**
     * 反序列化
     *
     * @param bytes 字节数组
     * @param clazz 反序列化的对象类型
     * @return 反序列化后的对象
     * */
    <T> T decode(byte[] bytes, Class<T> clazz);
}
