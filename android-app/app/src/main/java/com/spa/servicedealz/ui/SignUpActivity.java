package com.spa.servicedealz.ui;

import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.RelativeSizeSpan;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.localytics.android.Localytics;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.password.CompPassword;
import com.spa.notification.GCMNotificationIntentService;
import com.spa.notification.MyPush;
import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.ProfileActivity;
import com.spa.utils.Constant;
import com.spa.utils.CustomHttpClient;
import com.spa.utils.Jsondata;
import com.spa.utils.Url;
import com.spa.utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spa.utils.MenuItemGlobal.setCapitalizeTextWatcher;

/**
 * Created by Diwakar on 2/26/2015.
 */

/**
 * FileName : SignUpActivity
 * Description :Create an account for ServiceDeal
 * Dependencies : Internet,google play service
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, NetworkUtil.changeNetworkInterFace {
    private EditText mEditTextEmail, mEditTextPassword, mEditTextConfirmPassword, mEditTextName, mEditTextLastName;
    private Button mButtonRegister;
    private Boolean isInternetPresent = false;
    private CheckBox mCheckBoxTermConditions;
    private MyPush mypush = new MyPush(Constant.PROJECT_ID);
    private String mRegId;
    private TextView mTextViewTerm, mTextPasswordHint;
    private SharedPreferences mSharedPreferences;
    private CompPassword compPassword;
    String mCompPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        new HttpGetAsyncTaskGetCompPass().execute();
        NetworkUtil.setChangeNetWorkListener(SignUpActivity.this);
        isInternetPresent = NetworkUtil.isConnectingToInternet(SignUpActivity.this);
        mSharedPreferences = getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        setActionBar();
        MapFindId();
//        if (compPassword.getKey().equals("0")) {
//            mTextPasswordHint.setVisibility(View.VISIBLE);
//        }
     /*   Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false, null, null, null, null);
        startActivityForResult(intent, 10);*/
        mButtonRegister.setOnClickListener(this);
        setSpannableStringData();
        RegisterForPushNotification();

    }

    protected void onActivityResult(final int requestCode, final int resultCode, final Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {
            String emailAddress = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
            Toast.makeText(SignUpActivity.this, emailAddress, Toast.LENGTH_SHORT).show();

            // do something...
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("SignUpActivity");
    }

    /*set spannable string in term and conditions */
    private void setSpannableStringData() {
        SpannableString ss = new SpannableString(getResources().getString(R.string.term_condition));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View textView) {
                startActivity(new Intent(SignUpActivity.this, TermConditionsActivity.class));
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };
        ss.setSpan(new RelativeSizeSpan(1.0f), 13, 31, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(clickableSpan, 13, 31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTextViewTerm.setText(ss);
        mTextViewTerm.setMovementMethod(LinkMovementMethod.getInstance());
        mTextViewTerm.setHighlightColor(Color.red(17));
    }

    public void localyticstagEvent(String method) {
        Map<String, String> home_values = new HashMap<String, String>();

        home_values.put("Success", "Yes");
        home_values.put("Method", method);

        Localytics.tagEvent("SignUp", home_values);

    }

    /*
    * Method to find ids of views
    * */
    private void MapFindId() {
        mButtonRegister = (Button) findViewById(R.id.btn_register);
        mTextViewTerm = (TextView) findViewById(R.id.txt_term);
        mTextPasswordHint = (TextView) findViewById(R.id.passwordhint);
        mCheckBoxTermConditions = (CheckBox) findViewById(R.id.chk_term_conditions);
        mEditTextEmail = (EditText) findViewById(R.id.etx_email);
        mEditTextPassword = (EditText) findViewById(R.id.etx_password);
        mEditTextName = (EditText) findViewById(R.id.etx_name);
        mEditTextLastName = (EditText) findViewById(R.id.etx_lname);
        mEditTextConfirmPassword = (EditText) findViewById(R.id.etx_confirm_password);

        setCapitalizeTextWatcher(mEditTextName);
        setCapitalizeTextWatcher(mEditTextLastName);
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
        toolbar.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "ServiceDealz" + "</font>")));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*Register for Gcm notification*/
    public void RegisterForPushNotification() {
        if (!mypush.checkPlayServices(this)) {
        } else {
            mRegId = mypush.getRegistrationId(this);
            GCMNotificationIntentService.setTargetActivity(this);
            if (mRegId == null)
                mypush.registerInBackground(this, this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:

                setSendUserInformationToServer();
                break;
        }

    }

    /*Send user data to server */
    private void setSendUserInformationToServer() {

        if (Validation.isFieldEmpty(mEditTextName)) {
            Toast.makeText(this, getResources().getString(R.string.first_name_error), Toast.LENGTH_LONG)
                    .show();
        } else if (Validation.isFieldEmpty(mEditTextLastName)) {
            Toast.makeText(this, getResources().getString(R.string.last_name_error), Toast.LENGTH_LONG)
                    .show();
        } else if (Validation.isFieldEmpty(mEditTextEmail)) {
            Toast.makeText(this, getResources().getString(R.string.email_error), Toast.LENGTH_LONG)
                    .show();
        } else if (Validation.isEmailValid(mEditTextEmail.getText().toString())) {
            Toast.makeText(this, getResources().getString(R.string.email_valid_error),
                    Toast.LENGTH_LONG).show();
        } else if (Validation.isFieldEmpty(mEditTextPassword)) {
            Toast.makeText(this, getResources().getString(R.string.password_empty_error), Toast.LENGTH_LONG)
                    .show();
        } else if (Validation.isPasswordValidForNormal(mEditTextPassword) && mCompPassword.equalsIgnoreCase("0")) {
            Toast.makeText(this, getResources().getString(R.string.password_valid_error),
                    Toast.LENGTH_LONG).show();

        } else if (Validation.isPasswordValid(mEditTextPassword) && mCompPassword.equalsIgnoreCase("1")) {

            Toast.makeText(this, getResources().getString(R.string.password_valid_error),
                    Toast.LENGTH_LONG).show();


        } else if (Validation.isFieldEmpty(mEditTextConfirmPassword)) {
            Toast.makeText(this, getResources().getString(R.string.confirm_password_error),
                    Toast.LENGTH_LONG).show();
        } else if (Validation.isPasswordMatch(mEditTextPassword,
                mEditTextConfirmPassword)) {
            Toast.makeText(this, getResources().getString(R.string.password_match_error),
                    Toast.LENGTH_LONG).show();
        } else if ((mEditTextPassword.getText().toString().contains(" "))) {
            Toast.makeText(this, getResources().getString(R.string.space_error),
                    Toast.LENGTH_LONG).show();
        } else if (!mCheckBoxTermConditions.isChecked()) {
            Toast.makeText(this, getResources().getString(R.string.term_condition_error),
                    Toast.LENGTH_LONG).show();
        } else {

            if (isInternetPresent) {

                new HttpGetAsyncTask().execute();
            } else {
                ShowDailog.Show_Alert_Dailog(this);
            }


        }
    }



    @Override
    public void updateNetwork(String s) {

    }

    /*send user data to server in background*/
    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;
        List<Pair<String, String>> param = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SignUpActivity.this);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();
            mRegId = mypush.getRegistrationId(SignUpActivity.this);
            GCMNotificationIntentService.setTargetActivity(SignUpActivity.this);
            if (mRegId == null) {
                RegisterForPushNotification();
            }
            param.add(new Pair("email", mEditTextEmail.getText().toString()));
            try {
                param.add(new Pair("password", (mEditTextPassword.getText().toString())));
                param.add(new Pair("gcm_id", mRegId));
                param.add(new Pair("device_flag", "android"));
                param.add(new Pair("first_name", Jsondata.encryptMsg(mEditTextName.getText().toString().trim())));
                param.add(new Pair("last_name", Jsondata.encryptMsg(mEditTextLastName.getText().toString().trim())));
                param.add(new Pair("device_id", mSharedPreferences.getString("androidid", "")));
                param.add(new Pair("token", mSharedPreferences.getString("api_token", "")));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                response = CustomHttpClient.executeHttpPost(Jsondata.main_url + Url.sign_up_url,
                        param);
                System.out.println("Response:" + response);
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
            String Success = "";
            try {

                JSONObject jobj = new JSONObject(response);
                if (response != "" || response != null) {
                    Success = jobj.getString("success");
                    if (Success.equalsIgnoreCase("true")) {
                        String app_user_id = jobj.getString("app_user_id");
                        SharedPreferences.Editor editor = mSharedPreferences.edit();
                        editor.putString(Constant.APP_USER_ID,
                                app_user_id);
                        editor.putString(Constant.LOGIN_ACCOUNT_FLAG,
                                Constant.SIGNUP);
                        editor.putString("signupwithfirsttime",
                                Constant.YES_FLAG);
                        editor.putString(Constant.ZIPCODE_FLAG,
                                "");
                        editor.putString(Constant.USER_PREFRENCE,
                                Constant.FALSE);
                        editor.putString(Constant.USER_DASHBOARD_FLAG,
                                "");
                        editor.putString(Constant.SIGNUP_EMAIL,
                                mEditTextEmail.getText().toString());
                        editor.putString(Constant.USER_EMAIL,
                                mEditTextEmail.getText().toString());
                        editor.putString(Constant.LOGIN_FLAG,
                                Constant.YES_FLAG);
                        editor.putInt(Constant.VIEW_PAGER_POSITION, 0);
                        editor.commit();
                        new HttpGetAsyncTask_save_prefrence().execute();
                        localyticstagEvent("Profile");
                        Intent dashboard = new Intent(getApplicationContext(), ProfileActivity.class);
                        dashboard.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(dashboard);
                        finish();
                    } else {
                        ShowDailog.Show_Alert_Login(SignUpActivity.this, getResources().getText(R.string.sign_up_error).toString());
                    }

                } else {
                    ShowDailog.Show_Alert_Login(SignUpActivity.this, getResources().getText(R.string.server_error).toString());
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    class HttpGetAsyncTask_save_prefrence extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.get_save_prefrences(mSharedPreferences.getString(Constant.APP_USER_ID, ""), "true", "30", "true", "Weekly", "Weekly", "true", "3", "3", "true", "true", SignUpActivity.this);
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
        }
    }

    class HttpGetAsyncTaskGetCompPass extends AsyncTask<String, Void, String> {
        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            try {

                compPassword = Jsondata.getCompletedPassword(getBaseContext());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress mDialog
         * **/

        protected void onPostExecute(String response) {

            try {
                if (compPassword.getSuccess()) {

                    mCompPassword = compPassword.getKey();

                    if (mCompPassword.equalsIgnoreCase("0")) {
//
                        mEditTextPassword.setVisibility(View.VISIBLE);
                        mEditTextPassword.setHint("Please Enter Password");
                    }
                    if (mCompPassword.equalsIgnoreCase("1")) {
                        mEditTextPassword.setHint("Please Enter Complex Password");
                    }
                }
            } catch (Exception e) {
                //  ShowDailog.Show_Alert_Login(PlaceOrderAndPay.this, getResources().getText(R.string.server_error).toString());
                e.printStackTrace();
            }

        }
    }


}
