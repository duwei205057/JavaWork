package com.dw;

import com.sun.javafx.binding.StringFormatter;

import java.io.*;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test2 {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String a = "你";
        String b = a.replaceAll("\\b", "%");
        String c = "你们";
        System.out.println(b);
        Pattern p = Pattern.compile("("+a+")+");
        Matcher m = p.matcher(c);

        if (m.find()) System.out.println(c);
        a = "adbcdg";
        c = "adbc";
//        a = "cadbadbdfadfadbc";
//        c = "adb";
        System.out.println(Arrays.toString(a.split(c, 2)));
        System.out.println(a.startsWith(c));
        System.out.println(a.substring(c.length()));
        System.out.println(a.substring(3));
        System.out.println("====");
        System.out.println("===="+Float.parseFloat("1.2"));
        System.out.println("===="+(int)Float.parseFloat("1.2"));
        System.out.println("===="+new Float("1.2").intValue());
        System.out.println("===="+(int)Float.parseFloat("8"));
        System.out.println("===="+(int)5.6f);
        System.out.println("===="+Integer.parseInt("1"));
        System.out.printf("%02d : text\n", 1);
        System.out.printf("%02d : text\n", 105);
        System.out.printf("%02d : text\n", 45);
        String namePattern = "ABC".replaceAll("\\b", "%");
//        namePattern = namePattern.replaceAll("\\B", "%");
        System.out.println(namePattern);
        matchTheName("你好不好可", "你不");
        matchTheName("你好不好可", "好可");
        matchTheName("你好不好可", "号他");
        System.out.println("adgc fdf    fe.-‘@_/\\/：%#&*$~".matches("[a-zA-Z\\s.\\-‘@_：%#&*\\$~/\\\\]+"));
        short s = (short) 0x8000;
        System.out.println("short s= "+s);
        int i = 0xffff0000;
        System.out.println("int i= "+i);
        i = 0xff000000;
        System.out.println("int i= "+i);
        System.out.println("int i= "+Integer.toHexString(-16777216));

        int a1 = 89;
        int a2 = 308;
        int a3 = a1 ^ a2;
        System.out.println("a3="+a3+" a3^a1="+(a3^a1)+" a3^a2="+(a3^a2));
        System.out.println(0x00000000ffff070cl);
        String em = "\ud83d\ude01";
        String em1 = "\u2763";
        String em2 = "\u20e3";
//        String em2 = "\u0031\ufe0f\u20e3";
        System.out.println("em=="+em+" em2="+Integer.toHexString(em2.codePointAt(0))+"  em1=="+em1+"  em2=="+em2);
        byte[] emb = em2.getBytes();
        for (int j = 0; j < emb.length; j++){
            System.out.println(Integer.toHexString(emb[j] & 0xff));
        }
        char emc = '\ud83d';
        System.out.println(Integer.toHexString(emc));
        /*0==264a
        10-31 22:00:40.061  2029  2029 D xx      : 1==7a7a
        10-31 22:00:40.061  2029  2029 D xx      : 2==d83c
        10-31 22:00:40.061  2029  2029 D xx      : 3==ddee
        10-31 22:00:40.061  2029  2029 D xx      : 4==d83c
        10-31 22:00:40.061  2029  2029 D xx      : 5==ddf9
        10-31 22:00:40.061  2029  2029 D xx      : 6==37
        10-31 22:00:40.061  2029  2029 D xx      : 7==20e3
        10-31 22:00:40.061  2029  2029 D xx      : 8==6765*/
        match("\u264a2\ud83c\uddee\ud83c\uddf9\uD83C\uDDFA\uD83C\uDDF8的\u0037\u20e3来\ud83d\ude01去\u002A\uFE0F\u20E3h\ud83d\uddc4\uFE0FK\ud83e\udd18\ud83c\udffc合\ud83c\udf93\ud83c\udf73\u270d\ud83c\udffc\ud83c\udff3\uFE0F");
        try {
            byte[] aaa = "efe".getBytes("UTF-16");
            for (byte af : aaa) System.out.println((char)af + " " + Integer.toHexString(af & 0xff));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("------------");
        try {
            byte[] aaa = "efe".getBytes("UTF-16LE");
            for (byte af : aaa) System.out.println((char)af + " " + Integer.toHexString(af & 0xff));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("------------");
        try {
            byte[] aaa = "efe".getBytes("UTF-16BE");
            for (byte af : aaa) System.out.println((char)af + " " + Integer.toHexString(af & 0xff));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println("dfdfe".getBytes().length);
        char[] kk = new char[3];kk[0] = 3; kk[1] = 'a'; kk[2] = 'b';
        StringBuilder sb = new StringBuilder();
        for(int ii = 0; ii < kk.length; ii++){
            sb.append(ii).append(":").append((int)kk[ii]).append(";");
        }
        System.out.println(sb);
        HashSet set = new HashSet<>();
//        set.add("a");
//        set.add("a");
//        set.add("b");
        List<String> result = new ArrayList(set);
        String[] tmp = new String[result.size()];
        System.out.println(result.toArray(tmp));
        System.out.println("utf8 还原=" + getString());
        toUtf8();
//        analysisMemSize();

        /*try {
            InputStream is = new FileInputStream("/home/dw/dwcache/111/3043a657d2c376e9d023eec51d9ddf71eabc5522281d53ff105a2ce85ccac972.0");
            int ii = 0;
            int outb;
            while ((outb = is.read()) != -1) {
                ii++;
                if (outb == 0x2C) {
                    int bb = 8;
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        System.out.println((char)24464);
        System.out.println((char)20581);
        System.out.println((char)101);
        System.out.println((char)53);
        System.out.println((char)50);
        System.out.println((char)101);
        System.out.println((2 % 10000) / 1000);
        Random mRandom = new Random(200);
        System.out.println(mRandom.nextInt(1));
//        takeLock.unlock();
    }
    private static void match(String input) {
//        Pattern emoji1 = Pattern.compile("\\ud83c[\\udffb-\\udfff](?=\\ud83c[\\udffb-\\udfff])|" +
//                "(?:" +
//                      "[^\\ud800-\\udfff][\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]?|" +
//                      "[\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]|" +
//                      "(?:\\ud83c[\\udde6-\\uddff]){2}|" +
//                      "[\\ud800-\\udbff][\\udc00-\\udfff]|" +
//                      "[\\ud800-\\udfff]" +
//                ")[\\ufe0e\\ufe0f]?(?:[\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]|" +
//                "\\ud83c[\\udffb-\\udfff])?(?:\\u200d(?:[^\\ud800-\\udfff]|(?:\\ud83c[\\udde6-\\uddff]){2}|[\\ud800-\\udbff][\\udc00-\\udfff])[\\ufe0e\\ufe0f]?(?:[\\u0300-\\u036f\\ufe20-\\ufe23\\u20d0-\\u20f0]|\\ud83c[\\udffb-\\udfff])?)*", Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        String regularExpression = "([\ud83c\udde6-\ud83c\uddff]{2})|" +
                "(([\u00a9-\u00ae]|[\u203c-\u32ff]|[\ud83d\udc00-\ud83d\udfff]|[\ud83e\udd00-\ud83e\udfff])\ufe0f?(\ud83c\udffc)?)|" +
                "([\u0023-\u0039]\ufe0f?\u20e3)|" +
                "(([\ud83c\udc00-\ud83c\udd9a]|[\ud83c\ude00-\ud83c\udffa])\ufe0f?)";
        Pattern emoji1 = Pattern.compile(regularExpression);
        Matcher matcher1 = emoji1.matcher(input);
        while (matcher1.find()) {
            String aboveContext = matcher1.group();
            System.out.println("groupcount==" + matcher1.groupCount() + " s==" + aboveContext);
            for (int i = 0; i < aboveContext.length(); i++) {
                char c = aboveContext.charAt(i);
                System.out.println("***************" + i + "==" + Integer.toHexString(c));
            }
        }
    }

    private static boolean matchTheName(String base, String user) {
        int x = 0; int y = 0;
        char[] source = base.toCharArray();
        char[] target = user.toCharArray();
        while(x < target.length && y < source.length) {
            if (source[y] == target[x]) {
                x ++;
                y ++;
            } else
                y ++;
        }
        boolean r = x == target.length;
        System.out.println("base = "+base+" user="+user+" matches="+r);
        return r;
    }

    private static String getString() throws UnsupportedEncodingException {
//        String s = "\\xE6\\x82\\xA8\\xE5\\xB0\\x86\\xE6\\x94\\xB6\\xE5\\x88\\xB0\\xE5\\x9C\\xB0\\xE7\\x90\\x86\\xE4\\xBD\\x8D\\xE7\\xBD\\xAE\\xE6\\x9D\\x83\\xE9\\x99\\x90\\xE7\\x9A\\x84\\xE7\\xB3\\xBB\\xE7\\xBB\\x9F\\xE6\\x8F\\x90\\xE7\\xA4\\xBA\\xEF\\xBC\\x8C\\xE8\\xAF\\xB7\\xE6\\x94\\xBE\\xE5\\xBF\\x83\\xE9\\x80\\x89\\xE6\\x8B\\xA9\\xE5\\x85\\x81\\xE8\\xAE\\xB8\\xE3\\x80\\x82\\xE8\\xBE\\x93\\xE5\\x85\\xA5\\xE6\\xB3\\x95\\xE4\\xBC\\x9A\\xE4\\xB8\\xBA\\xE6\\x82\\xA8\\xE6\\x8F\\x90\\xE4\\xBE\\x9B\\xE6\\x89\\x80\\xE5\\x9C\\xA8\\xE5\\x9C\\xB0\\xE7\\x9A\\x84\\xE5\\xB8\\xB8\\xE7\\x94\\xA8\\xE8\\xAF\\x8D\\xE6\\xB1\\x87\\xE3\\x80\\x82";
//        String s = "\\xE2\\x80\\x9C\\xE6\\x99\\xBA\\xE8\\x83\\xBD\\xE5\\x9B\\x9E\\xE5\\xA4\\x8D\\xE2\\x80\\x9D\\xE5\\x8A\\x9F\\xE8\\x83\\xBD\\xE9\\x9C\\x80\\xE8\\xA6\\x81\\xE5\\xBC\\x80\\xE5\\x90\\xAF\\xE6\\x97\\xA0\\xE9\\x9A\\x9C\\xE7\\xA2\\x8D\\xE6\\x9D\\x83\\xE9\\x99\\x90";

        String s = "\\xE4\\xBB\\x8A\\xE5\\xB9\\xB4\\xE5\\xA4\\xA7\\xE5\\x90\\x89\\xE5\\xA4\\xA7\\xE5\\x88\\xA9";
//        String s = "\\xF0\\x9F\\x8E\\x8A\\xE7\\xA5\\x9D\\xE4\\xBD\\xA0\\xE5\\xB9\\xB4\\xE5\\xB9\\xB4\\xE7\\x9A\\x86\\xE8\\x83\\x9C\\xE6\\x84\\x8F";
        String[] ss = s.split("\\\\x");
        byte[] b = new byte[ss.length - 1];
        for(int i = 0; i < b.length; i++) {
            b[i] = (byte) (Integer.parseInt(ss[i + 1], 16) & 0xff);
        }
//        return new String(b, "iso-8859-1");
        return new String(b, "UTF-8");
    }

    private static void toUtf8() {
        char textChar = 200;
        String text = new String(new char[]{textChar});

        try {
            System.out.print(text.getBytes("UTF-8").length + "\t");
            System.out.println(bytesToHexString(text.getBytes("UTF-8")));

            System.out.print(text.getBytes("GBK").length + "\t");
            System.out.println(bytesToHexString(text.getBytes("GBK")));

            System.out.print(text.getBytes("GB2312").length + "\t");
            System.out.println(bytesToHexString(text.getBytes("GB2312")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    public static final String bytesToHexString(byte[] bArray) {
        StringBuffer sb = new StringBuffer(bArray.length);
        String sTemp;
        for (int i = 0; i < bArray.length; i++) {
            sTemp = Integer.toHexString(0xFF & bArray[i]);
            if (sTemp.length() < 2)
                sb.append(0);
            sb.append(sTemp.toUpperCase());
        }
        return sb.toString();
    }

    private static void analysisMem() {
        try {
            File f = new File("/home/dw/a.txt");
            System.out.println("a.txt exists ="+f.exists());
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            String lastaddr = null, lastfunc = null;
            HashMap<String, Integer> hm = new HashMap();
            while((line = br.readLine()) != null) {
                int addrIndex = line.indexOf("addr=");
                if (addrIndex < 0) continue;
                addrIndex += "addr=".length();
                String addr = line.substring(addrIndex, addrIndex + 8);
                int funcIndex = line.indexOf("func=") + "func=".length();
                String func = line.substring(funcIndex, funcIndex + 8);
                if (lastaddr != null && !lastaddr.equals(addr)) {
                    if (hm.containsKey(lastfunc)) {
                        int count = hm.get(lastfunc);
                        hm.put(lastfunc, count + 1);
                    } else {
                        hm.put(lastfunc, 1);
                    }
                }
                lastaddr = addr;
                lastfunc = func;
            }
            br.close();
            List<CountObj> l = new ArrayList();
            for (Map.Entry<String, Integer> entry : hm.entrySet()) {
                l.add(new CountObj(entry.getValue(), entry.getKey()));
            }
            Collections.sort(l);
            File out = new File("/home/dw/a_out.txt");
            FileWriter fw = new FileWriter(out);
            StringBuilder sb = new StringBuilder();
            for (CountObj countObj : l) {
                Process process = Runtime.getRuntime().exec(String.format("addr2line -i -f -e /home/dw/workspace/SogouInput/app/build/intermediates/cmake/debug/obj/armeabi-v7a/libsogouime.so %s", countObj.name));
                InputStream inputStream = process.getInputStream();
                br = new BufferedReader(new InputStreamReader(inputStream));
                sb.setLength(0);
                while ((line = br.readLine()) != null) {
                    sb.append(line+"\n");
                }
                br.close();
                countObj.stack = sb.toString();
                fw.write("---------------------------" + countObj.name + ":" + countObj.count + "\n");
                fw.write("stack:" + countObj.stack);
            }
            fw.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void analysisMemSize() {
        try {
            File f = new File("/home/dw/b.txt");
            System.out.println("b.txt exists ="+f.exists());
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            String lastaddr = null, lastfunc = null;
            HashMap<String, Integer> hm = new HashMap();
            int total = 0;
            int lineNo = 0;
            while((line = br.readLine()) != null) {
                lineNo ++;
                int startIndex = line.indexOf("size = ");
                if (startIndex < 0) continue;
                String addr = line.substring(startIndex - 11, startIndex - 1);
                String oldaddr = line.substring(startIndex - 22, startIndex - 12);
                startIndex += "size = ".length();
                int endIndex = line.indexOf("malloc by");
                String mem = line.substring(startIndex, endIndex);
                if (lastaddr != null && !lastaddr.equals(addr)) {
                    int size = Integer.parseInt(mem.trim());
                    if ("[00000000]".equals(oldaddr)) {
                        total += size;
                        hm.put(addr, size);
                    } else {
                        int oldSize = 0;
                        if (hm.containsKey(oldaddr))
                            oldSize = hm.remove(oldaddr);
                        System.out.println(oldaddr+" "+ lineNo+" oldsize="+oldSize+" newsize="+size);
                        hm.put(addr, size);
                        total += (size - oldSize);
                    }

                }
                lastaddr = addr;
            }
            br.close();
            System.out.println("total ===="+total);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class CountObj implements Comparable<CountObj>{
        int count;
        String name;
        String stack;

        public CountObj(int count, String name) {
            this.count = count;
            this.name = name;
        }

        @Override
        public int compareTo(CountObj o) {
            return o.count - count;
        }

        public String toString() {
            return name + ":" + count + ":" + stack + ";";
        }
    }
}
