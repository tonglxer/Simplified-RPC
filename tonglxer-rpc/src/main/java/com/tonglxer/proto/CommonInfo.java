package com.tonglxer.proto;

import com.tonglxer.common.constant.RPCConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author Tong LinXing
 * @date 2020/12/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonInfo {
    // 魔数标记为RPC请求或响应
    private String magicNumber = RPCConstant.MagicNumber;

    private String serialID;

    private String type;
}
