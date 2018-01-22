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

import com.spa.model.deallist.DealListItem;
import com.spa.servicedealz.R;
import com.squareup.picasso.Picasso;

public class BundleListAdapter extends RecyclerView.Adapter<BundleListAdapter.VersionViewHolder> {

    DealListItem statusLevelListEntities;
    Activity activity;


    public BundleListAdapter(Activity a, DealListItem deals) {

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
                    .setText("$" + statusLevelListEntities.getBundle_deals().get(i).getDeal_price());
            versionViewHolder.mTextViewDescription
                    .setText(statusLevelListEntities.getBundle_deals().get(i).getShort_description());
            versionViewHolder.mTextViewTitle
                    .setText(statusLevelListEntities.getBundle_deals().get(i).getTitle());
            Picasso.with(activity).load(statusLevelListEntities.getBundle_deals().get(i).getDeal_image_url()).into(versionViewHolder.mImageViewVendorImage);
            versionViewHolder.mTextViewbundleCombo.setText( statusLevelListEntities.getBundle_deals().get(i).getBundle_combo());
        } catch (Exception e) {

        }

    }


    @Override
    public int getItemCount() {

        return statusLevelListEntities == null ? 0 : statusLevelListEntities.getBundle_deals().size();
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

