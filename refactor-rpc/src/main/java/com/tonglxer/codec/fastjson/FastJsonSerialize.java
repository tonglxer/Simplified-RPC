package com.tonglxer.codec.fastjson;

import com.alibaba.fastjson.JSON;
import com.tonglxer.codec.RPCSerialize;
import lombok.extern.slf4j.Slf4j;

/**
 * FastJson方式序列化与反序列化
 *
 * @Author Tong LinXing
 * @date 2020/12/18
 */
@Slf4j
public class FastJsonSerialize implements RPCSerialize {

    @Override
    public byte[] encode(Object object) {
        log.info("Use FastJsonSerialize to encode.");
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        log.info("Use FastJsonSerialize to decode.");
        return JSON.parseObject(bytes, clazz);
    }
}
