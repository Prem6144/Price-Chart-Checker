package com.spa.servicedealz.ui;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.spa.adapter.GiftCardAdapter;
import com.spa.servicedealz.R;
import com.spa.utils.Jsondata;

/**
 * Created by Diwakar on 4/13/2016.
 */
public class GiftCardActivty extends Activity implements View.OnClickListener {
    private ImageView mImageViewClose;
    private Button mButtonClickHere;
    private TextView mTextViewWonMessage;
    private ListView mListView;
    private OutgoingReceiver outgoingReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.giftcard_activity);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        mImageViewClose = (ImageView) findViewById(R.id.img_close);
        mButtonClickHere = (Button) findViewById(R.id.btn_update);
        mListView = (ListView) findViewById(R.id.lv_listview);
        if (Jsondata.giftcardorder != null) {
            mListView.setAdapter(new GiftCardAdapter(GiftCardActivty.this));
        }

        mTextViewWonMessage = (TextView) findViewById(R.id.txt_won_message);
       /* if (Jsondata.giftCard != null) {
            if (Jsondata.giftCard.getMessageStatus()) {
                mTextViewWonMessage.setText(Jsondata.giftCard.getMessage());
            } else {
                mTextViewWonMessage.setVisibility(View.GONE);
            }
        } else {
            mTextViewWonMessage.setVisibility(View.GONE);
        }*/
        mImageViewClose.setOnClickListener(this);
        mButtonClickHere.setOnClickListener(this);

        outgoingReceiver = new OutgoingReceiver();
        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        registerReceiver(outgoingReceiver, intentFilter);
    }

    public void makecall(View view) {
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:123456789"));
            startActivity(intent);
            finish();
            // Toast.makeText(this, "TEST",Toast.LENGTH_LONG).show();

           /* Runnable showDialogRun = new Runnable() {
                public void run() {
                    Intent showDialogIntent = new Intent(getApplicationContext(), PlaceOrderAndPay.class);
                    startActivity(showDialogIntent);
                }
            };
            Handler h = new Handler();
            h.postDelayed(showDialogRun, 6000);*/
        } catch (ActivityNotFoundException activityException) {
            Throwable e = null;
            // Log.e("helloandroid dialing example", "Callfailed", e);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                Intent HOME = new Intent(getApplicationContext(), OrderDealDetailActivity.class);
                HOME.putExtra("orderid", getIntent().getIntExtra("orderid", 0));
                HOME.putExtra("userid", getIntent().getStringExtra("userid"));
                startActivity(HOME);
                finish();
                break;
            case R.id.btn_update:
                makecall(view);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(outgoingReceiver);
    }

    /**
     * Broadcast receiver to detect the outgoing calls.
     */
    public class OutgoingReceiver extends BroadcastReceiver {
        public OutgoingReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            String number = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);

            Runnable showDialogRun = new Runnable() {
                public void run() {
                    Intent showDialogIntent = new Intent(getApplicationContext(), OrderDealDetailActivity.class);
                    showDialogIntent.putExtra("orderid", getIntent().getIntExtra("orderid", 0));
                    showDialogIntent.putExtra("userid", getIntent().getStringExtra("userid"));
                    startActivity(showDialogIntent);
                }
            };
            Handler h = new Handler();
            h.postDelayed(showDialogRun, 3000);
        }
    }
}
