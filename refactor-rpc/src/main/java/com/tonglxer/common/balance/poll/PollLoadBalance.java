package com.tonglxer.common.balance.poll;

import com.tonglxer.common.balance.LoadBalance;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 基于轮询方式的负载均衡
 *
 * @Author Tong LinXing
 * @date 2020/12/23
 */
public class PollLoadBalance implements LoadBalance {
    /**
     * 轮询指针
     * */
    private static AtomicInteger index = new AtomicInteger(-1);

    /**
     * 单服务器场景时的数量
     * */
    private static final int SINGLE_SERVER = 1;

    /**
     * 饿汉模式单例
     * */
    private static LoadBalance instance = new PollLoadBalance();

    /**
     * 隐藏构造方法
     * */
    private PollLoadBalance() {
    }

    /**
     * 获取负载均衡实例
     *
     * @return 轮询实例
     * */
    public static LoadBalance getInstance() {
        return instance;
    }

    @Override
    public String getTargetAddress(List<String> serverList) {
        int n = serverList.size();
        if (n == SINGLE_SERVER) {
            return serverList.get(0);
        } else {
            int i = index.incrementAndGet()%n;
            return serverList.get(i);
        }
    }

}
