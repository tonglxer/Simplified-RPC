package com.tonglxer.proto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 响应信息
 *
 * @Author Tong LinXing
 * @date 2020/12/12
 */
@Data
@AllArgsConstructor
public class RPCResponse {
    /**
     * code:
     * 0： 成功
     * 其他表示失败
     *
     * */
    private int code = 0;
    private String message = "success";
    private Object data;
}
