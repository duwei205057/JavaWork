package com.dw;

import java.io.*;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.locks.ReentrantLock;

public class Test3 {
    public static void main(String[] args) {
        try {
            isANRFileContainIMEInfo(new File("src/emoji.xml"));
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(bao, "UTF-16LE");
//            OutputStreamWriter osw = new OutputStreamWriter(bao, "UTF-16LE");
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("你好");
            bw.close();
            byte[] out = bao.toByteArray();
            for(byte b : out) {
                System.out.println(Integer.toHexString(b & 0xff));
            }

            Date date = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss:S");
            System.out.println(df.format(date));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isANRFileContainIMEInfo(File anrFile) {
        System.out.println(anrFile.getAbsolutePath());
        if (anrFile == null || !anrFile.exists()) {
            return false;
        }
        FileInputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            inputStream = new FileInputStream(anrFile);
            bufferedInputStream = new BufferedInputStream(inputStream);
            byte[] buffer = new byte[1024];
            int len = -1;
            int imeLineNumber = 0;
            int ANR_MAX_IME_LINE_NUMBER = 10;
            StringBuilder stringBuilder = new StringBuilder();
            while((len = bufferedInputStream.read(buffer)) != -1) {
                stringBuilder.append(new String(buffer, 0 , len));
                String tmp = stringBuilder.toString();
                System.out.println(tmp.contains("fadfdfggdgeg"));
                    imeLineNumber ++;
                if (imeLineNumber > ANR_MAX_IME_LINE_NUMBER){
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return false;
    }
}
