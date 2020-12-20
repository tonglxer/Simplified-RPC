package com.tonglxer.codec.protostuff;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.tonglxer.codec.RPCSerialize;
import lombok.extern.slf4j.Slf4j;

/**
 * Protostuff方式序列化
 *
 *
 * @Author Tong LinXing
 * @date 2020/12/19
 */
@Slf4j
public class ProtostuffSerialize implements RPCSerialize {
    // 避免每次序列化都重复申请缓存空间
    private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    @Override
    public byte[] encode(Object object) {
        log.info("Use ProtostuffSerialize to encode.");
        Class<?> clazz = object.getClass();
        Schema schema = RuntimeSchema.getSchema(clazz);
        byte[] bytes;
        try {
            bytes = ProtostuffIOUtil.toByteArray(object, schema, BUFFER);
        } finally {
            // 序列化完成后清理Buffer
            BUFFER.clear();
        }
        return bytes;
    }

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        log.info("Use ProtostuffSerialize to decode.");
        Schema<T> schema = RuntimeSchema.getSchema(clazz);
        T obj = schema.newMessage();
        ProtostuffIOUtil.mergeFrom(bytes, obj, schema);
        return obj;
    }
}
