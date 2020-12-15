package com.tonglxer.proto;

import com.tonglxer.common.constant.RPCConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应信息
 *
 * @Author Tong LinXing
 * @date 2020/12/12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RPCResponse {
    // 状态码，默认成功
    private int code = RPCConstant.SUCCESS;
    // 响应信息，默认成功
    private String message = RPCConstant.SUCCESS_MESSAGE;
    // 响应体
    private Object data;
}
