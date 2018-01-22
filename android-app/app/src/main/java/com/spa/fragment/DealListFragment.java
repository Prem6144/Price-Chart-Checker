package com.spa.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.localytics.android.Localytics;
import com.spa.adapter.BundleListAdapter;
import com.spa.adapter.DealListAdapter;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.custemizedeal.com.spa.model.celphonedeal.Custemizedeal;
import com.spa.model.deallist.DealListItem;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.BundleDealDetailActivity;
import com.spa.servicedealz.ui.CableDealDeatilActivity;
import com.spa.servicedealz.ui.CellphoneCustemDealActivity;
import com.spa.servicedealz.ui.DealDetailActivity;
import com.spa.utils.Constant;
import com.spa.utils.ItemClickSupport;
import com.spa.utils.Jsondata;
import com.spa.utils.PaddingItemDecoration;

import java.util.HashMap;
import java.util.Map;

import co.spa.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Konstantin on 22.12.2014.
 */

/**
 * FileName : DealListFragment
 * Description :
 * Dependencies : Internet
 */
public class DealListFragment extends Fragment implements ScreenShotable {

    public static DealListItem dealListItem;
    public static ListView mLstViewDeal;
    private Boolean isInternetPresent = false, Headeradded = false;
    public static boolean Best_Deal_Flag = true;
    public static boolean Customisable_Flag = false;
    private SharedPreferences mSharedPreferences;
    private ProgressDialog pDialog;
    RecyclerView recyclerView;
    private LinearLayout mLinearLayoutBundleList;
    PaddingItemDecoration addItemDecoration;
    Custemizedeal custemizeDEalList = null;
    View rootView;
    public static Boolean EmailVerified = false;

    public static DealListFragment newInstance() {
        DealListFragment contentFragment = new DealListFragment();
        return contentFragment;
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
        isInternetPresent = NetworkUtil.isConnectingToInternet(getActivity());
        Best_Deal_Flag = true;
        mSharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        mLstViewDeal = (ListView) rootView.findViewById(R.id.lv_dashboard);
        SharedPreferences sp = getActivity().getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("ChalneEquipmentPrice", "");
        editor.putString("ChalnePremiumPrice", "");
        editor.putString("TvAdapterPrice", "");
        editor.putString("ExtraServicePrice ", "0.0");
        editor.putString("planPrice1", null);

        editor.commit();

        mLstViewDeal.setEmptyView(rootView.findViewById(android.R.id.empty));
        mLstViewDeal.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //boolean b = dealListItem.getDeal().get(position).getCustomisable();
//                if (dealListItem.getDeal().get(position).getCustomisable()) {

                try {

                    if (dealListItem.getDeal() != null && dealListItem.getDeal().size() > 0) {
                        if (dealListItem.getBundle_deals() != null && dealListItem.getBundle_deals().size() > 0) {
                            position = position - 1;

                        }
                        if (dealListItem.getDeal().get(position).getCustomisable()) {
//                            Toast.makeText(getActivity(), "" + dealListItem.getDeal().get(position).getCustomisable(), Toast.LENGTH_SHORT).show();
                            String DealId = "" + dealListItem.getDeal().get(position).getId();
                            String ServiceCategoryId = "" + dealListItem.getDeal().get(position).getService_category_id();
                            String ServiceCategoryName = dealListItem.getDeal().get(position).getService_category_name();
                            if (ServiceCategoryName.equalsIgnoreCase(getResources().getString(R.string.cell_phone))) {
                                Intent intent = new Intent(getActivity(), CellphoneCustemDealActivity.class);
                                SharedPreferences sp = getActivity().getSharedPreferences("Pref_name",
                                        Activity.MODE_WORLD_READABLE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("DealId", DealId);
                                editor.putString("ServiceCategoryId", ServiceCategoryId);
                                editor.commit();
                                startActivity(intent);
                            } else if (ServiceCategoryName.equalsIgnoreCase(getResources().getString(R.string.cable))) {
                                Intent intent = new Intent(getActivity(), CableDealDeatilActivity.class);
                                SharedPreferences sp = getActivity().getSharedPreferences("Pref_name",
                                        Activity.MODE_WORLD_READABLE);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("DealId", DealId);
                                editor.putString("ServiceCategoryId", ServiceCategoryId);
                                editor.commit();
                                startActivity(intent);

                            }
                        } else {
                            Intent in = new Intent(getActivity(), DealDetailActivity.class);
                            in.putExtra("deal_position", position);
                            startActivity(in);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return rootView;
    }

    public void onResume() {
        super.onResume();
        if (isInternetPresent) {
            new HttpGetAsyncTaskInternet().execute();
        } else {
            ShowDailog.Show_Alert_Dailog(getActivity());
        }
    }


    @Override
    public void takeScreenShot() {


    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }


    public class HttpGetAsyncTaskInternet extends AsyncTask<String, Void, String> {
        String response = "";

        public HttpGetAsyncTaskInternet() {
        }

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
                EmailVerified = Jsondata.EamilVerication(mSharedPreferences.getString(Constant.APP_USER_ID, ""), getActivity());
                dealListItem = Jsondata.get_deal(mSharedPreferences.getString(Constant.APP_USER_ID, ""), mSharedPreferences.getString("service_provider", "1"), getActivity());
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
            if (Jsondata.Success.equals("true")) {
                setadapter(getActivity());
                localyticstagEvent("ShowDealList");
            } else {
                ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
            }
        }
    }

    public void localyticstagEvent(String method) {
        Map<String, String> home_values = new HashMap<String, String>();

        home_values.put("Success", "Yes");
        home_values.put("Method", method);

        Localytics.tagEvent("DealList", home_values);

    }

    public void setadapter(Activity act) {
        try {
            mLstViewDeal.setAdapter(new DealListAdapter(act, dealListItem));
            LayoutInflater inflater1 = getActivity().getLayoutInflater();
            ViewGroup header = (ViewGroup) inflater1.inflate(R.layout.bundlelistheader, mLstViewDeal, false);
            if (dealListItem.getBundle_deals() != null && dealListItem.getBundle_deals().size() > 0) {
                if (!Headeradded) {
                    Headeradded = true;
                    mLstViewDeal.addHeaderView(header, null, false);
                    recyclerView = (RecyclerView) rootView.findViewById(R.id.lv_bundle);
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
                recyclerView.setAdapter(new BundleListAdapter(act, dealListItem));

                ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                        String DealId = dealListItem.getBundle_deals().get(position).getId();
                        String ServiceCategoryId = dealListItem.getBundle_deals().get(position).getService_category_id();
                        Intent intent = new Intent(getActivity(), BundleDealDetailActivity.class);
                        intent.putExtra("value", position);
//                        intent.putExtra("ServiceCategoryId", ServiceCategoryId);
                        SharedPreferences sp = getActivity().getSharedPreferences("Pref_name",
                                Activity.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("DealId", DealId);
                        editor.putString("ServiceCategoryId", ServiceCategoryId);
                        editor.commit();
                        startActivity(intent);
                    }
                });
            } else {
                mLstViewDeal.removeHeaderView(header);
                mLstViewDeal.setPadding(0, 35, 0, 0);
                Headeradded = false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String Show_Alert_Login(final Activity activity, String message) {
        AlertDialog.Builder alertbox = new AlertDialog.Builder(
                activity);

        alertbox.setMessage(message);
        alertbox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences sp = activity.getSharedPreferences("Pref_name",
                                Activity.MODE_WORLD_READABLE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("sortbyflag",
                                "price");
                        Best_Deal_Flag = false;
                        editor.commit();
                        if (isInternetPresent) {
                            new HttpGetAsyncTaskInternet().execute();
                        } else {
                            ShowDailog.Show_Alert_Dailog(getActivity());
                        }
                    }
                });
        alertbox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        alertbox.setCancelable(false);
        alertbox.show();
        return null;
    }


}


