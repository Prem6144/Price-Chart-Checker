package com.spa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spa.servicedealz.R;

import java.util.ArrayList;

/**
 * Created by Diwakar on 4/24/2015.
 */

/***************************************************************************************
 * FileName : Seller_user_adapter.java
 * <p/>
 * Dependencies :Seller_user_detail,
 * <p/>
 * Description : Show all the item in listview.
 ***************************************************************************************/
public class SellerUserAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mData = new ArrayList<String>();


    public SellerUserAdapter(Context context, ArrayList<String> completed_list) {
        this.mContext = context;
        this.mData = completed_list;

    }

    @Override
    public int getCount() {

        return mData.size();


    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
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
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            row = mInflater.inflate(R.layout.seller_user_detail_adapter, null);
            holder = new RecordHolder();
            holder.mTextViewSellerNumber = (TextView) row.findViewById(R.id.txt_s_number);
            holder.LinearLayoutSeller = (LinearLayout) row.findViewById(R.id.ll_seller);
            holder.mTextViewSellerName = (TextView) row.findViewById(R.id.txt_name);
            row.setTag(holder);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        if (position % 2 == 0) {
            holder.LinearLayoutSeller.setBackgroundResource(R.drawable.seller_background);
        }
        holder.mTextViewSellerName.setText(mData.get(position));
        int pos = position + 1;
        holder.mTextViewSellerNumber.setText(pos + ".");
        return row;
    }

    class RecordHolder {
        LinearLayout LinearLayoutSeller;
        TextView mTextViewSellerNumber, mTextViewSellerName;


    }
}
