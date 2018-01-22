package com.spa.servicedealz.ui;

/**
 * Created by E0115Diwakar on 2/13/2015.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.util.Log;

import com.crittercism.app.Crittercism;
import com.localytics.android.Localytics;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.notification.GCMNotificationIntentService;
import com.spa.service.RemovedTaskFromService;
import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.DashboardActivity;
import com.spa.servicedealz.drawer.ProfileActivity;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;
import com.spa.utils.TelephonyInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import io.branch.referral.Branch;
import io.branch.referral.BranchError;

/**
 * FileName : SplashActivity
 * Description :Show SplashScreen of ServiceDeal
 * Dependencies : No Dependencies
 */
public class SplashActivity extends Activity {

    private static final int PERMISSIONS_REQUEST_READ_PHONE_STATE = 0;
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    Branch branch;
    private SharedPreferences mSharedPreferences;
    private Boolean isInternetPresent = false;
    public static AlertDialog.Builder alertbox = null;
    String DeviceId = "47dh5ddsbhjj49", AndroidID = "89349579475395735", ProviderName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        mSharedPreferences = getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        try {
            isInternetPresent = NetworkUtil.isConnectingToInternet(this);
            if (isInternetPresent) {
                Crittercism.initialize(getApplicationContext(), "bf3594dd078a404fb0ebc8e6a6a86d9f00555300");
                isDualSimOrNot();

                // String serviceName = Context.TELEPHONY_SERVICE;
                //// TelephonyManager m_telephonyManager = (TelephonyManager) getSystemService(serviceName);
               /* AndroidID = Settings.Secure.getString(this.getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                isDualSimOrNot();
                DeviceId = m_telephonyManager.getDeviceId();
                try {
                    ProviderName = m_telephonyManager.getNetworkOperatorName();
                    Log.d("Android", "Android ID : " + AndroidID);
                    Log.i("Deviceid:  ", m_telephonyManager.getDeviceId());
                    Log.i("Provider:  ", m_telephonyManager.getNetworkOperatorName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
*/
                new HttpGetAsyncTask().execute();
                GCMNotificationIntentService.setTargetActivity(this);
            } else {
                Show_Alert_Dailog(this);
            }


        } catch (Exception e) {

            //Log.d(TAG, e.getMessage(), e);Qtfp9vZzf/S9zde8caQUwuyNicc=
        }

    }



    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.SendDeviceInformation(AndroidID, DeviceId, ProviderName, SplashActivity.this);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress dialog
         * **/


        protected void onPostExecute(String response) {
            if (response.length() > 1 && AndroidID.length() > 3) {
                Intent serIntent = new Intent(getApplicationContext(), RemovedTaskFromService.class);
                startService(serIntent);
                new Handler().postDelayed(new Runnable() {

			/*
             * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

                    @Override
                    public void run() {
                        // This method will be executed once the timer is over
                        // Start your app main activity
                        String login_flag = mSharedPreferences.getString("login_flag", "");
                        String login_flag_dashboard = mSharedPreferences.getString("login_flag_dashboard", "");
                        if (mSharedPreferences.getString("READ_INTRO", "").equalsIgnoreCase("yes")) {
                            if (login_flag.equalsIgnoreCase("yes")) {
                                if (login_flag_dashboard.equalsIgnoreCase("yes")) {
                                    Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
                                    startActivity(i);
                                } else {

                                    Intent i = new Intent(SplashActivity.this, ProfileActivity.class);
                                    startActivity(i);
                                }
                            } else {
                                Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                                startActivity(i);
                            }
                        } else {
                            Intent i = new Intent(SplashActivity.this, IntroScreenActivity.class);
                            startActivity(i);
                        }


                        // close this activity
                        finish();
                    }
                }, SPLASH_TIME_OUT);
            } else {
//                Show_Alert_Login(SplashActivity.this, "Please try after some time");

            }
        }
    }

    private void isDualSimOrNot() {
        TelephonyInfo telephonyInfo = TelephonyInfo.getInstance(SplashActivity.this);
        try {
            String imeiSIM1 =

                    telephonyInfo.getImeiSIM1();
            String imeiSIM2 =
                    telephonyInfo.getImeiSIM2();
            String serviceName = Context.TELEPHONY_SERVICE;
            TelephonyManager m_telephonyManager = (TelephonyManager) getSystemService(serviceName);
            DeviceId = m_telephonyManager.getDeviceId();
            if (DeviceId == null) {
                DeviceId = imeiSIM1;
                if (DeviceId == null) {
                    DeviceId = imeiSIM2;
                    if (DeviceId == null) {
                        try {
                            DeviceId = Settings.Secure.getString(getContentResolver(),
                                    Settings.Secure.ANDROID_ID);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
            AndroidID = imeiSIM1;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String Show_Alert_Login(Activity activity, String message) {
        alertbox = new AlertDialog.Builder(
                activity);

        alertbox.setMessage(message);
        alertbox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });
        alertbox.setCancelable(false);
        alertbox.show();
        return null;
    }

    public String Show_Alert_Dailog(Activity activity) {
        alertbox = new AlertDialog.Builder(
                activity);
        alertbox.setTitle(Constant.WARNING);
        alertbox.setMessage(Constant.CONNECTION_ERROR);
        alertbox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });
        alertbox.setCancelable(false);
        alertbox.show();
        return null;
    }


    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("SplashActivity");
    }

    @Override
    protected void onStart() {
        super.onStart();


        branch = Branch.getInstance();

        branch.setDebug();
        //branch.disableTouchDebugging();

        branch.initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams,
                                       BranchError error) {
                if (error != null) {
                    Log.i("BranchTestBed", "branch init failed. Caused by -" + error.getMessage());
                } else {
                    Log.i("BranchTestBed", "branch init complete!");
                    try {
                        Iterator<?> keys = referringParams.keys();
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            String value = referringParams.getString(key);
                            Log.i("BranchTestBed", key + ", " + value);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, this.getIntent().getData(), this);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_READ_PHONE_STATE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


        }
    }
}
