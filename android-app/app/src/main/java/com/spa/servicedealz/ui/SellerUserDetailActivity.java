package com.spa.servicedealz.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.spa.servicedealz.R;
import com.spa.internet_connectivity.NetworkUtil;

import java.util.ArrayList;

import com.spa.adapter.SellerUserAdapter;
import com.spa.utils.Jsondata;

/**
 * Created by Diwakar on 4/24/2015.
 */

/**
 * FileName : SellerUserDetailActivity
 * Description :Show seller user of deals
 * Dependencies : Internet
 */
public class SellerUserDetailActivity extends Activity implements View.OnClickListener, NetworkUtil.changeNetworkInterFace {

    ArrayList<String> vender_list = new ArrayList<String>();
   private ImageView mImageViewClose;
   private ListView mListViewSellerUser;
   private ArrayList<String> mCategoryListName = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_user_detail);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        //==========================================================================================
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mListViewSellerUser = (ListView) findViewById(R.id.lv_seller_user);


        //==========================================================================================
        mCategoryListName.clear();
        mCategoryListName.add("Ram Garg");
        mCategoryListName.add("Jay Prakash");
        mCategoryListName.add("Diwakar Gupta");
        mCategoryListName.add("Pratibha");
        mCategoryListName.add("Narendra");
        mCategoryListName.add("Sandeep");
        mCategoryListName.add("Sunil");
        mCategoryListName.add("Himanshu");
        mCategoryListName.add("Lalit");
        mCategoryListName.add("Bhawna");
        mCategoryListName.add("Subodh");
        mCategoryListName.add("Kanika");
        mCategoryListName.add("Rahul");
        mCategoryListName.add("Prakash");
        mCategoryListName.add("Deepmala");
        mCategoryListName.add("Tushar");

        mListViewSellerUser.setAdapter(new SellerUserAdapter(this, mCategoryListName));
        mImageViewClose = (ImageView) findViewById(R.id.img_close);

        mImageViewClose.setOnClickListener(this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {


            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.img_close:
                finish();

                break;

        }
    }

    @Override
    public void updateNetwork(String s) {

    }


    class HttpGetAsyncTask_vender extends AsyncTask<String, Void, String> {
        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                SharedPreferences sp = getSharedPreferences("Pref_name",
                        Activity.MODE_WORLD_READABLE);
                vender_list = Jsondata.getservices_provider(sp.getString("app_user_id", ""), "", SellerUserDetailActivity.this);

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


            String[] array = vender_list.toArray(new String[vender_list.size()]);

            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SellerUserDetailActivity.this, android.R.layout.simple_spinner_dropdown_item, array); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            //mSpinnerVendor.setAdapter(spinnerArrayAdapter);

        }

    }
}
