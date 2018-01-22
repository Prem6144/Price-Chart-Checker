package com.spa.servicedealz.services;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import com.spa.servicedealz.R;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.fragment.ShowDailog;
import com.spa.utils.EditTextLocker;
import com.spa.utils.Jsondata;
import com.spa.utils.MenuItemGlobal;
import com.spa.utils.Validation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

/**
 * Created by E0115Diwakar on 2/16/2015.
 */

/**
 * FileName : HomeSecurityServiceActivity
 * Description : Show HomeSecurityService
 * Dependencies : Internet
 */
public class HomeSecurityServiceActivity extends Activity implements View.OnClickListener, NetworkUtil.changeNetworkInterFace {
    // ActionBar actionBar;
    private Spinner mSpinnerVender;
    public static ArrayList<HashMap<String, String>> service_prefrences = new ArrayList<HashMap<String, String>>();
    private Boolean mIsInternetPresent = false;
    private EditText mEditTextEndDate, mEditTextPrice, mEditTextStartDate;
    public static int Year, Month, Day, position, count;
    private Button mButtonUpdate, mButtonUnsubscribe;
    public static final int DATE_DIALOG_ID = 0, DATE_DIALOG_ID1 = 1;
    private TextView mTextViewTitle, mTextViewVendor, mTextViewPrice, mTextViewEndDate;
    private CheckBox mCheckBoxContract;
    private ArrayList<String> mVenderlist = new ArrayList<String>();
    private ImageView mImageViewClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.services_form);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        //==========================================================================================
        mTextViewTitle = (TextView) findViewById(R.id.btn_title);
        mEditTextStartDate = (EditText) findViewById(R.id.etx_start_date);
        mTextViewTitle.setText(getResources().getText(R.string.home_security));
        mTextViewVendor = (TextView) findViewById(R.id.txt_vendor);
        String text = "<font color=#000000>Vendor</font> <font color=#ff00000>*</font>";
        mTextViewVendor.setText(Html.fromHtml(text));
        //==========================================================================================
        mImageViewClose = (ImageView) findViewById(R.id.img_close);
        mCheckBoxContract = (CheckBox) findViewById(R.id.chk_contract);
        mButtonUpdate = (Button) findViewById(R.id.btn_update);
        mEditTextPrice = (EditText) findViewById(R.id.etx_price);
        mButtonUnsubscribe = (Button) findViewById(R.id.btn_unsubscribe);
        mButtonUnsubscribe.setOnClickListener(this);
        EditTextLocker decimalEditTextLocker = new EditTextLocker(mEditTextPrice);
        decimalEditTextLocker.limitFractionDigitsinDecimal(2);
        mEditTextEndDate = (EditText) findViewById(R.id.etx_end_date);
        mSpinnerVender = (Spinner) findViewById(R.id.spn_vender);
        mEditTextEndDate.setOnClickListener(this);
        mEditTextStartDate.setOnClickListener(this);
        mButtonUpdate.setOnClickListener(this);
        mCheckBoxContract.setOnClickListener(this);
        mImageViewClose.setOnClickListener(this);
        mTextViewPrice = (TextView) findViewById(R.id.txt_price);
        mTextViewEndDate = (TextView) findViewById(R.id.txt_enddate);
        mTextViewEndDate.setText(Html.fromHtml("<font color=#000000>Contract Date</font> <font color=#ff00000>*</font>"));
        mTextViewPrice.setText(Html.fromHtml("<font color=#000000>Price($)</font> <font color=#ff00000>*</font>"));

        //==========================================================================================

        final Calendar c = Calendar.getInstance();

        Year = c.get(Calendar.YEAR);

        Month = c.get(Calendar.MONTH);

        Day = c.get(Calendar.DAY_OF_MONTH);
        c.before(Calendar.DATE);
        //==========================================================================================
        NetworkUtil.setChangeNetWorkListener(HomeSecurityServiceActivity.this);
        mIsInternetPresent = NetworkUtil.isConnectingToInternet(HomeSecurityServiceActivity.this);
        if (!mIsInternetPresent) {
            // Internet Connection is not present
            Toast.makeText(HomeSecurityServiceActivity.this, "No Internet Connection!",
                    Toast.LENGTH_SHORT).show();
            // stop executing code by return
            return;
        } else {
            new HttpGetAsyncTask_vender().execute();
            new HttpGetAsyncTask_get().execute();

        }
        //==============================================================================================
    }

    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("HomeSecurityServiceActivity");
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

    /*
           * Method to Set text in rightside in view
           * */
    private void SetTextRight() {
        mEditTextPrice.setSelection(mEditTextPrice.getText().toString().length());
        mEditTextPrice.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);

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
                finish();

                break;
            case R.id.btn_unsubscribe:
                if (!mIsInternetPresent) {
                    // Internet Connection is not present
                    Toast.makeText(HomeSecurityServiceActivity.this, "No Internet Connection!",
                            Toast.LENGTH_SHORT).show();
                    // stop executing code by return

                } else {

                    new HttpGetAsyncTask_unsubscribe().execute();


                }


                break;
            case R.id.btn_update:
                if (!mIsInternetPresent) {
                    // Internet Connection is not present
                    Toast.makeText(HomeSecurityServiceActivity.this, "No Internet Connection!",
                            Toast.LENGTH_SHORT).show();
                    // stop executing code by return
                    return;
                } else {
                    try {
                       /* String CurrDate = etx_start_date.getText().toString();
                        String PrvvDate = etx_end_date.getText().toString();
                        Date date1 = null;
                        Date date2 = null;
                        SimpleDateFormat df = new SimpleDateFormat("mm/dd/yyyy");
                        SimpleDateFormat df1 = new SimpleDateFormat("mm/dd/yyyy");
                        date1 = df.parse(CurrDate);
                        date2 = df1.parse(PrvvDate);
                        long diff = Math.abs(date2.getTime() - date1.getTime());
                        long diffDays = diff / (24 * 60 * 60 * 1000);
                        System.out.println(diffDays);*/

                    } catch (Exception e1) {
                        System.out.println("exception " + e1);
                    }
                    if (mSpinnerVender.getSelectedItem().toString().equalsIgnoreCase("Select Vendor")) {
                        Toast.makeText(HomeSecurityServiceActivity.this, "Please Select Vendor",
                                Toast.LENGTH_LONG).show();
                    } else if (Validation.isFieldEmpty(mEditTextPrice)) {
                        Toast.makeText(this, "Please fill price field",
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

            pDialog = new ProgressDialog(HomeSecurityServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                response = Jsondata.get_Unsuscribe_service(sp.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.homesecurity_id)), HomeSecurityServiceActivity.this);

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
                finish();
            } else {
                ShowDailog.Show_Alert_Login(HomeSecurityServiceActivity.this, getResources().getText(R.string.server_error).toString());
            }
        }

    }

    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(HomeSecurityServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String is_contract = "false";
                if (mCheckBoxContract.isChecked()) {
                    is_contract = "true";
                } else {

                }
                String price = "0";
                if (mEditTextPrice.getText().toString().length() <= 0) {

                } else {
                    price = mEditTextPrice.getText().toString();
                }

                String provider = mSpinnerVender.getSelectedItem().toString();
                int index = MenuItemGlobal.getIndex(provider, Jsondata.provider_id_list);
                String provider_id = Jsondata.provider_id_list.get(index).get("id").toString();
                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                response = Jsondata.getservices(sp.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.homesecurity_id)), provider_id, mEditTextEndDate.getText().toString(), is_contract, price, mEditTextStartDate.getText().toString(), "", "", HomeSecurityServiceActivity.this);

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

                finish();

            } else {
                ShowDailog.Show_Alert_Login(HomeSecurityServiceActivity.this, getResources().getText(R.string.server_error).toString());
            }
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

                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);

                mVenderlist = Jsondata.getservices_provider(sp.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.homesecurity_id)), HomeSecurityServiceActivity.this);

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

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(HomeSecurityServiceActivity.this, android.R.layout.simple_spinner_dropdown_item, array); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            mSpinnerVender.setAdapter(spinnerArrayAdapter);

        }

    }

    class HttpGetAsyncTask_get extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(HomeSecurityServiceActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                service_prefrences = Jsondata.getservices_prefences(sp.getString("app_user_id", ""), String.valueOf(getResources().getText(R.string.homesecurity_id)), HomeSecurityServiceActivity.this);

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
                    mButtonUnsubscribe.setVisibility(View.VISIBLE);
                    String check_string = service_prefrences.get(0).get("is_contract").toString();
                    String spin_string = service_prefrences.get(0).get("service_provider").toString();
                    if (check_string.equalsIgnoreCase("true")) {
                        mCheckBoxContract.setChecked(true);
                        mEditTextStartDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                        mEditTextEndDate.setBackgroundColor(getResources().getColor(R.color.edit_text_backround_color));
                    } else {
                        mCheckBoxContract.setChecked(false);
                        String[] departure = service_prefrences.get(0).get("end_date").toString().split(" ");
                        String[] start_date = service_prefrences.get(0).get("start_date").toString().split(" ");
                        mEditTextEndDate.setText(departure[0]);
                        mEditTextStartDate.setText(start_date[0]);
                    }

                    mEditTextPrice.setText(String.format("%.2f", Double.parseDouble(service_prefrences.get(0).get("price"))));
                    int index = MenuItemGlobal.getIndex(spin_string, Jsondata.provider_id_list);
                    String provider_name = Jsondata.provider_id_list.get(index).get("provider_name").toString();
                    int value = mVenderlist.indexOf(provider_name);
                    mSpinnerVender.setSelection(value);
                } else {
                    // Show_Dailog.Show_Alert_Login(Home_Security.this, getResources().getText(R.string.server_error).toString());
                }
            } catch (Exception e) {
            }

            SetTextRight();
        }

    }
}
