package com.spa.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.spa.servicedealz.R;

import co.spa.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Diwakar on 4/8/2015.
 */
/**
 * FileName : InviteFragment
 * Description :
 * Dependencies : Internet
 */
public class InviteFragment extends Fragment implements ScreenShotable, View.OnClickListener {
    private float rating;
    private View containerView;
    Boolean isInternetPresent = false;
    TextView txt_price, txt_deal_detail_title, txt_short_description, txt_about, txt_reviews;

    LinearLayout ll_star_rating;
    Button btn_cancel, btn_ok, btn_comment;
    ImageView img_share;
    String rating_deal;
    RatingBar ratingBar2;
    ImageView img_ot_invite;

    public static InviteFragment newInstance() {
        InviteFragment contentFragment = new InviteFragment();

        return contentFragment;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.containerView = view.findViewById(R.id.container);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // res = getArguments().getInt(Integer.class.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.invite, container, false);
        //==========================================================================================
        img_ot_invite = (ImageView) rootView.findViewById(R.id.img_ot_invite);
        img_ot_invite.setOnClickListener(this);
        //==========================================================================================


        return rootView;
    }

    @Override
    public void takeScreenShot() {


    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_ot_invite: {


               /* Intent in = new Intent(getActivity(), Invite_other.class);
                startActivity(in);*/
            }
        }
    }


}






