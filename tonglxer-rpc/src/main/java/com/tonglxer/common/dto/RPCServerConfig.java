package com.tonglxer.common.dto;

import com.tonglxer.codec.Decoder;
import com.tonglxer.codec.Encoder;
import com.tonglxer.codec.fastjson.JSONDecoder;
import com.tonglxer.codec.fastjson.JSONEncoder;
import com.tonglxer.codec.kyro.KyroDecoder;
import com.tonglxer.codec.kyro.KyroEncoder;
import com.tonglxer.codec.protostuff.ProtostuffDecoder;
import com.tonglxer.codec.protostuff.ProtostuffEncoder;
import com.tonglxer.transport.TransportServer;
import com.tonglxer.transport.http.HTTPTransportServer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 服务端配置
 *
 * @Author Tong LinXing
 * @date 2020/12/14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPCServerConfig {

    private Class<? extends TransportServer> transportClass = HTTPTransportServer.class;

    private Class<? extends Encoder> encoderClass = KyroEncoder.class;

    private Class<? extends Decoder> decoderClass = KyroDecoder.class;

    private int port = 8888;

}
