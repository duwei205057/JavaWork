package com.dw.pingback;

import org.json.JSONObject;

import java.io.*;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class KeyboardShow {

    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            File f = new File("/Users/apple/Desktop/_col1");

            FileReader fr = new FileReader(f);
            br = new BufferedReader(fr);
            Map<String, Integer> map = new TreeMap<String, Integer>(new Comparator<String>() {

                @Override
                public int compare(String o1, String o2) {
                    int l1 = o1.length();
                    int l2 = o2.length();
                    if (l1 != l2) {
                        return l1 - l2;
                    } else {
                        return o1.compareTo(o2);
                    }
                }
            });
            String line;
            int i = 0;
            int j = 0;
            while ((line = br.readLine()) != null) {
                line = line.replaceAll("\\\\x22", "");
                line = line.replaceAll("#", "-");
//                System.out.println(line);
                i ++;

                String key = "st_keyboard_time_dist:";
                int index = line.indexOf(key);
                if (index < 0) {
                    continue;
                }
                int endIndex = line.indexOf("}",index);
                String v = line.substring(index + key.length(), endIndex + 1);
//                System.out.println(v);
                JSONObject jsonObject = new JSONObject(v);
                for (String k : jsonObject.keySet()) {
                    int a = jsonObject.optInt(k);
                    if (map.containsKey(k)) {
                        int b = map.get(k);
                        map.put(k, a + b);
                    } else {
                        map.put(k, a);
                    }
                }
                System.out.println("------------------------"+i+"--------------------------");



//                if (i > 5) {
//                    break;
//                }
            }
            System.out.println(map);

            int total = 0;
            int totalTime = 0;
            for (String k : map.keySet()) {
                int a = map.get(k);
                int tab = Integer.parseInt(k.substring(3));
                totalTime += tab * a;
                total += a;
            }
            int tp90 = (int) (total * 0.9);
            int tp70 = (int) (total * 0.7);
            int tp50 = (int) (total * 0.5);
            System.out.println("total ==============" + total + "   tp90 ======"+ tp90);

            for (String k : map.keySet()) {
                if (tp90 > 0) {
                    tp90 -= map.get(k);
                    if (tp90 < 0) {
                        System.out.println("tp90 section ==============" + k);
                        break;
                    }
                }
                if (tp70 > 0) {
                    tp70 -= map.get(k);
                    if (tp70 < 0) {
                        System.out.println("tp70 section ==============" + k);
                    }
                }
                if (tp50 > 0) {
                    tp50 -= map.get(k);
                    if (tp50 < 0) {
                        System.out.println("tp50 section ==============" + k);
                    }
                }
            }
            System.out.println("totalTime ==============" + totalTime + "   avg ======"+ (totalTime / total));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
