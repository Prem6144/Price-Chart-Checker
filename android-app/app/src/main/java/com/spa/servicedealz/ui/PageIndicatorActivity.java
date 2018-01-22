package com.spa.servicedealz.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.localytics.android.Localytics;
import com.spa.MyApplication;
import com.spa.servicedealz.R;
import com.spa.adapter.ViewPagerAdapter;
import com.spa.utils.Constant;
import com.spa.viewpager.CirclePageIndicator;

/**
 * FileName : PageIndicatorActivity
 * Description :what step follow to use service deal
 * Dependencies : ViewPagerAdapter
 */
public class PageIndicatorActivity extends Activity {
    private ViewPagerAdapter adapter;
    public static int value = 0;
    private Button mButtonGotIt;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ViewPager mViewPager;
    private CirclePageIndicator mCircleIndicator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_introscreen);
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
        mEditor = mSharedPreferences.edit();
        mViewPager = (ViewPager) findViewById(R.id.myfivepanelpager);
        mCircleIndicator = (CirclePageIndicator) findViewById(R.id.llDots);
        mButtonGotIt = (Button) findViewById(R.id.txt_gotit);
        //  mButtonGotIt.setBackgroundResource(R.drawable.gotit1);
        setViewPager();

    }

    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("IntroActivity");
    }

    private void setViewPager() {
        adapter = new ViewPagerAdapter(PageIndicatorActivity.this, mImageArray);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(0);
        mCircleIndicator.setViewPager(mViewPager);
        mCircleIndicator.setExtraSpacing(20);
        mCircleIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                if (arg0 == 4) {
                    mButtonGotIt.setVisibility(View.VISIBLE);
                     mCircleIndicator.setVisibility(View.GONE);
                } else {
                      mCircleIndicator.setVisibility(View.VISIBLE);
                    mButtonGotIt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub

            }
        });

        mButtonGotIt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                mEditor.putString("READ_INTRO", "yes");
                mEditor.commit();

                if (getIntent().getBooleanExtra("Introfinish", false)) {
                    finish();
                } else {
                    Intent in = new Intent(PageIndicatorActivity.this,
                            LoginActivity.class);
                    startActivity(in);
                    finish();
                }
            }
        });


    }

    protected void onStart() {
        super.onStart();
        MyApplication application = (MyApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        Log.i("PageIndicatorActivity", "Setting screen name: " + "PageIndicatorActivity");
        mTracker.setScreenName("Image~" + "PageIndicatorActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private int mImageArray[] = {R.drawable.step1, R.drawable.step2,
            R.drawable.step3, R.drawable.step4, R.drawable.step5};


}
