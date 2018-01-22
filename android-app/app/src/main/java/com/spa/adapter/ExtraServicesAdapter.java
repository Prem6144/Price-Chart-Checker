package com.spa.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import crittercism.android.p;

/**
 * Created by Diwakar on 9/14/2016.
 */
public class ExtraServicesAdapter extends RecyclerView.Adapter<ExtraServicesAdapter.VersionViewHolder> {

    DealDeatil statusLevelListEntities;
    Activity activity;
    double value1;
    private String otherPrice;
    Integer deal_extra_service_id;
    String service_name;
    ArrayList<Boolean> arrayList2;
    Double value = 0.0;

    public ExtraServicesAdapter(Activity a, DealDeatil deals,ArrayList<Boolean> arrayList2) {
        this.arrayList2 = arrayList2;
        this.statusLevelListEntities = deals;
        this.activity = a;
        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ExtraServicePrice", "0.0");
        editor.commit();

    }
    public ExtraServicesAdapter(Activity a, DealDeatil deals) {

        this.statusLevelListEntities = deals;
        this.activity = a;


    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.dealextra_service_design, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {
        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        // Set Data in your views comes from CollectionClass
        try {
            versionViewHolder.mTextViewPrice
                    .setText("$" + statusLevelListEntities.getDeals().getDealExtraServices().get(i).getPrice());
            versionViewHolder.mTextViewDescription
                    .setText(statusLevelListEntities.getDeals().getDealExtraServices().get(i).getServiceDescription());

            versionViewHolder.mTextViewTitle
                    .setText(statusLevelListEntities.getDeals().getDealExtraServices().get(i).getServiceName());

            versionViewHolder.mCheckBoxCompare.setChecked(arrayList2.get(i));
//            versionViewHolder.mCheckBoxCompare.setTag(statusLevelListEntities.getDeals().getDealExtraServices().get(i));
            versionViewHolder.mCheckBoxCompare.setTag(i);
//            versionViewHolder.mCheckBoxCompare.setTag(statusLevelListEntities.getDeals().getDealExtraServices().get(i));
//            versionViewHolder.mCheckBoxCompare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//
//                    if (buttonView.isChecked()) {
//                        otherPrice = statusLevelListEntities.getDeals().getDealExtraServices().get(i).getPrice();
//                        value1 = Double.parseDouble(otherPrice);
//                        value1 = Math.round(value1 * 100.0) / 100.0;
//                        CellphoneCustemDealActivity.value = Math.round(CellphoneCustemDealActivity.value * 100.0) / 100.0;
//                        deal_extra_service_id = String.valueOf(statusLevelListEntities.getDeals().getDealExtraServices().get(i).getExtraServiceId());
//                        service_name = statusLevelListEntities.getDeals().getDealExtraServices().get(i).getServiceName();
//                        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putInt("ExtraserviceDealExtraId", Integer.parseInt(deal_extra_service_id));
//                        editor.putFloat("ExtraServicePrice", Float.parseFloat(otherPrice));
//
//                        CellphoneCustemDealActivity.value = CellphoneCustemDealActivity.value + value1;
//                        DecimalFormat f = new DecimalFormat("##.00");
//                        CellphoneCustemDealActivity.mTextViewEffectivePrice.setText("$" + f.format(CellphoneCustemDealActivity.value));
//                        //   Toast.makeText(,"checked",Toast.LENGTH_SHORT).show();
//                    } else {
//                        otherPrice = statusLevelListEntities.getDeals().getDealExtraServices().get(i).getPrice();
//                        value1 = Double.parseDouble(otherPrice);
//
//                        value1 = Math.round(value1 * 100.0) / 100.0;
//
//                        CellphoneCustemDealActivity.value = Math.round(CellphoneCustemDealActivity.value * 100.0) / 100.0;
//
//                        CellphoneCustemDealActivity.value = CellphoneCustemDealActivity.value - value1;
//
//                        DecimalFormat f = new DecimalFormat("##.00");
//                        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sp.edit();
//                        editor.putFloat("ExtraServicePrice", Float.parseFloat("0.0"));
//                        editor.commit();
//
//                        CellphoneCustemDealActivity.mTextViewEffectivePrice.setText("$" + f.format(CellphoneCustemDealActivity.value));
//                    }
//                }
//            });
            versionViewHolder.mCheckBoxCompare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();

                    if (versionViewHolder.mCheckBoxCompare.isChecked()) {
                        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        Double value2 = 0.0;
                        if (arrayList2.contains(true)) {
                            int pos = arrayList2.indexOf(true);
                            value2 = Double.parseDouble(statusLevelListEntities.getDeals().getDealExtraServices().get(pos).getPrice());

                        }
                        value1 = Double.parseDouble(statusLevelListEntities.getDeals().getDealExtraServices().get(position).getPrice());
                        otherPrice = String.valueOf(statusLevelListEntities.getDeals().getDealExtraServices().get(position).getPrice());
                        editor.putString("ExtraServicePrice", otherPrice);
                        service_name = statusLevelListEntities.getDeals().getDealExtraServices().get(position).getServiceName();
                        editor.putString("ExtraserviceExtraServiceName", service_name);
                        deal_extra_service_id = statusLevelListEntities.getDeals().getDealExtraServices().get(position).getId();
                        editor.putInt("extraserviceId", deal_extra_service_id);
                        editor.commit();

                        value1 = Math.round(value1 * 100.0) / 100.0;
                        CellphoneCustemDealActivity.value = Math.round(CellphoneCustemDealActivity.value * 100.0) / 100.0;
                        CellphoneCustemDealActivity.value = CellphoneCustemDealActivity.value + value1 - value2;
                        DecimalFormat f = new DecimalFormat("##.00");
                        CellphoneCustemDealActivity.mTextViewEffectivePrice.setText("$" + f.format(CellphoneCustemDealActivity.value));
                        ;
                        //   Toast.makeText(,"checked",Toast.LE``   1   NGTH_SHORT).show();

//                    mEquipementMap.put("EquipmentsColorName",mEquipementColorName);
//                    mEquipementMap.put("EquipmentsPhoneDetailId", String.valueOf(mEquipementCellPhoneDetailId));
//                    mEquipementMap.put("EquipementPrice",otherPrice);
//                    mEquipemenmapList.add(mEquipementMap);
                        if (arrayList2.contains(true)) {
                            int i1 = arrayList2.indexOf(true);
                            arrayList2.remove(i1);
                            arrayList2.add(i1, false);

                        }
                        arrayList2.remove(position);
                        arrayList2.add(position, true);
                        notifyDataSetChanged();


                    } else {
                        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("ExtraServicePrice", "0.0");
                        editor.commit();
                        if (arrayList2.contains(true)) {
                            int index = arrayList2.indexOf(true);

                            value1 = Double.parseDouble(statusLevelListEntities.getDeals().getDealExtraServices().get(index).getPrice());
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
                        arrayList2.remove(position);
                        arrayList2.add(position, false);
                        notifyDataSetChanged();

                    }
                }

            });

        } catch (Exception e) {

        }

    }


    @Override
    public int getItemCount() {

        return statusLevelListEntities == null ? 0 : statusLevelListEntities.getDeals().getDealExtraServices().size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView mTextViewTitle, mTextViewDescription, mTextViewPrice, mTextViewbundleCombo, mTextViewExpiryDate,
                mTextViewSavePrice, mTextViewEffectivePrice;
        private ImageView mImageViewVendorImage, mImageViewSocialSharing, mImageViewBestDealImage;
        private RatingBar mRatingDeal;
        private CheckBox mCheckBoxCompare;
        private LinearLayout mLinearLayoutEffective;
        private RelativeLayout mRelativeLayoutBeatDeal;

        public VersionViewHolder(View row) {
            super(row);

            mCheckBoxCompare = (CheckBox) row.findViewById(R.id.dealextraservicecheckbox);
            mTextViewTitle = (TextView) row.findViewById(R.id.basic_plantitle);
            mTextViewDescription = (TextView) row.findViewById(R.id.plan_description);
            mTextViewPrice = (TextView) row.findViewById(R.id.extraservice_price);
            mView = row;

        }
    }
}
