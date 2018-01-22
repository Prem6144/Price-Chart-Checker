package com.spa.adapter;


import android.app.Activity;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.spa.servicedealz.R;

public class ViewPagerAdapter extends PagerAdapter {
    private Activity mActivity;
    private int mImageArray[];

    public ViewPagerAdapter(Activity act, int[] imgArra) {
        this.mImageArray = imgArra;
        this.mActivity = act;
    }

    public int getCount() {
        return mImageArray.length;
    }

    public Object instantiateItem(ViewGroup collection, int position) {
        LayoutInflater inflater = (LayoutInflater) mActivity
                .getSystemService(mActivity.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater
                .inflate(R.layout.activity_introscreen, collection, false);
        ImageView mImageView = (ImageView) itemView.findViewById(R.id.imageView);
        mImageView.setBackgroundResource(mImageArray[position]);
        ((ViewPager) collection).addView(itemView, 0);
        return itemView;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}
