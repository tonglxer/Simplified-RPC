package com.tonglxer.proto;

import com.tonglxer.common.constant.RPCConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Tong LinXing
 * @date 2020/12/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonMessage {
    // 魔数：用于标识为RPC信息
    private int magicNumber = RPCConstant.RPC_MARK;

    // 请求序号
    private String requestID;

    // 序列化方式
    private int serializeType = RPCConstant.FASTJSON_SERIALIZE;

    // 传输方式
    private int transportType = RPCConstant.SOCKET_TRANSPORT;
}
