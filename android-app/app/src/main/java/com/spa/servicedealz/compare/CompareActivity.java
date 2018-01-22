package com.spa.servicedealz.compare;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.localytics.android.Localytics;
import com.spa.adapter.DealListAdapter;
import com.spa.fragment.DealListFragment;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.cableservice.Cable;
import com.spa.model.internetservice.Internet;
import com.spa.model.telephoneservice.Telephone;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.PlaceOrderAndPay;
import com.spa.servicedealz.ui.ShareBaseActivtiy;
import com.spa.utils.Constant;
import com.spa.utils.HttpGetAsyncTaskSubscribeDeal;
import com.spa.utils.Jsondata;
import com.squareup.picasso.Picasso;

/**
 * Created by Diwakar on 11/24/2015.
 */

/**
 * FileName : BundleCompareActivity
 * Description : Internet,Cable and Telephone current deal detail  and Two Compare Deal detail
 * Dependencies : Internet
 */
public class CompareActivity extends ShareBaseActivtiy implements View.OnClickListener {
    private Toolbar mToolbar;
    private Boolean isInternetPresent = false;
    private TextView mTextViewInternet, mTextViewCable, mTextViewTelephone, mTextViewDownloadFirst, mTextViewDownloadSecond, mTextViewCurrentUpload, mTextViewUploadFirst, mTextViewUploadSecond,
            mTextViewCurrentMinute, mTextViewCurrentText, mTextViewTextFirst, mTextViewTextSecond, mTextViewFreeChannels,
            mTextViewFreeChannelsFirst, mTextViewFreeChannelsSecond, mTextViewPremiumChannelsFirst, mTextViewPremiumChannelsSecond,
            mTextViewDownload, mTextViewCurrentProvider, mTextViewPriceFirst, mTextViewPriceSecond,
            mTextViewCurrentContractDate, mTextViewContractDateFirst, mTextViewContractDateSecond,
            mTextViewPremiumChannel, mTextViewMinutesFirst, mTextViewMinutesSecond, mTextViewInstallation, mTextViewMemoryInstallation, mTextViewInstallation2,
            mTextViewEquipments, mTextViewEquipments2, mTextViewProviderFirst, mTextViewProviderSecond, txt_yousave_compare1, mTextViewName, mTextViewModel, mTextViewModel1,
            txt_yousave_compare2, mTextViewOfferTitle, mTextViewTitleSecond, mTextViewTitleFirst, mTextViewOfferTitle2, mTextViewTitleInstallation, mTextViewOfferPrice, mTextViewOfferPrice2, mTextViewEffectiveFirst, mTextViewEffectiveSecond;

    private LinearLayout mLinearLayoutOffer, mLinearLayoutInternet, mLinearLayoutTelephone, mLinearLayoutCable, mLinearLayoutEffectiveFirst, mLinearLayoutEffectiveSecond;
    //private RatingBar mRatingFirst, mRatingSecond;
    private ImageView mImageViewFirst, mImageViewSecond;
    private SharedPreferences mSharedPreferences;
    private com.spa.model.bundleeservice.Bundle bundle;
    private Telephone telephone;
    private Button mButtonActivate, mButtonActivateSecond, mButtonShare, mButtonShareSecond;
    private Internet internet;
    private Cable cable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bundle_compare_activity);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mSharedPreferences = getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        String login_flag = mSharedPreferences.getString("login_flag", "");
        if (login_flag.equalsIgnoreCase("")) {
            ShowDailog.Show_Alert_Login(CompareActivity.this, "For better comparision please login.", "Alert");
        }
        mToolbar.setTitle(getResources().getString(R.string.compare_deal));
        MappingdId();
        setClickListner();
        setDataInTextView();
        isInternetPresent = NetworkUtil.isConnectingToInternet(CompareActivity.this);
        if (!isInternetPresent) {
            ShowDailog.Show_Alert_Dailog(this);
        } else {
            if (!mSharedPreferences.getString("login_flag", "").equals("")) {
                new HttpGetAsyncTask_get().execute();
            } else {
                txt_yousave_compare1.setVisibility(View.GONE);
                txt_yousave_compare2.setVisibility(View.GONE);
            }
        }
    }

    /*set click listner*/
    private void setClickListner() {
        mButtonActivate.setOnClickListener(this);
        mButtonActivateSecond.setOnClickListener(this);
        mButtonShare.setOnClickListener(this);
        mButtonShareSecond.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("CompareActivity");
    }

    /* * * Set value in EditText, TextView etc. * **/
    private void setDataInTextView() {
        String service_category_name = DealListFragment.dealListItem.getDeal().get(0).getService_category_name();
        if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.internet))) {
            setTextDataInternet();
            setTextDataComman();
            TextView Devider = (TextView) findViewById(R.id.txt_internet_devider);
            Devider.setVisibility(View.GONE);
            mLinearLayoutCable.setVisibility(View.GONE);
            mLinearLayoutTelephone.setVisibility(View.GONE);
            mTextViewInternet.setVisibility(View.GONE);

        } else if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.telephone))) {
            setTextDataTelephone();
            setTextDataComman();
            mLinearLayoutCable.setVisibility(View.GONE);
            mLinearLayoutInternet.setVisibility(View.GONE);
            mTextViewTelephone.setVisibility(View.GONE);

        } else if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.cable))) {
            setTextDataCable();
            setTextDataComman();
            mLinearLayoutInternet.setVisibility(View.GONE);
            mLinearLayoutTelephone.setVisibility(View.GONE);
            mTextViewCable.setVisibility(View.GONE);

        } else if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.cell_phone))) {
            setTextDataTelephone();
            setTextDataComman();
            mLinearLayoutCable.setVisibility(View.GONE);
            mLinearLayoutInternet.setVisibility(View.GONE);
            mTextViewTelephone.setVisibility(View.GONE);
        } else if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.bundle))) {
            setTextDataBundle();
        }
        if (DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).is_order_available()) {
            mButtonActivate.setEnabled(true);
            mButtonActivate.setClickable(true);
            mButtonActivate.setBackground(getResources().getDrawable(R.drawable.activate_drawable
            ));
        } else {
            mButtonActivate.setEnabled(false);
            mButtonActivate.setClickable(false);
            mButtonActivate.setBackground(getResources().getDrawable(R.drawable.disable_drawable));
        }
        if (DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).is_order_available()) {
            mButtonActivateSecond.setEnabled(true);
            mButtonActivateSecond.setClickable(true);
            mButtonActivateSecond.setBackground(getResources().getDrawable(R.drawable.activate_drawable
            ));
        } else {
            mButtonActivateSecond.setEnabled(false);
            mButtonActivateSecond.setClickable(false);
            mButtonActivateSecond.setBackground(getResources().getDrawable(R.drawable.disable_drawable));
        }
        mToolbar.setTitleTextColor(getResources().getColor(R.color.white_color));

    }

    private void setTextDataBundle() {
        setTextDataComman();
        setTextDataCable();
        setTextDataInternet();
        setTextDataTelephone();

    }

    private void setTextDataComman() {

        if (!DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getEffective_price().equalsIgnoreCase("0.00")) {
            mLinearLayoutEffectiveFirst.setVisibility(View.VISIBLE);
            //mTextViewPriceFirst.setPaintFlags(mTextViewPriceFirst.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            //mTextViewPriceFirst.setTypeface(null, Typeface.NORMAL);
            mTextViewEffectiveFirst.setText("$" + DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getEffective_price());
            mTextViewEffectiveFirst.setTypeface(null, Typeface.BOLD);
        } else {
            mLinearLayoutEffectiveFirst.setVisibility(View.GONE);
        }
        if (!DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getEffective_price().equalsIgnoreCase("0.00")) {
            mLinearLayoutEffectiveSecond.setVisibility(View.VISIBLE);
            //mTextViewPriceSecond.setPaintFlags(mTextViewPriceSecond.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            //mTextViewPriceSecond.setTypeface(null, Typeface.NORMAL);
            mTextViewEffectiveSecond.setText("$" + DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getEffective_price());
            mTextViewEffectiveSecond.setTypeface(null, Typeface.BOLD);
        } else {
            mLinearLayoutEffectiveSecond.setVisibility(View.GONE);
        }
        if (DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getContract_period().toString().equalsIgnoreCase("0")) {
            mTextViewContractDateFirst.setText("None");
        } else {
            String contract_period = DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getContract_period().toString();
            mTextViewContractDateFirst.setText(contract_period);
        }
        if (DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getContract_period().toString().equalsIgnoreCase("0")) {

            mTextViewContractDateSecond.setText("None");
        } else {
            String contract_period = DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getContract_period().toString();
            mTextViewContractDateSecond.setText(contract_period);
        }
        mTextViewTitleFirst.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getTitle().toString());
        mTextViewTitleSecond.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getTitle().toString());

        mTextViewPriceFirst.setText("$" + DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_price().toString());
        mTextViewPriceSecond.setText("$" + DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDeal_price().toString());
        mTextViewProviderFirst.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getService_provider_name().toString());
        mTextViewProviderSecond.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getService_provider_name().toString());
        // ImageLoader.getInstance().displayImage(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0)).getDeal_image_url().toString(), mImageViewFirst);
        //ImageLoader.getInstance().displayImage(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDeal_image_url().toString(), mImageViewSecond);
        Picasso.with(this)
                .load(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0)).getDeal_image_url().toString()).placeholder(R.drawable.progress_animation)
                .into(mImageViewFirst);
        Picasso.with(this)
                .load(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDeal_image_url().toString()).placeholder(R.drawable.progress_animation)
                .into(mImageViewSecond);
        if (DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getService_category_name().equalsIgnoreCase(getResources().getString(R.string.cell_phone))) {
            if (DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_equipments() != null && DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_equipments().size() > 0) {
                mTextViewInstallation.setText("$" + DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_equipments().get(0).getPrice().toString());
                mTextViewEquipments.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_equipments().get(0).getMemory().toString());
                mTextViewModel.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_equipments().get(0).getModel().toString());
            }
            if (DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getDeal_equipments() != null && DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getDeal_equipments().size() > 0) {
                mTextViewEquipments2.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDeal_equipments().get(0).getMemory().toString());
                mTextViewInstallation2.setText("$" + DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDeal_equipments().get(0).getPrice().toString());
                mTextViewModel1.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getDeal_equipments().get(0).getModel().toString());
            }
            mTextViewName.setText("Model Name");
            mTextViewMemoryInstallation.setText("Memory");
        } else {
            //  mTextViewTitleInstallation.setText("Equipment &amp; Installation-"+);
            mTextViewMemoryInstallation.setText("Installation");
            mTextViewName.setText("Name");
            if (DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_equipments() != null && DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_equipments().size() > 0) {
                mTextViewInstallation.setText("$" + DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_equipments().get(0).getPrice().toString());
                mTextViewEquipments.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_equipments().get(0).getInstallation().toString());
                mTextViewModel.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_equipments().get(0).getModel().toString());
            }
            if (DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getDeal_equipments() != null && DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getDeal_equipments().size() > 0) {
                mTextViewEquipments2.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDeal_equipments().get(0).getInstallation().toString());
                mTextViewInstallation2.setText("$" + DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDeal_equipments().get(0).getPrice().toString());
                mTextViewModel1.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getDeal_equipments().get(0).getModel().toString());
            }
        }
        if (DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_additional_offers() != null && DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_additional_offers().size() > 0) {
            mTextViewOfferTitle.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_additional_offers().get(0).getTitle().toString());
            mTextViewOfferPrice.setText("$" + DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_additional_offers().get(0).getPrice().toString());

            if (DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getDeal_additional_offers() != null && DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getDeal_additional_offers().size() > 0) {

                mTextViewOfferTitle2.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDeal_additional_offers().get(0).getTitle().toString());
                mTextViewOfferPrice2.setText("$" + DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDeal_additional_offers().get(0).getPrice().toString());
            }
        } else {
            if (DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getDeal_additional_offers() != null && DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getDeal_additional_offers().size() > 0) {

                mTextViewOfferTitle2.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDeal_additional_offers().get(0).getTitle().toString());
                mTextViewOfferPrice2.setText("$" + DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDeal_additional_offers().get(0).getPrice().toString());
            } else {
                mLinearLayoutOffer.setVisibility(View.GONE);
            }

        }
    }

    private void setTextDataCable() {
        mTextViewFreeChannelsFirst.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getFree_channels().toString());
        mTextViewFreeChannelsSecond.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getFree_channels().toString());
        mTextViewPremiumChannelsFirst.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getPremium_channels().toString());
        mTextViewPremiumChannelsSecond.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getPremium_channels().toString());

    }

    private void setTextDataTelephone() {
        mTextViewMinutesSecond.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDomestic_call_minutes().toString());
        mTextViewTextSecond.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getInternational_call_minutes().toString());
        mTextViewMinutesFirst.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0)).getDomestic_call_minutes().toString());
        mTextViewTextFirst.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0)).getInternational_call_minutes().toString());
    }

    private void setTextDataInternet() {

        mTextViewUploadSecond.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getUpload_speed().toString());
        mTextViewDownloadSecond.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDownload_speed().toString());
        mTextViewDownloadFirst.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0)).getDownload_speed().toString());
        mTextViewUploadFirst.setText(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0)).getUpload_speed().toString());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_activate:
                if (!mSharedPreferences.getString("login_flag", "").equalsIgnoreCase("")) {
                    if (NetworkUtil.isConnectingToInternet(this)) {
                        if (DealListFragment.EmailVerified) {
                            Intent intent = new Intent(getApplicationContext(), PlaceOrderAndPay.class);
                            intent.putExtra("dealid", DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0)).getId());
                            startActivity(intent);
                           // new HttpGetAsyncTaskSubscribeDeal(this).execute(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0)).getId().toString(), DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0)).getDeal_price().toString(), DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0)).getEffective_price().toString());

                        } else {
                            ShowDailog.Show_Alert_Login(this, "To access this feature please Verify Email");
                        }

                    }

                } else {
                    ShowDailog.Show_Alert_Login(this, "To access this feature please Login first.", "Alert");

                }
                break;
            case R.id.txt_activate1:
                if (!mSharedPreferences.getString("login_flag", "").equalsIgnoreCase("")) {
                    if (NetworkUtil.isConnectingToInternet(this)) {
                        if (DealListFragment.EmailVerified) {
                            Intent intent = new Intent(getApplicationContext(), PlaceOrderAndPay.class);
                            intent.putExtra("dealid", DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getId());
                            startActivity(intent);
                         //   new HttpGetAsyncTaskSubscribeDeal(this).execute(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getId().toString(), DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getDeal_price().toString(), DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1)).getEffective_price().toString());

                        } else {
                            ShowDailog.Show_Alert_Login(this, "To access this feature please Verify Email");
                        }


                    }

                } else {
                    ShowDailog.Show_Alert_Login(this, "To access this feature please Login first.", "Alert");

                }
                break;
            case R.id.txt_Share:
                displayPopupWindow(view, DealListAdapter.comapare_list.get(0));
                break;
            case R.id.txt_Share1:
                displayPopupWindow(view, DealListAdapter.comapare_list.get(1));
                break;
        }
    }

    /* *
             *   Get Current deal save detail in background task
             * **/
    class HttpGetAsyncTask_get extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CompareActivity.this, AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String service_category_name = DealListFragment.dealListItem.getDeal().get(0).getService_category_name();
                if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.internet))) {
                    internet = Jsondata.getInternetservices_prefences(mSharedPreferences.getString(Constant.APP_USER_ID, ""),
                            DealListFragment.dealListItem.getDeal().get(0).getService_category_id().toString(), CompareActivity.this);

                } else if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.telephone))) {
                    telephone = Jsondata.getTelephoneservices_prefences(mSharedPreferences.getString(Constant.APP_USER_ID, ""), DealListFragment.dealListItem.getDeal().get(0).getService_category_id().toString(), CompareActivity.this);

                } else if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.cable))) {
                    cable = Jsondata.getCableServices_Prefences(mSharedPreferences.getString(Constant.APP_USER_ID, ""), DealListFragment.dealListItem.getDeal().get(0).getService_category_id().toString(), CompareActivity.this);
                } else if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.cell_phone))) {
                    telephone = Jsondata.getTelephoneservices_prefences(mSharedPreferences.getString(Constant.APP_USER_ID, ""), DealListFragment.dealListItem.getDeal().get(0).getService_category_id().toString(), CompareActivity.this);
                } else if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.bundle))) {
                    bundle = Jsondata.getBundleservices_prefences(mSharedPreferences.getString(Constant.APP_USER_ID, ""), DealListFragment.dealListItem.getDeal().get(0).getService_category_id().toString(), CompareActivity.this);
                }
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
            String service_category_name = DealListFragment.dealListItem.getDeal().get(0).getService_category_name();
            if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.internet))) {
                setInternetData();
            } else if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.telephone))) {
                setCellphoneTelephoneData();
            } else if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.cable))) {
                setCableData();
            } else if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.cell_phone))) {
                setCellphoneTelephoneData();
            } else if (service_category_name.equalsIgnoreCase(getResources().getString(R.string.bundle))) {
                setBundleCurrentData();
            }

        }

    }

    private void setCableData() {
        try {
            if (cable.getSuccess()) {

                if (cable.getServicePreference().getIsContract()) {
                    mTextViewCurrentContractDate.setText("None");
                } else {
                    String[] departure = cable.getServicePreference().getEndDate().split(" ");
                    mTextViewCurrentContractDate.setText(departure[0]);
                }
                //  mTextViewCurrentPrice.setText(String.format("%.2f", cable.getServicePreference().getPrice()));
                mTextViewCurrentProvider.setText(mSharedPreferences.getString("current_provider", ""));
                mTextViewFreeChannels.setText("" + cable.getServicePreference().getFreeChannels());
                mTextViewPremiumChannel.setText("" + cable.getServicePreference().getPremiumChannels());
                double p11 = cable.getServicePreference().getPrice();
                double p12 = 0;//Double.parseDouble(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getDeal_price());
                p12 = Double.parseDouble(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getEffective_price());
                if (p11 - p12 < 0) {
                    txt_yousave_compare1.setText("($" + (String.format("%.2f", p12 - p11) + ")"));
                } else {
                    txt_yousave_compare1.setText("$" + (String.format("%.2f", p11 - p12)));
                }
                double p22 = 0;
                double p21 = cable.getServicePreference().getPrice();
                p22 = Double.parseDouble(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getEffective_price());
                if (p21 - p22 < 0) {
                    txt_yousave_compare2.setText("($" + (String.format("%.2f", p22 - p21) + ")"));
                } else {
                    txt_yousave_compare2.setText("$" + (String.format("%.2f", p21 - p22)));
                }
            } else {
                txt_yousave_compare1.setVisibility(View.GONE);
                txt_yousave_compare2.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setCellphoneTelephoneData() {
        try {
            if (telephone.getSuccess()) {
                if (telephone.getServicePreference().getIsContract()) {
                    mTextViewCurrentContractDate.setText("None");
                } else {
                    String[] departure = telephone.getServicePreference().getEndDate().split(" ");
                    mTextViewCurrentContractDate.setText(departure[0]);
                }
                double p11 = telephone.getServicePreference().getPrice();
                double p12 = Double.parseDouble(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getEffective_price());
                if (p11 - p12 < 0) {
                    txt_yousave_compare1.setText("($" + (String.format("%.2f", p12 - p11) + ")"));
                } else {
                    txt_yousave_compare1.setText("$" + (String.format("%.2f", p11 - p12)));
                }
                double p21 = telephone.getServicePreference().getPrice();
                double p22 = Double.parseDouble(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getEffective_price());
                if (p21 - p22 < 0) {
                    txt_yousave_compare2.setText("($" + (String.format("%.2f", p22 - p21) + ")"));
                } else {
                    txt_yousave_compare2.setText("$" + (String.format("%.2f", p21 - p22)));
                } // mTextViewCurrentPrice.setText(String.format("%.2f", telephone.getServicePreference().getPrice()));
                if (telephone.getServicePreference().getDomesticCallUnlimited()) {
                    mTextViewCurrentMinute.setText("Unlimited");
                } else {
                    mTextViewCurrentMinute.setText(telephone.getServicePreference().getDomesticCallMinutes());
                }
                if (telephone.getServicePreference().getInternationalCallUnlimited().equals("")) {
                    mTextViewCurrentText.setText("Unlimited");
                } else {
                    mTextViewCurrentText.setText(telephone.getServicePreference().getInternationalCallMinutes());
                }
                mTextViewCurrentProvider.setText(mSharedPreferences.getString("current_provider", ""));
            } else {
                txt_yousave_compare1.setVisibility(View.GONE);
                txt_yousave_compare2.setVisibility(View.GONE);
                // ShowDailog.Show_Alert_Login(TelephoneCompareActivity.this, getResources().getText(R.string.server_error).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInternetData() {
        try {
            if (internet.getSuccess()) {
                if (internet.getServicePreference().getIsContract()) {
                    mTextViewCurrentContractDate.setText("None");
                } else {
                    String[] departure = internet.getServicePreference().getEndDate().split(" ");
                    mTextViewCurrentContractDate.setText(departure[0]);
                }
                //  mTextViewCurrentProviderPrice.setText(String.format("%.2f", internet.getServicePreference().getPrice()));
                mTextViewDownload.setText("" + internet.getServicePreference().getDownloadSpeed());
                mTextViewCurrentUpload.setText("" + internet.getServicePreference().getUploadSpeed());
                double p11 = internet.getServicePreference().getPrice();
                double p12 = Double.parseDouble(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getEffective_price());
                if (p11 - p12 < 0) {
                    txt_yousave_compare1.setText("($" + (String.format("%.2f", p12 - p11) + ")"));
                } else {
                    txt_yousave_compare1.setText("$" + (String.format("%.2f", p11 - p12)));
                }
                double p21 = internet.getServicePreference().getPrice();
                double p22 = Double.parseDouble(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getEffective_price());
                if (p21 - p22 < 0) {
                    txt_yousave_compare2.setText("($" + (String.format("%.2f", p22 - p21) + ")"));
                } else {
                    txt_yousave_compare2.setText("$" + (String.format("%.2f", p21 - p22)));
                }
            } else {
                txt_yousave_compare1.setVisibility(View.GONE);
                txt_yousave_compare2.setVisibility(View.GONE);
                //  Show_Dailog.Show_Alert_Login(Internet_Services.this, getResources().getText(R.string.server_error).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setBundleCurrentData() {
        try {
            if (bundle.getSuccess()) {
                if (bundle.getServicePreference().getIsContract()) {
                    mTextViewCurrentContractDate.setText("None");
                } else {
                    String[] departure = bundle.getServicePreference().getEndDate().split(" ");
                    mTextViewCurrentContractDate.setText(departure[0]);
                }
                double p11 = bundle.getServicePreference().getPrice();
                double p12 = Double.parseDouble(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(0).intValue()).getEffective_price());
                if (p11 - p12 < 0) {
                    txt_yousave_compare1.setText("($" + (String.format("%.2f", p12 - p11) + ")"));
                } else {
                    txt_yousave_compare1.setText("$" + (String.format("%.2f", p11 - p12)));
                }
                double p21 = bundle.getServicePreference().getPrice();
                double p22 = Double.parseDouble(DealListFragment.dealListItem.getDeal().get(DealListAdapter.comapare_list.get(1).intValue()).getEffective_price());
                if (p21 - p22 < 0) {
                    txt_yousave_compare2.setText("($" + (String.format("%.2f", p22 - p21) + ")"));
                } else {
                    txt_yousave_compare2.setText("$" + (String.format("%.2f", p21 - p22)));
                }
                // mTextViewCurrentPrice.setText(String.format("%.2f", bundle.getServicePreference().getPrice()));
                mTextViewDownload.setText("" + bundle.getServicePreference().getDownloadSpeed());
                mTextViewCurrentUpload.setText(bundle.getServicePreference().getUploadSpeed());
                mTextViewFreeChannels.setText(bundle.getServicePreference().getFreeChannels());
                mTextViewPremiumChannel.setText(bundle.getServicePreference().getPremiumChannels());
                if (bundle.getServicePreference().getDomesticCallUnlimited()) {
                    mTextViewCurrentMinute.setText("Unlimited");
                } else {
                    mTextViewCurrentMinute.setText(bundle.getServicePreference().getDomesticCallMinutes());
                }
                if (Boolean.parseBoolean(""+bundle.getServicePreference().getInternationalCallUnlimited().equals(""))) {
                    mTextViewCurrentText.setText("Unlimited");
                } else {
                    mTextViewCurrentText.setText(bundle.getServicePreference().getInternationalCallMinutes());
                }
                mTextViewCurrentProvider.setText(mSharedPreferences.getString("current_provider", ""));
            } else {
                txt_yousave_compare1.setVisibility(View.GONE);
                txt_yousave_compare2.setVisibility(View.GONE);// ShowDailog.Show_Alert_Login(BundleCompareActivity.this, getResources().getText(R.string.server_error).toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* * *   Find by id all View Component
               * **/
    private void MappingdId() {
        mTextViewInternet = (TextView) findViewById(R.id.txt_internet);
        mTextViewTelephone = (TextView) findViewById(R.id.txt_telephone);
        mTextViewEffectiveFirst = (TextView) findViewById(R.id.effective_deal_price);
        mTextViewEffectiveSecond = (TextView) findViewById(R.id.effective_deal_price1);
        mTextViewTitleInstallation = (TextView) findViewById(R.id.txtinstallation);
        mTextViewMemoryInstallation = (TextView) findViewById(R.id.txt_memory);
        mTextViewCable = (TextView) findViewById(R.id.txt_cable);
        mTextViewInstallation = (TextView) findViewById(R.id.txt_installation);
        mTextViewName = (TextView) findViewById(R.id.txt_name);
        mTextViewModel1 = (TextView) findViewById(R.id.txt_model1);
        mTextViewModel = (TextView) findViewById(R.id.txt_model);

        mTextViewInstallation2 = (TextView) findViewById(R.id.txt_installation2);
        mTextViewEquipments = (TextView) findViewById(R.id.txt_equipments);
        mTextViewEquipments2 = (TextView) findViewById(R.id.txt_equipments2);
        mImageViewFirst = (ImageView) findViewById(R.id.img_provider_compare_image1);
        mImageViewSecond = (ImageView) findViewById(R.id.img_provider_compare_image2);
        mTextViewProviderSecond = (TextView) findViewById(R.id.txt_provider2);
        mButtonActivate = (Button) findViewById(R.id.txt_activate);
        mButtonActivateSecond = (Button) findViewById(R.id.txt_activate1);
        mButtonShare = (Button) findViewById(R.id.txt_Share);
        mButtonShareSecond = (Button) findViewById(R.id.txt_Share1);

        mTextViewTitleSecond = (TextView) findViewById(R.id.txt_title_compare2);
        mTextViewTitleFirst = (TextView) findViewById(R.id.txt_title_compare1);
 /* mRatingFirst = (RatingBar) findViewById(R.id.rating_compare1);
        mRatingSecond = (RatingBar) findViewById(R.id.rating_compare2);
        mTextViewCurrentPrice = (TextView) findViewById(R.id.txt_current_provider_price);
        mTextViewModemFirst = (TextView) findViewById(R.id.txt_modem_compare1);
        mTextViewModemSecond = (TextView) findViewById(R.id.txt_modem_compare2);
        mTextViewOfferSecond = (TextView) findViewById(R.id.txt_offer_compare1);
        mTextViewOfferFirst = (TextView) findViewById(R.id.txt_offer_compare1);
        mTextViewReviewFirst = (TextView) findViewById(R.id.txt_review_compare1);
        mTextViewReviewSecond = (TextView) findViewById(R.id.txt_review_compare2);





        mTextViewShortDescFirst = (TextView) findViewById(R.id.txt_short_desc_compare1);
        mTextViewShortDescSecond = (TextView) findViewById(R.id.txt_short_desc_compare2);
        mTextViewReviewFirst = (TextView) findViewById(R.id.txt_review_compare1);
        mTextViewReviewSecond = (TextView) findViewById(R.id.txt_review_compare2);*/
        txt_yousave_compare2 = (TextView) findViewById(R.id.txt_yousave_compare2);
        txt_yousave_compare1 = (TextView) findViewById(R.id.txt_yousave_compare1);
        mTextViewPremiumChannel = (TextView) findViewById(R.id.txt_premiumchannel_current_provider);
        mTextViewCurrentProvider = (TextView) findViewById(R.id.txt_current_provider);
        mTextViewCurrentContractDate = (TextView) findViewById(R.id.txt_current_provider_contract);
        mTextViewDownload = (TextView) findViewById(R.id.txt_current_provider_dowanload);
        mTextViewCurrentUpload = (TextView) findViewById(R.id.txt_current_provider_upload);
        mTextViewCurrentMinute = (TextView) findViewById(R.id.text_minutes_currentprovider);
        mTextViewCurrentText = (TextView) findViewById(R.id.text_text_currentprovider);

        mTextViewTextFirst = (TextView) findViewById(R.id.text_text_compare1);
        mTextViewTextSecond = (TextView) findViewById(R.id.text_text_compare2);
        mTextViewFreeChannels = (TextView) findViewById(R.id.txt_channels_current_provider);
        mTextViewFreeChannelsFirst = (TextView) findViewById(R.id.txt_channel_compare1);
        mTextViewFreeChannelsSecond = (TextView) findViewById(R.id.txt_channel_compare2);
        mTextViewPremiumChannelsFirst = (TextView) findViewById(R.id.txt_premiumchannel_compare1);
        mTextViewPremiumChannelsSecond = (TextView) findViewById(R.id.txt_premiumchannel_compare2);

        mTextViewUploadFirst = (TextView) findViewById(R.id.txt_upload_compare1);
        mTextViewUploadSecond = (TextView) findViewById(R.id.txt_upload_compare2);
        mTextViewDownloadSecond = (TextView) findViewById(R.id.txt_download_compare2);
        mTextViewDownloadFirst = (TextView) findViewById(R.id.txt_download_compare1);
        mTextViewPriceFirst = (TextView) findViewById(R.id.txt_price_compare1);
        mTextViewPriceSecond = (TextView) findViewById(R.id.txt_price_compare2);
        mTextViewProviderFirst = (TextView) findViewById(R.id.txt_provider1);
        mTextViewContractDateFirst = (TextView) findViewById(R.id.txt_contract_compare1);
        mTextViewContractDateSecond = (TextView) findViewById(R.id.txt_contract_compare2);
        mTextViewMinutesFirst = (TextView) findViewById(R.id.text_minutes_compare1);
        mTextViewMinutesSecond = (TextView) findViewById(R.id.text_minutes_compare2);
        mTextViewOfferTitle = (TextView) findViewById(R.id.txt_offer_title);
        mTextViewOfferTitle2 = (TextView) findViewById(R.id.txt_offer_title2);
        mTextViewOfferPrice = (TextView) findViewById(R.id.txt_offer_price);
        mTextViewOfferPrice2 = (TextView) findViewById(R.id.txt_offer_price2);
        mLinearLayoutOffer = (LinearLayout) findViewById(R.id.ll_offer);
        mLinearLayoutInternet = (LinearLayout) findViewById(R.id.ll_internet);
        mLinearLayoutTelephone = (LinearLayout) findViewById(R.id.ll_telephone);
        mLinearLayoutCable = (LinearLayout) findViewById(R.id.ll_cable);
        mLinearLayoutEffectiveFirst = (LinearLayout) findViewById(R.id.ll_effective_price);
        mLinearLayoutEffectiveSecond = (LinearLayout) findViewById(R.id.ll_effective_price1);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            DealListAdapter.comapare_list.clear();
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (item.getItemId() == android.R.id.home) {
            DealListAdapter.comapare_list.clear();
            this.finish();

        }
        return super.onOptionsItemSelected(item);
    }
}
