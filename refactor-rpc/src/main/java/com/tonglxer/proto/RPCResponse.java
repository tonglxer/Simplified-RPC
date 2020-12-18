package com.tonglxer.proto;

import com.tonglxer.common.constant.RPCConstant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * RPC 响应
 *
 * @Author Tong LinXing
 * @date 2020/12/18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RPCResponse {
    // RPC基本信息
    private CommonMessage rpcInfo;

    // 响应码
    private int code;

    // 响应信息
    private String message;

    // 返回的数据
    private Object data;

    /**
     * 生成成功响应信息
     *
     * @param rpcInfo rpc基本信息
     * @param data 返回的调用结果
     * @return 成功响应
     * */
    public static RPCResponse getSuccessResponse(CommonMessage rpcInfo, Object data) {
        RPCResponse response = RPCResponse.builder()
                .rpcInfo(rpcInfo).code(RPCConstant.SUCCESS_CODE)
                .message(RPCConstant.SUCCESS_MESSAGE)
                .data(data).build();
        return response;
    }

    /**
     * 生成成功响应信息
     *
     * @param rpcInfo rpc基本信息
     * @param message 失败信息
     * @return 失败响应
     * */
    public static RPCResponse getFailResponse(CommonMessage rpcInfo, String message) {
        RPCResponse response = RPCResponse.builder()
                .rpcInfo(rpcInfo).code(RPCConstant.FAIL_CODE)
                .message(message).build();
        return response;
    }
}
