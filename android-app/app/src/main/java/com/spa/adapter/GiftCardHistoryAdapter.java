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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spa.servicedealz.R;
import com.spa.model.orderhistory.MyEarnings;

/**
 * Created by Diwakar on 4/13/2016.
 */
public class GiftCardHistoryAdapter extends BaseAdapter {
    private Context mContext;
    private MyEarnings mDataList;
    private int count = 0;
    private int[] textureArrayWin = {
            R.drawable.giftbg1,
            R.drawable.giftbg2,
            R.drawable.giftbg3,
    };

    public GiftCardHistoryAdapter(Context context, MyEarnings gIftHistory) {
        this.mContext = context;
        this.mDataList = gIftHistory;
        count = 0;

    }

    @Override
    public int getCount() {

        return mDataList.getGifts().size();


    }

    @Override
    public Object getItem(int position) {
        return mDataList.getGifts().get(position);
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
            row = mInflater.inflate(R.layout.gift_card_history_adapter, null);

            holder = new RecordHolder();
            holder.mTextViewGiftPrice = (TextView) row.findViewById(R.id.txt_gift_price);
            holder.mTextViewCountActiveDeal = (TextView) row.findViewById(R.id.txt_count_active_deal);
            holder.mRelativeLayoutGift = (RelativeLayout) row.findViewById(R.id.ll_gift);
            holder.mImageViewbackground = (ImageView) row.findViewById(R.id.img_background);
            row.setTag(holder);
            if (count == 3) {
                count = 0;
                holder.mImageViewbackground.setBackgroundResource(textureArrayWin[count]);
            } else {
                holder.mImageViewbackground.setBackgroundResource(textureArrayWin[count]);
                count++;
            }
            holder.mTextViewGiftPrice.setText("$" + mDataList.getGifts().get(position).getAmount());

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
    }
}