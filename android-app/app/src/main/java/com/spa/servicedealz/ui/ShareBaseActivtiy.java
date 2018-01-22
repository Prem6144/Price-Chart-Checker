package com.spa.servicedealz.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.spa.servicedealz.R;
import com.spa.fragment.DealListFragment;
import com.spa.social.MyTwitter;
import com.spa.utils.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by Diwakar on 4/13/2016.
 */
public class ShareBaseActivtiy extends AppCompatActivity {
    private Intent in;
    public static String Twitter_Status = "", Twitter_image_url;
    public static final int WEBVIEW_REQUEST_CODE = 100;
    public static SharedPreferences mSharedPreferences;
    private static Twitter mTwitter;
    private static RequestToken requestToken;
    private ProgressDialog pDialog;
    private CallbackManager mCallbackManager;
    public static AlertDialog.Builder alertbox = null;

    @Override
    protected void onCreate(android.os.Bundle bundle) {
        super.onCreate(bundle);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        mSharedPreferences = getSharedPreferences(Constant.SHARED_PREF, 0);
        in = getIntent();
        setTwitterLogin();
    }

    /*Method check twitter login or not and twitter authenticate*/
    private void setTwitterLogin() {
        boolean isLoggedIn = mSharedPreferences.getBoolean(Constant.PREF_KEY_TWITTER_LOGIN, false);
        if (isLoggedIn) {
        } else {
            Uri uri = getIntent().getData();
            if (uri != null && uri.toString().startsWith(Constant.TWITTER_CALLBACK_URL)) {

                String verifier = uri.getQueryParameter(Constant.URL_TWITTER_OAUTH_VERIFIER);

                try {

					/* Getting oAuth authentication token */
                    AccessToken accessToken = mTwitter.getOAuthAccessToken(requestToken, verifier);

					/* Getting user id form access token */
                    long userID = accessToken.getUserId();
                    final User user = mTwitter.showUser(userID);
                    final String username = user.getName();

					/* save updated token */
                    saveTwitterInfo(accessToken);

                } catch (Exception e) {
                }
            }
        }
    }

    /**
     * Saving user information, after user is authenticated for the first time.
     * You don't need to show user to login, until user has a valid access toen
     */
    private void saveTwitterInfo(AccessToken accessToken) {

        long userID = accessToken.getUserId();

        User user;
        try {
            user = mTwitter.showUser(userID);

            String username = user.getName();

			/* Storing oAuth tokens to shared preferences */
            SharedPreferences.Editor e = mSharedPreferences.edit();
            e.putString(Constant.PREF_KEY_OAUTH_TOKEN, accessToken.getToken());
            e.putString(Constant.PREF_KEY_OAUTH_SECRET, accessToken.getTokenSecret());
            e.putBoolean(Constant.PREF_KEY_TWITTER_LOGIN, true);
            e.putString(Constant.PREF_USER_NAME, username);
            e.commit();
            new updateTwitterStatus().execute();
        } catch (TwitterException e1) {
            e1.printStackTrace();
        }
    }

    public void TwitterShare(String title, String description, String image_url) {
        Twitter_Status = title;// + "\n" + description;
        Twitter_image_url = image_url;
        String access_token = mSharedPreferences.getString(Constant.PREF_KEY_OAUTH_TOKEN, "");
        if (access_token.equalsIgnoreCase("") || access_token.equals(null)) {
            loginToTwitter();
        } else {
            new updateTwitterStatus().execute();
        }
    }

    /*Share twitter dashboard in background*/
    public class updateTwitterStatus extends AsyncTask<String, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(ShareBaseActivtiy.this);
            pDialog.setMessage("Posting to mTwitter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Void doInBackground(String... args) {
            String asubstring, status;
            if (Twitter_Status.length() > 135) {
                status = Twitter_Status.substring(0, 135);
            } else {
                status = Twitter_Status;
            }

            //args[0];
            try {
                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(Constant.TWITTER_CONSUMER_KEY);
                builder.setOAuthConsumerSecret(Constant.TWITTER_CONSUMER_SECRET);

                // Access Token
                String access_token = mSharedPreferences.getString(Constant.PREF_KEY_OAUTH_TOKEN, "");
                // Access Token Secret
                String access_token_secret = mSharedPreferences.getString(Constant.PREF_KEY_OAUTH_SECRET, "");

                AccessToken accessToken = new AccessToken(access_token, access_token_secret);
                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

                // Update status
                StatusUpdate statusUpdate = new StatusUpdate(status);
                try {

                    InputStream input = new URL(Twitter_image_url).openStream();
                    // InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                    statusUpdate.setMedia("image.jpg", input);

                    twitter4j.Status response = twitter.updateStatus(statusUpdate);
                    String string = response.getText();
                    Log.d("Twitter_Status", string);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (TwitterException e) {
                Log.d("Failed to post!", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

			/* Dismiss the progress dialog after sharing */
            pDialog.dismiss();
            Toast.makeText(ShareBaseActivtiy.this, "Posted to Twitter!", Toast.LENGTH_SHORT).show();
        }

    }

    /* Reading mTwitter essential configuration parameters from strings.xml */
    private void initTwitterConfigs() {
        MyTwitter.initTwitterConfigs(Constant.TWITTER_CONSUMER_KEY,
                Constant.TWITTER_CONSUMER_SECRET,
                Constant.TWITTER_CALLBACK_URL,
                Constant.URL_TWITTER_OAUTH_VERIFIER);

    }

    /*Method share data in whatsup application*/
    public void Whatsup_Share(int pos) {
        boolean installed = appInstalledOrNot("com.whatsapp");
        if (installed) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, DealListFragment.dealListItem.getDeal().get(pos).getTitle().toString() + "  $" + DealListFragment.dealListItem.getDeal().get(pos).getDeal_price().toString() + "/month" + "\n" + DealListFragment.dealListItem.getDeal().get(pos).getShort_description().toString() + "\n" + DealListFragment.dealListItem.getDeal().get(pos).getDetail_description().toString() + "\n" + "https://play.google.com/apps/testing/com.spa.servicedeal");
            try {
                startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getApplicationContext(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }
    }

    /*method check to app install or not in the device */
    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    /*Method share data in linkedIn application*/
    public void linkedIn_share(final int pos) {
        LISessionManager sessionManager = LISessionManager.getInstance(getApplicationContext());
        LISession session = sessionManager.getSession();
        boolean accessTokenValid = session.isValid();
        if (accessTokenValid) {
            String url = "https://api.linkedin.com/v1/people/~/shares";

            String share = DealListFragment.dealListItem.getDeal().get(pos).getTitle().toString() + "  $" + DealListFragment.dealListItem.getDeal().get(pos).getDeal_price().toString() + "/month" + "\n" + DealListFragment.dealListItem.getDeal().get(pos).getShort_description().toString() + "\n" + DealListFragment.dealListItem.getDeal().get(pos).getDetail_description().toString() + "\n" + DealListFragment.dealListItem.getDeal().get(pos).getShare_url().toString();// + "\n" + data.get(pos).get("url").toString();
            String payload = "{" +
                    "\"comment\":\"" + share + "\n" + "https://play.google.com/apps/testing/com.spa.servicedeal" +
                    "\"," +
                    "\"visibility\":{" +
                    "    \"code\":\"anyone\"}" +
                    "}";

            APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
            APIHelper.postRequest(getApplicationContext(), url, payload, new ApiListener() {
                @Override
                public void onApiSuccess(ApiResponse apiResponse) {
                    // Success!
                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onApiError(LIApiError liApiError) {
                    // Error making POST request!
                    // Toast.makeText(getApplicationContext(), "" + liApiError, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // linkedInLogin();
            LISessionManager.getInstance(getApplicationContext()).init(ShareBaseActivtiy.this, buildScope(), new AuthListener() {
                @Override
                public void onAuthSuccess() {

                    LISessionManager sessionManager = LISessionManager.getInstance(getApplicationContext());
                    LISession session = sessionManager.getSession();
                    boolean accessTokenValid = session.isValid();
                    String url = "https://api.linkedin.com/v1/people/~/shares";
                    String share = DealListFragment.dealListItem.getDeal().get(pos).getTitle().toString() + "  $" + DealListFragment.dealListItem.getDeal().get(pos).getDeal_price().toString() + "/month" + "\n" + DealListFragment.dealListItem.getDeal().get(pos).getShort_description().toString() + "\n" + DealListFragment.dealListItem.getDeal().get(pos).getDetail_description().toString();//+ "\n" + data.get(pos).get("url").toString();
                    String payload = "{" +
                            "\"comment\":\"" + share + "\n" + "https://play.google.com/apps/testing/com.spa.servicedeal" +
                            "\"," +
                            "\"visibility\":{" +
                            "    \"code\":\"anyone\"}" +
                            "}";

                    APIHelper apiHelper = APIHelper.getInstance(getApplicationContext());
                    APIHelper.postRequest(getApplicationContext(), url, payload, new ApiListener() {
                        @Override
                        public void onApiSuccess(ApiResponse apiResponse) {
                            // Success!
                            Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onApiError(LIApiError liApiError) {
                            // Error making POST request!
                            //Toast.makeText(context, "" + liApiError, Toast.LENGTH_SHORT).show();
                        }
                    });
                    //setUpdateState();
                    //   Toast.makeText(context, "success" + LISessionManager.getInstance(context).getSession().getAccessToken().toString(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onAuthError(LIAuthError error) {
                    // setUpdateState();
                    //  ((TextView) findViewById(R.id.at)).setText(error.toString());
                    //  Toast.makeText(getApplicationContext(), "failed " + error.toString(), Toast.LENGTH_LONG).show();
                }
            }, true);
        }
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }

    /*Method share data in facbook application*/
    public void FaceBookShare(int pos) {
        String url = "http://www.sp-assurance.com/";//Internet_deal_fragment.DEAL_LIST.get(pos).get("url").toString();
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!FacebookSdk.isInitialized()) {
            FacebookSdk.sdkInitialize(ShareBaseActivtiy.this);
        }

        com.facebook.AccessToken accessToken = com.facebook.AccessToken.getCurrentAccessToken();

        if (null == accessToken) {
            //Toast.makeText(this, "need to login first...", Toast.LENGTH_LONG).show();
        }
        mCallbackManager = CallbackManager.Factory.create();
        ShareDialog shareDialog = new ShareDialog(ShareBaseActivtiy.this);
        // this part is optional
        shareDialog.registerCallback(mCallbackManager, new FacebookCallback<Sharer.Result>() {

            @Override
            public void onSuccess(Sharer.Result result) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(FacebookException error) {
                // TODO Auto-generated method stub

            }


        });
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(DealListFragment.dealListItem.getDeal().get(pos).getTitle().toString() + "  $" + DealListFragment.dealListItem.getDeal().get(pos).getDeal_price().toString() + "/month")
                    .setContentDescription(DealListFragment.dealListItem.getDeal().get(pos).getShort_description().toString())
                    .setContentUrl(
                            Uri.parse("https://play.google.com/apps/testing/com.spa.servicedeal"))
                    .setImageUrl(Uri.parse(DealListFragment.dealListItem.getDeal().get(pos).getShare_url().toString()))
                    .build();

            ShareDialog.show(ShareBaseActivtiy.this, linkContent);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (Constant.facebook) {
            } else {
                String verifier = data.getExtras().getString(Constant.URL_TWITTER_OAUTH_VERIFIER);
                try {
                    AccessToken accessToken = mTwitter.getOAuthAccessToken(requestToken, verifier);

                    long userID = accessToken.getUserId();
                    final User user = mTwitter.showUser(userID);
                    String username = user.getName();

                    saveTwitterInfo(accessToken);


                } catch (Exception e) {
                    Log.e("Twitter Login Failed", e.getMessage());

                }
            }
        }


    }

    /*Method show Dailog to Rating and comment */
    public void displayPopupWindow(View anchorView, final int pos) {


        ImageView fb_share, whats_app_share, linkldin_share, twitter_share, img_close;
       /* PopupWindow popup = new PopupWindow(ShareBaseActivtiy.this);
        View layout = getLayoutInflater().inflate(R.layout.popup_content, null);
        popup.setContentView(layout);
        // Set content width and height
        popup.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        popup.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        // Closes the popup window when touch outside of it - when looses focus
        popup.setOutsideTouchable(true);
        popup.setFocusable(true);
        int screen_pos[] = new int[2];
// Get location of anchor view on screen
        anchorView.getLocationOnScreen(screen_pos);

// Get rect for anchor view
        Rect anchor_rect = new Rect(screen_pos[0], screen_pos[1], screen_pos[0]
                + anchorView.getWidth(), screen_pos[1] + anchorView.getHeight());

// Call view measure to calculate how big your view should be.
        layout.measure(ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.WRAP_CONTENT);

        int contentViewHeight = layout.getMeasuredHeight();
        int contentViewWidth = layout.getMeasuredWidth();
// In this case , i dont need much calculation for x and y position of
// tooltip
// For cases if anchor is near screen border, you need to take care of
// direction as well
// to show left, right, above or below of anchor view
        int position_x = anchor_rect.centerX() - (contentViewWidth / 2);
        int position_y = anchor_rect.bottom - (anchor_rect.height() / 2);
        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.showAtLocation(anchorView, Gravity.NO_GRAVITY, position_x,
                position_y);*/
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        View promptView = layoutInflater.inflate(R.layout.popup_content, null);
        alertbox = new AlertDialog.Builder(this, R.style.DialogAnimation);
        twitter_share = (ImageView) promptView.findViewById(R.id.twitter_share);
        fb_share = (ImageView) promptView.findViewById(R.id.fb_share);
        linkldin_share = (ImageView) promptView.findViewById(R.id.linkldin_share);
        whats_app_share = (ImageView) promptView.findViewById(R.id.whats_app_share);
        img_close = (ImageView) promptView.findViewById(R.id.img_close);
        // set prompts.xml to be the layout file of the alertdialog builder
        alertbox.setView(promptView);

        final AlertDialog alertD = alertbox.create();
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();

            }
        });

        twitter_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.facebook = false;
                TwitterShare(DealListFragment.dealListItem.getDeal().get(pos).getTitle().toString() + "  $" + DealListFragment.dealListItem.getDeal().get(pos).getDeal_price().toString() + "/month", "", DealListFragment.dealListItem.getDeal().get(pos).getShare_url().toString());
                alertD.dismiss();
            }
        });
        whats_app_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Whatsup_Share(pos);
                alertD.dismiss();
            }
        });
        linkldin_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkedIn_share(pos);
                alertD.dismiss();
            }
        });
        fb_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.facebook = true;
                FaceBookShare(pos);
                alertD.dismiss();
            }
        });
        // popup.showAsDropDown(anchorView);
        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertD.show();
    }

    private void loginToTwitter() {
        boolean isLoggedIn = mSharedPreferences.getBoolean(Constant.PREF_KEY_TWITTER_LOGIN, false);

        if (!isLoggedIn) {
            final ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(Constant.TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(Constant.TWITTER_CONSUMER_SECRET);

            final twitter4j.conf.Configuration configuration = builder.build();
            final TwitterFactory factory = new TwitterFactory(configuration);
            mTwitter = factory.getInstance();

            try {
                requestToken = mTwitter.getOAuthRequestToken(Constant.TWITTER_CALLBACK_URL);

                /**
                 *  Loading mTwitter login page on webview for authorization
                 *  Once authorized, results are received at onActivityResult
                 *  */
                final Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL, requestToken.getAuthenticationURL());
                startActivityForResult(intent, WEBVIEW_REQUEST_CODE);

            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {

        }
    }
}
