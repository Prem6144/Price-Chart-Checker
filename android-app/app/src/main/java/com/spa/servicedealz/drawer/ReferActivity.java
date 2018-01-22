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
import com.spa.fragment.ReferFragment;
import co.spa.sidemenu.util.ViewAnimator;
import io.branch.referral.Branch;

/**
 * Created by Diwakar on 5/26/2016.
 */
public class ReferActivity extends SlideMenuActivity {
    private ReferFragment referFragment;

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
        referFragment = ReferFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, referFragment)
                .commit();
        setActionBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAnimator = new ViewAnimator<>(this, mSlideMenuList, referFragment, mDrawerLayout, this);
        Localytics.tagScreen("ReferActivity");
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        Branch.getInstance(getApplicationContext()).initSession();
        MyApplication application = (MyApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        Log.i("DashboardActivity", "Setting screen name: " + "PreferenceActivity");
        mTracker.setScreenName("Image~" + "ReferActivity");
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
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Refer Friends" + "</font>")));
        setDrawer(toolbar);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}