package com.spa.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.localytics.android.Localytics;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.CashoutVoucher;
import com.spa.adapter.GiftCardHistoryAdapter;
import com.spa.adapter.ReferralAdapter;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.orderhistory.MyEarnings;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;

import java.util.HashMap;
import java.util.Map;

import co.spa.sidemenu.interfaces.ScreenShotable;

/**
 * FileName : AuctionMainFragment
 * Description :
 * Dependencies : Internet
 */
public class GiftCardFragment extends Fragment implements ScreenShotable, View.OnClickListener {
    private ListView mListView;
    private Boolean isInternetPresent = false;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ProgressDialog pDialog;
    private MyEarnings myEarnings;
    private TextView mTextViewTotalAmount, mTextViewTotalGiftCardsAmount, mTextViewTotalReferralsAmount, mTextViewTotalRedeemAmount;
    private Button btnGiftCard, btnReferred, mButtonReddemNow;
    private LinearLayout linearLayoutGiftCard, linearLayoutReferred;


    public static GiftCardFragment newInstance() {
        GiftCardFragment contentFragment = new GiftCardFragment();
        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //==========================================================================================
        View rootView = inflater.inflate(R.layout.giftcardfragment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mListView = (ListView) rootView.findViewById(R.id.lv_gift);
        mTextViewTotalAmount = (TextView) rootView.findViewById(R.id.txt_totalamount);
        mTextViewTotalReferralsAmount = (TextView) rootView.findViewById(R.id.txt_totalamount_referral);
        mTextViewTotalGiftCardsAmount = (TextView) rootView.findViewById(R.id.txt_totalamount_gift_card);
        mTextViewTotalRedeemAmount = (TextView) rootView.findViewById(R.id.txt_redeem_amount);

        btnGiftCard = (Button) rootView.findViewById(R.id.btn_earned_gift_card);
        mButtonReddemNow = (Button) rootView.findViewById(R.id.btn_redeemnow);
        btnReferred = (Button) rootView.findViewById(R.id.btn_referred);
        linearLayoutGiftCard = (LinearLayout) rootView.findViewById(R.id.lnr_earned_gift_card);
        linearLayoutReferred = (LinearLayout) rootView.findViewById(R.id.lnr_referred);
        btnGiftCard.setOnClickListener(this);
        btnReferred.setOnClickListener(this);
        mButtonReddemNow.setOnClickListener(this);

        mSharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        mEditor = mSharedPreferences.edit();
        mListView.setEmptyView(rootView.findViewById(android.R.id.empty));
        isInternetPresent = NetworkUtil.isConnectingToInternet(getActivity());
        if (isInternetPresent) {
            new HttpGetAsyncTask_Get_Data().execute();
        } else {
            ShowDailog.Show_Alert_Dailog(getActivity());
        }
        return rootView;
    }

    @Override
    public void takeScreenShot() {
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_earned_gift_card:
                linearLayoutGiftCard.setBackgroundColor(Color.RED);
                linearLayoutReferred.setBackgroundColor(Color.WHITE);
                mListView.setAdapter(new GiftCardHistoryAdapter(getActivity(), myEarnings));
                break;
            case R.id.btn_referred:
                linearLayoutGiftCard.setBackgroundColor(Color.WHITE);
                linearLayoutReferred.setBackgroundColor(Color.RED);
                mListView.setAdapter(new ReferralAdapter(getActivity(), myEarnings));
                break;
            case R.id.btn_redeemnow:
                if (myEarnings.getTotalAmount() - myEarnings.getRedeemAmount() > 0) {
                    Intent intent = new Intent(getActivity(), CashoutVoucher.class);
                    mEditor.putString("amount", "" + (myEarnings.getTotalAmount() - myEarnings.getRedeemAmount()));
                    intent.putExtra("amount", (myEarnings.getTotalAmount() - myEarnings.getRedeemAmount()));
                    mEditor.commit();
                    startActivity(intent);
                } else {
                    Toast.makeText(getActivity(), "Not sufficient balance", Toast.LENGTH_LONG)
                            .show();
                }
                break;
        }
    }

    class HttpGetAsyncTask_Get_Data extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                myEarnings = Jsondata.get_orderhistory("app_user_id=" + mSharedPreferences.getString(Constant.APP_USER_ID, ""), getActivity());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress dialog
         * **/

        protected void onPostExecute(String response) {
            pDialog.dismiss();
            try {


                if (myEarnings.getSuccess()) {
                    if (myEarnings.getGifts() == null) {
                        ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
                    } else {
                        mTextViewTotalReferralsAmount.setText("$" + myEarnings.getTotalReferralAmount());
                        mTextViewTotalAmount.setText("$" + myEarnings.getTotalAmount());
                        mTextViewTotalGiftCardsAmount.setText("$" + myEarnings.getGiftAmount());
                        mTextViewTotalRedeemAmount.setText("$" + myEarnings.getRedeemAmount());
                        if (myEarnings.getGifts().size() > 0) {
                            localyticstagEvent("MyOrder");

                            mListView.setAdapter(new GiftCardHistoryAdapter(getActivity(), myEarnings));
                        }
                    }

                }
            } catch (Exception e) {
                ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
                e.printStackTrace();
            }
        }
    }

    public void localyticstagEvent(String method) {
        Map<String, String> home_values = new HashMap<String, String>();

        home_values.put("Success", "Yes");
        home_values.put("Method", method);

        Localytics.tagEvent("home", home_values);

    }


}
