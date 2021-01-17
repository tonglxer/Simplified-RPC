package com.tonglxer.transport.netty.client;

import com.tonglxer.proto.RPCResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 客户端还未获得响应的请求集合
 * 以CompletableFuture形式存储
 *
 * @Author Tong LinXing
 * @date 2021/1/17
 */
@Slf4j
public class IncompleteRequests {
    /**
     * 未完成的请求任务集合
     * @key 请求序号
     * @value 请求的异步任务
     */
    private static final Map<String, CompletableFuture<RPCResponse>> INCOMPLETE_FUTURES = new ConcurrentHashMap<>();

    private static final IncompleteRequests instance = new IncompleteRequests();

    /**
     * 单例类隐藏构造函数：
     * 一个客户端对应一个单例
     */
    private IncompleteRequests() {
    }

    /**
     * 饿汉式单例
     *
     * @return 未完成请求类的单例对象
     */
    public static IncompleteRequests getInstance() {
        return instance;
    }


    /**
     * 将请求放入未完成集合中
     *
     * @param requestId 请求序号
     * @param future 请求的异步任务
     */
    public void put(String requestId, CompletableFuture<RPCResponse> future) {
        INCOMPLETE_FUTURES.put(requestId, future);
    }

    /**
     * 将收到响应的请求标记为已完成
     *
     * @param rpcResponse 收到的rpc响应
     */
    public void complete(RPCResponse rpcResponse) {
        CompletableFuture<RPCResponse> future = INCOMPLETE_FUTURES.remove(rpcResponse.getRpcInfo().getRequestID());
        if (null != future) {
            future.complete(rpcResponse);
        } else {
            log.error("The response {} future can not complete.", rpcResponse.getRpcInfo().getRequestID());
        }
    }
}
