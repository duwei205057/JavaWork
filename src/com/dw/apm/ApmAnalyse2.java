package com.dw.apm;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.*;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * 查询数据库，对apm的数据进行统计
 */
public class ApmAnalyse2 {


    public static void main(String[] args) {
        Connection con = null;
        Statement sta = null;
        ResultSet res = null;
        PreparedStatement pr = null;
        try {

            String url = new String("jdbc:mysql://dumpinfo.apm.rds.sogou:3306/sogou_android_apm?characterEncoding=utf8&useSSL=false");
            Class.forName("com.mysql.jdbc.Driver");

            con = (Connection) DriverManager.getConnection(url,"sogou_apm","SogouAndroidApm");
            sta = con.createStatement();
//            String sql = new String("select id,packetname,sdkint from dumpinfo_20210809 where version = '10.31.7' ");
            String sql = new String("select id,packetname,sdkint from dumpinfo_20210814 where version = '10.31.3' and buildversion != 'Build_941f815177_202108111937'");
            String insertSql = "insert into apm_cost (methodId,name,cost,brand,originId) values(?,?,?,?,?)";
            pr = con.prepareStatement(insertSql);
            res = sta.executeQuery(sql);
            int times = 0;
            while(res.next()){
                try {
                    System.out.println("-------------------------------------"+(++times)+"------------------------------------------------");
                    String packetname = res.getString("packetname");
                    int id = res.getInt("id");
                    int sdkint = res.getInt("sdkint");
                    System.out.println("id =" + id + " url =" + packetname);
                    String content = readFileByUrl(packetname);
                    if (content != null && content.length() > 0) {
                        int index = content.indexOf("onComputeListSize=");
                        int end = content.indexOf(" ", index);
                        String num = content.substring(index + 18, end);
                        System.out.println(num);

                        int brandIndex = content.indexOf("&mn=");
                        int brandIndexE = content.indexOf("&", brandIndex + 1);
                        String brand = content.substring(brandIndex + 4, brandIndexE);

                        int machineIndex = content.indexOf("&i=");
                        int machineIndexE = content.indexOf("&", machineIndex + 1);
                        String machine = content.substring(machineIndex + 3, machineIndexE);


                        pr.setInt(1, id);
                        pr.setString(2, machine);
                        pr.setInt(3, Integer.parseInt(num));
                        pr.setString(4, brand);
                        pr.setInt(5, sdkint);
                        pr.executeUpdate();
                    }

//                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

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


    public static String readFileByUrl(String urlStr) {
        String res="";
        int index = urlStr.indexOf("?");
        String name = urlStr.substring(0, index).replaceAll("/","");
        File f = new File("/Users/apple/miscellaneous/python/dump_zip/" + name);
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(30*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            InputStream inputStream = conn.getInputStream();

            FileOutputStream fos = new FileOutputStream(f);
            byte[] buf = new byte[4096];
            int length;
            while ((length = inputStream.read(buf)) > 0) {
                fos.write(buf, 0, length);
            }
            inputStream.close();
            fos.close();
            ZipFile zipFile = new ZipFile(f);
            Enumeration<?> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = (ZipEntry) entries.nextElement();
                System.out.println("解压" + entry.getName());
                // 如果是文件夹，就创建个文件夹
                if ("crash.cr".equals(entry.getName())) {
                    // 将压缩文件内容写入到这个文件中
                    InputStream is = zipFile.getInputStream(entry);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int len;
                    while ((len = is.read(buf)) != -1) {
                        baos.write(buf, 0, len);
                    }
                    res = URLDecoder.decode(new String(baos.toByteArray()));
//                    System.out.println(res);
                    // 关流顺序，先打开的后关闭
                    baos.close();
                    is.close();
                }
            }

        } catch (Exception e) {
            System.out.printf("通过url地址获取文本内容失败 Exception：" + e);
            e.printStackTrace();
        } finally {
            f.delete();
        }
        return res;
    }
}
