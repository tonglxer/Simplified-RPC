package com.tonglxer.server;

import com.tonglxer.codec.Decoder;
import com.tonglxer.codec.Encoder;
import com.tonglxer.common.utils.ReflectionUtils;
import com.tonglxer.proto.RPCRequest;
import com.tonglxer.proto.RPCResponse;
import com.tonglxer.server.impl.MapServiceManager;
import com.tonglxer.transport.RequestHandler;
import com.tonglxer.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * RPC对外提供的服务，即直接使用的API
 *
 * @Author Tong LinXing
 * @date 2020/12/14
 */
@Slf4j
public class RPCServer {
    private RPCServerConfig config;
    private TransportServer transport;
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;

    public RPCServer(RPCServerConfig config) {
        this.config = config;

        this.transport = ReflectionUtils.newInstance(config.getTransportClass());
        transport.init(config.getPort(), this.handler);

        this.encoder = ReflectionUtils.newInstance(config.getEncoderClass());
        this.decoder = ReflectionUtils.newInstance(config.getDecoderClass());

        this.serviceManager = new MapServiceManager();
        this.serviceInvoker = new ServiceInvoker();

    }

    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

    public void start(){
        transport.start();
    }
    public void stop(){
        transport.stop();
    }

    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream to) {
            RPCResponse response = new RPCResponse();
            try {
                // 1. 读数据
                byte[] inBytes = IOUtils.readFully(receive, receive.available());
                // 2. 反序列化
                RPCRequest request = decoder.decode(inBytes, RPCRequest.class);
                log.info("get request: {}",request);
                // 3. 查找服务
                ServiceInstance instance = serviceManager.lookup(request);
                // 4. 调用服务
                Object invoke = serviceInvoker.invoke(instance, request);
                response.setData(invoke);

            } catch (IOException e) {
                response.setCode(1);// 1代表失败
                response.setMessage("RPC Server have some error: " + e.getClass().getName());
            } finally {
                try {
                    // 5. 将调用结果序列化
                    byte[] outBytes = encoder.encode(response);
                    // 6. 写入结果
                    to.write(outBytes);
                    log.info("Start to response client");
                } catch (IOException e) {
                    log.error("RPC Server handler have IOException.");
                }
            }
        }
    };
}
