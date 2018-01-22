package com.spa.servicedealz.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
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
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.internetservice.Internet;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.WelcomeScreenActivity;
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
 * FileName : InternetServiceActivity
 * Description :show InternetService
 * Dependencies : Internet
 */
public class InternetServiceActivity extends Activity implements View.OnClickListener, NetworkUtil.changeNetworkInterFace {
    Spinner mSpinnerVendor;
    private Boolean mIsInternetPresent = false;
    private EditText mEditTextEndDate, mEditTextPrice, mEditTextStartDate, mEditTextUpload, mEditTextDownload, mEditTextData;
    public static int Year, Month, Day, position, count;
    private Button mButtonUpdate, mButttonUnsubscribe;
    static final int DATE_DIALOG_ID = 0, DATE_DIALOG_ID1 = 1;
    private TextView mTextViewTitle, mTextViewPrice, mTextViewEndDate, mTextViewdownload;
    private CheckBox mCheckBoxContract;
    private ArrayList<String> mVenderlist = new ArrayList<String>();
    private ImageView mImageViewClose;
    String is_contract, price, provider_id, end_date, start_date, download, upload, data;
    private SharedPreferences mSharedPreferences;
    private Internet internet = null;
    private SharedPreferences.Editor mEditor;
    String EditSrvice, AddService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.internet_services_activity);
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

            mButttonUnsubscribe.setVisibility(View.VISIBLE);

        } else {
            mButttonUnsubscribe.setVisibility(View.GONE);
        }
        final Calendar calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.after(Calendar.DATE);
        NetworkUtil.setChangeNetWorkListener(InternetServiceActivity.this);
        mIsInternetPresent = NetworkUtil.isConnectingToInternet(InternetServiceActivity.this);
        if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
            mButttonUnsubscribe.setText("Skip");
            mButttonUnsubscribe.setVisibility(View.VISIBLE);
        }
        if (!mIsInternetPresent) {
            // Internet Connection is not present
            ShowDailog.Show_Alert_Dailog(this);
            // stop executing code by return
            return;
        } else {
            new HttpGetAsyncTask_vender().execute();
            new HttpGetAsyncTask_get().execute();


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

    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("InternetServiceActivity");
    }

    private void setCompulsoryfield() {
        mTextViewdownload.setText(Html.fromHtml("<font color=#4e4d4d>Download (Mbps)</font> <font color=#ff00000>*</font>"));
        mTextViewEndDate.setText(Html.fromHtml("<font color=#4e4d4d>Contract Date</font> <font color=#ff00000>*</font>"));
        mTextViewPrice.setText(Html.fromHtml("<font color=#4e4d4d>Price($)</font> <font color=#ff00000>*</font>"));

    }

    private void setEditTextLocker() {
        EditTextLocker decimalEditTextLocker = new EditTextLocker(mEditTextPrice);
        decimalEditTextLocker.limitFractionDigitsinDecimal(2);
        EditTextLocker decimalEditTextLockerDownload = new EditTextLocker(mEditTextDownload);
        decimalEditTextLockerDownload.limitFractionDigitsinDecimal(1);
        EditTextLocker decimalEditTextLockerUpload = new EditTextLocker(mEditTextUpload);
        decimalEditTextLockerUpload.limitFractionDigitsinDecimal(1);
    }

    private void setClickListner() {
        mButttonUnsubscribe.setOnClickListener(this);
        mEditTextStartDate.setOnClickListener(this);
        mEditTextEndDate.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
        mCheckBoxContract.setOnClickListener(this);
        mImageViewClose.setOnClickListener(this);
    }

    private void MappingdId() {
        mCheckBoxContract = (CheckBox) findViewById(R.id.chk_contract);
        mTextViewdownload = (TextView) findViewById(R.id.txt_download);
        mTextViewTitle = (TextView) findViewById(R.id.btn_title);
        mTextViewTitle.setText(getResources().getText(R.string.internet_services));
        mTextViewPrice = (TextView) findViewById(R.id.txt_price);
        mTextViewEndDate = (TextView) findViewById(R.id.txt_enddate);
        mEditTextData = (EditText) findViewById(R.id.etx_data);
        mImageViewClose = (ImageView) findViewById(R.id.img_close);
        mButtonUpdate = (Button) findViewById(R.id.btn_update);
        mEditTextPrice = (EditText) findViewById(R.id.etx_price);
        mEditTextStartDate = (EditText) findViewById(R.id.etx_start_date);
        mButttonUnsubscribe = (Button) findViewById(R.id.btn_unsubscribe);
        mEditTextEndDate = (EditText) findViewById(R.id.etx_end_date);
        mEditTextStartDate = (EditText) findViewById(R.id.etx_start_date);
        mSpinnerVendor = (Spinner) findViewById(R.id.spn_vender);
        mEditTextUpload = (EditText) findViewById(R.id.spn_upload);
        mEditTextDownload = (EditText) findViewById(R.id.spn_download);
    }

    /*
           * Method to Set text in rightside in view
           * */
    private void SetTextRight() {
        mEditTextPrice.setSelection(mEditTextPrice.getText().toString().length());
        mEditTextPrice.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

        mEditTextData.setSelection(mEditTextData.getText().toString().length());
        mEditTextData.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextUpload.setSelection(mEditTextUpload.getText().toString().length());
        mEditTextUpload.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextDownload.setSelection(mEditTextDownload.getText().toString().length());
        mEditTextDownload.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
    }

    @Override
    public void updateNetwork(String s) {

    }


    class HttpGetAsyncTask_vender extends AsyncTask<String, Void, String> {
        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                mVenderlist = Jsondata.getservices_provider(mSharedPreferences.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.internet_id)), InternetServiceActivity.this);

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
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(InternetServiceActivity.this, android.R.layout.simple_spinner_dropdown_item, mVenderlist); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerVendor.setAdapter(spinnerArrayAdapter);
            mSpinnerVendor.setOnItemSelectedListener(OnCatSpinnerCL);

        }

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
//                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                return da;
            case DATE_DIALOG_ID1:
                DatePickerDialog da1;

                da1 = new DatePickerDialog(this, DateSetListener1, Year, Month, Day);

                da1.setCanceledOnTouchOutside(false);
                da1.setCancelable(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                    da1.getDatePicker().setCalendarViewShown(false);
                    da1 = new DatePickerDialog(this, R.style.Base_Theme_AppCompat_Light_Dialog, DateSetListener1, Year, Month, Day);
                } else {
                    da1 = new DatePickerDialog(this, DateSetListener1, Year, Month, Day);

                }
                da1.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                return da1;

        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mEditor.putString("signupwithfirsttime", "");
            mEditor.commit();

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.chk_contract:
                //showDialog(DATE_DIALOG_ID);

                if (mCheckBoxContract.isChecked()) {
                    mEditTextEndDate.setText("");
                    mEditTextEndDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                    mEditTextStartDate.setText("");
                    mEditTextStartDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
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
            case R.id.btn_unsubscribe:
                if (!mIsInternetPresent) {
                    // Internet Connection is not present
                    ShowDailog.Show_Alert_Dailog(this);
                    // stop executing code by return

                } else {
                    if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
                        Intent welcome = new Intent(getApplicationContext(), TelephoneServices.class);
                        startActivity(welcome);
                        finish();
                    } else {
                        new HttpGetAsyncTask_unsubscribe().execute();
                    }

                }


                break;
            case R.id.etx_end_date:
                if (mCheckBoxContract.isChecked()) {

                } else {
                    showDialog(DATE_DIALOG_ID1);
                }

                break;
            case R.id.img_close:

                mEditor.putString("signupwithfirsttime", "");
                mEditor.commit();
                finish();
                break;
            case R.id.btn_update:
//                if (mSpinnerVendor.getSelectedItem().toString().equalsIgnoreCase("Select Vendor")) {
//                    Toast.makeText(InternetServiceActivity.this, "Please Select Vendor",
//                            Toast.LENGTH_LONG).show();
//                } else {
                if (!mIsInternetPresent) {
                    // Internet Connection is not present
                    ShowDailog.Show_Alert_Dailog(this);
                    // stop executing code by return
                    return;
                } else if (Validation.isFieldEmpty(mEditTextPrice)) {
                    Toast.makeText(this, "Please fill price field",
                            Toast.LENGTH_SHORT).show();
                } else if (Validation.isFieldEmpty(mEditTextDownload) || Float.parseFloat(mEditTextDownload.getText().toString()) <= 0) {
                    Toast.makeText(this, "Please fill atleast 1 as value in download",
                            Toast.LENGTH_SHORT).show();
                } else if (mCheckBoxContract.isChecked()) {
                    GetTextData();
                    new HttpGetAsyncTask().execute();

                } else {
                    if (Validation.isFieldEmpty(mEditTextStartDate)) {
                        Toast.makeText(this, "Please Select Start Date",
                                Toast.LENGTH_SHORT).show();

                    } else if (Validation.isFieldEmpty(mEditTextEndDate)) {
                        Toast.makeText(this, "Please Select End Date",
                                Toast.LENGTH_SHORT).show();
                    } else {
                        GetTextData();
                        new HttpGetAsyncTask().execute();
                    }
                }


//                }

                break;
        }
    }

    private void GetTextData() {

        is_contract = "false";
        if (mCheckBoxContract.isChecked()) {
            is_contract = "true";
        } else {

        }
        price = "0";
        if (mEditTextPrice.getText().toString().length() <= 0) {

        } else {
            price = mEditTextPrice.getText().toString();
        }
        end_date = mEditTextEndDate.getText().toString();
        start_date = mEditTextStartDate.getText().toString();
        String provider = mSpinnerVendor.getSelectedItem().toString();
        download = mEditTextDownload.getText().toString();
        upload = mEditTextUpload.getText().toString();
        int index = MenuItemGlobal.getIndex(provider, Jsondata.provider_id_list);
        provider_id = Jsondata.provider_id_list.get(index).get("id").toString();
        data = mEditTextData.getText().toString();
    }

    class HttpGetAsyncTask_unsubscribe extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(InternetServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.get_Unsuscribe_service(mSharedPreferences.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.internet_id)), InternetServiceActivity.this);

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
                    Intent welcome = new Intent(getApplicationContext(), WelcomeScreenActivity.class);
                    startActivity(welcome);
                }
                finish();
            } else {
                ShowDailog.Show_Alert_Login(InternetServiceActivity.this, getResources().getText(R.string.server_error).toString());
            }
        }

    }

    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InternetServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                response = Jsondata.getinternetservices(mSharedPreferences.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.internet_id)), provider_id, end_date, is_contract, price, start_date, upload, download, InternetServiceActivity.this);

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
                if (Jsondata.Success.equalsIgnoreCase("true")) {
                    DashboardFragment.ServiceSave = true;
                    if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
                        Intent browserIntent1 = new Intent(getApplicationContext(), TelephoneServices.class);
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
                    ShowDailog.Show_Alert_Login(InternetServiceActivity.this, getResources().getText(R.string.server_error).toString());
                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }

    }

    class HttpGetAsyncTask_get extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(InternetServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                internet = Jsondata.getInternetservices_prefences(mSharedPreferences.getString("app_user_id", ""), "1", InternetServiceActivity.this);

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
                if (internet.getSuccess()) {
//                    mButttonUnsubscribe.setVisibility(View.VISIBLE);
                    String spin_string = internet.getServicePreference().getServiceProviderId();
                    if (internet.getServicePreference().getIsContract()) {
                        mCheckBoxContract.setChecked(true);
                        mEditTextEndDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                        mEditTextStartDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                    } else {
                        mCheckBoxContract.setChecked(false);
                        String[] departure = internet.getServicePreference().getEndDate().split(" ");
                        String[] start_date = internet.getServicePreference().getStartDate().split(" ");
                        mEditTextEndDate.setText(departure[0]);
                        mEditTextStartDate.setText(start_date[0]);
                    }

                    mEditTextPrice.setText("" + internet.getServicePreference().getPrice());
                    double downloadspeed = internet.getServicePreference().getDownloadSpeed();
                    mEditTextDownload.setText("" + downloadspeed);
                    mEditTextUpload.setText("" + internet.getServicePreference().getUploadSpeed());
//                    mEditTextPrice.setText(String.format("%.2f", internet.getServicePreference().getPrice()));
                    int index = MenuItemGlobal.getIndex("" + spin_string, Jsondata.provider_id_list);
                    String provider_name = Jsondata.provider_id_list.get(index).get("provider_name").toString();
                    int value = mVenderlist.indexOf(provider_name);
                    mSpinnerVendor.setSelection(value);

                } else {
                    //  Show_Dailog.Show_Alert_Login(Internet_Services.this, getResources().getText(R.string.server_error).toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            SetTextRight();
        }

    }
}
