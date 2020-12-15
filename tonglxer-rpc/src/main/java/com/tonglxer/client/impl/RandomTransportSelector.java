package com.tonglxer.client.impl;

import com.tonglxer.client.TransportSelector;
import com.tonglxer.common.utils.ReflectionUtils;
import com.tonglxer.proto.EndPoint;
import com.tonglxer.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Author Tong LinXing
 * @date 2020/12/15
 */
@Slf4j
public class RandomTransportSelector implements TransportSelector {
    // 已连接的客户端
    private List<TransportClient> clients;

    public RandomTransportSelector() {
        // 线程不安全 需要重构
        clients = new ArrayList<>();
    }

    @Override
    public void init(List<EndPoint> endPoints, int count,
                     Class<? extends TransportClient> clazz) {
        count = Math.max(count, 1);
        for (EndPoint e : endPoints) {
            for (int i=0; i<count; i++) {
                TransportClient client = ReflectionUtils.newInstance(clazz);
                client.connect(e);
                clients.add(client);
            }
            log.info("The {} is connect server.", e);
        }
    }

    @Override
    public TransportClient select() {
        int i = new Random().nextInt(clients.size());
        return clients.remove(i);
    }

    @Override
    public void release(TransportClient client) {
        clients.add(client);
    }

    @Override
    public void close() {
        for (TransportClient client: clients) {
            client.close();
        }
        clients.clear();
    }
}
