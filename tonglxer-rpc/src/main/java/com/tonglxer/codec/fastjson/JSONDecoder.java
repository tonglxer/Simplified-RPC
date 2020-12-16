package com.tonglxer.codec.fastjson;

import com.alibaba.fastjson.JSON;
import com.tonglxer.codec.Decoder;

/**
 * 基于json的反序列化
 *
 * @Author Tong LinXing
 * @date 2020/12/13
 */
public class JSONDecoder implements Decoder {

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
