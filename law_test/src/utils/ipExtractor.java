package utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zjm97 on 2019/5/22.
 */
public class ipExtractor {
    public  static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 ||"unknown".equalsIgnoreCase(ip)) {
           ip = request.getRemoteAddr();
        }
        return ip;
        }
}
