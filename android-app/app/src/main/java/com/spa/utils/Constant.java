package com.spa.utils;

import twitter4j.auth.RequestToken;

public class Constant {

    // Constants
    public static String TRUE = "true";
    public static String FALSE = "false";
    public static String CONNECTION_ERROR = "Please connect to the internet";
    public static String WARNING = "Warning";
    public static String SELECT_OPTIONS = "Select Option";
    public static String SHARED_PREF = "Pref_name";
    public static String LOGIN_ACCOUNT_FLAG = "login_with";
    public static String GOOGLE_PLUS = "googleplus";
    public static String FACEBOOK = "facebook";
    public static String SIGNIN = "sign_in";
    public static String SIGNUP = "sign_up";
    public static String SIGNUP_EMAIL = "sign_up_email";
    public static String USER_EMAIL = "etx_email";
    public static String FACEBOOK_EMAIL = "facebook_email";
    public static String FIRST_NAME = "first_name";
    public static String LAST_NAME = "last_name";
    public static String FACEBOOK_ID = "id";
    public static String GOOGLE_PLUS_EMAIL = "gplus_email";
    public static String GOOGLE_PLUS_IMAGE = "gplus_image";
    public static String APP_USER_ID = "app_user_id";
    public static String ZIPCODE = "enter_zipcode";
    public static String ZIPCODE_FLAG = "zipcode_flag";
    public static String SERVICE_FLAG = "service_flag";
    public static String YES_FLAG = "yes";
    //=========================================SignUp===============================================
    public static String WAIT = "Wait...";
    public static String LOGIN_FLAG = "login_flag";
    public static String VIEW_PAGER_POSITION = "page_position";
    //=========================================Login===============================================
    public static String USER_PASSWORD = "etx_pass";
    public static String USER_CREDETIALS_REMEMBER = "checked";
    public static String USER_PREFRENCE = "user_preference";
    public static String USER_DASHBOARD_FLAG = "login_flag_dashboard";
    public static String ADVERTISE_IMAGE = "service_provider_image";
    public static String ADVERTISE_URL = "service_provider_url";
    /**
     * Register your here app https://dev.twitter.com/apps/new and get your
     * consumer key and secret
     */
    public static String TWITTER_CONSUMER_KEY = "g2DkLkG8PpxYpO6RTfuuiml0l";
    public static String TWITTER_CONSUMER_SECRET = "SapX4UwQ93cFnjRqWea9HHc6IoJCS01VY6nJrBm466oGSaUuEd";
    public static final String TWITTER_CALLBACK_URL = "http://www.servicedealz.com";
    public static String OAUTH_CALLBACK_SCHEME = "x-oauthflow-linkedin";
    public static String OAUTH_CALLBACK_HOST = "callback";
    public static String OAUTH_CALLBACK_URL = OAUTH_CALLBACK_SCHEME + "://"
            + OAUTH_CALLBACK_HOST;
    // Twitter oauth urls
    public static final String URL_TWITTER_AUTH = "auth_url";
    public static final String URL_TWITTER_OAUTH_VERIFIER = "oauth_verifier";
    public static final String URL_TWITTER_OAUTH_TOKEN = "oauth_token";
    public static String LINKEDIN_CONSUMER_KEY = "7841vj3e60g8io";
    public static String LINKEDIN_CONSUMER_SECRET = "AvxW21DL91SziExA";
    // Facebook
    public static final String FaceBook_ApplicationId = "1553828824888000";
    public static String PROJECT_ID = "447266615651";

    //==============================================DeatiListActivity=====================

    public static boolean facebook = false;
    public static String compare = "", Twitter_Status = "", Twitter_image_url;
    /* Shared preference keys */
    public static final String PREF_NAME = "sample_twitter_pref";
    public static final String PREF_KEY_OAUTH_TOKEN = "oauth_token";
    public static final String PREF_KEY_OAUTH_SECRET = "oauth_token_secret";
    public static final String PREF_KEY_TWITTER_LOGIN = "is_twitter_loggedin";
    public static final String PREF_USER_NAME = "twitter_user_name";
    public static RequestToken requestToken;
    /* Any number for uniquely distinguish your request */
    public static final int WEBVIEW_REQUEST_CODE = 100;
    /* Share url*/
    public static final String INTERNET_SHARE_URL = "http://www.spad-metrics.com/qa/internet_share.png";
    public static final String CELLPHONE_SHARE_URL = "http://www.spad-metrics.com/qa/mobile_share.png";
    public static final String TELEPHONE_SHARE_URL = "http://www.spad-metrics.com/qa/telephone_share.png";
    public static final String BUNDLE_SHARE_URL = "http://www.spad-metrics.com/qa/bundle_share.png";
    public static final String CABLE_SHARE_URL = "http://www.spad-metrics.com/qa/cable_share.png";
    //==================================================
}
