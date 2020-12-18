package com.tonglxer.proto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Tong LinXing
 * @date 2020/12/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonMessage {
    // 魔数：用于标识为RPC请求或响应
    private int magicNumber;

    // 请求序号
    private String requestID;

    // 序列化方式
    private String serializeType;

    // 传输方式
    private String transportType;
}
