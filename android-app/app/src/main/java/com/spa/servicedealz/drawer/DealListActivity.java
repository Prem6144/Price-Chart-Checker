package com.spa.servicedealz.drawer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.linkedin.platform.LISessionManager;
import com.localytics.android.Localytics;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.SlideMenuActivity;
import com.spa.servicedealz.ui.WebViewActivity;
import com.spa.fragment.DealListFragment;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.social.MyTwitter;
import com.spa.utils.Constant;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import co.spa.sidemenu.util.ViewAnimator;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * FileName : DealListActivity
 * Description : Show deal detais
 * Dependencies : DealListFragment
 */
public class DealListActivity extends SlideMenuActivity {
    private DealListFragment mContentFragment;
    TextView txt_compare;


    private ProgressDialog pDialog;

    private static Twitter twitter;
    SharedPreferences.Editor editor;

    public static SharedPreferences mSharedPreferences, sp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTwitterConfigs();
        /* Enabling strict mode */
        sp = getSharedPreferences("Pref_name",
                Activity.MODE_WORLD_READABLE);
        editor = sp.edit();
        editor.putString("sortbyflag", "");
        editor.commit();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_main_dashboard);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        NetworkUtil.setChangeNetWorkListener(DealListActivity.this);
        Constant.compare = "";
        mContentFragment = DealListFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mContentFragment)
                .commit();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
    /* Initialize application preferences */
        mSharedPreferences = getSharedPreferences(Constant.PREF_NAME, 0);
        boolean isLoggedIn = mSharedPreferences.getBoolean(Constant.PREF_KEY_TWITTER_LOGIN, false);
        if (isLoggedIn) {

        } else {
            Uri uri = getIntent().getData();


            if (uri != null && uri.toString().startsWith(Constant.TWITTER_CALLBACK_URL)) {

                String verifier = uri.getQueryParameter(Constant.URL_TWITTER_OAUTH_VERIFIER);

                try {

					/* Getting oAuth authentication token */
                    AccessToken accessToken = twitter.getOAuthAccessToken(Constant.requestToken, verifier);

					/* Getting user id form access token */
                    long userID = accessToken.getUserId();
                    final User user = twitter.showUser(userID);
                    final String username = user.getName();

					/* save updated token */
                    saveTwitterInfo(accessToken);

                } catch (Exception e) {
                }
            }
        }
        setActionBar();

    }

    /**
     * Event Handling for Individual menu item selected
     * Identify single menu item by it's id
     */

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        LISessionManager.getInstance(getApplicationContext()).onActivityResult(this, requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (Constant.facebook) {

            } else {
                String verifier = data.getExtras().getString(Constant.URL_TWITTER_OAUTH_VERIFIER);
                try {
                    AccessToken accessToken = twitter.getOAuthAccessToken(Constant.requestToken, verifier);

                    long userID = accessToken.getUserId();
                    final User user = twitter.showUser(userID);
                    String username = user.getName();

                    saveTwitterInfo(accessToken);


                } catch (Exception e) {
                    Log.e("Twitter Login Failed", e.getMessage());
                }
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public class updateTwitterStatus extends AsyncTask<String, String, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(DealListActivity.this);
            pDialog.setMessage("Posting to twitter...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected Void doInBackground(String... args) {
            String asubstring, status;
            if (Constant.Twitter_Status.length() > 135) {
                status = Constant.Twitter_Status.substring(0, 135);
            } else {
                status = Constant.Twitter_Status;
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

                    InputStream input = new URL(Constant.Twitter_image_url).openStream();
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
            Toast.makeText(DealListActivity.this, "Posted to Twitter!", Toast.LENGTH_SHORT).show();
        }

    }

    public void TwitterShare(String title, String description, String image_url) {
        Constant.Twitter_Status = title;// + "\n" + description;
        Constant.Twitter_image_url = image_url;
        String access_token = mSharedPreferences.getString("oauth_token", "");
        if (access_token.equalsIgnoreCase("") || access_token.equals(null)) {
            loginToTwitter();
        } else {
            new updateTwitterStatus().execute();
        }
    }

    /* Initiating Menu XML file (menu.xml) */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.clear();
        if (!sp.getString("login_flag", "").equalsIgnoreCase("")) {
            menu.add(R.string.sortbyrelevence);
        }

        menu.add(R.string.sortbyprice);
        int cat_id = Integer.parseInt(sp.getString("service_provider", ""));
        switch (cat_id) {
            case 1:
                menu.add(R.string.sortbyspeed);
                break;
            case 2:
                menu.add(R.string.sortbycalling);
                break;
            case 3:
                menu.add(R.string.sortbyfreechannels);
                break;
            case 4:
                menu.add(R.string.sortbycalling);
                break;
            case 5:
                menu.add(R.string.sortbyspeed);
                menu.add(R.string.sortbycalling);
                menu.add(R.string.sortbyfreechannels);
                break;
            case 8:
                break;


        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        DealListFragment.Best_Deal_Flag = false;


        String title = item.getTitle().toString();
        if (title.equals(getResources().getString(R.string.sortbyprice))) {

            editor.putString("sortbyflag",
                    "price");
            editor.commit();

            mContentFragment.new HttpGetAsyncTaskInternet().execute();
        } else if (title.equals(getResources().getString(R.string.sortbyspeed))) {

            editor.putString("sortbyflag",
                    "download_speed");
            editor.commit();

            mContentFragment.new HttpGetAsyncTaskInternet().execute();
        } else if (title.equals(getResources().getString(R.string.sortbyrelevence))) {
            DealListFragment.Best_Deal_Flag = true;

            mContentFragment.new HttpGetAsyncTaskInternet().execute();
        } else if (title.equals(getResources().getString(R.string.sortbycalling))) {

            editor.putString("sortbyflag",
                    "call_minutes");
            editor.commit();

            mContentFragment.new HttpGetAsyncTaskInternet().execute();
        } else if (title.equals(getResources().getString(R.string.sortbyfreechannels))) {

            editor.putString("sortbyflag",
                    "free_channels");
            editor.commit();

            mContentFragment.new HttpGetAsyncTaskInternet().execute();
        }

        return true;


    }

    private void loginToTwitter() {
        boolean isLoggedIn = mSharedPreferences.getBoolean(Constant.PREF_KEY_TWITTER_LOGIN, false);

        if (!isLoggedIn) {
            final ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(Constant.TWITTER_CONSUMER_KEY);
            builder.setOAuthConsumerSecret(Constant.TWITTER_CONSUMER_SECRET);

            final twitter4j.conf.Configuration configuration = builder.build();
            final TwitterFactory factory = new TwitterFactory(configuration);
            twitter = factory.getInstance();

            try {
                Constant.requestToken = twitter.getOAuthRequestToken(Constant.TWITTER_CALLBACK_URL);

                /**
                 *  Loading twitter login page on webview for authorization
                 *  Once authorized, results are received at onActivityResult
                 *  */
                final Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL, Constant.requestToken.getAuthenticationURL());
                startActivityForResult(intent, Constant.WEBVIEW_REQUEST_CODE);

            } catch (TwitterException e) {
                e.printStackTrace();
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
            user = twitter.showUser(userID);

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

    /* Reading twitter essential configuration parameters from strings.xml */
    private void initTwitterConfigs() {
        MyTwitter.initTwitterConfigs(Constant.TWITTER_CONSUMER_KEY,
                Constant.TWITTER_CONSUMER_SECRET,
                Constant.TWITTER_CALLBACK_URL,
                Constant.URL_TWITTER_OAUTH_VERIFIER);

    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        RelativeLayout rl_compare_action = (RelativeLayout) toolbar.findViewById(R.id.rl_compare_action);
        txt_compare = (TextView) toolbar.findViewById(R.id.txt_compare);
        rl_compare_action.setVisibility(View.VISIBLE);
        toolbar.setNavigationIcon(R.drawable.menuicon);
        setSupportActionBar(toolbar);
        txt_compare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.compare = "yes";
                mContentFragment.setadapter(DealListActivity.this);

            }
        });
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        TextView txt_title = (TextView) toolbar.findViewById(R.id.txt_title);

        txt_title.setText(Html.fromHtml("<font color=\"#FFFFFF\">" + sp.getString("actionbar_title", "Internet Deals")));
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + sp.getString("actionbar_title", "Internet Deals") + "</font>")));
        setDrawer(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("DealListActivity");
        viewAnimator = new ViewAnimator<>(this, mSlideMenuList, mContentFragment, mDrawerLayout, this);
    }
}
