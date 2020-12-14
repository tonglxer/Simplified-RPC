package com.tonglxer.proto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 网络传输端点
 *
 * @Author Tong LinXing
 * @date 2020/12/12
 */
@Data
@AllArgsConstructor
public class EndPoint {
    private String host;
    private String port;
}
