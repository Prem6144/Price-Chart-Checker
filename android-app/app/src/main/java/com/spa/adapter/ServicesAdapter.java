package com.spa.adapter;

import android.app.Activity;
import android.content.Context;
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
 * FileName : Services_adapter.java
 * <p/>
 * Dependencies :Service_fragment,MainActivity,Main_fragment,
 * <p/>
 * Description : Show all the item in Gridtview.
 ***************************************************************************************/
public class ServicesAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> mData = new ArrayList<>();
    private ArrayList<String> mCompletedServicesList = new ArrayList<>();

    public ServicesAdapter(Context context, ArrayList<String> completed_list, ArrayList<String> c_list) {
        this.mContext = context;
        this.mData = completed_list;
        this.mCompletedServicesList = c_list;
        mData.remove("6");
        mData.remove("7");
        mData.remove("8");

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
            if (row == null) {
                LayoutInflater mInflater = (LayoutInflater)
                        mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                row = mInflater.inflate(R.layout.services_adapter, null);
                holder = new RecordHolder();
                holder.mImageViewService = (ImageView) row.findViewById(R.id.img_internet);
                holder.mTextViewServiceName = (TextView) row.findViewById(R.id.txt_cat_name);
                row.setTag(holder);
            } else {
                holder = (RecordHolder) row.getTag();
            }
           /* if (mData.get(position).equals(mContext.getResources().getString(R.string.internet_id))) {
                holder.mImageViewService.setBackgroundResource(R.drawable.internet);
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.internet));
                if (mCompletedServicesList.contains(mContext.getResources().getString(R.string.internet_id))) {
                    holder.mImageViewService.setImageResource(R.drawable.completed);
                } else {
                    holder.mImageViewService.setImageResource(R.drawable.internet);
                }
            } else if (mData.get(position).equals(mContext.getResources().getString(R.string.telephone_id))) {
                holder.mImageViewService.setBackgroundResource(R.drawable.telephoneicon);
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.telephone));
                if (mCompletedServicesList.contains(mContext.getResources().getString(R.string.telephone_id))) {
                    holder.mImageViewService.setImageResource(R.drawable.completed);
                } else {
                    holder.mImageViewService.setImageResource(R.drawable.telephoneicon);
                }

            }*/
            /* else if (mDataList.get(position).equals(mContext.getResources().getText(R.string.gas_id))) {
                holder.mImageViewService.setBackgroundResource(R.drawable.gas);
                holder.mTextViewServiceName.setText("Gas");
                if (count > position) {
                    holder.mImageViewService.setImageResource(R.drawable.completed);
                }
            } else if (mDataList.get(position).equals(mContext.getResources().getText(R.string.electricity_id))) {
              holder.mImageViewService.setBackgroundResource((R.drawable.electricity));
                holder.mTextViewServiceName.setText("Electricity");
                if (count > position) {
                    holder.mImageViewService.setImageResource(R.drawable.completed);
                }
            } */ /*else if (mData.get(position).equals(mContext.getResources().getString(R.string.homesecurity_id))) {
                holder.mImageViewService.setBackgroundResource((R.drawable.homesecurity));
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.home_securuty));
                if (mCompletedServicesList.contains(mContext.getResources().getString(R.string.homesecurity_id))) {
                    holder.mImageViewService.setImageResource(R.drawable.completed);
                } else {
                    holder.mImageViewService.setImageResource(R.drawable.homesecurity);
                }
            }*//* else if (mData.get(position).equals(mContext.getResources().getString(R.string.cable_id))) {
                holder.mImageViewService.setBackgroundResource((R.drawable.cable));
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.cable));
                if (mCompletedServicesList.contains(mContext.getResources().getString(R.string.cable_id))) {
                    holder.mImageViewService.setImageResource(R.drawable.completed);
                } else {
                    holder.mImageViewService.setImageResource(R.drawable.cable);
                }
            } else if (mData.get(position).equals(mContext.getResources().getString(R.string.cellphone_id))) {
                holder.mImageViewService.setBackgroundResource((R.drawable.cellphone));
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.cell_phone));
                if (mCompletedServicesList.contains(mContext.getResources().getString(R.string.cellphone_id))) {
                    holder.mImageViewService.setImageResource(R.drawable.completed);
                } else {
                    holder.mImageViewService.setImageResource(R.drawable.cellphone);
                }
            } else if (mData.get(position).equals(mContext.getResources().getString(R.string.bundle_id))) {
                holder.mTextViewServiceName.setText(mContext.getResources().getString(R.string.bundle));
                holder.mImageViewService.setBackgroundResource((R.drawable.bundleicon));
                if (mCompletedServicesList.contains(mContext.getResources().getString(R.string.bundle_id))) {
                    holder.mImageViewService.setImageResource(R.drawable.completed);
                } else {
                    holder.mImageViewService.setImageResource(R.drawable.bundleicon);
                }
            }*/
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return row;
    }

    class RecordHolder {
        ImageView mImageViewService;
        TextView mTextViewServiceName;


    }
}
