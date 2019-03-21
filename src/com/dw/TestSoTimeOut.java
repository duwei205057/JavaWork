package com.dw;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TestSoTimeOut {
    public static void main(String[] args) {
        try {
            URL url = new URL("https://dl.google.com/dl/android/studio/install/3.3.1.0/android-studio-ide-182.5264788-windows.exe");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            System.out.println(conn.getResponseCode());
            long startGetInputStream = System.currentTimeMillis();
            InputStream is  = conn.getInputStream();
            System.out.println("getInputStream cost time ="+(System.currentTimeMillis() - startGetInputStream));
            File f = new File("../src/downfile");
            OutputStream os = new FileOutputStream(f);
            byte[] buffer = new byte[8192];
            long readTime = 0;
            for(;;) {
                long startRead = System.currentTimeMillis();
                int data = is.read();
                if  (data == -1) break;
                readTime += (System.currentTimeMillis() - startRead);
                os.write(data);
                System.out.println("total cost = "+readTime);
            }
            is.close();
            os.close();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
