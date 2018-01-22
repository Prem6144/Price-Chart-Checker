package com.spa.notification;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.spa.servicedealz.R;

import java.io.IOException;

public class MyPush {
    GoogleCloudMessaging gcmObj;
    public static String regId = "";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static final String REG_ID = "regId";
    static String GOOGLE_PROJ_ID = null;
    public static String CUSTOM_NOTIFICATION_TITLE = "Service Deal Notification";
    public static int CUSTOM_NOTIFICATION_IMAGE = R.drawable.appicon;
    public static String MSG_KEY = "message";
    public static String REFF_KEY = "referral";

    public MyPush() {
        // TODO Auto-generated constructor stub
    }

    public MyPush(String projectId) {
        // TODO Auto-generated constructor stub
        GOOGLE_PROJ_ID = projectId;
    }

    public void registerInBackground(final Context ctx, Activity act) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String msg = "";
                try {
                    if (gcmObj == null) {
                        gcmObj = GoogleCloudMessaging
                                .getInstance(ctx);
                    }
                    regId = gcmObj
                            .register(GOOGLE_PROJ_ID);
                    msg = "Registration ID :" + regId;

                } catch (IOException ex) {
                    msg = "Error :" + ex.getMessage();
                }
                return msg;
            }

            @Override
            protected void onPostExecute(String msg) {
                if (!TextUtils.isEmpty(regId)) {
                    storeRegIdinSharedPref(ctx, regId);
                }
            }
        }.execute(null, null, null);
    }

    private void storeRegIdinSharedPref(Context ctx, String regId) {
        Log.e("Registration Id", regId);
        SharedPreferences prefs = ctx.getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(REG_ID, regId);
        editor.commit();

    }

    public boolean checkPlayServices(Activity ctx) {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(ctx);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, ctx,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }
            return false;
        }
        return true;
    }

    /**
     * If user want to custom Message Use "setMessages" method followed by String title,String msg, String msgKey, int image parameter,
     * String projectId, String serverUrl
     */
    public String getRegistrationId(Context context) {
        try {
            SharedPreferences prefs = context.getSharedPreferences("UserDetails", Context.MODE_PRIVATE);

            String regID = prefs.getString(REG_ID, null);

            if ((regID == null)) {
                return null;
            }

            return regID;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
