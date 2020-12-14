package com.tonglxer.transport;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * 处理网络请求
 *
 * @Author Tong LinXing
 * @date 2020/12/13
 */
public interface RequestHandler {
    void onRequest(InputStream receive, OutputStream to);
}
