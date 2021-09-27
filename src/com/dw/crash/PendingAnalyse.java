package com.dw.crash;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 查询数据库，对apm的数据进行统计
 */
public class PendingAnalyse {


    public static void main(String[] args) {
        Connection con = null;
        Statement sta = null;
        ResultSet res = null;
        PreparedStatement pr = null;
        try {
            int post = 0;
            int hidden = 0;
            int finish = 0;
            int start = 0;
            int pending = 0;
            int run = 0;
            int remove = 0;
            int destroy = 0;

            String url = new String("jdbc:mysql://dumpinfo.crash.rds.sogou:3306/crash_dump_info?characterEncoding=utf8&useSSL=false");
            Class.forName("com.mysql.jdbc.Driver");

            con = (Connection) DriverManager.getConnection(url,"CrashUser","Data2019Sogou");
            sta = con.createStatement();
            String sql = new String("select stack from crash_dump_info.dumpinfo_20210926 where version = '10.31.3' and stack like '%IllegalArgumentException: post%' ");
            res = sta.executeQuery(sql);
            int times = 0;
            while(res.next()){
                try {
                    System.out.println("-------------------------------------"+(++times)+"------------------------------------------------");
                    String stack = res.getString("stack");
                    System.out.println("-------------------------------------"+stack+"------------------------------------------------");
                    String startString = "IllegalArgumentException: ";
                    int index = stack.indexOf(startString);
                    int end = stack.indexOf(";");
                    String nums = stack.substring(index + startString.length(), end);
                    System.out.println(nums);
                    String numString[] = nums.split(":");
                    post += Integer.parseInt(numString[1]);
                    hidden += Integer.parseInt(numString[3]);
                    finish += Integer.parseInt(numString[5]);
                    start += Integer.parseInt(numString[7]);
                    pending += Integer.parseInt(numString[9]);
                    run += Integer.parseInt(numString[11]);
                    remove += Integer.parseInt(numString[13]);
                    destroy += Integer.parseInt(numString[15]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String content = "post:" + post + ":hidden:" + hidden + ":finish:" + finish
                    + ":start:" + start + ":pending:" + pending + ":run:" + run + ":remove:" + remove + ":destroy:" + destroy;
            System.out.println(content);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (sta != null) {
                    sta.close();
                }
                if (res != null) {
                    res.close();
                }
                if (pr != null) {
                    pr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }


}
