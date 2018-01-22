package com.spa.servicedealz.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.helpshift.Helpshift;
import com.spa.MyApplication;
import com.spa.fragment.AuctionMainFragment;
import com.spa.fragment.MainFragment;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.CalculatorActivity;
import com.spa.servicedealz.drawer.DashboardActivity;
import com.spa.servicedealz.drawer.GiftCardActivity;
import com.spa.servicedealz.drawer.GuestDashbaordActiviy;
import com.spa.servicedealz.drawer.MyOrderActivity;
import com.spa.servicedealz.drawer.ProfileActivity;
import com.spa.servicedealz.drawer.ReferActivity;
import com.spa.servicedealz.drawer.SellerActivity;
import com.spa.utils.Constant;
import com.spa.utils.MenuItemGlobal;

import java.util.ArrayList;
import java.util.List;

import co.spa.sidemenu.interfaces.Resourceble;
import co.spa.sidemenu.interfaces.ScreenShotable;
import co.spa.sidemenu.model.SlideMenuItem;
import co.spa.sidemenu.util.ViewAnimator;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;

/**
 * FileName : SlideMenuActivity
 * Description :For open SlideMenu
 * Dependencies : No Dependencies
 */
public class SlideMenuActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener, NetworkUtil.changeNetworkInterFace {

    private String TAG = "SlideMenuActivity";

    protected SlideMenuActivity mActivity;
    private String mActivityName;

    protected GoogleApiClient mGoogleApiClient;
    protected DrawerLayout mDrawerLayout;
    protected ActionBarDrawerToggle drawerToggle;
    protected List<SlideMenuItem> mSlideMenuList = new ArrayList<>();
    protected ViewAnimator<SlideMenuItem> viewAnimator;
    protected LinearLayout linearLayout;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        mSharedPreferences = getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        mEditor = mSharedPreferences.edit();
        mActivity = this;
        mActivityName = ((Object) this).getClass().getName();
        mActivityName = mActivityName.substring(mActivityName.lastIndexOf(".") + 1);
        TAG = mActivityName;

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        NetworkUtil.setChangeNetWorkListener(this);
    }

    public void setDrawer(Toolbar toolbar) {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        //  mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.closeDrawers();
            }
        });
        mSlideMenuList = MenuItemGlobal.createMenuList(this);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(drawerView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        };
        mDrawerLayout.setDrawerListener(drawerToggle);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        MainFragment contentFragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;

    }

    private String logout() {
        try {
            AccessToken accessToken = AccessToken.getCurrentAccessToken();
            if (accessToken != null) {
                LoginManager.getInstance().logOut();
            }
        } catch (Exception e) {
        }
        return null;

    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        SharedPreferences sp = getSharedPreferences("Pref_name",
                Activity.MODE_WORLD_READABLE);
        SharedPreferences.Editor editor = sp.edit();
        String zipcode_flag = sp.getString("zipcode_flag", "");
        String service_flag = sp.getString("service_flag", "");
        String login_flag = sp.getString("login_flag", "");
        switch (slideMenuItem.getName()) {
            case MenuItemGlobal.CLOSE:
                return screenShotable;
            case MenuItemGlobal.SIGNUP:
                Intent SIGNUP = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(SIGNUP);
                finish();
                return screenShotable;
            case MenuItemGlobal.HELP:
                Intent HELP = new Intent(getApplicationContext(), PageIndicatorActivity.class);
                HELP.putExtra("Introfinish", true);
                startActivity(HELP);
                return screenShotable;
            case MenuItemGlobal.AUCTION:
                AuctionMainFragment AuctioncontentFragment = AuctionMainFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, AuctioncontentFragment)
                        .commit();
                return AuctioncontentFragment;
            case MenuItemGlobal.GIFTCARD:
                if (!login_flag.equalsIgnoreCase("")) {
                    Intent intent = new Intent(getApplicationContext(), GiftCardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    ShowDailog.Show_Alert_Login(this, "To access this feature please Login first.", "Alert");
                }


                return screenShotable;
            case MenuItemGlobal.MY_ORDER:
                if (!login_flag.equalsIgnoreCase("")) {
                    Intent myorder = new Intent(getApplicationContext(), MyOrderActivity.class);
                    myorder.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(myorder);
                    finish();
                } else {
                    ShowDailog.Show_Alert_Login(this, "To access this feature please Login first.", "Alert");
                }

                return screenShotable;
            case MenuItemGlobal.FEEDBACK:
                Helpshift.showConversation(mActivity);
                return screenShotable;
            case MenuItemGlobal.SELLER:

                Intent seller = new Intent(getApplicationContext(), SellerActivity.class);
                seller.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(seller);
                return screenShotable;
            case MenuItemGlobal.CALCULATOR:

                Intent calculator = new Intent(getApplicationContext(), CalculatorActivity.class);
                calculator.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(calculator);
                return screenShotable;
            case MenuItemGlobal.LOGOUT:
                logout();

                editor.putString("access_token",
                        "");
                editor.putString(Constant.USER_PREFRENCE,
                        "");
                editor.putString("login_flag", "");
                editor.commit();

                if (mGoogleApiClient.isConnected()) {
                    Plus.AccountApi.clearDefaultAccount(mGoogleApiClient);
                    mGoogleApiClient.disconnect();
                    mGoogleApiClient.connect();
                }
                Intent login = new Intent(getApplicationContext(), LoginActivity.class);
                login.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(login);
                finish();

                return screenShotable;
            case MenuItemGlobal.INVITE:
                if (!login_flag.equalsIgnoreCase("")) {
                    Intent refer = new Intent(getApplicationContext(), ReferActivity.class);
                    refer.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(refer);
                    finish();
                } else {
                    ShowDailog.Show_Alert_Login(this, "To access this feature please Login first.", "Alert");
                }


                return screenShotable;
            case MenuItemGlobal.HOME:
                if (!login_flag.equalsIgnoreCase("")) {
                    if (zipcode_flag.equalsIgnoreCase("") || zipcode_flag.equals(null)) {
                        ShowDailog.Show_Alert_(this, "Please complete the profile", "Warning");
                    } /*else if (service_flag.equalsIgnoreCase("") || service_flag.equals(null)) {
                        editor.putString("login_flag_dashboard", "");
                        ShowDailog.Show_Alert_(this, "Please select at least one service", "Warning");
                    } */ else {
                        editor.putString("login_flag_dashboard", "yes");
                        editor.commit();
                        Intent HOME = new Intent(getApplicationContext(), DashboardActivity.class);
                        HOME.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(HOME);
                        finish();
                    }
                } else {
                    Intent HOME = new Intent(getApplicationContext(), GuestDashbaordActiviy.class);
                    HOME.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(HOME);
                    finish();
                }
                return screenShotable;
            case MenuItemGlobal.CHART:
                if (!login_flag.equalsIgnoreCase("")) {
                    if (zipcode_flag.equalsIgnoreCase("") || zipcode_flag.equals(null)) {
                        ShowDailog.Show_Alert_(this, "Please complete the profile", "Warning");
                    } /*else if (service_flag.equalsIgnoreCase("") || service_flag.equals(null)) {
                        editor.putString("login_flag_dashboard", "");
                        ShowDailog.Show_Alert_(this, "Please select at least one service", "Warning");
                    }*/ else {
                        Intent HOME = new Intent(getApplicationContext(), ChartActivity.class);
                        startActivity(HOME);
                    }
                } else {
                    ShowDailog.Show_Alert_Login(this, "To access this feature please Login first.", "Alert");
                }
                return screenShotable;
            case MenuItemGlobal.GENERAL_INFO:
                if (!login_flag.equalsIgnoreCase("")) {
                    editor.putInt("page_position", 0);
                    editor.commit();
                    Intent GENERAL_INFO = new Intent(getApplicationContext(), ProfileActivity.class);
                    mEditor.putBoolean("fullProfile", true);
                    mEditor.commit();
                    GENERAL_INFO.putExtra("page_position", 0);
                    GENERAL_INFO.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(GENERAL_INFO);
                    finish();
                } else {
                    ShowDailog.Show_Alert_Login(this, "To access this feature please Login first.", "Alert");
                }
                return screenShotable;
            case MenuItemGlobal.PREFERENCES:
                if (!login_flag.equalsIgnoreCase("")) {
                    Intent PREFERENCES = new Intent(getApplicationContext(), com.spa.servicedealz.drawer.PrefrenceActivity.class);
                    PREFERENCES.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(PREFERENCES);
                    finish();
                } else {
                    ShowDailog.Show_Alert_Login(this, "To access this feature please Login first.", "Alert");
                }
                return screenShotable;
           /* case MenuItemGlobal.SERVICES:
                if (!login_flag.equalsIgnoreCase("")) {
                    editor.putInt("page_position", 1);
                    editor.commit();
                    Intent SERVICES = new Intent(getApplicationContext(), ProfileActivity.class);
                    startActivity(SERVICES);
                    finish();
                } else {
                    ShowDailog.Show_Alert_Login(this, "To access this feature please Login first.", "Alert");
                }
                return screenShotable;*/

            default:
                return replaceFragment(screenShotable, position);
        }
    }


    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        mDrawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
        MyApplication application = (MyApplication) getApplication();
        Tracker mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Image~" + mActivityName);
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void updateNetwork(String s) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }
}
