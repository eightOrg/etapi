package com.eight.trundle.db.sqlExcute;

import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.sql.*;
import java.util.List;
import java.util.Properties;

/**
 * Created by miaoch on 2016/8/24.
 */
public class DBHelper {
    private static boolean isDebug = false;
    private static String jdbc_driverClassName;
    private static String jdbc_url;
    private static String jdbc_username;
    private static String jdbc_password;
    static {
        //读取配置文件设置常量
        Properties prop = new Properties();
        try {
            InputStream in = DBHelper.class.getClassLoader().getResourceAsStream("jdbc.properties");
            prop.load(in);
            jdbc_driverClassName = prop.getProperty( "jdbc_driverClassName" ).trim();
            jdbc_url = prop.getProperty( "jdbc_url" ).trim();
            jdbc_username = prop.getProperty( "jdbc_username" ).trim();
            jdbc_password = prop.getProperty( "jdbc_password" ).trim();
        } catch  (IOException e) {
            e.printStackTrace();
        }
        //判断是否处于debug状态
        List<String> args = ManagementFactory.getRuntimeMXBean().getInputArguments();
        for (String arg : args) {
            if (arg.startsWith("-Xrunjdwp") || arg.startsWith("-agentlib:jdwp")) {
                isDebug = true;
                break;
            }
        }
    }

    private static Connection getConnection(){
        Connection con = null;
        try {
            Class.forName(jdbc_driverClassName);//指定连接类型
            con = DriverManager.getConnection(jdbc_url, jdbc_username, jdbc_password);//获取连接
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }

    /**
     * 记录api请求响应时间 debug状态不统计
     * @param time 响应时间
     * @param path  请求路径
     * @param value 请求参数
     * @param method 请求方法
     */
    public static void recRequestTime(long time, String path, String value, String method) {
        Connection con = getConnection();
        PreparedStatement pst = null;
        if (!isDebug && con != null) {
            String sql = "insert et_api_record (path, useTime, value, method)\n " +
                    "values\n " +
                    "\t('" + path + "', '" + time + "', '" + value + "', '" + method + "');";
            try {
                pst = con.prepareStatement(sql);
                System.out.print(sql);
                pst.executeUpdate();
            }  catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
