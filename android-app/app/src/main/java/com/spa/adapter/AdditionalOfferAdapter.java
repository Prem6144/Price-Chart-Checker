package com.spa.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spa.model.orderdetail.OrderDetail;
import com.spa.model.placeorder.CustemCellphoneEquipmentsAdditionalOffers;
import com.spa.servicedealz.R;

/**
 * Created by Diwakar on 9/14/2016.
 */
public class AdditionalOfferAdapter extends RecyclerView.Adapter<AdditionalOfferAdapter.VersionViewHolder> {

    CustemCellphoneEquipmentsAdditionalOffers statusLevelListEntities;
    Activity activity;
    double value1;
    private String otherPrice;
    String deal_extra_service_id, service_name;
    OrderDetail orderDetail;

    public AdditionalOfferAdapter(Activity a, CustemCellphoneEquipmentsAdditionalOffers deals) {

        this.statusLevelListEntities = deals;
        this.activity = a;


    }


    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.orderadditionallayout, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, final int i) {

        // Set Data in your views comes from CollectionClass
        try {
            versionViewHolder.mTextViewPrice
                    .setText("$" + statusLevelListEntities.getDevicelist().get(i).getPhoneprice());
            versionViewHolder.mTextViewBrandName
                    .setText(statusLevelListEntities.getDevicelist().get(i).getPhonebrand());
            versionViewHolder.mTextViewTitle
                    .setText(statusLevelListEntities.getDevicelist().get(i).getPhonename());
            versionViewHolder.mTextViewColor
                    .setText(statusLevelListEntities.getDevicelist().get(i).getPhonecolor());
           /* versionViewHolder.mTextViewQuntity
                    .setText(statusLevelListEntities.getDevicelist().get(i).get());
*/

        } catch (Exception e) {

        }

    }


    @Override
    public int getItemCount() {

        return statusLevelListEntities == null ? 0 : statusLevelListEntities.getDevicelist().size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView mTextViewTitle, mTextViewBrandName, mTextViewPrice, mTextViewColor, mTextViewQuntity;
        private ImageView mImageViewVendorImage;

        public VersionViewHolder(View row) {
            super(row);
            mTextViewTitle = (TextView) row.findViewById(R.id.ordersumrytxt_ordertitle);
            mTextViewBrandName = (TextView) row.findViewById(R.id.txt_brandname);
            mTextViewColor = (TextView) row.findViewById(R.id.ordersumrytxt_colorname);

            mTextViewQuntity = (TextView) row.findViewById(R.id.ordersumrytxt_order_quantity);
            mTextViewPrice = (TextView) row.findViewById(R.id.equipements_price);
            mTextViewColor = (TextView) row.findViewById(R.id.ordersumrytxt_colorname);
            mImageViewVendorImage = (ImageView) row.findViewById(R.id.ordersumry_img_provider);
            mView = row;

        }
    }
}
