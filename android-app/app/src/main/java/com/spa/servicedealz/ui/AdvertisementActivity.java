package com.spa.servicedealz.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;

import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.DealListActivity;
import com.spa.utils.Constant;

/**
 * Created by E0115Diwakar on 2/27/2015.
 */

/**
 * FileName : AdvertisementActivity
 * Description :show Advertisement
 * Dependencies : Internet
 */
public class AdvertisementActivity extends Activity implements View.OnClickListener {
    private ImageView mImageViewAdvertisement, mImageViewClose;

    private Handler mHandler;

    private Runnable mRunnable;

    private int i = 0;

    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.advertisement);
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

        mImageViewClose.setOnClickListener(this);
        // mImageViewAdvertisement.setOnClickListener(this);
        // setThreadRunnable();

    }

    /*Running thread in background for showing imaage in advertisement */
    private void setThreadRunnable() {
        if (mHandler == null) {
            mHandler = new Handler();
        }

        mRunnable = new Runnable() {
            public void run() {

                try {
                    if (i == 2) { // just remove call backs
                        mHandler.removeCallbacks(mRunnable);
                        Intent i = new Intent(AdvertisementActivity.this, DealListActivity.class);
                        startActivity(i);
                        finish();
                    } else { // post again
                        i++;
                        mHandler.postDelayed(this, 1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        mHandler.postDelayed(mRunnable, 0);
    }

    /*autogenrated method Click button*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
           /* case R.id.img_add:
                i = 2;
                if (!mSharedPreferences.getString(Constant.ADVERTISE_URL, "").equalsIgnoreCase("")) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(mSharedPreferences.getString(Constant.ADVERTISE_URL, "")));
                    startActivity(browserIntent);
                }
                break;*/
            case R.id.img_close:
                // i = 2;
                finish();
                break;
        }
    }
}
