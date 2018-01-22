package com.spa.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.SellerUserDetailActivity;
import com.spa.adapter.SellerAdapter;
import com.spa.internet_connectivity.NetworkUtil;

import java.util.ArrayList;

import co.spa.sidemenu.interfaces.ScreenShotable;
/**
 * FileName : SellerFragment
 * Description :
 * Dependencies : Internet
 */
public class SellerFragment extends Fragment implements ScreenShotable, View.OnClickListener {
    Boolean isInternetPresent = false;
    private ArrayList<String> mCategory_list_name = new ArrayList<String>();
   private ListView mListViewSeller;
    public static SellerFragment newInstance() {
        SellerFragment contentFragment = new SellerFragment();
        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //==========================================================================================
        View serivces = inflater.inflate(R.layout.seller_main, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mListViewSeller = (ListView) serivces.findViewById(R.id.lv_seller);


        //==========================================================================================
        mCategory_list_name.clear();
        mCategory_list_name.add("Ram Garg");
        mCategory_list_name.add("Jay Prakash");
        mCategory_list_name.add("Diwakar Gupta");
        mCategory_list_name.add("Pratibha");
        mCategory_list_name.add("Narendra");
        mCategory_list_name.add("Sandeep");
        mCategory_list_name.add("Sunil");
        mCategory_list_name.add("Himanshu");
        mCategory_list_name.add("Lalit");
        mCategory_list_name.add("Bhawna");
        mCategory_list_name.add("Subodh");
        mCategory_list_name.add("Kanika");
        mCategory_list_name.add("Rahul");
        mCategory_list_name.add("Prakash");
        mCategory_list_name.add("Deepmala");
        mCategory_list_name.add("Tushar");
        mListViewSeller.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent seller = new Intent(getActivity().getApplicationContext(), SellerUserDetailActivity.class);
                startActivity(seller);
            }
        });
        mListViewSeller.setAdapter(new SellerAdapter(getActivity(), mCategory_list_name));


        //==========================================================================================

        return serivces;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);

    }

    public void onResume() {
        super.onResume();
        isInternetPresent = NetworkUtil.isConnectingToInternet(getActivity());
        if (isInternetPresent) {

            //  new HttpGetAsyncTask().execute();
        } else {
            ShowDailog.Show_Alert_(getActivity(), "Please connect to the internet", "Warning");
        }


        //==========================================================================================
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
                // completed_list.clear();
                //completed_list = Jsondata.getservices_completed(sp.getString("app_user_id", ""), getActivity());

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

          /*  int size = completed_list.size();

            for (int i = 1; i <= 8; i++) {
                if (completed_list.contains("" + i)) {


                } else {
                    completed_list.add("" + i);
                }
            }
            gridview.setAdapter(new Services_adapter(getActivity(), completed_list, size));*/
        }


    }
}
