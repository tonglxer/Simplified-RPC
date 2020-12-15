package com.tonglxer.client;

import com.tonglxer.codec.Decoder;
import com.tonglxer.codec.Encoder;
import com.tonglxer.common.dto.RPCClientConfig;
import com.tonglxer.common.utils.ReflectionUtils;

import java.lang.reflect.Proxy;

/**
 * @Author Tong LinXing
 * @date 2020/12/15
 */
public class RPCClient {
    private RPCClientConfig config;

    private Encoder encoder;

    private Decoder decoder;

    private TransportSelector selector;

    public RPCClient(RPCClientConfig config) {
        this.config = config;
        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());
        this.selector = ReflectionUtils.newInstance(config.getSelectorClass());

        this.selector.init(
                this.config.getServers(),
                this.config.getConnectCount(),
                this.config.getTransportClass()
        );
    }

    public RPCClient() {
        this(new RPCClientConfig());
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
              clazz.getClassLoader(),
              new Class[]{clazz},
              new RemoteInvoker(clazz, encoder, decoder, selector)
        );
    }
}
