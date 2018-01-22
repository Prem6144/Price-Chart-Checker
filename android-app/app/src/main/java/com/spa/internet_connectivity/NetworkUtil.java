package com.spa.internet_connectivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * This class contains various methods for Check the InterNetConnectivity status whether the mobile data is on
 * Or Wifi is enabled
 * @since 0.9
 */


public class NetworkUtil {
    private static changeNetworkInterFace myInterface;
    public static boolean isConnected = false;
    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;


    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;

    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = "Wifi enabled & now you are connected";

            isConnected = true;

        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = "Mobile data enabled & now you are connected";
            isConnected = true;
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {

            status = "Not connected to Internet";
            isConnected = false;
        }
        if (myInterface != null) {
            myInterface.updateNetwork(status);
        }
        return status;
    }

    /**
     * Checking for all possible internet providers
     */
    public static boolean isConnectingToInternet(Context _context) {
        ConnectivityManager connectivity = (ConnectivityManager) _context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isConnected = true;
                        return isConnected;
                    }

        }
        isConnected = false;
        return isConnected;
    }

    public interface changeNetworkInterFace {
        public void updateNetwork(String s);
    }


    public static void setChangeNetWorkListener(Context ctx) {
        myInterface = (changeNetworkInterFace) ctx;
    }
}
