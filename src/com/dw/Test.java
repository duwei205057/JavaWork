package com.dw;

import sun.security.provider.MD5;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test implements Comparable{

    int prop1;
    String prop2 = "abc";
    private long prop3 = 5;
    public static final Point value = new Point();
    private static final long _90DAYS_SECOND_TIME = 90 * 24 * 60 * 60;


    static void a() {
        int i = 0;
        try {
            if(1 == 1)
            throw new Exception("test");
        } catch (Exception e) {
//            e.printStackTrace();
            throw new RuntimeException("get it", e);
        } finally {
            System.out.println("finally");
        }
        if(i == 0)System.out.println("end");
    }

    static void b() {
        a();
//        throw new Exception("test");
    }


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        System.out.println("ab".getClass() == "cd".getClass());
        Exception exception = new Exception("hello");
        exception.setStackTrace(Thread.currentThread().getStackTrace());
        exception.printStackTrace();
        try {
            b();
        } catch (Exception e) {
            e.printStackTrace();
        }

        String str1 = "ja";
        final String str2 = "va";
        String str3 = str1 + str2;
        System.out.println("java" == str3);
        System.out.println("isAssignableFrom="+List.class.isAssignableFrom(ArrayList.class));
        TreeMap tm = new TreeMap();
        tm.put(new Test(), "123");
        tm.put(new Test(), "234");
        System.out.println("Treemap = "+tm);

        System.out.println("------默认相对路径，取得路径不同-----");
        File f = new File("../src/file");
        System.out.println(f.getPath());
        System.out.println(f.getAbsolutePath());
        System.out.println(f.getCanonicalPath());
        f.getParentFile().mkdirs();
        f.createNewFile();
        System.out.println("------默认相对路径，取得路径不同-----");
        File f2 = new File("./src/file");
        System.out.println(f2.getPath());
        System.out.println(f2.getAbsolutePath());
        System.out.println(f2.getCanonicalPath());
        System.out.println("------默认绝对路径，取得路径相同-----");
        f2.getParentFile().mkdirs();
        f2.createNewFile();
        /*File f3 = new File("/src/file");
        System.out.println(f3.getPath());
        System.out.println(f3.getAbsolutePath());
        System.out.println(f3.getCanonicalPath());
        f3.getParentFile().mkdirs();
        f3.createNewFile();*/

        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] result = md.digest("im a key".getBytes());
        System.out.println(result.length);
        String s = byteArrayToHexString(result);
        System.out.println(s+" "+s.length());
        System.out.println(s.getBytes("unicodeLittleUnmarked").length);

        Result r = new Result<String, Integer>();
//        r.show();

        String encode = "adb你好";
        byte[] code1 = encode.getBytes();
        System.out.println(new String(encode.getBytes("GBK"))+" dadf\n的发呆发\tuuuu\n的发呆发 uuuu");
        System.out.print("\t5");

        long currentTime = System.currentTimeMillis();
        System.out.println(currentTime+" :"+ new Date(currentTime)+" -90 :="+ new Date(currentTime - _90DAYS_SECOND_TIME * 1000));


        String iii = "dfe附件诶分解";
        byte[] original = iii.getBytes("GBK");
        String tmp = new String(original);
        System.out.println("tmp == "+tmp);
        byte[] outcome = tmp.getBytes();
        System.out.println("结果:"+Arrays.equals(original, outcome));
        System.out.println("original="+byteArrayToHexString(original)+" outcome=="+byteArrayToHexString(outcome));
        System.out.println("back original="+new String(original,"GBK")+" outcome uft="+new String(outcome,"UTF-8")+" GBK="+new String(outcome,"GBK"));
        HashMap<String,Long> mAverageMethodTime = new HashMap();
        Long l = mAverageMethodTime.get("");
        System.out.println("l= "+l);

        StringBuilder sb = new StringBuilder();
        String sbb = null;
        sb.append(sbb);
        System.out.println(sb.toString());

        Pattern p = Pattern.compile("([0-9|a-z|A-Z]{32}_){2}[0-9|a-z|A-Z]{32}");
        Matcher matcher = p.matcher("ffef3jg4kgfk3kf3kdkwkqwkwkekwkqw_1235698740qwertyuioplkjhgfdsazxc_1235698740qwertyuioplkjhgfdsazxc");
        System.out.println(matcher.matches());
    }

    StringBuilder prop4 = new StringBuilder();

    @Override
    public int compareTo(Object o) {
        return 1;
    }

    public static <T,V> Result<T,V> fromJsonObject(Reader reader, Class<T> clazz) {
        return null;
    }

    public static String byteArrayToHexString(byte[] input){
        if (input == null || input.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (byte b : input){
            String s = Integer.toHexString(b & 0xff);
            System.out.println(s+" "+s.length());
            if (s.length() < 2) s = "0" + s;
            sb.append(s);
        }
        return sb.toString();
    }
}

class P{
    public<k> P(k i){

    }
}
class S extends P{
    public S(){
        super(3);
    }
}
class Result<T,V>{
    Result<T,List<V>>[]  rs;
    Result<T,List<V>>[][]  rss;
    String[] s;
    public void test(List<? extends Result > a){
        System.out.println("enter test function-----------");
    }
    public void show(){
        try {
            /**
             * List<T ? entends>[]：这里的List就是ParameterizedType，T就是TypeVariable，T ? entends就是WildcardType（注意，WildcardType不是Java类型，而是一个表达式），整个List<T ? entends>[]就是GenericArrayType
             */
            System.out.println(Result.class.getDeclaredField("rs").getGenericType().getTypeName());
            System.out.println(Result.class.getDeclaredField("rss").getGenericType().getTypeName());
            System.out.println(Result.class.getDeclaredField("s").getGenericType().getTypeName());
            System.out.println();

            Result.class.getTypeParameters();

            T tt;
            Method method = Result.class.getMethod("test",List.class);
            Type[] upperBounds = null;
            Type[] lowerBounds = null;
            Type[] types = method.getGenericParameterTypes();
            method.invoke(this, null);

            Object[] o = new String[]{"1"};
            //如果类之间存在继承关系的话,数组同样有继承关系
            System.out.println("继承关系:" + (new S[0] instanceof P[]));

            for(Type type : types){
                Type[] actualTypeArgument = ((ParameterizedType)type).getActualTypeArguments();
                for(Type t : actualTypeArgument){
                    WildcardType wildcardType = (WildcardType) t;
                    lowerBounds = wildcardType.getLowerBounds();
                    upperBounds = wildcardType.getUpperBounds();
                    System.out.println("通配符表达式类型是："+ wildcardType);
                    if(upperBounds.length != 0){
                        System.out.println("表达式上边界："+Arrays.asList(upperBounds));
                    }
                    if(lowerBounds.length != 0){
                        System.out.println("表达式下边界："+Arrays.asList(lowerBounds));
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}