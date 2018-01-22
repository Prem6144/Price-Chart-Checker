package com.spa.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.localytics.android.Localytics;
import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.DashboardActivity;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.prefrence.Prefrence;
import com.spa.utils.Constant;
import com.spa.utils.InputFilterMinMax;
import com.spa.utils.Jsondata;

import java.util.Arrays;

import co.spa.sidemenu.interfaces.ScreenShotable;

/**
 * FileName : Preferences
 * Description :
 * Dependencies : Internet
 */
public class Preferences extends Fragment implements ScreenShotable {
    private Button mButtonUpdate;
    private Boolean isInternetPresent = false;
    private RadioButton mRadioButtonYes, mRadioButtonNo;
    private RadioGroup mRadioGroup;
    private EditText mEditTextNotificationDay;
    private ProgressDialog pDialog;
    private SharedPreferences mSharedPreferences;
    private Prefrence prefrence;
    private CheckBox mCheckBoxTrendingDeals, mCheckBoxCallReceive, mCheckBoxReceiveEmails, mCheckBoxReceiveText;
    private Spinner mSpinnerFrequency, mSpinnerRatingsServiceProvider, mSpinnerRatingMax, mSpinnerNotificationRecive;
    String response = "", notify = "false", notify_trending = "false", day = "0", repeat_notification_frequency, trending_deal_frequency, receive_call = "false", min_service_provider_rating, min_deal_rating, receive_email = "false", receive_text = "false";

    public static Preferences newInstance() {
        Preferences contentFragment = new Preferences();
        return contentFragment;
    }

    /*
              * Method to Set text in rightside in view
              * */
    private void SetTextRight() {
        mEditTextNotificationDay.setSelection(mEditTextNotificationDay.getText().toString().length());
        mEditTextNotificationDay.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.preferences, container, false);
        mSharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        setMappingId(v);
        setDataChange();
        setSpinnerData();
        SetTextRight();
        return v;
    }

    private void setSpinnerData() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.frequency_array, R.layout.singleitem_list);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(), R.array.rating_array, R.layout.prefrece_spinner);
        adapter1.setDropDownViewResource(R.layout.prefrececlikadapter);
        mSpinnerNotificationRecive.setAdapter(adapter);
        mSpinnerFrequency.setAdapter(adapter);
        mSpinnerFrequency.setSelection(1);
        mSpinnerNotificationRecive.setSelection(1);
        mSpinnerRatingMax.setAdapter(adapter1);
        mSpinnerRatingMax.setSelection(2);
        mSpinnerRatingsServiceProvider.setAdapter(adapter1);
        mSpinnerRatingsServiceProvider.setSelection(2);
        mSpinnerNotificationRecive.setOnItemSelectedListener(OnCatSpinnerCL);
        mSpinnerFrequency.setOnItemSelectedListener(OnCatSpinnerCL);
        mSpinnerRatingMax.setOnItemSelectedListener(OnCatSpinnerCL1);
        mSpinnerRatingsServiceProvider.setOnItemSelectedListener(OnCatSpinnerCL1);
    }

    private void setDataChange() {
        mCheckBoxTrendingDeals.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mSpinnerFrequency.setEnabled(b);
            }
        });
        mEditTextNotificationDay.setFilters(new InputFilter[]{new InputFilterMinMax(0, 365)});
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (mRadioButtonNo.isChecked()) {
                   // mRadioButtonNo.setButtonTintMode();
                    mEditTextNotificationDay.setBackgroundResource(R.drawable.disable_edittext);
                    mEditTextNotificationDay.setClickable(false);
                    mEditTextNotificationDay.setFocusable(false);
                    mEditTextNotificationDay.setFocusableInTouchMode(false);
                    mEditTextNotificationDay.setText("");
                    final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
                } else {
                    mEditTextNotificationDay.setBackgroundResource(R.drawable.edt_background);
                    mEditTextNotificationDay.setClickable(true);
                    mEditTextNotificationDay.setFocusable(true);
                    mEditTextNotificationDay.setFocusableInTouchMode(true);
                    mEditTextNotificationDay.setText("30");
                }

            }
        });
        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetPresent) {
                    String zipcode_flag = mSharedPreferences.getString(Constant.ZIPCODE_FLAG, "");
                    if (zipcode_flag.equalsIgnoreCase("") || zipcode_flag.equals(null)) {
                        ShowDailog.Show_Alert_(getActivity(), "Please complete the profile", "Warning");
                    } else {
                        GetTextData();
                        if (mRadioButtonYes.isChecked()) {
                            if (mEditTextNotificationDay.getText().toString().length() > 0) {
                                new HttpGetAsyncTask_save().execute();
                            } else {
                                ShowDailog.Show_Alert_(getActivity(), "Please fill the days", "Warning");
                            }

                        } else {
                            new HttpGetAsyncTask_save().execute();
                        }
                    }
                } else {
                    ShowDailog.Show_Alert_(getActivity(), "Please connect to the internet", "Warning");
                }

            }
        });
    }

    private void GetTextData() {
        if (mRadioButtonYes.isChecked()) {
            notify = "true";
            day = mEditTextNotificationDay.getText().toString();
        }
        if (mCheckBoxTrendingDeals.isChecked()) {
            notify_trending = "true";
            trending_deal_frequency = mSpinnerFrequency.getSelectedItem().toString();
        }
        if (mCheckBoxCallReceive.isChecked()) {
            receive_call = "true";
        }
        if (mCheckBoxReceiveEmails.isChecked()) {
            receive_email = "true";
        }
        if (mCheckBoxReceiveText.isChecked()) {
            receive_text = "true";
        }
        min_service_provider_rating = "" + mSpinnerRatingsServiceProvider.getSelectedItem().toString().replaceAll(" ", "").length();
        min_deal_rating = "" + mSpinnerRatingMax.getSelectedItem().toString().replaceAll(" ", "").length();
        repeat_notification_frequency = mSpinnerNotificationRecive.getSelectedItem().toString();
    }

    private void setMappingId(View v) {
        mEditTextNotificationDay = (EditText) v.findViewById(R.id.etx_day);
        mSpinnerNotificationRecive = (Spinner) v.findViewById(R.id.etx_day1);
        mButtonUpdate = (Button) v.findViewById(R.id.btn_update);
        mCheckBoxTrendingDeals = (CheckBox) v.findViewById(R.id.chk_trending);
        mCheckBoxCallReceive = (CheckBox) v.findViewById(R.id.chk_call_receive);
        mCheckBoxReceiveEmails = (CheckBox) v.findViewById(R.id.chk_receive_emails);
        mCheckBoxReceiveText = (CheckBox) v.findViewById(R.id.chk_receive_text);
        mRadioButtonYes = (RadioButton) v.findViewById(R.id.radioGroupButton0);
        mSpinnerFrequency = (Spinner) v.findViewById(R.id.spn_frequency);
        mSpinnerRatingsServiceProvider = (Spinner) v.findViewById(R.id.spn_ratings);
        mSpinnerRatingMax = (Spinner) v.findViewById(R.id.spn_ratingsmax);
        mRadioGroup = (RadioGroup) v.findViewById(R.id.rg);
        mRadioButtonNo = (RadioButton) v.findViewById(R.id.radioGroupButton1);
    }

    private AdapterView.OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            try {


                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                ((TextView) parent.getChildAt(0)).setTextSize(14);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private AdapterView.OnItemSelectedListener OnCatSpinnerCL1 = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            try {


                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                ((TextView) parent.getChildAt(0)).setTextSize(22);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
        isInternetPresent = NetworkUtil.isConnectingToInternet(getActivity());
        if (isInternetPresent) {
            new HttpGetAsyncTask().execute();
        } else {
            ShowDailog.Show_Alert_Dailog(getActivity());
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Localytics.tagScreen("PreferenceFragment");
    }

    @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
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
                prefrence = Jsondata.get_prefrences(mSharedPreferences.getString(Constant.APP_USER_ID, ""), getActivity());

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
            try {
                if (prefrence.getSuccess()) {
                    Boolean trending_notification = prefrence.getNotification().getRecieveTrendingDeals();
                    mCheckBoxTrendingDeals.setChecked(trending_notification);
                    String day = "" + prefrence.getNotification().getDay();
                    if (prefrence.getNotification().getRecieveNotification()) {
                        mRadioButtonYes.setChecked(true);
                        mRadioButtonNo.setChecked(false);
                        mEditTextNotificationDay.setText(day);
                        Editable etext = mEditTextNotificationDay.getText();
                        Selection.setSelection(etext, day.length());
                    } else {
                        mRadioButtonYes.setChecked(false);
                        mRadioButtonNo.setChecked(true);
                    }
                    mCheckBoxCallReceive.setChecked(prefrence.getNotification().getReceiveCall());
                    mCheckBoxReceiveEmails.setChecked(prefrence.getNotification().getReceiveEmail());
                    mCheckBoxReceiveText.setChecked(prefrence.getNotification().getReceiveText());
                    int index = Arrays.asList(getResources().getStringArray(R.array.frequency_array)).indexOf(prefrence.getNotification().getTrendingDealFrequency());
                    mSpinnerFrequency.setSelection(index);
                    int idex1 = Arrays.asList(getResources().getStringArray(R.array.frequency_array)).indexOf(prefrence.getNotification().getRepeatNotificationFrequency());
                    mSpinnerNotificationRecive.setSelection(idex1);
                    mSpinnerRatingsServiceProvider.setSelection(5 - prefrence.getNotification().getMinServiceProviderRating());
                    mSpinnerRatingMax.setSelection(5 - prefrence.getNotification().getMinDealRating());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }

    class HttpGetAsyncTask_save extends AsyncTask<String, Void, String> {

        private ProgressDialog pDialog;

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
                response = Jsondata.get_save_prefrences(mSharedPreferences.getString(Constant.APP_USER_ID, ""), notify, day, notify_trending, repeat_notification_frequency, trending_deal_frequency, receive_call, min_service_provider_rating, min_deal_rating, receive_email, receive_text, getActivity());
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
            String zipcode_flag = mSharedPreferences.getString(Constant.ZIPCODE_FLAG, "");
            String service_flag = mSharedPreferences.getString(Constant.SERVICE_FLAG, "");
            if (zipcode_flag.equalsIgnoreCase("") || zipcode_flag.equals(null)) {
                Toast toast = Toast.makeText(getActivity(),
                        getResources().getString(R.string.suceessfully), Toast.LENGTH_SHORT);
                toast.show();
            } /*else if (service_flag.equalsIgnoreCase("") || service_flag.equals(null)) {
                Toast toast = Toast.makeText(getActivity(),
                        getResources().getString(R.string.suceessfully), Toast.LENGTH_SHORT);
                toast.show();
            } */else {
                Intent HOME = new Intent(getActivity().getApplicationContext(), DashboardActivity.class);
                startActivity(HOME);
                getActivity().finish();
            }


        }
    }
}


