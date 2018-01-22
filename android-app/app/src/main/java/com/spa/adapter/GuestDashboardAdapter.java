package com.spa.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.LoginActivity;
import com.spa.fragment.ShowDailog;
import com.spa.model.guestdashboard.GuestDashboard;
import com.squareup.picasso.Picasso;

/**
 * Created by Diwakar on 4/28/2016.
 */
public class GuestDashboardAdapter extends BaseAdapter {
    private Activity mActivty;
  //  private int[] mImageArray = {R.drawable.internet_deal, R.drawable.cellphone, R.drawable.gas_deal, R.drawable.electricity_deal, R.drawable.homesecurity_deal, R.drawable.cable_deal, R.drawable.telephone, R.drawable.bundle};
    private GuestDashboard mData;
    public static AlertDialog.Builder alertbox = null;
    public static String category_click = "";

    public GuestDashboardAdapter(Activity activity, GuestDashboard goc) {
        this.mActivty = activity;
        this.mData = goc;
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisc(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(mActivty)
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
    }

    @Override
    public int getCount() {
        return mData.getDashboardData().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        View row = convertView;
        try {
            LayoutInflater mInflater = (LayoutInflater)
                    mActivty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            row = mInflater.inflate(R.layout.guestdashboardadapter, null);
            holder = new ViewHolder();
            holder.mLinearLayoutTrendingEffective = (LinearLayout) row.findViewById(R.id.ll_trending_effective_price);

            holder.mTextViewTrendingEffectiveDealPrice = (TextView) row.findViewById(R.id.trending_effective_deal_price);
            holder.mTextViewCategoryName = (TextView) row.findViewById(R.id.txt_category_name);
            holder.mTextViewPreferredDealPrice = (TextView) row.findViewById(R.id.preferred_price);
            holder.mTextViewPreferredShowDetail = (TextView) row.findViewById(R.id.txt_preferred_viewmore);
            holder.mTextViewPreferredShortDesc = (TextView) row.findViewById(R.id.txt_preferred_provider_short_desc);
            holder.mTextViewVendorName = (TextView) row.findViewById(R.id.tv_provider);
            holder.mTextViewTrendingExp = (TextView) row.findViewById(R.id.txt_trend_expiry_date);
            holder.mTextViewAddService = (TextView) row.findViewById(R.id.txt_add_services);
            holder.mImageViewPreferredDealImage = (ImageView) row.findViewById(R.id.img_best2);
            holder.mImageViewImage = (ImageView) row.findViewById(R.id.img_trend_exp);
            holder.mImageViewPreferredService = (ImageView) row.findViewById(R.id.img_preferred_provider0);
            // holder.mRelativeLayoutPreferredDealDetail = (RelativeLayout) row.findViewById(R.id.rl_preferred_provider_deal_detail);
            row.setTag(holder);
            holder.mTextViewCategoryName.setText(mData.getDashboardData().get(position).getServiceCategoryName().toString());
            String service_vender = mData.getDashboardData().get(position).getServiceCategoryName().toString();
            if (mData.getDashboardData().size() > 0) {
                if (!mData.getDashboardData().get(position).getTrendingDeal().getEffective_price().equalsIgnoreCase("0")) {
                    holder.mLinearLayoutTrendingEffective.setVisibility(View.VISIBLE);
                   // holder.mTextViewPreferredDealPrice.setPaintFlags(holder.mTextViewPreferredDealPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                  //  holder.mTextViewPreferredDealPrice.setTypeface(null, Typeface.NORMAL);
                    holder.mTextViewTrendingEffectiveDealPrice.setText("$" +mData.getDashboardData().get(position).getTrendingDeal().getEffective_price());
                    holder.mTextViewTrendingEffectiveDealPrice.setTypeface(null, Typeface.BOLD);
                } else {
                    holder.mLinearLayoutTrendingEffective.setVisibility(View.GONE);
                }
                String[] departure1 = mData.getDashboardData().get(position).getTrendingDeal().getEndDate().toString().split(" ");
                holder.mTextViewPreferredDealPrice.setText("$" + mData.getDashboardData().get(position).getTrendingDeal().getDealPrice());
                holder.mTextViewPreferredShortDesc.setText("" + mData.getDashboardData().get(position).getTrendingDeal().getShortDescription());
                ImageLoader.getInstance().displayImage(mData.getDashboardData().get(position).getTrendingDeal().getDealImageUrl(), holder.mImageViewPreferredService);
                Picasso.with(mActivty)
                        .load(mData.getDashboardData().get(position).getTrendingDeal().getDealImageUrl()).placeholder(R.drawable.progress_animation)
                        .into(holder.mImageViewPreferredService);
                if (mData.getDashboardData().get(position).getTrendingDeal().getEndDate().toString().equalsIgnoreCase("")) {
                    holder.mTextViewTrendingExp.setText("");
                    holder.mImageViewImage.setVisibility(View.GONE);
                } else {
                    holder.mTextViewTrendingExp.setText("Expiry Date: " + departure1[0]);
                    holder.mImageViewImage.setVisibility(View.VISIBLE);
                }
            } else {
                //    holder.mRelativeLayoutPreferredDeal.setVisibility(View.GONE);
                //   holder.mRelativeLayoutPreferredDealDetail.setVisibility(View.GONE);

            }
            holder.mTextViewAddService.setTag(position);
            holder.mTextViewAddService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = (int) view.getTag();
                    Alert_Login(mActivty, "To access this feature please Login first.", "Alert", pos);

                }
            });
            holder.mTextViewPreferredShowDetail.setTag(position);

            holder.mTextViewPreferredShowDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int vv = (int) v.getTag();
                    ShowDailog.CustemDailog(mActivty, mData.getDashboardData().get(vv).getTrendingDeal().getDetailDescription(), "Deal Detail");
                }
            });
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


        Animation animation = AnimationUtils.loadAnimation(mActivty, R.anim.push_left_in);
        row.startAnimation(animation);

        return row;
    }

    public String Alert_Login(final Activity activity, String message, String title, final int pos) {
        try {


            alertbox = new AlertDialog.Builder(
                    activity, R.style.DialogAnimation);
            //   } else alertbox = new AlertDialog.Builder(
            //        activity);

            alertbox.setTitle(title);
            alertbox.setMessage(message + "                              ");
            alertbox.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {
                            category_click = mData.getDashboardData().get(pos).getServiceCategoryName();
                            Intent PREFERENCES = new Intent(activity, LoginActivity.class);

                            PREFERENCES.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(PREFERENCES);
                            activity.finish();
                        }
                    });
            alertbox.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

            alertbox.setCancelable(false);
            final AlertDialog alertD = alertbox.create();
            alertD.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    int titleDividerId = activity.getResources()
                            .getIdentifier("titleDivider", "id", "android");

                    View titleDivider = alertD.findViewById(titleDividerId);
                    if (titleDivider != null) {
                        titleDivider.setBackgroundColor(activity.getResources()
                                .getColor(R.color.devider));
                    }
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            } else {
                alertD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
            alertD.show();
        } catch (Exception e) {
        }
        return null;
    }

    class ViewHolder {
        private TextView mTextViewAddService, mTextViewVendorName, mTextViewEndDate, mTextViewCategoryName, mTextViewShowDetail,
                mTextViewPreferredShowDetail, mTextViewExpiryDate, mTextViewShortDesc, mTextViewPreferredShortDesc, mTextViewTrendingExp,
                mTextViewCurrentPlanPrice, mTextViewDealPrice, mTextViewPreferredDealPrice, mTextViewDealSavePrice, mTextViewTrendingEffectiveDealPrice;
        ImageView mImageViewImage, mImageViewDealImage, mImageViewPreferredService, mImageViewPreferredDealImage;
        RelativeLayout mRelativeLayoutBestDealDetail, mRelativeLayoutBestSave, mRelativeLayoutCurrentPlan;
        LinearLayout mLinearLayoutBestDeal, mLinearLayoutTrendingEffective;
    }
}