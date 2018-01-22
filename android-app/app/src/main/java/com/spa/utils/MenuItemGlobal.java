package com.spa.utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import com.spa.servicedealz.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import co.spa.sidemenu.model.SlideMenuItem;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.SharingHelper;

/**
 * Created by Diwakar on 4/16/2015.
 */

/**
 * FileName : MenuItemGlobal
 * Description :show slidemenu and open
 * Dependencies :No Dependencies
 */

public class MenuItemGlobal extends Observable {
    public static List<SlideMenuItem> list = new ArrayList<>();
    public static final String CLOSE = "Close";
    public static final String HOME = "Dashboard";
    public static final String GENERAL_INFO = "Profile";
    public static final String PREFERENCES = "Preferences";
    public static final String SERVICES = "Services";
    public static final String INVITE = "Refer Friends";
    public static final String LOGOUT = "Logout";
    public static final String SIGNUP = "Login";
    public static final String AUCTION = "Auction";
    public static final String SELLER = "Reseller";
    public static final String FEEDBACK = "Feedback";
    public static final String HELP = "Help";
    public static final String CHART = "Chart";
    public static final String GIFTCARD = "My Earnings";
    public static final String MY_ORDER = "My Orders";
    public static final String CALCULATOR = "Calculator";

    /*
    * show menu itemlist and perform action on indvidual item click
    *
    * @parm act
    * */
    public static List<SlideMenuItem> createMenuList(Activity act) {
        SharedPreferences sp = act.getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        list.clear();
       /* SlideMenuItem menuItem0 = new SlideMenuItem(CLOSE, R.drawable.icn_close);
        list.add(menuItem0);*/
        SlideMenuItem menuItem = new SlideMenuItem(HOME, R.drawable.home);
        list.add(menuItem);
      /*  SlideMenuItem menuItem3 = new SlideMenuItem(SERVICES, R.drawable.service);
        list.add(menuItem3);*/
        SlideMenuItem menuItem2 = new SlideMenuItem(PREFERENCES, R.drawable.prefrence);
        list.add(menuItem2);
        SlideMenuItem menuItem10 = new SlideMenuItem(CALCULATOR, R.drawable.calcicon);
        list.add(menuItem10);
        SlideMenuItem menuItem4 = new SlideMenuItem(INVITE, R.drawable.invite);
        list.add(menuItem4);
        SlideMenuItem menuItem1 = new SlideMenuItem(GENERAL_INFO, R.drawable.profile);
        list.add(menuItem1);
        SlideMenuItem menuItem6 = new SlideMenuItem(MY_ORDER, R.drawable.orderhistory);
        list.add(menuItem6);
       /* SlideMenuItem menuItem7 = new SlideMenuItem(Dashboard_Fragment.SELLER, R.drawable.seller);
        list.add(menuItem7);*/
        list.add(new SlideMenuItem(CHART, R.drawable.chart));
        list.add(new SlideMenuItem(GIFTCARD, R.drawable.gift));
        SlideMenuItem menuItem8 = new SlideMenuItem(FEEDBACK, R.drawable.feedback);
        list.add(menuItem8);
        SlideMenuItem menuItem9 = new SlideMenuItem(HELP, R.drawable.help);
        list.add(menuItem9);
        if (sp.getString(Constant.LOGIN_FLAG, "").equalsIgnoreCase("")) {
            SlideMenuItem menuItem5 = new SlideMenuItem(SIGNUP, R.drawable.login);
            list.add(menuItem5);
        } else {
            SlideMenuItem menuItem5 = new SlideMenuItem(LOGOUT, R.drawable.logout);
            list.add(menuItem5);
        }

        return list;
    }

    /*
    * get index from hashmap
    *
    * @parm provider
    * @parm arrayList
    * */
    public static int getIndex(String provider, ArrayList<HashMap<String, String>> arrayList) {
        int i = -1;
        for (Map<String, String> map : arrayList) {
            for (String key : map.keySet()) {
                if (map.get(key).equals(provider)) {

                    i = arrayList.indexOf(map);

                }
            }
        }

        return i;
    }

    /*
        * referralclick on item
        *
        * @parm activity
        * */
    public static void referralClicked(String code, Activity activity) {
        Branch branch = Branch.getInstance(activity);


        JSONObject obj = new JSONObject();
        try {
            obj.put("name", "ServiceDealz");
            // obj.put("auto_deeplink_key_1", "This is an auto deep linked value");
            // obj.put("message", "hello there with short url");
            obj.put("$og_title", "ServiceDealz - Switch & Save. Share & Earn");
            obj.put("$og_description", "Use my invite code " + code + " and get $10 as your first earning. You are Invited to join ServiceDealz. Save $1,000s by switching services via ServiceDealz.");
            obj.put("$og_image_url", "http://www.spad-metrics.com/qa/app_share.png");
        } catch (JSONException ex) {
            ex.printStackTrace();
        }

        new Branch.ShareLinkBuilder(activity, obj)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.FACEBOOK)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.EMAIL)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.MESSAGE)
                .addPreferredSharingOption(SharingHelper.SHARE_WITH.TWITTER)
                .setMessage("Use my invite code " + code + " and get $10 as your first earning. You are Invited to join ServiceDealz. Save $1,000s by switching services via ServiceDealz.")
                .setSubject("ServiceDealz - Switch & Save. Share & Earn")
                .setStage("stage1")
                .setFeature("feature1")
                .addTag("Tag1")
                .addTag("Tag2")
                .setDefaultURL("https://play.google.com/store/apps/details?id=com.kindred.android")
                .setCallback(new Branch.BranchLinkShareListener() {
                    @Override
                    public void onShareLinkDialogLaunched() {
                        Log.i("BranchTestBed", "onShareLinkDialogLaunched()");
                    }

                    @Override
                    public void onShareLinkDialogDismissed() {
                        Log.i("BranchTestBed", "onShareLinkDialogDismissed()");
                    }

                    @Override
                    public void onLinkShareResponse(String sharedLink, String sharedChannel, BranchError error) {
                        if (error != null) {
                            Log.i("BranchTestBed", "onLinkShareResponse... " + sharedLink + " " + sharedChannel + " " + error.getMessage());
                        } else {
                            Log.i("BranchTestBed", "onLinkShareResponse... " + sharedLink + " " + sharedChannel);
                        }
                    }

                    @Override
                    public void onChannelSelected(String channelName) {
                        Log.i("BranchTestBed", "onChannelSelected... " + channelName);
                    }
                })
                // Custom style for Copy url and More options
                //.setCopyUrlStyle(getResources().getDrawable(android.R.drawable.ic_menu_send),"Save this URl","Link added to clipboard")
                //.setMoreOptionStyle(getResources().getDrawable(android.R.drawable.ic_menu_search), "Show more")
                .shareLink();

    }

    /*
          * set edittextfirstCharacter caps
          *
          * @parm str
          * */
    private static String FirstCharacterCapital(String str) {
        /*if (str.contains(" ")) {
            String[] separated = str.split(" ");
            StringBuilder sb = null, stringbuild = null;
            stringbuild = new StringBuilder();
            for (int i = 0; i < separated.length; i++) {
                sb = new StringBuilder(separated[i]);
                if (separated[i].length() > 0) {

                    sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
                    stringbuild.append(sb.toString());
                    stringbuild.append(" ");

                }
            }
            return stringbuild.toString();
        } else {*/
        StringBuilder sb = new StringBuilder(str);
        if (str.length() > 1) {
            sb.setCharAt(0, Character.toUpperCase(sb.charAt(0)));
        }
        return sb.toString();
        // }
    }

    public static void setCapitalizeTextWatcher(final EditText editText) {
        final TextWatcher textWatcher = new TextWatcher() {

            int mStart = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mStart = start + count;
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Use WordUtils.capitalizeFully if you only want the first letter of each word to be capitalized
                String capitalizedText =  Wordutils.capitalize(editText.getText().toString());//FirstCharacterCapital();
                if (!capitalizedText.equals(editText.getText().toString())) {
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            editText.setSelection(mStart);
                            editText.removeTextChangedListener(this);
                        }
                    });
                    editText.setText(capitalizedText);
                }
            }
        };
        editText.addTextChangedListener(textWatcher);
    }
}
