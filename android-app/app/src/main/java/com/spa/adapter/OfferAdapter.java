package com.spa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spa.servicedealz.R;
import com.spa.model.deallist.DealListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diwakar on 5/11/2016.
 */
public class OfferAdapter extends BaseAdapter {
    private Context mContext;
    private List<DealListItem.DealEntity.DealAdditionalOffersEntity> mDataList = new ArrayList<>();

    public OfferAdapter(Context context, List<DealListItem.DealEntity.DealAdditionalOffersEntity> completed_list) {
        this.mContext = context;
        this.mDataList = completed_list;

    }

    @Override
    public int getCount() {

        return mDataList.size();


    }

    @Override
    public Object getItem(int i) {
        return null;
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecordHolder holder = null;
        View row = convertView;
        try {
            if (row == null) {
                LayoutInflater mInflater = (LayoutInflater)
                        mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                row = mInflater.inflate(R.layout.additional_offer_layout, null);

                holder = new RecordHolder();
                holder.mTextViewAdditionalOfferTitle = (TextView) row.findViewById(R.id.txt_offertitle);
                holder.mTextViewOfferPrice = (TextView) row.findViewById(R.id.txt_offer_price);
                holder.mTextViewAdditionalOfferDetail = (TextView) row.findViewById(R.id.txt_important_detail);
                row.setTag(holder);
            } else {
                holder = (RecordHolder) row.getTag();
            }
            holder.mTextViewAdditionalOfferTitle.setText(mDataList.get(position).getTitle().toString());
            holder.mTextViewOfferPrice.setText("$" + mDataList.get(position).getPrice().toString());
            holder.mTextViewAdditionalOfferDetail.setText(mDataList.get(position).getDescription().toString());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return row;
    }

    private static class RecordHolder {
        private TextView mTextViewAdditionalOfferTitle, mTextViewAdditionalOfferDetail,
                mTextViewAdditionalOffer, mTextViewOfferPrice;
    }
}
