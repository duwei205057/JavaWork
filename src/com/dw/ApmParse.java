package com.dw;

import java.util.HashMap;

public class ApmParse {

    static String origin = "132\t997 at com.sohu.inputmethod.sogou.SogouIME hideWindow ()V\n" +
            "133\t54726 at com.sohu.inputmethod.sogou.MainImeServiceDel hideWindow ()V\n" +
            "134\t15491 at com.sogou.common_components.vibratesound.VibrateAndSoundManager cancelVibrate ()V\n" +
            "135\t54727 at com.sohu.inputmethod.sogou.MainImeServiceDel doHideWindow ()V\n" +
            "136\t24165 at com.sogou.lib.spage.BaseInputMethodService doParentHideWindow ()V\n" +
            "137\t56093 at com.sohu.inputmethod.sogou.SogouIME onFinishInputView (Z)V\n" +
            "138\t24135 at com.sogou.lib.spage.BaseInputMethodService onFinishInputView (Z)V\n" +
            "139\t54693 at com.sohu.inputmethod.sogou.MainImeServiceDel onFinishInputView (Z)V\n" +
            "140\t37774 at com.sohu.inputmethod.flx.FlxImeServiceBridge onFinishInputView (Z)V\n" +
            "141\t54694 at com.sohu.inputmethod.sogou.MainImeServiceDel doOnFinishInputView (Z)V\n" +
            "142\t36344 at com.sohu.inputmethod.cloudrecord.CloudRecorder upLoadRecord (Landroid.content.Context,)V\n" +
            "143\t55701 at com.sohu.inputmethod.sogou.PingbackSender sendPingbackNow (ILjava.lang.String,[B)V\n" +
            "144\t54698 at com.sohu.inputmethod.sogou.MainImeServiceDel clearAllStatus (Z)V\n" +
            "145\t54588 at com.sohu.inputmethod.sogou.MainImeServiceDel clearKeyboardStatus ()V\n" +
            "146\t54589 at com.sohu.inputmethod.sogou.MainImeServiceDel clearKeyboardStatus (Z)V\n" +
            "147\t54586 at com.sohu.inputmethod.sogou.MainImeServiceDel reset ()V\n" +
            "148\t54585 at com.sohu.inputmethod.sogou.MainImeServiceDel reset (Z)V\n" +
            "149\t46207 at com.sohu.inputmethod.input.InputLogic reset (Z)V\n" +
            "150\t46208 at com.sohu.inputmethod.input.InputLogic resetChinese (Z)V\n" +
            "151\t46199 at com.sohu.inputmethod.input.InputLogic updateSuggestions ()V\n" +
            "152\t46326 at com.sohu.inputmethod.input.InputLogic updateComposingText ()V\n" +
            "153\t53804 at com.sohu.inputmethod.sogou.ComposeMgr showComposingText ()V\n" +
            "154\t54981 at com.sohu.inputmethod.sogou.MainImeServiceDel getSogouInputConnection ()Lcom.sogou.bu.basic.ic.SogouInputConnectionInterface,\n" +
            "155\t56109 at com.sohu.inputmethod.sogou.SogouIME getSogouInputConnection ()Lcom.sogou.bu.basic.ic.SogouInputConnectionInterface,\n" +
            "156\t56344 at com.sohu.inputmethod.sogou.SogouInputConnectionManager setOrUpdateRealInputConnection (Landroid.view.inputmethod.InputConnection,)Lcom.sogou.bu.basic.ic.SogouInputConnectionProxyInterface,\n" +
            "157\t39951 at com.sohu.inputmethod.flx.screen.FlxScreenProcessor closeFanLingxiScreen (ZZ)Z\n" +
            "158\t37655 at com.sohu.inputmethod.flx.FlxActionConductor closeSwitchVoiceWindow ()V\n" +
            "159\t41484 at com.sohu.inputmethod.flxbridge.FlxActionConductorInterfaceImpl$1 closeSwitchVoiceWindow ()V\n" +
            "160\t66584 at com.sohu.inputmethod.voiceinput.voiceswitch.VoiceSwitchSendHelper newInstance (Landroid.content.Context,)Lcom.sohu.inputmethod.voiceinput.voiceswitch.VoiceSwitchSendHelper,\n" +
            "161\t66583 at com.sohu.inputmethod.voiceinput.voiceswitch.VoiceSwitchSendHelper (Landroid.content.Context,)V\n" +
            "162\t61397 at com.sohu.inputmethod.sogou.vpa.SmartBarManager clearBar (Z)V\n" +
            "163\t61386 at com.sohu.inputmethod.sogou.vpa.SmartBarManager setSmartBarState (Lcom.sohu.inputmethod.sogou.vpa.SmartBarState,)V\n" +
            "164\t61387 at com.sohu.inputmethod.sogou.vpa.SmartBarManager setSmartBarState (Lcom.sohu.inputmethod.sogou.vpa.SmartBarState,ZZ)V\n" +
            "165\t24050 at com.sogou.lib.slog.SLogger flush (Z)V\n" +
            "166\t25250 at com.sogou.networking.traffic.TrafficPermissionChecker checkPermission (Lcom.sogou.networking.IPermissionRequest,Lcom.sogou.networking.IConfiguration,)V\n" +
            "167\t23851 at com.sogou.lib.performance.PerformanceManager doFlush ()V\n" +
            "168\t23848 at com.sogou.lib.performance.PerformanceManager meetFlushTimeGap ()Z\n" +
            "169\t998 at com.sohu.inputmethod.sogou.SogouIME onWindowHidden ()V\n" +
            "170\t54704 at com.sohu.inputmethod.sogou.MainImeServiceDel onWindowHidden ()V\n" +
            "171\t54705 at com.sohu.inputmethod.sogou.MainImeServiceDel doOnWindowHidden ()V";

    static String cost = "63\t0,997,1,4964\n" +
            "43\t1,54726,1,4917\n" +
            "44\t2,15491,1,221\n" +
            "45\t2,54727,1,4696\n" +
            "46\t3,24165,1,4653\n" +
            "47\t4,56093,1,3348\n" +
            "48\t5,24135,1,441\n" +
            "49\t5,54693,1,913\n" +
            "50\t6,37774,1,43\n" +
            "51\t6,54694,1,417\n" +
            "52\t7,36344,1,285\n" +
            "53\t8,55701,1,45\n" +
            "54\t7,54698,1,132\n" +
            "55\t8,54588,1,44\n" +
            "56\t9,54589,1,44\n" +
            "57\t10,54586,1,44\n" +
            "58\t11,54585,1,44\n" +
            "59\t12,46207,1,44\n" +
            "60\t13,46208,1,44\n" +
            "61\t14,46199,1,44\n" +
            "62\t15,46326,1,44\n" +
            "63\t16,53804,1,44\n" +
            "64\t17,54981,1,44\n" +
            "65\t18,56109,1,44\n" +
            "66\t19,56344,1,44\n" +
            "67\t8,39951,1,43\n" +
            "68\t9,37655,1,43\n" +
            "69\t10,41484,1,43\n" +
            "70\t11,66584,1,43\n" +
            "71\t12,66583,1,43\n" +
            "72\t8,61397,1,45\n" +
            "73\t9,61386,1,45\n" +
            "74\t10,61387,1,45\n" +
            "75\t6,24050,1,46\n" +
            "76\t5,25250,1,700\n" +
            "77\t5,23851,1,224\n" +
            "78\t6,23848,1,224\n" +
            "79\t4,998,1,1305\n" +
            "80\t5,54704,1,1305\n" +
            "81\t6,54705,1,732";

    public static void main(String[] args) {
//        System.out.println(origin.replaceAll("[0-9]*\\t",""));
//        System.out.println(cost.replaceAll("[0-9]*\\t",""));
        String a = origin.replaceAll("[0-9]*\\t","");
        String b = cost.replaceAll("[0-9]*\\t","");
        HashMap map = new HashMap();
        String [] segs = a.split("\\n");
        for (String seg : segs) {
            String[] c = seg.split(" at ");
//            System.out.println(c[0] + "  " + c[1]);
            map.put(c[0], c[1]);
        }

        String[] d = b.split("\\n");
        for (String e : d) {
            String[] f = e.split(",");
            int level = Integer.parseInt(f[0]);
            for (int i = 0; i < level; i++) {
                System.out.print("  ");
            }
            System.out.print(level + " ");
            System.out.print(map.get(f[1]));
            System.out.print(" ");
            System.out.print(f[3]);
            System.out.println("");
        }
    }

}
