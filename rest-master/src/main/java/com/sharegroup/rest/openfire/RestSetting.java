package com.sharegroup.rest.openfire;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 配置文件
 * Created by xiangxj on 2015/12/7.
 */
public class RestSetting {

    // 允许访问IP白名单
    public static List<String> IP_WHITELIST = new ArrayList<String>();

    public static String SERVICE_ADMIN;

    public static Integer SERVICE_ADMIN_PORT;

    public static String SERVICE_HOST;

    public static String SERVICE_NAME;

    public static Integer SERVICE_PORT;

    public static Boolean SERVICE_ISDEBUG;

    public static String AUTH_USERNAME;
    public static String AUTH_PASSWORD;

    static {
        reload();
    }

    public static void reload() {
        try {
            Configuration config = new PropertiesConfiguration("config/sharegroup_rest.properties");

            String path = config.getClass().getClassLoader().getResource("").getPath();
            // 允许访问IP白名单
            String[] ips = config.getString("IP_WHITELIST").split("\\|");
            for (String ip : ips) {
                if (isIPAddress(ip)) {
                    IP_WHITELIST.add(ip);
                }
            }
            //admin console
            SERVICE_ADMIN = config.getString("SERVICE_ADMIN");
            SERVICE_ADMIN_PORT = config.getInteger("SERVICE_ADMIN_PORT",9090);
            // openfire service
            SERVICE_HOST = config.getString("SERVICE_HOST");
            //default service name is host
            SERVICE_NAME = config.getString("SERVICE_NAME",SERVICE_HOST);
            //openfire defautl port 5222
            SERVICE_PORT = config.getInt("SERVICE_PORT", 5222);

            SERVICE_ISDEBUG = config.getBoolean("SERVICE_ISDEBUG", false);

            //openfire admin
            AUTH_USERNAME = config.getString("authenticationToken.username");
            AUTH_PASSWORD = config.getString("authenticationToken.password");

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 验证IP地址格式
     *
     * @param ipaddr 需要验证的IP地址
     * @return true：格式正确   false：格式不正确
     */
    private static boolean isIPAddress(String ipaddr) {
        boolean flag = false;
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        Matcher m = pattern.matcher(ipaddr);
        flag = m.matches();
        return flag;
    }

    /**
     * 检查IP是否在白名单中
     *
     * @param ipaddr 被检查的IP地址
     * @return
     */
    public static boolean isIPAddressWhite(String ipaddr) {
        return IP_WHITELIST.contains(ipaddr);
    }

    public static void main(String[] args) {
    }
}
