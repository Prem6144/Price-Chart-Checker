package com.spa.servicedealz.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.spa.servicedealz.R;
import com.spa.servicedealz.services.InternetServiceActivity;
import com.spa.utils.Constant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Diwakar on 6/30/2016.
 */
public class WelcomeScreenActivity extends Activity implements View.OnClickListener {
    private ImageView mImageViewClose;
    private int i = 0;
    private SharedPreferences mSharedPreferences;
    private WebView mWebViewWelcome;
    private TextView mTextView, mTextViewWelcome, mTextViewto;
    private Button mButtonSave;
    private SharedPreferences.Editor mEditor;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
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
        mImageViewClose = (ImageView) findViewById(R.id.img_close);
        mTextViewWelcome = (TextView) findViewById(R.id.wv_welcome);
        mTextView = (TextView) findViewById(R.id.txt_welcome);
        mTextViewto = (TextView) findViewById(R.id.wv_welcome1);
        mButtonSave = (Button) findViewById(R.id.btn_save);
        mButtonSave.setOnClickListener(this);
        //  mWebViewWelcome.getSettings().setJavaScriptEnabled(true);
        String fontPath = "fonts/arbekely.ttf";
        Typeface tf2 = Typeface.createFromAsset(getAssets(), fontPath);
        // Applying font
        mTextView.setTypeface(tf2);

        //  mTextViewto.setTypeface(tf2);
        mTextViewto.setText(Html.fromHtml("" + "<b><font color=\"#3f3f3f\">" + "" +
                "It is easy as 1 - 2 - 3" + "</font></b>"));
        mTextViewWelcome.setText(Html.fromHtml("" + "<b><font color=\"#2b9d11\">" + " $ave "
                + "</font></b>" + "<b><font color=\"#3f3f3f\">" + " $1000s by switching " + "</font></b>" + "<b><font color=\"#e9262a\">" + " &nbsp; Internet,TV,   Telephone, Cellphone" + "</font></b>"));
        //  mWebViewWelcome.loadUrl("file:///android_asset/welcome.html");
        mImageViewClose.setOnClickListener(this);
    }

    public String Show_Alert_Login(final Activity activity, String message, String title) {
        try {


            AlertDialog.Builder alertbox = new AlertDialog.Builder(
                    activity, R.style.DialogAnimation);
            //   } else alertbox = new AlertDialog.Builder(
            //        activity);

            alertbox.setTitle(title);
            alertbox.setMessage(message + "                            ");
            alertbox.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {

                            Intent PREFERENCES = new Intent(activity, LoginActivity.class);

                            PREFERENCES.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(PREFERENCES);
                            activity.finish();
                        }
                    });
            alertbox.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            finish();
                        }
                    });

            alertbox.setCancelable(false);
            final AlertDialog alertD = alertbox.create();
            alertD.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    int titleDividerId = activity.getResources()
                            .getIdentifier("titleDivider", "id", "android");

                    View titleDivider = alertD.findViewById(titleDividerId);
                    if (titleDivider != null) {
                        titleDivider.setBackgroundColor(activity.getResources()
                                .getColor(R.color.devider));
                    }
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            } else {
                alertD.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
            alertD.show();
        } catch (Exception e) {
        }
        return null;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            mEditor.putString("signupwithfirsttime", "");
            mEditor.commit();

            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /*autogenrated method Click button*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                if (getIntent().getBooleanExtra("guestwelcome", false)) {
                    Show_Alert_Login(this, "To access this feature please Login first.", "Alert");
                } else {
                    Intent browserIntent = new Intent(getApplicationContext(), InternetServiceActivity.class);
                    startActivity(browserIntent);
                    finish();
                }

                break;
            case R.id.img_close:
                // i = 2;
                mEditor.putString("signupwithfirsttime", "");
                mEditor.commit();
                finish();
                break;
        }
    }

    private String readTxt() {
        InputStream inputStream = getResources().openRawResource(R.raw.welcome);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i;
        try {
            i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return byteArrayOutputStream.toString();
    }
}

