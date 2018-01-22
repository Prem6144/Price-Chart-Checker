package com.spa.service;

/**
 * Created by Diwakar on 7/28/2016.
 */

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.spa.utils.Jsondata;


/**
 * Created by Sunil on 7/2/2016.
 */
public class RemovedTaskFromService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        // Log.e("Yl starst service removed task on creatre");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        new HttpGetAsyncTask().execute();


        super.onTaskRemoved(rootIntent);
        this.stopSelf();
    }

    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.DestroyToken(RemovedTaskFromService.this);

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
            android.util.Log.e("YL", "On task removed");
            android.util.Log.e("DeviceTokenDisable:true", "");
        }
    }
}