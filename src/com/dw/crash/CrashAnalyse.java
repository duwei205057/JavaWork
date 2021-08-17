package com.dw.crash;


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
public class CrashAnalyse {


    public static void main(String[] args) {
        Connection con = null;
        Statement sta = null;
        ResultSet res = null;
        PreparedStatement pr = null;
        try {

            String url = new String("jdbc:mysql://dumpinfo.crash.rds.sogou:3306/crash_dump_info?characterEncoding=utf8&useSSL=false");
            Class.forName("com.mysql.jdbc.Driver");

            con = (Connection) DriverManager.getConnection(url,"CrashUser","Data2019Sogou");
            sta = con.createStatement();
            String sql = new String("select id,packetname from crash_dump_info.dumpinfo_20210813 where version = '10.31.3' and type = 0 and stack like '%Lsogou/pingback/R$string%' ");
            res = sta.executeQuery(sql);
            int times = 0;
            int find = 0;
            while(res.next()){
                try {
                    System.out.println("-------------------------------------"+(++times)+"------------------------------------------------");
                    String packetname = res.getString("packetname");
                    int id = res.getInt("id");
                    System.out.println("id =" + id + " url =" + packetname);
                    String content = readFileByUrl(packetname);
//                    if (content.contains("||||buildId=Build_941f815177_202108111937")) {
                    if (content.contains("||||buildId=Patch_db5d1bc5a6_202108131733")) {
                        find ++;
                    }

                    int index = content.indexOf("||||buildId=");
                    int end = content.indexOf("||||", index + 4);
                    String num = content.substring(index + 12, end);
                    System.out.println(num);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("find ------------------"+find);
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
                    res = new String(baos.toByteArray());
//                    res = URLDecoder.decode(new String(baos.toByteArray()));
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
