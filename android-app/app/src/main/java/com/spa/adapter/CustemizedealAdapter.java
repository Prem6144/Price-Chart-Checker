package com.spa.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.spa.model.custemizedeal.com.spa.model.celphonedeal.Custemizedeal;
import com.spa.servicedealz.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Diwakar on 9/14/2016.
 */
public class CustemizedealAdapter extends RecyclerView.Adapter<CustemizedealAdapter.VersionViewHolder> {

    Custemizedeal statusLevelListEntities;
    Activity activity;


    public CustemizedealAdapter(Activity a, Custemizedeal deals) {

        this.statusLevelListEntities = deals;
        this.activity = a;


    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bundlelistadapter, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(VersionViewHolder versionViewHolder, int i) {

        // Set Data in your views comes from CollectionClass
        try {
            versionViewHolder.mTextViewPrice
                    .setText("$" + statusLevelListEntities.getDeals().get(i).getPrice());
            versionViewHolder.mTextViewDescription
                    .setText(statusLevelListEntities.getDeals().get(i).getShortDescription());
            versionViewHolder.mTextViewTitle
                    .setText(statusLevelListEntities.getDeals().get(i).getTitle());
            Picasso.with(activity).load(statusLevelListEntities.getDeals().get(i).getImage().getUrl()).into(versionViewHolder.mImageViewVendorImage);
            versionViewHolder.mTextViewbundleCombo.setText("" + statusLevelListEntities.getDeals().get(i).getServiceCategoryName());
        } catch (Exception e) {

        }

    }


    @Override
    public int getItemCount() {

        return statusLevelListEntities == null ? 0 : statusLevelListEntities.getDeals().size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView mTextViewTitle, mTextViewDescription, mTextViewPrice, mTextViewbundleCombo, mTextViewExpiryDate,
                mTextViewSavePrice, mTextViewEffectivePrice;
        private ImageView mImageViewVendorImage, mImageViewSocialSharing, mImageViewBestDealImage;
        private RatingBar mRatingDeal;
        private CheckBox mCheckBoxCompare;
        private LinearLayout mLinearLayoutEffective;
        private RelativeLayout mRelativeLayoutBeatDeal;

        public VersionViewHolder(View row) {
            super(row);

            // mTextViewEffectivePrice = (TextView) row.findViewById(R.id.txt_effective_deal_price);
            //mLinearLayoutEffective = (LinearLayout) row.findViewById(R.id.ll_effective);
            // mImageViewBestDealImage = (ImageView) row.findViewById(R.id.img_bestdeal);
            mImageViewVendorImage = (ImageView) row.findViewById(R.id.img_company);
            //  mImageViewSocialSharing = (ImageView) row.findViewById(R.id.img_share);
            // mRatingDeal = (RatingBar) row.findViewById(R.id.ratingBar_deal);
            // mTextViewSavePrice = (TextView) row.findViewById(R.id.txt_save);
            // mButtonActivate = (Button) row.findViewById(R.id.txt_activate);
            // mTextViewExpiryDate = (TextView) row.findViewById(R.id.txt_expiry_date);
            mTextViewbundleCombo = (TextView) row.findViewById(R.id.txt_bundlecombo);
            // mCheckBoxCompare = (CheckBox) row.findViewById(R.id.chk_comparedeal);
            mTextViewTitle = (TextView) row.findViewById(R.id.txt_deal_title);
            mTextViewDescription = (TextView) row.findViewById(R.id.txt_deal_description);
            mTextViewPrice = (TextView) row.findViewById(R.id.txt_price);
            // mRelativeLayoutBeatDeal = (RelativeLayout) row.findViewById(R.id.rl_top);
            mView = row;

        }
    }
}
