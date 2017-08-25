package utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangshengchen on 2017/8/25.
 */
public class UserIpUtil {
    public static String getIPAddress(HttpServletRequest request) {
        String ip = filterIp(request,"x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = filterIp(request,"Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = filterIp(request,"WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        Integer commaIndex = ip.indexOf(",");
        if(commaIndex > 0)
            ip = ip.substring(0,commaIndex);
        return ip;
    }

    private static String filterIp(HttpServletRequest request, String key){
        String ip = request.getHeader(key);
        if(StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip))
            return ip;
        if(ip.indexOf(",") > 0){
            String[] ips = StringUtils.split(ip, ",");
            for (String sip : ips) {
                if(StringUtils.isNotEmpty(sip) && checkIp(sip))
                    return sip;
            }
            return null;
        }
        if(checkIp(ip))
            return ip;
        return null;
    }
    public static boolean checkIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            return false;
        }
        String[] ips = ip.split("\\.");
        if (ips.length < 2) {
            return false;
        }
        for (String temp : ips) {
            if (!StringUtils.isNumeric(temp)) {
                return false;
            }
            if (Integer.valueOf(temp).intValue() > 255) {
                return false;
            }
        }
        return true;
    }
}
