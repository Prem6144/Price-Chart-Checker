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
import android.widget.TextView;

import com.spa.servicedealz.R;

import java.util.ArrayList;

/**
 * Created by E0115Diwakar on 2/17/2015.
 */

/***************************************************************************************
 * FileName : Auction_adapter.java
 * <p/>
 * Dependencies :Auction_category, Auction_category_fragment
 * <p/>
 * Description : Show all the item in listview.
 ***************************************************************************************/
public class AuctionAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mDataList = new ArrayList<String>();

    public AuctionAdapter(Context context, ArrayList<String> completed_list) {
        this.mContext = context;
        this.mDataList = completed_list;

    }

    @Override
    public int getCount() {

        return mDataList.size();


    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
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
            if (row == null) {
                LayoutInflater mInflater = (LayoutInflater)
                        mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                row = mInflater.inflate(R.layout.auction_adapter, null);

                holder = new RecordHolder();
                holder.mImageViewService = (ImageView) row.findViewById(R.id.img_internet);
                holder.mTextViewServiceName = (TextView) row.findViewById(R.id.txt_cat_name);
                row.setTag(holder);
            } else {
                holder = (RecordHolder) row.getTag();
            }
         /*   if (mDataList.get(position).equals(mContext.getResources().getString(R.string.internet_id))) {
                holder.mImageViewService.setBackgroundResource(R.drawable.auction_internet);
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.internet));

            } else if (mDataList.get(position).equals(mContext.getResources().getString(R.string.telephone_id))) {
                holder.mImageViewService.setBackgroundResource((R.drawable.auction_telephone));
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.telephone));

            } else if (mDataList.get(position).equals(mContext.getResources().getString(R.string.gas_id))) {
                holder.mImageViewService.setBackgroundResource((R.drawable.auction_gas));
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.gas));

            } else if (mDataList.get(position).equals(mContext.getResources().getString(R.string.electricity_id))) {
                holder.mImageViewService.setBackgroundResource((R.drawable.auction_electricity));
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.electricity));

            } else if (mDataList.get(position).equals(mContext.getResources().getString(R.string.homesecurity_id))) {
                holder.mImageViewService.setBackgroundResource((R.drawable.auction_homesecurity));
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.home_securuty));

            } else if (mDataList.get(position).equals(mContext.getResources().getString(R.string.cable_id))) {
                holder.mImageViewService.setBackgroundResource((R.drawable.auction_cable));
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.cable));

            } else if (mDataList.get(position).equals(mContext.getResources().getString(R.string.cellphone_id))) {
                holder.mImageViewService.setBackgroundResource((R.drawable.auction_cellphone));
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.cell_phone));

            } else if (mDataList.get(position).equals(mContext.getResources().getString(R.string.bundle_id))) {
                holder.mTextViewServiceName.setText(mContext.getResources().getText(R.string.bundle));
                holder.mImageViewService.setBackgroundResource((R.drawable.auction_bundle));

            }*/


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return row;
    }

    class RecordHolder {
        private ImageView mImageViewService;
        private TextView mTextViewServiceName;
    }
}
