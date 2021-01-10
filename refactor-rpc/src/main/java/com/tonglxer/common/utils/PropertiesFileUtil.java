package com.tonglxer.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * 配置文件工具类
 * 用于读取(.properties)形式的配置文件
 *
 * @Author Tong LinXing
 * @date 2021/01/10
 */
@Slf4j
public class PropertiesFileUtil {
    /**
     * 工具类隐藏构造函数
     * */
    private PropertiesFileUtil() {
    }

    /**
     * 读取指定的配置文件(.properties)
     *
     * @param fileName 配置文件名
     * @return 配置文件信息
     * */
    public static Properties readPropertiesFile(String fileName) {
        // 1. 获取当前调用类的url
        URL url = Thread.currentThread().getContextClassLoader().getResource("");
        String rpcConfigPath = "";
        if (url != null) {
            // 2. 拼接指定的配置文件路径
            rpcConfigPath = url.getPath() + fileName;
        }
        Properties properties = null;
        try (InputStreamReader inputStreamReader = new InputStreamReader(
                new FileInputStream(rpcConfigPath), StandardCharsets.UTF_8)) {
            properties = new Properties();
            // 3. 加载配置文件信息
            properties.load(inputStreamReader);
        } catch (IOException e) {
            log.error("Read properties file [{}] have some error.", fileName);
        }
        return properties;
    }
}
