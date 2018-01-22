package com.spa.internet_connectivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * This class contains Broadcast Receiver to Check the InterNetConnectivity

 * @author  Deepmala Verma
 * @since 0.9
 
 */


public class NetworkChangeReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(final Context context, final Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);
		Toast.makeText(context, status, Toast.LENGTH_LONG).show();
	}

}
