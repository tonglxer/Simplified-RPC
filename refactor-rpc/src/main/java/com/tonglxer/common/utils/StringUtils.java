package com.tonglxer.common.utils;

import java.text.Normalizer;

/**
 * 字符串相关工具类
 *
 * @Author Tong LinXing
 * @date 2020/12/22
 */
public class StringUtils {



    /**
     * IP正则校验
     *
     * @param ip 需要校验的ip
     * @return true为合法ip，反之则然
     */
    public static boolean checkIp(String ip) {
        // 字符串规范化
        String tempStr = Normalizer.normalize(ip, Normalizer.Form.NFKC);
        if ("0.0.0.0".equals(tempStr) || "255.255.255.255".equals(tempStr)) {
            return false;
        }
        String regex = "(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d" +
                "|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])";
        if (tempStr != null) {
            return tempStr.matches(regex);
        }
        return false;
    }

    /**
     * 端口正则校验
     *
     * @param port 需要校验的端口
     * @return true为合法端口，反之则然
     * */
    public static boolean checkPort(String port) {
        String regex = "([1-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])";
        String tempStr = Normalizer.normalize(port, Normalizer.Form.NFKC);
        if (tempStr != null) {
            return tempStr.matches(regex);
        }
        return false;
    }

}
