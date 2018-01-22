package com.spa.servicedealz.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.localytics.android.Localytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.spa.adapter.CommentAdapter;
import com.spa.adapter.EquipmentsAdapter;
import com.spa.adapter.ExtraServicesAdapter;
import com.spa.adapter.PlanSubscriptionAdater;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.cellphondetail.DealDeatil;
import com.spa.model.custemizedealdetail.DealExtraService;
import com.spa.model.profile.Userdata;
import com.spa.servicedealz.R;
import com.spa.utils.Constant;
import com.spa.utils.ItemClickSupport;
import com.spa.utils.Jsondata;
import com.spa.utils.PaddingItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Diwakar on 9/14/2016.
 */
public class CellphoneCustemDealActivity extends ShareBaseActivtiy implements View.OnClickListener {
    private Toolbar mToolbar;
    private float mRating;
    private EditText mEditTextCommentText, mEditTextCommentTitle;
    private Boolean mIsInternetPresent = false;
    public static TextView mTextViewPrice, mTextViewDealTitle, mTextViewDealDetailDesc, mTextViewAbout, mTextViewReview,
            mTextViewExpiryDate, mTextViewEffectivePrice;
    private Button mButtonCancel, mButtonOk, mButtonActivate;
    private ImageView mImageViewShare, mImageViewUser, mImageViewCompany;
    private LinearLayout mLinearLayoutStarRating, mLinearLayoutEffective, mLinearLayoutEquipments, mLinearLayoutExtraServices, mLinearLayoutPlanSubscriptions;
    private RatingBar mRatingBarSecond;
    private String rating_deal;
    private ListView lv_comment;
    public static double value;
    ArrayList<Boolean> arrayList = new ArrayList<>();

    public static ArrayList<HashMap<String, String>> comment_list = new ArrayList<HashMap<String, String>>();
    //    public static SharedPreferences mSharedPreferences;
    private Userdata userdata;
    private RecyclerView mRecyclerViewEquipments, mRecyclerViewExtraServices, mRecyclerViewPlanSubscriptions;
    private DealDeatil custemizeDealDetail;
    PaddingItemDecoration addItemDecoration;
    private float mmEquipementPrice;
    private List<DealExtraService> url;
    ArrayList<Boolean> arrayList2 = new ArrayList<>();
    String mTotelEffective, mUrlDescription, mCellPhoneTitle, mEquipementColorName, mEquipementCellPhoneDetailId, mEquipementDealId, mEquipementEffectivePrice, mEquipementEffectiveTotelPrice;
    public static Boolean EmailVerified = false;
    ProgressDialog pd;
    ProgressDialog progressDialog;
    private String effectivePrice, otherPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.deal_detail_layout);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
          /* Initialize application preferences */
        mSharedPreferences = getSharedPreferences(Constant.SHARED_PREF, 0);
        mIsInternetPresent = NetworkUtil.isConnectingToInternet(this);

        setActionbar();
        setMapFindId();

        setClickListener();
        if (mIsInternetPresent) {
            new HttpGetAsyncTask_get().execute();
            new HttpGetAsyncTask_comment().execute();
        } else {
            ShowDailog.Show_Alert_Dailog(this);
        }

    }


    /*method to set clicklistener */
    private void setClickListener() {
        mLinearLayoutStarRating.setOnClickListener(this);
        mImageViewShare.setOnClickListener(this);
        mButtonActivate.setEnabled(true);
        mButtonActivate.setOnClickListener(this);
    }

    /*Method to set  data in view Component*/
    private void setDataInViewComponent() {

        if (custemizeDealDetail.getDeals().getIsOrderAvailable()) {
            mButtonActivate.setEnabled(true);
            mButtonActivate.setClickable(true);
            mButtonActivate.setBackground(getResources().getDrawable(R.drawable.activate_drawable
            ));
        } else {
            mButtonActivate.setEnabled(false);
            mButtonActivate.setClickable(false);
            mButtonActivate.setBackground(getResources().getDrawable(R.drawable.disable_drawable));
        }
        if (custemizeDealDetail.getDeals().getAverageRating().toString().equalsIgnoreCase("")) {
            mRatingBarSecond.setRating((float) 0.00);
        } else {
            float f1 = Float.parseFloat(custemizeDealDetail.getDeals().getAverageRating().toString());
            mRatingBarSecond.setRating(f1);
        }
        if (custemizeDealDetail.getDeals().getContractPeriod() == 0) {
            mTextViewExpiryDate.setText("Contract: None");
        } else {
            mTextViewExpiryDate.setText("Contract: " + custemizeDealDetail.getDeals().getContractPeriod() + " months");
        }
        Picasso.with(this)
                .load(custemizeDealDetail.getDeals().getDealImageUrl().toString()).placeholder(R.drawable.progress_animation)
                .into(mImageViewCompany);
        mTextViewReview.setText(custemizeDealDetail.getDeals().getRatingCount().toString() + " reviews");
        mTextViewPrice.setText(custemizeDealDetail.getDeals().getDealPrice().toString() + " /");
        mTextViewDealTitle.setText(custemizeDealDetail.getDeals().getTitle().toString());

        mTextViewDealDetailDesc.setText(custemizeDealDetail.getDeals().getShortDescription().toString());
        mTextViewAbout.setText(custemizeDealDetail.getDeals().getDetailDescription().toString());
        if (!custemizeDealDetail.getDeals().getEffectivePrice().equalsIgnoreCase("0.00")) {
            mLinearLayoutEffective.setVisibility(View.VISIBLE);
            effectivePrice = custemizeDealDetail.getDeals().getEffectivePrice();
//            effectivePrice=  String.format("%.2f", custemizeDealDetail.getDeals().getEffectivePrice());
            try {
                value = Double.parseDouble(effectivePrice);
            } catch (Exception e) {
                e.printStackTrace();
            }


            mTextViewEffectivePrice.setText("$" + value);

            mTextViewEffectivePrice.setTypeface(null, Typeface.BOLD);
        } else {
            mLinearLayoutEffective.setVisibility(View.GONE);
        }
//      iewEffectivePrice.setText("" + effectivePrice);
        try {
            if (addItemDecoration == null) {
                addItemDecoration = new PaddingItemDecoration(this);
            }
        } catch (Exception e) {
        }

        if (custemizeDealDetail.getDeals().getDealEquipments() != null && custemizeDealDetail.getDeals().getDealEquipments().size() > 0) {

            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerViewEquipments.setLayoutManager(layoutManager);
            //  mRecyclerViewEquipments.addItemDecoration(addItemDecoration);
            mRecyclerViewEquipments.setAdapter(new EquipmentsAdapter(CellphoneCustemDealActivity.this, custemizeDealDetail));
        } else {
            mLinearLayoutEquipments.setVisibility(View.GONE);
        }
        if (custemizeDealDetail.getDeals().getDealAttributes() != null && custemizeDealDetail.getDeals().getDealAttributes().size() > 0) {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerViewPlanSubscriptions.setLayoutManager(layoutManager);
            //  mRecyclerViewPlanSubscriptions.addItemDecoration(addItemDecoration);
//            mRecyclerViewPlanSubscriptions.setAdapter(new PlanSubscriptionAdater(CellphoneCustemDealActivity.this, custemizeDealDetail));
            for (int i = 0; custemizeDealDetail.getDeals().getDealAttributes().size() > i; i++) {
                if (i == 0) {
                    arrayList.add(i, true);
                } else {
                    arrayList.add(i, false);
                }

            }
            mRecyclerViewPlanSubscriptions.setAdapter(new PlanSubscriptionAdater(CellphoneCustemDealActivity.this, custemizeDealDetail, arrayList));
        } else {
            mLinearLayoutPlanSubscriptions.setVisibility(View.GONE);
        }
        if (custemizeDealDetail.getDeals().getDealExtraServices() != null && custemizeDealDetail.getDeals().getDealExtraServices().size() > 0) {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            mRecyclerViewExtraServices.setLayoutManager(layoutManager);
            // mRecyclerViewExtraServices.addItemDecoration(addItemDecoration);
            for (int i = 0; custemizeDealDetail.getDeals().getDealExtraServices().size() > i; i++) {
                arrayList2.add(i, false);
            }
            mRecyclerViewExtraServices.setAdapter(new ExtraServicesAdapter(CellphoneCustemDealActivity.this, custemizeDealDetail, arrayList2));
        } else {
            SharedPreferences sp = getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("ExtraServicePrice", "0.0");
            editor.commit();
            mLinearLayoutExtraServices.setVisibility(View.GONE);
        }


    }

    /*Method set to find id view component*/
    private void setMapFindId() {
        LayoutInflater inflater1 = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater1.inflate(R.layout.cellphone_custem, lv_comment, false);
        lv_comment = (ListView) findViewById(R.id.lv_comment);
        lv_comment.addHeaderView(header, null, false);
        mLinearLayoutStarRating = (LinearLayout) findViewById(R.id.ll_star_rating);
        mLinearLayoutEquipments = (LinearLayout) findViewById(R.id.ll_equiments);
        mLinearLayoutExtraServices = (LinearLayout) findViewById(R.id.ll_services);
        mLinearLayoutPlanSubscriptions = (LinearLayout) findViewById(R.id.ll_plan_subscriptions);
        mRecyclerViewEquipments = (RecyclerView) findViewById(R.id.lv_equipments);
        mRecyclerViewExtraServices = (RecyclerView) findViewById(R.id.lv_data_services);
        mRecyclerViewPlanSubscriptions = (RecyclerView) findViewById(R.id.lv_extra_services);
        pd = new ProgressDialog(CellphoneCustemDealActivity.this);

        ItemClickSupport.addTo(mRecyclerViewEquipments).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                mCellPhoneTitle = custemizeDealDetail.getDeals().getDealEquipments().get(position).getCellphoneName();
                mEquipementDealId = String.valueOf(custemizeDealDetail.getDeals().getDealEquipments().get(position).getId());

                mmEquipementPrice = Float.parseFloat(custemizeDealDetail.getDeals().getDealEquipments().get(position).getPrice());

//                Toast.makeText(getApplicationContext(), "" + mEquipementColorName, Toast.LENGTH_SHORT).show();
                mUrlDescription = custemizeDealDetail.getDeals().getDealEquipments().get(position).getDescription();
//                Show_Alert_Dailog(CellphoneCustemDealActivity.this);
                showChangeLangDialog();
            }
        });

        ItemClickSupport.addTo(mRecyclerViewExtraServices).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                mCellPhoneTitle = custemizeDealDetail.getDeals().getDealExtraServices().get(position).getServiceName();

                mUrlDescription = custemizeDealDetail.getDeals().getDealExtraServices().get(position).getServiceDescription();
                showChangeLangDialog();
            }
        });

        ItemClickSupport.addTo(mRecyclerViewPlanSubscriptions).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                mCellPhoneTitle = custemizeDealDetail.getDeals().getDealAttributes().get(position).getTitle();
                mUrlDescription = custemizeDealDetail.getDeals().getDealAttributes().get(position).getDescription();

                showChangeLangDialog();

            }
        });

        mTextViewEffectivePrice = (TextView) findViewById(R.id.cellphoneffective_deal_price);
        mLinearLayoutEffective = (LinearLayout) findViewById(R.id.ll_effective);
        mRatingBarSecond = (RatingBar) findViewById(R.id.ratingBar2);
        LayerDrawable stars = (LayerDrawable) mRatingBarSecond.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.yellow_color), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1)
                .setColorFilter(getResources().getColor(R.color.ratingblankcolor),
                        PorterDuff.Mode.SRC_ATOP);
        mButtonActivate = (Button) findViewById(R.id.txt_activate11);
        mTextViewPrice = (TextView) findViewById(R.id.txt_price);
        mTextViewDealTitle = (TextView) findViewById(R.id.txt_deal_detail_title);
        mTextViewDealDetailDesc = (TextView) findViewById(R.id.txt_short_description);
        mTextViewAbout = (TextView) findViewById(R.id.txt_about);
        mTextViewReview = (TextView) findViewById(R.id.txt_reviews);
        mTextViewExpiryDate = (TextView) findViewById(R.id.txt_expiry_date);
        mImageViewShare = (ImageView) findViewById(R.id.img_share);
        mImageViewCompany = (ImageView) findViewById(R.id.img_company);


    }


    public void showChangeLangDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_submit, null);
        dialogBuilder.setView(dialogView);
        final TextView edt = (TextView) dialogView.findViewById(R.id.customdailog);
        dialogBuilder.setTitle(mCellPhoneTitle);
//        dialogBuilder.setMessage(mCellPhoneTitle);
        dialogBuilder.setMessage(mUrlDescription);

        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    /*method set to action bar */
    private void setActionbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        mToolbar.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Deal Detail" + "</font>")));
    }

    /*background task to get comment data */
    class HttpGetAsyncTask_comment extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                comment_list
                        = Jsondata.get_comment(mSharedPreferences.getString("DealId", "0"), mSharedPreferences.getString(Constant.APP_USER_ID, ""), CellphoneCustemDealActivity.this);
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
            //  pDialog.dismiss();
            if (comment_list.size() > 0) {
                mTextViewReview.setText(Jsondata.comment_count + " reviews");
                float f1 = Float.parseFloat(Jsondata.average_rating);
                mRatingBarSecond.setRating(f1);
            }
            lv_comment.setAdapter(new CommentAdapter(CellphoneCustemDealActivity.this, comment_list));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("DealDetailActivity");


    }

    /*Get profile image in background*/
    public class HttpGetAsyncTask_get extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {

            progressDialog = new ProgressDialog(CellphoneCustemDealActivity.this);
            progressDialog.setMessage("Wait");
            progressDialog.show();
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            try {
                EmailVerified = Jsondata.EamilVerication(mSharedPreferences.getString(Constant.APP_USER_ID, ""), CellphoneCustemDealActivity.this);
                userdata = Jsondata.get_general_info(mSharedPreferences.getString(Constant.APP_USER_ID, ""), CellphoneCustemDealActivity.this);
                custemizeDealDetail = Jsondata.GetCustmizeDealDetail(mSharedPreferences.getString("DealId", ""), CellphoneCustemDealActivity.this);
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
            progressDialog.dismiss();

            if (Jsondata.Success.equals("true")) {
                localyticstagEvent("ShowDealList");
            } /*else {
                ShowDailog.Show_Alert_Login(CellphoneCustemDealActivity.this, getResources().getText(R.string.server_error).toString());
            }*/
            setDataInViewComponent();

        }
    }


    public void localyticstagEvent(String method) {
        Map<String, String> home_values = new HashMap<String, String>();

        home_values.put("Success", "Yes");
        home_values.put("Method", method);


        Localytics.tagEvent("DealList", home_values);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_star_rating: {
                if (!mSharedPreferences.getString("login_flag", "").equalsIgnoreCase("")) {
                    Rating_object();
                } else {
                    ShowDailog.Show_Alert_Login(this, "To access this feature please Login first.", "Alert");
                }
            }

            break;
            case R.id.txt_activate11:
                if (custemizeDealDetail.getDeals().getIsOrderAvailable()) {
                    if (!mSharedPreferences.getString("login_flag", "").equalsIgnoreCase("")) {
                        if (NetworkUtil.isConnectingToInternet(CellphoneCustemDealActivity.this)) {
                            if (EmailVerified) {
                                Intent intent = new Intent(getApplicationContext(), PlaceOrderAndPay.class);
                                intent.putExtra("dealid", custemizeDealDetail.getDeals().getId());
                                intent.putExtra("CustomActivity", true);
                                intent.putExtra("CableActivity", getResources().getString(R.string.cell_phone));
                                String mLatestEffectivePrice = mTextViewEffectivePrice.getText().toString().replace("$", "");
                                SharedPreferences sp = getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sp.edit();
//
                                editor.putString("value", mLatestEffectivePrice);
                                editor.commit();

                                startActivity(intent);

                            } else {
                                ShowDailog.Show_Alert_Login(this, "To access this feature please Verify Email");
                            }

                        }

                    } else {
                        ShowDailog.Show_Alert_Login(this, "To access this feature please Login first.", "Alert");

                    }
                }
                break;

            case R.id.img_share: {

                displayPopupWindow(v, getIntent().getIntExtra("deal_position", 0));
                break;
            }


        }
    }


    /*Method open rating dailog and rate to the deal*/

    private void Rating_object() {
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        View promptView = layoutInflater.inflate(R.layout.rate_deal, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

        // set prompts.xml to be the layout file of the alertdialog builder
        alertDialogBuilder.setView(promptView);
        mImageViewUser = (ImageView) promptView.findViewById(R.id.img_user);
        mButtonOk = (Button) promptView.findViewById(R.id.btn_ok);
        mButtonCancel = (Button) promptView.findViewById(R.id.btn_cancel);
        mEditTextCommentTitle = (EditText) promptView.findViewById(R.id.etx_comment_title);
        mEditTextCommentText = (EditText) promptView.findViewById(R.id.etx_comment);
        final RatingBar ratingbar1 = (RatingBar) promptView.findViewById(R.id.ratingBar);

        int rounded_value = 200;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true)
                .displayer(new RoundedBitmapDisplayer(rounded_value))
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getBaseContext()).defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);

        if (comment_list.size() > 0) {
            if (comment_list.get(0).get("app_user_id").toString().equals(mSharedPreferences.getString(Constant.APP_USER_ID, ""))) {
                mEditTextCommentTitle.setText(comment_list.get(0).get("comment_title").toString());
                mEditTextCommentText.setText(comment_list.get(0).get("comment_text").toString());
                float f1 = Float.parseFloat(comment_list.get(0).get("rating_point").toString());
                ratingbar1.setRating(f1);
                if (comment_list.get(0).get("app_user_image_url").toString().equals(null) || comment_list.get(0).get("app_user_image_url").toString().equalsIgnoreCase("")) {
                } else {
                    ImageLoader.getInstance().displayImage(comment_list.get(0).get("app_user_image_url").toString(), mImageViewUser, options);
                }
            }
        } else {
            try {
                if (userdata.getSuccess()) {
                    ImageLoader.getInstance().displayImage(userdata.getAppUser().getAvatarUrl(), mImageViewUser, options);
                }
            } catch (Exception e) {
            }


        }
        final AlertDialog alertD = alertDialogBuilder.create();
        mButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                rating_deal = String.valueOf(ratingbar1.getRating());
                mRating = ratingbar1.getRating();
                if (mRating > 0) {
                    alertD.dismiss();
                    new HttpGetAsyncTask().execute();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill Rating.", Toast.LENGTH_SHORT).show();
                }


            }
        });
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();

            }
        });


        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        // gps.showSettingsAlert();
        alertD.show();
    }

    /*Send data to server in background*/
    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CellphoneCustemDealActivity.this);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response
                        = Jsondata.get_rating(mSharedPreferences.getString(Constant.APP_USER_ID, ""), "" + custemizeDealDetail.getDeals().getId(), rating_deal,
                        mEditTextCommentTitle.getText().toString().trim(), mEditTextCommentText.getText().toString().trim(), CellphoneCustemDealActivity.this);
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
            if (response.equalsIgnoreCase("true")) {
                Toast toast;
                toast = Toast.makeText(CellphoneCustemDealActivity.this, "Thank you.",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                if (mIsInternetPresent) {

                    new HttpGetAsyncTask_comment().execute();
                } else {
                    ShowDailog.Show_Alert_Dailog(CellphoneCustemDealActivity.this);
                }
            } else if (response.equalsIgnoreCase("false")) {
            } else {
                ShowDailog.Show_Alert_Login(CellphoneCustemDealActivity.this, getResources().getText(R.string.server_error).toString());
            }
        }


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
}
