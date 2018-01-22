package com.spa.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.spa.servicedealz.R;

import co.spa.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Diwakar on 4/8/2015.
 */
/**
 * FileName : InviteOtherFragment
 * Description :
 * Dependencies : Internet
 */
public class InviteOtherFragment extends Fragment implements ScreenShotable, View.OnClickListener {
    private float rating;
    private View containerView;
    Boolean isInternetPresent = false;
    TextView txt_price, txt_deal_detail_title, txt_short_description, txt_about, txt_reviews;
    //  private int[] stars = new int[]{R.id.rating_star_0, R.id.rating_star_1,
    //   R.id.rating_star_2, R.id.rating_star_3, R.id.rating_star_4};
    // private ImageView[] starViews = new ImageView[stars.length];
    LinearLayout ll_star_rating;
    Button btn_cancel, btn_ok, btn_comment;
    ImageView img_share;
    String rating_deal;
    RatingBar ratingBar2;

    public static InviteOtherFragment newInstance() {
        InviteOtherFragment contentFragment = new InviteOtherFragment();
        //  Bundle bundle = new Bundle();
        //  bundle.putInt(Integer.class.getName(), resId);
        //  contentFragment.setArguments(bundle);
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
        View rootView = inflater.inflate(R.layout.invite_other, container, false);
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
    }


    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                SharedPreferences sp = getActivity().getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                Intent in = getActivity().getIntent();
               // response = Jsondata.get_rating(sp.getString("app_user_id", ""), Internet_deal_fragment.DEAL_LIST.get(in.getIntExtra("deal_position", 0)).get("id").toString(), rating_deal, getActivity());

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress dialog
         * **/

        protected void onPostExecute(String response) {
            pDialog.dismiss();
            if (response.equalsIgnoreCase("true")) {
                Toast toast;
                toast = Toast.makeText(getActivity(), "Thank you.",
                        Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                ratingBar2.setRating(rating);
            } else if (response.equalsIgnoreCase("false")) {
                ShowDailog.Show_Alert_Login(getActivity(), "You already rate this deal.");
            } else {
                ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
            }
        }


    }
}






