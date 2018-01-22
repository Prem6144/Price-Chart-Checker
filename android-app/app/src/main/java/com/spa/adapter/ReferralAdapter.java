package com.spa.adapter;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spa.servicedealz.R;
import com.spa.model.orderhistory.MyEarnings;
import com.squareup.picasso.Picasso;

/**
 * Created by Diwakar on 5/27/2016.
 */
public class ReferralAdapter extends BaseAdapter {
    private Context mContext;
    private MyEarnings mDataList;

    public ReferralAdapter(Context context, MyEarnings gIftHistory) {
        this.mContext = context;
        this.mDataList = gIftHistory;

    }

    @Override
    public int getCount() {

        return mDataList.getAccountReferral().size();


    }

    @Override
    public Object getItem(int position) {
        return mDataList.getAccountReferral().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecordHolder holder = null;
        View row = convertView;
        try {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            row = mInflater.inflate(R.layout.refer_adapter, null);

            holder = new RecordHolder();
            holder.mTextViewGiftPrice = (TextView) row.findViewById(R.id.txt_refer_price);
            holder.mTextViewCountActiveDeal = (TextView) row.findViewById(R.id.txt_count_active_deal);
            holder.mRelativeLayoutGift = (RelativeLayout) row.findViewById(R.id.ll_gift);
            holder.mImageViewbackground = (ImageView) row.findViewById(R.id.img_refer);
            holder.mLinearLayout = (LinearLayout) row.findViewById(R.id.ll_doller);
            row.setTag(holder);
            Picasso.with(mContext)
                    .load(mDataList.getAccountReferral().get(position).getReferImage()).placeholder(R.drawable.progress_animation)
                    .into(holder.mImageViewbackground);
            holder.mTextViewGiftPrice.setText(mDataList.getAccountReferral().get(position).getReferMessage());

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return row;
    }

    class RecordHolder {
        private ImageView mImageViewbackground;
        private TextView mTextViewGiftPrice, mTextViewCountActiveDeal;
        private RelativeLayout mRelativeLayoutGift;
        private LinearLayout mLinearLayout;
    }
}