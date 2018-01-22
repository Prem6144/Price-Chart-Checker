package com.spa.servicedealz.ui;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.localytics.android.Localytics;
import com.spa.fragment.DashboardFragment;
import com.spa.fragment.DealListFragment;
import com.spa.servicedealz.R;
import com.spa.internet_connectivity.NetworkUtil;

import java.util.ArrayList;

/**
 * FileName : ChartActivity
 * Description :show Chart
 * Dependencies : Internet
 */
public class ChartActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private BarChart mChart;
    private Boolean mIsInternetPresent = false;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.setThreadPolicy(new Builder().permitAll().build());
        setContentView(R.layout.chart_activity);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        setActionbar();
        setChartValue();


    }

    /*Show Chart*/
    private void setChartValue() {
        mIsInternetPresent = NetworkUtil.isConnectingToInternet(ChartActivity.this);
        if (!mIsInternetPresent) {
            // Internet Connection is not present
            Toast.makeText(ChartActivity.this, "No Internet Connection!",
                    Toast.LENGTH_SHORT).show();
            // stop executing code by return
            return;
        } else {
            mChart = (BarChart) findViewById(R.id.chart);
            BarData data = new BarData(getXAxisValues(), getDataSet());
            mChart.setData(data);
            mChart.animateXY(2000, 2000);
            mChart.setDescription("");
            mChart.getAxisLeft().setStartAtZero(!mChart.getAxisLeft().isStartAtZeroEnabled());
            mChart.getAxisRight().setStartAtZero(!mChart.getAxisRight().isStartAtZeroEnabled());
            mChart.invalidate();
        }
    }

    /*Method set action bar */
    private void setActionbar() {
        this.mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(this.mToolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        this.mToolbar.setTitle(Html.fromHtml("<font color=\"#FFFFFF\">Report Chart</font>"));
    }

    /*
    * Method to getData and set
    * */
    private ArrayList<BarDataSet> getDataSet() {
        ArrayList<BarEntry> valueSet1 = new ArrayList();
        ArrayList<BarEntry> valueSet2 = new ArrayList();
        ArrayList<BarEntry> valueSet3 = new ArrayList();
        for (int i = 0; DashboardFragment.chartDataSetArrayList.size() > i; i++) {
            valueSet1.add(new BarEntry((DashboardFragment.chartDataSetArrayList.get(i)).getCurrent_price(), i));
            valueSet2.add(new BarEntry(Float.parseFloat((DashboardFragment.chartDataSetArrayList.get(i)).getDealprice()), i));
            valueSet3.add(new BarEntry(Float.parseFloat((DashboardFragment.chartDataSetArrayList.get(i)).getYousave()), i));
        }
        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "Current Deal price");
        barDataSet1.setColor(Color.rgb(0, 155, 155));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "Best Deal price");
        barDataSet2.setColor(Color.rgb(255, 0, 0));
        BarDataSet barDataSet3 = new BarDataSet(valueSet3, "Save");
        barDataSet3.setColor(Color.rgb(0, 255, 0));
        ArrayList<BarDataSet> dataSets = new ArrayList();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        dataSets.add(barDataSet3);
        return dataSets;
    }

    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("ChartActivity");
    }

    /*
           * Method to get Axis Value
           * */
    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList();
        for (int i = 0; DashboardFragment.chartDataSetArrayList.size() > i; i++) {
            xAxis.add((DashboardFragment.chartDataSetArrayList.get(i)).getCategory_name());
        }
        return xAxis;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
