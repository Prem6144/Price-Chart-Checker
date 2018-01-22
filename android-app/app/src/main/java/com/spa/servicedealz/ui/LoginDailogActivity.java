package com.spa.servicedealz.ui;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.spa.MyApplication;
import com.spa.servicedealz.R;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.notification.GCMNotificationIntentService;
import com.spa.notification.MyPush;
import com.spa.utils.CustomizeDialog;
import com.spa.utils.Jsondata;
import com.spa.utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


/**
 * Created by E0115Diwakar on 2/24/2015.
 */

/**
 * FileName : LoginDailogActivity
 * Description :Log in With Facebook ,GooglePlus, Or Email and password
 * Dependencies : Facebook sdk ,Googleplayservice ,.Internet
 */
public class LoginDailogActivity extends Activity implements NetworkUtil.changeNetworkInterFace,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener {
    private ImageView mButtonGooglePlus, mButtonFb, mButtonClose;
    public static ArrayList<HashMap<String, String>> user_profile = new ArrayList<HashMap<String, String>>();
    private ProgressDialog pDialog;

    Boolean isInternetPresent = false;
    //ShowAlert alert = new ShowAlert();
    private Button mButtonSignUp, mButtonSignIn;
    private Context mContext;
    // Instance of Facebook Class
    @SuppressWarnings("deprecation")
    //  private LoginButton mButtonFb;
            CustomizeDialog customizeDialog;
    private static String PROJECT_ID = "447266615651";
    MyPush mypush = new MyPush(PROJECT_ID);

    public static SharedPreferences mPrefs;
    EditText etx_email, etx_password;
    private static final int RC_SIGN_IN = 0;

    public static GoogleApiClient mGoogleApiClient;
    private boolean mIntentInProgress;
    String regId;
    //  private boolean mSignInClicked;

    public static ConnectionResult mConnectionResult;
    private CallbackManager callbackManager;

    String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.logindailogactvity);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        mContext = this;
        // Helpshift.showConversation(Login.this);
        mPrefs = getPreferences(mContext.MODE_PRIVATE);
        NetworkUtil.setChangeNetWorkListener(LoginDailogActivity.this);
        isInternetPresent = NetworkUtil.isConnectingToInternet(LoginDailogActivity.this);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.spa.servicedeal",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        //==========================================================================================

        if (!mypush.checkPlayServices(this)) {
//            Toast.makeText(
//                    this,
//                    "This device doesn't support Play services, App will not work normally",
//                    Toast.LENGTH_LONG).show();
        } else {
            regId = mypush.getRegistrationId(this);
            GCMNotificationIntentService.setTargetActivity(this);
            if (regId == null) {
                RegisterForPushNotification();
            }
        }
        //==========================================================================================
        etx_email = (EditText) findViewById(R.id.etx_email);
        etx_password = (EditText) findViewById(R.id.etx_password);
        mButtonFb = (ImageView) findViewById(R.id.img_fb);
        mButtonClose = (ImageView) findViewById(R.id.img_close);
        mButtonClose.setOnClickListener(this);
        mButtonFb.setBackgroundResource(R.drawable.facebook);
        //mButtonFb.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
        //mButtonFb.setHeight(180);
        //mButtonFb.setWidth(80);
        mButtonSignUp = (Button) findViewById(R.id.btn_sign_up);
        mButtonSignIn = (Button) findViewById(R.id.btn_sign_in);
        mButtonGooglePlus = (ImageView) findViewById(R.id.img_plus);
        mButtonGooglePlus.setOnClickListener(this);
        mButtonFb.setOnClickListener(this);
        mButtonSignUp.setOnClickListener(this);
        mButtonSignIn.setOnClickListener(this);
        SharedPreferences sp = getSharedPreferences("Pref_name",
                Activity.MODE_WORLD_READABLE);


        //==========================================================================================
        //  myFacebook.setFacebookLogin(mContext);
       /* MyLinkedin.linkedinInit(Constant.LINKEDIN_CONSUMER_KEY,
                Constant.LINKEDIN_CONSUMER_SECRET);
                  myLinkedin = new MyLinkedin(this);
        myLinkedin.setLinkedinLogin(this);*/


        //===========================================================================================
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN)
                .build();

        //==========================================================================================
    }

    public static void signOutFromGplus() {

    }

    @Override
    public void onClick(View v) {
        SharedPreferences sp = getSharedPreferences("Pref_name",
                Activity.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sp.edit();
        switch (v.getId()) {

            case R.id.img_fb:


                if (!isInternetPresent) {
                    // Internet Connection is not present
                    Toast.makeText(getApplicationContext(), "No Internet Connection!",
                            Toast.LENGTH_SHORT).show();
                    // stop executing code by return
                    return;
                } else {


                    facebookLogin();

                }

                break;
            case R.id.img_plus:
                if (!mGoogleApiClient.isConnecting()) {
                    resolveSignInError();
                } else {

                }
                break;
            case R.id.img_close:
                finish();
                break;


            case R.id.txt_forget_password:
                showForgotPasswordDialog();

                break;
            case R.id.btn_sign_in:


                if (isInternetPresent) {

                    if (Validation.isFieldEmpty(etx_email)) {
                        Toast.makeText(this, "Email is Required", Toast.LENGTH_LONG)
                                .show();
                    } else if (Validation.isEmailValid(etx_email.getText().toString())) {
                        Toast.makeText(this, "Please enter valid Email",
                                Toast.LENGTH_LONG).show();
                    } else if (Validation.isFieldEmpty(etx_password)) {
                        Toast.makeText(this, "Please Enter Password", Toast.LENGTH_LONG)
                                .show();
                    } else if (Validation.isPasswordValid(etx_password)) {
                        Toast.makeText(
                                this,
                                "Please enter valid Password between 8 to 12 characters",
                                Toast.LENGTH_LONG).show();
                    } else {
                        new HttpGetAsyncTask().execute();
                    }
                } else {
                    ShowDailog.Show_Alert_Dailog(LoginDailogActivity.this);
                }


                break;
            case R.id.btn_sign_up:

                Intent btn_sign_up = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(btn_sign_up);

                break;

            case R.id.img_tw:
                ShowDailog.Show_Alert_Login(LoginDailogActivity.this, "Coming Soon".toString());

                break;
        }
    }

    /*
                  * Method toshow forgotpassworddialog
                  * */
    public void showForgotPasswordDialog() {

        customizeDialog = new CustomizeDialog(this);
        customizeDialog.setCancelable(false);
        customizeDialog.setContentView(R.layout.forgot_password_dialog);
        final EditText ed_email = (EditText) customizeDialog.findViewById(R.id.ed_email);
        ImageButton imagebtn_Cancel = (ImageButton) customizeDialog.findViewById(R.id.imgbtn_cancel);
        Button btn_Submit = (Button) customizeDialog.findViewById(R.id.btnforgotpassword);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetPresent) {
                    if (Validation.isFieldEmpty(ed_email)) {
                        Toast.makeText(getApplicationContext(), "Email is Required", Toast.LENGTH_LONG)
                                .show();
                    } else if (Validation.isEmailValid(ed_email.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Please enter valid Email",
                                Toast.LENGTH_LONG).show();
                    } else {
                        new HttpGetAsyncTask_forgetpassword().execute(ed_email.getText().toString().trim());
                    }
                } else {
                    ShowDailog.Show_Alert_Dailog(LoginDailogActivity.this);
                }
            }
        });
        imagebtn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customizeDialog.dismiss();
            }
        });
        customizeDialog.show();
    }

    @Override
    public void updateNetwork(String s) {

    }

    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginDailogActivity.this);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                regId = mypush.getRegistrationId(LoginDailogActivity.this);
                GCMNotificationIntentService.setTargetActivity(LoginDailogActivity.this);
                if (regId == null) {
                    RegisterForPushNotification();
                }

               /* user_profile = Jsondata.getlogin(etx_email.getText().toString(),
                        etx_password.getText().toString(), regId, LoginDailogActivity.this);*/

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
                if (Jsondata.Success.equalsIgnoreCase("true")) {

                    SharedPreferences sp = getSharedPreferences("Pref_name",
                            Activity.MODE_WORLD_READABLE);
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("login_with",
                            "sign_in");

                    editor.putString("app_user_id",
                            user_profile.get(0).get("userid"));

                    editor.putString("login_flag",
                            "yes");
                    editor.commit();
                    String login_flag_dashboard = sp.getString("login_flag_dashboard", "");
                    if (login_flag_dashboard.equalsIgnoreCase("yes")) {
                      /*  Intent mButtonSignIn = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(mButtonSignIn);*/
                        finish();
                    } else {
                      /*  Intent mButtonSignIn = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mButtonSignIn);*/
                        finish();
                    }

                    editor.commit();


                } else if (Jsondata.Success.equalsIgnoreCase("false")) {

                    ShowDailog.Show_Alert_Login(LoginDailogActivity.this,
                            getResources().getText(R.string.login_error).toString());
                } else if (Jsondata.Success.equalsIgnoreCase("")) {

                    ShowDailog.Show_Alert_Login(LoginDailogActivity.this,
                            getResources().getText(R.string.server_error).toString());
                }
            } catch (Exception e) {
                ShowDailog.Show_Alert_Login(LoginDailogActivity.this, "Server not responding");
            }

        }

    }

    class HttpGetAsyncTask_forgetpassword extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginDailogActivity.this);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {


                response = Jsondata.getforget_password(params[0], LoginDailogActivity.this);

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
                    customizeDialog.dismiss();
                    ShowDailog.Show_Alert_Login(LoginDailogActivity.this, "Your password has been sent on your email-id.");
                } else if (Jsondata.Success.equalsIgnoreCase("false")) {
                    ShowDailog.Show_Alert_Login(LoginDailogActivity.this, "Fill valid email-id.");
                } else {
                    ShowDailog.Show_Alert_Login(LoginDailogActivity.this, "Server not responding");
                }
            } catch (Exception e) {
                ShowDailog.Show_Alert_Login(LoginDailogActivity.this, "Server not responding");
            }
        }

    }

    class HttpGetAsyncTask_app_userid extends AsyncTask<String, Void, String> {
        String response;
        private JSONObject profile;
        private ProgressDialog pdia;

      /*  HttpGetAsyncTask_app_userid(JSONObject profile) {
            this.profile = profile;
        }*/

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(LoginDailogActivity.this);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);

                String login_with = sp.getString("login_with", "");
                String facebook_email = "";
                if (login_with.equalsIgnoreCase("sign_in")) {
                    //   facebook_email = etx_email.getText().toString();
                } else if (login_with.equalsIgnoreCase("facebook")) {
                    facebook_email = sp.getString("facebook_email", "");
                } else if (login_with.equalsIgnoreCase("googleplus")) {
                    facebook_email = sp.getString("gplus_email", "");
                }

                response = Jsondata.get_app_userid(facebook_email, LoginDailogActivity.this);

            } catch (Exception e) {
                pDialog.dismiss();
                // TODO Auto-generated catch block
                e.printStackTrace();
                ShowDailog.Show_Alert_Login(LoginDailogActivity.this, "Server not responding");

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


                    SharedPreferences sp = getSharedPreferences("Pref_name",
                            Activity.MODE_WORLD_READABLE);
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("app_user_id",
                            response);

                    editor.putString("login_flag",
                            "yes");
                    editor.commit();
                    String login_flag_dashboard = sp.getString("login_flag_dashboard", "");
                    if (login_flag_dashboard.equalsIgnoreCase("yes")) {
                      /*  Intent mButtonSignIn = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(mButtonSignIn);*/
                        finish();
                    } else {
                       /* Intent mButtonSignIn = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(mButtonSignIn);*/
                        finish();
                    }

                } else {
                    new facebook_login_save().execute();
                    // Show_Dailog.Show_Alert_Login(Login.this, getResources().getText(R.string.login_error).toString());
                }

            } catch (Exception e) {
                ShowDailog.Show_Alert_Login(LoginDailogActivity.this, "Server not responding");
            }

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub

        {
            super.onActivityResult(requestCode, resultCode, data);
            try {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            } catch (Exception e) {
            }

        }
        if (requestCode == 100) {
        } else if (requestCode == 32665) {

            //  facebook.authorizeCallback(requestCode, resultCode, data);

        } else if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {

            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }

    class facebook_login_save extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(LoginDailogActivity.this);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                if (sp.getString("login_with", "").equalsIgnoreCase("facebook")) {
                    response = Jsondata.getfb_save(regId, "", sp.getString("first_name", ""),
                            sp.getString("last_name", ""), sp.getString("facebook_email", ""), "", "", "", "",
                            LoginDailogActivity.this);
                } else if (sp.getString("login_with", "").equalsIgnoreCase("googleplus")) {
                    response = Jsondata.getfb_save(regId, "", sp.getString("first_name", ""),
                            sp.getString("last_name", ""), sp.getString("gplus_email", ""), "", "", "", "",
                            LoginDailogActivity.this);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                ShowDailog.Show_Alert_Login(LoginDailogActivity.this, "Server not responding");
            }
            return response;
        }


        protected void onPostExecute(String response) {
            pDialog.dismiss();
            try {


                if (response != "" || response != null) {
                    JSONObject jobj = new JSONObject(response);
                    String Success = jobj.getString("success");
                    if (Success.equalsIgnoreCase("true")) {
                        String app_user_id = jobj.getString("app_user_id");
                        SharedPreferences sp = getSharedPreferences("Pref_name",
                                Activity.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("app_user_id",
                                app_user_id);
                        editor.putString("app_user_id",
                                app_user_id);


                        editor.putString("login_flag",
                                "yes");
                        editor.commit();


                        // Intent dashboard = new Intent(getApplicationContext(), MainActivity.class);
                        // startActivity(dashboard);
                        finish();
                    } else {
                        ShowDailog.Show_Alert_Login(LoginDailogActivity.this,
                                getResources().getText(R.string.sign_up_error).toString());
                    }

                } else {
                    ShowDailog.Show_Alert_Login(LoginDailogActivity.this,
                            getResources().getText(R.string.sign_up_error).toString());
                }


            } catch (Exception e) {
                e.printStackTrace();
                ShowDailog.Show_Alert_Login(LoginDailogActivity.this, "Server not responding");
            }
        }
    }

    /*
                     * Method to login with facebook
                     * */
    private void facebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email"));
        callbackManager = CallbackManager.Factory.create();
        // loginButton = (LoginButton) findViewById(R.id.login_button);
        //loginButton.setBackgroundResource(R.mipmap.ic_launcher);

        //List<String> permissionNeeds = Arrays.asList("user_photos", "email", "user_birthday", "public_profile");
        //mButtonFb.setReadPermissions(permissionNeeds);

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
                                //System.out.println("Check: " + response.toString());
                                try {

                                    String id = object.getString("id");
                                    String name = object.getString("name");
                                    String email = object.getString("email");


                                    SharedPreferences sp = getSharedPreferences("Pref_name",
                                            Activity.MODE_WORLD_READABLE);
                                    SharedPreferences.Editor editor = sp.edit();
                                    editor.putString("first_name",
                                            name);
                                    editor.putString("id",
                                            id);
                                    editor.putString("last_name",
                                            "");
                                    editor.putString("facebook_name",
                                            name);

                                    editor.putString("facebook_email",
                                            email);
                                    editor.putString("login_with",
                                            "facebook");

                                    editor.commit();
                                    new HttpGetAsyncTask_app_userid().execute();
                                    //System.out.println(id + ", " + name + ", " + email + ", " + gender + ", " + birthday);
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
                System.out.println("onCancel");
            }


            @Override
            public void onError(FacebookException exception) {
                System.out.println("onError");
                Log.v("LoginActivity", exception.getCause().toString());
            }
        });
    }


    /* @Override
      public void onFacebooklogin(JSONObject profile) {


          try {
              //  {"id":"773239699413399","first_name":"Diwakar","timezone":5.5,"email":"diwakargupta143@gmail
              .com","verified":true,"name":"Diwakar Gupta","locale":"en_US","link":"https:\/\/www.facebook.com\/a
              pp_scoped_user_id\/773239699413399\/","last_name":"Gupta","gender":"male","updated_time":"2015-02-24T05:
              09:09+0000"}



              SharedPreferences sp = getSharedPreferences("Pref_name",
                      Activity.MODE_WORLD_READABLE);
              SharedPreferences.Editor mEditor = sp.edit();
              mEditor.putString("first_name",
                      profile.getString("first_name"));
              mEditor.putString("id",
                      profile.getString("id"));
              mEditor.putString("last_name",
                      profile.getString("last_name"));
              mEditor.putString("facebook_name",
                      profile.getString("name"));

              mEditor.putString("facebook_email",
                      profile.getString("email"));
              mEditor.putString("login_flag",
                      "yes");
              mEditor.putString("login_with",
                      "facebook");

              mEditor.commit();
              new HttpGetAsyncTask_app_userid(profile).execute();


          } catch (Exception e) {
              // TODO Auto-generated catch block
              e.printStackTrace();
          }

      }
  */
      /*
                     * Method to RegisterForPushNotification
                     * */
    public void RegisterForPushNotification() {

        if (mypush.checkPlayServices(this)) {
            GCMNotificationIntentService.setTargetActivity(this);

            mypush.registerInBackground(this, this);
//            "http://flyppapps.com/pushandroid/push/index.php");
        }
    }

    protected void
    onStart() {
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
        //  Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
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


                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("first_name", currentPerson.getDisplayName());

                editor.putString("gplus_image",
                        currentPerson.getImage().getUrl());


                editor.putString("gplus_email",
                        Plus.AccountApi.getAccountName(mGoogleApiClient));
                editor.putString("login_flag",
                        "yes");
                editor.putString("login_with",
                        "googleplus");

                editor.commit();
                new HttpGetAsyncTask_app_userid().execute();

            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
            ShowDailog.Show_Alert_Login(LoginDailogActivity.this, "Server not responding");
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


}
