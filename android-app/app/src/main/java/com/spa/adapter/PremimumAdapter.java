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
 * Created by E0147ambar on 9/12/2016.
 */
public class PremimumAdapter extends RecyclerView.Adapter<PremimumAdapter.VersionViewHolder> {

    CustemiseCable channelPackagesBeen = null;
    private String effectivePrice, otherPrice;
    Activity activity;
    String premiumID, premiumName;
    ArrayList<Boolean> arrayList3;
    double value1;
    float a;


    public PremimumAdapter(Activity a, CustemiseCable channelPackagesBeen1, ArrayList<Boolean> arrayList3) {
        this.arrayList3 = arrayList3;
        this.channelPackagesBeen = channelPackagesBeen1;
        this.activity = a;
        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ChalnePremiumPrice", "");
        editor.commit();


    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.get_more_with_premimum_channels, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {

        // Set Data in your views comes from CollectionClass
        try {
            versionViewHolder.txtViewMoney
                    .setText("$" + channelPackagesBeen.getDeals().getChannelPackages().get(i).getPrice() + "/mo");
            versionViewHolder.textViewChannels
                    .setText("" + channelPackagesBeen.getDeals().getChannelPackages().get(i).getChannelCount() + "+Channels");
            versionViewHolder.txtViewBAsic
                    .setText(channelPackagesBeen.getDeals().getChannelPackages().get(i).getPackageName());
            versionViewHolder.txtViewMovieChannel
                    .setText(channelPackagesBeen.getDeals().getChannelPackages().get(i).getDescription());

//            versionViewHolder.mCheckBoxCompare.setTag(channelPackagesBeen.getDeals().getChannelPackages().get(i));
//
//            versionViewHolder.mCheckBoxCompare.setOnCheckedChangeListener(null);
            versionViewHolder.mCheckBoxCompare.setChecked(arrayList3.get(i));
            versionViewHolder.mCheckBoxCompare.setTag(i);
            versionViewHolder.mCheckBoxCompare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    if (versionViewHolder.mCheckBoxCompare.isChecked()) {


                        Double value2 = 0.0;
                        if (arrayList3.contains(true)) {
                            int pos = arrayList3.indexOf(true);
                            value2 = Double.parseDouble(channelPackagesBeen.getDeals().getChannelPackages().get(pos).getPrice());
                        }
                        value1 = Double.parseDouble(channelPackagesBeen.getDeals().getChannelPackages().get(position).getPrice());
                        otherPrice = String.valueOf(channelPackagesBeen.getDeals().getChannelPackages().get(position).getPrice());
                        editor.putString("ChalnePremiumPrice", otherPrice);
                        premiumID = String.valueOf(channelPackagesBeen.getDeals().getChannelPackages().get(position).getId());
                        premiumName = channelPackagesBeen.getDeals().getChannelPackages().get(position).getPackageName();
//                        price=channelPackagesBeen.getDeals().getDealEquipments().get(i).getPrice();
                        editor.putString("ChalnePremiumID", premiumID);
                        editor.putString("ChalnePremiumName", premiumName);
                        editor.commit();
                        value1 = Math.round(value1 * 100.0) / 100.0;
                        CableDealDeatilActivity.value = Math.round(CableDealDeatilActivity.value * 100.0) / 100.0;
                        CableDealDeatilActivity.value = CableDealDeatilActivity.value + value1 - value2;
                        DecimalFormat f = new DecimalFormat("##.00");
                        CableDealDeatilActivity.mTextViewEffectivePrice.setText("$" + f.format(CableDealDeatilActivity.value));
                        if (arrayList3.contains(true)) {
                            int i1 = arrayList3.indexOf(true);
                            arrayList3.remove(i1);
                            arrayList3.add(i1, false);

                        }
                        arrayList3.remove(position);
                        arrayList3.add(position, true);
                        notifyDataSetChanged();
                    } else {
                        editor.putString("ChalnePremiumPrice", "");
                        editor.commit();
                        if (arrayList3.contains(true)) {
                            int index = arrayList3.indexOf(true);

                            value1 = Double.parseDouble(channelPackagesBeen.getDeals().getChannelPackages().get(index).getPrice());
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
                        arrayList3.remove(position);
                        arrayList3.add(position, false);
                        notifyDataSetChanged();
                    }
                }
            });


//            versionViewHolder.mCheckBoxCompare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (buttonView.isChecked()){
//                        otherPrice=channelPackagesBeen.getDeals().getChannelPackages().get(i).getPrice();
//                        a=Float.parseFloat(otherPrice);
//
//                        SharedPreferences sharedPreferences=activity.getSharedPreferences(Constant.SHARED_PREF, Activity.MODE_WORLD_READABLE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                        premiumID= String.valueOf(channelPackagesBeen.getDeals().getChannelPackages().get(i).getId());
//
//                        editor.putString("PremiumID", premiumID);
//                        editor.commit();
//
//                        CableDealDeatilActivity.value= CableDealDeatilActivity.value+a;
//                        CableDealDeatilActivity.textViewEffectivePrice.setText("$" + String.format("%.2f", CableDealDeatilActivity.value));
//                        //   Toast.makeText(,"checked",Toast.LENGTH_SHORT).show();
//                    }else {
//                        otherPrice=channelPackagesBeen.getDeals().getChannelPackages().get(i).getPrice();
//                        a= Float.parseFloat(otherPrice);
//
//                        CableDealDeatilActivity.value= CableDealDeatilActivity.value-a;
//                        CableDealDeatilActivity.textViewEffectivePrice.setText("$" + String.format("%.2f", CableDealDeatilActivity.value));
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

        return channelPackagesBeen == null ? 0 : channelPackagesBeen.getDeals().getChannelPackages().size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView txtViewBAsic, textViewChannels, txtViewMovieChannel, txtViewMoney;
        private ImageView mImageViewVendorImage, mImageViewSocialSharing, mImageViewBestDealImage;
        private RatingBar mRatingDeal;
        private CheckBox mCheckBoxCompare;
        private LinearLayout mLinearLayoutEffective;
        private RelativeLayout mRelativeLayoutBeatDeal;

        public VersionViewHolder(View row) {
            super(row);

            txtViewBAsic = (TextView) row.findViewById(R.id.txt_title);
            textViewChannels = (TextView) row.findViewById(R.id.txt_chanels);
            //txtViewQuality = (TextView) row.findViewById(R.id.quality);
            txtViewMovieChannel = (TextView) row.findViewById(R.id.txt_movie_pack);
            txtViewMoney = (TextView) row.findViewById(R.id.txt_price);
            mCheckBoxCompare = (CheckBox) row.findViewById(R.id.plansubcriptioncheckbox);
            mView = row;

        }
    }
}