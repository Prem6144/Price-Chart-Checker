package com.spa.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.spa.model.orderdetail.OrderDetail;
import com.spa.servicedealz.R;

/**
 * Created by Narendra on 10/24/2016.
 */

public class OrderDealDetailListAdapter extends RecyclerView.Adapter<OrderDealDetailListAdapter.VersionViewHolder> {
    Activity activity;
    OrderDetail orderDetail;
    TextView mOrderEquipementCellName, mOrderEquipementBrandName, mOrderEquipementPrice, mOrderEquipementColorList, mOrderPlanName, mOrderPlanPrice, mOrderExtraServiceName, mOrderExtraPrice;
    String mName,BrandName,colorname,price,OrderPlanname,OrderPlanPrice ;
//    private LinearLayout mLinearLayoutLegelOrder, mLinearMainEqupements,mLinearLayoutEquipments, mLinearLayoutAttributesDetail, mLinearLayoutExtraServiceDetailHeading, mLinearLayoutAttributesDetailHeding, mLinearLayoutExtraServiceDetail;
//    private RelativeLayout mLinearLayoutEquipementsDetail;

    public OrderDealDetailListAdapter(Activity a, OrderDetail orderDetail) {

        this.orderDetail = orderDetail;
        this.activity = a;

    }


    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orderadditionallayout, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(VersionViewHolder holder, int position) {
        if (orderDetail.getOrderEquipments().size() > 0) {
                mName =orderDetail.getOrderEquipments().get(position).getName();
                mOrderEquipementCellName.setText(mName);
                BrandName =orderDetail.getOrderEquipments().get(position).getBrand();
                mOrderEquipementBrandName.setText(BrandName);
                price =orderDetail.getOrderEquipments().get(position).getPrice();
                if(price != null) {
                    mOrderEquipementPrice.setText(price);
                }else{
                    Toast.makeText(activity,"Price Is null",Toast.LENGTH_SHORT).show();
                }
              colorname =orderDetail.getOrderEquipments().get(position).getColorName();
              mOrderEquipementColorList.setText(colorname);

        }

////
    }


    @Override
    public int getItemCount() {
        return orderDetail == null ? 0 : orderDetail.getOrderEquipments().size();
    }

    class VersionViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewOrderDeal;
        public VersionViewHolder(View itemView) {

            super(itemView);
            mOrderEquipementCellName = (TextView)itemView.findViewById(R.id.ordersumrytxt_ordertitle);
            mOrderEquipementBrandName = (TextView) itemView.findViewById(R.id.txt_brandname);
            mOrderEquipementPrice = (TextView) itemView.findViewById(R.id.equipements_price);
            mOrderEquipementColorList = (TextView) itemView.findViewById(R.id.ordersumrytxt_colorname);
            mOrderPlanName = (TextView) itemView.findViewById(R.id.dealattributes_planplansubscription);
            mOrderPlanPrice = (TextView) itemView.findViewById(R.id.dealattributes_planplansubscription_price);
            mOrderExtraServiceName = (TextView)itemView. findViewById(R.id.dealattributes_plan);
            mOrderExtraPrice = (TextView)itemView. findViewById(R.id.dealattributes_plan_price);

        }
    }
}
