package com.tonglxer.proto;

import com.tonglxer.common.constant.RPCResponseEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
public class RPCResponse {
    private CommonInfo commonInfo;
    // 状态码，默认成功
    private int code = RPCResponseEnum.SUCCESS.getCode();
    // 响应信息，默认成功
    private String message = RPCResponseEnum.SUCCESS.getMessage();
    // 响应体
    private Object data;
}
