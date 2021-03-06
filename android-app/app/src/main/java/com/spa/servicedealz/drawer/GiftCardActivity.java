package com.spa.servicedealz.drawer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;

import com.localytics.android.Localytics;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.SlideMenuActivity;
import com.spa.fragment.GiftCardFragment;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.utils.Constant;
import com.spa.utils.MenuItemGlobal;

import co.spa.sidemenu.util.ViewAnimator;

/**
 * Created by Diwakar on 4/19/2016.
 */
public class GiftCardActivity extends SlideMenuActivity {
    private GiftCardFragment mContentFragment;
    public static SharedPreferences mSharedPreferences;
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
        NetworkUtil.setChangeNetWorkListener(GiftCardActivity.this);
        Constant.compare = "";
        mContentFragment = GiftCardFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mContentFragment)
                .commit();
        setActionBar();

    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon((R.drawable.menuicon));
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + MenuItemGlobal.GIFTCARD + "</font>")));
        setDrawer(toolbar);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("GiftCardListActivity");
        viewAnimator = new ViewAnimator<>(this, mSlideMenuList, mContentFragment, mDrawerLayout, this);
    }
}
