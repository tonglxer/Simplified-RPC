package com.tonglxer.common.balance;

import java.util.List;

/**
 * 负载均衡
 *
 * @Author Tong LinXing
 * @date 2020/12/23
 */
public interface LoadBalance {
    /**
     * 获取目标地址
     *
     * @param serverList 服务器地址列表
     * @return 分配的服务器地址
     * */
    String getTargetAddress(List<String> serverList);
}
