package com.dw;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Random;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Random;

public class Test4 {

    public static void main(String[] args) {
        new Test4().begin();
    }

    final int[] VERSIONS = {1627, 1628, 1629, 1630, 1631, 1632, 1633, 1634, 1635, 1636, 1637, 1638, 1639, 1640, 1641, 1642, 1643, 1644, 1645, 1646};
    final String scelDir = "/";
    final String patchDir = "/";
    final String scelSuffix = ".scel";
    final String patchSuffix = ".patch";
    Random mRandom = new Random(200);


    public void begin() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                executeTask();
            }
        }).start();
    }


    private void executeTask() {
        try {
            int j = 20;
            while (j -- > 0) {
                for (int i = 0; i < VERSIONS.length - 1; i++) {
                    String orignalString = scelDir + VERSIONS[i] + scelSuffix;
//                    File orignal = new File(orignalString);
//                    if (!orignal.exists()) {
//                        continue;
//                    }
                    int times = 20;
                    while (times-- > 0) {
                        String scelFileString = scelDir + mRandom.nextInt(200);
//                        copyByTransfer(orignalString, scelFileString);
//                        File scelFile = new File(scelFileString);
//                        if (!scelFile.exists()) {
//                            continue;
//                        }
                        int targetVersion = i + 1 + mRandom.nextInt(VERSIONS.length - i - 1);
                        String patchString = patchDir + VERSIONS[i] + "_" + VERSIONS[targetVersion] + patchSuffix;
                        System.out.println("orignal == " + orignalString+" scel == "+ scelFileString+" patch == "+patchString);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void copyByTransfer(String srcPath, String dstPath) {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel fisChannel = null;
        FileChannel fosChannel = null;
        try {
            fis = new FileInputStream(srcPath);
            fos = new FileOutputStream(dstPath);
            fisChannel = fis.getChannel();
            fosChannel = fos.getChannel();
            long len = fisChannel.transferTo(0, fisChannel.size(), fosChannel);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fisChannel != null) {
                try {
                    fisChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fosChannel != null) {
                try {
                    fosChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
