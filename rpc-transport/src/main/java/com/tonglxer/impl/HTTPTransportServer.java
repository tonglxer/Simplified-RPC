package com.tonglxer.impl;

import com.tonglxer.RequestHandler;
import com.tonglxer.TransportServer;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Author Tong LinXing
 * @date 2020/12/13
 */
@Slf4j
public class HTTPTransportServer implements TransportServer {

    private RequestHandler handler;

    private Server server;// jetty

    @Override
    public void init(int port, RequestHandler handler) {
        this.handler = handler;
        this.server = new Server(port);

        // servlet 接收请求
        ServletContextHandler ctx = new ServletContextHandler();
        server.setHandler(ctx);

        ServletHolder holder = new ServletHolder(new RequestServlet());
        ctx.addServlet(holder, "/*");

    }

    @Override
    public void start() {
        try {
            server.start();
            server.join();// 线程阻塞直至完成
        } catch (Exception e) {
            log.error("HTTPTransportServer start have some error.");
        }
    }

    @Override
    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            log.error("HTTPTransportServer stop have some error.");
        }
    }

    private class RequestServlet extends HttpServlet {
        @Override
        protected void doPost(HttpServletRequest req,
                              HttpServletResponse resp)
                throws ServletException, IOException {
            InputStream in = req.getInputStream();
            OutputStream out = resp.getOutputStream();

            if (handler != null) {
                handler.onRequest(in, out);
            }

            out.flush();
        }
    }
}
