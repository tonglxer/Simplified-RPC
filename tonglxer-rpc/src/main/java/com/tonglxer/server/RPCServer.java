package com.tonglxer.server;

import com.tonglxer.codec.Decoder;
import com.tonglxer.codec.Encoder;
import com.tonglxer.common.constant.RPCConstant;
import com.tonglxer.common.utils.ReflectionUtils;
import com.tonglxer.proto.RPCRequest;
import com.tonglxer.proto.RPCResponse;
import com.tonglxer.common.dto.RPCServerConfig;
import com.tonglxer.common.dto.ServiceInstance;
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
    // 核心参数
    private RPCServerConfig config;
    // 传输方式
    private TransportServer transport;
    // 序列化
    private Encoder encoder;
    // 反序列化
    private Decoder decoder;
    // 注册或查找服务
    private ServiceManager serviceManager;
    // 调用服务方法
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

    /**
     * 注册服务
     *
     * @param interfaceClass 服务接口类
     * @param bean 服务实例
     * */
    public <T> void register(Class<T> interfaceClass, T bean) {
        serviceManager.register(interfaceClass, bean);
    }

    /**
     * 启动RPC服务端
     * */
    public void start(){
        transport.start();
    }

    /**
     * 停止RPC服务端
     * */
    public void stop(){
        transport.stop();
    }

    /**
     * 请求处理
     * */
    private RequestHandler handler = new RequestHandler() {
        @Override
        public void onRequest(InputStream in, OutputStream to) {
            RPCResponse response = new RPCResponse();
            try {
                // 1. 读数据
                byte[] inBytes = IOUtils.readFully(in, in.available());
                // 2. 反序列化
                RPCRequest request = decoder.decode(inBytes, RPCRequest.class);
                log.info("RPC Server get the request: {}", request);
                // 3. 查找服务
                ServiceInstance service = serviceManager.lookup(request);
                // 4. 调用服务
                Object invoke = serviceInvoker.invoke(service, request);
                // 5. 将结果存入响应体
                response.setData(invoke);

            } catch (IOException e) {
                response.setCode(RPCConstant.FAIL);// 1代表失败
                response.setMessage(RPCConstant.FAIL_MESSAGE + e.getClass().getName());
            } finally {
                try {
                    // 6. 将调用结果序列化
                    byte[] outBytes = encoder.encode(response);
                    // 7. 将序列化后的结果写入输出流
                    to.write(outBytes);
                    log.info("Send response to RPC client.");
                } catch (IOException e) {
                    log.error("RPC Server handler have IOException.");
                }
            }
        }
    };
}
