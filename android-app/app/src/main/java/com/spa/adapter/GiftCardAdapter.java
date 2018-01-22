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

import com.spa.model.giftcardorder.Giftcardorder;
import com.spa.servicedealz.R;
import com.spa.model.giftcard.GiftCard;
import com.spa.utils.Jsondata;
import com.squareup.picasso.Picasso;

/**
 * Created by Diwakar on 5/25/2016.
 */
public class GiftCardAdapter extends BaseAdapter {
    private Context mContext;
    private Giftcardorder mDataList;

    public GiftCardAdapter(Context context) {
        this.mContext = context;
        this.mDataList = Jsondata.giftcardorder;

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
            row = mInflater.inflate(R.layout.gift_card_adapter, null);

            holder = new RecordHolder();
            holder.mImageViewbackground = (ImageView) row.findViewById(R.id.img_giftcard);
            row.setTag(holder);
            Picasso.with(mContext)
                    .load(mDataList.getGifts().get(position).getGiftImageUrl().toString())
                    .into(holder.mImageViewbackground);
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