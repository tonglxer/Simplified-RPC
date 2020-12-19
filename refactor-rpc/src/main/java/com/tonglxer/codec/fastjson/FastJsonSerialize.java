package com.tonglxer.codec.fastjson;

import com.alibaba.fastjson.JSON;
import com.tonglxer.codec.RPCSerialize;
import lombok.Data;

/**
 * FastJson方式序列化与反序列化
 *
 * @Author Tong LinXing
 * @date 2020/12/18
 */
@Data
public class FastJsonSerialize implements RPCSerialize {

    @Override
    public byte[] encode(Object object) {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        return JSON.parseObject(bytes, clazz);
    }
}
