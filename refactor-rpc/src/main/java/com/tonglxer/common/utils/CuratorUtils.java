package com.tonglxer.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.imps.CuratorFrameworkState;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Zookeeper相关操作工具类
 *
 * @Author Tong LinXing
 * @date 2020/12/21
 */
@Slf4j
public final class CuratorUtils {
    // zk节点默认前缀 用于标识RPC服务节点
    public static final String ZK_REGISTER_ROOT_PATH = "/tonglxer-rpc";

    // 重试间隔
    private static final int BASE_SLEEP_TIME = 3000;

    // 重试次数
    private static final int MAX_RETRIES = 3;

    // 服务对应的服务器地址列表信息 key: 服务全限定名 value: 该服务注册的服务器地址列表
    private static final Map<String, List<String>> SERVICE_ADDRESS_MAP = new ConcurrentHashMap<>();

    // 已注册的节点路径
    private static final Set<String> REGISTERED_PATH_SET = ConcurrentHashMap.newKeySet();

    // zk客户端实例
    private static CuratorFramework zkClient;

    // 工具类隐藏构造方法
    private CuratorUtils(){
    }

    /**
     * 创建永久节点
     *
     * @param zkClient zk客户端
     * @param path 节点路径
     */
    public static void createPersistentNode(CuratorFramework zkClient, String path) {
        try {
            if (REGISTERED_PATH_SET.contains(path) || zkClient.checkExists().forPath(path) != null) {
                log.info("The node already exists. The node is: [{}]", path);
            } else {
                // 创建永久节点：/tonglxer-rpc/服务全限定名/ip:port
                zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
                log.info("The node was created successfully. The node is: [{}]", path);
            }
            REGISTERED_PATH_SET.add(path);
        } catch (Exception e) {
            log.error("Create persistent node for path [{}] fail", path);
        }
    }

    /**
     * 获取该节点的子节点（获取该服务的所有服务器信息）
     *
     * @param serviceName 服务全限定名称
     * @return 该节点下的所有子节点（该服务启动的所有服务器地址）
     */
    public static List<String> getChildrenNodes(CuratorFramework zkClient, String serviceName) {
        if (SERVICE_ADDRESS_MAP.containsKey(serviceName)) {
            return SERVICE_ADDRESS_MAP.get(serviceName);
        }
        List<String> result = null;
        String servicePath = ZK_REGISTER_ROOT_PATH + "/" + serviceName;
        try {
            result = zkClient.getChildren().forPath(servicePath);
            // 获取服务的服务器地址信息并存入缓存
            SERVICE_ADDRESS_MAP.put(serviceName, result);
            // 添加监听器，实时更新地址信息
            addServiceAddressListener(serviceName, zkClient);
        } catch (Exception e) {
            log.error("get children nodes for path [{}] fail", servicePath);
        }
        return result;
    }

    /**
     * 清空服务注册信息
     *
     * @param zkClient zk客户端
     * @param inetSocketAddress 服务提供者的ip与端口信息
     */
    public static void clearRegistry(CuratorFramework zkClient, InetSocketAddress inetSocketAddress) {
        // parallel(): 并行流 提升效率
        REGISTERED_PATH_SET.stream().parallel().forEach(p -> {
            try {
                // 若后缀为需要清空的服务器地址，则删除节点
                if (p.endsWith(inetSocketAddress.toString())) {
                    zkClient.delete().forPath(p);
                }
            } catch (Exception e) {
                log.error("clear registry for path [{}] fail", p);
            }
        });
        log.info("All registered services on the server are cleared: [{}]", REGISTERED_PATH_SET.toString());
    }

    /**
     * 获取zk客户端实例
     *
     * @param inetSocketAddress zk服务器地址
     * @return zk客户端
     * */
    public static CuratorFramework getZkClient(InetSocketAddress inetSocketAddress) {
        String zookeeperAddress = inetSocketAddress.getHostName() + ":" + inetSocketAddress.getPort();
        // 若zk已启动，则直接返回已启动实例
        if (zkClient != null && zkClient.getState() == CuratorFrameworkState.STARTED) {
            return zkClient;
        }
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETRIES);
        zkClient = CuratorFrameworkFactory.builder()
                .connectString(zookeeperAddress) // 服务器列表: host1:port1,host2:port2,...
                .retryPolicy(retryPolicy) // 重试策略
                .build();
        zkClient.start();
        return zkClient;
    }

    /**
     * 对服务节点注册监听
     *
     * @param serviceName 服务全限定名
     */
    private static void addServiceAddressListener(String serviceName, CuratorFramework zkClient) throws Exception {
        String servicePath = ZK_REGISTER_ROOT_PATH + "/" + serviceName;
        // 可以监听servicePath路径节点下的子节点变化（1. 创建 2. 修改 3. 删除）
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, servicePath, true);
        // 定义监听器功能
        PathChildrenCacheListener pathChildrenCacheListener = (curatorFramework, pathChildrenCacheEvent) -> {
            // 获取该服务的服务器地址
            List<String> serviceAddresses = curatorFramework.getChildren().forPath(servicePath);
            // 若该服务的服务器列表有变化时，更新该服务服务器地址信息
            SERVICE_ADDRESS_MAP.put(serviceName, serviceAddresses);
        };
        // 注册监听器
        pathChildrenCache.getListenable().addListener(pathChildrenCacheListener);
        // 启动监听
        pathChildrenCache.start();
    }

}
