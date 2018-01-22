package com.spa.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spa.model.cellphondetail.DealDeatil;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.CellphoneCustemDealActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diwakar on 9/14/2016.
 */
public class PlanSubscriptionAdater extends RecyclerView.Adapter<PlanSubscriptionAdater.VersionViewHolder> {

    DealDeatil statusLevelListEntities;
    Activity activity;

    List<String> mList = new ArrayList<>();
    double value1;
    private String otherPrice;
    ArrayList<Boolean> booleanArrayList;
    //  Double value = 0.0;
    String mPlanName;

    public PlanSubscriptionAdater(Activity a, DealDeatil deals, ArrayList<Boolean> booleanArrayList) {
        this.statusLevelListEntities = deals;
        this.booleanArrayList = booleanArrayList;
        this.activity = a;
        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        try {
            editor.putString("PlanName1", statusLevelListEntities.getDeals().getDealAttributes().get(0).getTitle());
            editor.putString("planPrice1", statusLevelListEntities.getDeals().getDealAttributes().get(0).getDataPlanPrice());
            editor.putInt("PlanId1", statusLevelListEntities.getDeals().getDealAttributes().get(0).getId());
            editor.putInt("planTitle1", statusLevelListEntities.getDeals().getDealAttributes().get(0).getDealId());
            editor.putString("EffectiveTotalValue", String.valueOf(CellphoneCustemDealActivity.mTextViewEffectivePrice.getText().toString()));
            editor.commit();
        } catch (Exception e) {
            editor.putString("planPrice1", null);
            editor.commit();
        }

        // value = CellphoneCustemDealActivity.value;
    }

    public PlanSubscriptionAdater(Activity a, DealDeatil deals) {

        this.statusLevelListEntities = deals;
        this.activity = a;


    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plansubsccription, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {

        // Set Data in your views comes from CollectionClass
        try {
            versionViewHolder.mTextViewPrice
                    .setText("$" + statusLevelListEntities.getDeals().getDealAttributes().get(i).getDataPlanPrice());
            versionViewHolder.mTextViewDescriptionInternatonal
                    .setText(statusLevelListEntities.getDeals().getDealAttributes().get(i).getDescription());


            mPlanName = statusLevelListEntities.getDeals().getDealAttributes().get(i).getTitle();
            statusLevelListEntities.getDeals().getDealAttributes().get(i).getDealId();
//                statusLevelListEntities.getDeals().getDealAttributes().get(i).getDealId();
//                statusLevelListEntities.getDeals().getDealAttributes().get(i).getDealId();


            versionViewHolder.mTextViewTitle.setText(statusLevelListEntities.getDeals().getDealAttributes().get(i).getTitle());
            versionViewHolder.mTextViewbundleCombo.setText(statusLevelListEntities.getDeals().getDealAttributes().get(i).getDomesticCallMinutes());

            versionViewHolder.mCheckBoxCompare.setChecked(booleanArrayList.get(i));
            versionViewHolder.mCheckBoxCompare.setTag(i);
//
            versionViewHolder.mCheckBoxCompare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = (int) v.getTag();

                    if (versionViewHolder.mCheckBoxCompare.isChecked()) {
                        Double value2 = 0.0;

                        if (booleanArrayList.contains(true)) {
                            int pos = booleanArrayList.indexOf(true);
                            value2 = Double.parseDouble(statusLevelListEntities.getDeals().getDealAttributes().get(pos).getDataPlanPrice());

                        }
                        value1 = Double.parseDouble(statusLevelListEntities.getDeals().getDealAttributes().get(position).getDataPlanPrice());
                        otherPrice = String.valueOf(statusLevelListEntities.getDeals().getDealAttributes().get(position).getDataPlanPrice());

                        value1 = Math.round(value1 * 100.0) / 100.0;
                        CellphoneCustemDealActivity.value = Math.round(CellphoneCustemDealActivity.value * 100.0) / 100.0;
                        CellphoneCustemDealActivity.value = CellphoneCustemDealActivity.value + value1 - value2;
                        DecimalFormat f = new DecimalFormat("##.00");
                        CellphoneCustemDealActivity.mTextViewEffectivePrice.setText("$" + f.format(CellphoneCustemDealActivity.value));
                        String mTotalValue = CellphoneCustemDealActivity.mTextViewEffectivePrice.getText().toString();

                        int planType = statusLevelListEntities.getDeals().getDealAttributes().get(position).getDealId();
                        int planId = statusLevelListEntities.getDeals().getDealAttributes().get(position).getId();

                        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("planPrice1", otherPrice);
                        editor.putInt("PlanId1", planId);
                        editor.putInt("planTitle1", planType);
                        mPlanName = statusLevelListEntities.getDeals().getDealAttributes().get(position).getTitle();
                        editor.putString("PlanName1", mPlanName);
                        editor.putString("EffectiveTotalValue", String.valueOf(mTotalValue));
                        editor.commit();

                        if (booleanArrayList.contains(true)) {
                            int i1 = booleanArrayList.indexOf(true);
                            booleanArrayList.remove(i1);
                            booleanArrayList.add(i1, false);

                        }
                        booleanArrayList.remove(position);
                        booleanArrayList.add(position, true);
                        notifyDataSetChanged();


                    } else {
                        versionViewHolder.mCheckBoxCompare.setChecked(true);
                        /*if (booleanArrayList.contains(true)) {
                            int index = booleanArrayList.indexOf(true);

                            value1 = Double.parseDouble(statusLevelListEntities.getDeals().getDealAttributes().get(index).getDataPlanPrice());
                            CellphoneCustemDealActivity.value = CellphoneCustemDealActivity.value - value1;
                            value1 = Math.round(value1 * 100.0) / 100.0;
                            CellphoneCustemDealActivity.value = Math.round(CellphoneCustemDealActivity.value * 100.0) / 100.0;
                            DecimalFormat f = new DecimalFormat("##.00");
                            CellphoneCustemDealActivity.mTextViewEffectivePrice.setText("$" + f.format(CellphoneCustemDealActivity.value));

                        } else {
                            CellphoneCustemDealActivity.value = Math.round(CellphoneCustemDealActivity.value * 100.0) / 100.0;
                            DecimalFormat f = new DecimalFormat("##.00");
                            CellphoneCustemDealActivity.mTextViewEffectivePrice.setText("$" + f.format(CellphoneCustemDealActivity.value));
                        }
                        booleanArrayList.remove(position);
                        booleanArrayList.add(position, false);
                        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("planPrice", null);
                        editor.commit();
                        notifyDataSetChanged();*/

                    }
                }
//                }
            });

        } catch (Exception e) {

        }

    }

    @Override
    public int getItemCount() {

        return statusLevelListEntities == null ? 0 : statusLevelListEntities.getDeals().getDealAttributes().size();
    }

    public void showSharedPrefrenceData() {


    }

    class VersionViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView mTextViewTitle, mTextViewDescriptionInternatonal, mTextViewPrice, mTextViewbundleCombo, mTextViewExpiryDate,
                mTextViewSavePrice, mTextViewEffectivePrice;
        private ImageView mImageViewVendorImage, mImageViewSocialSharing, mImageViewBestDealImage;
        private RatingBar mRatingDeal;
        private CheckBox mCheckBoxCompare;
        private LinearLayout mLinearLayoutEffective;
        private RelativeLayout mRelativeLayoutBeatDeal;

        public VersionViewHolder(View row) {
            super(row);

            mTextViewbundleCombo = (TextView) row.findViewById(R.id.plansubcription_domestic);
            // mCheckBoxCompare = (CheckBox) row.findViewById(R.id.chk_comparedeal);
            mTextViewTitle = (TextView) row.findViewById(R.id.subscription_plantitle);
            mTextViewDescriptionInternatonal = (TextView) row.findViewById(R.id.plansubcr_international);
            mTextViewPrice = (TextView) row.findViewById(R.id.plansubscription_price);
            // mRelativeLayoutBeatDeal = (RelativeLayout) row.findViewById(R.id.rl_top);
            mCheckBoxCompare = (CheckBox) row.findViewById(R.id.plansubcriptioncheckbox1);
            mView = row;

        }
    }
}