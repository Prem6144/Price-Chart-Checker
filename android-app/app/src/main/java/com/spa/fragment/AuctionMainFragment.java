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
import android.widget.GridView;

import com.spa.internet_connectivity.NetworkUtil;
import com.spa.servicedealz.drawer.AuctionCategoryActivity;
import com.spa.servicedealz.R;

import java.util.ArrayList;

import com.spa.adapter.AuctionAdapter;
import co.spa.sidemenu.interfaces.ScreenShotable;
/**
 * FileName : AuctionMainFragment
 * Description :
 * Dependencies : Internet
 */
public class AuctionMainFragment extends Fragment implements ScreenShotable, View.OnClickListener {
    Boolean isInternetPresent = false;
    ArrayList<String> category_list = new ArrayList<String>();
    ArrayList<String> category_list_name = new ArrayList<String>();
  private   GridView mGridView;

    public static AuctionMainFragment newInstance() {
        AuctionMainFragment contentFragment = new AuctionMainFragment();
        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //==========================================================================================
        View serivces = inflater.inflate(R.layout.auction_main, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mGridView = (GridView) serivces.findViewById(R.id.gridview);
        category_list.clear();
        category_list_name.clear();
        category_list_name.add("" + getResources().getText(R.string.internet_services));
        category_list_name.add("" + getResources().getText(R.string.cell_phone_services));
        category_list_name.add("" + getResources().getText(R.string.gas_services));
        category_list_name.add("" + getResources().getText(R.string.electricity_services));
        category_list_name.add("" + getResources().getText(R.string.home_security));
        category_list_name.add("" + getResources().getText(R.string.cable_services));
        category_list_name.add("" + getResources().getText(R.string.telephone_services));
        category_list_name.add("" + getResources().getText(R.string.bundle_services));
       /* category_list.add("" + getResources().getText(R.string.internet_id));
        category_list.add("" + getResources().getText(R.string.cellphone_id));
        category_list.add("" + getResources().getText(R.string.gas_id));
        category_list.add("" + getResources().getText(R.string.electricity_id));
        category_list.add("" + getResources().getText(R.string.homesecurity_id));
        category_list.add("" + getResources().getText(R.string.cable_id));
        category_list.add("" + getResources().getText(R.string.telephone_id));
        category_list.add("" + getResources().getText(R.string.bundle_id));*/
        mGridView.setAdapter(new AuctionAdapter(getActivity(), category_list));
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                String category = category_list_name.get(position).toString();
                                                Intent Internet_Services = new Intent(getActivity(), AuctionCategoryActivity.class);
                                                Internet_Services.putExtra("category", category);
                                                startActivity(Internet_Services);
                                                getActivity().finish();
                                            }

                                        }

        );
        //==========================================================================================

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
            mGridView.setAdapter(new Services_adapter(getActivity(), completed_list, size));*/
        }


    }
}
