package com.spa.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.text.Html;
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

import com.spa.fragment.ShowDailog;
import com.spa.model.dashbordmodel.Dashboard;
import com.spa.servicedealz.R;
import com.spa.servicedealz.services.BundleServiceActivity;
import com.spa.servicedealz.services.CableServiceActivity;
import com.spa.servicedealz.services.CellPhoneServiceActivity;
import com.spa.servicedealz.services.InternetServiceActivity;
import com.spa.servicedealz.services.TelephoneServices;
import com.spa.servicedealz.ui.OrderDealDetailActivity;
import com.spa.utils.Constant;
import com.squareup.picasso.Picasso;

/**
 * Created by E0115Diwakar on 2/17/2015.
 */

/**
 * ************************************************************************************
 * FileName : Dashboard_adapter.java
 * <p/>
 * Dependencies :Dashboard,ContentFragment
 * <p/>
 * Description : Show all the item in listview.
 * *************************************************************************************
 */
public class DashboardAdapter extends BaseAdapter {
    private Activity mActivty;
    // private int[] mImageArray = {R.drawable.internet_deal, R.drawable.cellphone, R.drawable.gas_deal, R.drawable.electricity_deal, R.drawable.homesecurity_deal, R.drawable.cable_deal, R.drawable.telephone, R.drawable.bundle};
    private Dashboard mData;
    public static int best_deal_flag = 1;
    private SharedPreferences mSharedPreferences;
    Boolean editUnsuscribe = false;
    private SharedPreferences.Editor mEditor;

    public DashboardAdapter(Activity activity, Dashboard goc) {
        this.mActivty = activity;
        this.mData = goc;
        best_deal_flag = 1;
    }

    @Override
    public int getCount() {
        return mData.getDashboard_data().size();
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
        final ViewHolder holder;
        View row = convertView;
        try {
            LayoutInflater mInflater = (LayoutInflater)
                    mActivty.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            row = mInflater.inflate(R.layout.dashboard_adapter, null);
            holder = new ViewHolder();

            //===========================================Order Deal======================================================
            holder.mRelativeLayoutOrder = (RelativeLayout) row.findViewById(R.id.rl_order_deal_detail);
            holder.mRelativeLayoutOrderStatus = (RelativeLayout) row.findViewById(R.id.rl_order_activationstatus);

            holder.mTextViewOrderStatus = (TextView) row.findViewById(R.id.txt_order_activtion_status);
            holder.mTextViewOrderDesc = (TextView) row.findViewById(R.id.txt_order_short_desc);
            holder.mTextViewExpiryOrder = (TextView) row.findViewById(R.id.txt_order_expiry_date);
            holder.mTextViewOrederPrice = (TextView) row.findViewById(R.id.order_price);
            holder.mTextViewOrdershowDetail = (TextView) row.findViewById(R.id.txt_order_viewmore);

            holder.mImageViewOrder = (ImageView) row.findViewById(R.id.img_order_deal);
            holder.mImageViewExpiry = (ImageView) row.findViewById(R.id.img_order_exp);
            //=========================================Trending Deal=============================
            holder.mRelativeLayoutPreferredDealDetail = (RelativeLayout) row.findViewById(R.id.rl_preferred_provider_deal_detail);
            holder.mRelativeLayoutTrendingActivationStatus = (RelativeLayout) row.findViewById(R.id.rl_activationstatus);

            holder.mLinearLayoutTrendingEffective = (LinearLayout) row.findViewById(R.id.ll_trending_effective_price);

            holder.mTextViewTrendingActivationStatus = (TextView) row.findViewById(R.id.txt_activtion_status);
            holder.mTextViewTrendingEffectiveDealPrice = (TextView) row.findViewById(R.id.trending_effective_deal_price);
            holder.mTextViewPreferredDealPrice = (TextView) row.findViewById(R.id.preferred_price);
            holder.mTextViewPreferredShowDetail = (TextView) row.findViewById(R.id.txt_preferred_viewmore);
            holder.mTextViewPreferredShortDesc = (TextView) row.findViewById(R.id.txt_preferred_provider_short_desc);
            holder.mTextViewTrendingExp = (TextView) row.findViewById(R.id.txt_trend_expiry_date);

            holder.mImageViewImage = (ImageView) row.findViewById(R.id.img_trend_exp);
            holder.mImageViewPreferredService = (ImageView) row.findViewById(R.id.img_preferred_provider0);


            //=========================================Best Deal=============================
            holder.mRelativeLayoutBestActivationStatus = (RelativeLayout) row.findViewById(R.id.rl_best_activationstatus);
            holder.mRelativeLayoutBestDealDetail = (RelativeLayout) row.findViewById(R.id.rl_best_deal_detail);
            holder.mRelativeLayoutBestSave = (RelativeLayout) row.findViewById(R.id.rl_best_save);
            holder.mRelativeLayoutCurrentPlan = (RelativeLayout) row.findViewById(R.id.rl_your_plan);

            holder.mLinearLayoutBestDeal = (LinearLayout) row.findViewById(R.id.llbestdeal);
            holder.mLinearLayoutBestEffective = (LinearLayout) row.findViewById(R.id.ll_effective_price);

            holder.mTextViewBestActivationStatus = (TextView) row.findViewById(R.id.txt_best_activtion_status);
            holder.mTextViewBestDealEffectivePrice = (TextView) row.findViewById(R.id.effective_deal_price);
            holder.mTextViewCategoryName = (TextView) row.findViewById(R.id.txt_category_name);
            holder.mTextViewExpiryDate = (TextView) row.findViewById(R.id.txt_expiry_date);
            holder.mTextViewCurrentPlanPrice = (TextView) row.findViewById(R.id.your_plan_price);
            holder.mTextViewDealPrice = (TextView) row.findViewById(R.id.best_deal_price);
            holder.mTextViewDealSavePrice = (TextView) row.findViewById(R.id.best_deal_save_price);
            holder.mTextViewShortDesc = (TextView) row.findViewById(R.id.txt_best_short_desc);
            holder.mTextViewShowDetail = (TextView) row.findViewById(R.id.txt_best_viewmore);
            holder.mTextViewVendorName = (TextView) row.findViewById(R.id.tv_provider);
            holder.mTextViewAddService = (TextView) row.findViewById(R.id.txt_add_services);

            holder.mImageViewDealImage = (ImageView) row.findViewById(R.id.img_best0);
            holder.mImageViewPreferredDealImage = (ImageView) row.findViewById(R.id.img_best2);
            row.setTag(holder);
            holder.mTextViewCategoryName.setText(mData.getDashboard_data().get(position).getService_category_name().toString());
            final String service_vender = mData.getDashboard_data().get(position).getService_category_name().toString();
            mSharedPreferences = mActivty.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            mEditor = mSharedPreferences.edit();
            if (mData.getDashboard_data().get(position).isBest_deal_flag()) {
                holder.mTextViewAddService.setText((Html.fromHtml("<u>Edit Services</u>")));
                String editService = "EditService";
                mEditor.putString("EditService", editService);
                mEditor.putString("AddService", "edit");
                mEditor.commit();
            } else {
                String addService = "AddService";
                mEditor.putString("AddService", addService);
                mEditor.putString("EditService", "add");
                mEditor.commit();
            }

            if (mData.getDashboard_data().get(position).getBest_deal() != null) {

                setTextviewData(holder, position);

            } else {
                holder.mLinearLayoutBestDeal.setVisibility(View.GONE);
                holder.mRelativeLayoutBestDealDetail.setVisibility(View.GONE);
                holder.mRelativeLayoutBestSave.setVisibility(View.GONE);

            }
            if (mData.getDashboard_data().get(position).getTrending_deal() != null) {
                setTextViewTrendingData(holder, position);

            } else {
                // holder.mRelativeLayoutPreferredDeal.setVisibility(View.GONE);
                holder.mRelativeLayoutPreferredDealDetail.setVisibility(View.GONE);

            }
            if (mData.getDashboard_data().get(position).getOrder_deal() != null) {
                setTextViewOrderData(holder, position);

            } else {
                holder.mRelativeLayoutOrder.setVisibility(View.GONE);

            }
            holder.mRelativeLayoutOrder.setTag(position);
            holder.mTextViewShowDetail.setTag(position);
            holder.mTextViewOrdershowDetail.setTag(position);
            holder.mTextViewPreferredShowDetail.setTag(position);

            holder.mTextViewAddService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (service_vender.equalsIgnoreCase(mActivty.getResources().getString(R.string.internet))) {
                        Intent Internet_Services = new Intent(mActivty, InternetServiceActivity.class);
                        Internet_Services.putExtra("bestdealflag", best_deal_flag);
                        mActivty.startActivity(Internet_Services);
                    } else if (service_vender.equalsIgnoreCase(mActivty.getResources().getString(R.string.telephone))) {
                        Intent Telephone_services = new Intent(mActivty, TelephoneServices.class);
                        Telephone_services.putExtra("bestdealflag", best_deal_flag);
                        mActivty.startActivity(Telephone_services);
                    } else if (service_vender.equalsIgnoreCase(mActivty.getResources().getString(R.string.gas))) {
                    } else if (service_vender.equalsIgnoreCase(mActivty.getResources().getString(R.string.electricity))) {
                    } else if (service_vender.equalsIgnoreCase(mActivty.getResources().getString(R.string.home_securuty))) {
                    } else if (service_vender.equalsIgnoreCase(mActivty.getResources().getString(R.string.cable))) {
                        Intent Cable_services = new Intent(mActivty, CableServiceActivity.class);
                        Cable_services.putExtra("bestdealflag", best_deal_flag);
                        mActivty.startActivity(Cable_services);
                    } else if (service_vender.equalsIgnoreCase(mActivty.getResources().getString(R.string.cell_phone))) {
                        Intent Cell_Phone_Services = new Intent(mActivty, CellPhoneServiceActivity.class);
                        Cell_Phone_Services.putExtra("bestdealflag", best_deal_flag);
                        mActivty.startActivity(Cell_Phone_Services);
                    } else if (service_vender.equalsIgnoreCase(mActivty.getResources().getString(R.string.bundle))) {
                        Intent Bundle_services = new Intent(mActivty, BundleServiceActivity.class);
                        Bundle_services.putExtra("bestdealflag", best_deal_flag);
                        mActivty.startActivity(Bundle_services);
                    }

                }
            });
            holder.mRelativeLayoutOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int vv = (int) v.getTag();
                    Intent intent = new Intent(mActivty, OrderDealDetailActivity.class);
                    int id = mData.getDashboard_data().get(vv).getOrder_deal().getOrder_id();
                    mSharedPreferences = mActivty.getSharedPreferences(Constant.SHARED_PREF,
                            Activity.MODE_WORLD_READABLE);
                    intent.putExtra("orderid", id);
                    intent.putExtra("userid", mSharedPreferences.getString(Constant.APP_USER_ID, ""));
                    mActivty.startActivity(intent);
                }
            });
            holder.mTextViewShowDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int vv = (int) v.getTag();

                    ShowDailog.CustemDailog(mActivty, mData.getDashboard_data().get(vv).getBest_deal().getDetail_description(), "Deal Detail");

                }
            });
            holder.mTextViewPreferredShowDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int vv = (int) v.getTag();
                    ShowDailog.CustemDailog(mActivty, mData.getDashboard_data().get(vv).getTrending_deal().getDetail_description(), "Deal Detail");
                }
            });
            holder.mTextViewOrdershowDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int vv = (int) v.getTag();
                    ShowDailog.CustemDailog(mActivty, mData.getDashboard_data().get(vv).getOrder_deal().getDetail_description(), "Deal Detail");
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

    private void setTextViewTrendingData(ViewHolder holder, int position) {

        String[] departure1 = mData.getDashboard_data().get(position).getTrending_deal().getEnd_date().toString().split(" ");
        holder.mTextViewPreferredShortDesc.setText("" + mData.getDashboard_data().get(position).getTrending_deal().getShort_description());
        // ImageLoader.getInstance().displayImage(mData.getDashboard_data().get(position).getTrending_deal().getDeal_image_url(), holder.mImageViewPreferredService);
        Picasso.with(mActivty)
                .load(mData.getDashboard_data().get(position).getTrending_deal().getDeal_image_url()).placeholder(R.drawable.progress_animation)
                .into(holder.mImageViewPreferredService);
        if (mData.getDashboard_data().get(position).getTrending_deal().getContract_period() == 0) {
            holder.mTextViewTrendingExp.setText("Contract: None");
            holder.mImageViewImage.setVisibility(View.VISIBLE);
        } else {
            holder.mTextViewTrendingExp.setText("Contract: " + mData.getDashboard_data().get(position).getTrending_deal().getContract_period() + " months");
            holder.mImageViewImage.setVisibility(View.VISIBLE);
        }
        holder.mTextViewPreferredDealPrice.setText("$" + mData.getDashboard_data().get(position).getTrending_deal().getDeal_price());

        if (!mData.getDashboard_data().get(position).getTrending_deal().getEffective_price().equalsIgnoreCase("0.00")) {
            holder.mLinearLayoutTrendingEffective.setVisibility(View.VISIBLE);
            // holder.mTextViewPreferredDealPrice.setPaintFlags(holder.mTextViewDealPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            //  holder.mTextViewPreferredDealPrice.setTypeface(null, Typeface.NORMAL);
            holder.mTextViewTrendingEffectiveDealPrice.setText("$" + mData.getDashboard_data().get(position).getTrending_deal().getEffective_price());
            holder.mTextViewTrendingEffectiveDealPrice.setTypeface(null, Typeface.BOLD);
        } else {
            holder.mLinearLayoutTrendingEffective.setVisibility(View.GONE);
        }
       /* if (!mData.getDashboard_data().get(position).getBest_deal().getActivation_status().equalsIgnoreCase("")) {
            holder.mRelativeLayoutTrendingActivationStatus.setVisibility(View.GONE);
            holder.mTextViewTrendingActivationStatus.setText("");
        }*/

    }

    private void setTextviewData(ViewHolder holder, int position) {
        best_deal_flag++;
        if (mData.getDashboard_data().get(position).getContract_fee().equalsIgnoreCase("0.0")) {
        } else {
            holder.mTextViewCurrentPlanPrice.setText(("$" + String.format("%.2f", Double.parseDouble(mData.getDashboard_data().get(position).getContract_fee()))));
        }
        holder.mTextViewVendorName.setText(("Your Vendor: " + mData.getDashboard_data().get(position).getService_provider_name()));
        if (mData.getDashboard_data().get(position).getBest_deal().getContract_period() == 0) {
            holder.mTextViewExpiryDate.setText("Contract: None");
            holder.mImageViewPreferredDealImage.setVisibility(View.VISIBLE);
        } else {
            holder.mTextViewExpiryDate.setText("Contract: " + mData.getDashboard_data().get(position).getBest_deal().getContract_period() + " months");
            holder.mImageViewPreferredDealImage.setVisibility(View.VISIBLE);
        }
        String deal_price = "$" + String.format("%.2f", Double.parseDouble(mData.getDashboard_data().get(position).getBest_deal().getDeal_price()));

        if (Double.parseDouble(mData.getDashboard_data().get(position).getYou_save_text()) < 0.0) {
            String str = mData.getDashboard_data().get(position).getYou_save_text().replace("-", "");
            holder.mTextViewDealSavePrice.setText("($" + String.format("%.2f", Double.parseDouble(str)) + ")");

        } else {
            holder.mTextViewDealSavePrice.setText("$" + String.format("%.2f", Double.parseDouble(mData.getDashboard_data().get(position).getYou_save_text())) + "/year");

        }
        holder.mTextViewDealPrice.setText(Html.fromHtml("<strike>" + deal_price + "</strike>"));

        holder.mTextViewShortDesc.setText("" + mData.getDashboard_data().get(position).getBest_deal().getShort_description().trim());
        Picasso.with(mActivty)
                .load(mData.getDashboard_data().get(position).getBest_deal().getDeal_image_url()).placeholder(R.drawable.progress_animation)
                .into(holder.mImageViewDealImage);
        if (!mData.getDashboard_data().get(position).getBest_deal().getEffective_price().equalsIgnoreCase("0.00")) {
            holder.mLinearLayoutBestEffective.setVisibility(View.VISIBLE);
            // holder.mTextViewDealPrice.setPaintFlags(holder.mTextViewDealPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            // holder.mTextViewDealPrice.setTypeface(null, Typeface.NORMAL);
            holder.mTextViewBestDealEffectivePrice.setText("$" + mData.getDashboard_data().get(position).getBest_deal().getEffective_price());
            holder.mTextViewBestDealEffectivePrice.setTypeface(null, Typeface.BOLD);
        }

    }

    private void setTextViewOrderData(ViewHolder holder, int position) {
        String OrderStatus = mData.getDashboard_data().get(position).getOrder_deal().getOrder_status().toString();
        if (OrderStatus.equalsIgnoreCase("In-progress")) {
            holder.mRelativeLayoutOrderStatus.setBackgroundResource(R.drawable.progress);
        } else if (OrderStatus.equalsIgnoreCase("Switched")) {
            holder.mRelativeLayoutOrderStatus.setBackgroundResource(R.drawable.switched);
        } else {
            holder.mRelativeLayoutOrderStatus.setBackgroundResource(R.drawable.switched);
        }
        holder.mRelativeLayoutOrderStatus.setVisibility(View.VISIBLE);
        holder.mTextViewOrderStatus.setText("  " + mData.getDashboard_data().get(position).getOrder_deal().getOrder_status().toString());
        String[] departure1 = mData.getDashboard_data().get(position).getOrder_deal().getEnd_date().toString().split(" ");
        holder.mTextViewOrderDesc.setText("" + mData.getDashboard_data().get(position).getOrder_deal().getShort_description());
        Picasso.with(mActivty)
                .load(mData.getDashboard_data().get(position).getOrder_deal().getDeal_image_url()).placeholder(R.drawable.progress_animation)
                .into(holder.mImageViewOrder);
        if (mData.getDashboard_data().get(position).getOrder_deal().getContract_period() == 0) {
            holder.mTextViewExpiryOrder.setText("Contract: None");
            holder.mImageViewExpiry.setVisibility(View.VISIBLE);
        } else {
            holder.mTextViewExpiryOrder.setText("Contract: " + mData.getDashboard_data().get(position).getOrder_deal().getContract_period() + " months");
            holder.mImageViewExpiry.setVisibility(View.VISIBLE);
        }
        if (mData.getDashboard_data().get(position).getOrder_deal().getEffective_price().toString().equalsIgnoreCase("0.00")) {
            holder.mTextViewOrederPrice.setText("$" + mData.getDashboard_data().get(position).getOrder_deal().getDeal_price());
        } else {
            holder.mTextViewOrederPrice.setText("$" + mData.getDashboard_data().get(position).getOrder_deal().getEffective_price());
        }

    }

    class ViewHolder {
        private TextView mTextViewAddService, mTextViewVendorName, mTextViewCategoryName, mTextViewShowDetail, mTextViewPreferredShowDetail, mTextViewExpiryDate, mTextViewShortDesc, mTextViewPreferredShortDesc, mTextViewTrendingExp,
                mTextViewCurrentPlanPrice, mTextViewDealPrice, mTextViewPreferredDealPrice, mTextViewDealSavePrice, mTextViewOrederPrice,
                mTextViewExpiryOrder, mTextViewOrderDesc, mTextViewOrdershowDetail, mTextViewOrderStatus,
                mTextViewTrendingActivationStatus, mTextViewBestActivationStatus, mTextViewBestDealEffectivePrice, mTextViewTrendingEffectiveDealPrice;
        ImageView mImageViewImage, mImageViewDealImage, mImageViewPreferredService, mImageViewPreferredDealImage, mImageViewOrder, mImageViewExpiry;
        RelativeLayout mRelativeLayoutBestDealDetail, mRelativeLayoutBestSave, mRelativeLayoutCurrentPlan,
                mRelativeLayoutBestActivationStatus, mRelativeLayoutTrendingActivationStatus, mRelativeLayoutOrder,
                mRelativeLayoutPreferredDealDetail, mRelativeLayoutOrderStatus;
        LinearLayout mLinearLayoutBestDeal, mLinearLayoutTrendingEffective, mLinearLayoutBestEffective;

    }
}
