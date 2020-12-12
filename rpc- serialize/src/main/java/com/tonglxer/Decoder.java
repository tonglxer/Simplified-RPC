package com.tonglxer;

/**
 * 反序列化
 *
 * @Author Tong LinXing
 * @date 2020/12/12
 */
public interface Decoder {
    <T> T decode(byte[] bytes, Class<T> clazz);
}
