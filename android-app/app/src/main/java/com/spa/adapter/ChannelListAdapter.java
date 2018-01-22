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
import java.util.List;

/**
 * Created by Diwakar on 4/23/2016.
 */
public class ChannelListAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mDataList = new ArrayList<>();


    public ChannelListAdapter(Context context, List<String> completed_list) {
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
            row = mInflater.inflate(R.layout.channellistadapter, null);

            holder = new RecordHolder();
            holder.mTextViewSellerName = (TextView) row.findViewById(R.id.txt_channelname);
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
