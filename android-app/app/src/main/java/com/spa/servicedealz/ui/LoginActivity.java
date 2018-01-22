package com.spa.servicedealz.ui;


import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.localytics.android.Localytics;
import com.spa.MyApplication;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.login.LoginData;
import com.spa.model.password.CompPassword;
import com.spa.notification.GCMNotificationIntentService;
import com.spa.notification.MyPush;
import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.DashboardActivity;
import com.spa.servicedealz.drawer.GuestDashbaordActiviy;
import com.spa.servicedealz.drawer.ProfileActivity;
import com.spa.utils.Constant;
import com.spa.utils.CustomizeDialog;
import com.spa.utils.Jsondata;
import com.spa.utils.Utilities;
import com.spa.utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by E0115Diwakar on 2/24/2015.
 */

/**
 * FileName : LoginActivity
 * Description :Log in With Facebook ,GooglePlus, Or Email and password
 * Dependencies : Internet,facebook,google plus,google play service
 */
public class LoginActivity extends AppCompatActivity implements NetworkUtil.changeNetworkInterFace, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private ImageView mImageViewTwitter, mImageViewGooglePlus, mImageViewFacebook;

    private ProgressDialog pDialog;
    private CustomizeDialog mCustomizeDialog;

    private Boolean isInternetPresent = false;
    private Button mButtonSighUp, mButtonSighIn, mButtonGuestLogin;

    private MyPush mypush = new MyPush(Constant.PROJECT_ID);
    private EditText mEditTextEmail, mEditTextPassword;
    private CheckBox mCheckBoxRemember;
    private static final int RC_SIGN_IN = 0;

    private TextView mTextViewForgetPassword;
    private boolean mIntentInProgress;
    private String regId;
    private ConnectionResult mConnectionResult;
    private CallbackManager callbackManager;
    private ProgressDialog mProgressDialog;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private GoogleApiClient mGoogleApiClient;
    private String TAG = "LoginActivity";
    private LoginData loginData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.login);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        NetworkUtil.setChangeNetWorkListener(LoginActivity.this);
        mSharedPreferences = getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        mEditor = mSharedPreferences.edit();
        mEditor.putString(Constant.LOGIN_FLAG,
                "");
        mEditor.commit();
        isInternetPresent = NetworkUtil.isConnectingToInternet(LoginActivity.this);
        Utilities.GetKeyHash(LoginActivity.this);
        RegisterForPushNotification();
        MapFindId();
        setEdiTextData();
        setClickListner();
        //=====================================Google Api client connection object======================================================
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();
        //==========================================================================================
    }

    public void localyticstagEvent(String method, String success, String event) {
        Map<String, String> home_values = new HashMap<String, String>();

        home_values.put("Success", success);
        home_values.put("Method", method);

        Localytics.tagEvent(event, home_values);

    }


    /*
        * Method to set clicklistner
        * */
    private void setClickListner() {
        mImageViewTwitter.setOnClickListener(this);
        mTextViewForgetPassword.setOnClickListener(this);
        mImageViewGooglePlus.setOnClickListener(this);
        mImageViewFacebook.setOnClickListener(this);
        mButtonSighUp.setOnClickListener(this);
        mButtonGuestLogin.setOnClickListener(this);
        mButtonSighIn.setOnClickListener(this);
        mCheckBoxRemember.setOnClickListener(this);
    }

    /*
    * Method to set data in edittext
    * */
    private void setEdiTextData() {
        String etx_email_string = mSharedPreferences.getString(Constant.USER_EMAIL, "");
        String etx_pass_string = mSharedPreferences.getString(Constant.USER_PASSWORD, "");
        String checked = mSharedPreferences.getString(Constant.USER_CREDETIALS_REMEMBER, "");
        if (checked.equalsIgnoreCase(Constant.TRUE)) {
            mCheckBoxRemember.setChecked(true);
            mEditTextEmail.setText(etx_email_string);
            mEditTextPassword.setText(etx_pass_string);
        }
    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    /*
       * Method to find Id of view
       * */
    private void MapFindId() {
        mButtonGuestLogin = (Button) findViewById(R.id.txt_guest_login);
        mTextViewForgetPassword = (TextView) findViewById(R.id.txt_forget_password);
        mCheckBoxRemember = (CheckBox) findViewById(R.id.chk_remember);
        mEditTextEmail = (EditText) findViewById(R.id.etx_email);
        mEditTextPassword = (EditText) findViewById(R.id.etx_password);
        mImageViewFacebook = (ImageView) findViewById(R.id.img_fb);
        mImageViewFacebook.setBackgroundResource(R.drawable.facebook);
        mButtonSighUp = (Button) findViewById(R.id.btn_sign_up);
        mButtonSighIn = (Button) findViewById(R.id.btn_sign_in);
        mImageViewTwitter = (ImageView) findViewById(R.id.img_tw);
        mImageViewGooglePlus = (ImageView) findViewById(R.id.img_plus);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_fb:
                if (!isInternetPresent) {
                    ShowDailog.Show_Alert_Dailog(LoginActivity.this);
                    return;
                } else {
                    facebookLogin();
                }
                break;
            case R.id.img_plus:
                if (!mGoogleApiClient.isConnecting()) {
                    mProgressDialog = new ProgressDialog(this);
                    mProgressDialog.setMessage(("Please Wait...."));
                    mProgressDialog.setCancelable(false);
                    mProgressDialog.show();
                    resolveSignInError();
                }
                break;
            case R.id.txt_forget_password:
                showForgotPasswordDialog();

                break;
            case R.id.txt_guest_login:
                mEditor.putString("guestfitstime", Constant.YES_FLAG);
                mEditor.commit();
                if (!mSharedPreferences.getString("guestzipcode", "").equalsIgnoreCase("")) {

                    Intent intent = new Intent(getApplicationContext(), GuestDashbaordActiviy.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    Intent guestlogin = new Intent(getApplicationContext(), ZipCodeActivity.class);
                    startActivity(guestlogin);
                }

                break;
            case R.id.btn_sign_in:
                if (mCheckBoxRemember.isChecked()) {
                    mEditor.putString(Constant.USER_EMAIL,
                            mEditTextEmail.getText().toString());
                    mEditor.putString(Constant.USER_CREDETIALS_REMEMBER,
                            Constant.TRUE);
                    mEditor.putString(Constant.USER_PASSWORD,
                            mEditTextPassword.getText().toString());
                    mEditor.commit();
                } else {
                    mEditor.putString(Constant.USER_CREDETIALS_REMEMBER,
                            Constant.FALSE);
                    mEditor.putString(Constant.USER_EMAIL,
                            "");
                    mEditor.putString(Constant.USER_PASSWORD,
                            "");
                    mEditor.commit();
                }
                if (isInternetPresent) {
                    if (Validation.isFieldEmpty(mEditTextEmail)) {
                        Toast.makeText(this, getResources().getString(R.string.email_error), Toast.LENGTH_LONG).show();
                    } else if (Validation.isEmailValid(mEditTextEmail.getText().toString())) {
                        Toast.makeText(this, getResources().getString(R.string.email_valid_error),
                                Toast.LENGTH_LONG).show();
                    } else if (Validation.isFieldEmpty(mEditTextPassword)) {
                        Toast.makeText(this, getResources().getString(R.string.password_empty_error), Toast.LENGTH_LONG).show();
                    } else {
                        new HttpGetAsyncTask().execute();
                    }
                } else {
                    ShowDailog.Show_Alert_Dailog(LoginActivity.this);
                }


                break;
            case R.id.btn_sign_up:
//                new HttpGetAsyncTaskGetCompPass().execute();
                Intent btn_sign_up = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(btn_sign_up);
                break;
            case R.id.chk_remember:

                if (mCheckBoxRemember.isChecked()) {

                    mEditor.putString(Constant.USER_EMAIL,
                            mEditTextEmail.getText().toString());
                    mEditor.putString(Constant.USER_CREDETIALS_REMEMBER,
                            Constant.TRUE);
                    mEditor.putString(Constant.USER_PASSWORD,
                            mEditTextPassword.getText().toString());
                    mEditor.commit();
                } else {
                    mEditor.putString(Constant.USER_CREDETIALS_REMEMBER,
                            Constant.FALSE);
                    mEditor.putString(Constant.USER_EMAIL,
                            "");
                    mEditor.putString(Constant.USER_PASSWORD,
                            "");
                    mEditor.commit();

                }
                break;
            case R.id.img_tw:
                ShowDailog.Show_Alert_Login(LoginActivity.this, "Coming Soon".toString());

                break;
        }
    }

    /*
          * Method to show forgotpassword dialog
          * */
    public void showForgotPasswordDialog() {
        mCustomizeDialog = new CustomizeDialog(this);
        mCustomizeDialog.setCancelable(false);
        mCustomizeDialog.setContentView(R.layout.forgot_password_dialog);
        final EditText EMAIL = (EditText) mCustomizeDialog.findViewById(R.id.ed_email);
        ImageButton imagebtn_Cancel = (ImageButton) mCustomizeDialog.findViewById(R.id.imgbtn_cancel);
        Button btn_Submit = (Button) mCustomizeDialog.findViewById(R.id.btnforgotpassword);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetPresent) {
                    if (Validation.isFieldEmpty(EMAIL)) {
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.email_error), Toast.LENGTH_LONG)
                                .show();
                    } else if (Validation.isEmailValid(EMAIL.getText().toString())) {
                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.email_valid_error),
                                Toast.LENGTH_LONG).show();
                    } else {
                        new HttpGetAsyncTask_forgetpassword().execute(EMAIL.getText().toString().trim());
                    }
                } else {
                    ShowDailog.Show_Alert_Dailog(LoginActivity.this);
                }
            }
        });
        imagebtn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomizeDialog.dismiss();
            }
        });
        mCustomizeDialog.show();
    }

    @Override
    public void updateNetwork(String s) {

    }

    /*To check user account is valid or not*/
    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "", email, pass;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();
            email = mEditTextEmail.getText().toString();
            pass = mEditTextPassword.getText().toString();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                regId = mypush.getRegistrationId(LoginActivity.this);
                GCMNotificationIntentService.setTargetActivity(LoginActivity.this);
                if (regId == null) {
                    RegisterForPushNotification();
                }
                loginData = Jsondata.getlogin(email, pass, regId, LoginActivity.this);
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
                if (loginData.isSuccess()) {
                    mEditor.putString(Constant.LOGIN_ACCOUNT_FLAG,
                            Constant.SIGNIN);
                    mEditor.putString(Constant.APP_USER_ID,
                            loginData.getData().getId() + "");
                    mEditor.putString(Constant.LOGIN_FLAG,
                            Constant.YES_FLAG);
                    mEditor.commit();
                    localyticstagEvent("Mail", "Yes", "Login Attempt");
                    String user_preference = mSharedPreferences.getString(Constant.USER_PREFRENCE, "");
                    if (user_preference.equalsIgnoreCase(Constant.TRUE)) {
                        mEditor.putString(Constant.USER_DASHBOARD_FLAG,
                                Constant.YES_FLAG);
                        localyticstagEvent("Dashboard", "Yes", "Login Move");
                        mEditor.putString("signupwithfirsttime",
                                "");
                        mEditor.commit();
                        Intent btn_sign_in = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(btn_sign_in);
                        finish();
                       /* Intent giftcard = new Intent(getApplicationContext(), AdvertisementActivity.class);
                        startActivity(giftcard);*/
                    } else {
                        localyticstagEvent("Profile", "Yes", "Login Move");
                        mEditor.putString("signupwithfirsttime",
                                Constant.YES_FLAG);
                        mEditor.commit();
                        Intent btn_sign_in = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(btn_sign_in);
                        finish();
                    }
                    mEditor.commit();
                } else if (!loginData.isSuccess()) {
                    pDialog.dismiss();
                    localyticstagEvent("Mail", "No", "Login Attempt");
                    //localyticstagEventNo();
                    ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getText(R.string.login_error).toString());
                } else if (Jsondata.Success.equalsIgnoreCase("")) {
                    pDialog.dismiss();
                    ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getText(R.string.server_error).toString());
                }
            } catch (Exception e) {
                pDialog.dismiss();
                ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getText(R.string.server_error).toString());
            }

        }

    }

    /* Send password to user email id*/
    class HttpGetAsyncTask_forgetpassword extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.getforget_password(params[0], LoginActivity.this);
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
                if (Jsondata.Success.equalsIgnoreCase(Constant.TRUE)) {
                    mCustomizeDialog.dismiss();
                    ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getString(R.string.forget_password));
                } else if (Jsondata.Success.equalsIgnoreCase(Constant.FALSE)) {
                    ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getString(R.string.email_valid_error));
                } else {
                    ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getText(R.string.server_error).toString());
                }
            } catch (Exception e) {
                ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getText(R.string.server_error).toString());
            }
        }

    }

    /*Get App user id on the basis of social network email id*/
    class HttpGetAsyncTask_app_userid extends AsyncTask<String, Void, String> {
        String response;
        String facebook_email = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();
            String login_with = mSharedPreferences.getString(Constant.LOGIN_ACCOUNT_FLAG, "");
            if (login_with.equalsIgnoreCase(Constant.SIGNIN)) {
                facebook_email = mEditTextEmail.getText().toString();
            } else {
                facebook_email = mSharedPreferences.getString(Constant.FACEBOOK_EMAIL, "");
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.get_app_userid(facebook_email, LoginActivity.this);
            } catch (Exception e) {
                pDialog.dismiss();
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
                if (Jsondata.Success.equalsIgnoreCase(Constant.TRUE)) {
                    mEditor.putString(Constant.APP_USER_ID,
                            response);
                    mEditor.putString(Constant.LOGIN_FLAG,
                            Constant.YES_FLAG);
                    mEditor.commit();
                    String user_preference = mSharedPreferences.getString(Constant.USER_PREFRENCE, "");
                    if (user_preference.equalsIgnoreCase(Constant.TRUE)) {
                        mEditor.putString(Constant.USER_DASHBOARD_FLAG,
                                Constant.YES_FLAG);

                        localyticstagEvent("Dashboard", "Yes", "Login Move");
                        mEditor.putString("signupwithfirsttime",
                                "");
                        mEditor.commit();
                        Intent btn_sign_in = new Intent(getApplicationContext(), DashboardActivity.class);
                        startActivity(btn_sign_in);
                        finish();
//                        Intent giftcard = new Intent(getApplicationContext(), AdvertisementActivity.class);
//                        startActivity(giftcard);
                    } else {
                        localyticstagEvent("Profile", "Yes", "Login Move");
                        mEditor.putString("signupwithfirsttime",
                                Constant.YES_FLAG);
                        mEditor.commit();
                        Intent btn_sign_in = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(btn_sign_in);
                        finish();
                    }
                } else if (Jsondata.Success.equalsIgnoreCase(Constant.FALSE)) {
                    new facebook_login_save().execute();
                } else {
                    ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getText(R.string.server_error).toString());
                }
            } catch (Exception e) {
                ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getText(R.string.server_error).toString());
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        try {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        } catch (Exception e) {
        }
        if (requestCode == RC_SIGN_IN) {
            mIntentInProgress = false;
            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    class facebook_login_save extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginActivity.this);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.getfb_save(regId, "", mSharedPreferences.getString(Constant.FIRST_NAME, ""), mSharedPreferences.getString(Constant.LAST_NAME, ""), mSharedPreferences.getString(Constant.FACEBOOK_EMAIL, ""), "", "", "", "", LoginActivity.this);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }


        protected void onPostExecute(String response) {
            pDialog.dismiss();
            try {
                if (response != "" || response != null) {
                    JSONObject jobj = new JSONObject(response);
                    String Success = jobj.getString("success");
                    if (Success.equalsIgnoreCase(Constant.TRUE)) {
                        String app_user_id = jobj.getString("app_user_id");
                        mEditor.putString(Constant.APP_USER_ID,
                                app_user_id);
                        mEditor.putString(Constant.LOGIN_FLAG,
                                Constant.YES_FLAG);
                        mEditor.commit();

                        new HttpGetAsyncTask_save_prefrence().execute();
                        localyticstagEvent("Profile", "Yes", "Login Move");
                        mEditor.putString("signupwithfirsttime",
                                Constant.YES_FLAG);
                        mEditor.commit();
                        Intent dashboard = new Intent(getApplicationContext(), ProfileActivity.class);
                        startActivity(dashboard);
                        finish();
                    } else {
                        ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getText(R.string.server_error).toString());
                    }

                } else {
                    ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getText(R.string.server_error).toString());
                }


            } catch (Exception e) {
                e.printStackTrace();
                ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getText(R.string.server_error).toString());
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
                response = Jsondata.get_save_prefrences(mSharedPreferences.getString(Constant.APP_USER_ID, ""), "true", "30", "true", "Weekly", "Weekly", "true", "3", "3", "true", "true", LoginActivity.this);
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

    /*
            * Method to Login with facebook
            * */
    private void facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("onSuccess");
                GraphRequest request = GraphRequest.newMeRequest
                        (loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                // Application code
                                Log.v("LoginActivity", response.toString());
                                try {
                                    localyticstagEvent("Facebook", "Yes", "Login Attempt");
                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    mEditor.putString(Constant.FIRST_NAME,
                                            name);
                                    mEditor.putString("id",
                                            id);
                                    mEditor.putString(Constant.FACEBOOK_EMAIL,
                                            email);
                                    mEditor.putString(Constant.LOGIN_ACCOUNT_FLAG,
                                            Constant.FACEBOOK);
                                    mEditor.commit();
                                    new HttpGetAsyncTask_app_userid().execute();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();

            }

            @Override
            public void onCancel() {

                localyticstagEvent("Facebook", "No", "Login Attempt");
                System.out.println("onCancel");
            }


            @Override
            public void onError(FacebookException exception) {
                localyticstagEvent("Mail", "No", "Login Attempt");
                System.out.println("onError");
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("LoginActivity");
    }

    /*
            * Method to RegisterForPushNotification
            * */
    public void RegisterForPushNotification() {
        if (mypush != null && !mypush.checkPlayServices(this)) {
        } else {
            regId = mypush.getRegistrationId(this);
            GCMNotificationIntentService.setTargetActivity(this);
            if (regId == null) {
                mypush.registerInBackground(this, this);
            }
        }
    }

    protected void onStart() {
        super.onStart();
        mypush.checkPlayServices(this);
        if (mGoogleApiClient.isConnected()) {
            Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
            mGoogleApiClient.disconnect();
            mGoogleApiClient.connect();
        } else {
            mGoogleApiClient.connect();
        }
        // Obtain the shared Tracker instance.
        MyApplication application = (MyApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        Log.i(TAG, "Setting screen name: " + TAG);
        mTracker.setScreenName("Image~" + TAG);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    /**
     * Method to resolve any signin errors
     */
    private void resolveSignInError() {
        try {
            if (mConnectionResult.hasResolution()) {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);

            }
        } catch (Exception e) {
            mIntentInProgress = false;
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult result) {
        localyticstagEvent("Google Plus", "No", "Login Attempt");
        if (!result.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
                    0).show();
            return;
        }
        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = result;
        }
    }

    @Override
    public void onConnected(Bundle arg0) {
        // Get user's information
        getProfileInformation();
    }

    /*
               * Method to get all details of googleplus user's
               * */
    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                com.google.android.gms.plus.model.people.Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                mEditor.putString(Constant.FIRST_NAME, currentPerson.getDisplayName());

                mEditor.putString(Constant.GOOGLE_PLUS_IMAGE,
                        currentPerson.getImage().getUrl());

                mEditor.putString(Constant.GOOGLE_PLUS_EMAIL,
                        Plus.AccountApi.getAccountName(mGoogleApiClient));
                mEditor.putString(Constant.FACEBOOK_EMAIL,
                        Plus.AccountApi.getAccountName(mGoogleApiClient));
                mEditor.putString(Constant.LOGIN_FLAG,
                        Constant.YES_FLAG);
                mEditor.putString(Constant.LOGIN_ACCOUNT_FLAG,
                        Constant.GOOGLE_PLUS);
                mEditor.commit();
                mProgressDialog.dismiss();
                localyticstagEvent("Google Plus", "Yes", "Login Attempt");
                new HttpGetAsyncTask_app_userid().execute();

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
            ShowDailog.Show_Alert_Login(LoginActivity.this, getResources().getText(R.string.server_error).toString());
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }



}
