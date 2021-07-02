package com.dw;


import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 获取指定url中的内容
 */

public class UrlContent {

    public static void main(String[] args) {
        readFileByUrl("http://10.143.62.141/20200519.log.instant");
    }

    public static String readFileByUrl(String urlStr) {
        String res=null;
        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            //设置超时间为3秒
            conn.setConnectTimeout(3*1000);
            //防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            //得到输入流
            InputStream inputStream = conn.getInputStream();
            res = readInputStream(inputStream);
        } catch (Exception e) {
            System.out.printf("通过url地址获取文本内容失败 Exception：" + e);
        }
        return res;
    }

    /**
     * 从输入流中获取字符串
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        FileOutputStream fos = new FileOutputStream("/home/dw/桌面/backup/无法输入/10.9.8.20200519.log.instant");
        while((len = inputStream.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }
        fos.close();
        return "";
    }

}
