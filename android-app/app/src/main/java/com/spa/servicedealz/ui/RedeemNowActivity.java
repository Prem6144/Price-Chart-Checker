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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.localytics.android.Localytics;
import com.spa.servicedealz.R;
import com.spa.utils.Constant;
import com.spa.utils.CustomizeDialog;
import com.spa.utils.Validation;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Diwakar on 5/31/2016.
 */
public class RedeemNowActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mButtonMaximumCashAmount, mButtonModifyCashAmount, mButtonCancel;
    private TextView mTextViewAmount;
    private CustomizeDialog mCustomizeDialog;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.redeemnow_activity);
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

    public void showForgotPasswordDialog() {
        mCustomizeDialog = new CustomizeDialog(this);
        mCustomizeDialog.setCancelable(false);
        mCustomizeDialog.setContentView(R.layout.cashout_amount_popup);
        final EditText Amount = (EditText) mCustomizeDialog.findViewById(R.id.etx_amount);
        ImageButton imagebtn_Cancel = (ImageButton) mCustomizeDialog.findViewById(R.id.imgbtn_cancel);
        Button btn_Submit = (Button) mCustomizeDialog.findViewById(R.id.btn_submit);
        btn_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validation.isFieldEmpty(Amount)) {
                    Toast.makeText(getApplicationContext(), "Amount should not be blank", Toast.LENGTH_LONG)
                            .show();
                } else if (Integer.parseInt(Amount.getText().toString()) > getIntent().getIntExtra("amount", 0)) {
                    Toast.makeText(getApplicationContext(), "Amount should not be greater than available balance", Toast.LENGTH_LONG)
                            .show();
                } else {
                    mCustomizeDialog.dismiss();
                    Intent intent = new Intent(getApplicationContext(), CashoutType.class);
                    mEditor.putString("amount", Amount.getText().toString());
                    mEditor.commit();
                    startActivity(intent);
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

    private void MapIngId() {
        mButtonCancel = (Button) findViewById(R.id.btn_cancel);
        mButtonMaximumCashAmount = (Button) findViewById(R.id.btn_cashout_maximum);
        mButtonModifyCashAmount = (Button) findViewById(R.id.btn_modify_cashout);
        mTextViewAmount = (TextView) findViewById(R.id.txt_available_balance);
        mButtonCancel.setOnClickListener(this);
        mButtonMaximumCashAmount.setOnClickListener(this);
        mButtonModifyCashAmount.setOnClickListener(this);
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
            case R.id.btn_cancel:
                finish();
                break;
            case R.id.btn_cashout_maximum:
                Intent intent = new Intent(getApplicationContext(), CashoutType.class);
                startActivity(intent);
                break;
            case R.id.btn_modify_cashout:
                showForgotPasswordDialog();
                break;
        }

    }
}