package com.spa.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v4.content.IntentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.LoginActivity;
import com.spa.servicedealz.ui.LoginDailogActivity;
import com.spa.utils.Constant;

import java.util.Observable;

/**
 * Created by E0115Diwakar on 2/28/2015.
 */

/**
 * FileName : ShowDailog
 * Description :
 * Dependencies : Internet
 */
public class ShowDailog extends Observable {
    public static AlertDialog.Builder alertbox = null;


    public static String Show_Alert_Dailog(Activity activity) {
        alertbox = new AlertDialog.Builder(
                activity);
        alertbox.setTitle(Constant.WARNING);
        alertbox.setMessage(Constant.CONNECTION_ERROR);
        alertbox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        alertbox.setCancelable(false);
        alertbox.show();
        return null;
    }

    /*
       * Mehod to show alert dialog
       *
       * @parm activity
       * @parm message
       * */
    public static String Show_Alert_Login(Activity activity, String message) {
        alertbox = new AlertDialog.Builder(
                activity);

        alertbox.setMessage(message);
        alertbox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        alertbox.setCancelable(false);
        alertbox.show();
        return null;
    }

    /*
           * Mehod to show custom dialog
           *
           * @parm activity
           * @parm desc
           * @parm title
           * */
    public static void CustemDailog(Activity activity, String desc, String title) {
        TextView txt_title, txt_desc, txt_ok;
        ImageView imageViewcross;
        LayoutInflater layoutInflater = LayoutInflater.from(activity);

        View promptView = layoutInflater.inflate(R.layout.custemdailog_title_message_ok, null);
        alertbox = new AlertDialog.Builder(activity, R.style.DialogAnimation);
        txt_title = (TextView) promptView.findViewById(R.id.txt_title);
        txt_desc = (TextView) promptView.findViewById(R.id.txt_desc);
        txt_ok = (TextView) promptView.findViewById(R.id.txt_ok);
        imageViewcross=(ImageView)promptView.findViewById(R.id.img_close);

        txt_desc.setText(desc);
        txt_title.setText(title);
        // set prompts.xml to be the layout file of the alertdialog builder
        alertbox.setView(promptView);
       /* alertbox.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });*/
        final AlertDialog alertD = alertbox.create();
        imageViewcross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();
            }
        });
        txt_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertD.dismiss();

            }
        });

        alertD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        alertD.show();
    }

    /*
          * Mehod to show alert dialog
          *
          * @parm activity
          * @parm message
          * @parm title
          * */
    public static String Show_Alert_(final Activity activity, String message, String title) {
        try {


            alertbox = new AlertDialog.Builder(
                    activity, R.style.DialogAnimation);
            //   } else alertbox = new AlertDialog.Builder(
            //        activity);

            alertbox.setTitle(title);
            alertbox.setMessage(message + "                              ");
            alertbox.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });
            alertbox.setCancelable(false);
            final AlertDialog alertD = alertbox.create();
            alertD.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    int titleDividerId = activity.getResources()
                            .getIdentifier("titleDivider", "id", "android");

                    View titleDivider = alertD.findViewById(titleDividerId);
                    if (titleDivider != null) {
                        titleDivider.setBackgroundColor(activity.getResources()
                                .getColor(R.color.devider));
                    }
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            } else {
                alertD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
            alertD.show();
        } catch (Exception e) {
        }
        return null;
    }

    /*
          * Mehod to show alert dialog
          *
          * @parm activity
          * @parm message
          * @parm title
          * */
    public static String Show_Alert_Login(final Activity activity, String message, String title) {
        try {


            alertbox = new AlertDialog.Builder(
                    activity, R.style.DialogAnimation);
            //   } else alertbox = new AlertDialog.Builder(
            //        activity);

            alertbox.setTitle(title);
            alertbox.setMessage(message + "                              ");
            alertbox.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {

                            Intent PREFERENCES = new Intent(activity, LoginActivity.class);

                            PREFERENCES.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                            activity.startActivity(PREFERENCES);
                            activity.finish();
                        }
                    });
            alertbox.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface arg0, int arg1) {

                        }
                    });

            alertbox.setCancelable(false);
            final AlertDialog alertD = alertbox.create();
            alertD.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialog) {
                    int titleDividerId = activity.getResources()
                            .getIdentifier("titleDivider", "id", "android");

                    View titleDivider = alertD.findViewById(titleDividerId);
                    if (titleDivider != null) {
                        titleDivider.setBackgroundColor(activity.getResources()
                                .getColor(R.color.devider));
                    }
                }
            });
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            } else {
                alertD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
            alertD.show();
        } catch (Exception e) {
        }
        return null;
    }

    /*
              * Mehod to show alert dialog for login
              *
              * @parm activity
              * @parm message
              * @parm title
              * */
    public static String Show_Alert_login(final Activity activity, String message, String title) {

        Intent login = new Intent(activity, LoginDailogActivity.class);
        activity.startActivity(login);
      /*  final AlertDialog.Builder alertbox = new AlertDialog.Builder(
                activity, R.style.DialogAnimation);
        alertbox.setTitle(title);
        alertbox.setMessage(message + "                              ");
        alertbox.setPositiveButton("Sign In",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent in = new Intent(activity, Login.class);
                        activity.startActivity(in);
                        activity.finish();
                    }
                });
        alertbox.setCancelable(false);
        alertbox.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });
        //  alertDialog = builder.create();
        //alertDialog.getWindow().setLayout(600, 400); //Controlling width and height.
        //alertDialog.show();
//        Dialog d = alertbox.create();
//        d.getWindow().setLayout(600, WindowManager.LayoutParams.WRAP_CONTENT);
        // d.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        alertbox.show();*/
        return null;
    }
}
