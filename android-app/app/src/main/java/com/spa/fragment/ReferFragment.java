package com.spa.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.localytics.android.Localytics;
import com.spa.servicedealz.R;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.referral.ReferralCode;
import com.spa.model.referral.ReferralCodeSubmit;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;
import com.spa.utils.MenuItemGlobal;
import com.spa.utils.Validation;

import java.util.HashMap;
import java.util.Map;

import co.spa.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Diwakar on 5/26/2016.
 */
public class ReferFragment extends Fragment implements ScreenShotable, View.OnClickListener {
    private Boolean isInternetPresent = false;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ProgressDialog pDialog;
    private TextView mTextViewReferralCode, mTextViewEdit;
    private Button mButtonInvite, mButtonReferalCode;
    private EditText mEditTextreferralCode;
    private String referralcode, referral_gift_coins, referrer_gift_coins;
    private ReferralCodeSubmit referralCodeSubmit;
    private RelativeLayout mRelativeLayout;
    private ReferralCode referralCode;

    public static ReferFragment newInstance() {
        ReferFragment referFragment = new ReferFragment();
        return referFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.refer_fragment, container, false);
        mSharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        mEditor = mSharedPreferences.edit();
        mButtonReferalCode = (Button) rootView.findViewById(R.id.btn_refferal_code);
        mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.rl_promo_code);
        // mTextViewEdit = (TextView) rootView.findViewById(R.id.txt_edit);
        mEditTextreferralCode = (EditText) rootView.findViewById(R.id.etx_referral_code);
        mEditTextreferralCode.addTextChangedListener(passwordWatcher);
        mButtonInvite = (Button) rootView.findViewById(R.id.btn_invite);
        mButtonInvite.setOnClickListener(this);
        mButtonReferalCode.setOnClickListener(this);
        mTextViewReferralCode = (TextView) rootView.findViewById(R.id.txt_refrralcode);
        return rootView;
    }

    private final TextWatcher passwordWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            if (s.length() >= 4) {
                mButtonReferalCode.setEnabled(true);
            } else {
                mButtonReferalCode.setEnabled(false);
            }
        }
    };

    @Override
    public void takeScreenShot() {
    }

    @Override
    public void onResume() {
        super.onResume();
        isInternetPresent = NetworkUtil.isConnectingToInternet(getActivity());
        if (isInternetPresent) {
            new HttpGetAsyncTask_Get_Data().execute();
        } else {
            ShowDailog.Show_Alert_Dailog(getActivity());
        }
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_invite:
                MenuItemGlobal.referralClicked(referralCode.getApp_user_code(), getActivity());
                break;

            case R.id.btn_refferal_code:

                isInternetPresent = NetworkUtil.isConnectingToInternet(getActivity());
                if (isInternetPresent) {
                    if (Validation.isFieldEmpty(mEditTextreferralCode)) {
                        Toast.makeText(getActivity(), "Referral code is required", Toast.LENGTH_LONG)
                                .show();
                    } else if (mEditTextreferralCode.getText().toString().equalsIgnoreCase(referralCode.getApp_user_code())) {
                        Toast.makeText(getActivity(), "Please use different referral code", Toast.LENGTH_LONG)
                                .show();
                    } else {
                        GetTextData();
                        new HttpGetAsyncTask().execute();
                    }
                } else {
                    ShowDailog.Show_Alert_Dailog(getActivity());
                }
                break;


        }
    }

    private void GetTextData() {

        referralcode = mEditTextreferralCode.getText().toString();
        referral_gift_coins = "";
        referrer_gift_coins = "";

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
                referralCode = Jsondata.getreferral_code(mSharedPreferences.getString(Constant.APP_USER_ID, ""), getActivity());
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
            if (referralCode != null) {
                if (referralCode.isSuccess()) {
                    localyticstagEvent("ReferralCode");
                    if (referralCode.isRefer_status()) {
                        mRelativeLayout.setVisibility(View.GONE);
                    } else {
                        mRelativeLayout.setVisibility(View.VISIBLE);
                    }
                    mTextViewReferralCode.setText(referralCode.getApp_user_code());
                }
            } else {
                ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
            }

        }
    }

    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
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
                referralCodeSubmit = Jsondata.get_referral_code(mSharedPreferences.getString(Constant.APP_USER_ID, ""), referralcode, referral_gift_coins, referrer_gift_coins, getActivity());
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
            if (referralCodeSubmit != null) {
                if (referralCodeSubmit.isSuccess()) {
                    localyticstagEvent("ReferralCode");
                    mRelativeLayout.setVisibility(View.GONE);
                    ShowDailog.Show_Alert_Login(getActivity(), referralCodeSubmit.getMessage());
                } else {
                    ShowDailog.Show_Alert_Login(getActivity(), referralCodeSubmit.getMessage());
                }
            } else {
                ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
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


