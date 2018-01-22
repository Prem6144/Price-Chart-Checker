package com.spa;

import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.helpshift.Helpshift;
import com.localytics.android.LocalyticsActivityLifecycleCallbacks;
import com.spa.servicedealz.R;
import com.spa.utils.TypefaceUtil;
import com.spa.utils.Utilities;

import io.branch.referral.Branch;

public class MyApplication extends MultiDexApplication {

    public static final String SHARED_PREF = "sharePrefs";
    public static SharedPreferences sharePreferences;

    private Tracker mTracker;

    /**
     * Gets the default {@link com.google.android.gms.analytics.Tracker} for this {@link android.app.Application}.
     *
     * @return tracker
     */
    synchronized public Tracker getDefaultTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mTracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mTracker;
    }

    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        // ACRA.init(this);
      /*  String customFont = "Pacifico.ttf";
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/" + customFont)
                        .build()
        );*/
       /* TypefaceUtil.overrideFont(getApplicationContext(), "MONOSPACE", "Pacifico.ttf");
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "Pacifico.ttf");
        TypefaceUtil.overrideFont(getApplicationContext(), "SANS_SERIF", "Pacifico.ttf");*/
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/OpenSans-Regular.ttf");
        Branch.getAutoInstance(this);
        Helpshift.install(this, // "this" should be the application object
                "2ba6c60e123d889b3d4b4ef4d294d242",
                "servicedealz.helpshift.com",
                "servicedealz_platform_20160708125152910-116f4322c03b279");
        sharePreferences = getSharedPreferences(SHARED_PREF, MODE_WORLD_READABLE);
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread thread, Throwable ex) {
                Utilities.logExceptionToFile(ex);
                Log.e("Uncaught Exception", "Some exception occured in SamsungVM app", ex);
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });


        // Register LocalyticsActivityLifecycleCallbacks
        registerActivityLifecycleCallbacks(
                new LocalyticsActivityLifecycleCallbacks(this));

    }

    public static SharedPreferences getPreference() {
        return sharePreferences;
    }
}
