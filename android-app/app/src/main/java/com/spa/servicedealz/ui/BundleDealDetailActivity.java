package com.spa.servicedealz.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.localytics.android.Localytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.spa.adapter.CommentAdapter;
import com.spa.fragment.DealListFragment;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.BundleDetailResponseModel;
import com.spa.model.profile.Userdata;
import com.spa.servicedealz.R;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Prakash on 8/26/2016.
 */
public class BundleDealDetailActivity extends ShareBaseActivtiy implements View.OnClickListener {
    private Toolbar mToolbar;
    private float mRating;
    private EditText mEditTextCommentText, mEditTextCommentTitle;
    private Boolean mIsInternetPresent = false;
    private TextView mTextViewPrice, mTextViewDealTitle, mTextViewDealDetailDesc, mTextViewAbout, mTextViewReview,
            mTextViewExpiryDate, txt_save, mChannelsTitle, mChannelsDetail, mTextViewEquipment,
            mTextViewPremuimChannel, mTextViewCountry, mTextViewFeatures, mTextViewEffectivePrice, mTextViewInstallation;
    private Button mButtonCancel, mButtonOk, mButtonActivate, mButtonViewMore, mButtonPremuimViewMore, mButtonCountryViewMore, mButtonFeaturesViewMore;
    private ImageView mImageViewShare, mImageViewUser, mImageViewCompany;
    private LinearLayout mLinearLayoutStarRating, mLinearLayoutEffective;

    private RatingBar mRatingBarSecond;
    private RelativeLayout mRealtiveChannel, mRelativeLayoutequipment, mRelativeLayoutOffer, mRelativeLayoutPremuimChannels, mRelativeLayoutCountry, mRelativeLayoutFeatures;

    private String rating_deal;
    private ListView lv_comment;
    private Intent in;
    public static ArrayList<HashMap<String, String>> comment_list = new ArrayList<HashMap<String, String>>();
    public static SharedPreferences mSharedPreferences;
    private Userdata userdata;
    private BundleDetailResponseModel bundleDetailResponseModel;
    private String[] ChannelListArray, PremuimChannelListArray, CountryListArray, FeaturesListArray;
    private static List<String> ChannelListsorted;
    private static List<String> PremuimChannelListsorted;
    private List<String> CountryListsorted;
    private List<String> FeaturesListsorted;

    int mIntentPos;

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
        in = getIntent();
        setActionbar();
        setMapFindId();

        setClickListener();
        mIntentPos = in.getIntExtra("value", 0);

    }


    /*method to set clicklistener */
    private void setClickListener() {
        mLinearLayoutStarRating.setOnClickListener(this);
        mImageViewShare.setOnClickListener(this);
//        mButtonActivate.setEnabled(false);
        mButtonActivate.setOnClickListener(this);
        mButtonViewMore.setOnClickListener(this);
        mButtonPremuimViewMore.setOnClickListener(this);
        mButtonCountryViewMore.setOnClickListener(this);
        mButtonFeaturesViewMore.setOnClickListener(this);
    }

    /*Method to set  data in view Component*/
    private void setDataInViewComponent() {

        try {
            if (bundleDetailResponseModel.getDeals().getAverage_rating().toString().equalsIgnoreCase("")) {
                mRatingBarSecond.setRating((float) 0.00);
            } else {
                float f1 = Float.parseFloat(bundleDetailResponseModel.getDeals().getAverage_rating().toString());
                mRatingBarSecond.setRating(f1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


//        txt_save.setText(Internet_deal_fragment.DEAL_LIST.get(in.getIntExtra("deal_position", 0)).get("you_save_text").toString());
        /*if (DealListFragment.dealListItem.getDeal().get(in.getIntExtra("deal_position", 0)).getEnd_date().toString().equalsIgnoreCase("")) {
            mTextViewExpiryDate.setText("");
        } else {
            String[] departure = DealListFragment.dealListItem.getDeal().get(in.getIntExtra("deal_position", 0)).getEnd_date().toString().split(" ");
            mTextViewExpiryDate.setText("Expiry Date: " + departure[0]);
        }*/

        try {
            if (bundleDetailResponseModel.getDeals().getContract_period() == 0) {
                mTextViewExpiryDate.setText("Contract: None");
            } else {
                mTextViewExpiryDate.setText("Contract: " + bundleDetailResponseModel.getDeals().getContract_period() + " months");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        Picasso.with(this)
                .load(bundleDetailResponseModel.getDeals().getDeal_image_url().toString()).placeholder(R.drawable.progress_animation)
                .into(mImageViewCompany);
        // ImageLoader.getInstance().displayImage(DealListFragment.dealListItem.getDeal().get(in.getIntExtra("deal_position", 0)).getDeal_image_url().toString(), mImageViewCompany);
        mTextViewReview.setText(bundleDetailResponseModel.getDeals().getRating_count().toString() + " reviews");
        mTextViewPrice.setText(bundleDetailResponseModel.getDeals().getDeal_price().toString() + " /");
        mTextViewDealTitle.setText(bundleDetailResponseModel.getDeals().getTitle().toString());
        mTextViewDealDetailDesc.setText(bundleDetailResponseModel.getDeals().getShort_description().toString());
        mTextViewAbout.setText(bundleDetailResponseModel.getDeals().getDetail_description().toString());
        if (!bundleDetailResponseModel.getDeals().getEffective_price().equalsIgnoreCase("0.00")) {
            mLinearLayoutEffective.setVisibility(View.VISIBLE);
            //  mTextViewPrice.setPaintFlags(mTextViewPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            mTextViewEffectivePrice.setText("$" + bundleDetailResponseModel.getDeals().getEffective_price());
            mTextViewEffectivePrice.setTypeface(null, Typeface.BOLD);
        } else {
            mLinearLayoutEffective.setVisibility(View.GONE);
        }
        if (bundleDetailResponseModel.getDeals().getDeal_equipments() != null && bundleDetailResponseModel.getDeals().getDeal_equipments().size() > 0) {
            try {
//                ListView listView = (ListView) findViewById(R.id.lv_equpment);
                TableLayout tableLayout = (TableLayout) findViewById(R.id.tbl_equpment);
                for (int i = 0; i < bundleDetailResponseModel.getDeals().getDeal_equipments().size(); i++) {

                    TableRow row = new TableRow(this);
                    TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                    row.setLayoutParams(lp);
                    LayoutInflater mInflater = (LayoutInflater)
                            getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                    View view = mInflater.inflate(R.layout.equipment_adapter, null);

                    TextView mTextViewAdditionalEquipmentTitle = (TextView) view.findViewById(R.id.txt_equipment_model);
                    TextView mTextViewEquipmentPrice = (TextView) view.findViewById(R.id.txt_equipment_price);
                    TextView mTextViewEquipmentMemory = (TextView) view.findViewById(R.id.txt_equipment_memory);
                    TextView mTextViewMemory = (TextView) view.findViewById(R.id.txt_memory);

                    String cat_id = bundleDetailResponseModel.getDeals().getService_category_name();
                  /*  if (!cat_id.equalsIgnoreCase(getResources().getString(R.string.cell_phone))) {
                        mTextViewMemory.setText("Installation");
                        mTextViewEquipmentMemory.setText(mDataList.get(i).getInstallation().toString());
                    } else {

                    }*/

                    //Pending
//                    mTextViewMemory.setText(bundleDetailResponseModel.getDeals().getDeal_equipments().get(i).getName());
                    mTextViewEquipmentMemory.setText(bundleDetailResponseModel.getDeals().getDeal_equipments().get(i).getOffer().toString());
//                    mTextViewAdditionalEquipmentTitle.setText(bundleDetailResponseModel.getDeals().getDeal_equipments().get(i).getModel().toString());
//                    mTextViewEquipmentPrice.setText("$" + bundleDetailResponseModel.getDeals().getDeal_equipments().get(i).getPrice().toString());
                    row.addView(view);
                    tableLayout.addView(row, i);
                }
//                listView.setAdapter(new EqupmentAdapter(DealDetailActivity.this, DealListFragment.dealListItem.getDeal().get(in.getIntExtra("deal_position", 0)).getDeal_equipments(), DealListFragment.dealListItem.getDeal().get(in.getIntExtra("deal_position", 0)).getService_category_name()));

//                setListViewHeightBasedOnItems1(listView);

                // mTextViewInstallation.setText(DealListFragment.dealListItem.getDeal().get(in.getIntExtra("deal_position", 0)).getDeal_equipments().get(0).getInstallation().toString());
                //mTextViewEquipment.setText(DealListFragment.dealListItem.getDeal().get(in.getIntExtra("deal_position", 0)).getDeal_equipments().get(0).getModel().toString());

            } catch (Exception e) {

            }

        } else {
            mTextViewInstallation = (TextView) findViewById(R.id.txtinstallation);
            mTextViewInstallation.setVisibility(View.GONE);
        }
        if (bundleDetailResponseModel.getDeals().getDeal_additional_offers() != null && bundleDetailResponseModel.getDeals().getDeal_additional_offers().size() > 0) {
            ListView listView = (ListView) findViewById(R.id.lv_offer);
            //Pending
//            listView.setAdapter(new OfferAdapter(BundleDealDetailActivity.this, bundleDetailResponseModel.getDeals().getDeal_additional_offers()));
            setListViewHeightBasedOnItems(listView);
        } else {
            mRelativeLayoutOffer.setVisibility(View.GONE);

        }

        mIsInternetPresent = NetworkUtil.isConnectingToInternet(this);
        if (mIsInternetPresent) {
            new HttpGetAsyncTask_get().execute();
            new HttpGetAsyncTask_comment().execute();
        } else {
            ShowDailog.Show_Alert_Dailog(this);
        }
        mChannelsTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BundleDealDetailActivity.this, ChannelListActivity.class);
                intent.putExtra("Dealid", "" + bundleDetailResponseModel.getDeals().getId());
                startActivity(intent);
            }
        });

        mRelativeLayoutFeatures.setVisibility(View.GONE);
        mRelativeLayoutCountry.setVisibility(View.GONE);

    }

    public void makeTextViewResizable(final TextView tv, final int maxLine, final String expandText, final boolean viewMore, final String clickid) {

        if (tv.getTag() == null) {
            tv.setTag(tv.getText());
        }
        ViewTreeObserver vto = tv.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            @SuppressWarnings("deprecation")
            @Override
            public void onGlobalLayout() {

                ViewTreeObserver obs = tv.getViewTreeObserver();
                obs.removeGlobalOnLayoutListener(this);
              /*  if (maxLine == 0) {
                    int lineEndIndex = tv.getLayout().getLineEnd(0);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore, clickid), TextView.BufferType.SPANNABLE);
                }*/
                if (maxLine > 0 && tv.getLineCount() >= maxLine) {
                    int lineEndIndex = tv.getLayout().getLineEnd(maxLine - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex - expandText.length() + 1) + "... " +
                            "" + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, maxLine, expandText,
                                    viewMore, clickid), TextView.BufferType.SPANNABLE);
                } /*else {
                    int lineEndIndex = tv.getLayout().getLineEnd(tv.getLayout().getLineCount() - 1);
                    String text = tv.getText().subSequence(0, lineEndIndex) + " " + expandText;
                    tv.setText(text);
                    tv.setMovementMethod(LinkMovementMethod.getInstance());
                    tv.setText(
                            addClickablePartTextViewResizable(Html.fromHtml(tv.getText().toString()), tv, lineEndIndex, expandText,
                                    viewMore, clickid), TextView.BufferType.SPANNABLE);
                }*/
            }
        });

    }


    private SpannableStringBuilder addClickablePartTextViewResizable(final Spanned strSpanned, final TextView tv,
                                                                     final int maxLine, final String spanableText, final boolean viewMore, final String clickid) {
        String str = strSpanned.toString();
        SpannableStringBuilder ssb = new SpannableStringBuilder(strSpanned);

        if (str.contains(spanableText)) {
            ssb.setSpan(new ClickableSpan() {

                @Override
                public void onClick(View widget) {

                    if (viewMore) {
                        // tv.setLayoutParams(tv.getLayoutParams());
                        //  tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        //  tv.invalidate();
                        if (clickid.equalsIgnoreCase("channel")) {
//                            popupWindowChannelList("General", ChannelListsorted);
                            Intent intent = new Intent(BundleDealDetailActivity.this, ChannelListActivity.class);
                            intent.putExtra("Dealid", "" + bundleDetailResponseModel.getDeals().getId());
                            startActivity(intent);
                        } else if (clickid.equalsIgnoreCase("premuim")) {
                            popupWindowChannelList("Premium", PremuimChannelListsorted);
                        } else if (clickid.equalsIgnoreCase("features")) {
                            popupWindowChannelList("Features", FeaturesListsorted);
                        } else if (clickid.equalsIgnoreCase("country")) {
                            popupWindowChannelList("Countries", CountryListsorted);
                        }
                    }/* else {
                        //makeTextViewResizable(tv, -1, "View Less", false);
                        tv.setLayoutParams(tv.getLayoutParams());
                        tv.setText(tv.getTag().toString(), TextView.BufferType.SPANNABLE);
                        tv.invalidate();
                        //makeTextViewResizable(tv, 3, "View More", true);
                    }*/

                }
            }, str.indexOf(spanableText), str.indexOf(spanableText) + spanableText.length(), 0);

        }
        return ssb;

    }

    /*Method set to find id view component*/
    private void setMapFindId() {
        LayoutInflater inflater1 = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater1.inflate(R.layout.deal_detail, lv_comment, false);

        lv_comment = (ListView) findViewById(R.id.lv_comment);
        lv_comment.addHeaderView(header, null, false);
        mRelativeLayoutCountry = (RelativeLayout) findViewById(R.id.rl_country);
        mRelativeLayoutFeatures = (RelativeLayout) findViewById(R.id.rl_features);
        mRelativeLayoutOffer = (RelativeLayout) findViewById(R.id.rl_offer);
        mRelativeLayoutequipment = (RelativeLayout) findViewById(R.id.installaton);
        mRealtiveChannel = (RelativeLayout) findViewById(R.id.rl_channel);
        mRelativeLayoutPremuimChannels = (RelativeLayout) findViewById(R.id.rl_premuim_channel);

        mButtonViewMore = (Button) findViewById(R.id.btn_viewmore);
        mButtonPremuimViewMore = (Button) findViewById(R.id.btn_premuim_viewmore);
        mButtonCountryViewMore = (Button) findViewById(R.id.btn_country_viewmore);
        mButtonFeaturesViewMore = (Button) findViewById(R.id.btn_features_viewmore);

        mTextViewCountry = (TextView) findViewById(R.id.txt_country_detail);
        mTextViewFeatures = (TextView) findViewById(R.id.txt_features_detail);

//        mTextViewInstallation = (TextView) findViewById(R.id.txt_installation);
//        mTextViewEquipment = (TextView) findViewById(R.id.txt_equipments);

        mTextViewPremuimChannel = (TextView) findViewById(R.id.txt_premuim_channel_detail);
        mLinearLayoutStarRating = (LinearLayout) findViewById(R.id.ll_star_rating);

        mTextViewEffectivePrice = (TextView) findViewById(R.id.txt_effective_deal_price);
        mLinearLayoutEffective = (LinearLayout) findViewById(R.id.ll_effective);
        mRatingBarSecond = (RatingBar) findViewById(R.id.ratingBar2);
        LayerDrawable stars = (LayerDrawable) mRatingBarSecond.getProgressDrawable();

        stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.yellow_color), PorterDuff.Mode.SRC_ATOP);
        stars.getDrawable(1)
                .setColorFilter(getResources().getColor(R.color.ratingblankcolor),
                        PorterDuff.Mode.SRC_ATOP);
        LayoutInflater inflater_footer = getLayoutInflater();


        mButtonActivate = (Button) findViewById(R.id.txt_activate);
        mChannelsTitle = (TextView) findViewById(R.id.txt_channel_title);
        mChannelsTitle.setVisibility(View.GONE);
        mChannelsDetail = (TextView) findViewById(R.id.txt_channel_detail);
        mTextViewPrice = (TextView) findViewById(R.id.txt_price);
        mTextViewDealTitle = (TextView) findViewById(R.id.txt_deal_detail_title);
        mTextViewDealDetailDesc = (TextView) findViewById(R.id.txt_short_description);
        mTextViewAbout = (TextView) findViewById(R.id.txt_about);
        mTextViewReview = (TextView) findViewById(R.id.txt_reviews);
        mTextViewExpiryDate = (TextView) findViewById(R.id.txt_expiry_date);
        txt_save = (TextView) findViewById(R.id.txt_save);
        mImageViewShare = (ImageView) findViewById(R.id.img_share);
        mImageViewCompany = (ImageView) findViewById(R.id.img_company);


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
                        = Jsondata.get_comment(mSharedPreferences.getString("DealId", "0"), mSharedPreferences.getString(Constant.APP_USER_ID, ""), BundleDealDetailActivity.this);
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
            lv_comment.setAdapter(new CommentAdapter(BundleDealDetailActivity.this, comment_list));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("DealDetailActivity");
       mIsInternetPresent = NetworkUtil.isConnectingToInternet(this);
        if (mIsInternetPresent) {
            new HttpGetAsyncTask_get().execute();
        } else {
            ShowDailog.Show_Alert_Dailog(this);
        }
    }

    /*Get profile image in background*/
    class HttpGetAsyncTask_get extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            try {
                userdata = Jsondata.get_general_info(mSharedPreferences.getString(Constant.APP_USER_ID, ""), BundleDealDetailActivity.this);
                bundleDetailResponseModel = Jsondata.GetBundleDealDetail(mSharedPreferences.getString("DealId", ""), BundleDealDetailActivity.this);

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
            setDataInViewComponent();
            if (DealListFragment.dealListItem.getBundle_deals().get(mIntentPos).is_order_available()) {
                mButtonActivate.setEnabled(true);
                mButtonActivate.setClickable(true);
                mButtonActivate.setBackground(getResources().getDrawable(R.drawable.activate_drawable
                ));
            } else {
                mButtonActivate.setEnabled(false);
                mButtonActivate.setClickable(false);
                mButtonActivate.setBackground(getResources().getDrawable(R.drawable.disable_drawable));
            }

        }
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
            case R.id.txt_activate:
                if (DealListFragment.dealListItem.getBundle_deals().get(in.getIntExtra("deal_position", 0)).is_order_available()) {
                    if (!mSharedPreferences.getString("login_flag", "").equalsIgnoreCase("")) {
                        if (NetworkUtil.isConnectingToInternet(BundleDealDetailActivity.this)) {
                            if (DealListFragment.EmailVerified) {
                                Intent intent = new Intent(getApplicationContext(), PlaceOrderAndPay.class);
                                intent.putExtra("dealid", bundleDetailResponseModel.getDeals().getId());
                                startActivity(intent);
//                            new HttpGetAsyncTaskSubscribeDeal(BundleDealDetailActivity.this).execute(bundleDetailResponseModel.getDeals().getId().toString(), DealListFragment.dealListItem.getDeal().get(in.getIntExtra("deal_position", 0)).getDeal_price().toString(), DealListFragment.dealListItem.getDeal().get(in.getIntExtra("deal_position", 0)).getEffective_price().toString());

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
            case R.id.btn_viewmore: {
                popupWindowChannelList("Channels List", ChannelListsorted);
                break;

            }
            case R.id.btn_premuim_viewmore: {
                popupWindowChannelList("Premuim Channels List", PremuimChannelListsorted);
                break;

            }
            case R.id.btn_country_viewmore: {
                popupWindowChannelList("Country List", CountryListsorted);
                break;

            }
            case R.id.btn_features_viewmore: {
                popupWindowChannelList("Features List", FeaturesListsorted);
                break;

            }

        }
    }

    private void popupWindowChannelList(String title, List<String> channelListsorted) {
        LayoutInflater layoutInflater = LayoutInflater.from(BundleDealDetailActivity.this);

        View promptView = layoutInflater.inflate(R.layout.channellistpopup, null);
        alertbox = new AlertDialog.Builder(this, R.style.DialogAnimation);
        ListView listView = (ListView) promptView.findViewById(R.id.listView1);
        TextView Title = (TextView) promptView.findViewById(R.id.txt_title);
        Button button = (Button) promptView.findViewById(R.id.btn_ok);
        Title.setText(title);
//        listView.setAdapter(new ChannelListAdapter(context, sorted));
        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, channelListsorted));
        // set prompts.xml to be the layout file of the alertdialog builder
        alertbox.setView(promptView);

        final AlertDialog alertD = alertbox.create();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertD.dismiss();
            }
        });
        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertD.show();

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
        // LayerDrawable stars = (LayerDrawable) ratingbar1.getProgressDrawable();
        // stars.getDrawable(2).setColorFilter(getResources().getColor(R.color.yellow_color), PorterDuff.Mode.SRC_ATOP);
        final AlertDialog alertD = alertDialogBuilder.create();
        //======================================mRating==============================================

              /*  for (int i = 0; i < stars.length; i++) {
                    starViews[i] = (ImageView) promptView.findViewById(stars[i]);
                    starViews[i].setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            for (int i = 0; i < 5; i++) {
                                if (v.getId() == stars[i]) {
                                    mRating = i + 1;
                                    break;
                                }
                            }
                            setRatingImage();
                        }
                    });
                }*/
//==================================================================================================
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
            pDialog = new ProgressDialog(BundleDealDetailActivity.this);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response
                        = Jsondata.get_rating(mSharedPreferences.getString(Constant.APP_USER_ID, ""), "" + bundleDetailResponseModel.getDeals().getId(), rating_deal,
                        mEditTextCommentTitle.getText().toString().trim(), mEditTextCommentText.getText().toString().trim(), BundleDealDetailActivity.this);
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
                toast = Toast.makeText(BundleDealDetailActivity.this, "Thank you.",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();

                if (mIsInternetPresent) {

                    new HttpGetAsyncTask_comment().execute();
                } else {
                    ShowDailog.Show_Alert_Dailog(BundleDealDetailActivity.this);
                }
            } else if (response.equalsIgnoreCase("false")) {
            } else {
                ShowDailog.Show_Alert_Login(BundleDealDetailActivity.this, getResources().getText(R.string.server_error).toString());
            }
        }


    }

    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                params.height = (totalItemsHeight + totalDividersHeight) / 2;
                // Do something for lollipop and above versions
            } else {
                params.height = (totalItemsHeight + totalDividersHeight);
                // do something for phones running an SDK before lollipop
            }
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems1(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            int currentapiVersion = android.os.Build.VERSION.SDK_INT;
            if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                params.height = (totalItemsHeight + totalDividersHeight) / 2 + 48;
                // Do something for lollipop and above versions
            } else {
                params.height = (totalItemsHeight + totalDividersHeight);
                // do something for phones running an SDK before lollipop
            }

            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
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
