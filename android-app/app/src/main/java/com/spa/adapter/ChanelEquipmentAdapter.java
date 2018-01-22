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

import com.spa.model.custemisecable.CustemiseCable;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.CableDealDeatilActivity;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Narendra on 9/21/2016.
 */
public class ChanelEquipmentAdapter extends RecyclerView.Adapter<ChanelEquipmentAdapter.VersionViewHolder> {
    private CustemiseCable channelPackagesBeen = null;
    private String effectivePrice, otherPrice;
    double value1;
    Activity activity;
    String equipmentID, equipmentName;
    ArrayList<Boolean> arrayList1;


    public ChanelEquipmentAdapter(Activity a, CustemiseCable channelPackagesBeen1, ArrayList<Boolean> arrayList1) {

        this.arrayList1 = arrayList1;
        this.channelPackagesBeen = channelPackagesBeen1;
        this.activity = a;
        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ChalneEquipmentPrice", "");
        editor.commit();


    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.channel_equipments, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {

        // Set Data in your views comes from CollectionClass
        try {
            versionViewHolder.txtViewMoney
                    .setText("$" + channelPackagesBeen.getDeals().getDealEquipments().get(i).getPrice() + "/mo");
            versionViewHolder.txtViewtitle
                    .setText(channelPackagesBeen.getDeals().getDealEquipments().get(i).getName());
            /*versionViewHolder.textViewRecords
                    .setText(channelPackagesBeen.getDeals().get(0).getDealEquipments().get(i).));
            versionViewHolder.txtViewHDPrograms
                    .setText(channelPackagesBeen.getDeals().get(0).getDealAttributes().get(i).getDescription());*/
//            versionViewHolder.mCheckBoxCompare.setTag(channelPackagesBeen.getDeals().getDealEquipments().get(i));
//            versionViewHolder.mCheckBoxCompare.setOnCheckedChangeListener(null);
            versionViewHolder.mCheckBoxCompare.setChecked(arrayList1.get(i));
            versionViewHolder.mCheckBoxCompare.setTag(i);
            versionViewHolder.mCheckBoxCompare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    if (versionViewHolder.mCheckBoxCompare.isChecked()) {


                        Double value2 = 0.0;
                        if (arrayList1.contains(true)) {
                            int pos = arrayList1.indexOf(true);
                            value2 = Double.parseDouble(channelPackagesBeen.getDeals().getDealEquipments().get(pos).getPrice());
                        }
                        value1 = Double.parseDouble(channelPackagesBeen.getDeals().getDealEquipments().get(position).getPrice());
                        otherPrice = String.valueOf(channelPackagesBeen.getDeals().getDealEquipments().get(position).getPrice());
                        editor.putString("ChalneEquipmentPrice", otherPrice);
                        equipmentID = String.valueOf(channelPackagesBeen.getDeals().getDealEquipments().get(position).getId());
                        equipmentName = channelPackagesBeen.getDeals().getDealEquipments().get(position).getName();
                        editor.putString("ChalneEquipmentName", equipmentName);
//                        price=channelPackagesBeen.getDeals().getDealEquipments().get(i).getPrice();
                        editor.putString("ChalneEquipmentID", equipmentID);
                        editor.commit();
                        value1 = Math.round(value1 * 100.0) / 100.0;
                        CableDealDeatilActivity.value = Math.round(CableDealDeatilActivity.value * 100.0) / 100.0;
                        CableDealDeatilActivity.value = CableDealDeatilActivity.value + value1 - value2;
                        DecimalFormat f = new DecimalFormat("##.00");
                        CableDealDeatilActivity.mTextViewEffectivePrice.setText("$" + f.format(CableDealDeatilActivity.value));

                        if (arrayList1.contains(true)) {
                            int i1 = arrayList1.indexOf(true);
                            arrayList1.remove(i1);
                            arrayList1.add(i1, false);

                        }
                        arrayList1.remove(position);
                        arrayList1.add(position, true);
                        notifyDataSetChanged();
                    } else {
                        editor.putString("ChalneEquipmentPrice", "");
                        editor.commit();
                        if (arrayList1.contains(true)) {
                            int index = arrayList1.indexOf(true);

                            value1 = Double.parseDouble(channelPackagesBeen.getDeals().getDealEquipments().get(index).getPrice());
                            CableDealDeatilActivity.value = CableDealDeatilActivity.value - value1;
                            value1 = Math.round(value1 * 100.0) / 100.0;
                            CableDealDeatilActivity.value = Math.round(CableDealDeatilActivity.value * 100.0) / 100.0;
                            DecimalFormat f = new DecimalFormat("##.00");
                            CableDealDeatilActivity.mTextViewEffectivePrice.setText("$" + f.format(CableDealDeatilActivity.value));


                        } else {
                            CableDealDeatilActivity.value = Math.round(CableDealDeatilActivity.value * 100.0) / 100.0;
                            DecimalFormat f = new DecimalFormat("##.00");
                            CableDealDeatilActivity.mTextViewEffectivePrice.setText("$" + f.format(CableDealDeatilActivity.value));

                        }
                        arrayList1.remove(position);
                        arrayList1.add(position, false);
                        notifyDataSetChanged();
                    }
                }
            });


//            versionViewHolder.mCheckBoxCompare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (buttonView.isChecked()){
//                        otherPrice=  channelPackagesBeen.getDeals().getDealEquipments().get(i).getPrice();
//                        value1=Float.parseFloat(otherPrice);
//
//                        SharedPreferences sharedPreferences=activity.getSharedPreferences(Constant.SHARED_PREF, Activity.MODE_WORLD_READABLE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                        equipmentID= String.valueOf(channelPackagesBeen.getDeals().getDealEquipments().get(i).getId());
//                        price=channelPackagesBeen.getDeals().getDealEquipments().get(i).getPrice();
//                        editor.putString("EquipmentID", equipmentID);
//                        editor.putString("EquipmentPrice", price);
//                        editor.commit();
//
//                        CableDealDeatilActivity.value=CableDealDeatilActivity.value+value1;
//                        CableDealDeatilActivity.textViewEffectivePrice.setText("$" +  String.format("%.2f",CableDealDeatilActivity.value));
//                        //   Toast.makeText(,"checked",Toast.LENGTH_SHORT).show();
//                    }else {
//
//                        otherPrice=  channelPackagesBeen.getDeals().getDealEquipments().get(i).getPrice();
//                        value1=Float.parseFloat(otherPrice);
//
//                        CableDealDeatilActivity.value=CableDealDeatilActivity.value-value1;
//                        CableDealDeatilActivity.textViewEffectivePrice.setText("$" + String.format("%.2f",CableDealDeatilActivity.value));
//                    }
//                }
//            });

            /*Picasso.with(activity).load(String.valueOf(cableServiceCust.getDeals().get(i).getImage().getUrl())).into(versionViewHolder.mImageViewVendorImage);*/
            // versionViewHolder.mTextViewbundleCombo.setText( cellphone.getDeals().get(i).getUrl());
        } catch (Exception e) {

        }

    }


    @Override
    public int getItemCount() {

        return channelPackagesBeen == null ? 0 : channelPackagesBeen.getDeals().getDealEquipments().size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView txtViewtitle, textViewRecords, txtViewHDPrograms, txtViewMoney;
        private ImageView mImageViewVendorImage, mImageViewSocialSharing, mImageViewBestDealImage;
        private RatingBar mRatingDeal;
        private CheckBox mCheckBoxCompare;
        private LinearLayout mLinearLayoutEffective;
        private RelativeLayout mRelativeLayoutBeatDeal;

        public VersionViewHolder(View row) {
            super(row);

            txtViewtitle = (TextView) row.findViewById(R.id.txt_title_euipment);
            textViewRecords = (TextView) row.findViewById(R.id.txt_record);
            txtViewHDPrograms = (TextView) row.findViewById(R.id.txt_hd_programs);
            // txtViewMovieChannel= (TextView) row.findViewById(R.id.premimum_movie);
            txtViewMoney = (TextView) row.findViewById(R.id.txt_prices);
            mCheckBoxCompare = (CheckBox) row.findViewById(R.id.plansubcriptioncheckbox);
            mView = row;

        }
    }
}
