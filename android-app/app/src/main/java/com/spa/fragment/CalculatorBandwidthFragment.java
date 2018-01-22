package com.spa.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.spa.internet_connectivity.NetworkUtil;
import com.spa.seekbarhint.SeekBarHint;
import com.spa.servicedealz.R;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by E0147ambar on 6/30/2016.
 */
public class CalculatorBandwidthFragment extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener, SeekBarHint.OnSeekBarHintProgressChangeListener {
    private SharedPreferences mSharedPreferences;
    private Boolean isInternetPresent = false;
    private SeekBarHint mSeekBarEmail, mSeekBarWeb, mSeekBarVideo, mSeekBarImages, mSeekBarAudio, mSeekBarVideoStreaming;

    private Spinner spinnerDevice;
    private ProgressBar mProgressBarTotal;
    private TextView mTextViewTotal, mTextViewEmail, mTextViewVideo, mTextViewWeb, mTextViewImages, mTextViewAudio, mTextViewVideoStreaming, mTextViewCalculate;
    private Button mButtonCalculate;
    private int email, web, video, images, video_streming, audio;
    private String device;
    private ProgressDialog pDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calculator_fragment, viewGroup, false);

        mSharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        setMapingId(view);
        spinnerDevice.setOnItemSelectedListener(this);
        mButtonCalculate.setOnClickListener(this);
        // Spinner Drop down elements

        return view;
    }

    private void setMapingId(View view) {

        mSeekBarEmail = (SeekBarHint) view.findViewById(R.id.seekBar_email);
        mButtonCalculate = (Button) view.findViewById(R.id.btn_calculate);
        mSeekBarWeb = (SeekBarHint) view.findViewById(R.id.seekBar_web);
        mSeekBarVideo = (SeekBarHint) view.findViewById(R.id.seekBar_video);
        mSeekBarAudio = (SeekBarHint) view.findViewById(R.id.seekBar_audio);
        mSeekBarVideoStreaming = (SeekBarHint) view.findViewById(R.id.seekBar_streaming);
        mSeekBarImages = (SeekBarHint) view.findViewById(R.id.seekBar_photo);
        mProgressBarTotal = (ProgressBar) view.findViewById(R.id.progressBar);
        mTextViewTotal = (TextView) view.findViewById(R.id.txt_total_devices);
        mTextViewEmail = (TextView) view.findViewById(R.id.txt_value_email);
        mTextViewWeb = (TextView) view.findViewById(R.id.txt_value_web);
        mTextViewVideo = (TextView) view.findViewById(R.id.txt_value_video);
        mTextViewImages = (TextView) view.findViewById(R.id.txt_value_photo);
        mTextViewAudio = (TextView) view.findViewById(R.id.txt_value_audio);
        mTextViewVideoStreaming = (TextView) view.findViewById(R.id.txt_value_streaming);
        mTextViewCalculate = (TextView) view.findViewById(R.id.txt_calculate);
        spinnerDevice = (Spinner) view.findViewById(R.id.spinner);
        List<String> categories = new ArrayList<String>();
        categories.add("1");
        categories.add("2");
        categories.add("3");
        categories.add("4");
        categories.add("5");
        categories.add("6");
        categories.add("7");
        categories.add("8");
        categories.add("9");
        categories.add("10");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinnerDevice.setAdapter(dataAdapter);
        setSeekBarValue();
        // setSeekBarCahange();
    }

    private void setSeekBarValue() {

        mProgressBarTotal.setProgress(0);
        mTextViewAudio.setText(mSharedPreferences.getInt("audio_value", 500) + " Minutes");
        mTextViewVideoStreaming.setText(mSharedPreferences.getInt("streaming_value", 500) + " Minutes");
        mTextViewEmail.setText(mSharedPreferences.getInt("email_value", 50) + " Emails");
        mTextViewWeb.setText(mSharedPreferences.getInt("web_value", 250) + " Pages");
        mTextViewVideo.setText(mSharedPreferences.getInt("video_value", 50) + " Minutes");
        int pos = mSharedPreferences.getInt("NoofDevice", 0);
        spinnerDevice.setSelection(pos);
        mTextViewImages.setText(mSharedPreferences.getInt("images_value", 50) + " Photos");
        mSeekBarEmail.setMax(mSharedPreferences.getInt("email_value", 50));
        mSeekBarWeb.setMax(mSharedPreferences.getInt("web_value", 250));
        mSeekBarVideo.setMax(mSharedPreferences.getInt("video_value", 50));
        mSeekBarImages.setMax(mSharedPreferences.getInt("images_value", 50));
        mSeekBarAudio.setMax(mSharedPreferences.getInt("audio_value", 500));
        mSeekBarVideoStreaming.setMax(mSharedPreferences.getInt("streaming_value", 500));
        mSeekBarEmail.setProgress(mSharedPreferences.getInt("progress_email_value", 50));
        mSeekBarWeb.setProgress(mSharedPreferences.getInt("progress_web_value", 250));
        mSeekBarVideo.setProgress(mSharedPreferences.getInt("progress_video_value", 50));
        mSeekBarImages.setProgress(mSharedPreferences.getInt("progress_images_value", 50));
        mSeekBarAudio.setProgress(mSharedPreferences.getInt("progress_audio_value", 500));
        mSeekBarVideoStreaming.setProgress(mSharedPreferences.getInt("progress_streaming_value", 500));
        mSeekBarEmail.setListener(this);
        mSeekBarWeb.setListener(this);
        mSeekBarVideo.setListener(this);
        mSeekBarImages.setListener(this);
        mSeekBarAudio.setListener(this);
        mSeekBarVideoStreaming.setListener(this);


    }



  /*  @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int value = seekBar.getProgress(); //this is the value of the progress bar (1-100)
        //value = progress; //this should also work
        String valueString = value + ""; //this is the string that will be put above the slider
        seekBar.setThumb(writeOnDrawable(R.mipmap.tooltip_bg, valueString));
        seekBar.setMax(100);
    }*/



   /* @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_calculate:
                isInternetPresent = NetworkUtil.isConnectingToInternet(getActivity());
                if (isInternetPresent) {
                    GetTextData();
                    new HttpGetAsyncTask().execute();
                } else {
                    ShowDailog.Show_Alert_Dailog(getActivity());
                }
        }
    }

    private void GetTextData() {
        video_streming = mSeekBarVideoStreaming.getProgress();
        audio = mSeekBarAudio.getProgress();
        email = mSeekBarEmail.getProgress();
        web = mSeekBarWeb.getProgress();
        video = mSeekBarVideo.getProgress();
        images = mSeekBarImages.getProgress();
        device = spinnerDevice.getSelectedItem().toString();
    }

    @Override
    public String onProgressChanged(SeekBarHint seekBarHint, int progress) {
        return null;
    }

    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
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
                response = Jsondata.CalculateBandwidth(email, web, video, images, device, video_streming, audio, getActivity());

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
            try {
                pDialog.dismiss();
                mTextViewTotal.setText("Total Monthly Data Usage for " + spinnerDevice.getSelectedItem() + " Devices");
                // mProgressBarTotal.setProgress(Integer.parseInt(response));
                mTextViewCalculate.setText(response);
                mTextViewCalculate.setVisibility(View.VISIBLE);
            } catch (Exception e) {
            }

        }
    }
}
