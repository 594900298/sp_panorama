package com.example.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 域名
 */
public class DomainUtil {
    public static String getCurrentDomain(HttpServletRequest request) {
        // 获取请求的协议（http或https）
        String scheme = request.getScheme();
        // 获取当前服务的域名
        String serverName = request.getServerName();
        // 获取当前服务的端口号
        int serverPort = request.getServerPort();
        // 构建完整的域名，包括协议、域名和端口号
        StringBuilder domain = new StringBuilder();
        domain.append(scheme).append("://").append(serverName);
        if ((scheme.equals("http") && serverPort != 80) || (scheme.equals("https") && serverPort != 443)) {
            domain.append(":").append(serverPort);
        }
        return domain.toString();
    }
}
