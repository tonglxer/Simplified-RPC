package com.tonglxer.transport.netty.codec;

import com.tonglxer.codec.RPCSerialize;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @Author Tong LinXing
 * @date 2021/1/8
 */
@AllArgsConstructor
@Slf4j
public class RPCNettyDecoder extends ByteToMessageDecoder {
    /**
     * 序列化方式
     * */
    private final RPCSerialize serialize;

    /**
     * 要转换的对象类型
     * */
    private final Class<?> clazz;

    /**
     * Netty数据包基础长度
     */
    private static final int BODY_LENGTH = 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {

        //1.至少四字节才可读
        if (in.readableBytes() >= BODY_LENGTH) {
            //2.标记当前readIndex的位置，以便重置readIndex时使用
            in.markReaderIndex();
            //3.读取消息的长度
            int dataLength = in.readInt();
            //4.遇到不合理的情况直接 return
            if (dataLength < 0 || in.readableBytes() < 0) {
                log.error("Data length or byteBuf readableBytes is not valid");
                return;
            }
            //5.如果可读字节数小于消息长度的话，说明是不完整的消息，重置readIndex
            if (in.readableBytes() < dataLength) {
                in.resetReaderIndex();
                return;
            }
            // 6.获取字节数组
            byte[] body = new byte[dataLength];
            in.readBytes(body);
            // 7.将bytes数组转换为特定对象
            Object obj = serialize.decode(body, clazz);
            out.add(obj);
            log.info("RPCNettyDecoder successful decode ByteBuf to {}", clazz.getName());
        }
    }
}
