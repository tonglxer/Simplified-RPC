package com.tonglxer;

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
public class Peer {
    private String host;
    private String port;
}
