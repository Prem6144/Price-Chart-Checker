package com.spa.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
/**
 * FileName : AuctionMainFragment
 * Description :
 * Dependencies : Internet
 */
public class BaseFragment extends Fragment {

    private static ProgressDialog mProgressDialog;

    public static void showDialog(Activity act) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(act);
            mProgressDialog.setTitle("Thinking...");
            mProgressDialog.setMessage("Doing the action...");
        }
        mProgressDialog.show();
    }
/*
* Method for hide dialog
* */
    public static void hideDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


}
