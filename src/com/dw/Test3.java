package com.dw;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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


        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update("adc".getBytes());//md.getDigest() 输出1
            md.update("def".getBytes());//md.getDigest() 输出1
            md.update("adc".getBytes());//md.getDigest() 输出1
            System.out.println(bytesToHexString(md.digest()));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        uuid();
    }

    public static String bytesToHexString(byte[] bArr) {
        StringBuffer sb = new StringBuffer(bArr.length);
        String sTmp;

        for (int i = 0; i < bArr.length; i++) {
            sTmp = Integer.toHexString(0xFF & bArr[i]);
            if (sTmp.length() < 2)
                sb.append(0);
            sb.append(sTmp.toUpperCase());
        }

        return sb.toString();
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


    private static void uuid() {
        byte[] data = new byte[]{117,117,105,100,61,72,2,-17,-65,-67,37,36,-17,-65,-67,-17,-65,-67,-54,-106,-17
                ,-65,-67,36,43,-17,-65,-67,64,42,74,72,5,-17,-65,-67,21,99,-17,-65,-67,-23,-95,-89,88,105,-17,-65,-67,-17,-65,-67,-17,-65,-67,44,74};

//        [117 117 105 100 61 72 2 239 191 189 37 36 239 191 189 239 191 189 202 150 239 191 189 36 43 239 191 189
//        64 42 74 72 5 239 191 189 21 99 239 191 189 233 161 167 88 105 239 191 189 239 191 189 239 191 189 44 74]
        try {
            System.out.println(new String(data, "UTF-8"));
            System.out.println(new String(data, "GBK"));
            System.out.println(bytesToHexString(data));


            System.out.println(bytesToHexString("uuid=H\u0002�%$��ʖ�$+�@*JH\u0005�\u0015c�顧Xi���,J".getBytes()));
            System.out.println(Arrays.toString("705ce31c29c5b469a6ee73d13f4ea2a8".getBytes()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
