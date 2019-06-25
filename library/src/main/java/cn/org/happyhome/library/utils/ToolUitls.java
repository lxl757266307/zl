package cn.org.happyhome.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/8/2.
 */

public class ToolUitls {

    /*控制日志打印   不需要在relese 的时候注视日志代码*/
    public static boolean debug = true;

    public static void toast(Context context, String message) {
        if (debug) {
            if (context != null) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }

    }

    public static void print(String tag, String... message) {
        if (debug) {
            for (int i = 0; i < message.length; i++) {
                Log.e(tag, message[i]);
            }
        }

    }


    /**
     * 生成随机字符串
     *
     * @param length
     * @return
     */

    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString().toUpperCase();
    }


    /**
     * RSA加密
     */
    public static String getSign(TreeMap<String, String> map) {
        String jsonStr = "";
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            if (map.get(key) != null) {

                jsonStr += key + "=" + map.get(key) + "&";

//                ToolUitls.print("---", key+"==" + map.get(key));
            }
        }
//        jsonStr = Contacts.SIGN + jsonStr.substring(0, jsonStr.length()) +
//                "key=" + Contacts.KEY + Contacts.SIGN;
//        ToolUitls.print("---", "json==" + jsonStr);
        return MD5.getMessageDigest(jsonStr.getBytes()).toUpperCase();
    }

    public static String getShaSign(TreeMap<String, String> map) throws Exception {

        StringBuffer stringBuffer = new StringBuffer();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            if (map.get(key) != null) {
                stringBuffer.append(key).append("=").append(map.get(key));
            }
        }
//        Log.d("ToolUitls", stringBuffer.toString());
        return Sha.encryptSHA(stringBuffer.toString().trim());
    }

    public static String getShaSign2(TreeMap<String, Object> map) throws Exception {

        StringBuffer stringBuffer = new StringBuffer();
        Set<String> keySet = map.keySet();
        for (String key : keySet) {
            if (map.get(key) != null) {
                stringBuffer.append(key).append("=").append(map.get(key));
            }
        }
//        Log.d("ToolUitls", stringBuffer.toString());
        return Sha.encryptSHA(stringBuffer.toString().trim());
    }


    /**
     * 获取设备的唯一识别码
     *
     * @param context
     * @return
     */
    @SuppressLint("MissingPermission")
    public static String getMyUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
//        final String tmDevice, tmSerial, androidId;
//        tmDevice = "" + tm.getDeviceId();
//        tmSerial = "" + tm.getSimSerialNumber();
//        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
//        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
//        String uniqueId = deviceUuid.toString();
        return tm.getDeviceId();
    }


    public static void getCallBackStr(final String path) {
//        ToolUitls.print("TEST", "path=" + path);
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    URL url = new URL(path);
                    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                    if (urlConnection.getResponseCode() == 200) {

                        InputStream inputStream = urlConnection.getInputStream();

                        int len = 0;

                        byte[] bytes = new byte[1024];

                        StringBuilder builder = new StringBuilder();

                        while ((len = inputStream.read(bytes)) != -1) {

                            builder.append(new String(bytes, 0, len));
                        }

//                        ToolUitls.print("TEST", "str=" + builder.toString());


                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }

    /*
     * 获取当前程序的版本名
     */
    public static String getVersionName(Context context) throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        Log.e("TAG", "版本号" + packInfo.versionCode);
        Log.e("TAG", "版本名" + packInfo.versionName);
        return packInfo.versionName;
    }

    /*
     * 获取当前程序的版本号
     */
    public static int getVersionCode(Context context) throws Exception {
        //获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        //getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        Log.e("TAG", "版本号" + packInfo.versionCode);
        Log.e("TAG", "版本名" + packInfo.versionName);
        return packInfo.versionCode;
    }

    public static String timeFormart(long mills) {
        Date date = new Date(mills);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }


}
