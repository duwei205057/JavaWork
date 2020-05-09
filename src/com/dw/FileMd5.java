package com.dw;

/*
* 该类是生成app/jars/filemd5.jar的源文件，该jar包是在打包编译前执行计算系统词库文件的md5,
* 并将md5写入assets/dictmd5文件，这样加载系统词库时就可以校验词库文件正确性
* 该类在输入法整个生命周期内不会有调用，之所以放这里是为了备份
* 其他人不应该调用该类，整体已加注释
* */


import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.URLDecoder;
import java.security.MessageDigest;

public class FileMd5 {

    private static int BUFF_SIZE = 1024;
    protected static char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6',
        '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
    
    private static String mRunningLocation;
    private static String mAssertLocation;

	public static void main(String[] args) {
		getRunningLocation(new FileMd5());
		String sysDictPath = mAssertLocation + "/raw/sgim_sys.bin";
		String sysDictMd5 = getMD5(sysDictPath);
		try {
			sysDictMd5 = Encrypt(sysDictMd5, "6E09C97EB8798EEB");
		} catch (Exception e) {
			// TODO: handle exception
		}		
		File md5File = new File(mAssertLocation + "/dictmd5");
		if (md5File.exists())
		    md5File.delete();
		try {
			FileWriter fw =new FileWriter(md5File);
	    	fw.write(sysDictMd5);
	    	fw.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	private static void getRunningLocation(FileMd5 fileMd5) {
		mRunningLocation = fileMd5.getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		try {
			mRunningLocation = URLDecoder.decode(mRunningLocation, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (mRunningLocation.endsWith(".jar")) {
			mRunningLocation = mRunningLocation.substring(0,
					mRunningLocation.lastIndexOf(File.separator) + 1);
		}
		File f = new File(mRunningLocation);
		mRunningLocation = f.getAbsolutePath();
		System.out.println("===========location="+mRunningLocation);
		mAssertLocation = mRunningLocation.replace("/jars", "/assets");
		System.out.println("===========assert location="+mAssertLocation);
	}

    
    public static String Encrypt(String sSrc, String sKey) throws Exception {
        byte[] raw = sKey.getBytes("UTF-8");
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

        byte[] encrypted = cipher.doFinal(sSrc.getBytes());
        return byte2hex(encrypted).toLowerCase();
    }
    
    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {

            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
	
    public static String getMD5(String filePath) {
    	File file = new File(filePath);
    	try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            InputStream fis;
            fis = new FileInputStream(file);
            byte[] buffer = new byte[BUFF_SIZE];
            int numRead = 0;
            while ((numRead = fis.read(buffer)) > 0) {
                md5.update(buffer, 0, numRead);
            }
            fis.close();
            fis = null;
            return bufferToHex(md5.digest());
		} catch (Exception e) {
			// TODO: handle exception
		}

    	return "";
    }
    
    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
    return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }
}
