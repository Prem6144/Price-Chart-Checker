package com.spa.fragment;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.localytics.android.Localytics;
import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.ProfileActivity;
import com.spa.servicedealz.services.BundleServiceActivity;
import com.spa.servicedealz.services.CableServiceActivity;
import com.spa.servicedealz.services.CellPhoneServiceActivity;
import com.spa.servicedealz.services.HomeSecurityServiceActivity;
import com.spa.servicedealz.services.InternetServiceActivity;
import com.spa.servicedealz.services.TelephoneServices;
import com.spa.adapter.ServicesAdapter;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * FileName : ServiceFragment
 * Description :
 * Dependencies : Internet
 */
public class ServiceFragment extends Fragment implements View.OnClickListener {
    private Boolean isInternetPresent = false;
    private ArrayList<String> mServiceList = new ArrayList<>();
    private ArrayList<String> mServiceCompletedList = new ArrayList<>();
    private GridView mGridViewServiceList;
    private ProgressDialog pDialog;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //==========================================================================================
        mSharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        mEditor = mSharedPreferences.edit();
        View serivces = inflater.inflate(R.layout.services_fragment, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mGridViewServiceList = (GridView) serivces.findViewById(R.id.gridview);
        mGridViewServiceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                        @Override
                                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                            String zipcode_flag = mSharedPreferences.getString("zipcode_flag", "");
                                                            if (zipcode_flag.equalsIgnoreCase("") || zipcode_flag.equals(null)) {
                                                                ShowDailog.Show_Alert_(getActivity(), "Please complete the profile", "Warning");
                                                            } else {
                                                                if (mServiceList.get(position).equals(getResources().getText(R.string.internet_id))) {
                                                                    Intent Internet_Services = new Intent(getActivity(), InternetServiceActivity.class);
                                                                    startActivity(Internet_Services);
                                                                } else if (mServiceList.get(position).equals(getResources().getText(R.string.telephone_id))) {
                                                                    Intent Telephone_services = new Intent(getActivity(), TelephoneServices.class);
                                                                    startActivity(Telephone_services);
                                                                }/* else if (mServiceList.get(position).equals(getResources().getText(R.string.gas_id))) {
                                                    Intent Gas_Services = new Intent(getActivity(), Gas_Services.class);
                                                    startActivity(Gas_Services);
                                                } else if (mServiceList.get(position).equals(getResources().getText(R.string.electricity_id))) {
                                                    Intent Electricity_Services = new Intent(getActivity(), Electricity_Services.class);
                                                    startActivity(Electricity_Services);
                                                } */ else if (mServiceList.get(position).equals(getResources().getText(R.string.homesecurity_id))) {
                                                                    Intent Home_Security = new Intent(getActivity(), HomeSecurityServiceActivity.class);
                                                                    startActivity(Home_Security);
                                                                } else if (mServiceList.get(position).equals(getResources().getText(R.string.cable_id))) {
                                                                    Intent Cable_services = new Intent(getActivity(), CableServiceActivity.class);
                                                                    startActivity(Cable_services);
                                                                } else if (mServiceList.get(position).equals(getResources().getText(R.string.cellphone_id))) {
                                                                    Intent Cell_Phone_Services = new Intent(getActivity(), CellPhoneServiceActivity.class);
                                                                    startActivity(Cell_Phone_Services);
                                                                } else if (mServiceList.get(position).equals(getResources().getText(R.string.bundle_id))) {
                                                                    Intent Bundle_services = new Intent(getActivity(), BundleServiceActivity.class);
                                                                    startActivity(Bundle_services);
                                                                }

                                                            }
                                                        }
                                                    }

        );
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
        Localytics.tagScreen("ServiceFragment");
        isInternetPresent = NetworkUtil.isConnectingToInternet(getActivity());
        if (isInternetPresent) {
            new HttpGetAsyncTask().execute();
        } else {
            ShowDailog.Show_Alert_Dailog(getActivity());
        }
    }

    @Override
    public void onClick(View v) {

    }


    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                mServiceList.clear();
                mServiceList = Jsondata.getservices_completed(mSharedPreferences.getString(Constant.APP_USER_ID, ""), getActivity());
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
            mServiceCompletedList.clear();
            mEditor.putString(Constant.SERVICE_FLAG, "");
            if (MainFragment.Tab.getCurrentItem() == 1) {
                if (null != mServiceList && mServiceList.size() > 0) {
                    String zipcode_flag = mSharedPreferences.getString(Constant.ZIPCODE_FLAG, "");
                    if (zipcode_flag.equalsIgnoreCase(Constant.YES_FLAG)) {
                        mEditor.putString(Constant.USER_DASHBOARD_FLAG, Constant.YES_FLAG);
                    }
                    ((ProfileActivity) getActivity()).rlNext.setVisibility(View.VISIBLE);
                } else {
                    mEditor.putString(Constant.SERVICE_FLAG, "");
                    mEditor.putString(Constant.USER_DASHBOARD_FLAG, "yes");
                    ((ProfileActivity) getActivity()).rlNext.setVisibility(View.GONE);
                }
            }
            for (int i = 1; i <= 8; i++) {

                if (mServiceList.contains("" + i)) {
                    ((ProfileActivity) getActivity()).rlNext.setVisibility(View.GONE);
                    mEditor.putString(Constant.SERVICE_FLAG, Constant.YES_FLAG);
                    mServiceCompletedList.add("" + i);
                } else {
                    mServiceList.add("" + i);
                }
            }
            localyticstagEvent("Serviceselected");
            mEditor.commit();
            mGridViewServiceList.setAdapter(new ServicesAdapter(getActivity(), mServiceList, mServiceCompletedList));
        }


    }
    public void localyticstagEvent(String method){
        Map<String,String> home_values = new HashMap<String, String>();

        home_values.put("Success", "Yes");
        home_values.put("Method", method);

        Localytics.tagEvent("ServiceFill", home_values);

    }
}
