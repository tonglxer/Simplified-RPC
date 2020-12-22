package com.tonglxer.registry.zk;

import com.tonglxer.common.constant.RPCConstant;
import com.tonglxer.common.exception.RPCException;
import com.tonglxer.common.exception.RPCTransportException;
import com.tonglxer.common.utils.CuratorUtils;
import com.tonglxer.common.utils.StringUtils;
import com.tonglxer.registry.RPCServiceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * 基于Zookeeper实现注册中心
 *
 * @Author Tong LinXing
 * @date 2020/12/22
 */
@Slf4j
public class ZookeeperServiceRegistry implements RPCServiceRegistry {

    @Override
    public void register(String serviceName, InetSocketAddress endPoint) {
        String ip = endPoint.getHostName();
        String port = String.valueOf(endPoint.getPort());
        // ip和端口的合法性校验
        if (!StringUtils.checkIp(ip)) {
            log.error("The ip [{}] set is illegal.", ip);
            return;
        }
        if (!StringUtils.checkPort(port)) {
            log.error("The port [{}] set is illegal.", port);
            return;
        }
        // ex: /tonglxer-rpc/serviceName/127.0.0.01:2181
        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH +
                System.lineSeparator() + serviceName + endPoint.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        // 添加永久节点以注册服务及服务器地址
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, serviceName);
        if (serviceUrlList == null || serviceUrlList.size() == 0) {
            log.error("Failed to get server address of service: [{}].", serviceName);
            throw new RPCException("Failed to get server address of service: " + serviceName);
        }
        // TODO: 2020/12/22 添加负载均衡
        String targetServiceUrl = serviceUrlList.get(0);
        log.info("Successfully found the service address: [{}]", targetServiceUrl);
        // 通过字符串分割获取ip和端口
        String[] socketAddressArray = targetServiceUrl.split(":");
        // 再次校验ip和端口的合法性
        if (StringUtils.checkIp(socketAddressArray[0])
                && StringUtils.checkPort(socketAddressArray[1])) {
            String host = socketAddressArray[0];
            int port = Integer.parseInt(socketAddressArray[1]);
            return new InetSocketAddress(host, port);
        } else {
            log.error("Please check that the registered service [{}] address is valid.", serviceName);
            throw new RPCException("Please check that the registered service address is valid.");
        }
    }
}
