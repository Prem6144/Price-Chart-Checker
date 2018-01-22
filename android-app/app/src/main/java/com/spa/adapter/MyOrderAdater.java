package com.spa.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spa.servicedealz.R;
import com.spa.model.myorder.MyOrder;
import com.squareup.picasso.Picasso;

/**
 * Created by Diwakar on 5/19/2016.
 */
public class MyOrderAdater extends BaseAdapter {
    private Context mContext;
    private MyOrder mDataList;

    public MyOrderAdater(Context context, MyOrder myOrder) {
        this.mContext = context;
        this.mDataList = myOrder;

    }

    @Override
    public int getCount() {

        return mDataList.getOrder().size();


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
    public View getView(final int position, View convertView, ViewGroup parent) {
        RecordHolder holder = null;
        View row = convertView;
        try {

            LayoutInflater mInflater = (LayoutInflater)
                    mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            row = mInflater.inflate(R.layout.my_order, null);

            holder = new RecordHolder();
            holder.mRelativeLayout = (RelativeLayout) row.findViewById(R.id.rl_order_top);
            holder.mTextViewOrderId = (TextView) row.findViewById(R.id.txt_orderid);
            holder.mTextViewDateTime = (TextView) row.findViewById(R.id.txt_datetime);
            holder.mTextViewTitle = (TextView) row.findViewById(R.id.txt_title);
            holder.mTextViewPrice = (TextView) row.findViewById(R.id.txt_price);
            holder.mTextViewActivationStatus = (TextView) row.findViewById(R.id.txt_activationstatus);
            // holder.mButtonRepeatAgain = (Button) row.findViewById(R.id.btn_repeat_again);
            holder.mImageViewProvider = (ImageView) row.findViewById(R.id.img_provider);
            holder.mTextViewShortDesc = (TextView) row.findViewById(R.id.txt_shortdesc);
            row.setTag(holder);


            holder.mTextViewShortDesc.setText(mDataList.getOrder().get(position).getDeal().getShortDescription().toString());
            holder.mTextViewOrderId.setText(mDataList.getOrder().get(position).getOrderId().toString());
            holder.mTextViewDateTime.setText(mDataList.getOrder().get(position).getOrderPlaceTime().toString());
            holder.mTextViewTitle.setText(mDataList.getOrder().get(position).getDeal().getTitle().toString());

            if (mDataList.getOrder().get(position).getEffectivePrice().toString().equalsIgnoreCase("0.0")) {
                holder.mTextViewPrice.setText("$" + String.format("%.2f", Double.parseDouble(mDataList.getOrder().get(position).getDealPrice())));
            } else {
                if (mDataList.getOrder().get(position).getEffectivePrice().toString().equalsIgnoreCase("0")) {
                    holder.mTextViewPrice.setText("$" + String.format("%.2f", Double.parseDouble( mDataList.getOrder().get(position).getDealPrice())));
                } else {
                    holder.mTextViewPrice.setText("$" + String.format("%.2f",  Double.parseDouble(mDataList.getOrder().get(position).getEffectivePrice())));
                }
            }


            holder.mTextViewActivationStatus.setText(mDataList.getOrder().get(position).getStatus().toString());
            Picasso.with(mContext)
                    .load(mDataList.getOrder().get(position).getDeal().getDealImageUrl().toString()).placeholder(R.drawable.progress_animation)
                    .into(holder.mImageViewProvider);
            holder.mRelativeLayout.setTag(position);
            /*holder.mRelativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = mDataList.getOrder().get(position).getId();
                    Intent intent = new Intent(mContext, OrderDealDetailActivity.class);
                    intent.putExtra("orderid", pos);

                    intent.putExtra("userid", mDataList.getOrder().get(position).getAppUserId());
                    mContext.startActivity(intent);
                }
            });*/
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return row;
    }

    private static class RecordHolder {
        private TextView mTextViewOrderId, mTextViewDateTime, mTextViewShortDesc,
                mTextViewTitle, mTextViewPrice, mTextViewActivationStatus;
        private Button mButtonRepeatAgain;
        private RelativeLayout mRelativeLayout;
        private ImageView mImageViewProvider;
    }
}
