package com.tonglxer.codec.protostuff;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.tonglxer.codec.Encoder;
import lombok.extern.slf4j.Slf4j;

/**
 * Protostuff方式序列化
 *
 * ProtoBuffer相关的类都无法使用反射获取，故需要进一步重构框架
 *
 * @Author Tong LinXing
 * @date 2020/12/17
 */
@Slf4j
public class ProtostuffEncoder implements Encoder {

    // 避免每次序列化都重复申请缓存空间
    private static final LinkedBuffer BUFFER = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);

    @Override
    public byte[] encode(Object object) {
        Class<?> clazz = object.getClass();
        Schema schema = RuntimeSchema.getSchema(clazz);
        byte[] bytes;
        try {
            bytes = ProtostuffIOUtil.toByteArray(object, schema, BUFFER);
        } finally {
            // 序列化完成后清理Buffer
            BUFFER.clear();
        }
        log.info("ProtostuffEncoder encoder...");
        return bytes;
    }
}
