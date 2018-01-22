package com.spa.notification;

import android.app.Activity;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.IntentCompat;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.spa.servicedealz.drawer.DashboardActivity;
import com.spa.servicedealz.drawer.ProfileActivity;
import com.spa.servicedealz.ui.LoginActivity;


public class GCMNotificationIntentService extends IntentService {
    // Sets an ID for the notification, so it can be updated
    int notify_no = 0;
    public static Activity activity;

    public GCMNotificationIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);

        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {
            String message = extras.getString("message");
            String deepLink = extras.getString("deeplink");

            if(deepLink!=null) {

//                intent = new Intent(Intent.ACTION_VIEW, Uri.parse("ServiceDealz://deeplink/" + deepLink))
//                        .putExtras(extras);
                sendNotification(message,deepLink);
                return;
            }

            if(message!=null){
                sendNotification(message,null);
                return;
            }
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
                sendNotification("Send error: " + extras.toString(),null);
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
                sendNotification("Deleted messages on server: " + extras.toString(),null);
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {
                try {
                    if (!extras.get(MyPush.MSG_KEY).equals(null)) {

                        sendNotification("" + extras.get(MyPush.MSG_KEY),null);
                    } else if (!extras.get(MyPush.REFF_KEY).equals(null)) {
                        sendNotification("Referral" + extras.get(MyPush.REFF_KEY),null);
                    }
                } catch (Exception e) {

                }

            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg,String deepLink) {
        try {
            Intent resultIntent;
            SharedPreferences sp = getSharedPreferences("Pref_name",
                    Activity.MODE_WORLD_READABLE);
            String login_flag = sp.getString("login_flag", "");
            String login_flag_dashboard = sp.getString("login_flag_dashboard", "");
            String service_flag = sp.getString("service_flag", "");
            if (login_flag.equalsIgnoreCase("yes")) {
                if (login_flag_dashboard.equalsIgnoreCase("yes") && service_flag.equalsIgnoreCase("yes")) {
                    resultIntent = new Intent(getApplicationContext(), DashboardActivity.class);
                } else {
                    resultIntent = new Intent(getApplicationContext(), ProfileActivity.class);
                }
            } else {
                resultIntent = new Intent(getApplicationContext(), LoginActivity.class);
            }

            if(deepLink!=null) {
                resultIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("ServiceDealz://deeplink/" + deepLink));
            }
            resultIntent.putExtra("msg", msg);
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0,
                    resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            if (notify_no < 9) {
                notify_no = notify_no + 1;
            } else {
                notify_no = 0;
            }
            NotificationCompat.Builder mNotifyBuilder;
            NotificationManager mNotificationManager;
            mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setContentTitle(MyPush.CUSTOM_NOTIFICATION_TITLE)
                    .setSmallIcon(MyPush.CUSTOM_NOTIFICATION_IMAGE);
            // Set pending intent
            mNotifyBuilder.setContentIntent(resultPendingIntent);
            // Set Vibrate, Sound and Light
            int defaults = 0;
            defaults = defaults | Notification.DEFAULT_LIGHTS;
            defaults = defaults | Notification.DEFAULT_VIBRATE;
            defaults = defaults | Notification.DEFAULT_SOUND;
            mNotifyBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(msg));
            mNotifyBuilder.setDefaults(defaults);
            // Set the content for Notification
            mNotifyBuilder.setContentText(msg);
            // Set autocancel
            mNotifyBuilder.setAutoCancel(true);
            // Post a notification
            mNotificationManager.notify((int) (System.currentTimeMillis() + (5 * 1000)), mNotifyBuilder.build());
        } catch (Exception e) {

        }
    }


    public static void setTargetActivity(Activity act) {
        activity = act;
    }

}
