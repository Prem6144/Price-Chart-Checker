package com.spa.servicedealz.ui;

import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;

import com.localytics.android.Localytics;
import com.spa.servicedealz.R;
import com.spa.fragment.Preferences;
import com.spa.internet_connectivity.NetworkUtil;

/**
 * Created by Diwakar on 5/5/2016.
 */
public class PrefrenceActivity extends AppCompatActivity implements View.OnClickListener, NetworkUtil.changeNetworkInterFace {
    private Preferences mDashboardFragment;
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
        mDashboardFragment = Preferences.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mDashboardFragment)
                .commit();
        setActionbar();
    }

    /*method set to action bar */
    private void setActionbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        mToolbar.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Preferences" + "</font>")));
    }


    @Override
    public void onResume() {
        super.onResume();
        Localytics.tagScreen("PreferenceFragment");
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void updateNetwork(String s) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (item.getItemId() == android.R.id.home) {
            this.finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
