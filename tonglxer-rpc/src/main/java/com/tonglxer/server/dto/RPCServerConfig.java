package com.tonglxer.server.dto;

import com.tonglxer.codec.Decoder;
import com.tonglxer.codec.Encoder;
import com.tonglxer.codec.impl.JSONDecoder;
import com.tonglxer.codec.impl.JSONEncoder;
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

    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    private int port = 8888;

}
