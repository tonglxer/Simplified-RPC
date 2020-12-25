package com.tonglxer.transport.socket;

import com.tonglxer.codec.RPCSerialize;
import com.tonglxer.codec.fastjson.FastJsonSerialize;
import com.tonglxer.codec.kyro.KyroSerialize;
import com.tonglxer.codec.protostuff.ProtostuffSerialize;
import com.tonglxer.common.constant.RPCConstant;
import com.tonglxer.common.exception.RPCException;
import com.tonglxer.proto.CommonMessage;
import com.tonglxer.proto.RPCRequest;
import com.tonglxer.proto.RPCResponse;
import com.tonglxer.registry.RPCServiceRegistry;
import com.tonglxer.registry.zk.ZookeeperServiceRegistry;
import com.tonglxer.transport.RPCTransport;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Socket客户端
 *
 * @Author Tong LinXing
 * @date 2020/12/24
 */
@Data
@AllArgsConstructor
@Slf4j
public class RPCSocketClient implements RPCTransport {
    // 注册中心
    private RPCServiceRegistry registry;

    @Override
    public Object sendRpcRequest(RPCRequest rpcRequest) {
        String serviceName = rpcRequest.getInterfaceName();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 8889);
//                registry.lookupService(serviceName);
        try (Socket socket = new Socket()){
            socket.connect(inetSocketAddress);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            // 1. 将rpc请求写入输出流传输至服务端
            RPCSerialize serialize = serializeSelector(rpcRequest.getRpcInfo().getSerializeType());
            outputStream.write(serialize.encode(rpcRequest));
            outputStream.flush();
            outputStream.close();
            // 2. 从服务端接收返回结果
            DataInputStream inputStream = new DataInputStream(socket.getInputStream());
            RPCResponse response = serialize.decode(inputStream.readAllBytes(), RPCResponse.class);
            System.out.println("accept:" + response.toString());
            return null;
        } catch (IOException e) {
            throw new RPCException("Server connection failed.");
        }
    }

    /**
     * 序列化选择器
     *
     * @param serializeType 指定的序列化类型
     * @return 指定的序列化实例
     * */
    private RPCSerialize serializeSelector(int serializeType) {
        if (serializeType == RPCConstant.FASTJSON_SERIALIZE) {
            return new FastJsonSerialize();
        } else if (serializeType == RPCConstant.KYRO_SERIALIZE) {
            return new KyroSerialize();
        } else if (serializeType == RPCConstant.PROTOSTUFF_SERIALIZE) {
            return new ProtostuffSerialize();
        } else {
            throw new RPCException("Unsupported serialization type.");
        }
    }

}
