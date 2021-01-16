package com.tonglxer.common.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
    private static String PROPERTIES_FILE_NAME = "rpc.properties";

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

    /**
     * 获取默认的配置文件指定项的值
     *
     * @param key 指定的配置项
     * @return 对应的属性值
     */
    public static String getProperties(String key) {
        Properties properties = new Properties();
        // 使用ClassLoader加载properties配置文件生成对应的输入流
        try (InputStream in = PropertiesFileUtil.class
                .getClassLoader()
                .getResourceAsStream(PROPERTIES_FILE_NAME)) {
            // 使用properties对象加载输入流
            properties.load(in);
        } catch (IOException e) {
            log.error("Get {} have some error.", key);
        }
        //获取key对应的value值
        return properties.getProperty(key);
    }
}
