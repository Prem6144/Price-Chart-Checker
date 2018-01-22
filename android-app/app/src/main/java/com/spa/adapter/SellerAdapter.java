package com.spa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.spa.servicedealz.R;

import java.util.ArrayList;

/**
 * Created by E0115Diwakar on 2/17/2015.
 */

/***************************************************************************************
 * FileName : Seller_adapter.java
 * <p/>
 * Dependencies :Seller,Seller_fragment
 * <p/>
 * Description : Show all the item in listview.
 ***************************************************************************************/
public class SellerAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mDataList = new ArrayList<String>();


    public SellerAdapter(Context context, ArrayList<String> completed_list) {
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        RecordHolder holder = null;
        View row = convertView;
        try {
            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            row = mInflater.inflate(R.layout.seller_adapter, null);

            holder = new RecordHolder();
            holder.mTextViewSellerName = (TextView) row.findViewById(R.id.txt_seller_name);
            row.setTag(holder);
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        holder.mTextViewSellerName.setText(mDataList.get(position));
        return row;
    }

    class RecordHolder {
        TextView mTextViewSellerName;


    }
}
