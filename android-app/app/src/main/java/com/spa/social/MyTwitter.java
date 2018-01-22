package com.spa.social;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.StrictMode;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

@SuppressLint("NewApi")
public class MyTwitter {

    /* Shared preference keys */
    private static final String PREF_NAME = "sample_twitter_pref";
    private static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    private static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    private static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
    public static final String PREF_USER_NAME = "twitter_user_name";

    /* Any number for uniquely distinguish your request */
    public static final int WEBVIEW_REQUEST_CODE = 100;

    private ProgressDialog pDialog;

    public static Twitter twitter;
    public static RequestToken requestToken;

    public static String consumerKey = null;
    public static String consumerSecret = null;
    public static String callbackUrl = null;
    public static String oAuthVerifier = null;

    private Context ctx;
    private Activity act;
    private SharedPreferences mSharedPreferences;
    private TwitterLogin twitterLogin;

    public MyTwitter() {
        // TODO Auto-generated constructor stub
    }

    public MyTwitter(Context ctx, Activity act,
                     SharedPreferences mSharedPreferences) {
        // TODO Auto-generated constructor stub
        this.act = act;
        this.ctx = ctx;
        this.mSharedPreferences = mSharedPreferences;
    }

    /* Reading twitter essential configuration parameters from strings.xml */
    public static void initTwitterConfigs(String consumer_Key,
                                          String consumer_Secret, String callback_Url, String oAuth_Verifier) {
        consumerKey = consumer_Key;
        consumerSecret = consumer_Secret;
        callbackUrl = callback_Url;
        oAuthVerifier = oAuth_Verifier;
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NewApi")
    public void loginToTwitter() {

		/* initializing twitter parameters from string.xml */
        initTwitterConfigs(consumerKey, consumerSecret, callbackUrl,
                oAuthVerifier);

		/* Enabling strict mode */
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);

		/* Setting activity layout file */

        boolean isLoggedIn = mSharedPreferences.getBoolean(
                PREF_KEY_TWITTER_LOGIN, false);

        if (!isLoggedIn) {
            final ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(consumerKey);
            builder.setOAuthConsumerSecret(consumerSecret);

            final Configuration configuration = builder.build();
            final TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();

            try {
                requestToken = twitter.getOAuthRequestToken(callbackUrl);

                /**
                 * Loading twitter login page on webview for authorization Once
                 * authorized, results are received at onActivityResult
                 * */
                final Intent intent = new Intent(ctx,
                        TwitterWebViewActivity.class);
                intent.putExtra(TwitterWebViewActivity.EXTRA_URL,
                        requestToken.getAuthenticationURL());
                act.startActivityForResult(intent, WEBVIEW_REQUEST_CODE);

            } catch (TwitterException e) {
                e.printStackTrace();
            }
        } else {
            twitterLogin.onAlreadyLogin();
            // loginLayout.setVisibility(View.GONE);
            // shareLayout.setVisibility(View.VISIBLE);
        }
    }


    public interface TwitterLogin {
        public void onAlreadyLogin();
    }

}