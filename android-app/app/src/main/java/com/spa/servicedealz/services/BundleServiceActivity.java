package com.spa.servicedealz.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.localytics.android.Localytics;
import com.spa.fragment.DashboardFragment;
import com.spa.fragment.DealListFragment;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.DashboardActivity;
import com.spa.servicedealz.ui.WowScreenActivity;
import com.spa.utils.Constant;
import com.spa.utils.EditTextLocker;
import com.spa.utils.Jsondata;
import com.spa.utils.MenuItemGlobal;
import com.spa.utils.Validation;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by E0115Diwakar on 2/16/2015.
 */

/**
 * FileName : BundleServiceActivity
 * Description : Show Bundle service
 * Dependencies : Internet
 */
public class BundleServiceActivity extends Activity implements View.OnClickListener, NetworkUtil.changeNetworkInterFace {
    private Spinner mSpinnerVendor;
    private ProgressDialog pDialog;
    private SharedPreferences mSharedPreferences;
    private EditText mEditTextEndDate, mEditTextPrice, mEditTextStartDate, mEditTextFreeChannels, mEditTextPremiumChannels, mEditTextDownload,
            mEditTextUpload, mEditTextData, mEditTextMinutes, mEditTextText, mEditTextDataPlan, mEditTextDataSpeeds;
    public static int Year, Month, Day;
    private Button mButtonUpdate, mButtonUnsubscribe;
    static final int DATE_DIALOG_ID = 0, DATE_DIALOG_ID1 = 1;
    private TextView mTextViewTitle, mTextViewVendor, mTextViewEndDate, mTextViewPrice, mTextViewTalk, mTextViewFreeChannels, mTextViewDownload;
    private CheckBox mCheckBoxContract, mCheckBoxTalk, mCheckBoxText, mCheckBoxCable, mCheckBoxInternet, mCheckBoxTelephone;
    private Boolean isInternetPresent = false;
    private ArrayList<String> mVendorList = new ArrayList<String>();
    private ImageView mImageViewClose;
    private String isContract = "false", EndDate, ProviderId, Price = "0", StartDate, FreeChannels = "", PremiumChannels = "", Download = "", Upload = "", Minutes = "", Text = "", isTalk = "false", isText = "false", Bundle_package;
    private LinearLayout mLinearLayoutInternet, mLinearLayoutCable, mLinearLayoutTelephone;
    com.spa.model.bundleeservice.Bundle bundle;
    private SharedPreferences.Editor mEditor;
    String EditSrvice, AddService;
    int price ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bundle_services);
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
        MappingdId();
        setClickListner();
        setEditTextLocker();
        setCompulsoryfield();
        EditSrvice = mSharedPreferences.getString("EditService", null);
        AddService = mSharedPreferences.getString("AddService", null);
        if (EditSrvice.equalsIgnoreCase("EditService")) {

            mButtonUnsubscribe.setVisibility(View.VISIBLE);

        } else {
            mButtonUnsubscribe.setVisibility(View.GONE);
        }
        final Calendar c = Calendar.getInstance();
        Year = c.get(Calendar.YEAR);
        Month = c.get(Calendar.MONTH);
        Day = c.get(Calendar.DAY_OF_MONTH);
        c.before(Calendar.DATE);
        NetworkUtil.setChangeNetWorkListener(BundleServiceActivity.this);
        isInternetPresent = NetworkUtil.isConnectingToInternet(BundleServiceActivity.this);
        if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
            mButtonUnsubscribe.setText("Skip");
            mButtonUnsubscribe.setVisibility(View.VISIBLE);
        }
        /*check internet connection*/
        if (!isInternetPresent) {
            ShowDailog.Show_Alert_Dailog(this);
        } else {
            new HttpGetAsyncTask_vender().execute();
            new HttpGetAsyncTask_get().execute();
        }
    }

    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("BundleServiceActivity");
    }

    /*set compulsory field*/
    private void setCompulsoryfield() {
        mTextViewTitle.setText(getResources().getText(R.string.bundle_services));
        String text = "<font color=#4e4d4d>Vendor</font> <font color=#ff00000>*</font>";
        mTextViewTalk.setText(Html.fromHtml("<font color=#4e4d4d>Domestic</font> <font color=#ff00000>*</font>"));
        mTextViewEndDate.setText(Html.fromHtml("<font color=#4e4d4d>Contract Date</font> <font color=#ff00000>*</font>"));
        mTextViewPrice.setText(Html.fromHtml("<font color=#4e4d4d>Price($)</font> <font color=#ff00000>*</font>"));
        mTextViewDownload.setText(Html.fromHtml("<font color=#4e4d4d>Download (Mbps)</font> <font color=#ff00000>*</font>"));
        mTextViewFreeChannels.setText(Html.fromHtml("<font color=#4e4d4d>Channels</font> <font color=#ff00000>*</font>"));
    }

    /*set click listner*/
    private void setClickListner() {
        mCheckBoxTalk.setOnClickListener(this);
        mCheckBoxText.setOnClickListener(this);
        mButtonUnsubscribe.setOnClickListener(this);
        mEditTextStartDate.setOnClickListener(this);
        mEditTextEndDate.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
        mImageViewClose.setOnClickListener(this);
        mCheckBoxContract.setOnClickListener(this);
        mCheckBoxCable.setOnClickListener(this);
        mCheckBoxInternet.setOnClickListener(this);
        mCheckBoxInternet.setChecked(true);
        if (mCheckBoxInternet.isChecked()) {
            mLinearLayoutInternet.setVisibility(View.VISIBLE);
        } else {
            mLinearLayoutInternet.setVisibility(View.GONE);
        }
        mCheckBoxTelephone.setOnClickListener(this);
    }

    /*set edittext limitation in decimal point*/
    private void setEditTextLocker() {
        EditTextLocker decimalEditTextLocker = new EditTextLocker(mEditTextPrice);
        decimalEditTextLocker.limitFractionDigitsinDecimal(2);
        EditTextLocker decimalEditTextLockerDownload = new EditTextLocker(mEditTextDownload);
        decimalEditTextLockerDownload.limitFractionDigitsinDecimal(1);
        EditTextLocker decimalEditTextLockerUpload = new EditTextLocker(mEditTextUpload);
        decimalEditTextLockerUpload.limitFractionDigitsinDecimal(1);
    }

    /*
       * Method to Set text in rightside in view
       * */
    private void SetTextRight() {
        mEditTextPrice.setSelection(mEditTextPrice.getText().toString().length());
        mEditTextPrice.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextFreeChannels.setSelection(mEditTextFreeChannels.getText().toString().length());
        mEditTextFreeChannels.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextPremiumChannels.setSelection(mEditTextPremiumChannels.getText().toString().length());
        mEditTextPremiumChannels.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextDownload.setSelection(mEditTextDownload.getText().toString().length());
        mEditTextDownload.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextUpload.setSelection(mEditTextUpload.getText().toString().length());
        mEditTextUpload.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        mEditTextData.setSelection(mEditTextData.getText().toString().length());
        mEditTextData.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextMinutes.setSelection(mEditTextMinutes.getText().toString().length());
        mEditTextMinutes.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextText.setSelection(mEditTextText.getText().toString().length());
        mEditTextText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextDataPlan.setSelection(mEditTextDataPlan.getText().toString().length());
        mEditTextDataPlan.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextDataSpeeds.setSelection(mEditTextDataSpeeds.getText().toString().length());
        mEditTextDataSpeeds.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
    }

    /*set date in EditText*/
    private void updateDisplay() {
        if (Month >= 9 && Day >= 10) {
            mEditTextStartDate.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append(Month + 1).append("/")

                    .append(Day).append("/").append(Year));
        } else if (Month >= 9 && Day < 10) {
            mEditTextStartDate.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append(Month + 1).append("/")
                    .append("0").append(Day).append("/").append(Year));

        } else if (Month < 9 && Day >= 10) {
            mEditTextStartDate.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append("0").append(Month + 1)

                    .append("/").append(Day).append("/").append(Year));
        } else {
            mEditTextStartDate.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append("0").append(Month + 1)
                    .append("/")
                    .append("0").append(Day).append("/").append(Year));

        }
    }

    private DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Year = year;
            Month = monthOfYear;
            Day = dayOfMonth;

            updateDisplay();
        }
    };

    /*set date in EditText*/
    private void updateDisplay1() {
        if (Month >= 9 && Day >= 10) {
            mEditTextEndDate.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append(Month + 1).append("/")

                    .append(Day).append("/").append(Year));
        } else if (Month >= 9 && Day < 10) {
            mEditTextEndDate.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append(Month + 1).append("/")
                    .append("0").append(Day).append("/").append(Year));

        } else if (Month < 9 && Day >= 10) {
            mEditTextEndDate.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append("0").append(Month + 1)

                    .append("/").append(Day).append("/").append(Year));
        } else {
            mEditTextEndDate.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append("0").append(Month + 1)
                    .append("/")
                    .append("0").append(Day).append("/").append(Year));
        }
    }


    private DatePickerDialog.OnDateSetListener DateSetListener1 = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Year = year;
            Month = monthOfYear;
            Day = dayOfMonth;

            updateDisplay1();
        }
    };

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                DatePickerDialog da;

                da = new DatePickerDialog(this, DateSetListener, Year, Month, Day);

                da.setCanceledOnTouchOutside(false);
                da.setCancelable(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                    da.getDatePicker().setCalendarViewShown(false);
                    da = new DatePickerDialog(this, R.style.Base_Theme_AppCompat_Light_Dialog, DateSetListener, Year, Month, Day);

                } else {
                    da = new DatePickerDialog(this, DateSetListener, Year, Month, Day);
                }
                da.getDatePicker().setMaxDate(System.currentTimeMillis());
//                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                return da;
            case DATE_DIALOG_ID1:
                DatePickerDialog da1 = new DatePickerDialog(this, DateSetListener1,
                        Year, Month, Day);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                    da1.getDatePicker().setCalendarViewShown(false);
                    da1 = new DatePickerDialog(this, R.style.Base_Theme_AppCompat_Light_Dialog, DateSetListener1, Year, Month, Day);
                } else {
                    da1 = new DatePickerDialog(this, DateSetListener1, Year, Month, Day);

                }


                da1.setCanceledOnTouchOutside(false);
                da1.setCancelable(false);

                da1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                return da1;

        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
                Intent browserIntent1 = new Intent(getApplicationContext(), WowScreenActivity.class);
                startActivity(browserIntent1);
                mEditor.putString("signupwithfirsttime", "");
                mEditor.commit();

            }

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chk_talk:
                if (mCheckBoxTalk.isChecked()) {
                    mEditTextMinutes.setText("");
                    mEditTextMinutes.setClickable(false);
                    mEditTextMinutes.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                    mEditTextMinutes.setEnabled(false);
                } else {
                    mEditTextMinutes.setClickable(true);
                    mEditTextMinutes.setEnabled(true);
                    mEditTextMinutes.setBackgroundResource(R.drawable.edt_background);
                }
                break;
            case R.id.chk_text:
                if (mCheckBoxText.isChecked()) {
                    mEditTextText.setClickable(false);
                    mEditTextText.setText("");
                    mEditTextText.setEnabled(false);
                    mEditTextText.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                } else {
                    mEditTextText.setClickable(true);
                    mEditTextText.setEnabled(true);
                    mEditTextText.setBackgroundResource(R.drawable.edt_background);
                }
                break;
            case R.id.chk_contract:
                if (mCheckBoxContract.isChecked()) {
                    mEditTextEndDate.setText("");
                    mEditTextStartDate.setText("");
                    mEditTextEndDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                    mEditTextStartDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                } else {
                    mEditTextEndDate.setBackgroundResource(R.drawable.edt_background);
                    mEditTextStartDate.setBackgroundResource(R.drawable.edt_background);
                }
                break;
            case R.id.chk_cable:
                if (mCheckBoxCable.isChecked()) {
                    mLinearLayoutCable.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayoutCable.setVisibility(View.GONE);
                }
                break;
            case R.id.chk_internet:
                if (mCheckBoxInternet.isChecked()) {
                    mLinearLayoutInternet.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayoutInternet.setVisibility(View.GONE);
                }
                break;
            case R.id.chk_telephone:
                if (mCheckBoxTelephone.isChecked()) {
                    mLinearLayoutTelephone.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayoutTelephone.setVisibility(View.GONE);
                }
                break;
            case R.id.etx_start_date:
                if (mCheckBoxContract.isChecked()) {

                } else {
                    showDialog(DATE_DIALOG_ID);
                }
                break;

            case R.id.etx_end_date:
                if (mCheckBoxContract.isChecked()) {

                } else {
                    showDialog(DATE_DIALOG_ID1);
                }

                break;
            case R.id.img_close:
                if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
                    Intent browserIntent1 = new Intent(getApplicationContext(), WowScreenActivity.class);
                    startActivity(browserIntent1);
                    mEditor.putString("signupwithfirsttime", "");
                    mEditor.commit();

                }
                finish();

                break;
            case R.id.btn_update:
                GetTextData();
                if (!isInternetPresent) {
                    ShowDailog.Show_Alert_Dailog(this);

                } else {
                   /* if (!mSpinnerVendor.getSelectedItem().toString().equalsIgnoreCase("Select Vendor")) {
                        Toast.makeText(BundleServiceActivity.this, getResources().getString(R.string.select_vendor_error),
                                Toast.LENGTH_LONG).show();
                    }*/ if (Validation.isFieldEmpty(mEditTextPrice)) {
                        Toast.makeText(this, getResources().getString(R.string.price_error),
                                Toast.LENGTH_SHORT).show();
                    } else if (mCheckBoxInternet.isChecked() && mCheckBoxCable.isChecked() || mCheckBoxInternet.isChecked() && mCheckBoxTelephone.isChecked() || mCheckBoxCable.isChecked() && mCheckBoxTelephone.isChecked()) {
                        if (mCheckBoxInternet.isChecked() && mCheckBoxCable.isChecked() && mCheckBoxTelephone.isChecked()) {
                            Bundle_package = "Internet,Telephone and Cable";
                            if (Validation.isFieldEmpty(mEditTextFreeChannels) || Integer.parseInt(mEditTextFreeChannels.getText().toString()) <= 0) {
                                Toast.makeText(this, getResources().getString(R.string.freechannels_error),
                                        Toast.LENGTH_SHORT).show();
                            } else if (Validation.isFieldEmpty(mEditTextDownload) || Float.parseFloat(mEditTextDownload.getText().toString()) <= 0) {
                                Toast.makeText(this, getResources().getString(R.string.download_error),
                                        Toast.LENGTH_SHORT).show();
                            } else if (!mCheckBoxTalk.isChecked() && Validation.isFieldEmpty(mEditTextMinutes)) {
                                Toast.makeText(this, getResources().getString(R.string.minutes_error),
                                        Toast.LENGTH_SHORT).show();
                            } else if (!mCheckBoxTalk.isChecked() && Integer.parseInt(mEditTextMinutes.getText().toString()) <= 0) {
                                Toast.makeText(this, getResources().getString(R.string.minute_error),
                                        Toast.LENGTH_SHORT).show();
                            } else if (mCheckBoxContract.isChecked()) {
                                new HttpGetAsyncTask().execute();

                            } else {
                                if (Validation.isFieldEmpty(mEditTextStartDate)) {
                                    Toast.makeText(this, "Please Select Start Date",
                                            Toast.LENGTH_SHORT).show();

                                } else if (Validation.isFieldEmpty(mEditTextEndDate)) {
                                    Toast.makeText(this, "Please Select End Date",
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    new HttpGetAsyncTask().execute();
                                }
                            }
                        } else if (mCheckBoxInternet.isChecked() && mCheckBoxCable.isChecked()) {
                            Bundle_package = "Internet and Cable";

                            if (Validation.isFieldEmpty(mEditTextFreeChannels) || Integer.parseInt(mEditTextFreeChannels.getText().toString()) <= 0) {
                                Toast.makeText(this, getResources().getString(R.string.freechannels_error),
                                        Toast.LENGTH_SHORT).show();
                            } else if (Validation.isFieldEmpty(mEditTextDownload) || Float.parseFloat(mEditTextDownload.getText().toString()) <= 0) {
                                Toast.makeText(this, getResources().getString(R.string.download_error),
                                        Toast.LENGTH_SHORT).show();
                            } else if (mCheckBoxContract.isChecked()) {
                                new HttpGetAsyncTask().execute();

                            } else {
                                if (Validation.isFieldEmpty(mEditTextStartDate)) {
                                    Toast.makeText(this, "Please Select Start Date",
                                            Toast.LENGTH_SHORT).show();

                                } else if (Validation.isFieldEmpty(mEditTextEndDate)) {
                                    Toast.makeText(this, "Please Select End Date",
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    new HttpGetAsyncTask().execute();
                                }
                            }
                        } else if (mCheckBoxInternet.isChecked() && mCheckBoxTelephone.isChecked()) {
                            Bundle_package = "Internet and Telephone";
                            try {
                                if (Validation.isFieldEmpty(mEditTextDownload) || Float.parseFloat(mEditTextDownload.getText().toString()) <= 0) {
                                    Toast.makeText(this, getResources().getString(R.string.download_error),
                                            Toast.LENGTH_SHORT).show();
                                } else if (!mCheckBoxTalk.isChecked() && Validation.isFieldEmpty(mEditTextMinutes)) {
                                    Toast.makeText(this, getResources().getString(R.string.minutes_error),
                                            Toast.LENGTH_SHORT).show();
                                } else if (!mCheckBoxTalk.isChecked() && Integer.parseInt(mEditTextMinutes.getText().toString()) <= 0) {
                                    Toast.makeText(this, getResources().getString(R.string.minute_error),
                                            Toast.LENGTH_SHORT).show();
                                } else if (mCheckBoxContract.isChecked()) {
                                    new HttpGetAsyncTask().execute();

                                } else {
                                    if (Validation.isFieldEmpty(mEditTextStartDate)) {
                                        Toast.makeText(this, "Please Select Start Date",
                                                Toast.LENGTH_SHORT).show();

                                    } else if (Validation.isFieldEmpty(mEditTextEndDate)) {
                                        Toast.makeText(this, "Please Select End Date",
                                                Toast.LENGTH_SHORT).show();
                                    } else {

                                        new HttpGetAsyncTask().execute();
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else if (mCheckBoxCable.isChecked() && mCheckBoxTelephone.isChecked()) {
                            Bundle_package = "Telephone and Cable";
                            if (Validation.isFieldEmpty(mEditTextFreeChannels) || Integer.parseInt(mEditTextFreeChannels.getText().toString()) <= 0) {
                                Toast.makeText(this, getResources().getString(R.string.freechannels_error),
                                        Toast.LENGTH_SHORT).show();
                            } else if (!mCheckBoxTalk.isChecked() && Validation.isFieldEmpty(mEditTextMinutes)) {
                                Toast.makeText(this, getResources().getString(R.string.minutes_error),
                                        Toast.LENGTH_SHORT).show();
                            } else if (!mCheckBoxTalk.isChecked() && Integer.parseInt(mEditTextMinutes.getText().toString()) <= 0) {
                                Toast.makeText(this, getResources().getString(R.string.minute_error),
                                        Toast.LENGTH_SHORT).show();
                            } else if (mCheckBoxContract.isChecked()) {
                                new HttpGetAsyncTask().execute();

                            } else {
                                if (Validation.isFieldEmpty(mEditTextStartDate)) {
                                    Toast.makeText(this, "Please Select Start Date",
                                            Toast.LENGTH_SHORT).show();

                                } else if (Validation.isFieldEmpty(mEditTextEndDate)) {
                                    Toast.makeText(this, "Please Select End Date",
                                            Toast.LENGTH_SHORT).show();
                                } else {

                                    new HttpGetAsyncTask().execute();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.service_error),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.btn_unsubscribe:
                if (!mButtonUnsubscribe.getText().toString().equalsIgnoreCase("Skip")) {
                    if (!isInternetPresent) {
//                    Intent mServiceIntent =new Intent(BundleServiceActivity.this, DashboardActivity.class);
//                    startActivity(mServiceIntent);
                        ShowDailog.Show_Alert_Dailog(this);
                    } else {

                        new HttpGetAsyncTask_unsubscribe().execute();
                    }
                }else {
                    finish();
                }
                break;
        }
    }

    /*get data to view component*/
    private void GetTextData() {

        if (mCheckBoxContract.isChecked()) {
            isContract = "true";
        }
        if (mEditTextPrice.getText().toString().length() <= 0) {

        } else {
            Price = mEditTextPrice.getText().toString();
        }
        String provider = mSpinnerVendor.getSelectedItem().toString();

        int index = MenuItemGlobal.getIndex(provider, Jsondata.provider_id_list);
        ProviderId = Jsondata.provider_id_list.get(index).get("id").toString();
        EndDate = mEditTextEndDate.getText().toString();
        StartDate = mEditTextStartDate.getText().toString();

        if (mCheckBoxCable.isChecked()) {
            FreeChannels = mEditTextFreeChannels.getText().toString();
            PremiumChannels = mEditTextPremiumChannels.getText().toString();
        }
        if (mCheckBoxInternet.isChecked()) {
            Download = mEditTextDownload.getText().toString();
            Upload = mEditTextUpload.getText().toString();
        }
        if (mCheckBoxTelephone.isChecked()) {
            Minutes = mEditTextMinutes.getText().toString();
            Text = mEditTextText.getText().toString();
            if (mCheckBoxTalk.isChecked()) {
                isTalk = "true";
            }
            if (mCheckBoxText.isChecked()) {
                isText = "true";
            }
        }

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

    @Override
    public void updateNetwork(String s) {

    }

    /*Send service data to server in background*/
    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BundleServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.getBundleservices(mSharedPreferences.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.bundle_id)), ProviderId, EndDate, isContract, Price,
                        StartDate, Minutes, Text, isTalk, isText, Upload, Download, FreeChannels, PremiumChannels, Bundle_package, BundleServiceActivity.this);
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
            if (Jsondata.Success.equalsIgnoreCase(Constant.TRUE)) {
                DashboardFragment.ServiceSave = true;
                if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
                    Intent prefrence = new Intent(getApplicationContext(), WowScreenActivity.class);
                    startActivity(prefrence);
                    mEditor.putString("signupwithfirsttime", "");
                    mEditor.commit();

                }
                finish();

            } else {
                // Show_Dailog.Show_Alert_Login(Bundle_services.this, getResources().getText(R.string.server_error).toString());
            }
        }

    }

    /*unsubscribe service to database */
    class HttpGetAsyncTask_unsubscribe extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BundleServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.get_Unsuscribe_service(mSharedPreferences.getString(Constant.APP_USER_ID, ""), String.valueOf(getResources().getText(R.string.bundle_id)), BundleServiceActivity.this);
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
            if (Jsondata.Success.equalsIgnoreCase("true")) {
                DashboardFragment.ServiceSave = true;
                finish();
            } else {
                ShowDailog.Show_Alert_Login(BundleServiceActivity.this, getResources().getText(R.string.server_error).toString());
            }
        }

    }

    /*get service data in background*/
    class HttpGetAsyncTask_get extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BundleServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                bundle = Jsondata.getBundleservices_prefences(mSharedPreferences.getString(Constant.APP_USER_ID, ""), String.valueOf(getResources().getText(R.string.bundle_id)), BundleServiceActivity.this);
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
                if (bundle.getSuccess()) {
                    setDataViewComponent();

                } else {
                    // Show_Dailog.Show_Alert_Login(Bundle_services.this, getResources().getText(R.string.server_error).toString());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            SetTextRight();
        }

    }

    /*Set data in view component*/
    private void setDataViewComponent() {
//        mButtonUnsubscribe.setVisibility(View.VISIBLE);
        if (bundle.getServicePreference().getIsContract()) {
            mCheckBoxContract.setChecked(true);
            mEditTextEndDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
            mEditTextStartDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
        } else {
            mCheckBoxContract.setChecked(false);
            String[] departure = bundle.getServicePreference().getEndDate().split(" ");
            String[] start_date = bundle.getServicePreference().getStartDate().split(" ");
            mEditTextEndDate.setText(departure[0]);
            mEditTextStartDate.setText(start_date[0]);
        }
        mEditTextFreeChannels.setText("" + bundle.getServicePreference().getFreeChannels());
        if (bundle.getServicePreference().getBundleCombo().contains("Cable")) {
            mLinearLayoutCable.setVisibility(View.VISIBLE);
            mCheckBoxCable.setChecked(true);
        }
        mEditTextPremiumChannels.setText(bundle.getServicePreference().getPremiumChannels());

        mEditTextDownload.setText("" + bundle.getServicePreference().getDownloadSpeed());
        if (bundle.getServicePreference().getBundleCombo().contains("Internet")) {
            mLinearLayoutInternet.setVisibility(View.VISIBLE);
            mCheckBoxInternet.setChecked(true);
        }
        mEditTextUpload.setText(bundle.getServicePreference().getUploadSpeed());

        mEditTextMinutes.setText(bundle.getServicePreference().getDomesticCallMinutes());
        if (bundle.getServicePreference().getBundleCombo().contains("Telephone")) {
            mLinearLayoutTelephone.setVisibility(View.VISIBLE);
            mCheckBoxTelephone.setChecked(true);
        }
        mEditTextText.setText(bundle.getServicePreference().getInternationalCallMinutes());
//        mCheckBoxTalk.setChecked((bundle.getServicePreference().getDomesticCallUnlimited()));
        mCheckBoxText.setChecked(Boolean.parseBoolean((bundle.getServicePreference().getInternationalCallUnlimited())));

        if (mCheckBoxTalk.isChecked()) {

            mLinearLayoutTelephone.setVisibility(View.VISIBLE);
            mCheckBoxTelephone.setChecked(true);
            mEditTextMinutes.setText("");
            mEditTextMinutes.setClickable(false);
            mEditTextMinutes.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
            mEditTextMinutes.setEnabled(false);
        } else {
            mEditTextMinutes.setClickable(true);
            mEditTextMinutes.setEnabled(true);
            mEditTextMinutes.setBackgroundResource(R.drawable.edt_background);
        }
        if (mCheckBoxText.isChecked()) {
            mEditTextText.setClickable(false);
            mEditTextText.setText("");
            mEditTextText.setEnabled(false);
            mEditTextText.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
        } else {
            mEditTextText.setClickable(true);
            mEditTextText.setEnabled(true);
            mEditTextText.setBackgroundResource(R.drawable.edt_background);
        }
         price =bundle.getServicePreference().getPrice();

        mEditTextPrice.setText(""+ price);
//        mEditTextPrice.setText(String.format("%.2f",""+ bundle.getServicePreference().getPrice()));
        int index = MenuItemGlobal.getIndex("" + bundle.getServicePreference().getServiceProviderId(), Jsondata.provider_id_list);
        String provider_name = Jsondata.provider_id_list.get(index).get("provider_name").toString();
        int value = mVendorList.indexOf(provider_name);

        mSpinnerVendor.setSelection(value);
    }

    /*get vendor list in background*/
    class HttpGetAsyncTask_vender extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                mVendorList = Jsondata.getservices_provider(mSharedPreferences.getString(Constant.APP_USER_ID, ""), String.valueOf(getResources().getText(R.string.bundle_id)), BundleServiceActivity.this);
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
            String[] array = mVendorList.toArray(new String[mVendorList.size()]);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(BundleServiceActivity.this, android.R.layout.simple_spinner_dropdown_item, array); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerVendor.setAdapter(spinnerArrayAdapter);
            mSpinnerVendor.setOnItemSelectedListener(OnCatSpinnerCL);

        }

    }

    /* *
        * Mapping Id
        * **/
    private void MappingdId() {
        mTextViewTalk = (TextView) findViewById(R.id.text);
        mCheckBoxInternet = (CheckBox) findViewById(R.id.chk_internet);
        mCheckBoxCable = (CheckBox) findViewById(R.id.chk_cable);
        mCheckBoxTelephone = (CheckBox) findViewById(R.id.chk_telephone);
        mLinearLayoutCable = (LinearLayout) findViewById(R.id.ll_cable);
        mLinearLayoutCable.setVisibility(View.GONE);
        mLinearLayoutInternet = (LinearLayout) findViewById(R.id.ll_internet);
        mLinearLayoutInternet.setVisibility(View.GONE);
        mLinearLayoutTelephone = (LinearLayout) findViewById(R.id.ll_telephone);
        mLinearLayoutTelephone.setVisibility(View.GONE);
        mCheckBoxTalk = (CheckBox) findViewById(R.id.chk_talk);
        mCheckBoxText = (CheckBox) findViewById(R.id.chk_text);
        mEditTextFreeChannels = (EditText) findViewById(R.id.etx_freechannels);
        mEditTextPremiumChannels = (EditText) findViewById(R.id.etx_premium_channels);
        mEditTextDownload = (EditText) findViewById(R.id.spn_download);
        mEditTextUpload = (EditText) findViewById(R.id.spn_upload);
        mEditTextData = (EditText) findViewById(R.id.etx_data);
        mEditTextMinutes = (EditText) findViewById(R.id.etx_minutes);
        mEditTextText = (EditText) findViewById(R.id.etx_sms);
        mEditTextDataPlan = (EditText) findViewById(R.id.etx_dataplan);
        mEditTextDataSpeeds = (EditText) findViewById(R.id.etx_dataspeeds);
        mTextViewTitle = (TextView) findViewById(R.id.btn_title);
        mImageViewClose = (ImageView) findViewById(R.id.img_close);
        mCheckBoxContract = (CheckBox) findViewById(R.id.chk_contract);
        mButtonUpdate = (Button) findViewById(R.id.btn_update);
        mEditTextPrice = (EditText) findViewById(R.id.etx_price);
        mTextViewVendor = (TextView) findViewById(R.id.txt_vendor);
        mTextViewPrice = (TextView) findViewById(R.id.txt_price);
        mTextViewDownload = (TextView) findViewById(R.id.txt_download);
        mTextViewFreeChannels = (TextView) findViewById(R.id.txt_freechannels);
        mTextViewEndDate = (TextView) findViewById(R.id.txt_enddate);
        mEditTextEndDate = (EditText) findViewById(R.id.etx_end_date);
        mEditTextStartDate = (EditText) findViewById(R.id.etx_start_date);
        mSpinnerVendor = (Spinner) findViewById(R.id.spn_vender);
        mButtonUnsubscribe = (Button) findViewById(R.id.btn_unsubscribe);
    }
}
