package com.dw.apm;


import java.sql.*;

/**
 * 查询数据库，对apm的stack信息进行分析，计算出每个函数的耗时
 */
public class ApmAnalyse {

    private static boolean test = false;
    private static String input = "0,999,1,476 at com.sohu.inputmethod.ui.frame.NormalIMERootContainer dispatchTouchEvent (Landroid.view.MotionEvent,)Z;1,3978,1,476 at com.sogou.component.RootComponentView onTouchEvent (Landroid.view.MotionEvent,)Z;2,61215,1,476 at com.sogou.lib.bu.ui.component.keyboard.SogouKeyboardComponent dispatchTouchEvent (Landroid.view.MotionEvent,)Z;3,3799,1,476 at com.sogou.component.Component dispatchTouchEvent (Landroid.view.MotionEvent,)Z;4,61217,1,476 at com.sogou.lib.bu.ui.component.keyboard.SogouKeyboardComponent onTouchEvent (Landroid.view.MotionEvent,)Z;5,24634,1,476 at com.sohu.inputmethod.input.KeyboardTouchEventHandler handleOnTouchEvent (Landroid.view.MotionEvent,)Z;6,24638,1,476 at com.sohu.inputmethod.input.KeyboardTouchEventHandler onTouchEventForMain (Landroid.view.MotionEvent,)Z;7,24648,1,476 at com.sohu.inputmethod.input.KeyboardTouchEventHandler onModifiedTouchEvent (Landroid.view.MotionEvent,IZ)Z;8,24649,1,476 at com.sohu.inputmethod.input.KeyboardTouchEventHandler handleUpEventNormal (IIJI)Z;9,24678,1,476 at com.sohu.inputmethod.input.KeyboardTouchEventHandler detectAndSendKey (IIIJIIZ)V;10,24680,1,476 at com.sohu.inputmethod.input.KeyboardTouchEventHandler detectAndSendKey (IIIJZIIZ)V;11,24683,1,476 at com.sohu.inputmethod.input.KeyboardTouchEventHandler sendKeyByKeySolver (I[IZIIII)V;12,24684,1,476 at com.sohu.inputmethod.input.KeyboardTouchEventHandler resolveOnKeyAction (I[IZII)V;13,24469,1,476 at com.sohu.inputmethod.input.InputLogic onKey (I[IZII)V;14,22796,1,476 at com.sohu.inputmethod.foreign.ForeignBusController handleImeChangeKey ()V;15,32347,1,476 at com.sohu.inputmethod.sogou.MainImeServiceDel switchLanguage (II)Z;16,32352,1,470 at com.sohu.inputmethod.sogou.MainImeServiceDel switchIMEType (IIIIZ)Z;17,32329,1,470 at com.sohu.inputmethod.sogou.MainImeServiceDel processModeChange (IIIZZ)V;18,33257,1,46 at com.sohu.inputmethod.sogou.RichImeStatus setCurLanId (I)V;18,22800,1,424 at com.sohu.inputmethod.foreign.ForeignBusController onLanguageSwitch (IIIIII)V;19,22776,1,41 at com.sohu.inputmethod.foreign.ForeignBusController getInputSession ()Lcom.sohu.inputmethod.foreign.inputsession.InputSession,;19,64561,1,377 at com.sohu.inputmethod.foreign.keyboard.ForeignKeyboardSwitcher onLanguageChanged (IIII)Z;20,65443,1,377 at com.sohu.inputmethod.foreign.keyboard.ForeignKeyboardState initSwitchState (I)Z;21,65444,1,377 at com.sohu.inputmethod.foreign.keyboard.ForeignKeyboardState setAlphabetKeyboard ()Z;22,64567,1,377 at com.sohu.inputmethod.foreign.keyboard.ForeignKeyboardSwitcher setAlphabetKeyboard ()Z;23,64568,1,377 at com.sohu.inputmethod.foreign.keyboard.ForeignKeyboardSwitcher setKeyboard (I)Z;24,22918,1,377 at com.sohu.inputmethod.foreign.ForeignKeyboardLoader getKeyboard (IIZI)Lcom.sohu.inputmethod.foreign.keyboard.KeyboardProxy,;25,22916,1,372 at com.sohu.inputmethod.foreign.ForeignKeyboardLoader$Builder build ()Lcom.sohu.inputmethod.foreign.keyboard.KeyboardProxy,;26,40254,1,372 at com.sohu.inputmethod.ui.KeyboardComponentManager getForeignKeyboard (IIIZZZI)Lcom.sohu.inputmethod.foreign.keyboard.KeyboardProxy,;27,61040,1,367 at com.sogou.lib.bu.ui.component.keyboard.KeyboardComponentController getForeignKeyboard (IIIIZZZI)Lcom.sohu.inputmethod.foreign.keyboard.KeyboardProxy,;28,61041,1,324 at com.sogou.lib.bu.ui.component.keyboard.KeyboardComponentController buildForeignComponent (IILjava.lang.String,ZI)Lcom.sogou.lib.bu.ui.component.keyboard.SogouKeyboardComponent,;29,62030,1,298 at com.sogou.theme.install.impl.ThemeImpl getViewData (Ljava.lang.Class,Ljava.util.List,)Lcom.sogou.theme.data.view.BaseViewData,;30,62015,1,298 at com.sogou.theme.install.impl.ThemeDataFetcherFactory getBaseData (Ljava.lang.Class,Lcom.sogou.theme.parse.interfaces.BaseThemeConfig,Lcom.sogou.theme.parse.entity.RequestParams,)Lcom.sogou.theme.data.view.BaseData,;31,63037,1,293 at com.sogou.theme.parse.frame.ForeignKeyboardParseFrame obtainData ()Ljava.lang.Object,;32,63004,1,293 at com.sogou.theme.parse.frame.ForeignKeyboardParseFrame obtainData ()Lcom.sogou.theme.data.keyboard.KeyboardData,;33,63056,1,293 at com.sogou.theme.parse.frame.KeyboardParseFrame obtainData ()Lcom.sogou.theme.data.keyboard.KeyboardData,;34,63225,1,247 at com.sogou.theme.parse.parseimpl.ForeignKeyboardDataParser parserProperty (Ljava.lang.String,Ljava.lang.String,)Z;35,62954,1,47 at com.sogou.theme.parse.frame.BaseFileParseFrame$ParserSetImpl getParserData (ILjava.util.ArrayList,Ljava.lang.String,Landroid.support.v4.util.ArrayMap,Lcom.sogou.theme.data.view.BaseData,)Ljava.lang.Object,;35,62954,1,44 at com.sogou.theme.parse.frame.BaseFileParseFrame$ParserSetImpl getParserData (ILjava.util.ArrayList,Ljava.lang.String,Landroid.support.v4.util.ArrayMap,Lcom.sogou.theme.data.view.BaseData,)Ljava.lang.Object,;35,62954,1,56 at com.sogou.theme.parse.frame.BaseFileParseFrame$ParserSetImpl getParserData (ILjava.util.ArrayList,Ljava.lang.String,Landroid.support.v4.util.ArrayMap,Lcom.sogou.theme.data.view.BaseData,)Ljava.lang.Object,;35,62954,1,85 at com.sogou.theme.parse.frame.BaseFileParseFrame$ParserSetImpl getParserData (ILjava.util.ArrayList,Ljava.lang.String,Landroid.support.v4.util.ArrayMap,Lcom.sogou.theme.data.view.BaseData,)Ljava.lang.Object,;36,63278,1,85 at com.sogou.theme.parse.parseimpl.KeyboardTableParser parserProperty (Ljava.lang.String,Ljava.lang.String,)Z;37,63279,1,85 at com.sogou.theme.parse.parseimpl.KeyboardTableParser parseKeys (Ljava.lang.String,)V;38,62954,1,49 at com.sogou.theme.parse.frame.BaseFileParseFrame$ParserSetImpl getParserData (ILjava.util.ArrayList,Ljava.lang.String,Landroid.support.v4.util.ArrayMap,Lcom.sogou.theme.data.view.BaseData,)Ljava.lang.Object,;39,63224,1,44 at com.sogou.theme.parse.parseimpl.ForeignKeyDataParser parserProperty (Lcom.sogou.theme.data.view.BaseData,Ljava.lang.String,Ljava.lang.String,Ljava.lang.String,Lcom.sogou.theme.data.view.BaseData,)Z;40,63220,1,44 at com.sogou.theme.parse.parseimpl.ForeignKeyDataParser parserProperty (Lcom.sogou.theme.data.key.KeyData,Ljava.lang.String,Ljava.lang.String,Ljava.lang.String,Lcom.sogou.theme.data.view.BaseData,)Z;41,63262,1,44 at com.sogou.theme.parse.parseimpl.KeyDataParser getKeyList (Lcom.sogou.theme.data.key.KeyData,Ljava.lang.String,)Landroid.util.SparseArray,;42,62954,1,39 at com.sogou.theme.parse.frame.BaseFileParseFrame$ParserSetImpl getParserData (ILjava.util.ArrayList,Ljava.lang.String,Landroid.support.v4.util.ArrayMap,Lcom.sogou.theme.data.view.BaseData,)Ljava.lang.Object,;43,63224,1,34 at com.sogou.theme.parse.parseimpl.ForeignKeyDataParser parserProperty (Lcom.sogou.theme.data.view.BaseData,Ljava.lang.String,Ljava.lang.String,Ljava.lang.String,Lcom.sogou.theme.data.view.BaseData,)Z;44,63220,1,34 at com.sogou.theme.parse.parseimpl.ForeignKeyDataParser parserProperty (Lcom.sogou.theme.data.key.KeyData,Ljava.lang.String,Ljava.lang.String,Ljava.lang.String,Lcom.sogou.theme.data.view.BaseData,)Z;45,63260,1,34 at com.sogou.theme.parse.parseimpl.KeyDataParser parserProperty (Lcom.sogou.theme.data.key.KeyData,Ljava.lang.String,Ljava.lang.String,Ljava.lang.String,Lcom.sogou.theme.data.view.BaseData,)Z;46,62954,1,34 at com.sogou.theme.parse.frame.BaseFileParseFrame$ParserSetImpl getParserData (ILjava.util.ArrayList,Ljava.lang.String,Landroid.support.v4.util.ArrayMap,Lcom.sogou.theme.data.view.BaseData,)Ljava.lang.Object,;47,63315,1,34 at com.sogou.theme.parse.parseimpl.PopupFgParser parserProperty (Ljava.lang.String,Ljava.lang.String,)Z;48,63318,1,34 at com.sogou.theme.parse.parseimpl.PopupFgParser getTextStyle (Ljava.lang.String,)Lcom.sogou.theme.data.style.MultiColorStyle,;49,62954,1,34 at com.sogou.theme.parse.frame.BaseFileParseFrame$ParserSetImpl getParserData (ILjava.util.ArrayList,Ljava.lang.String,Landroid.support.v4.util.ArrayMap,Lcom.sogou.theme.data.view.BaseData,)Ljava.lang.Object,;50,63333,1,34 at com.sogou.theme.parse.parseimpl.TextStyleParser parserProperty (Ljava.lang.String,Ljava.lang.String,)Z;51,62854,1,28 at com.sogou.theme.parse.constants.ThemePathAssembly getTypefaceSrcRootPath (Lcom.sogou.theme.parse.interfaces.BaseThemeConfig,Lcom.sogou.theme.parse.entity.RequestParams,)Ljava.util.ArrayList,;52,62892,1,28 at com.sogou.theme.parse.constants.ThemePathAssembly getForeignResPath (Lcom.sogou.theme.parse.interfaces.BaseThemeConfig,ZI)Ljava.lang.String,;53,62888,1,28 at com.sogou.theme.parse.constants.ThemePathAssembly getForeignPrefix (Lcom.sogou.theme.parse.interfaces.BaseThemeConfig,Ljava.lang.String,I)Ljava.lang.String,;28,61231,1,43 at com.sogou.lib.bu.ui.component.keyboard.SogouKeyboardComponent doWhenSetKeyboard (IIZZ)V";

    private static int deep = 100;
    private static String[] names = new String[deep];
    private static int[] costs = new int[deep];
    private static int[] methodIds = new int[deep];

    public static void main(String[] args) {
        Connection con = null;
        Statement sta = null;
        ResultSet res = null;
        PreparedStatement pr = null;
        try {

            String url = new String("jdbc:mysql://dumpinfo.apm.rds.sogou:3306/sogou_android_apm?characterEncoding=utf8&useSSL=false");
            Class.forName("com.mysql.jdbc.Driver");

            con = (Connection) DriverManager.getConnection(url,"sogou_apm","SogouAndroidApm");
            sta = con.createStatement();
            String sql;
            if (test) {
                sql = new String("select * from dumpinfo_20210718  where version = '10.29.8' limit 1");
            } else {
                sql = new String("select id , stack from dumpinfo_20210727 where version = '10.29.23' and stackid = 5824");
            }

            String insertSql = "insert into apm_cost (methodId,name,cost,originId) values(?,?,?,?)";
            String truncateSql = "truncate table apm_cost";
            pr = con.prepareStatement(insertSql);
            //清空临时表
            sta.execute(truncateSql);
            res = sta.executeQuery(sql);
            while(res.next()){
                String stack = res.getString("stack");
                if (test) {
                    stack = input;
                }
                int originId = res.getInt("id");
                String[] phrases = stack.split(";");
                int lastLevel = -1;
                for (String phrase : phrases) {
                    int index = phrase.indexOf(" at ");
                    String[] infos = phrase.substring(0, index).split(",");
                    int level = Integer.parseInt(infos[0]);
                    int methodId = Integer.parseInt(infos[1]);
                    int cost = Integer.parseInt(infos[3]);
                    String name = phrase.substring(index + 4);
//                  System.out.println("level = " +level +" cost = "+cost +" name = "+name);

                    if (level > 0) {
                        costs[level - 1] = costs[level - 1] - cost;
                    }
                    while (level <= lastLevel) {
                        //System.out.println("id " + originId + " " + lastLevel + " " +names[lastLevel] + " cost " + costs[lastLevel]);
                        if (costs[lastLevel] > 0) {
                            pr.setInt(1, methodIds[lastLevel]);
                            pr.setString(2, names[lastLevel]);
                            pr.setInt(3, costs[lastLevel]);
                            pr.setInt(4, originId);
                            pr.executeUpdate();
                        }
                        lastLevel--;
                    }
                    //存入当前信息
                    names[level] = name;
                    costs[level] = cost;
                    methodIds[level] = methodId;
                    lastLevel = level;
                }
                while (0 <= lastLevel) {
                    //System.out.println("id " + originId + " " + lastLevel + " " +names[lastLevel] + " cost " + costs[lastLevel]);
                    if (costs[lastLevel] > 0) {
                        pr.setInt(1, methodIds[lastLevel]);
                        pr.setString(2, names[lastLevel]);
                        pr.setInt(3, costs[lastLevel]);
                        pr.setInt(4, originId);
                        pr.executeUpdate();
                    }
                    lastLevel--;
                }
                //System.out.println("------------------------------------------------------");
            }

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
                if (pr != null) {
                    pr.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
