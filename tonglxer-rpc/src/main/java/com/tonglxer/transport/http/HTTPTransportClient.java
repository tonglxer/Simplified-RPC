package com.tonglxer.transport.http;

import com.tonglxer.proto.EndPoint;
import com.tonglxer.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * 基于HTTP的实现
 *
 * <note>http是短连接 不需要手动关闭</note>
 *
 * @Author Tong LinXing
 * @date 2020/12/13
 */
@Slf4j
public class HTTPTransportClient implements TransportClient {
   private String url;

    @Override
    public void connect(EndPoint endPoint) {
        this.url = "http://" + endPoint.getHost() + ":" + endPoint.getPort();
    }

    @Override
    public InputStream write(InputStream data) {
        try {
            URLConnection urlConnection = new URL(url).openConnection();
            if (urlConnection instanceof HttpURLConnection) {
                HttpURLConnection httpURLConnection = (HttpURLConnection)urlConnection;
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.connect();
                IOUtils.copy(data, httpURLConnection.getOutputStream());

                int code = httpURLConnection.getResponseCode();
                if (code == HttpURLConnection.HTTP_OK) {
                    return httpURLConnection.getInputStream();
                } else {
                    return httpURLConnection.getErrorStream();
                }
            } else {
                log.error("The connection type is not HTTP.");
            }
        } catch (IOException e) {
            log.error("HTTPTransportClient write have some error.");
        }
        return null;
    }

    @Override
    public void close() {

    }
}
