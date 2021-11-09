package com.dw.apm;


import java.sql.*;

/**
 * 查询数据库，根据关键字查询单方法耗时
 */
public class ApmAnalyse3 {

    private static int deep = 100;
    private static String[] names = new String[deep];
    private static int[] costs = new int[deep];
    private static int[] methodIds = new int[deep];

    public static void main(String[] args) {
        Connection con = null;
        Statement sta = null;
        ResultSet res = null;
        try {
//            String key = "ThemeSettingManager getBoolean (";
            String key = "AccountSetManager getBoolean";
//            String key = "KeyboardSetting getString (";

            String url = new String("jdbc:mysql://dumpinfo.apm.rds.sogou:3306/sogou_android_apm?characterEncoding=utf8&useSSL=false");
            Class.forName("com.mysql.jdbc.Driver");

            con = (Connection) DriverManager.getConnection(url,"sogou_apm","SogouAndroidApm");
            sta = con.createStatement();
            String sql;

            sql = new String("select id , stack from dumpinfo_20211106 where version = '10.35.6' and stack like '%"+key+"%'");

            res = sta.executeQuery(sql);
            int times = 0;
            int totalCost = 0;
            int maxCost = 0;
            while(res.next()){
                System.out.println("-------------------------------------"+(++times)+"------------------------------------------------");
                String stack = res.getString("stack");
                int originId = res.getInt("id");
                String[] phrases = stack.split(";");
                int lastLevel = -1;
                boolean find = false;
                for (String phrase : phrases) {
                    int index = phrase.indexOf(" at ");
                    String[] infos = phrase.substring(0, index).split(",");
                    int level = Integer.parseInt(infos[0]);
                    int methodId = Integer.parseInt(infos[1]);
                    int cost = Integer.parseInt(infos[3]);
                    String name = phrase.substring(index + 4);
                    if (find) {
                        find = false;
                        if (name.contains("MMKVWorkChainImpl")) {
                            System.out.println("------------------------------------------------------"+name+" "+cost);
                            totalCost += cost;
                            if (cost > maxCost) {
                                maxCost = cost;
                            }
                        }

                    }
//                    if (name.contains("AccountSetManager getBoolean")) {
//                        find = true;
//                    }
                    if (name.contains(key)) {
                        find = true;
                    }
                }
            }
            System.out.println("------------------------------------------------------avg cost =" + (totalCost/times) + " max cost = " +maxCost);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
                if (sta != null) {
                    sta.close();
                }
                if (res != null) {
                    res.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
