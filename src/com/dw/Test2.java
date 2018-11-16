package com.dw;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test2 {

    public static void main(String[] args) {
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
}
