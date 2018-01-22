package com.spa.servicedealz.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.localytics.android.Localytics;
import com.spa.servicedealz.R;
import com.spa.utils.Constant;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Diwakar on 5/31/2016.
 */
public class CashoutType extends AppCompatActivity implements View.OnClickListener {
    private Button mButtonCashAmount, mButtonGetGiftCard, mButtonCancel;
    private TextView mTextViewAmount;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giftcardcashout_activity);
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
        setActionBar();
        MapIngId();

    }

    private void MapIngId() {
        mButtonCashAmount = (Button) findViewById(R.id.btn_getcash);
        mButtonCancel = (Button) findViewById(R.id.btn_cancel);
        mButtonGetGiftCard = (Button) findViewById(R.id.btn_get_gift_card);
        mTextViewAmount = (TextView) findViewById(R.id.txt_available_balance);
        mButtonCashAmount.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
        mButtonGetGiftCard.setOnClickListener(this);
        mTextViewAmount.setText("$" + mSharedPreferences.getString("amount", ""));

    }

    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("SignUpActivity");
    }

    public void localyticstagEvent(String method) {
        Map<String, String> home_values = new HashMap<String, String>();

        home_values.put("Success", "Yes");
        home_values.put("Method", method);

        Localytics.tagEvent("SignUp", home_values);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_getcash:
                Intent intent = new Intent(getApplicationContext(), GetCashoutInfo.class);
                mEditor.putString("cashout_voucher", "");
                mEditor.putString("cashout_cash", "true");
                mEditor.commit();
                startActivity(intent);
                break;
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_get_gift_card:
                Intent intent1 = new Intent(getApplicationContext(), CashoutVoucher.class);
                mEditor.putString("cashout_cash", "false");
                mEditor.commit();
                startActivity(intent1);
                break;

        }

    }
}
