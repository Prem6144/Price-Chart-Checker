package com.spa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spa.servicedealz.R;

/**
 * Created by E0115Diwakar on 2/17/2015.
 */

/***************************************************************************************
 * FileName : Empty_dasboard_adapter.java
 * <p/>
 * Dependencies :Dashboard,ContentFragment
 * <p/>
 * Description : Show all the item in listview.
 ***************************************************************************************/
public class EmptyDashboardAdapter extends BaseAdapter {
    private Context mContext;
    int[] image_array = null; //{R.drawable.internet_deal, R.drawable.cellphone, R.drawable.gas_deal, R.drawable.electricity_deal, R.drawable.homesecurity_deal, R.drawable.cable_deal, R.drawable.telephone, R.drawable.bundle};
    // List<String> mDataList;

    public EmptyDashboardAdapter(Context context) {
        this.mContext = context;


    }

    @Override
    public int getCount() {

        return image_array.length;


    }

    @Override
    public Object getItem(int position) {
        return position;
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

            // resultp = mDataList.get(position);
            if (row == null) {
                LayoutInflater mInflater = (LayoutInflater)
                        mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                row = mInflater.inflate(R.layout.empty_dashboard_adapter, null);

                holder = new RecordHolder();
                holder.txt_category_name = (TextView) row.findViewById(R.id.txt_category_name);


                holder.img_services = (ImageView) row.findViewById(R.id.img_services1);


                row.setTag(holder);
            } else {
                holder = (RecordHolder) row.getTag();
            }


            String service_vender = String.valueOf(position + 1);
         /*   if (service_vender.equalsIgnoreCase(String.valueOf(mContext.getResources().getText(R.string.internet_id)))) {

                holder.txt_category_name.setText("Internet");
                holder.img_services.setImageResource(image_array[0]);
            } else if (service_vender.equalsIgnoreCase(String.valueOf(mContext.getResources().getText(R.string.telephone_id)))) {
                holder.txt_category_name.setText("Telephone");
                holder.img_services.setImageResource(image_array[6]);

            } else if (service_vender.equalsIgnoreCase(String.valueOf(mContext.getResources().getText(R.string.gas_id)))) {

                holder.txt_category_name.setText("Gas");
                holder.img_services.setImageResource(image_array[2]);
            } else if (service_vender.equalsIgnoreCase(String.valueOf(mContext.getResources().getText(R.string.electricity_id)))) {

                holder.txt_category_name.setText("Electricity");
                holder.img_services.setImageResource(image_array[3]);
            } else if (service_vender.equalsIgnoreCase(String.valueOf(mContext.getResources().getText(R.string.homesecurity_id)))) {

                holder.txt_category_name.setText("Home Security");
                holder.img_services.setImageResource(image_array[4]);
            } else if (service_vender.equalsIgnoreCase(String.valueOf(mContext.getResources().getText(R.string.cable_id)))) {

                holder.txt_category_name.setText("Cable");
                holder.img_services.setImageResource(image_array[5]);
            } else if (service_vender.equalsIgnoreCase(String.valueOf(mContext.getResources().getText(R.string.cellphone_id)))) {

                holder.txt_category_name.setText("Cellphone");
                holder.img_services.setImageResource(image_array[1]);
            } else if (service_vender.equalsIgnoreCase(String.valueOf(mContext.getResources().getText(R.string.bundle_id)))) {

                holder.txt_category_name.setText("Bundle");
                holder.img_services.setImageResource(image_array[7]);
            }*/


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }


        Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.push_left_in);
        row.startAnimation(animation);
        return row;
    }

    class RecordHolder {
        TextView txt_category_name, txt_expiry_date, txt_best_short_desc, txt_preferred_provider_short_desc, tv_best_offer_category_name, txt_best_offer, your_plan_price, best_deal_price, preferred_price, best_deal_save_price, preferred_save_price;
        ImageView img_best0, img_preferred_provider0, img_services;
        RelativeLayout rl_best_deal_detail, rl_best_save, rl_preferred_provider_deal, rl_best, rl_preferred_provider_deal_detail, rl_preferred_provider_save;


    }
}
