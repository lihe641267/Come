package com.lt.vs.come.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.telephony.TelephonyManager;
import android.util.Log;

/**
 * Created by 金色VS时空 on 2017/9/16.
 */

public class IntenetUtil {
    //没有网络情况
    public static final String NETWORN_NONE = "当前没有网络";
    //wifi连接
    public static final String NETWORN_WIFI = "当前网络是WIFI";
    //2G网络
    public static final String NETWORN_2G = "当前网络是2G";
    //3G网络
    public static final String NETWORN_3G = "当前网络是3G";
    //4G网络
    public static final String NETWORN_4G = "当前网络是4G";
    public static final String NETWORN_MOBILE = "当前网络是MOBILE";

    public static String isConnect(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        // 通过网络管理器对象获取到网络信息对象
        NetworkInfo info = manager.getActiveNetworkInfo();
        // 如果网络信息不为空，并且可以获取到，并且已经连接上或者正在连接
        if (info != null && info.isAvailable()&& info.isConnectedOrConnecting()) {
            String type = info.getTypeName();
            return type;
        } else {
            return "未连接成功";
        }
    }
    public static String getNetworkState(Context context) {
        //获取系统的网络服务
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == connectivityManager) {
            Log.e("@@@","========"+0);
            return NETWORN_NONE;
        }

        //判断连接是不是wifi
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (null != wifiInfo) {
            NetworkInfo.State state = wifiInfo.getState();
            if (null != state) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    Log.e("@@@","========"+2);
                    return NETWORN_WIFI;
                }
            }
        }
        //如果不是WiFi，则判断当前连接的是运营商的哪种网络2/a3/4G等
        NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (null != mobileInfo) {
            NetworkInfo.State state = mobileInfo.getState();
            String strTypeName = mobileInfo.getSubtypeName();
            if (state != null) {
                if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
                    switch (mobileInfo.getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_GPRS://2G
                        case TelephonyManager.NETWORK_TYPE_CDMA://联通2g
                        case TelephonyManager.NETWORK_TYPE_EDGE://电信2g
                        case TelephonyManager.NETWORK_TYPE_1xRTT://移动2g
                        case TelephonyManager.NETWORK_TYPE_IDEN:
                            Log.e("@@@","========"+3);
                            return NETWORN_2G;
                        //3G网络
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EVDO_0:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                            Log.e("@@@","========"+4);
                            return NETWORN_3G;
                        //4G网络
                        case TelephonyManager.NETWORK_TYPE_LTE:
                            Log.e("@@@","========"+5);
                            return NETWORN_4G;

                        default://中国移动、联通、电信三种3G制式
                            if (strTypeName.equalsIgnoreCase("TD-SCDMA") ||
                                    strTypeName.equalsIgnoreCase("WCDMA") || strTypeName.equalsIgnoreCase("CDMA2000")) {
                                Log.e("@@@","========"+6);
                                return NETWORN_3G;
                            } else {
                                Log.e("@@@","========"+7);
                                return NETWORN_MOBILE;
                            }

                    }
                }
            }
        }
        Log.e("@@@","========"+8);
        return NETWORN_NONE;
    }

}