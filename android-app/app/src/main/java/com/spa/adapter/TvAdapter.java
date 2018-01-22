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
public class TvAdapter extends RecyclerView.Adapter<TvAdapter.VersionViewHolder> {

    CustemiseCable channelPackagesBeen = null;
    Activity activity;
    String dealattributesID;
    double value1;
    private String otherPrice, mChannelName;
    String mTvAdapterId;
    float a;
    ArrayList<Boolean> arrayList2;

    public TvAdapter(Activity a, CustemiseCable channelPackagesBeen1, ArrayList<Boolean> arrayList2) {
        this.arrayList2 = arrayList2;
        this.channelPackagesBeen = channelPackagesBeen1;
        this.activity = a;
        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("TvAdapterChannelName", "");
        editor.putString("TvAdapterPrice", "");
        editor.commit();

    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cutom_tv, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {

        // Set Data in your views comes from CollectionClass
        try {
            versionViewHolder.txtViewMoney
                    .setText("$" + channelPackagesBeen.getDeals().getDealAttributes().get(i).getAmount() + "/mo");
            versionViewHolder.textViewChannels
                    .setText(channelPackagesBeen.getDeals().getDealAttributes().get(i).getChannelCount() + "+ Channels");
            /*versionViewHolder.txtViewBAsic
                    .setText((CharSequence) channelPackagesBeen.getDeals().getDealAttributes().get(i).getTitle());*/
            versionViewHolder.txtViewBAsic
                    .setText(channelPackagesBeen.getDeals().getDealAttributes().get(i).getDescription());
            versionViewHolder.txtViewQuality.setText("" + channelPackagesBeen.getDeals().getDealAttributes().get(i).getHdChannels() + "+ HD");

//            versionViewHolder.mCheckBoxCompare.setTag(channelPackagesBeen.getDeals().getDealAttributes().get(i));

//            versionViewHolder.mCheckBoxCompare.setOnCheckedChangeListener(null);
            versionViewHolder.mCheckBoxCompare.setChecked(arrayList2.get(i));
            versionViewHolder.mCheckBoxCompare.setTag(i);
            versionViewHolder.mCheckBoxCompare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = (int) v.getTag();
                    SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    if (versionViewHolder.mCheckBoxCompare.isChecked()) {

                        Double value2 = 0.0;
                        if (arrayList2.contains(true)) {
                            int pos = arrayList2.indexOf(true);
                            value2 = Double.parseDouble(channelPackagesBeen.getDeals().getDealAttributes().get(pos).getAmount());
                        }
                        value1 = Double.parseDouble(channelPackagesBeen.getDeals().getDealAttributes().get(position).getAmount());
                        otherPrice = String.valueOf(channelPackagesBeen.getDeals().getDealAttributes().get(position).getAmount());
                        mChannelName = (channelPackagesBeen.getDeals().getDealAttributes().get(position).getDescription());
                        editor.putString("TvAdapterChannelName", mChannelName);
                        editor.putString("TvAdapterPrice", otherPrice);
                        value1 = Math.round(value1 * 100.0) / 100.0;
                        CableDealDeatilActivity.value = Math.round(CableDealDeatilActivity.value * 100.0) / 100.0;
                        CableDealDeatilActivity.value = CableDealDeatilActivity.value + value1 - value2;
                        DecimalFormat f = new DecimalFormat("##.00");
                        CableDealDeatilActivity.mTextViewEffectivePrice.setText("$" + f.format(CableDealDeatilActivity.value));
                        mTvAdapterId = "" + channelPackagesBeen.getDeals().getDealAttributes().get(position).getId();
                        editor.putString("TvAdapterId", mTvAdapterId);
                        editor.commit();
                        if (arrayList2.contains(true)) {
                            int i1 = arrayList2.indexOf(true);
                            arrayList2.remove(i1);
                            arrayList2.add(i1, false);

                        }
                        arrayList2.remove(position);
                        arrayList2.add(position, true);
                        notifyDataSetChanged();
                    } else {
                        editor.putString("TvAdapterChannelName", "");
                        editor.putString("TvAdapterPrice", "");
                        editor.commit();
                        if (arrayList2.contains(true)) {
                            int index = arrayList2.indexOf(true);

                            value1 = Double.parseDouble(channelPackagesBeen.getDeals().getDealAttributes().get(index).getAmount());
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
                        arrayList2.remove(position);
                        arrayList2.add(position, false);
                        notifyDataSetChanged();
                    }
                }
            });
//            versionViewHolder.mCheckBoxCompare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    if (buttonView.isChecked()){
//
//                        otherPrice= channelPackagesBeen.getDeals().getDealAttributes().get(i).getAmount();
//                        a=Float.parseFloat(otherPrice);
//
//                        SharedPreferences sharedPreferences=activity.getSharedPreferences(Constant.SHARED_PREF, Activity.MODE_WORLD_READABLE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        dealattributesID= String.valueOf(channelPackagesBeen.getDeals().getDealAttributes().get(i).getId());
//
//                        editor.putString("DealattributesID", dealattributesID);
//                        editor.commit();
//
//                        CableDealDeatilActivity.value=CableDealDeatilActivity.value+a;
//                        CableDealDeatilActivity.textViewEffectivePrice.setText("$" + String.format("%.2f",CableDealDeatilActivity.value));
//
//                     //   Toast.makeText(,"checked",Toast.LENGTH_SHORT).show();
//                    }else {
//
//                        otherPrice= channelPackagesBeen.getDeals().getDealAttributes().get(i).getAmount();
//                        a=Float.parseFloat(otherPrice);
//
//                        CableDealDeatilActivity.value=CableDealDeatilActivity.value-a;
//                        CableDealDeatilActivity.textViewEffectivePrice.setText("$" +String.format("%.2f",CableDealDeatilActivity.value));
//                    }
//                    //notifyDataSetChanged();
//
//                }
//
//            });

            /*Picasso.with(activity).load(String.valueOf(cableServiceCust.getDeals().get(i).getImage().getUrl())).into(versionViewHolder.mImageViewVendorImage);*/
            // versionViewHolder.mTextViewbundleCombo.setText( cellphone.getDeals().get(i).getUrl());
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


    @Override
    public int getItemCount() {

        return channelPackagesBeen == null ? 0 : channelPackagesBeen.getDeals().getDealAttributes().size();
    }


    public static class VersionViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        public TextView txtViewBAsic, textViewChannels, txtViewQuality, txtViewMovieChannel, txtViewMoney;
        private ImageView mImageViewVendorImage, mImageViewSocialSharing, mImageViewBestDealImage;
        private RatingBar mRatingDeal;
        public CheckBox mCheckBoxCompare;
        private LinearLayout mLinearLayoutEffective;
        private RelativeLayout mRelativeLayoutBeatDeal;

        public VersionViewHolder(View row) {
            super(row);

            txtViewBAsic = (TextView) row.findViewById(R.id.basic);
            textViewChannels = (TextView) row.findViewById(R.id.channel);
            txtViewQuality = (TextView) row.findViewById(R.id.quality);
            txtViewMovieChannel = (TextView) row.findViewById(R.id.premimum_movie);
            txtViewMoney = (TextView) row.findViewById(R.id.money);
            mCheckBoxCompare = (CheckBox) row.findViewById(R.id.checkbox_tv);
            mView = row;

        }
    }
}


