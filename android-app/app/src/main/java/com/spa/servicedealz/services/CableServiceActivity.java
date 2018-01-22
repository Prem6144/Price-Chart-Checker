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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.localytics.android.Localytics;
import com.spa.fragment.DashboardFragment;
import com.spa.fragment.DealListFragment;
import com.spa.model.internetservice.Internet;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.WowScreenActivity;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.cableservice.Cable;
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
 * FileName : CableServiceActivity
 * Description : Show Cable service
 * Dependencies : Internet
 */
public class CableServiceActivity extends Activity implements View.OnClickListener, NetworkUtil.changeNetworkInterFace {

    private Spinner mSpinnerVendor;
    private ProgressDialog pDialog;
    private EditText mEditTextEndDate, mEditTextPrice, mEditTextPremiumChannels, mEditTextFreeChannels, mEditTextStartDate;
    public static int Year, Month, month, day, Day, position, count;
    private Button mButtonUpdate, mButtonUnsubscribe;
    static final int DATE_DIALOG_ID = 0, DATE_DIALOG_ID1 = 1;
    private TextView mTextViewTitle, mTextViewVendor, mTextViewPrice, mTextViewEndDate, mTextViewFreeChannels;
    private CheckBox mCheckBoxContract;
    private Boolean isInternetPresent = false;
    private ArrayList<String> mVendorList = new ArrayList<String>();
    private ImageView mImageViewClose;
    private String isContract = "false", price = "0", provider_id, end_date, start_date, free_channels, premioum_channels;
    private Cable cable;
    String EditSrvice, AddService;
    private SharedPreferences mSharedPreferences;
    private Internet internet = null;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cable_services);
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
        NetworkUtil.setChangeNetWorkListener(CableServiceActivity.this);
        isInternetPresent = NetworkUtil.isConnectingToInternet(CableServiceActivity.this);
        if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
            mButtonUnsubscribe.setText("Skip");
            mButtonUnsubscribe.setVisibility(View.VISIBLE);
        }
        if (!isInternetPresent) {
            ShowDailog.Show_Alert_Dailog(this);
        } else {
            new HttpGetAsyncTask_vender().execute();
            new HttpGetAsyncTask_get().execute();
        }
    }

    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("CableServiceActivity");
    }

    /*set click listner*/
    private void setClickListner() {
        mEditTextStartDate.setOnClickListener(this);
        mEditTextEndDate.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
        mCheckBoxContract.setOnClickListener(this);
        mImageViewClose.setOnClickListener(this);
        mButtonUnsubscribe.setOnClickListener(this);
    }

    /*set edittext limitation in decimal point*/
    private void setEditTextLocker() {
        EditTextLocker decimalEditTextLocker = new EditTextLocker(mEditTextPrice);
        decimalEditTextLocker.limitFractionDigitsinDecimal(2);
    }

    /*set compulsory field*/
    private void setCompulsoryfield() {
        mTextViewTitle.setText(getResources().getText(R.string.cable_services));
        mTextViewFreeChannels.setText(Html.fromHtml("<font color=#4e4d4d>Channels</font> <font color=#ff00000>*</font>"));
        String text = "<font color=#4e4d4d>Vendor</font> <font color=#ff00000>*</font>";
        mTextViewEndDate.setText(Html.fromHtml("<font color=#4e4d4d>Contract Date</font> <font color=#ff00000>*</font>"));
        mTextViewPrice.setText(Html.fromHtml("<font color=#4e4d4d>Price($)</font> <font color=#ff00000>*</font>"));

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

    /* *
            * Mapping Id
            * **/
    private void MappingdId() {
        mEditTextFreeChannels = (EditText) findViewById(R.id.etx_freechannels);
        mEditTextPremiumChannels = (EditText) findViewById(R.id.etx_premium_channels);
        mTextViewTitle = (TextView) findViewById(R.id.btn_title);
        mImageViewClose = (ImageView) findViewById(R.id.img_close);
        mCheckBoxContract = (CheckBox) findViewById(R.id.chk_contract);
        mButtonUpdate = (Button) findViewById(R.id.btn_update);
        mEditTextPrice = (EditText) findViewById(R.id.etx_price);
        mTextViewVendor = (TextView) findViewById(R.id.txt_vendor);
        mTextViewPrice = (TextView) findViewById(R.id.txt_price);
        mTextViewFreeChannels = (TextView) findViewById(R.id.txt_freechannel);
        mTextViewEndDate = (TextView) findViewById(R.id.txt_enddate);
        mEditTextEndDate = (EditText) findViewById(R.id.etx_end_date);
        mEditTextStartDate = (EditText) findViewById(R.id.etx_start_date);
        mSpinnerVendor = (Spinner) findViewById(R.id.spn_vender);
        mButtonUnsubscribe = (Button) findViewById(R.id.btn_unsubscribe);
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


    private DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Year = year;
            Month = monthOfYear;
            Day = dayOfMonth;

            updateDisplay();
        }
    };
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
                return da;
            case DATE_DIALOG_ID1:
                DatePickerDialog da1;
                da1 = new DatePickerDialog(this, DateSetListener1, Year, Month, Day);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                    da1.getDatePicker().setCalendarViewShown(false);
                    da1 = new DatePickerDialog(this, R.style.Base_Theme_AppCompat_Light_Dialog, DateSetListener1, Year, Month, Day);
                } else {
                    da1 = new DatePickerDialog(this, DateSetListener1, Year, Month, Day);

                }
                da1.setCanceledOnTouchOutside(false);
                da1.setCancelable(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                    da1.getDatePicker().setCalendarViewShown(false);

                }
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

            case R.id.chk_contract:
                if (mCheckBoxContract.isChecked()) {
                    mEditTextEndDate.setText("");
                    mEditTextStartDate.setText("");
                    mEditTextStartDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                    mEditTextEndDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                } else {
                    mEditTextEndDate.setBackgroundResource(R.drawable.edt_background);
                    mEditTextStartDate.setBackgroundResource(R.drawable.edt_background);
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
                if (!isInternetPresent) {
                    ShowDailog.Show_Alert_Dailog(this);
                } else {
                    GetTextData();
                    /*if (mSpinnerVendor.getSelectedItem().toString().equalsIgnoreCase("Select Vendor")) {
                        Toast.makeText(CableServiceActivity.this, getResources().getString(R.string.select_vendor_error),
                                Toast.LENGTH_LONG).show();
                    } else*/
                    if (Validation.isFieldEmpty(mEditTextPrice)) {
                        Toast.makeText(this, getResources().getString(R.string.price_error),
                                Toast.LENGTH_SHORT).show();
                    } else if (Validation.isFieldEmpty(mEditTextFreeChannels) || Integer.parseInt(mEditTextFreeChannels.getText().toString()) <= 0) {
                        Toast.makeText(this, getResources().getString(R.string.freechannels_error),
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


                break;
            case R.id.btn_unsubscribe:
                if (!isInternetPresent) {
                    ShowDailog.Show_Alert_Dailog(this);
                } else {
                    if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
                        Intent welcome = new Intent(getApplicationContext(), CellPhoneServiceActivity.class);
                        startActivity(welcome);
                        finish();
                    } else {
                        new HttpGetAsyncTask_unsubscribe().execute();
                    }
                }


                break;
        }
    }

    /*get data to view component*/
    private void GetTextData() {

        if (mCheckBoxContract.isChecked()) {
            isContract = "true";
        }
        price = mEditTextPrice.getText().toString();
        String provider = mSpinnerVendor.getSelectedItem().toString();
        int index = MenuItemGlobal.getIndex(provider, Jsondata.provider_id_list);
        provider_id = Jsondata.provider_id_list.get(index).get("id").toString();
        end_date = mEditTextEndDate.getText().toString();
        start_date = mEditTextStartDate.getText().toString();
        free_channels = mEditTextFreeChannels.getText().toString();
        premioum_channels = mEditTextPremiumChannels.getText().toString();
    }

    @Override
    public void updateNetwork(String s) {

    }

    /*Send service data to server in background*/
    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CableServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage(Constant.WAIT);

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                response = Jsondata.get_cableservices(mSharedPreferences.getString(Constant.APP_USER_ID, ""), String.valueOf(getResources().getText(R.string.cable_id)), provider_id, end_date, isContract, price, start_date, free_channels, premioum_channels, CableServiceActivity.this);

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
                if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
                    Intent browserIntent1 = new Intent(getApplicationContext(), CellPhoneServiceActivity.class);
                    startActivity(browserIntent1);
                }
                finish();
               /* if (getIntent().getIntExtra("bestdealflag", 0) == 1) {
                    Intent prefrence = new Intent(getApplicationContext(), PrefrenceActivity.class);
                    startActivity(prefrence);
                    finish();
                } else {
                    finish();
                }*/
            } else {
                // Show_Dailog.Show_Alert_Login(Cable_services.this, getResources().getText(R.string.server_error).toString());
            }
        }

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
                mVendorList = Jsondata.getservices_provider(mSharedPreferences.getString(Constant.APP_USER_ID, ""), String.valueOf(getResources().getText(R.string.cable_id)), CableServiceActivity.this);
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
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(CableServiceActivity.this, android.R.layout.simple_spinner_dropdown_item, array); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerVendor.setAdapter(spinnerArrayAdapter);
            mSpinnerVendor.setOnItemSelectedListener(OnCatSpinnerCL);

        }

    }

    /*get service data in background*/
    class HttpGetAsyncTask_get extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CableServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                cable = Jsondata.getCableServices_Prefences(mSharedPreferences.getString(Constant.APP_USER_ID, ""), String.valueOf(getResources().getText(R.string.cable_id)), CableServiceActivity.this);
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
                if (cable.getSuccess()) {
//                    mButtonUnsubscribe.setVisibility(View.VISIBLE);
                    if (cable.getServicePreference().getIsContract()) {
                        mCheckBoxContract.setChecked(true);
                        mEditTextEndDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                        mEditTextStartDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                    } else {
                        mCheckBoxContract.setChecked(false);
                        String[] departure = cable.getServicePreference().getEndDate().split(" ");
                        String[] start_date = cable.getServicePreference().getStartDate().split(" ");
                        mEditTextEndDate.setText(departure[0]);
                        mEditTextStartDate.setText(start_date[0]);
                    }
//                    mEditTextPrice.setText(String.format("%.2f", cable.getServicePreference().getPrice()));
                    int price = cable.getServicePreference().getPrice();
                    mEditTextPrice.setText("" + price);
                    mEditTextFreeChannels.setText("" + cable.getServicePreference().getFreeChannels());
                    mEditTextPremiumChannels.setText("" + cable.getServicePreference().getPremiumChannels());
                    int index = MenuItemGlobal.getIndex("" + cable.getServicePreference().getServiceProviderId(), Jsondata.provider_id_list);
                    String provider_name = Jsondata.provider_id_list.get(index).get("provider_name").toString();
                    int value = mVendorList.indexOf(provider_name);
                    mSpinnerVendor.setSelection(value);

                }
            } catch (Exception e) {
            }
            SetTextRight();
        }

    }

    /*unsubscribe service to database */
    class HttpGetAsyncTask_unsubscribe extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CableServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.get_Unsuscribe_service(mSharedPreferences.getString(Constant.APP_USER_ID, ""), String.valueOf(getResources().getText(R.string.cable_id)), CableServiceActivity.this);
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
                ShowDailog.Show_Alert_Login(CableServiceActivity.this, getResources().getText(R.string.server_error).toString());
            }
        }

    }
}
