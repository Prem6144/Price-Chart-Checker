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
import com.spa.fragment.DashboardFragment;
import com.spa.fragment.DealListFragment;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.SlideMenuActivity;
import com.spa.utils.Constant;

import io.branch.referral.Branch;
import co.spa.sidemenu.util.ViewAnimator;
/**
 * FileName : DashboardActivity
 * Description :
 * Dependencies : AuctionCategoryFragment
 */
public class DashboardActivity extends SlideMenuActivity {
    private DashboardFragment mDashboardFragment;

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
        mDashboardFragment = DashboardFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mDashboardFragment)
                .commit();
        setActionBar();
        Localytics.registerPush(Constant.PROJECT_ID);
//        Localytics.setTestModeEnabled(true);
//        Localytics.setLoggingEnabled(true);


    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAnimator = new ViewAnimator<>(this, mSlideMenuList, mDashboardFragment, mDrawerLayout, this);
        Localytics.tagScreen("DashboardActivity");
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Branch.getInstance(getApplicationContext()).initSession();
        MyApplication application = (MyApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        Log.i("DashboardActivity", "Setting screen name: " + "DashboardActivity");
        mTracker.setScreenName("Image~" + "DashboardActivity");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    protected void onStop() {
        super.onStop();
        Branch.getInstance(getApplicationContext()).closeSession();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon((R.drawable.menuicon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Dashboard" + "</font>")));
        setDrawer(toolbar);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
