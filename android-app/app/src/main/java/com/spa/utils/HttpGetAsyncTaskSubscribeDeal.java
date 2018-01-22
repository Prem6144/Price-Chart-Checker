package com.spa.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;

/**
 * Created by Narendra on 12/24/2015.
 */
public class HttpGetAsyncTaskSubscribeDeal extends AsyncTask<String, Void, String> {
    String response = "";
    Activity mActivity;
    SharedPreferences mSharedPreferences;
    String deal_postion = "";

    public HttpGetAsyncTaskSubscribeDeal(Activity mActivity) {
        this.mActivity = mActivity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        try {
           // deal_postion = params[3];
            mSharedPreferences = mActivity.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_PRIVATE);
            //Jsondata.get_subscribed_deals(mSharedPreferences.getString(Constant.APP_USER_ID, ""), params[0], params[1], params[2]);
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
       /* Intent intent = new Intent(mActivity.getApplicationContext(), PlaceOrderAndPay.class);
        intent.putExtra("deal_position", deal_postion);
        mActivity.startActivity(intent);*/
      //  if (Jsondata.giftCard.getSuccess()) {
           // int count = mSharedPreferences.getInt("Activate_Count", 0);
          /*  if (count == 0) {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:0123456789"));
                mActivity.startActivity(intent);
            } else {

                Intent intent = new Intent(mActivity.getApplicationContext(), GiftCardActivty.class);
                mActivity.startActivity(intent);
            }*/
            // Intent intent = new Intent(mActivity.getApplicationContext(), GiftCardActivty.class);
            // mActivity.startActivity(intent);

    }
}
