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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.localytics.android.Localytics;
import com.spa.adapter.GuestDashboardAdapter;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.guestdashboard.GuestDashboard;
import com.spa.model.servicemodel.ChartDataSet;
import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.DealListActivity;
import com.spa.servicedealz.ui.WelcomeScreenActivity;
import com.spa.servicedealz.ui.ZipCodeActivity;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.spa.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Diwakar on 4/28/2016.
 */
public class GuestDashbaordFragment extends Fragment implements ScreenShotable {
    public static ListView LISTVIEW_DASHBOARD;
    private Boolean isInternetPresent = false;
    private String mAdvertisementImageUrl = "", mAdvertisementUrl = "";
    private GuestDashboard mDashboardJsonData;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ProgressDialog pDialog;
    public static ArrayList<ChartDataSet> chartDataSetArrayList;
    private RelativeLayout mRelativeLayoutZipCode;
    private TextView mTextViewZipCode, mTextViewEdit, mTextViewUserType;

    public static GuestDashbaordFragment newInstance() {
        GuestDashbaordFragment dashboardFragment = new GuestDashbaordFragment();
        return dashboardFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main_drawer, container, false);
        mSharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        mEditor = mSharedPreferences.edit();
        mRelativeLayoutZipCode = (RelativeLayout) rootView.findViewById(R.id.rl_zipcode);
        mRelativeLayoutZipCode.setVisibility(View.VISIBLE);
        if (mSharedPreferences.getString("guestfitstime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
            Intent welcome = new Intent(getActivity(), WelcomeScreenActivity.class);
            welcome.putExtra("guestwelcome", true);
            mEditor.putString("guestfitstime", "");
            mEditor.commit();
            getActivity().startActivity(welcome);
        }

        mTextViewUserType = (TextView) rootView.findViewById(R.id.txt_usertype);
        mTextViewEdit = (TextView) rootView.findViewById(R.id.txt_edit);
        mTextViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent guestlogin = new Intent(getActivity(), ZipCodeActivity.class);
                startActivity(guestlogin);
            }
        });
        mTextViewZipCode = (TextView) rootView.findViewById(R.id.txt_zipcode);
        if (mSharedPreferences.getString("deal_type", "").equalsIgnoreCase("residence")) {
            mTextViewUserType.setText("Residence");

        } else {
            mTextViewUserType.setText("Business");

        }
        mTextViewZipCode.setText(mSharedPreferences.getString("guestzipcode", "75024"));
        LISTVIEW_DASHBOARD = (ListView) rootView.findViewById(R.id.lv_dashboard);
        LISTVIEW_DASHBOARD.setPadding(0, 35, 0, 0);
        LISTVIEW_DASHBOARD.setEmptyView(rootView.findViewById(android.R.id.empty));
        LISTVIEW_DASHBOARD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                      @Override
                                                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                          String service_vender = "";
                                                          try {
                                                              mEditor.putString("current_provider", "Current Plan");
                                                              if (mDashboardJsonData.getDashboardData().size() > position) {
                                                                  String deal_id = mDashboardJsonData.getDashboardData().get(position).getTrendingDeal().getId().toString();
                                                                  service_vender = "" + mDashboardJsonData.getDashboardData().get(position).getTrendingDeal().getServiceCategoryId();
                                                                  mEditor.putString("actionbar_title",
                                                                          mDashboardJsonData.getDashboardData().get(position).getServiceCategoryName().toString() + " Deals");
                                                                  mEditor.putString("trending_deal_id", deal_id);
                                                                  mEditor.putString("deal_id", "");
                                                                  mEditor.putString("service_provider",
                                                                          service_vender);
                                                                  Intent btn_update = new Intent(getActivity(), DealListActivity.class);
                                                                  startActivity(btn_update);
                                                                  mEditor.commit();
                                                              } else {
                                                                  ShowDailog.Show_Alert_Login(getActivity(), "No Deal Available");
                                                              }
                                                          } catch (Exception e) {
                                                              e.printStackTrace();
                                                              ShowDailog.Show_Alert_Login(getActivity(), "No Deal Available");
                                                          }

                                                      }
                                                  }

        );
        isInternetPresent = NetworkUtil.isConnectingToInternet(getActivity());
        if (isInternetPresent) {
            new HttpGetAsyncTask_Get_Data().execute();
        } else {
            ShowDailog.Show_Alert_Dailog(getActivity());
        }
        return rootView;
    }

    @Override
    public void takeScreenShot() {
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    class HttpGetAsyncTask_Get_Data extends AsyncTask<String, Void, String> {
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
                mDashboardJsonData = Jsondata.get_deal_guest_dashboard(mSharedPreferences.getString("guestzipcode", "75024"), getActivity());
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
            if (Jsondata.Success.equalsIgnoreCase(Constant.TRUE)) {

                if (mDashboardJsonData == null) {
                    // LISTVIEW_DASHBOARD.setAdapter(new EmptyDashboardAdapter(getActivity()));
                    ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
                } else {
                    if (mDashboardJsonData.getDashboardData().size() > 0) {
                        localyticstagEvent("GuestDashboard");
                        LISTVIEW_DASHBOARD.setAdapter(new GuestDashboardAdapter(getActivity(), mDashboardJsonData));
                    } else {
                        //  LISTVIEW_DASHBOARD.setAdapter(new EmptyDashboardAdapter(getActivity()));
                        ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
                    }
                }


            } else {
                ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
            }
        }
    }

    public void localyticstagEvent(String method) {
        Map<String, String> home_values = new HashMap<String, String>();

        home_values.put("Success", "Yes");
        home_values.put("Method", method);

        Localytics.tagEvent("GuestHome", home_values);

    }

}

