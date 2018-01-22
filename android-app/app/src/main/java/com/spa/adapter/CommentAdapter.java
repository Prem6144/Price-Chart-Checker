package com.spa.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.spa.servicedealz.R;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by E0115Diwakar on 2/17/2015.
 */

/***************************************************************************************
 * FileName : Comment_adapter.java
 * <p/>
 * Dependencies :Deal_Detail_Activity
 * <p/>
 * Description : Show all the item in listview.
 ***************************************************************************************/
public class CommentAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<HashMap<String, String>> mDataList = new ArrayList<HashMap<String, String>>();

    public CommentAdapter(Context context, ArrayList<HashMap<String, String>> comment) {
        this.mContext = context;
        this.mDataList = comment;
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
            if (row == null) {
                LayoutInflater mInflater = (LayoutInflater)
                        mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                row = mInflater.inflate(R.layout.comment_adapter, null);
                holder = new RecordHolder();
                holder.mRatingBar = (RatingBar) row.findViewById(R.id.ratingBar_comment);
                holder.mTextViewCommentTitle = (TextView) row.findViewById(R.id.txt_comment_title);
                holder.mTextViewComment = (TextView) row.findViewById(R.id.txt_comment);
                holder.mTextViewUserName = (TextView) row.findViewById(R.id.txt_comment_name);
                holder.mTextViewCommentDate = (TextView) row.findViewById(R.id.txt_comment_date);
                holder.mImageViewUser = (ImageView) row.findViewById(R.id.img_comment_user);
                row.setTag(holder);
            } else {
                holder = (RecordHolder) row.getTag();
            }
            if (mDataList.get(position).get("rating_point").toString().equalsIgnoreCase("")) {
                holder.mRatingBar.setRating((float) 0.00);
            } else {
                float f1 = Float.parseFloat(mDataList.get(position).get("rating_point").toString());
                holder.mRatingBar.setRating(f1);
            }
            if (mDataList.get(position).get("app_user_image_url").toString().equalsIgnoreCase("") || mDataList.get(position).get("app_user_image_url").toString().equals(null)) {

            } else {
                int rounded_value = 200;
                DisplayImageOptions options = new DisplayImageOptions.Builder()
                        .cacheInMemory(true).cacheOnDisc(true)
                        .displayer(new RoundedBitmapDisplayer(rounded_value))
                        .build();
                ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                        mContext).defaultDisplayImageOptions(options)
                        .build();
                ImageLoader.getInstance().init(config);

                ImageLoader.getInstance().displayImage(mDataList.get(position).get("app_user_image_url").toString(), holder.mImageViewUser, options);
            }
            if (mDataList.get(position).get("comment_title").toString().equalsIgnoreCase("")) {
                holder.mTextViewCommentTitle.setVisibility(View.GONE);
            } else {
                holder.mTextViewCommentTitle.setText(FirstCharacterCapital(mDataList.get(position).get("comment_title").toString()));
            }
            if (mDataList.get(position).get("comment_text").toString().equalsIgnoreCase("")) {
                holder.mTextViewComment.setVisibility(View.GONE);
            } else {
                holder.mTextViewComment.setText(FirstCharacterCapital(mDataList.get(position).get("comment_text").toString()));
            }
            LayerDrawable stars = (LayerDrawable) holder.mRatingBar.getProgressDrawable();
            stars.getDrawable(2).setColorFilter(mContext.getResources().getColor(R.color.yellow_color), PorterDuff.Mode.SRC_ATOP);
            stars.getDrawable(1)
                    .setColorFilter(mContext.getResources().getColor(R.color.ratingblankcolor),
                            PorterDuff.Mode.SRC_ATOP);
            holder.mTextViewCommentDate.setText(mDataList.get(position).get("comment_date").toString());

            holder.mTextViewUserName.setText(FirstCharacterCapital(mDataList.get(position).get("app_user_name").toString()));
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return row;
    }

    private String FirstCharacterCapital(String str) {
        StringBuilder sb = new StringBuilder(str);
        if (str.length() > 1) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
        return sb.toString();
    }

    class RecordHolder {
        private ImageView mImageViewUser;
        private RatingBar mRatingBar;
        private TextView mTextViewUserName, mTextViewComment, mTextViewCommentTitle, mTextViewCommentDate;


    }
}
