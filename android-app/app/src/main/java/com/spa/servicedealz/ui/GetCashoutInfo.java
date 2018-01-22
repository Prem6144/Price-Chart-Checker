package com.spa.servicedealz.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.localytics.android.Localytics;
import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.DashboardActivity;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;
import com.spa.utils.Validation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Diwakar on 5/31/2016.
 */
public class GetCashoutInfo extends AppCompatActivity implements View.OnClickListener {
    private Button mButtonGetItNow, mButtonCancel;
    private TextView mTextViewAmount;
    private EditText mEditTextEmail, mEditTextConfirmEmail;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ProgressDialog pDialog;
    private Boolean isInternetPresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_cashout_info);
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

    }

    private void MapIngId() {
        mButtonCancel = (Button) findViewById(R.id.btn_cancel);
        mButtonGetItNow = (Button) findViewById(R.id.btn_get_itnow);
        mTextViewAmount = (TextView) findViewById(R.id.txt_available_balance);
        mEditTextEmail = (EditText) findViewById(R.id.etx_email);
        mEditTextConfirmEmail = (EditText) findViewById(R.id.etx_confirm_email);
        mButtonGetItNow.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
        mButtonGetItNow.setOnClickListener(this);
        mTextViewAmount.setText("$" + mSharedPreferences.getString("amount", ""));

    }

    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("SignUpActivity");
    }

    public void localyticstagEvent(String method) {
        Map<String, String> home_values = new HashMap<String, String>();

        home_values.put("Success", "Yes");
        home_values.put("Method", method);

        Localytics.tagEvent("SignUp", home_values);

    }

    class HttpGetAsyncTask_Get_Data extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(GetCashoutInfo.this);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.get_cashout(mSharedPreferences.getString(Constant.APP_USER_ID, ""), mSharedPreferences.getString("amount", ""), mSharedPreferences.getString("cashout_voucher", ""), mSharedPreferences.getString("cashout_cash", ""), mEditTextEmail.getText().toString(), GetCashoutInfo.this);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress dialog
         * **/

        protected void onPostExecute(String response1) {
            pDialog.dismiss();

            if (response.equalsIgnoreCase("true")) {
                Show_Alert_Dailog();
            } else {
                ShowDailog.Show_Alert_Login(GetCashoutInfo.this, getResources().getText(R.string.server_error).toString());
            }
        }

    }

    public String Show_Alert_Dailog() {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(
                this);
        alertbox.setTitle("Cashout request successful!");
        alertbox.setMessage("You'll receive your money and instructions within 7 business days.");
        alertbox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent HOME = new Intent(getApplicationContext(), DashboardActivity.class);
                        HOME.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(HOME);
                        finish();
                    }
                });
        alertbox.setCancelable(false);
        alertbox.show();
        return null;
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
        toolbar.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Cashout Information" + "</font>")));
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
            case R.id.btn_get_itnow:
                if (Validation.isFieldEmpty(mEditTextEmail)) {
                    Toast.makeText(this, getResources().getString(R.string.email_error), Toast.LENGTH_LONG)
                            .show();
                } else if (Validation.isEmailValid(mEditTextEmail.getText().toString())) {
                    Toast.makeText(this, getResources().getString(R.string.email_valid_error),
                            Toast.LENGTH_LONG).show();
                } else if (Validation.isFieldEmpty(mEditTextConfirmEmail)) {
                    Toast.makeText(this, getResources().getString(R.string.email_error), Toast.LENGTH_LONG)
                            .show();
                } else if (Validation.isEmailValid(mEditTextConfirmEmail.getText().toString())) {
                    Toast.makeText(this, getResources().getString(R.string.email_valid_error),
                            Toast.LENGTH_LONG).show();
                } else if (Validation.isPasswordMatch(mEditTextConfirmEmail,
                        mEditTextEmail)) {
                    Toast.makeText(this, "Email does not match",
                            Toast.LENGTH_LONG).show();
                } else {
                    isInternetPresent = NetworkUtil.isConnectingToInternet(this);
                    if (isInternetPresent) {
                        new HttpGetAsyncTask_Get_Data().execute();
                    } else {
                        ShowDailog.Show_Alert_Dailog(this);
                    }

                }

                break;
            case R.id.btn_cancel:
                finish();
                break;


        }

    }
}
