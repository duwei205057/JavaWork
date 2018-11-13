package com.dw;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class BufInputStrm_A {
    public static void main(String[] args) throws IOException {
        String s = "ABCDEFGHI@JKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        byte[] buf = s.getBytes();
        ByteArrayInputStream in = new ByteArrayInputStream(buf);
        BufferedInputStream bis = new BufferedInputStream(in, 13);
        int i = 0;
        int k;
        do {
            k = bis.read();
            System.out.print((char) k + " ");
            i++;

            if (i == 4) {
                bis.mark(8);
            }
        } while (k != '@');

        System.out.println();

        bis.reset();
        k = bis.read();
        while (k != -1) {

            System.out.print((char) k + " ");
            k = bis.read();
        }

        System.out.println();

        bis.reset();
        k = bis.read();
        while (k != -1) {
            System.out.print((char) k + " ");
            k = bis.read();

        }

    }
}
