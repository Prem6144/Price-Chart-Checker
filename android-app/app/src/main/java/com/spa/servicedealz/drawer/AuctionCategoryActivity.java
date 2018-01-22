package com.spa.servicedealz.drawer;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;

import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.SlideMenuActivity;
import com.spa.fragment.AuctionCategoryFragment;

import co.spa.sidemenu.util.ViewAnimator;

/**
 * FileName : AuctionCategoryActivity
 * Description :
 * Dependencies : AuctionCategoryFragment
 */
public class AuctionCategoryActivity extends SlideMenuActivity {
    private AuctionCategoryFragment mAuctionFragment;
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
        mAuctionFragment = AuctionCategoryFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mAuctionFragment)
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
        toolbar.setNavigationIcon((R.drawable.menuicon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        Intent in = getIntent();
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + in.getStringExtra("category") + "</font>")));
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewAnimator = new ViewAnimator<>(this, mSlideMenuList, mAuctionFragment, mDrawerLayout, this);
    }

}
