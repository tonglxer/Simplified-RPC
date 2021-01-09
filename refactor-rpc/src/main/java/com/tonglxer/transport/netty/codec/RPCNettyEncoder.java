package com.tonglxer.transport.netty.codec;

import com.tonglxer.codec.RPCSerialize;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Tong LinXing
 * @date 2021/1/9
 */
@AllArgsConstructor
@Slf4j
public class RPCNettyEncoder extends MessageToByteEncoder<Object> {
    // 序列化方式
    private final RPCSerialize serialize;
    // 需要序列化的对象类型
    private final Class<?> clazz;

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (clazz.isInstance(o)) {
            // 1. 将对象编码为字节数组
            byte[] body = serialize.encode(o);
            // 2. 获取消息的长度
            int dataLength = body.length;
            // 3. 写入消息对应的字节数组长度(int 占 4字节)
            byteBuf.writeInt(dataLength);
            // 4. 将字节数组写入ByteBuf中
            byteBuf.writeBytes(body);
        } else {
            log.error("The object not instance of {}.", clazz.getName());
        }
    }
}
