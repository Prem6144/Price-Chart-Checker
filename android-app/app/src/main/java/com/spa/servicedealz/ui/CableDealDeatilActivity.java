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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
import com.spa.adapter.ChanelEquipmentAdapter;
import com.spa.adapter.CommentAdapter;
import com.spa.adapter.PremimumAdapter;
import com.spa.adapter.TvAdapter;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.custemisecable.CustemiseCable;
import com.spa.model.profile.Userdata;
import com.spa.servicedealz.R;
import com.spa.utils.Constant;
import com.spa.utils.ItemClickSupport;
import com.spa.utils.Jsondata;
import com.spa.utils.PaddingItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by E0147ambar on 9/12/2016.
 */
public class CableDealDeatilActivity extends ShareBaseActivtiy implements View.OnClickListener {

    private Toolbar mToolbar;
    public static TextView textViewDealPrice, textViewTitle, textViewShortDetail, textViewContract, textViewEffectivePrice, textViewTotalPrice, mTextViewEffectivePrice, textViewAboutTheoffer, mTextViewReview;
    private float mRating;
    private Boolean isInternetPresent = false, Headeradded = false;
    private ProgressDialog pDialog;
    private EditText mEditTextCommentText, mEditTextCommentTitle;
    private ImageView imageViewIcon, mImageViewUser, mImageViewShare;
    private String effectivePrice, otherPrice;
    public static double value;
    int size;
    private Button mButtonCancel, mButtonOk, mOrder;
    private TvAdapter tvAdapter;
    private RatingBar mRatingBarSecond;
    private CustemiseCable channelPackagesBeen = null;
    RecyclerView recyclerViewTv, recyclerViewPremium, recyclerViewEquipment;
    public static Boolean EmailVerified = false;
    PaddingItemDecoration addItemDecoration;
    String mCableChannel, rating_deal;
    ;
    private Userdata userdata;
    public static ArrayList channel_list = new ArrayList();
    private String mCableChannelTitle, mCableDescription;
    public static ArrayList<HashMap<String, String>> comment_list = new ArrayList<HashMap<String, String>>();
    private ListView lv_comment;
    private Boolean mIsInternetPresent = false;
    LinearLayout mLinearLayoutStarRating;
    ArrayList<Boolean> arrayList2 = new ArrayList<>();
    ArrayList<Boolean> arrayList1 = new ArrayList<>();
    ArrayList<Boolean> arrayList3 = new ArrayList<>();

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
        mIsInternetPresent = NetworkUtil.isConnectingToInternet(this);
          /* Initialize application preferences */
        mSharedPreferences = getSharedPreferences(Constant.SHARED_PREF, 0);
        setActionbar();
        findID();
        if (mIsInternetPresent) {
            new HttpGetAsyncTask_Get_Data().execute();
            new HttpGetAsyncTask_comment().execute();
        } else {
            ShowDailog.Show_Alert_Dailog(CableDealDeatilActivity.this);
        }

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }


    /*@Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

        SharedPreferences sharedPreferences=getSharedPreferences(Constant.SHARED_PREF,Activity.MODE_PRIVATE);
        mSharedPreferences.getString("DealattributesID",null);
        mSharedPreferences.getString("EquipmentID",null);
        mSharedPreferences.getString("EquipmentPrice",null);
        mSharedPreferences.getString("PremiumID",null);

        }*/


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

    private void findID() {

        LayoutInflater inflater1 = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater1.inflate(R.layout.cablelistadpater, lv_comment, false);

        lv_comment = (ListView) findViewById(R.id.lv_comment);
        lv_comment.addHeaderView(header, null, false);
        mLinearLayoutStarRating = (LinearLayout) findViewById(R.id.ll_star_rating);
        textViewDealPrice = (TextView) findViewById(R.id.txt_price);
        mTextViewEffectivePrice = (TextView) findViewById(R.id.txtcable_effective_deal_price);
        textViewTitle = (TextView) findViewById(R.id.txt_deal_detail_title);
        textViewShortDetail = (TextView) findViewById(R.id.txt_short_description);
        textViewContract = (TextView) findViewById(R.id.txt_expiry_date);
        textViewEffectivePrice = (TextView) findViewById(R.id.txt_effective_deal_price);
        textViewTotalPrice = (TextView) findViewById(R.id.text_price_total);
        textViewAboutTheoffer = (TextView) findViewById(R.id.txt_about);
        imageViewIcon = (ImageView) findViewById(R.id.img_company);
        recyclerViewPremium = (RecyclerView) findViewById(R.id.horizontal_recycler_view_premimum);
        recyclerViewTv = (RecyclerView) findViewById(R.id.horizontal_recycler_view);
        recyclerViewEquipment = (RecyclerView) findViewById(R.id.horizontal_recycler_view_equipment);
        mRatingBarSecond = (RatingBar) findViewById(R.id.ratingBar2);
        LayerDrawable stars = (LayerDrawable) mRatingBarSecond.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.yellow_color), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1)
                .setColorFilter(getResources().getColor(R.color.ratingblankcolor),
                        PorterDuff.Mode.SRC_ATOP);
        mOrder = (Button) findViewById(R.id.txt_activate);
        mImageViewShare = (ImageView) findViewById(R.id.img_sharecable);
        mTextViewReview = (TextView) findViewById(R.id.txt_reviews);
        mLinearLayoutStarRating.setOnClickListener(this);

        mOrder.setOnClickListener(this);
        mImageViewShare.setOnClickListener(this);
        mTextViewReview.setOnClickListener(this);
//        mOrder.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int DealID=channelPackagesBeen.getDeals().getId();
//                Intent intent = new Intent(CableDealDeatilActivity.this,PlaceOrderAndPay.class);
//                intent.putExtra("dealid",DealID);
////                intent.putExtra("effectivePrice",value);
//                intent.putExtra("CustomActivity",true);
//                String mLatestEffectivePrice=  textViewEffectivePrice.getText().toString().replace("$","");
//                SharedPreferences sp = getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sp.edit();
//                editor.putString("value",mLatestEffectivePrice );
//                editor.commit();
//                startActivity(intent);
//            }
//
//        });

        ItemClickSupport.addTo(recyclerViewPremium).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                int Id = 0;//channelPackagesBeen.get(position).getId();
//                String DealId = Integer.toString(Id);
                // otherPrice= (String) channelPackagesBeen.getDeals().get(0).getChannelPackages().get(position).getPrice();

//                float a= (float) 40.00;
//                value=value+a;
//                textViewEffectivePrice.setText("$" + value);
                mCableChannelTitle = channelPackagesBeen.getDeals().getChannelPackages().get(position).getPackageName();
                //mCableDescription   = channelPackagesBeen.getDeals().getChannelPackages().get(position).getDescription();
                //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                size = channelPackagesBeen.getDeals().getChannelPackages().get(position).getChannelName().size();
                showChangeLangDialog(position);
                //showChangeDialog();


            }
        });
        ItemClickSupport.addTo(recyclerViewTv).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                int Id = 0;//channelPackagesBeen.get(position).getId();
//                String DealId = Integer.toString(Id);
                /*TvAdapter.VersionViewHolder.mCheckBoxCompare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (buttonView.isChecked()){
                            float a= (float) 60.00;
                            value=value+a;
                            textViewEffectivePrice.setText("$" + value);
                            Toast.makeText(CableDealDeatilActivity.this,"checked",Toast.LENGTH_SHORT).show();
                        }else {
                            float a= (float) 60.00;
                            value=value-a;
                            textViewEffectivePrice.setText("$" + value);
                        }
                    }
                });*/

                //Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();
                mCableDescription = channelPackagesBeen.getDeals().getDealAttributes().get(position).getDescription();
                mCableChannelTitle = (String) channelPackagesBeen.getDeals().getDealAttributes().get(position).getTitle();
                //showChangeDialog();


                       /* mCableChannel=channelPackagesBeen.getDeals().get(position).getChannelPackages().get(position).getChannelName();
                        mCableChannelTitle=channelPackagesBeen.getDeals().get(position).getChannelPackages().get(position).getPackageName();*/


            }


        });
        ItemClickSupport.addTo(recyclerViewEquipment).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                int Id = 0;//channelPackagesBeen.get(position).getId();
//                String DealId = Integer.toString(Id);
                /*otherPrice= (String) channelPackagesBeen.getDeals().get(0).getDealEquipments().get(position).getPrice();
                value1=Float.parseFloat(otherPrice);
                value=value+value1;
                textViewEffectivePrice.setText("$" + value);*/
                //  Toast.makeText(getApplicationContext(), "Hello", Toast.LENGTH_SHORT).show();

//                mCableDescription=channelPackagesBeen.getDeals().getDealEquipments().get(position).getD;
                mCableChannelTitle = channelPackagesBeen.getDeals().getDealEquipments().get(position).getName();
                showChangeDialog();

                       /* mCableChannel=channelPackagesBeen.getDeals().get(position).getChannelPackages().get(position).getChannelName();
                        mCableChannelTitle=channelPackagesBeen.getDeals().get(position).getChannelPackages().get(position).getPackageName();*/


            }
        });

    }


    private void showChangeDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_submit, null);
        dialogBuilder.setView(dialogView);
        final TextView edt = (TextView) dialogView.findViewById(R.id.customdailog);
        dialogBuilder.setTitle(mCableChannelTitle);
//        dialogBuilder.setMessage(mCellPhoneTitle);
        dialogBuilder.setMessage(mCableDescription);

        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }


    @Override
    public void onResume() {
        super.onResume();
//        isInternetPresent = NetworkUtil.isConnectingToInternet(CableDealDeatilActivity.this);
//        if (isInternetPresent) {
//            if (ServiceSave) {
//                ServiceSave = false;
//                new HttpGetAsyncTask_Get_Data().execute();
//            }
//        } else {
//            ShowDailog.Show_Alert_Dailog(CableDealDeatilActivity.this);
//        }
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
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
            case R.id.txt_activate:
                if (channelPackagesBeen.getDeals().getIsOrderAvailable()) {
                    if (!mSharedPreferences.getString("login_flag", "").equalsIgnoreCase("")) {
                        if (NetworkUtil.isConnectingToInternet(CableDealDeatilActivity.this)) {
                            if (EmailVerified) {
                                Intent intent = new Intent(getApplicationContext(), PlaceOrderAndPay.class);
                                intent.putExtra("dealid", channelPackagesBeen.getDeals().getId());
                                intent.putExtra("CustomActivity", true);
                                intent.putExtra("CableActivity", getResources().getString(R.string.cable));
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
            case R.id.img_sharecable: {

                displayPopupWindow(v, getIntent().getIntExtra("deal_position", 0));
                break;
            }
        }
    }


    private class HttpGetAsyncTask_Get_Data extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CableDealDeatilActivity.this);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
//                Intent intent=getIntent();
//                String id=intent.getStringExtra("DealId");
                // mDashboardJsonData = Jsondata.get_deal_dashboard("app_user_id=" + mSharedPreferences.getString(Constant.APP_USER_ID, "") + "&zip_code=" + Jsondata.encryptMsg(mSharedPreferences.getString(Constant.ZIPCODE, "")), getActivity());
                EmailVerified = Jsondata.EamilVerication(mSharedPreferences.getString(Constant.APP_USER_ID, ""), CableDealDeatilActivity.this);
                channelPackagesBeen = Jsondata.get_cable_deal(mSharedPreferences.getString("DealId", "0"), CableDealDeatilActivity.this);
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

            if (channelPackagesBeen.getSuccess()) {

                //setDetails(CableDealDeatilActivity.this);

                if (channelPackagesBeen.getDeals() == null) {
                    // LISTVIEW_DASHBOARD.setAdapter(new EmptyDashboardAdapter(getActivity()));
                    ShowDailog.Show_Alert_Login(CableDealDeatilActivity.this, getResources().getText(R.string.server_error).toString());
                } else {
                    if (channelPackagesBeen.getDeals().getChannelPackages().size() > 0) {
                        localyticstagEvent("CABLE");
                        setadapter(CableDealDeatilActivity.this);

                        //  setDetails(CableDealDeatilActivity.this);

                    } else {
                        // LISTVIEW_DASHBOARD.setAdapter(new EmptyDashboardAdapter(getActivity()));
                        ShowDailog.Show_Alert_Login(CableDealDeatilActivity.this, getResources().getText(R.string.server_error).toString());
                    }
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
                comment_list = Jsondata.get_comment(mSharedPreferences.getString("DealId", "0"), mSharedPreferences.getString(Constant.APP_USER_ID, ""), CableDealDeatilActivity.this);
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
            lv_comment.setAdapter(new CommentAdapter(CableDealDeatilActivity.this, comment_list));
        }
    }

    private void setadapter(Activity activity) {

        try {

            //   LISTVIEW_DASHBOARD.setAdapter(new DashboardAdapter(activity, mDashboardJsonData));
            LayoutInflater inflater1 = getLayoutInflater();

            if (channelPackagesBeen != null && channelPackagesBeen.getDeals().getChannelPackages().size() > 0) {
                if (!Headeradded) {
                    Headeradded = true;


                    try {
                        addItemDecoration = new PaddingItemDecoration(this);
                    } catch (Exception e) {
                    }
                    //mLinearLayoutBundleList = (LinearLayout) rootView.findViewById(R.id.ll_cellphone);
                    LinearLayoutManager layoutManager
                            = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewPremium.setLayoutManager(layoutManager);
                    for (int i = 0; channelPackagesBeen.getDeals().getChannelPackages().size() > i; i++) {
                        arrayList3.add(i, false);
                    }
                    recyclerViewPremium.addItemDecoration(addItemDecoration);

                    LinearLayoutManager layoutManager1
                            = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewTv.setLayoutManager(layoutManager1);
                    for (int i = 0; channelPackagesBeen.getDeals().getDealAttributes().size() > i; i++) {
                        arrayList2.add(i, false);
                    }
                    recyclerViewTv.addItemDecoration(addItemDecoration);

                    LinearLayoutManager layoutManager2
                            = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
                    recyclerViewEquipment.setLayoutManager(layoutManager2);
                    for (int i = 0; channelPackagesBeen.getDeals().getDealEquipments().size() > i; i++) {
                        arrayList1.add(i, false);
                    }
                    recyclerViewEquipment.addItemDecoration(addItemDecoration);
                }
                // mLinearLayoutBundleList.setVisibility(View.VISIBLE);
                recyclerViewPremium.setAdapter(new PremimumAdapter(activity, channelPackagesBeen, arrayList3));
                recyclerViewTv.setAdapter(new TvAdapter(activity, channelPackagesBeen, arrayList2));
                recyclerViewEquipment.setAdapter(new ChanelEquipmentAdapter(activity, channelPackagesBeen, arrayList1));


                if (channelPackagesBeen.getDeals().getContractPeriod() == 0) {
                    textViewContract.setText("Contract: None");
                } else {
                    textViewContract.setText("Contract: " + channelPackagesBeen.getDeals().getContractPeriod() + " months");
                }
                Picasso.with(this)
                        .load(channelPackagesBeen.getDeals().getDealImageUrl().toString()).placeholder(R.drawable.progress_animation)
                        .into(imageViewIcon);
                // mTextViewReview.setText(custemizeDealDetail.getDeals().getRatingCount().toString() + " reviews");
                textViewDealPrice.setText(channelPackagesBeen.getDeals().getDealPrice().toString());
                textViewTitle.setText(channelPackagesBeen.getDeals().getTitle().toString());
                textViewShortDetail.setText(channelPackagesBeen.getDeals().getShortDescription().toString());
                textViewAboutTheoffer.setText(channelPackagesBeen.getDeals().getDetailDescription().toString());
                if (channelPackagesBeen.getDeals().getIsOrderAvailable()) {
                    mOrder.setEnabled(true);
                    mOrder.setClickable(true);
                    mOrder.setBackground(getResources().getDrawable(R.drawable.activate_drawable
                    ));
                } else {
                    mOrder.setEnabled(false);
                    mOrder.setClickable(false);
                    mOrder.setBackground(getResources().getDrawable(R.drawable.disable_drawable));

                }
                if (!channelPackagesBeen.getDeals().getEffectivePrice().equalsIgnoreCase("0.00")) {
                    // mLinearLayoutEffective.setVisibility(View.VISIBLE);
                    effectivePrice = channelPackagesBeen.getDeals().getEffectivePrice();
                    value = Float.parseFloat(effectivePrice);

                    textViewEffectivePrice.setText("$" + value);
                    textViewEffectivePrice.setTypeface(null, Typeface.BOLD);
                }
                mTextViewReview.setText(channelPackagesBeen.getDeals().getRatingCount().toString() + " reviews");

            } else {

                Headeradded = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showChangeLangDialog(int pos) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog, null);
        dialogBuilder.setView(dialogView);
        final ListView listView = (ListView) dialogView.findViewById(R.id.listView);
        dialogBuilder.setTitle(mCableChannelTitle);
//        int Size = mCableChannel.size();
//
//        List<String> strings = new ArrayList<>();
//
//        for (int i = 0; Size > i; i++) {
//
//            strings.add(mCableChannel.get(i).getChannelName().get(i).getChannelName());
//
//        }


        for (int i = 0; i < size; i++) {
            mCableChannel = channelPackagesBeen.getDeals().getChannelPackages().get(pos).getChannelName().get(i).getChannelName().toString();


//        HashMap<String, String> meMap=new HashMap<String, String>();
            // meMap.put("channel_name",mCableChannel);
            channel_list.add(mCableChannel);
        }
        ArrayAdapter<ArrayList> adapter = new ArrayAdapter<ArrayList>(CableDealDeatilActivity.this, android.R.layout.simple_spinner_dropdown_item, channel_list);
        listView.setAdapter(adapter);


        dialogBuilder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //pass
            }
        });
        dialogBuilder.show();
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
            pDialog = new ProgressDialog(CableDealDeatilActivity.this);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response
                        = Jsondata.get_rating(mSharedPreferences.getString(Constant.APP_USER_ID, ""), "" + channelPackagesBeen.getDeals().getId(), rating_deal,
                        mEditTextCommentTitle.getText().toString().trim(), mEditTextCommentText.getText().toString().trim(), CableDealDeatilActivity.this);
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
                toast = Toast.makeText(CableDealDeatilActivity.this, "Thank you.",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                if (mIsInternetPresent) {

                    new CableDealDeatilActivity.HttpGetAsyncTask_comment().execute();
                } else {
                    ShowDailog.Show_Alert_Dailog(CableDealDeatilActivity.this);
                }
            } else if (response.equalsIgnoreCase("false")) {
            } else {
                ShowDailog.Show_Alert_Login(CableDealDeatilActivity.this, getResources().getText(R.string.server_error).toString());
            }
        }


    }

}