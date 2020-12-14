package com.tonglxer.codec;

/**
 * 序列化
 *
 * @Author Tong LinXing
 * @date 2020/12/12
 */
public interface Encoder {
    byte[] encode(Object object);
}
