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
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.cellphoneservice.Cellphone;
import com.spa.model.cellphoneservice.CellphoneDetail;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.WowScreenActivity;
import com.spa.utils.Constant;
import com.spa.utils.EditTextLocker;
import com.spa.utils.Jsondata;
import com.spa.utils.MenuItemGlobal;
import com.spa.utils.Validation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by E0115Diwakar on 2/16/2015.
 */

/**
 * FileName : CellPhoneServiceActivity
 * Description : Show CellPhone service
 * Dependencies : Internet
 */
public class CellPhoneServiceActivity extends Activity implements View.OnClickListener, NetworkUtil.changeNetworkInterFace {
    private Spinner mSpinnerVendor, mSpinnerNumberofLine, mSpinnerDevices;
    private ProgressDialog pDialog;
    private SharedPreferences mSharedPreferences;
    private EditText mEditTextEndDate, mEditTextPrice, mEditTextStartDate, mEditTextDataPlan, mEditTextDataSpeeds, mEditTextMinutes, mEditTextText;
    public static int Year, Month, Day;
    private Button mButtonUpdate, mButtonUnsubscribe;
    static final int DATE_DIALOG_ID = 0, DATE_DIALOG_ID1 = 1;
    private TextView mTextViewTitle, mTextViewVendor, mTextViewPrice, mTextViewEndDate, mTextViewTalk;
    private CheckBox mCheckBoxContract, mCheckBoxTalk, mCheckBoxText;
    private Boolean isInternetPresent = false;
    private ArrayList<String> mVendorList, mCellphoneList = new ArrayList<String>();
    private ImageView mImageViewClose;
    private String end_date, price, cellphonedeviceid, numberofline, start_date, dataplan, dataspeeds, is_contract = "false", provider_id, minute, voiceMail, is_talk = "false", is_text = "false";
    Cellphone cellphone = null;
    private SharedPreferences.Editor mEditor;
    CellphoneDetail cellphoneDetail;
    String EditSrvice, AddService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cell_phone_services);
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
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(CellPhoneServiceActivity.this, R.array.cellphone_numberofline_array, android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerNumberofLine.setAdapter(adapter1);
        mSpinnerNumberofLine.setOnItemSelectedListener(OnCatSpinnerCL1);
        NetworkUtil.setChangeNetWorkListener(CellPhoneServiceActivity.this);
        isInternetPresent = NetworkUtil.isConnectingToInternet(CellPhoneServiceActivity.this);
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
        Localytics.tagScreen("CellphoneServiceActivity");
    }

    /*set click listner*/
    private void setClickListner() {
        mButtonUnsubscribe.setOnClickListener(this);
        mEditTextStartDate.setOnClickListener(this);
        mEditTextEndDate.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
        mCheckBoxContract.setOnClickListener(this);
        mCheckBoxTalk.setOnClickListener(this);
        mCheckBoxText.setOnClickListener(this);
        mImageViewClose.setOnClickListener(this);
    }

    /*set edittext limitation in decimal point*/
    private void setEditTextLocker() {
        EditTextLocker decimalEditTextLocker = new EditTextLocker(mEditTextPrice);
        decimalEditTextLocker.limitFractionDigitsinDecimal(2);
        EditTextLocker decimalEditTextLockerDownload = new EditTextLocker(mEditTextDataPlan);
        decimalEditTextLockerDownload.limitFractionDigitsinDecimal(1);
        EditTextLocker decimalEditTextLockerUpload = new EditTextLocker(mEditTextDataSpeeds);
        decimalEditTextLockerUpload.limitFractionDigitsinDecimal(1);
    }

    /*set compulsory field*/
    private void setCompulsoryfield() {
        String text = "<font color=#4e4d4d>Vendor</font> <font color=#ff00000>*</font>";
        mTextViewTitle.setText(getResources().getText(R.string.cell_phone_services));
        mTextViewTalk.setText(Html.fromHtml("<font color=#4e4d4d>Domestic</font> <font color=#ff00000>*</font>"));
        mTextViewEndDate.setText(Html.fromHtml("<font color=#4e4d4d>Contract Date</font> <font color=#ff00000>*</font>"));
        mTextViewPrice.setText(Html.fromHtml("<font color=#4e4d4d>Price($)</font> <font color=#ff00000>*</font>"));

    }

    /*
       * Method to Set text in rightside in view
       * */
    private void SetTextRight() {
        mEditTextPrice.setSelection(mEditTextPrice.getText().toString().length());
        mEditTextMinutes.setSelection(mEditTextMinutes.getText().toString().length());
        mEditTextText.setSelection(mEditTextText.getText().toString().length());
        mEditTextDataPlan.setSelection(mEditTextDataPlan.getText().toString().length());
        mEditTextDataSpeeds.setSelection(mEditTextDataSpeeds.getText().toString().length());
        mEditTextDataSpeeds.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextPrice.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextMinutes.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextText.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextDataPlan.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
    }

    /*
       * Method to find Ids of views
       * */
    private void MappingdId() {
        mTextViewTalk = (TextView) findViewById(R.id.text);
        mCheckBoxTalk = (CheckBox) findViewById(R.id.chk_talk);
        mCheckBoxText = (CheckBox) findViewById(R.id.chk_text);
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
        mTextViewEndDate = (TextView) findViewById(R.id.txt_enddate);
        mEditTextEndDate = (EditText) findViewById(R.id.etx_end_date);
        mEditTextStartDate = (EditText) findViewById(R.id.etx_start_date);
        mSpinnerNumberofLine = (Spinner) findViewById(R.id.etx_no_line);
        mSpinnerVendor = (Spinner) findViewById(R.id.spn_vender);
        mSpinnerDevices = (Spinner) findViewById(R.id.spn_device_no_line);
        mButtonUnsubscribe = (Button) findViewById(R.id.btn_unsubscribe);
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
                    mEditTextEndDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                    mEditTextStartDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                } else {
                    mEditTextEndDate.setBackgroundResource(R.drawable.edt_background);
                    mEditTextStartDate.setBackgroundResource(R.drawable.edt_background);
                }
                break;
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
            case R.id.btn_unsubscribe:
                if (!isInternetPresent) {
                    ShowDailog.Show_Alert_Dailog(this);
                } else {
                    if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
                        Intent welcome = new Intent(getApplicationContext(), BundleServiceActivity.class);
                        startActivity(welcome);
                        finish();
                    } else {
                        new HttpGetAsyncTask_unsubscribe().execute();
                    }
                }


                break;
            case R.id.btn_update:
                if (!isInternetPresent) {
                    ShowDailog.Show_Alert_Dailog(this);
                } else {
                    getTextData();
                   /* if (mSpinnerVendor.getSelectedItem().toString().equalsIgnoreCase("Select Vendor")) {
                        Toast.makeText(this, getResources().getString(R.string.select_vendor_error),
                                Toast.LENGTH_LONG).show();
                    } else*/ if (Validation.isFieldEmpty(mEditTextPrice)) {
                        Toast.makeText(this, getResources().getString(R.string.price_error),
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
                break;
        }
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
            pDialog = new ProgressDialog(CellPhoneServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.getCellphoneservices(mSharedPreferences.getString(Constant.APP_USER_ID, ""), String.valueOf(getResources().getText(R.string.cellphone_id)), provider_id, end_date, is_contract, price, start_date, minute, voiceMail, is_talk, is_text
                        , dataplan, dataspeeds, numberofline, cellphonedeviceid, CellPhoneServiceActivity.this);
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
                    Intent browserIntent1 = new Intent(getApplicationContext(), BundleServiceActivity.class);
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
            }
        }

    }

    /*get service data in background*/
    class HttpGetAsyncTask_get extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CellPhoneServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage(Constant.WAIT);

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                cellphone = Jsondata.getCellphoneservices_prefences(mSharedPreferences.getString(Constant.APP_USER_ID, ""), String.valueOf(getResources().getText(R.string.cellphone_id)), CellPhoneServiceActivity.this);
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
            if (cellphone != null) {
                if (cellphone.getSuccess()) {
                    setDataInViewComponent();
                }
            }
            SetTextRight();
        }

    }

    /*set data in view component*/
    private void setDataInViewComponent() {
//        mButtonUnsubscribe.setVisibility(View.VISIBLE);
        if (cellphone.getServicePreference().getIsContract()) {
            mCheckBoxContract.setChecked(true);
            mEditTextEndDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
            mEditTextStartDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
        } else {
            mCheckBoxContract.setChecked(false);
            String[] departure = cellphone.getServicePreference().getEndDate().split(" ");
            String[] start_date = cellphone.getServicePreference().getStartDate().split(" ");
            mEditTextEndDate.setText(departure[0]);
            mEditTextStartDate.setText(start_date[0]);
        }
        int index1 = Arrays.asList(getResources().getStringArray(R.array.cellphone_numberofline_array)).indexOf("" + cellphone.getServicePreference().getNoOfLines());
        mSpinnerNumberofLine.setSelection(index1);
//        mEditTextPrice.setText(String.format("%.2f", cellphone.getServicePreference().getPrice()));
        String price =cellphone.getServicePreference().getPrice();
        mEditTextPrice.setText(""+price);
//        int index = MenuItemGlobal.getIndex("" + cellphone.getServicePreference().getServiceProviderId(), Jsondata.provider_id_list);
//        String provider_name = Jsondata.provider_id_list.get(index).get("provider_name").toString();
//        int value = mVendorList.indexOf(provider_name);

//        mSpinnerVendor.setSelection(value);

        try {
            int cellphoneid = cellphone.getServicePreference().getCellphoneDetailId();

            for (int i = 0; cellphoneDetail.getCellphoneDetails().size() > i; i++) {
                if (cellphoneDetail.getCellphoneDetails().get(i).getId() == cellphoneid) {
                    mSpinnerDevices.setSelection(i);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        mEditTextText.setText(cellphone.getServicePreference().getInternationalCallUnlimited());
        mEditTextMinutes.setText(String.valueOf(cellphone.getServicePreference().getDomesticCallUnlimited()));
        mCheckBoxTalk.setChecked((cellphone.getServicePreference().getDomesticCallUnlimited()));
        mCheckBoxText.setChecked(Boolean.parseBoolean((cellphone.getServicePreference().getInternationalCallUnlimited())));
        mEditTextDataPlan.setText(cellphone.getServicePreference().getPlanName());
//        mEditTextDataSpeeds.setText(cellphone.getServicePreference().getDataSpeed());

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
                mVendorList = Jsondata.getservices_provider(mSharedPreferences.getString(Constant.APP_USER_ID, ""), String.valueOf(getResources().getText(R.string.cellphone_id)), CellPhoneServiceActivity.this);
                cellphoneDetail = Jsondata.get_cellphonedetail("", CellPhoneServiceActivity.this);
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
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(CellPhoneServiceActivity.this, android.R.layout.simple_spinner_dropdown_item, array); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerVendor.setAdapter(spinnerArrayAdapter);
            mSpinnerVendor.setOnItemSelectedListener(OnCatSpinnerCL);
            mCellphoneList.clear();
            for (int i = 0; cellphoneDetail.getCellphoneDetails().size() > i; i++) {
                mCellphoneList.add(cellphoneDetail.getCellphoneDetails().get(i).getCellphoneName());
            }
            ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(CellPhoneServiceActivity.this, android.R.layout.simple_spinner_dropdown_item, mCellphoneList); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerDevices.setAdapter(spinnerArrayAdapter1);
            mSpinnerDevices.setOnItemSelectedListener(OnCatSpinnerCL);

        }

    }

    /*unsubscribe service to database */
    class HttpGetAsyncTask_unsubscribe extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(CellPhoneServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.get_Unsuscribe_service(mSharedPreferences.getString(Constant.APP_USER_ID, ""), String.valueOf(getResources().getText(R.string.cellphone_id)), CellPhoneServiceActivity.this);

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
                ShowDailog.Show_Alert_Login(CellPhoneServiceActivity.this, getResources().getText(R.string.server_error).toString());
            }
        }

    }


    private void getTextData() {

        if (mCheckBoxContract.isChecked()) {
            is_contract = "true";
        }

        if (mCheckBoxTalk.isChecked()) {
            is_talk = "true";
        }

        if (mCheckBoxText.isChecked()) {
            is_text = "true";
        }
        try {
            String devicename = mSpinnerDevices.getSelectedItem().toString();
            int index1 = mCellphoneList.indexOf(devicename);
            cellphonedeviceid = "" + cellphoneDetail.getCellphoneDetails().get(index1).getId();

        } catch (Exception e) {
            cellphonedeviceid = "1";
        }
        numberofline = mSpinnerNumberofLine.getSelectedItem().toString();
        String provider = mSpinnerVendor.getSelectedItem().toString();
        int index = MenuItemGlobal.getIndex(provider, Jsondata.provider_id_list);
        provider_id = Jsondata.provider_id_list.get(index).get("id").toString();
        end_date = mEditTextEndDate.getText().toString();
        price = mEditTextPrice.getText().toString();
        start_date = mEditTextStartDate.getText().toString();
        dataplan = mEditTextDataPlan.getText().toString();
        dataspeeds = mEditTextDataSpeeds.getText().toString();
        minute = mEditTextMinutes.getText().toString();
        voiceMail = mEditTextText.getText().toString();
    }
}
