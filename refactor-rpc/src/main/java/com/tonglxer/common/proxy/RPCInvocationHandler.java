package com.tonglxer.common.proxy;

import com.tonglxer.common.constant.RPCConstant;
import com.tonglxer.proto.CommonMessage;
import com.tonglxer.proto.RPCRequest;
import com.tonglxer.proto.RPCResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * 基于jdk实现代理
 *
 * @Author Tong LinXing
 * @date 2020/12/24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class RPCInvocationHandler implements InvocationHandler {
    /**
     * 序列化方式，默认为fastjson
     * */
    private int serializeType = RPCConstant.FASTJSON_SERIALIZE;

    /**
     * 传输方式，默认暂定socket
     * */
    private int transportType = RPCConstant.SOCKET_TRANSPORT;

    /**
     * 动态生成代理类并返回
     *
     * @param clazz 目标类
     * @return 代理类
     * */
    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[]{clazz}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        log.info("RPCClient invoke the  method: [{}]", method.getName());
        CommonMessage common = CommonMessage.builder().magicNumber(RPCConstant.RPC_MARK)
                .requestID(UUID.randomUUID().toString())
                .serializeType(this.serializeType)
                .transportType(this.transportType)
                .build();
        RPCRequest request = RPCRequest.builder().rpcInfo(common)
                .interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .returnType(method.getReturnType())
                .build();
        RPCResponse rpcResponse = null;
        // TODO: 2020/12/24 通过网络传输获取服务端返回结果
        return rpcResponse.getData();
    }
}
