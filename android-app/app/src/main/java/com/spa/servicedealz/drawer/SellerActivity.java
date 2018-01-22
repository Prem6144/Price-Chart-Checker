package com.spa.servicedealz.drawer;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;

import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.SlideMenuActivity;
import com.spa.fragment.SellerFragment;

import co.spa.sidemenu.util.ViewAnimator;

/**
 * FileName : SellerActivity
 * Description :
 * Dependencies : SellerFragment
 */
public class SellerActivity extends SlideMenuActivity {
    private SellerFragment mSellerFragment;

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
        mSellerFragment = SellerFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mSellerFragment)
                .commit();
        setActionBar();
    }


    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.menuicon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Reseller" + "</font>")));

    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAnimator = new ViewAnimator<>(this, mSlideMenuList, mSellerFragment, mDrawerLayout, this);
    }


}
