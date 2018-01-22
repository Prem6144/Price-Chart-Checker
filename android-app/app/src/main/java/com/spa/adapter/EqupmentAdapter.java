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
public class EqupmentAdapter extends BaseAdapter

{
    private Context mContext;
    private List<DealListItem.DealEntity.DealEquipmentsEntity> mDataList = new ArrayList<>();
    String cat_id;

    public EqupmentAdapter(Context context, List<DealListItem.DealEntity.DealEquipmentsEntity> completed_list, String Cat_id) {
        this.mContext = context;
        this.mDataList = completed_list;
        this.cat_id = Cat_id;
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
                row = mInflater.inflate(R.layout.equipment_adapter, null);

                holder = new RecordHolder();
                holder.mTextViewAdditionalEquipmentTitle = (TextView) row.findViewById(R.id.txt_equipment_model);
                holder.mTextViewEquipmentPrice = (TextView) row.findViewById(R.id.txt_equipment_price);
                holder.mTextViewEquipmentMemory = (TextView) row.findViewById(R.id.txt_equipment_memory);
                holder.mTextViewMemory = (TextView) row.findViewById(R.id.txt_memory);
                row.setTag(holder);
            } else {
                holder = (RecordHolder) row.getTag();
            }
            if (!cat_id.equalsIgnoreCase(mContext.getResources().getString(R.string.cell_phone))) {
                holder.mTextViewMemory.setText("Installation");
                holder.mTextViewEquipmentMemory.setText(mDataList.get(position).getInstallation().toString());
            } else {
                holder.mTextViewEquipmentMemory.setText(mDataList.get(position).getMemory().toString());
            }

            holder.mTextViewAdditionalEquipmentTitle.setText(mDataList.get(position).getModel().toString());
            holder.mTextViewEquipmentPrice.setText("$" + mDataList.get(position).getPrice().toString());

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return row;
    }

    private static class RecordHolder {
        private TextView mTextViewAdditionalEquipmentTitle, mTextViewEquipmentPrice, mTextViewMemory,
                mTextViewEquipmentMemory;
    }
}
