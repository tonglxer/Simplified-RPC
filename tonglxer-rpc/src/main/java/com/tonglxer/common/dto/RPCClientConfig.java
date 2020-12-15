package com.tonglxer.common.dto;

import com.tonglxer.client.TransportSelector;
import com.tonglxer.client.impl.RandomTransportSelector;
import com.tonglxer.codec.Decoder;
import com.tonglxer.codec.Encoder;
import com.tonglxer.codec.impl.JSONDecoder;
import com.tonglxer.codec.impl.JSONEncoder;
import com.tonglxer.proto.EndPoint;
import com.tonglxer.transport.TransportClient;
import com.tonglxer.transport.http.HTTPTransportClient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

/**
 * @Author Tong LinXing
 * @date 2020/12/15
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPCClientConfig {
    private Class<? extends TransportClient> transportClass = HTTPTransportClient.class;

    private Class<? extends Encoder> encoderClass = JSONEncoder.class;

    private Class<? extends Decoder> decoderClass = JSONDecoder.class;

    private Class<? extends TransportSelector> selectorClass = RandomTransportSelector.class;

    private int connectCount = 1;

    private List<EndPoint> servers = Arrays.asList(
            new EndPoint("127.0.0.1", 8888));

}
