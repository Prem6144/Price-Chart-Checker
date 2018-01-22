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

import com.localytics.android.Localytics;
import com.spa.adapter.MyOrderAdater;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.myorder.MyOrder;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.OrderDealDetailActivity;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;

import java.util.HashMap;
import java.util.Map;

import co.spa.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Diwakar on 5/19/2016.
 */
public class MyOrderFragment extends Fragment implements ScreenShotable {
    public static ListView LISTVIEW_MYORDER;
    private Boolean isInternetPresent = false;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private ProgressDialog pDialog;
    public static MyOrder myOrder;

    public static MyOrderFragment newInstance() {
        MyOrderFragment myOrderFragment = new MyOrderFragment();
        return myOrderFragment;
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
        LISTVIEW_MYORDER = (ListView) rootView.findViewById(R.id.lv_dashboard);
        LISTVIEW_MYORDER.setPadding(0,35,0,0);
        LISTVIEW_MYORDER.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int pos = myOrder.getOrder().get(i).getId();
                Intent intent = new Intent(getActivity(), OrderDealDetailActivity.class);
                intent.putExtra("orderid", pos);

                intent.putExtra("userid", "" + myOrder.getOrder().get(i).getAppUserId());
                getActivity().startActivity(intent);
            }
        });
        LISTVIEW_MYORDER.setEmptyView(rootView.findViewById(android.R.id.empty));
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
    public void onResume() {
        super.onResume();

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
                myOrder = Jsondata.get_my_order("app_user_id=" + mSharedPreferences.getString(Constant.APP_USER_ID, ""), getActivity());
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
            try {


                if (myOrder.getSuccess()) {

                    if (myOrder.getOrder() == null) {
                        ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
                    } else {
                        if (myOrder.getOrder().size() > 0) {
                            localyticstagEvent("MyOrder");
                            LISTVIEW_MYORDER.setAdapter(new MyOrderAdater(getActivity(), myOrder));
                        } else {
                            ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
                        }
                    }

                } else {
                    ShowDailog.Show_Alert_Login(getActivity(), "No Order");
                }
            } catch (Exception e) {
                ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
                e.printStackTrace();
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

