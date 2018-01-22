package com.spa.servicedealz.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.localytics.android.Localytics;
import com.spa.model.channels.Channel;
import com.spa.model.channels.ChannelList;
import com.spa.model.channels.Channel_;
import com.spa.servicedealz.R;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;

import java.util.List;

/**
 * Created by Narendra on 8/2/2016.
 */
public class ChannelListActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channellist);
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
        setActionBar();
        MapIngId();

        new HttpGetAsyncTask().execute();
    }

    private void MapIngId() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("SignUpActivity");
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

    /*set action bar*/
    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        toolbar.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Channel List" + "</font>")));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }

    }

    public void addChannels(List<Channel> channels) {
        LinearLayout mainLinearLayout = (LinearLayout) findViewById(R.id.main_linear_layout);
        for (int i = 0; i < channels.size(); i++) {
            Channel channel = channels.get(i);

            LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            View row = (View) inflater.inflate(R.layout.channel_item, null);
            TextView channelType = (TextView) row.findViewById(R.id.channel_type);
            channelType.setText(channel.getCategoryName());
            LinearLayout ll = (LinearLayout) row.findViewById(R.id.linearLayout_channel);
            populateLinks(ll, channel.getChannel());

            mainLinearLayout.addView(row);
        }
    }

    private void populateLinks(LinearLayout ll, List<Channel_> channels) {

        Display display = getWindowManager().getDefaultDisplay();
        int maxWidth = display.getWidth() - 10;

        if (channels.size() > 0) {
            LinearLayout llAlso = new LinearLayout(this);
            llAlso.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            llAlso.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtSample = new TextView(this);
            txtSample.setText("");

            llAlso.addView(txtSample);
            txtSample.measure(0, 0);

            int widthSoFar = txtSample.getMeasuredWidth();
            for (Channel_ samItem : channels) {
                TextView txtSamItem = new TextView(this, null,
                        android.R.attr.textColorLink);
                LinearLayout.LayoutParams buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                buttonLayoutParams.setMargins(50, 20, 0, 0);
                txtSamItem.setLayoutParams(buttonLayoutParams);

                txtSamItem.setText(samItem.getChannelName());
                txtSamItem.setPadding(30, 30, 30, 30);
                txtSamItem.setTag(samItem);
                txtSamItem.setSingleLine(true);
                txtSamItem.setBackgroundResource(R.drawable.edt_background);

                txtSamItem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                txtSamItem.measure(0, 0);
                widthSoFar += txtSamItem.getMeasuredWidth();

                if (widthSoFar >= maxWidth) {
                    ll.addView(llAlso);

                    llAlso = new LinearLayout(this);
                    llAlso.setLayoutParams(new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT));
                    llAlso.setOrientation(LinearLayout.HORIZONTAL);

                    llAlso.addView(txtSamItem);
                    widthSoFar = txtSamItem.getMeasuredWidth();
                } else {
                    llAlso.addView(txtSamItem);
                }
            }

            ll.addView(llAlso);
        }
    }

    class HttpGetAsyncTask extends AsyncTask<Void, Void, ChannelList> {
        String response = "";
        ProgressDialog progress;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progress = ProgressDialog.show(ChannelListActivity.this, "Please wait",
                    "Channel list are downloading...", true);
        }

        @Override
        protected ChannelList doInBackground(Void... params) {
            ChannelList channelList = null;
            try {
                channelList = Jsondata.getChannelList(getIntent().getStringExtra("Dealid"), ChannelListActivity.this);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return channelList;
        }

       /* *
         * After completing background task Dismiss the progress dialog
         * **/

        protected void onPostExecute(ChannelList channelList) {
            progress.dismiss();
            if (channelList != null) {
                addChannels(channelList.getChannels());

            }
        }
    }
}
