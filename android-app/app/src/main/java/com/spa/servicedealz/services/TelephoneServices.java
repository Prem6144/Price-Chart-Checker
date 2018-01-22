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
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.telephoneservice.Telephone;
import com.spa.servicedealz.R;
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
 * FileName : TelephoneServicesActivity
 * Description :show TelephoneServices
 * Dependencies : Internet
 */
public class TelephoneServices extends Activity implements View.OnClickListener, NetworkUtil.changeNetworkInterFace {
    private Spinner mSpinnerVender;
    private EditText mEditTextEndDate, mEditTextPrice, mEditTextStartDate, mEditTextDataPlan,
            mEditTextDataSpeed, mEditTextMinute, mEditTextSms;
    public static int Year, Month, Day, position, count;
    Button btn_update, btn_unsubscribe;
    static final int DATE_DIALOG_ID = 0, DATE_DIALOG_ID1 = 1;
    private TextView mTextViewTitle, mTextViewPrice, mTextViewEndDate, mTextViewTalk;
    private CheckBox mCheckBoxContract, mCheckBarText, mCheckBoxTalk;
    private Boolean mIsInternetPresent = false;
    private ArrayList<String> mVenderlist = new ArrayList<String>();
    private ImageView mImageViewClose;
    private SharedPreferences mSharedPreferences;
    private String end_date, price, start_date, dataplan, dataspeeds, is_contract = "false", provider_id, minute, voiceMail, is_text = "false", is_talk = "false";
    private Telephone telephone;
    private SharedPreferences.Editor mEditor;
    String EditSrvice, AddService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.telephone_services);
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

            btn_unsubscribe.setVisibility(View.VISIBLE);

        } else {
            btn_unsubscribe.setVisibility(View.GONE);
        }
        final Calendar c = Calendar.getInstance();
        Year = c.get(Calendar.YEAR);
        Month = c.get(Calendar.MONTH);
        Day = c.get(Calendar.DAY_OF_MONTH);
        c.before(Calendar.DATE);
        NetworkUtil.setChangeNetWorkListener(TelephoneServices.this);
        mIsInternetPresent = NetworkUtil.isConnectingToInternet(TelephoneServices.this);
        if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
            btn_unsubscribe.setText("Skip");
            btn_unsubscribe.setVisibility(View.VISIBLE);
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


    private void setCompulsoryfield() {
        mTextViewTitle.setText(getResources().getText(R.string.telephone_services));
        String text = "<font color=#4e4d4d>Vendor</font> <font color=#ff00000>*</font>";
        mTextViewTalk.setText(Html.fromHtml("<font color=#4e4d4d>Domestic</font> <font color=#ff00000>*</font>"));
        mTextViewEndDate.setText(Html.fromHtml("<font color=#4e4d4d>Contract Date</font> <font color=#ff00000>*</font>"));
        mTextViewPrice.setText(Html.fromHtml("<font color=#4e4d4d>Price($)</font> <font color=#ff00000>*</font>"));
    }

    private void setEditTextLocker() {
        EditTextLocker decimalEditTextLocker = new EditTextLocker(mEditTextPrice);
        decimalEditTextLocker.limitFractionDigitsinDecimal(2);
    }


    private void setClickListner() {
        btn_unsubscribe.setOnClickListener(this);
        mEditTextStartDate.setOnClickListener(this);
        mEditTextEndDate.setOnClickListener(this);
        btn_update.setOnClickListener(this);
        mCheckBoxContract.setOnClickListener(this);
        mImageViewClose.setOnClickListener(this);
        mCheckBarText.setOnClickListener(this);
        mCheckBoxTalk.setOnClickListener(this);
    }

    private void MappingdId() {
        mTextViewTitle = (TextView) findViewById(R.id.btn_title);
        mEditTextEndDate = (EditText) findViewById(R.id.etx_end_date);
        mSpinnerVender = (Spinner) findViewById(R.id.spn_vender);
        btn_unsubscribe = (Button) findViewById(R.id.btn_unsubscribe);
        mTextViewPrice = (TextView) findViewById(R.id.txt_price);
        mTextViewEndDate = (TextView) findViewById(R.id.txt_enddate);
        mTextViewTalk = (TextView) findViewById(R.id.text);
        mCheckBarText = (CheckBox) findViewById(R.id.chk_talk);
        mCheckBoxTalk = (CheckBox) findViewById(R.id.chk_text);
        mImageViewClose = (ImageView) findViewById(R.id.img_close);
        mCheckBoxContract = (CheckBox) findViewById(R.id.chk_contract);
        btn_update = (Button) findViewById(R.id.btn_update);
        mEditTextPrice = (EditText) findViewById(R.id.etx_price);
        mEditTextMinute = (EditText) findViewById(R.id.etx_minutes);
        mEditTextSms = (EditText) findViewById(R.id.etx_sms);
        mEditTextStartDate = (EditText) findViewById(R.id.etx_start_date);
        mEditTextDataPlan = (EditText) findViewById(R.id.etx_dataplan);
        mEditTextDataSpeed = (EditText) findViewById(R.id.etx_dataspeeds);
    }

    /*
           * Method to Set text in rightside in view
           * */
    private void SetTextRight() {
        mEditTextPrice.setSelection(mEditTextPrice.getText().toString().length());
        mEditTextPrice.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextMinute.setSelection(mEditTextMinute.getText().toString().length());
        mEditTextMinute.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextSms.setSelection(mEditTextSms.getText().toString().length());
        mEditTextSms.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextDataPlan.setSelection(mEditTextDataPlan.getText().toString().length());
        mEditTextDataPlan.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        mEditTextDataSpeed.setSelection(mEditTextDataSpeed.getText().toString().length());
        mEditTextDataSpeed.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
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

    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("TelephoneServiceActivity");
    }

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
                if (mCheckBarText.isChecked()) {
                    mEditTextMinute.setText("");
                    mEditTextMinute.setClickable(false);
                    mEditTextMinute.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                    mEditTextMinute.setEnabled(false);
                } else {
                    mEditTextMinute.setClickable(true);
                    mEditTextMinute.setEnabled(true);
                    mEditTextMinute.setBackgroundResource(R.drawable.edt_background);
                }
                break;
            case R.id.chk_text:
                if (mCheckBoxTalk.isChecked()) {
                    mEditTextSms.setClickable(false);
                    mEditTextSms.setText("");
                    mEditTextSms.setEnabled(false);
                    mEditTextSms.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                } else {
                    mEditTextSms.setClickable(true);
                    mEditTextSms.setEnabled(true);
                    mEditTextSms.setBackgroundResource(R.drawable.edt_background);
                }
                break;
            case R.id.chk_contract:
                //showDialog(DATE_DIALOG_ID);

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
            case R.id.etx_start_date:
                if (mCheckBoxContract.isChecked()) {

                } else {
                    showDialog(DATE_DIALOG_ID);
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
                if (!mIsInternetPresent) {
                    // Internet Connection is not present
                    ShowDailog.Show_Alert_Dailog(this);
                    // stop executing code by return

                } else {
                    if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
                        Intent welcome = new Intent(getApplicationContext(), CableServiceActivity.class);
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
            case R.id.btn_update:
                if (!mIsInternetPresent) {
                    // Internet Connection is not present
                    ShowDailog.Show_Alert_Dailog(this);
                    // stop executing code by return

                } else {
                    getTextData();
                   /* if (mSpinnerVender.getSelectedItem().toString().equalsIgnoreCase("Select Vendor")) {
                        Toast.makeText(TelephoneServices.this, "Please Select Vendor",
                                Toast.LENGTH_LONG).show();
                    } else*/ if (Validation.isFieldEmpty(mEditTextPrice)) {
                        Toast.makeText(this, "Please fill price field",
                                Toast.LENGTH_SHORT).show();
                    } else if (!mCheckBarText.isChecked() && Validation.isFieldEmpty(mEditTextMinute)) {
                        Toast.makeText(this, "Please fill minutes field",
                                Toast.LENGTH_SHORT).show();
                    } else if (!mCheckBarText.isChecked() && Integer.parseInt(mEditTextMinute.getText().toString()) <= 0) {
                        Toast.makeText(this, "Please fill atleast 1 as value",
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

    class HttpGetAsyncTask_unsubscribe extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(TelephoneServices.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.get_Unsuscribe_service(mSharedPreferences.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.telephone_id)), TelephoneServices.this);

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
                ShowDailog.Show_Alert_Login(TelephoneServices.this, getResources().getText(R.string.server_error).toString());
            }
        }

    }

    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TelephoneServices.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.getTelephoneservices(mSharedPreferences.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.telephone_id)), provider_id, end_date, is_contract, price, start_date, minute, voiceMail, is_talk, is_text
                        , TelephoneServices.this);

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
                    Intent browserIntent1 = new Intent(getApplicationContext(), CableServiceActivity.class);
                    startActivity(browserIntent1);
                }
                finish();
              /*  if (getIntent().getIntExtra("bestdealflag",0)==1){
                    Intent prefrence = new Intent(getApplicationContext(), PrefrenceActivity.class);
                    startActivity(prefrence);
                    finish();
                }else {
                    finish();
                }*/
            } else {
                ShowDailog.Show_Alert_Login(TelephoneServices.this, getResources().getText(R.string.server_error).toString());
            }
        }

    }

    class HttpGetAsyncTask_get extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(TelephoneServices.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                telephone = Jsondata.getTelephoneservices_prefences(mSharedPreferences.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.telephone_id)), TelephoneServices.this);
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


            if (telephone.getSuccess()) {
                setDataInViewComponent();
            } /*else {
                Show_Dailog.Show_Alert_Login(Cell_Phone_Services.this, getResources().getText(R.string.server_error).toString());
            }*/
            SetTextRight();
        }

    }

    private void setDataInViewComponent() {
//        btn_unsubscribe.setVisibility(View.VISIBLE);
        if (telephone.getServicePreference().getIsContract()) {
            mCheckBoxContract.setChecked(true);
            mEditTextEndDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
            mEditTextStartDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
        } else {
            mCheckBoxContract.setChecked(false);
            String[] departure = telephone.getServicePreference().getEndDate().split(" ");
            String[] start_date = telephone.getServicePreference().getStartDate().split(" ");
            mEditTextEndDate.setText(departure[0]);
            mEditTextStartDate.setText(start_date[0]);
        }
        double price =telephone.getServicePreference().getPrice();
//        mEditTextPrice.setText(String.format("%.2f", ));
        mEditTextPrice.setText(""+price);
        mEditTextSms.setText(telephone.getServicePreference().getInternationalCallMinutes());
        mEditTextMinute.setText(telephone.getServicePreference().getDomesticCallMinutes());
        mCheckBarText.setChecked(telephone.getServicePreference().getDomesticCallUnlimited());
        mCheckBoxTalk.setChecked(Boolean.parseBoolean(telephone.getServicePreference().getInternationalCallUnlimited()));
//        int index = MenuItemGlobal.getIndex("" + telephone.getServicePreference().getServiceProviderId(), Jsondata.provider_id_list);
//        String provider_name = Jsondata.provider_id_list.get(index).get("provider_name").toString();
//        int value = mVenderlist.indexOf(provider_name);
//        mSpinnerVender.setSelection(value);


        if (mCheckBarText.isChecked()) {
            mEditTextMinute.setText("");
            mEditTextMinute.setClickable(false);
            mEditTextMinute.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
            mEditTextMinute.setEnabled(false);
        } else {
            mEditTextMinute.setClickable(true);
            mEditTextMinute.setEnabled(true);
            mEditTextMinute.setBackgroundResource(R.drawable.edt_background);
        }
        if (mCheckBoxTalk.isChecked()) {
            mEditTextSms.setClickable(false);
            mEditTextSms.setText("");
            mEditTextSms.setEnabled(false);
            mEditTextSms.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
        } else {
            mEditTextSms.setClickable(true);
            mEditTextSms.setEnabled(true);
            mEditTextSms.setBackgroundResource(R.drawable.edt_background);
        }

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
                mVenderlist = Jsondata.getservices_provider(mSharedPreferences.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.telephone_id)), TelephoneServices.this);

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
            String[] array = mVenderlist.toArray(new String[mVenderlist.size()]);
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(TelephoneServices.this, android.R.layout.simple_spinner_dropdown_item, array); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mSpinnerVender.setAdapter(spinnerArrayAdapter);
            mSpinnerVender.setOnItemSelectedListener(OnCatSpinnerCL);

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

    private void getTextData() {

        if (mCheckBoxContract.isChecked()) {
            is_contract = "true";
        }
        if (mCheckBarText.isChecked()) {
            is_talk = "true";
        }

        if (mCheckBoxTalk.isChecked()) {
            is_text = "true";
        }
        String provider = mSpinnerVender.getSelectedItem().toString();
        int index = MenuItemGlobal.getIndex(provider, Jsondata.provider_id_list);
        provider_id = Jsondata.provider_id_list.get(index).get("id").toString();
        end_date = mEditTextEndDate.getText().toString();
        price = mEditTextPrice.getText().toString();
        start_date = mEditTextStartDate.getText().toString();
        dataplan = mEditTextDataPlan.getText().toString();
        dataspeeds = mEditTextDataSpeed.getText().toString();
        minute = mEditTextMinute.getText().toString();
        voiceMail = mEditTextSms.getText().toString();
    }
}
