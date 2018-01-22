package com.spa.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer.Result;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.linkedin.platform.APIHelper;
import com.linkedin.platform.LISession;
import com.linkedin.platform.LISessionManager;
import com.linkedin.platform.errors.LIApiError;
import com.linkedin.platform.errors.LIAuthError;
import com.linkedin.platform.listeners.ApiListener;
import com.linkedin.platform.listeners.ApiResponse;
import com.linkedin.platform.listeners.AuthListener;
import com.linkedin.platform.utils.Scope;
import com.spa.fragment.DealListFragment;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.deallist.DealListItem;
import com.spa.servicedealz.R;
import com.spa.servicedealz.compare.CompareActivity;
import com.spa.servicedealz.drawer.DealListActivity;
import com.spa.servicedealz.ui.PlaceOrderAndPay;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Created by E0115Diwakar on 2/18/2015.
 */

/**
 * ************************************************************************************
 * FileName : Internet_Deal_Adapter.java
 * <p/>
 * Dependencies :Internet_Deal,Internet_deal_fragment
 * <p/>
 * Description : Show all the item in listview.
 * *************************************************************************************
 */
public class DealListAdapter extends BaseAdapter {
    private int mCount = 0;
    public static List<Integer> comapare_list = new ArrayList<>();
    Activity mActivity;
    private CallbackManager mCallbackManager;
    public SharedPreferences mSharedPreferences;
    public static AlertDialog.Builder alertbox = null;
    private DealListItem mDataList;

    public DealListAdapter(Activity activity, DealListItem deals) {
        this.mActivity = activity;
        this.mDataList = deals;
        try {
            mSharedPreferences = mActivity.getSharedPreferences(Constant.SHARED_PREF, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        comapare_list.clear();
    }

    @Override
    public int getCount() {
        if (mDataList != null) {
            return mDataList.getDeal().size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return mDataList.getDeal().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        RecordHolder holder = null;
        View row = convertView;
        try {

            LayoutInflater mInflater = (LayoutInflater)
                    mActivity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            row = mInflater.inflate(R.layout.internet_deal_adapter, null);

            holder = new RecordHolder();
            holder.mTextViewNoOfLine = (TextView) row.findViewById(R.id.txt_noofline);
            holder.mTextViewEffectivePrice = (TextView) row.findViewById(R.id.txt_effective_deal_price);
            holder.mLinearLayoutEffective = (LinearLayout) row.findViewById(R.id.ll_effective);
            holder.mImageViewBestDealImage = (ImageView) row.findViewById(R.id.img_bestdeal);
            holder.mImageViewVendorImage = (ImageView) row.findViewById(R.id.img_company);
            holder.mImageViewSocialSharing = (ImageView) row.findViewById(R.id.img_share);
            holder.mRatingDeal = (RatingBar) row.findViewById(R.id.ratingBar_deal);
            holder.mTextViewSavePrice = (TextView) row.findViewById(R.id.txt_save);
            holder.mButtonActivate = (Button) row.findViewById(R.id.txt_activate);
            holder.mTextViewExpiryDate = (TextView) row.findViewById(R.id.txt_expiry_date);
            holder.mTextViewReviews = (TextView) row.findViewById(R.id.txt_reviews);
            holder.mCheckBoxCompare = (CheckBox) row.findViewById(R.id.chk_comparedeal);
            holder.mTextViewTitle = (TextView) row.findViewById(R.id.txt_deal_title);
            holder.mTextViewDescription = (TextView) row.findViewById(R.id.txt_deal_description);
            holder.mTextViewPrice = (TextView) row.findViewById(R.id.txt_price);
            holder.mRelativeLayoutBeatDeal = (RelativeLayout) row.findViewById(R.id.rl_top);
            row.setTag(holder);
            int i = position;

            try {
                boolean b = mDataList.getDeal().get(i).getCustomisable();
                if (mSharedPreferences.getString("deal_id", "").equalsIgnoreCase("" + mDataList.getDeal().get(position).getId())) {
                    holder.mImageViewBestDealImage.setVisibility(View.VISIBLE);
                    holder.mImageViewBestDealImage.setImageResource(R.drawable.bestdeal);


                } else if (mSharedPreferences.getString("trending_deal_id", "").equalsIgnoreCase("" + mDataList.getDeal().get(position).getId())) {
                    if (b == true) {
                        holder.mImageViewBestDealImage.setVisibility(View.VISIBLE);
                        holder.mImageViewBestDealImage.setImageResource(R.drawable.custom_icon);

                    } else {
                        holder.mImageViewBestDealImage.setVisibility(View.VISIBLE);
                        holder.mImageViewBestDealImage.setImageResource(R.drawable.trending);
                    }

                } else if (b == true) {
                    holder.mImageViewBestDealImage.setVisibility(View.VISIBLE);
                    holder.mImageViewBestDealImage.setImageResource(R.drawable.custom_icon);

                } else {
                    holder.mImageViewBestDealImage.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            if (mDataList.getDeal().get(position).getService_category_id().equalsIgnoreCase(mActivity.getResources().getString(R.string.cellphone_id))) {
                holder.mTextViewNoOfLine.setText("Line :" + mDataList.getDeal().get(position).getNo_of_lines());
                holder.mTextViewNoOfLine.setVisibility(View.VISIBLE);
            } else {
                holder.mTextViewNoOfLine.setVisibility(View.GONE);
            }
            holder.mRelativeLayoutBeatDeal.setBackgroundResource(R.drawable.offer_level);
            holder.mTextViewTitle.setTextColor(Color.RED);
            holder.mTextViewDescription.setTextColor(Color.GRAY);
            holder.mTextViewExpiryDate.setTextColor(Color.GRAY);
            holder.mTextViewSavePrice.setTextColor(Color.GRAY);
            holder.mImageViewSocialSharing.setTag(position);
            holder.mCheckBoxCompare.setTag(position);
            holder.mButtonActivate.setTag(position);


            if (mDataList.getDeal().get(position).is_order_available()) {
                holder.mButtonActivate.setEnabled(true);
                holder.mButtonActivate.setClickable(true);
                holder.mButtonActivate.setBackground(mActivity.getResources().getDrawable(R.drawable.activate_drawable
                ));
            } else {
                holder.mButtonActivate.setEnabled(false);
                holder.mButtonActivate.setClickable(false);
                holder.mButtonActivate.setBackground(mActivity.getResources().getDrawable(R.drawable.disable_drawable));
            }
            holder.mImageViewSocialSharing.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    displayPopupWindow(v, pos);
                }
            });
            final RecordHolder finalHolder = holder;
            if (!Constant.compare.equalsIgnoreCase("yes")) {

                holder.mCheckBoxCompare.setVisibility(View.GONE);
            }
            if (Jsondata.compare_boolean.get(position)) {
                holder.mCheckBoxCompare.setChecked(true);
            } else {
                holder.mCheckBoxCompare.setChecked(false);
            }
            holder.mCheckBoxCompare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();

                    if (finalHolder.mCheckBoxCompare.isChecked()) {
                        mCount = mCount + 1;
                        comapare_list.add(pos);
                        Jsondata.compare_boolean.add(pos, true);
                    } else {
                        Jsondata.compare_boolean.add(pos, false);
                        mCount = mCount - 1;
                        comapare_list.remove(0);
                    }
                    if (mCount == 2) {
                        Collections.replaceAll(Jsondata.compare_boolean, true, false);
                        mActivity.startActivity(new Intent(mActivity, CompareActivity.class));
                    }
                }
            });
            holder.mButtonActivate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (!mSharedPreferences.getString("login_flag", "").equalsIgnoreCase("")) {

                        if (NetworkUtil.isConnectingToInternet(mActivity)) {
                            if (DealListFragment.EmailVerified) {
                                Intent intent = new Intent(mActivity, PlaceOrderAndPay.class);
                                intent.putExtra("dealid", mDataList.getDeal().get(pos).getId());
                                mActivity.startActivity(intent);
                            } else {
                                ShowDailog.Show_Alert_Login(mActivity, "To access this feature please Verify Email");
                            }
                            //  new HttpGetAsyncTaskSubscribeDeal(mActivity).execute(mDataList.getDeal().get(pos).getId().toString(), mDataList.getDeal().get(pos).getDeal_price().toString(), mDataList.getDeal().get(pos).getEffective_price().toString());

                        }

                    } else {
                        ShowDailog.Show_Alert_Login(mActivity, "To access this feature please Login first.", "Alert");
                    }
                }


            });

            if (mDataList.getDeal().get(position).getAverage_rating().toString().equalsIgnoreCase("")) {
                holder.mRatingDeal.setRating((float) 0.00);
            } else {
                float f1 = Float.parseFloat(mDataList.getDeal().get(position).getAverage_rating().toString());
                holder.mRatingDeal.setRating(f1);
            }
//            holder.mTextViewSavePrice.setText(mDataList.get(position).get("you_save_text").toString());
            if (mDataList.getDeal().get(position).getContract_period().toString().equalsIgnoreCase("0")) {
                holder.mTextViewExpiryDate.setText("Contract: None");
            } else {
                String[] departure = mDataList.getDeal().get(position).getEnd_date().toString().split(" ");
                holder.mTextViewExpiryDate.setText("Contract: " + mDataList.getDeal().get(position).getContract_period() + " months");
            }
            if (!mDataList.getDeal().get(position).getEffective_price().equalsIgnoreCase("0.00")) {
                holder.mLinearLayoutEffective.setVisibility(View.VISIBLE);
                //  holder.mTextViewPrice.setPaintFlags(holder.mTextViewPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                holder.mTextViewEffectivePrice.setText("$" + mDataList.getDeal().get(position).getEffective_price());
                holder.mTextViewEffectivePrice.setTypeface(null, Typeface.BOLD);
            } else {
                holder.mLinearLayoutEffective.setVisibility(View.GONE);
            }
            holder.mTextViewReviews.setText(mDataList.getDeal().get(position).getRating_count().toString() + " reviews");
            holder.mTextViewPrice.setText(mDataList.getDeal().get(position).getDeal_price().toString() + " /");
            holder.mTextViewTitle.setText(mDataList.getDeal().get(position).getTitle().toString());
            holder.mTextViewDescription.setText(mDataList.getDeal().get(position).getShort_description().toString());
            Picasso.with(mActivity)
                    .load(mDataList.getDeal().get(position).getDeal_image_url().toString()).placeholder(R.drawable.progress_animation)
                    .into(holder.mImageViewVendorImage);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        LayerDrawable stars = (LayerDrawable) holder.mRatingDeal.getProgressDrawable();
        stars.getDrawable(2).setColorFilter(mActivity.getResources().getColor(R.color.yellow_color), PorterDuff.Mode.SRC_ATOP);

        stars.getDrawable(1).setColorFilter(mActivity.getResources().getColor(R.color.ratingblankcolor), PorterDuff.Mode.SRC_ATOP);
     /*   stars.getDrawable(0).setColorFilter(mActivity.getResources().getColor(R.color.white_color), PorterDuff.Mode.SRC_ATOP);
*/
        Animation animation = AnimationUtils.loadAnimation(mActivity, R.anim.push_left_in);
        row.startAnimation(animation);
        return row;
    }


    public void displayPopupWindow(View anchorView, final int pos) {
        ImageView fb_share, whats_app_share, linkldin_share, twitter_share, img_close;
        LayoutInflater layoutInflater = LayoutInflater.from(mActivity);
        View promptView = layoutInflater.inflate(R.layout.popup_content, null);
        alertbox = new AlertDialog.Builder(mActivity, R.style.DialogAnimation);
        twitter_share = (ImageView) promptView.findViewById(R.id.twitter_share);
        fb_share = (ImageView) promptView.findViewById(R.id.fb_share);
        linkldin_share = (ImageView) promptView.findViewById(R.id.linkldin_share);
        whats_app_share = (ImageView) promptView.findViewById(R.id.whats_app_share);
        img_close = (ImageView) promptView.findViewById(R.id.img_close);
        // set prompts.xml to be the layout file of the alertdialog builder
        alertbox.setView(promptView);

        final AlertDialog alertD = alertbox.create();
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();

            }
        });
        twitter_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Constant.facebook = false;
                ((DealListActivity) mActivity).TwitterShare(mDataList.getDeal().get(pos).getTitle().toString() + "  $" + mDataList.getDeal().get(pos).getDeal_price().toString() + "/month", "", mDataList.getDeal().get(pos).getShare_url().toString());
                alertD.dismiss();
            }
        });
        whats_app_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Whatsup_Share(pos);
                alertD.dismiss();
            }
        });
        linkldin_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                linkedIn_share(pos);
                alertD.dismiss();
            }
        });
        fb_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.facebook = true;
                FaceBookShare(pos);
                alertD.dismiss();

            }
        });
        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertD.show();
    }

    public void Whatsup_Share(int pos) {

        boolean installed = appInstalledOrNot("com.whatsapp");
        if (installed) {
            Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
            whatsappIntent.setType("text/plain");
            whatsappIntent.setPackage("com.whatsapp");
            whatsappIntent.putExtra(Intent.EXTRA_TEXT, mDataList.getDeal().get(pos).getTitle().toString() + "  $" + mDataList.getDeal().get(pos).getDeal_price().toString() + "/month" + "\n" + mDataList.getDeal().get(pos).getShort_description().toString() + "\n" + mDataList.getDeal().get(pos).getDetail_description().toString() + "\n" + "https://play.google.com/apps/testing/com.spa.servicedeal");
            try {
                mActivity.startActivity(whatsappIntent);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(mActivity, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mActivity, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean appInstalledOrNot(String uri) {
        PackageManager pm = mActivity.getPackageManager();
        boolean app_installed;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    public void linkedIn_share(final int pos) {
        LISessionManager sessionManager = LISessionManager.getInstance(mActivity);
        LISession session = sessionManager.getSession();
        boolean accessTokenValid = session.isValid();
        if (accessTokenValid) {
            String url = "https://api.linkedin.com/v1/people/~/shares";

            String share = mDataList.getDeal().get(pos).getTitle().toString() + "  $" + mDataList.getDeal().get(pos).getDeal_price().toString() + "/month" + "\n" + mDataList.getDeal().get(pos).getShort_description().toString() + "\n" + mDataList.getDeal().get(pos).getDetail_description().toString() + "\n" + mDataList.getDeal().get(pos).getShare_url().toString();// + "\n" + mDataList.get(pos).get("url").toString();
            String payload = "{" +
                    "\"comment\":\"" + share + "\n" + "https://play.google.com/apps/testing/com.spa.servicedeal" +
                    "\"," +
                    "\"visibility\":{" +
                    "    \"code\":\"anyone\"}" +
                    "}";

            APIHelper apiHelper = APIHelper.getInstance(mActivity);
            APIHelper.postRequest(mActivity, url, payload, new ApiListener() {
                @Override
                public void onApiSuccess(ApiResponse apiResponse) {
                    // Success!
                    Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onApiError(LIApiError liApiError) {
                    // Error making POST request!
                    // Toast.makeText(mActivity, "" + liApiError, Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            LISessionManager.getInstance(mActivity).init(mActivity, buildScope(), new AuthListener() {
                @Override
                public void onAuthSuccess() {

                    LISessionManager sessionManager = LISessionManager.getInstance(mActivity);
                    LISession session = sessionManager.getSession();
                    boolean accessTokenValid = session.isValid();
                    String url = "https://api.linkedin.com/v1/people/~/shares";
                    String share = mDataList.getDeal().get(pos).getTitle().toString() + "  $" + mDataList.getDeal().get(pos).getDeal_price().toString() + "/month" + "\n" + mDataList.getDeal().get(pos).getShort_description().toString() + "\n" + mDataList.getDeal().get(pos).getDetail_description().toString();//+ "\n" + mDataList.get(pos).get("url").toString();
                    String payload = "{" +
                            "\"comment\":\"" + share + "\n" + "https://play.google.com/apps/testing/com.spa.servicedeal" +
                            "\"," +
                            "\"visibility\":{" +
                            "    \"code\":\"anyone\"}" +
                            "}";

                    APIHelper apiHelper = APIHelper.getInstance(mActivity);
                    APIHelper.postRequest(mActivity, url, payload, new ApiListener() {
                        @Override
                        public void onApiSuccess(ApiResponse apiResponse) {
                            // Success!
                            Toast.makeText(mActivity, "Success", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onApiError(LIApiError liApiError) {
                            // Error making POST request!
                            //Toast.makeText(mContext, "" + liApiError, Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onAuthError(LIAuthError error) {
                    // Toast.makeText(mActivity, "failed " + error.toString(), Toast.LENGTH_LONG).show();
                }
            }, true);
        }
    }

    private static Scope buildScope() {
        return Scope.build(Scope.R_BASICPROFILE, Scope.W_SHARE);
    }

    public void FaceBookShare(int pos) {
        String url = "http://www.sp-assurance.com/";//mDataList.get(pos).get("url").toString();
        try {
            if (!url.startsWith("http://") && !url.startsWith("https://")) {
                url = "http://" + url;
            }
            if (!FacebookSdk.isInitialized()) {
                FacebookSdk.sdkInitialize(mActivity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        com.facebook.AccessToken accessToken = com.facebook.AccessToken.getCurrentAccessToken();

        if (null == accessToken) {
        }
        mCallbackManager = CallbackManager.Factory.create();
        ShareDialog shareDialog = new ShareDialog(mActivity);
        // this part is optional
        shareDialog.registerCallback(mCallbackManager, new FacebookCallback<Result>() {

            @Override
            public void onSuccess(Result result) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onCancel() {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(FacebookException error) {
                // TODO Auto-generated method stub

            }


        });
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(mDataList.getDeal().get(pos).getTitle().toString() + "  $" + mDataList.getDeal().get(pos).getDeal_price().toString() + "/month")
                    .setContentDescription(mDataList.getDeal().get(pos).getShort_description().toString())
                    .setContentUrl(
                            Uri.parse("https://play.google.com/apps/testing/com.spa.servicedeal"))
                    .setImageUrl(Uri.parse(mDataList.getDeal().get(pos).getShare_url().toString()))
                    .build();

            ShareDialog.show(mActivity, linkContent);
        }
    }


    class RecordHolder {
        private Button mButtonActivate;
        private TextView mTextViewTitle, mTextViewDescription, mTextViewPrice, mTextViewReviews, mTextViewExpiryDate, mTextViewNoOfLine,
                mTextViewSavePrice, mTextViewEffectivePrice;
        private ImageView mImageViewVendorImage, mImageViewSocialSharing, mImageViewBestDealImage;
        private RatingBar mRatingDeal;
        private CheckBox mCheckBoxCompare;
        private LinearLayout mLinearLayoutEffective;
        private RelativeLayout mRelativeLayoutBeatDeal;
    }
}

