package com.spa.servicedealz.drawer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.localytics.android.Localytics;
import com.spa.MyApplication;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.SlideMenuActivity;
import com.spa.fragment.MyOrderFragment;

import co.spa.sidemenu.util.ViewAnimator;

/**
 * Created by Diwakar on 5/19/2016.
 */
public class MyOrderActivity extends SlideMenuActivity {
    private MyOrderFragment myOrderFragment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        myOrderFragment = MyOrderFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, myOrderFragment)
                .commit();
        setActionBar();
//        Localytics.setTestModeEnabled(true);
//        Localytics.setLoggingEnabled(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAnimator = new ViewAnimator<>(this, mSlideMenuList, myOrderFragment, mDrawerLayout, this);
        Localytics.tagScreen("OrderActivity");
    }

    protected void onStart() {
        super.onStart();
        MyApplication application = (MyApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        Log.i("OrderActivity", "Setting screen name: " + "OrderActivity");
        mTracker.setScreenName("Image~" + "OrderActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon((R.drawable.menuicon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "My Orders" + "</font>")));
        setDrawer(toolbar);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}