package com.spa.servicedealz.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.servicedealz.R;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;

/**
 * Created by Diwakar on 6/30/2016.
 */
public class WowScreenActivity extends Activity implements View.OnClickListener {
    private ImageView mImageViewClose;
    private TextView mTextViewWow, mTextViewYouSave, mTextViewReviewDeals;
    private SharedPreferences mSharedPreferences;
    private Boolean isInternetPresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wow_screen);
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
        // mImageViewAdvertisement = (ImageView) findViewById(R.id.img_add);
        mImageViewClose = (ImageView) findViewById(R.id.img_close);
        mTextViewWow = (TextView) findViewById(R.id.txt_wow);
        mTextViewYouSave = (TextView) findViewById(R.id.txt_wow2);
        mTextViewReviewDeals = (TextView) findViewById(R.id.txt_wow3);

        String fontPath = "fonts/arbekely.ttf";
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        mTextViewWow.setTypeface(tf2);
        mTextViewReviewDeals.setOnClickListener(this);
        mImageViewClose.setOnClickListener(this);

        isInternetPresent = NetworkUtil.isConnectingToInternet(this);
        if (isInternetPresent) {
            new HttpGetAsyncTask().execute();
        } else {
            ShowDailog.Show_Alert_Dailog(this);
        }


        // mImageViewAdvertisement.setOnClickListener(this);
        // setThreadRunnable();

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
                response = Jsondata.getYouSave(mSharedPreferences.getString(Constant.APP_USER_ID, ""), WowScreenActivity.this);
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
            mTextViewYouSave.setText("$" + response+"/annum");
        }
    }
    /*Running thread in background for showing imaage in advertisement */

    /*autogenrated method Click button*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_wow3:
                finish();
                break;
            case R.id.img_close:
                // i = 2;
                finish();

                break;
        }
    }
}
