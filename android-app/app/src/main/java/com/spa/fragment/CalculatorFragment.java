package com.spa.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.localytics.android.Localytics;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.servicedealz.R;
import com.spa.utils.Constant;

import java.util.HashMap;
import java.util.Map;

import co.spa.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Diwakar on 7/7/2016.
 */
public class CalculatorFragment extends Fragment implements ScreenShotable, View.OnClickListener {
    private Boolean isInternetPresent = false;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ProgressDialog pDialog;
    private LinearLayout mLinearLayoutLow, mLinearLayoutMedium, mLinearLayoutHigh;
    private TextView mTextViewLow, mTextViewMedium, mTextViewHigh;

    public static CalculatorFragment newInstance() {
        CalculatorFragment contentFragment = new CalculatorFragment();
        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //==========================================================================================
        View rootView = inflater.inflate(R.layout.distr, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mSharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        mEditor = mSharedPreferences.edit();
        MapingId(rootView);
        isInternetPresent = NetworkUtil.isConnectingToInternet(getActivity());
        if (isInternetPresent) {
            // new HttpGetAsyncTask_Get_Data().execute();
        } else {
            // ShowDailog.Show_Alert_Dailog(getActivity());
        }
        return rootView;
    }


    private void MapingId(View rootView) {
        mLinearLayoutLow = (LinearLayout) rootView.findViewById(R.id.low);
        mLinearLayoutMedium = (LinearLayout) rootView.findViewById(R.id.medium);
        mLinearLayoutHigh = (LinearLayout) rootView.findViewById(R.id.high);
        mTextViewLow = (TextView) rootView.findViewById(R.id.low_text);
        mTextViewMedium = (TextView) rootView.findViewById(R.id.medium_text);
        mTextViewHigh = (TextView) rootView.findViewById(R.id.high_text);
        mLinearLayoutLow.setOnClickListener(this);
        mLinearLayoutMedium.setOnClickListener(this);
        mLinearLayoutHigh.setOnClickListener(this);
        mLinearLayoutLow.setBackgroundColor(Color.parseColor("#248dc2"));
        mLinearLayoutMedium.setBackgroundColor(Color.parseColor("#ffffff"));
        mLinearLayoutHigh.setBackgroundColor(Color.parseColor("#ffffff"));
        mTextViewLow.setTextColor(Color.parseColor("#ffffff"));
        mTextViewMedium.setTextColor(Color.parseColor("#248dc2"));
        mTextViewHigh.setTextColor(Color.parseColor("#248dc2"));
        mEditor.putInt("email_value", 50);
        mEditor.putInt("web_value", 250);
        mEditor.putInt("video_value", 50);
        mEditor.putInt("images_value", 50);
        mEditor.putInt("audio_value", 500);
        mEditor.putInt("streaming_value", 500);
        mEditor.putInt("NoofDevice",0);
        mEditor.putInt("progress_email_value", 10);
        mEditor.putInt("progress_web_value", 100);
        mEditor.putInt("progress_video_value", 10);
        mEditor.putInt("progress_images_value", 10);
        mEditor.putInt("progress_audio_value", 100);
        mEditor.putInt("progress_streaming_value", 20);
        mEditor.commit();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.output, new CalculatorBandwidthFragment());
        transaction.commit();
    }

    @Override
    public void takeScreenShot() {
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public void onClick(View view) {

        Fragment fragment = null;
        switch (view.getId()) {
            case R.id.low:
                mLinearLayoutLow.setBackgroundColor(Color.parseColor("#248dc2"));
                mLinearLayoutMedium.setBackgroundColor(Color.parseColor("#ffffff"));
                mLinearLayoutHigh.setBackgroundColor(Color.parseColor("#ffffff"));
                mTextViewLow.setTextColor(Color.parseColor("#ffffff"));
                mTextViewMedium.setTextColor(Color.parseColor("#248dc2"));
                mTextViewHigh.setTextColor(Color.parseColor("#248dc2"));
                mEditor.putInt("email_value", 50);
                mEditor.putInt("NoofDevice",0);
                mEditor.putInt("web_value", 250);
                mEditor.putInt("video_value", 50);
                mEditor.putInt("images_value", 50);
                mEditor.putInt("audio_value", 500);
                mEditor.putInt("streaming_value", 500);
                mEditor.putInt("progress_email_value", 10);
                mEditor.putInt("progress_web_value", 100);
                mEditor.putInt("progress_video_value", 10);
                mEditor.putInt("progress_images_value", 10);
                mEditor.putInt("progress_audio_value", 100);
                mEditor.putInt("progress_streaming_value", 20);
                fragment = new CalculatorBandwidthFragment();
                break;

            case R.id.medium:
                mLinearLayoutLow.setBackgroundColor(Color.parseColor("#ffffff"));
                mLinearLayoutMedium.setBackgroundColor(Color.parseColor("#248dc2"));
                mLinearLayoutHigh.setBackgroundColor(Color.parseColor("#ffffff"));
                mTextViewLow.setTextColor(Color.parseColor("#248dc2"));
                mTextViewMedium.setTextColor(Color.parseColor("#ffffff"));
                mTextViewHigh.setTextColor(Color.parseColor("#248dc2"));
                mEditor.putInt("email_value", 100);
                mEditor.putInt("web_value", 500);
                mEditor.putInt("NoofDevice",4);
                mEditor.putInt("video_value", 100);
                mEditor.putInt("images_value", 100);
                mEditor.putInt("audio_value", 1000);
                mEditor.putInt("streaming_value", 1000);
                mEditor.putInt("progress_email_value", 25);
                mEditor.putInt("progress_web_value", 150);
                mEditor.putInt("progress_video_value", 25);
                mEditor.putInt("progress_images_value", 25);
                mEditor.putInt("progress_audio_value", 250);
                mEditor.putInt("progress_streaming_value", 250);
                fragment = new CalculatorBandwidthFragment();
                break;

            case R.id.high:
                mLinearLayoutLow.setBackgroundColor(Color.parseColor("#ffffff"));
                mLinearLayoutMedium.setBackgroundColor(Color.parseColor("#ffffff"));
                mLinearLayoutHigh.setBackgroundColor(Color.parseColor("#248dc2"));
                mTextViewLow.setTextColor(Color.parseColor("#248dc2"));
                mTextViewMedium.setTextColor(Color.parseColor("#248dc2"));
                mTextViewHigh.setTextColor(Color.parseColor("#ffffff"));
                mEditor.putInt("email_value", 200);
                mEditor.putInt("web_value", 1000);
                mEditor.putInt("NoofDevice", 9);
                mEditor.putInt("video_value", 200);
                mEditor.putInt("images_value", 200);
                mEditor.putInt("audio_value", 2000);
                mEditor.putInt("streaming_value", 2000);
                mEditor.putInt("progress_email_value", 200);
                mEditor.putInt("progress_web_value", 1000);
                mEditor.putInt("progress_video_value", 200);
                mEditor.putInt("progress_images_value", 200);
                mEditor.putInt("progress_audio_value", 2000);
                mEditor.putInt("progress_streaming_value", 2000);
                fragment = new CalculatorBandwidthFragment();
                break;

        }
        mEditor.commit();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.output, fragment);
        transaction.commit();


    }

    class HttpGetAsyncTask_Get_Data extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                // myEarnings = Jsondata.get_orderhistory("app_user_id=" + mSharedPreferences.getString(Constant.APP_USER_ID, ""), getActivity());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress dialog
         * **/

        protected void onPostExecute(String response) {
            pDialog.dismiss();

        }
    }

    public void localyticstagEvent(String method) {
        Map<String, String> home_values = new HashMap<String, String>();

        home_values.put("Success", "Yes");
        home_values.put("Method", method);

        Localytics.tagEvent("home", home_values);

    }


}
