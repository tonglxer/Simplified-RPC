package com.tonglxer.codec.kyro;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.tonglxer.codec.RPCSerialize;
import com.tonglxer.common.exception.RPCSerializeException;
import com.tonglxer.proto.RPCRequest;
import com.tonglxer.proto.RPCResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Kyro方式序列化与反序列化
 *
 * @Author Tong LinXing
 * @date 2020/12/20
 */
@Slf4j
public class KyroSerialize implements RPCSerialize {

    /**
     * kyro线程不安全, 所以使用ThreadLocal存储kyro对象
     * */
    private final ThreadLocal<Kryo> kryoThreadLocal = ThreadLocal.withInitial(() -> {
        Kryo kryo = new Kryo();
        // 注册支持的类型
        kryo.register(RPCRequest.class);
        kryo.register(RPCResponse.class);
        return kryo;
    });

    @Override
    public byte[] encode(Object object) {
        log.info("Use KyroSerialize to encode.");
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            Output output = new Output(byteArrayOutputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            // Object->byte:将对象序列化为byte数组
            kryo.writeObject(output, object);
            // 使用完成后清除kyro
            kryoThreadLocal.remove();
            return output.toBytes();
        } catch (IOException e) {
            throw new RPCSerializeException("KyroSerialize encode failed.");
        }
    }

    @Override
    public <T> T decode(byte[] bytes, Class<T> clazz) {
        log.info("Use KyroSerialize to decode.");
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            Input input = new Input(byteArrayInputStream)) {
            Kryo kryo = kryoThreadLocal.get();
            // byte->Object:从byte数组中反序列化出对对象
            Object o = kryo.readObject(input, clazz);
            // 使用完成后移除kyro
            kryoThreadLocal.remove();
            return clazz.cast(o);
        } catch (IOException e) {
            throw new RPCSerializeException("KyroSerialize decode failed.");
        }
    }
}
