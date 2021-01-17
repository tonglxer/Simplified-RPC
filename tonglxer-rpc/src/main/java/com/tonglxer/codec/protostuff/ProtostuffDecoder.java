package com.tonglxer.codec.protostuff;

import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.tonglxer.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

/**
 * ProtoBuffer方式反序列化
 *
 * @Author Tong LinXing
 * @date 2020/12/17
 */
@Slf4j
public class ProtostuffDecoder implements Decoder {
    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        T obj = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        log.info("ProtoBufferDecoder decoder...");
        return obj;
    }
}
