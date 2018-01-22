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
import android.support.v4.content.IntentCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.localytics.android.Localytics;
import com.spa.adapter.CustemizedealAdapter;
import com.spa.adapter.DashboardAdapter;
import com.spa.adapter.EmptyDashboardAdapter;
import com.spa.adapter.GuestDashboardAdapter;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.custemizedeal.com.spa.model.celphonedeal.Custemizedeal;
import com.spa.model.dashbordmodel.Dashboard;
import com.spa.model.servicemodel.ChartDataSet;
import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.DealListActivity;
import com.spa.servicedealz.drawer.ProfileActivity;
import com.spa.servicedealz.services.BundleServiceActivity;
import com.spa.servicedealz.services.CableServiceActivity;
import com.spa.servicedealz.services.CellPhoneServiceActivity;
import com.spa.servicedealz.services.InternetServiceActivity;
import com.spa.servicedealz.services.TelephoneServices;
import com.spa.servicedealz.ui.CellphoneCustemDealActivity;
import com.spa.servicedealz.ui.WelcomeScreenActivity;
import com.spa.utils.Constant;
import com.spa.utils.ItemClickSupport;
import com.spa.utils.Jsondata;
import com.spa.utils.PaddingItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import co.spa.sidemenu.interfaces.ScreenShotable;

/**
 * FileName : DashboardFragment
 * Description :
 * Dependencies : Internet
 */
public class DashboardFragment extends Fragment implements ScreenShotable {
    public static ListView LISTVIEW_DASHBOARD;
    private Boolean isInternetPresent = false, Headeradded = false;
    ;
    public static boolean ServiceSave = true;
    private String mAdvertisementImageUrl = "", mAdvertisementUrl = "";
    private Dashboard mDashboardJsonData;
    Custemizedeal custemizeDEalList = null;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ProgressDialog pDialog;
    RelativeLayout mRelativeLayoutZipCode;
    public static ArrayList<ChartDataSet> chartDataSetArrayList;
    private TextView mTextViewZipCode, mTextViewEdit, mTextViewUserType;
    RecyclerView recyclerView;
    private LinearLayout mLinearLayoutBundleList;
    PaddingItemDecoration addItemDecoration;
    View rootView;

    public static DashboardFragment newInstance() {
        DashboardFragment dashboardFragment = new DashboardFragment();
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
        rootView = inflater.inflate(R.layout.fragment_main_drawer, container, false);
        ServiceSave = true;

        if (GuestDashboardAdapter.category_click.equalsIgnoreCase(getResources().getString(R.string.internet))) {
            GuestDashboardAdapter.category_click = "";
            Intent Internet_Services = new Intent(getActivity(), InternetServiceActivity.class);
            Internet_Services.putExtra("bestdealflag", DashboardAdapter.best_deal_flag);
            getActivity().startActivity(Internet_Services);
        } else if (GuestDashboardAdapter.category_click.equalsIgnoreCase(getActivity().getResources().getString(R.string.telephone))) {
            GuestDashboardAdapter.category_click = "";
            Intent Telephone_services = new Intent(getActivity(), TelephoneServices.class);
            Telephone_services.putExtra("bestdealflag", DashboardAdapter.best_deal_flag);
            getActivity().startActivity(Telephone_services);
        } else if (GuestDashboardAdapter.category_click.equalsIgnoreCase(getActivity().getResources().getString(R.string.gas))) {
        } else if (GuestDashboardAdapter.category_click.equalsIgnoreCase(getActivity().getResources().getString(R.string.electricity))) {
        } else if (GuestDashboardAdapter.category_click.equalsIgnoreCase(getActivity().getResources().getString(R.string.home_securuty))) {
        } else if (GuestDashboardAdapter.category_click.equalsIgnoreCase(getActivity().getResources().getString(R.string.cable))) {
            GuestDashboardAdapter.category_click = "";
            Intent Cable_services = new Intent(getActivity(), CableServiceActivity.class);
            Cable_services.putExtra("bestdealflag", DashboardAdapter.best_deal_flag);
            getActivity().startActivity(Cable_services);
        } else if (GuestDashboardAdapter.category_click.equalsIgnoreCase(getActivity().getResources().getString(R.string.cell_phone))) {
            GuestDashboardAdapter.category_click = "";
            Intent Cell_Phone_Services = new Intent(getActivity(), CellPhoneServiceActivity.class);
            Cell_Phone_Services.putExtra("bestdealflag", DashboardAdapter.best_deal_flag);
            getActivity().startActivity(Cell_Phone_Services);
        } else if (GuestDashboardAdapter.category_click.equalsIgnoreCase(getActivity().getResources().getString(R.string.bundle))) {
            GuestDashboardAdapter.category_click = "";
            Intent Bundle_services = new Intent(getActivity(), BundleServiceActivity.class);
            Bundle_services.putExtra("bestdealflag", DashboardAdapter.best_deal_flag);
            getActivity().startActivity(Bundle_services);
        }
        mSharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        mEditor = mSharedPreferences.edit();
        if (mSharedPreferences.getString("signupwithfirsttime", "").equalsIgnoreCase(Constant.YES_FLAG)) {
            Intent welcome = new Intent(getActivity(), WelcomeScreenActivity.class);
            getActivity().startActivity(welcome);
        }
        mTextViewUserType = (TextView) rootView.findViewById(R.id.txt_usertype);
        mRelativeLayoutZipCode = (RelativeLayout) rootView.findViewById(R.id.rl_zipcode);
        mRelativeLayoutZipCode.setVisibility(View.VISIBLE);
        mTextViewEdit = (TextView) rootView.findViewById(R.id.txt_edit);
        mTextViewEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent guestlogin = new Intent(getActivity(), ProfileActivity.class);
                mEditor.putBoolean("fullProfile", true);
                mEditor.commit();
                guestlogin.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(guestlogin);
                getActivity().finish();
            }
        });
        mTextViewZipCode = (TextView) rootView.findViewById(R.id.txt_zipcode);

        mTextViewUserType.setText(mSharedPreferences.getString("BuisnessName", ""));
        mTextViewZipCode.setText(mSharedPreferences.getString("enter_zipcode", "75024"));
        LISTVIEW_DASHBOARD = (ListView) rootView.findViewById(R.id.lv_dashboard);
        LISTVIEW_DASHBOARD.setPadding(0,35,0,0);
        LISTVIEW_DASHBOARD.setEmptyView(rootView.findViewById(android.R.id.empty));
        LISTVIEW_DASHBOARD.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                      @Override
                                                      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                                          String service_vender = "";
                                                          try {
                                                              mEditor.putString("current_provider", "Current Plan");
                                                                /*  if (mDashboardJsonData.getDashboardData().get(position).getAdvertisement().size() > 0) {
    //                                                            mAdvertisementImageUrl = String.valueOf(mDashboardJsonData.getDashboardData().get(position).getAdvertisement().get(0).getAdvertisementImageUrl() != null);
    //                                                            mAdvertisementUrl = mDashboardJsonData.getDashboardData().get(position).getAdvertisement().get(0).getUrl();
                                                                  }*/
                                                              service_vender = "" + mDashboardJsonData.getDashboard_data().get(position).getService_category_id();
                                                              if (mDashboardJsonData.getDashboard_data().get(position).getTrending_deal() != null) {
                                                                  String trending_deal_id = "" + mDashboardJsonData.getDashboard_data().get(position).getTrending_deal().getId();
                                                                  mEditor.putString("trending_deal_id", trending_deal_id);

                                                              } else {
                                                                  mEditor.putString("trending_deal_id", "");
                                                              }
                                                              if (mDashboardJsonData.getDashboard_data().get(position).getBest_deal() != null) {
                                                                  String deal_id = "" + mDashboardJsonData.getDashboard_data().get(position).getBest_deal().getId();
                                                                  mEditor.putString("deal_id", deal_id);
                                                              } else {
                                                                  mEditor.putString("deal_id", "");
                                                              }
                                                              mEditor.putString("actionbar_title",
                                                                      mDashboardJsonData.getDashboard_data().get(position).getService_category_name().toString() + " Deals");
                                                              mEditor.putString("service_provider",
                                                                      service_vender);
                                                              mEditor.commit();
                                                              Intent btn_update = new Intent(getActivity(), DealListActivity.class);
                                                              startActivity(btn_update);

                                                          } catch (Exception e) {
                                                              e.printStackTrace();
                                                              ShowDailog.Show_Alert_Login(getActivity(), "No Deal Available");
                                                          }

                                                      }
                                                  }

        );

        return rootView;
    }

    @Override
    public void takeScreenShot() {
    }

    @Override
    public void onResume() {
        super.onResume();
        isInternetPresent = NetworkUtil.isConnectingToInternet(getActivity());
        if (isInternetPresent) {
            if (ServiceSave) {
                ServiceSave = false;
                new HttpGetAsyncTask_Get_Data().execute();
            }
        } else {
            ShowDailog.Show_Alert_Dailog(getActivity());
        }
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
                mDashboardJsonData = Jsondata.get_deal_dashboard("app_user_id=" + mSharedPreferences.getString(Constant.APP_USER_ID, "") + "&zip_code=" + Jsondata.encryptMsg(mSharedPreferences.getString(Constant.ZIPCODE, "")), getActivity());
               // custemizeDEalList = Jsondata.getCustemizeDealz("app_user_id=" + mSharedPreferences.getString(Constant.APP_USER_ID, ""), getActivity());
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
                    LISTVIEW_DASHBOARD.setAdapter(new EmptyDashboardAdapter(getActivity()));
                    ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
                } else {
                    if (mDashboardJsonData.getDashboard_data().size() > 0) {
                        localyticstagEvent("Dashbord");
                        LISTVIEW_DASHBOARD.setAdapter(new DashboardAdapter(getActivity(), mDashboardJsonData));

                        LayoutInflater inflater1 = getActivity().getLayoutInflater();
                        ViewGroup header = (ViewGroup) inflater1.inflate(R.layout.bundlelistheader, LISTVIEW_DASHBOARD, false);
                       /* if (custemizeDEalList.getDeals() != null && custemizeDEalList.getDeals().size() > 0) {
                            if (!Headeradded) {
                                Headeradded = true;
                               // LISTVIEW_DASHBOARD.addHeaderView(header, null, false);
                                recyclerView = (RecyclerView) rootView.findViewById(R.id.lv_bundle);
                                recyclerView.setVisibility(View.GONE);
                                try {
                                    addItemDecoration = new PaddingItemDecoration(getActivity());
                                } catch (Exception e) {
                                }
                                mLinearLayoutBundleList = (LinearLayout) rootView.findViewById(R.id.ll_bundle);
                                LinearLayoutManager layoutManager
                                        = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.addItemDecoration(addItemDecoration);
                            }
                            mLinearLayoutBundleList.setVisibility(View.VISIBLE);
                            recyclerView.setAdapter(new CustemizedealAdapter(getActivity(), custemizeDEalList));

                            ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                @Override
                                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                    String DealId = "" + custemizeDEalList.getDeals().get(position).getId();
                                    String ServiceCategoryId = "" + custemizeDEalList.getDeals().get(position).getServiceCategoryId();
                                    String ServiceCategoryName  =custemizeDEalList.getDeals().get(position).getServiceCategoryName();
                                    if (ServiceCategoryName.equals("Cellphone")) {
                                        Intent intent = new Intent(getActivity(), CellphoneCustemDealActivity.class);
                                        SharedPreferences sp = getActivity().getSharedPreferences("Pref_name",
                                                Activity.MODE_WORLD_READABLE);
                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putString("DealId", DealId);
                                        editor.putString("ServiceCategoryId", ServiceCategoryId);
                                        editor.commit();
                                        startActivity(intent);
                                    }else {

                                    }
                                }
                            });
                        } else {
                           // LISTVIEW_DASHBOARD.removeHeaderView(header);
                            Headeradded = false;
                        }*/

                    } else {
                         LISTVIEW_DASHBOARD.setAdapter(new EmptyDashboardAdapter(getActivity()));
                        ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
                    }
                }
                chartDataSetArrayList = new ArrayList<>();
                if (mDashboardJsonData.getDashboard_data().size() > 0) {
                    ChartDataSet chartDataSet;
                    for (int i = 0; mDashboardJsonData.getDashboard_data().size() > i; i++) {
                        chartDataSet = new ChartDataSet();
                        if (mDashboardJsonData.getDashboard_data().get(i).getBest_deal() != null) {
                            chartDataSet.setCategory_name(mDashboardJsonData.getDashboard_data().get(i).getService_category_name());
                            chartDataSet.setYousave(mDashboardJsonData.getDashboard_data().get(i).getYou_save_text());
                            chartDataSet.setCurrent_price(Float.parseFloat("" + mDashboardJsonData.getDashboard_data().get(i).getContract_fee()));

                            chartDataSet.setDealprice(mDashboardJsonData.getDashboard_data().get(i).getBest_deal().getDeal_price());
                            chartDataSetArrayList.add(chartDataSet);
                        }
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

        Localytics.tagEvent("home", home_values);

    }


}