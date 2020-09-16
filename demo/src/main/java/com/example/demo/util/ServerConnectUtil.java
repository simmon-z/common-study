package com.example.demo.util;

import com.example.demo.vo.ServerVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @program: demo
 * @description: 测试服务器是否能连接
 * @author: simmonZ
 * @create: 2020-09-08 15:08
 */

public class ServerConnectUtil {
    static Logger logger = LoggerFactory.getLogger(ServerConnectUtil.class);

    public static boolean isReachable(String ip, String port, int timeout) {
        boolean reachable = false;
        // 如果端口为空，使用 isReachable 检测，非空使用 socket 检测
        if (port == null) {
            try {
                InetAddress address = InetAddress.getByName(ip);
                reachable = address.isReachable(timeout);
            } catch (Exception e) {
                logger.error(e.getMessage());
                reachable = false;
            }
        } else {
            Socket socket = new Socket();
            try {
                socket.connect(new InetSocketAddress(ip, Integer.parseInt(port)), timeout);
                reachable = true;
            } catch (Exception e) {
                logger.error(e.getMessage());
                reachable = false;
            } finally {
                try {
                    if (socket != null) socket.close();
                } catch (Exception e) {
                }
            }
        }
        return reachable;
    }

    public static void main(String[] args) {
        boolean result=ServerConnectUtil.isReachable("111.22.149.51","9811",10000);
        try {
            String path= ResourceUtils.getURL("classpath:").getPath();
            System.out.println(path);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }
}
