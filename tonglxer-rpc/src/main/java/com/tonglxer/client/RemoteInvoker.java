package com.tonglxer.client;

import com.tonglxer.codec.Decoder;
import com.tonglxer.codec.Encoder;
import com.tonglxer.common.constant.RPCConstant;
import com.tonglxer.proto.RPCRequest;
import com.tonglxer.proto.RPCResponse;
import com.tonglxer.proto.ServiceDescriptor;
import com.tonglxer.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 远程服务代理类
 *
 * @Author Tong LinXing
 * @date 2020/12/15
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {
    private Class clazz;

    private Encoder encoder;

    private Decoder decoder;

    private TransportSelector selector;

    public RemoteInvoker(Class clazz, Encoder encoder,
                  Decoder decoder, TransportSelector selector) {
        this.clazz = clazz;
        this.encoder = encoder;
        this.decoder = decoder;
        this.selector = selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RPCRequest request = new RPCRequest();
        request.setService(ServiceDescriptor.from(clazz, method));
        request.setArgs(args);

        RPCResponse response = invokeRemote(request);
        if (response == null || response.getCode()!= RPCConstant.SUCCESS) {
            log.error("The remote proxy invoke is fail. {}", response);
        }
        return response.getData();
    }

    private RPCResponse invokeRemote(RPCRequest request) {
        RPCResponse response = null;
        TransportClient client = null;
        try {
            client = selector.select();

            // 1. 将请求编码
            byte[] outBytes = encoder.encode(request);
            // 2. 写入序列化的请求，并接收响应
            InputStream receive = client.write(new ByteArrayInputStream(outBytes));
            // 3. 读取响应byte数组
            byte[] inBytes = IOUtils.readFully(receive, receive.available());
            // 4. 解码响应
            response = decoder.decode(inBytes, RPCResponse.class);
        } catch (IOException e) {
            log.error("The invokeRemote read response fail.");
            response = new RPCResponse();
            response.setCode(RPCConstant.FAIL);
            response.setMessage(RPCConstant.FAIL_MESSAGE);
        } finally {
            if (client != null) {
                selector.release(client);
            }
        }
        // 5. 返回响应结果
        return response;
    }
}
