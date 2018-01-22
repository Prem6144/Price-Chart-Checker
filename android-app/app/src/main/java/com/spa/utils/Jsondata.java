package com.spa.utils;

//Created by Diwakar on 08-01-15.

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.Pair;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.spa.fragment.DealListFragment;
import com.spa.model.BundleDetailResponseModel;
import com.spa.model.bundleeservice.Bundle;
import com.spa.model.cableservice.Cable;
import com.spa.model.cellphondetail.DealDeatil;
import com.spa.model.cellphoneservice.Cellphone;
import com.spa.model.cellphoneservice.CellphoneDetail;
import com.spa.model.channels.ChannelList;
import com.spa.model.confirmationorder.Confirmationorder;
import com.spa.model.custemisecable.CustemiseCable;
import com.spa.model.custemizedeal.com.spa.model.celphonedeal.Custemizedeal;
import com.spa.model.dashbordmodel.Dashboard;
import com.spa.model.deallist.DealListItem;
import com.spa.model.equipments.CellphoneEquipments;
import com.spa.model.giftcardorder.Giftcardorder;
import com.spa.model.guestdashboard.GuestDashboard;
import com.spa.model.internetservice.Internet;
import com.spa.model.login.LoginData;
import com.spa.model.myorder.MyOrder;
import com.spa.model.orderdetail.OrderDetail;
import com.spa.model.orderhistory.MyEarnings;
import com.spa.model.password.CompPassword;
import com.spa.model.placeorder.business.BusinessUserPlaceOrder;
import com.spa.model.placeorder.residence.ResidenceUserPlaceOrder;
import com.spa.model.prefrence.Prefrence;
import com.spa.model.profile.Userdata;
import com.spa.model.referral.ReferralCode;
import com.spa.model.referral.ReferralCodeSubmit;
import com.spa.model.state.PrimaryAndSecondaryId;
import com.spa.model.state.State;
import com.spa.model.telephoneservice.Telephone;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.PlaceOrderAndPay;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;

import javax.crypto.SecretKey;


public class Jsondata extends Observable {
    public static String response, Success = "", comment_count = "", average_rating = "0.0", KEY_PASSWORD = "1428324560542678";
    public static ArrayList<String> description;
    public static ArrayList<HashMap<String, String>> provider_id_list = new ArrayList<>();
    public static ArrayList<Boolean> compare_boolean = new ArrayList<>();
    public static SecretKey secret;
//    public static String main_url = "http://103.243.5.242:2123"; //qa
    //     public static String main_url = " http://172.16.2.228:3000";//qa local
//     public static String main_url = "http://172.16.2.205:3000";//local
//    public static String main_url = "http://52.39.217.10";//production
    public static String main_url = "http://servicedealz.com/";//website
    public static Giftcardorder giftcardorder = null;
    // ***********************************GETTING PasswordCompleted*********************************************
    public static CompPassword getCompletedPassword(Context context) {
        CompPassword compPassword = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.sign_uppassword_url);
            if (response != "" || response != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                compPassword = gson.fromJson(response.toString(), CompPassword.class);
            }

        } catch (Exception e) {
            Utilities.logToFile("BusinessUserPlaceOrderDetail", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return compPassword;

    }
    // ***********************************GETTING Login*********************************************
    public static LoginData getlogin(final String user, final String pass, final String gcm_id,
                                     final Context context) {
        LoginData loginData = null;
        SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        try {
            List<Pair<String, String>> param = new ArrayList<>();
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("email", user));
            param.add(new Pair("password", pass));
            param.add(new Pair("gcm_id", gcm_id));
            param.add(new Pair("device_flag", "android"));
            response = CustomHttpClient.executeHttpPost(main_url + Url.login_url, param);
            if (response != "" || response != null) {
                loginData = new LoginData();
                JSONObject jobj = new JSONObject(response);
                loginData.setSuccess(jobj.getBoolean("success"));
                if (jobj.getBoolean("success")) {
                    loginData.setUser_preference(jobj.getBoolean("user_preference"));
                    LoginData.DataEntity dataEntity = new LoginData.DataEntity();
                    JSONObject user_detail = jobj.getJSONObject("data");
                    dataEntity.setId(user_detail.getInt("id"));
                    SharedPreferences.Editor editor = sp.edit();
                    if (user_detail.getString("zip").equals(null) || user_detail.isNull("zip") || user_detail.get("zip").toString().equalsIgnoreCase("")) {
                        editor.putString("zipcode_flag", "");
                        if (jobj.getBoolean("user_preference")) {
                            editor.putBoolean("main_flag", false);
                        }
                        editor.putInt("page_position", 0);

                        editor.putString("enter_zipcode",
                                "");
                        editor.putString("user_preference",
                                "false");
                    } else {
                        editor.putString("zipcode_flag",
                                "yes");
                        editor.putString("enter_zipcode",
                                decryptMsg(user_detail.getString("zip")));
                        if (jobj.getBoolean("user_preference")) {
                            editor.putString("service_flag",
                                    "yes");
                        }
                        editor.putString("user_preference", "true");
                    }
                    if (user_detail.getString("user_type").equalsIgnoreCase("residence")) {
                        editor.putString("BuisnessName", decryptMsg(user_detail.getString("first_name")) + " " + decryptMsg(user_detail.getString("last_name")));

                    } else {
                        editor.putString("BuisnessName", decryptMsg(user_detail.getString("business_name")));
                    }
                    editor.putString("user_type", user_detail.getString("user_type"));
                    editor.putString(Constant.FIRST_NAME, decryptMsg(user_detail.getString("first_name")));
                    editor.commit();
                    loginData.setData(dataEntity);
                } else {
                    Utilities.logToFile("LoginPostData", "device_id=" + sp.getString("androidid", "") +
                            ",token=" + sp.getString("api_token", "") +
                            ",email=" + user +
                            ",password=" + pass +
                            ",gcm_id=" + gcm_id +
                            ",device_flag=android");
                    Utilities.logToFile("Login", response);
                }
            } else {
                Success = "";
            }


        } catch (JSONException e) {
            Success = "";
            Utilities.logToFile("LoginPostData", "device_id=" + sp.getString("androidid", "") +
                    ",token=" + sp.getString("api_token", "") +
                    ",email=" + user +
                    ",password=" + pass +
                    ",gcm_id=" + gcm_id +
                    ",device_flag=android");
            Utilities.logToFile("Login", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Success = "";
            Utilities.logToFile("LoginPostData", "device_id=" + sp.getString("androidid", "") +
                    ",token=" + sp.getString("api_token", "") +
                    ",email=" + user +
                    ",password=" + pass +
                    ",gcm_id=" + gcm_id +
                    ",device_flag=android");
            Utilities.logToFile("Login", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("LoginPostData", "device_id=" + sp.getString("androidid", "") +
                    ",token=" + sp.getString("api_token", "") +
                    ",email=" + user +
                    ",password=" + pass +
                    ",gcm_id=" + gcm_id +
                    ",device_flag=android");
            Utilities.logToFile("Login", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return loginData;

    }
    // ***********************************GETTING Services*********************************************

    public static String getservices(final String user_id, final String service_name, final String service_provider, final String contract_date, final String is_contract, final String contract_fee,
                                     final String startdate, final String upload, final String download, final Context context) {

        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("service_category_id", service_name));
            param.add(new Pair("service_provider_id", service_provider));
            param.add(new Pair("start_date", startdate));
            param.add(new Pair("price", contract_fee));
            param.add(new Pair("end_date", contract_date));
            param.add(new Pair("is_contract", is_contract));
            param.add(new Pair("app_user_id", user_id));

            response = CustomHttpClient.executeHttpPost(main_url + Url.services_save_url, param);
            if (response != "" || response != null) {
                JSONObject jobj = new JSONObject(response);
                Success = jobj.getString("success");
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("ServicePostData", response);
                }
            } else {
                Utilities.logToFile("ServicePostData", response);
            }


        } catch (JSONException e) {
            Success = "";
            Utilities.logToFile("ServicePostData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Success = "";
            Utilities.logToFile("ServicePostData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("ServicePostData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return Success;

    }

    public static String getinternetservices(final String user_id, final String service_name, final String service_provider, final String contract_date, final String is_contract, final String contract_fee,
                                             final String startdate, final String upload, final String download, final Context context) {

        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("service_category_id", service_name));
            param.add(new Pair("service_provider_id", service_provider));
            param.add(new Pair("start_date", startdate));
            param.add(new Pair("price", contract_fee));
            param.add(new Pair("end_date", contract_date));
            param.add(new Pair("is_contract", is_contract));
            param.add(new Pair("app_user_id", user_id));
            param.add(new Pair("upload_speed", upload));
            param.add(new Pair("download_speed", download));

            response = CustomHttpClient.executeHttpPost(main_url + Url.services_save_url, param);


            if (response != "" || response != null) {
                JSONObject jobj = new JSONObject(response);
                Success = jobj.getString("success");
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("ServicePostData", response);
                }
            } else {
                Utilities.logToFile("ServicePostData", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("ServicePostData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return Success;

    }
// ***********************************GETTING Services*********************************************

    public static String getTelephoneservices(final String user_id, final String service_name, final String service_provider, final String contract_date, final String is_contract, final String price,
                                              final String startdate, final String minute, final String text_sms, String is_talk, String is_text, final Context context) {

        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("service_category_id", service_name));
            param.add(new Pair("service_provider_id", service_provider));
            param.add(new Pair("end_date", contract_date));
            param.add(new Pair("start_date", startdate));
            param.add(new Pair("domestic_call_minutes", minute));
            param.add(new Pair("international_call_minutes", text_sms));
            param.add(new Pair("price", price));
            param.add(new Pair("is_contract", is_contract));
            param.add(new Pair("app_user_id", user_id));

            param.add(new Pair("domestic_call_unlimited", is_talk));
            param.add(new Pair("international_call_unlimited", is_text));

            response = CustomHttpClient.executeHttpPost(main_url + Url.services_save_url, param);


            if (response != "" || response != null) {
                JSONObject jobj = new JSONObject(response);
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("ServicePostData", response);
                }
            } else {
                Utilities.logToFile("ServicePostData", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("ServicePostData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return Success;

    }

    // ***********************************GETTING Services*********************************************

    public static String getBundleservices(final String user_id, final String service_name, final String service_provider, final String end_date, final String is_contract, final String price,
                                           final String startdate, final String minute, final String text_sms, String is_talk, String is_text,
                                           final String upload_speed, final String download_speed, final String free_channels, String premium_channels, String bundle_combo, final Context context) {

        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("bundle_combo", bundle_combo));
            param.add(new Pair("service_category_id", service_name));
            param.add(new Pair("service_provider_id", service_provider));
            param.add(new Pair("end_date", end_date));
            param.add(new Pair("start_date", startdate));
            param.add(new Pair("domestic_call_minutes", minute));
            param.add(new Pair("international_call_minutes", text_sms));
            param.add(new Pair("price", price));
            param.add(new Pair("is_contract", is_contract));
            param.add(new Pair("app_user_id", user_id));
            param.add(new Pair("domestic_call_unlimited", is_talk));
            param.add(new Pair("international_call_unlimited", is_text));
            param.add(new Pair("upload_speed", upload_speed));
            param.add(new Pair("download_speed", download_speed));
            param.add(new Pair("free_channels", free_channels));
            param.add(new Pair("premium_channels", premium_channels));
            param.add(new Pair("data_plan", ""));
            param.add(new Pair("data_speed", ""));
            param.add(new Pair("data", ""));
            response = CustomHttpClient.executeHttpPost(main_url + Url.services_save_url, param);


            if (response != "" || response != null) {
                JSONObject jobj = new JSONObject(response);
                Success = jobj.getString("success");
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("ServicePostData", response);
                }
            } else {
                Utilities.logToFile("ServicePostData", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("ServicePostData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return Success;

    }
// ***********************************GETTING Services*********************************************

    public static String getCellphoneservices(final String user_id, final String service_name, final String service_provider, final String contract_date, final String is_contract, final String price,
                                              final String startdate, final String minute, final String text_sms, String is_talk, String is_text, String dataplan, String dataspeeds, String numberofline, String cellphonedeviceid, final Context context) {

        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("service_category_id", service_name));
            param.add(new Pair("service_provider_id", service_provider));
            param.add(new Pair("end_date", contract_date));
            param.add(new Pair("start_date", startdate));
            param.add(new Pair("domestic_call_minutes", minute));
            param.add(new Pair("international_call_minutes", text_sms));
            param.add(new Pair("price", price));
            param.add(new Pair("is_contract", is_contract));
            param.add(new Pair("app_user_id", user_id));
            param.add(new Pair("no_of_lines", numberofline));
            param.add(new Pair("data_plan", dataplan));
            param.add(new Pair("data_speed", dataspeeds));
            param.add(new Pair("cellphone_detail_id", cellphonedeviceid));

            param.add(new Pair("domestic_call_unlimited", is_talk));
            param.add(new Pair("international_call_unlimited", is_text));

            response = CustomHttpClient.executeHttpPost(main_url + Url.services_save_url, param);


            if (response != "" || response != null) {
                JSONObject jobj = new JSONObject(response);
                // JSONObject jobj1 = jobj.getJSONObject("response");
                Success = jobj.getString("success");
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("ServicePostData", response);
                }
            } else {
                Utilities.logToFile("ServicePostData", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("ServicePostData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return Success;

    }

    public static String get_cableservices(final String user_id, final String service_cat_id, final String service_provider_id, final String end_date, final String is_contract, final String contract_fee,

                                           final String startdate, final String free_channels, final String premeoium_channels, final Context context) {

        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("service_category_id", service_cat_id));
            param.add(new Pair("service_provider_id", service_provider_id));
            param.add(new Pair("end_date", end_date));
            param.add(new Pair("start_date", startdate));
            param.add(new Pair("free_channels", free_channels));
            param.add(new Pair("premium_channels", premeoium_channels));
            param.add(new Pair("price", contract_fee));
            param.add(new Pair("is_contract", is_contract));
            param.add(new Pair("app_user_id", user_id));

            response = CustomHttpClient.executeHttpPost(main_url + Url.services_save_url, param);
            if (response != "" || response != null) {
                JSONObject jobj = new JSONObject(response);
                Success = jobj.getString("success");
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("ServicePostData", response);
                }
            } else {
                Utilities.logToFile("ServicePostData", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("ServicePostData", response + "Crash================================\n\n" + e);
        }
        return Success;

    }

    // ***********************************GETTING Services*********************************************
    public static String getDeviceAndToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        return "&device_id=" + sp.getString("androidid", "") + "&token=" + sp.getString("api_token", "");
    }

    public static String get_app_userid(final String email,
                                        final Context context) {

        try {


            response = CustomHttpClient.executeHttpGet(main_url + Url.app_userid_url + "email=" + email + getDeviceAndToken(context));


            if (response != null && response != "") {
                JSONObject jobj = new JSONObject(response);
                Success = jobj.getString("success");
                if (Success.equalsIgnoreCase("true")) {
                    String user_preference = jobj.getString("user_preference");
                    JSONObject jobj1 = jobj.getJSONObject("app_user");
                    SharedPreferences sp = context.getSharedPreferences("Pref_name",
                            Activity.MODE_WORLD_READABLE);
                    SharedPreferences.Editor editor = sp.edit();
                    if (jobj1.isNull("zip") || jobj1.getString("zip").equals(null) || jobj1.get("zip").toString().equalsIgnoreCase("")) {
                        editor.putString("zipcode_flag",
                                "");
                        if (user_preference.equalsIgnoreCase("true")) {
                            editor.putBoolean("main_flag", false);
                        }
                        editor.putInt("page_position", 0);
                        editor.putString("enter_zipcode",
                                "");
                        editor.putString("user_preference",
                                "false");
                    } else {
                        editor.putString("enter_zipcode",
                                decryptMsg(jobj1.getString("zip")));
                        if (user_preference.equalsIgnoreCase("false")) {
                            editor.putInt("page_position", 1);
                        }
                        editor.putString("zipcode_flag",
                                "yes");
                        if (Boolean.parseBoolean(user_preference)) {
                            editor.putString("service_flag",
                                    "yes");
                        }
                        editor.putString("user_preference",
                                user_preference);
                    }


                    editor.commit();
                    response = jobj1.getString("id");
                }

                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("GetAppUserId", response);
                }
            } else {
                Utilities.logToFile("GetAppUserId", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("GetAppUserId", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return response;

    }

    // ***********************************Unsubscribe*********************************************
    public static String get_Unsuscribe_service(final String appuserid, final String catid,
                                                final Context context) {

        try {
            response = CustomHttpClient.executeHttpDelete(main_url + Url.unsubscribe_url + "app_user_id=" + appuserid + "&category=" + catid + getDeviceAndToken(context));


            if (response != "" || response != null) {
                JSONObject jobj = new JSONObject(response);
                Success = jobj.getString("success");
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("Unsuscribe_service", response);
                }
            } else {
                Utilities.logToFile("Unsuscribe_service", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("Unsuscribe_service", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return response;

    }

    // ***********************************General Info*********************************************

    public static String getgeneralinfo(final String user_id, final String first_name, final String last_name, final String email, final String state, final String city,
                                        final String zip, final String address, final Context context, String image_base64, String usertype, String buisnessname, String buisness_type, String federal_number, String ssn_number, String mobileNumber, String primarynumber, String secondarynumber, String address1, String address2, String towncity, String primaryid, String secondaryid, String addressZipcode, String addressname) {

        try {
           /* List<Pair<String, String>> param = new ArrayList<>();
            param.add(new Pair("first_name", first_name));
            param.add(new Pair("picture_data", image_base64));
            param.add(new Pair("last_name", last_name));
            param.add(new Pair("email", email));
            param.add(new Pair("state", state));
            param.add(new Pair("city", city));

            param.add(new Pair("zip", zip));
            param.add(new Pair("user_type", usertype));
            param.add(new Pair("id", user_id));
            param.add(new Pair("address", address));
            param.add(new Pair("business_name", buisnessname));*/

// Receiving side
            // byte[] data1 = Base64.decode(base64, Base64.DEFAULT);
            //String text = new String(data, "UTF-8");


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("email", email);
            jsonObject.put("user_type", usertype);
            jsonObject.put("first_name", encryptMsg(first_name));
            jsonObject.put("last_name", encryptMsg(last_name));
            jsonObject.put("picture_data", image_base64);
            jsonObject.put("zip", encryptMsg(zip));

            jsonObject.put("mobile", encryptMsg(mobileNumber));
            jsonObject.put("primary_id", primaryid);
            jsonObject.put("primary_id_number", primarynumber);
            jsonObject.put("secondary_id", secondaryid);
            jsonObject.put("secondary_id_number", secondarynumber);

            JSONObject jsonObjectBuisness = new JSONObject();
            jsonObjectBuisness.put("business_name", encryptMsg(buisnessname));
            jsonObjectBuisness.put("business_type", buisness_type);
            jsonObjectBuisness.put("federal_number", encryptMsg(federal_number));
            jsonObjectBuisness.put("ssn", encryptMsg(ssn_number));
            JSONArray jsonArrayBuisnessAddress = new JSONArray();
            JSONObject jsonObjectbuisnessAddress = new JSONObject();
            jsonObjectbuisnessAddress.put("address_type", 2);
            jsonObjectbuisnessAddress.put("address_name", addressname);
            jsonObjectbuisnessAddress.put("city", towncity);
            jsonObjectbuisnessAddress.put("zip", encryptMsg(addressZipcode));
            jsonObjectbuisnessAddress.put("address1", address1);
            jsonObjectbuisnessAddress.put("address2", address2);
            jsonObjectbuisnessAddress.put("state", state);
            jsonArrayBuisnessAddress.put(0, jsonObjectbuisnessAddress);
            if (usertype.equalsIgnoreCase("residence")) {
                jsonObject.put("app_user_addresses", jsonArrayBuisnessAddress);
            } else {
                jsonObject.put("business_addresses", jsonArrayBuisnessAddress);
            }

            jsonObject.put("business", jsonObjectBuisness);
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            jsonObject.put("device_id", sp.getString("androidid", ""));
            jsonObject.put("token", sp.getString("api_token", ""));
            response = CustomHttpClient.executeHttpPostJsonObject(main_url + Url.update_profile_url,
                    jsonObject.toString());


            if (response != "" || response != null) {
                JSONObject jobj = new JSONObject(response);
                Success = jobj.getString("success");
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("GetProfileData", response);
                }
            } else {
                Utilities.logToFile("GetProfileData", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("GetProfileData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return Success;

    }

    public static String encryptMsg(String plain) {
        try {
          /*  byte[] iv = new byte[16];
            new SecureRandom().nextBytes(iv);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(KEY_PASSWORD.getBytes("utf-8"), "AES"), new IvParameterSpec(iv));
            byte[] cipherText = cipher.doFinal(plain.getBytes("utf-8"));
           // byte[] ivAndCipherText = getCombinedArray(iv, cipherText);*/
            // Sending side
            byte[] data = plain.trim().getBytes("UTF-8");
            String base64 = Base64.encodeToString(data, Base64.DEFAULT);

// Receiving side

            return base64.trim();
            //  return Base64.encodeToString(ivAndCipherText, Base64.NO_WRAP);
        } catch (Exception e) {
            // Ln.e(e);
            return null;
        }
    }

    public static String decryptMsg(String encoded) {
        try {
            byte[] data = Base64.decode(encoded, Base64.DEFAULT);
            String text = new String(data, "UTF-8");
          /*  byte[] ivAndCipherText = Base64.decode(encoded, Base64.NO_WRAP);
            byte[] iv = Arrays.copyOfRange(ivAndCipherText, 0, 16);
            byte[] cipherText = Arrays.copyOfRange(ivAndCipherText, 16, ivAndCipherText.length);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(KEY_PASSWORD.getBytes("utf-8"), "AES"), new IvParameterSpec(iv));*/
            return text;
            // return new String(cipher.doFinal(cipherText), "utf-8");
        } catch (Exception e) {
            // Ln.e(e);
            return null;
        }
    }

    private static byte[] getCombinedArray(byte[] one, byte[] two) {
        byte[] combined = new byte[one.length + two.length];
        for (int i = 0; i < combined.length; ++i) {
            combined[i] = i < one.length ? one[i] : two[i - one.length];
        }
        return combined;
    }

    // ***********************************General Info*********************************************

    public static String send_contact(final String jsonobject) {

        try {
            response = CustomHttpClient.executeHttpPostJsonObject(main_url + Url.refer_contact,
                    jsonobject);
            if (response != "" && response != null) {
                JSONObject jobj = new JSONObject(response);
                Success = jobj.getString("success");
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("SendContactData", response);
                }
            } else {
                Utilities.logToFile("SendContactData", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("SendContactData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return Success;

    }

    public static Confirmationorder send_order_buisness(String user_id, String name, String email, String billing_address1, String billing_address2, String billing_country, String billing_zipcode,
                                                        String shiping_address1, String shiping_address2, String shiping_country,
                                                        String shiping_zipcode, String mobile_no, String bisiness_name, String db_number, String federal_number,
                                                        String buisness_type, String ssn_number, String buisness_mobile_number, String deal_id, String deal_price, String effective_price,
                                                        String managername, String lastname, String primary_id, String secondary_id,
                                                        String DOB, String dbumber, String DBA, String is_shipping_address_same, String service_address1, String service_address2, String service_city,
                                                        String service_zipcode,String freetext, String is_service_address_same, Integer id, String primary_id_number, String secondary_id_number, String billingState,
                                                        String serviceState, String shipingState, String billingaddressname, String shipingaddressname, String serviceaddressname,
                                                        String equipementprice, String equipementcolorname, String equipementCellphoneDetailId,
                                                        String planprice, String planTitle, String planid, String ServiceId, String ServiceName, String ServicePrice, String mChannelpremiumprice, String mChalnePremiumID, String mChalneEquipmentID, String chalneEquipmentPrice, String tvAdapterPrice, String tvAdapterId, int providerid, Context context) {
        Confirmationorder confirmationorder = null;
        try {


            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObjectOrder = new JSONObject();
            jsonObjectOrder.put("app_user_id", user_id);
            jsonObjectOrder.put("status", "new_order");
            jsonObjectOrder.put("order_type", "1");
            jsonObjectOrder.put("primary_id", primary_id);
            jsonObjectOrder.put("secondary_id", secondary_id);
            jsonObjectOrder.put("primary_id_number", primary_id_number);
            jsonObjectOrder.put("secondary_id_number", secondary_id_number);
            jsonObjectOrder.put("free_text", freetext);

            jsonObject.put("order", jsonObjectOrder);
            JSONArray jsonObjectOrederitems = new JSONArray();
            JSONObject jsonObjectOrderitems = new JSONObject();

            jsonObjectOrderitems.put("deal_id", deal_id);
            jsonObjectOrderitems.put("deal_price", deal_price);
            jsonObjectOrderitems.put("effective_price", effective_price);
            jsonObjectOrderitems.put("status", "new_order");
//            jsonObjectOrderitems.put("primary_id", primary_id);
//            jsonObjectOrderitems.put("secondary_id", secondary_id);
            jsonObjectOrederitems.put(0, jsonObjectOrderitems);
            jsonObject.put("order_items", jsonObjectOrederitems);

            JSONObject jsonObjectAppUser = new JSONObject();
            jsonObjectAppUser.put("email", email);
            jsonObjectAppUser.put("user_type", "business");
            jsonObjectAppUser.put("first_name", encryptMsg(name));
            jsonObjectAppUser.put("last_name", encryptMsg(lastname));

            jsonObjectAppUser.put("is_service_address_same", is_service_address_same);
            jsonObjectAppUser.put("is_shipping_address_same", is_shipping_address_same);

            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            jsonObjectAppUser.put("zip", encryptMsg(sp.getString(Constant.ZIPCODE, "")));
            jsonObjectAppUser.put("mobile", encryptMsg(mobile_no));
            jsonObject.put("app_user", jsonObjectAppUser);

            JSONArray jsonObjectOrederequipmentsarray = new JSONArray();
            if (providerid == 4) {

                if (PlaceOrderAndPay.custemCellphoneEquipmentsAdditionalOffers.getDevicelist() != null) {
                    for (int i = 0; PlaceOrderAndPay.custemCellphoneEquipmentsAdditionalOffers.getDevicelist().size() > i; i++) {
                        JSONObject jsonObjectOrderequipments = new JSONObject();
                        jsonObjectOrderequipments.put("equipment_price", PlaceOrderAndPay.custemCellphoneEquipmentsAdditionalOffers.getDevicelist().get(i).getPhoneprice());
                        jsonObjectOrderequipments.put("equipment_id", equipementCellphoneDetailId);/*PlaceOrderAndPay.custemCellphoneEquipmentsAdditionalOffers.getDevicelist().get(i).getPhoneid()*/
                        jsonObjectOrderequipments.put("color", PlaceOrderAndPay.custemCellphoneEquipmentsAdditionalOffers.getDevicelist().get(i).getPhonecolor());
                        jsonObjectOrederequipmentsarray.put(i, jsonObjectOrderequipments);
                    }
                    if (jsonObjectOrederequipmentsarray.length() > 0) {
                        jsonObject.put("order_equipments", jsonObjectOrederequipmentsarray);
                    }
                }


                JSONArray jsonObjectOrederattributes = new JSONArray();
                JSONObject jsonObjectOrderattributes = new JSONObject();
                jsonObjectOrderattributes.put("ref_id", planid);
                jsonObjectOrderattributes.put("ref_type", "cellphone");
                jsonObjectOrderattributes.put("price", planprice);

                jsonObjectOrederattributes.put(0, jsonObjectOrderattributes);
                if (!planprice.equalsIgnoreCase("0.0")) {
                    jsonObject.put("order_attributes", jsonObjectOrederattributes);
                }
                JSONArray jsonObjectOrederextraservices = new JSONArray();
                JSONObject jsonObjectOrderextraservices = new JSONObject();
                jsonObjectOrderextraservices.put("deal_extra_service_id", ServiceId);
                jsonObjectOrderextraservices.put("service_name", ServiceName);
                jsonObjectOrderextraservices.put("price", ServicePrice);

                jsonObjectOrederextraservices.put(0, jsonObjectOrderextraservices);
                if (!ServicePrice.equalsIgnoreCase("0.0")) {
                    jsonObject.put("order_extra_services", jsonObjectOrederextraservices);
                }
            }

            if (providerid == 3) {
            /*cable Hash start*/
                JSONArray jsonObjectOrederCableequipmentsarray = new JSONArray();
                JSONObject jsonObjectOrderCableequipments = new JSONObject();
                jsonObjectOrderCableequipments.put("equipment_id", mChalnePremiumID);
                jsonObjectOrderCableequipments.put("equipment_price", chalneEquipmentPrice);

                if (!mChannelpremiumprice.equalsIgnoreCase("")) {
                    jsonObjectOrederCableequipmentsarray.put(jsonObjectOrderCableequipments);
                    jsonObject.put("order_cable_equipments", jsonObjectOrederCableequipmentsarray);
                }
                JSONArray jsonObjectOrederCableattributes = new JSONArray();
                JSONObject jsonObjectOrderCableextraservices = new JSONObject();
                jsonObjectOrderCableextraservices.put("ref_id", tvAdapterId);
                jsonObjectOrderCableextraservices.put("ref_type", "cable");
                jsonObjectOrderCableextraservices.put("price", tvAdapterPrice);
                jsonObjectOrederCableattributes.put(0, jsonObjectOrderCableextraservices);
                if (!tvAdapterPrice.equalsIgnoreCase("")) {
                    jsonObject.put("order_cable_attributes", jsonObjectOrederCableattributes);
                }

                JSONArray jsonObjectOrederCablextraservices = new JSONArray();
                JSONObject jsonObjectOrderCablextraservices = new JSONObject();
                jsonObjectOrderCableextraservices.put("ref_id", mChalneEquipmentID);
                jsonObjectOrderCableextraservices.put("ref_type", "Channel");
                jsonObjectOrderCableextraservices.put("price", mChannelpremiumprice);
                jsonObjectOrederCablextraservices.put(0, jsonObjectOrderCablextraservices);
                if (!mChannelpremiumprice.equalsIgnoreCase("")) {
                    jsonObjectOrederCableequipmentsarray.put(jsonObjectOrederCablextraservices);
                    jsonObject.put("order_cable_equipments", jsonObjectOrederCableequipmentsarray);
                }
            }

            JSONObject jsonObjectBuisness = new JSONObject();
            jsonObjectBuisness.put("business_name", encryptMsg(bisiness_name));
            jsonObjectBuisness.put("id", id);
            jsonObjectBuisness.put("business_type", buisness_type);
            jsonObjectBuisness.put("federal_number", encryptMsg(federal_number));
            jsonObjectBuisness.put("business_status", "Recontracted");
            jsonObjectBuisness.put("db_number", encryptMsg(db_number));
            jsonObjectBuisness.put("dob", encryptMsg(String.valueOf(DOB)));
            jsonObjectBuisness.put("ssn", encryptMsg(ssn_number));
            jsonObjectBuisness.put("contact_number", encryptMsg(buisness_mobile_number));

            jsonObjectBuisness.put("manager_name", managername);
            jsonObjectBuisness.put("manager_contact", encryptMsg(buisness_mobile_number));
            jsonObjectBuisness.put("business_dba", encryptMsg(DBA));

            jsonObject.put("business", jsonObjectBuisness);

            JSONArray jsonArrayBuisnessAddress = new JSONArray();
            JSONObject jsonObjectbuisnessAddress = new JSONObject();
            jsonObjectbuisnessAddress.put("address_type", 2);
            jsonObjectbuisnessAddress.put("address_name", billingaddressname);
            jsonObjectbuisnessAddress.put("zip", billing_zipcode);
            jsonObjectbuisnessAddress.put("city", billing_country);
            jsonObjectbuisnessAddress.put("state", billingState);
            jsonObjectbuisnessAddress.put("address1", billing_address1);
            jsonObjectbuisnessAddress.put("address2", billing_address2);
            jsonObjectbuisnessAddress.put("contact_number", buisness_mobile_number);
            jsonObjectbuisnessAddress.put("manager_name", managername);
            jsonObjectbuisnessAddress.put("manager_contact", buisness_mobile_number);


            JSONObject jsonObjectshipingbuisnessAddress = new JSONObject();
            jsonObjectshipingbuisnessAddress.put("address_type", 1);
            jsonObjectshipingbuisnessAddress.put("address_name", shipingaddressname);
            jsonObjectshipingbuisnessAddress.put("state", shipingState);
            jsonObjectshipingbuisnessAddress.put("city", shiping_country);
            jsonObjectshipingbuisnessAddress.put("zip", shiping_zipcode);
            jsonObjectshipingbuisnessAddress.put("address1", shiping_address1);
            jsonObjectshipingbuisnessAddress.put("address2", shiping_address2);
            jsonObjectshipingbuisnessAddress.put("contact_number", buisness_mobile_number);
            jsonObjectshipingbuisnessAddress.put("manager_name", managername);
            jsonObjectshipingbuisnessAddress.put("manager_contact", buisness_mobile_number);

            JSONObject jsonObjectshipingServiceAddress = new JSONObject();
            jsonObjectshipingServiceAddress.put("address_type", 0);
            jsonObjectshipingServiceAddress.put("address_name", serviceaddressname);
            jsonObjectshipingServiceAddress.put("zip", service_zipcode);
            jsonObjectshipingServiceAddress.put("city", service_city);
            jsonObjectshipingServiceAddress.put("state", serviceState);
            jsonObjectshipingServiceAddress.put("address1", service_address1);
            jsonObjectshipingServiceAddress.put("address2", service_address2);
            jsonObjectshipingServiceAddress.put("contact_number", buisness_mobile_number);
            jsonObjectshipingServiceAddress.put("manager_name", managername);
            jsonObjectshipingServiceAddress.put("manager_contact", buisness_mobile_number);

            jsonArrayBuisnessAddress.put(0, jsonObjectbuisnessAddress);
            jsonArrayBuisnessAddress.put(1, jsonObjectshipingbuisnessAddress);
            jsonArrayBuisnessAddress.put(2, jsonObjectshipingServiceAddress);

            jsonObject.put("business_addresses", jsonArrayBuisnessAddress);
            jsonObject.put("device_id", sp.getString("androidid", ""));
            jsonObject.put("token", sp.getString("api_token", ""));
            response = CustomHttpClient.executeHttpPostJsonObject(main_url + Url.order_url,
                    jsonObject.toString());
            if (response != "" && response != null) {
                JSONObject jobj = new JSONObject(response);
                Success = jobj.getString("success");
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                confirmationorder = gson.fromJson(response.toString(), Confirmationorder.class);
                giftcardorder = gson.fromJson(response.toString(), Giftcardorder.class);
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("SendOrderData", response);
                }
            } else {
                Utilities.logToFile("SendOrderData", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("SendOrderData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return confirmationorder;

    }


    public static String getfb_save(String regid, final String user_id, final String first_name, final String last_name, final String email, final String state, final String city,
                                    final String zip, final String address, final Context context) {

        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);

            param.add(new Pair("email",email));
            try {
//                param.add(new Pair("password", (mEditTextPassword.getText().toString())));
                param.add(new Pair("gcm_id", regid));
                param.add(new Pair("device_flag", "android"));
                param.add(new Pair("first_name",encryptMsg(first_name) ));
                param.add(new Pair("last_name", encryptMsg(last_name)));
                param.add(new Pair("device_id", sp.getString("androidid", "")));
                param.add(new Pair("token",sp.getString("api_token", "")));
            } catch (Exception e) {
                e.printStackTrace();
            }


//            param.add(new Pair("device_id", sp.getString("androidid", "")));
//            param.add(new Pair("token", sp.getString("api_token", "")));
//            param.add(new Pair("first_name", encryptMsg(first_name)));
//            param.add(new Pair("last_name", encryptMsg(last_name)));
//            param.add(new Pair("gcm_id", regid));
//            param.add(new Pair("device_flag", "android"));
////            param.add(new Pair("address", address));
//            param.add(new Pair("email", email));
//            param.add(new Pair("state", state));
//            param.add(new Pair("city", city));
//            param.add(new Pair("zip", encryptMsg(zip)));


            response = CustomHttpClient.executeHttpPost(main_url + Url.sign_up_url,
                    param);
            if (response != "" && response != null) {
                JSONObject jobj = new JSONObject(response);
                Success = jobj.getString("success");
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("zipcode_flag",
                        "");
                editor.putInt("page_position", 0);
                editor.putString("enter_zipcode",
                        "");
                editor.putString("user_preference",
                        "false");
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("SendSignupDataSocial", response);
                }
            } else {
                Utilities.logToFile("SendSignupDataSocial", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("SendSignupDataSocial", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return response;

    }


    // ***********************************Prefrences*********************************************

    public static String get_save_prefrences(final String user_id, final String service_notification, final String day, final String trendingNotification,
                                             final String repeat_notification_frequency, final String trending_deal_frequency, final String receive_call, final String min_service_provider_rating, final String min_deal_rating, final String receive_email, final String receive_text, final Context context) {

        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("recieve_notification", service_notification));
            param.add(new Pair("day", day));
            param.add(new Pair("recieve_trending_deals", trendingNotification));
            param.add(new Pair("app_user_id", user_id));

            param.add(new Pair("repeat_notification_frequency", repeat_notification_frequency));
            param.add(new Pair("trending_deal_frequency", trending_deal_frequency));
            param.add(new Pair("receive_call", receive_call));
            param.add(new Pair("min_service_provider_rating", min_service_provider_rating));
            param.add(new Pair("min_deal_rating", min_deal_rating));
            param.add(new Pair("receive_email", receive_email));
            param.add(new Pair("receive_text", receive_text));


            response = CustomHttpClient.executeHttpPost(main_url + Url.preferences_save_url,
                    param);


            if (response != "" || response != null) {
                JSONObject jobj = new JSONObject(response);
                Success = jobj.getString("success");

                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("SendPrefrenceData", response);
                }
            } else {
                Utilities.logToFile("SendPrefrenceData", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("SendPrefrenceData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return Success;

    }

    public static String getforget_password(final String email,
                                            final Context context) {
        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("email", email));
            response = CustomHttpClient.executeHttpPost(main_url + Url.forget_password_url, param);
            JSONObject jsonobj = new JSONObject(response);
            if (response != "" || response != null) {
                Success = jsonobj.getString("success");
                if (!jsonobj.getBoolean("success")) {
                    Utilities.logToFile("Sendforget_password", response);
                }
            } else {
                Utilities.logToFile("Sendforget_password", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("Sendforget_password", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return Success;

    }


    public static Dashboard get_deal_dashboard(final String user_id,
                                               final Context context) {
        Dashboard dashboard = new Dashboard();
        //  Dashboard goc = null;
        List<Dashboard.DashboardDataEntity> dashboardDataEntities = new ArrayList<>();
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.dashboard_url + user_id + getDeviceAndToken(context));

            if (response == "" || response == null) {
                Utilities.logToFile("DashBoardData", response);
                Success = "";
            } else {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                JSONObject jsonObject = new JSONObject(response);
                JSONArray jsonArray = jsonObject.getJSONArray("dashboard_data");

                for (int i = 0; jsonArray.length() > i; i++) {
                    JSONObject jobj = jsonArray.getJSONObject(i);
                    Dashboard.DashboardDataEntity dashboardDataEntity = new Dashboard.DashboardDataEntity();

                    JSONObject jsonArrayt_rending_deal = null, jsonArrayt_best_deal = null, jsonArrayt_order_deal = null;
                    if (!jobj.isNull("trending_deal")) {
                        jsonArrayt_rending_deal = jobj.getJSONObject("trending_deal");
                    }
                    if (!jobj.isNull("best_deal")) {
                        jsonArrayt_best_deal = jobj.getJSONObject("best_deal");
                    }
                    if (!jobj.isNull("order_deal")) {
                        jsonArrayt_order_deal = jobj.getJSONObject("order_deal");
                    }


                    if (jsonArrayt_rending_deal != null) {
                        Dashboard.DashboardDataEntity.TrendingDealEntity trendingDealEntity = new Dashboard.DashboardDataEntity.TrendingDealEntity();
                        int id = jsonArrayt_rending_deal.getInt("id");
                        trendingDealEntity.setId(id);
                        int service_provider_id = jsonArrayt_rending_deal.getInt("service_provider_id");
                        trendingDealEntity.setService_provider_id(service_provider_id);

                        String title = jsonArrayt_rending_deal.getString("title");
                        trendingDealEntity.setTitle(title);
                        String short_description = jsonArrayt_rending_deal.getString("short_description");
                        trendingDealEntity.setShort_description(short_description);

                        String effective_price = jsonArrayt_rending_deal.getString("effective_price");
                        trendingDealEntity.setEffective_price(effective_price);
                        String detail_description = jsonArrayt_rending_deal.getString("detail_description");
                        trendingDealEntity.setDetail_description(detail_description);
                        String url = jsonArrayt_rending_deal.getString("url");
                        trendingDealEntity.setUrl(url);
                        String start_date = jsonArrayt_rending_deal.getString("start_date");
                        trendingDealEntity.setStart_date(start_date);
                        String end_date = jsonArrayt_rending_deal.getString("end_date");
                        trendingDealEntity.setEnd_date(end_date);
                      /*  String is_nationwide = jsonArrayt_rending_deal.getBoolean("is_nationwide");
                        trendingDealEntity.setIs_nationwide(is_nationwide);
                        String deal_type = jsonArrayt_rending_deal.getString("deal_type");
                        trendingDealEntity.setDeal_type(deal_type);
                        String is_active = jsonArrayt_rending_deal.getBoolean("is_active");
                        trendingDealEntity.setIs_active(is_active);*/
                        String deal_image_url = jsonArrayt_rending_deal.getString("deal_image_url");
                        trendingDealEntity.setDeal_image_url(deal_image_url);
                        String average_rating = jsonArrayt_rending_deal.getString("average_rating");
                        trendingDealEntity.setAverage_rating(average_rating);
                        int rating_count = jsonArrayt_rending_deal.getInt("rating_count");
                        trendingDealEntity.setRating_count(rating_count);
                        int contract_period = jsonArrayt_rending_deal.getInt("contract_period");
                        trendingDealEntity.setContract_period(contract_period);
                        String deal_price = jsonArrayt_rending_deal.getString("deal_price");
                        trendingDealEntity.setDeal_price(deal_price);
                        dashboardDataEntity.setTrending_deal(trendingDealEntity);
                    }
                    if (jsonArrayt_order_deal != null) {
                        Dashboard.DashboardDataEntity.OrderDealEntity orderDealEntity = new Dashboard.DashboardDataEntity.OrderDealEntity();
                        int id = jsonArrayt_order_deal.getInt("id");
                        orderDealEntity.setId(id);
                        int contract_period = jsonArrayt_order_deal.getInt("contract_period");
                        orderDealEntity.setContract_period(contract_period);
                        int service_provider_id = jsonArrayt_order_deal.getInt("service_provider_id");
                        orderDealEntity.setService_provider_id(service_provider_id);
                        int order_id = jsonArrayt_order_deal.getInt("order_id");
                        orderDealEntity.setOrder_id(order_id);
                        String title = jsonArrayt_order_deal.getString("title");
                        orderDealEntity.setTitle(title);
                        String order_status = jsonArrayt_order_deal.getString("order_status");
                        orderDealEntity.setOrder_status(order_status);
                        String short_description = jsonArrayt_order_deal.getString("short_description");
                        orderDealEntity.setShort_description(short_description);

                        String effective_price = jsonArrayt_order_deal.getString("effective_price");
                        orderDealEntity.setEffective_price(effective_price);
                        String detail_description = jsonArrayt_order_deal.getString("detail_description");
                        orderDealEntity.setDetail_description(detail_description);
                        String url = jsonArrayt_order_deal.getString("url");
                        orderDealEntity.setUrl(url);
                        String start_date = jsonArrayt_order_deal.getString("start_date");
                        orderDealEntity.setStart_date(start_date);
                        String end_date = jsonArrayt_order_deal.getString("end_date");
                        orderDealEntity.setEnd_date(end_date);
                      /*  String is_nationwide = jsonArrayt_rending_deal.getBoolean("is_nationwide");
                        trendingDealEntity.setIs_nationwide(is_nationwide);
                        String deal_type = jsonArrayt_rending_deal.getString("deal_type");
                        trendingDealEntity.setDeal_type(deal_type);
                        String is_active = jsonArrayt_rending_deal.getBoolean("is_active");
                        trendingDealEntity.setIs_active(is_active);*/
                        String deal_image_url = jsonArrayt_order_deal.getString("deal_image_url");
                        orderDealEntity.setDeal_image_url(deal_image_url);
                        //String average_rating = jsonArrayt_rending_deal.getString("average_rating");
                        // orderDealEntity.setAverage_rating(average_rating);
                        int rating_count = jsonArrayt_order_deal.getInt("rating_count");
                        orderDealEntity.setRating_count(rating_count);
                        String deal_price = jsonArrayt_order_deal.getString("deal_price");
                        orderDealEntity.setDeal_price(deal_price);
                        dashboardDataEntity.setOrder_deal(orderDealEntity);
                    }

                    if (jsonArrayt_best_deal != null) {

                        Dashboard.DashboardDataEntity.BestDealEntity bestDealEntity = new Dashboard.DashboardDataEntity.BestDealEntity();
                        int id = jsonArrayt_best_deal.getInt("id");
                        bestDealEntity.setId(id);
                        int contract_period = jsonArrayt_best_deal.getInt("contract_period");
                        bestDealEntity.setContract_period(contract_period);
                        int service_provider_id = jsonArrayt_best_deal.getInt("service_provider_id");
                        bestDealEntity.setService_provider_id(service_provider_id);
                        String title = jsonArrayt_best_deal.getString("title");
                        bestDealEntity.setTitle(title);
                        String short_description = jsonArrayt_best_deal.getString("short_description");
                        bestDealEntity.setShort_description(short_description);
                        String effective_price = jsonArrayt_best_deal.getString("effective_price");
                        bestDealEntity.setEffective_price(effective_price);
                        String detail_description = jsonArrayt_best_deal.getString("detail_description");
                        bestDealEntity.setDetail_description(detail_description);
                        String url = jsonArrayt_best_deal.getString("url");
                        bestDealEntity.setUrl(url);
                        String start_date = jsonArrayt_best_deal.getString("start_date");
                        bestDealEntity.setStart_date(start_date);
                        String end_date = jsonArrayt_best_deal.getString("end_date");
                        bestDealEntity.setEnd_date(end_date);
                      /*  boolean is_nationwide = jsonArrayt_best_deal.getBoolean("is_nationwide");
                        bestDealEntity.setIs_nationwide(is_nationwide);*/
                        String deal_type = jsonArrayt_best_deal.getString("deal_type");
                        bestDealEntity.setDeal_type(deal_type);
                      /*  boolean is_active = jsonArrayt_best_deal.getBoolean("is_active");
                        bestDealEntity.setIs_active(is_active);*/
                        String deal_image_url = jsonArrayt_best_deal.getString("deal_image_url");
                        bestDealEntity.setDeal_image_url(deal_image_url);
                        String average_rating = jsonArrayt_best_deal.getString("average_rating");
                        bestDealEntity.setAverage_rating(average_rating);
                        int rating_count = jsonArrayt_best_deal.getInt("rating_count");
                        bestDealEntity.setRating_count(rating_count);
                        String deal_price = jsonArrayt_best_deal.getString("deal_price");
                        bestDealEntity.setDeal_price(deal_price);
                        dashboardDataEntity.setBest_deal(bestDealEntity);
                    }
                    if (!jobj.isNull("trending_deal") || !jobj.isNull("best_deal")) {
                        boolean best_deal_flag = jobj.getBoolean("best_deal_flag");
                        dashboardDataEntity.setBest_deal_flag(best_deal_flag);
                        String you_save_text = jobj.getString("you_save_text");
                        dashboardDataEntity.setYou_save_text(you_save_text);
                        String contract_fee = jobj.getString("contract_fee");
                        dashboardDataEntity.setContract_fee(contract_fee);
                        String service_provider_name = jobj.getString("service_provider_name");
                        dashboardDataEntity.setService_provider_name(service_provider_name);
                        String service_category_name = jobj.getString("service_category_name");
                        dashboardDataEntity.setService_category_name(service_category_name);
                        int service_category_id = jobj.getInt("service_category_id");
                        dashboardDataEntity.setService_category_id(service_category_id);
                        dashboardDataEntities.add(dashboardDataEntity);
                    }


                }
                dashboard.setDashboard_data(dashboardDataEntities);
                // called fromJson() method and passed incoming buffer from json
                // file
                //  goc = gson.fromJson(response.toString(), Dashboard.class);
                Success = "true";
                Utilities.logToFile("DashBoardData", response);
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("DashBoardData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return dashboard;

    }

    public static MyOrder get_my_order(final String user_id,
                                       final Context context) {
        MyOrder myOrder = new MyOrder();
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.my_order + user_id + getDeviceAndToken(context));
            Utilities.logToFile("MyOrderData", response);
            if (response == "" || response == null) {
                Success = "";
            } else {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                myOrder = gson.fromJson(response.toString(), MyOrder.class);
            }


        } catch (Exception e) {

            e.printStackTrace();
            Success = "";
        }
        return myOrder;

    }

    public static Custemizedeal getCustemizeDealz(final String user_id,
                                                  final Context context) {
        Custemizedeal custemizeDEalList = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.custemizedeals);
            Utilities.logToFile("MyOrderData", response);
            if (response == "" || response == null) {
                Success = "";
            } else {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                custemizeDEalList = gson.fromJson(response.toString(), Custemizedeal.class);
            }


        } catch (Exception e) {

            e.printStackTrace();
            Success = "";
        }
        return custemizeDEalList;

    }

    public static MyEarnings get_orderhistory(final String user_id,
                                              final Context context) {
        MyEarnings myEarnings = new MyEarnings();
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.my_gifts + user_id + getDeviceAndToken(context));
            Utilities.logToFile("MyEarning", response);
            if (response == "" || response == null) {
                Success = "";
            } else {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                myEarnings = gson.fromJson(response.toString(), MyEarnings.class);
            }


        } catch (Exception e) {
            e.printStackTrace();
            Success = "";
        }
        return myEarnings;

    }

    public static GuestDashboard get_deal_guest_dashboard(final String zipcode,
                                                          final Context context) {
        GuestDashboard goc = null;
        try {
            SharedPreferences sp = context.getSharedPreferences("Pref_name",
                    Context.MODE_WORLD_READABLE);
            String deal_type = sp.getString("deal_type", "");
            response = CustomHttpClient.executeHttpGet(main_url + Url.dashboard_url + "zip_code=" + encryptMsg(zipcode) + "&deal_type=" + deal_type + getDeviceAndToken(context));
            Utilities.logToFile("GuestDashboard", response);
            if (response == "" || response == null) {
                Success = "";
            } else {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                goc = gson.fromJson(response.toString(), GuestDashboard.class);
                Success = "true";
                Log.d("tag", goc.toString());
            }


        } catch (Exception e) {
            e.printStackTrace();
            Success = "";
        }
        return goc;

    }
    // ***********************************GETTING Services*********************************************

    public static String get_rating(final String user_id, final String deal_id, final String rating, final String comment_title, final String comment_text,
                                    final Context context) {

        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("deal_id", deal_id));
            param.add(new Pair("rating_point", rating));
            param.add(new Pair("app_user_id", user_id));
            param.add(new Pair("comment_text", comment_text));
            param.add(new Pair("comment_title", comment_title));
            response = CustomHttpClient.executeHttpPost(main_url + Url.comment_url, param);


            if (response != "" || response != null) {
                JSONObject jobj = new JSONObject(response);
                Success = jobj.getString("success");
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("SendRating", response);
                }
            } else {
                Utilities.logToFile("SendRating", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("SendRating", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return Success;

    }


    public static ReferralCodeSubmit get_referral_code(final String user_id, final String referralcode, String referral_gift_coins, final String referrer_gift_coins, final Context context) {
        ReferralCodeSubmit referralCodeSubmit = null;
        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("referral_code", referralcode));
            param.add(new Pair("referrer_id", user_id));
            param.add(new Pair("referral_gift_coins", referral_gift_coins));
            param.add(new Pair("referrer_gift_coins", referrer_gift_coins));
            response = CustomHttpClient.executeHttpPost(main_url + Url.submit_referral_code, param);
            if (response != "" || response != null) {
                JSONObject jobj1 = new JSONObject(response);
                referralCodeSubmit = new ReferralCodeSubmit();
                referralCodeSubmit.setSuccess(jobj1.getBoolean("success"));
                if (!jobj1.getBoolean("success")) {
                    Utilities.logToFile("ReferralCodeSubmit", response);
                }
            } else {
                Utilities.logToFile("ReferralCodeSubmit", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("ReferralCodeSubmit", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return referralCodeSubmit;

    }

    public static String get_cashout(final String user_id, final String cashamount, String cashout_voucher, final String cashout_cash, final String cashout_email, final Context context) {
        String status = "";
        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("reedeem_amount", cashamount));
            param.add(new Pair("app_user_id", user_id));
            param.add(new Pair("gift_card_type", cashout_voucher));
            param.add(new Pair("is_cash", cashout_cash));
            param.add(new Pair("email_id", cashout_email));
            response = CustomHttpClient.executeHttpPost(main_url + Url.cashout_url, param);
            if (response != "" || response != null) {
                JSONObject jobj1 = new JSONObject(response);
                if (!jobj1.getBoolean("success")) {
                    Utilities.logToFile("Cashout", response);
                }
            } else {
                Utilities.logToFile("Cashout", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("Cashout", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return status;

    }

    public static BusinessUserPlaceOrder get_user_detail(final String user_id, String deal_id, final Context context) {
        BusinessUserPlaceOrder buisnessUserPlaceOrder = null;
        String status = "";
        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("deal_ids", deal_id));
            param.add(new Pair("app_user_id", user_id));
            response = CustomHttpClient.executeHttpPost(main_url + Url.order_user_detail_url, param);
            Utilities.logToFile("BusinessUserPlaceOrderDetail", response);
            if (response != "" || response != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                buisnessUserPlaceOrder = gson.fromJson(response.toString(), BusinessUserPlaceOrder.class);
            }

        } catch (Exception e) {
            Utilities.logToFile("BusinessUserPlaceOrderDetail", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return buisnessUserPlaceOrder;

    }

    public static State getState(Context context) {
        State state = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.state + getDeviceAndToken(context));
            if (response != "" || response != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                state = gson.fromJson(response.toString(), State.class);
            }

        } catch (Exception e) {
            Utilities.logToFile("BusinessUserPlaceOrderDetail", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return state;

    }

    public static CellphoneEquipments getcellphone_equipments(Context context, String userid, String dealid) {
        CellphoneEquipments cellphoneEquipments = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.cellphone_equipments + "app_user_id=" + userid + "&deal_id=" + dealid + getDeviceAndToken(context));
            if (response != "" || response != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                cellphoneEquipments = gson.fromJson(response.toString(), CellphoneEquipments.class);
            }

        } catch (Exception e) {
            Utilities.logToFile("CellphoneEquipments", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return cellphoneEquipments;

    }

    public static PrimaryAndSecondaryId getPrimaryAndSecondaryId(String user_id, Context context) {
        PrimaryAndSecondaryId primaryAndSecondaryId = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.primaryidandsecondaryid + "app_user_id=" + user_id + getDeviceAndToken(context));
            if (response != "" || response != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                primaryAndSecondaryId = gson.fromJson(response.toString(), PrimaryAndSecondaryId.class);
            }

        } catch (Exception e) {
            Utilities.logToFile("BusinessUserPlaceOrderDetail", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return primaryAndSecondaryId;

    }

    public static ResidenceUserPlaceOrder get_user_detail_residence(final String user_id, String deal_id, final Context context) {
        ResidenceUserPlaceOrder residenceUserPlaceOrder = null;
        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("deal_ids", deal_id));
            param.add(new Pair("app_user_id", user_id));
            response = CustomHttpClient.executeHttpPost(main_url + Url.order_user_detail_url, param);
            Utilities.logToFile("ResidenceUserPlaceOrderDetail", response);
            if (response != "" || response != null) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                residenceUserPlaceOrder = gson.fromJson(response.toString(), ResidenceUserPlaceOrder.class);
              /*  residenceUserPlaceOrder.setSuccess(jobj1.getBoolean("success"));
                if (residenceUserPlaceOrder.isSuccess()) {
                    ResidenceUserPlaceOrder.AppUserEntity appUserEntity = new ResidenceUserPlaceOrder.AppUserEntity();
                    ResidenceUserPlaceOrder.AppUserAddressesEntity appUserAddressesEntity = new ResidenceUserPlaceOrder.AppUserAddressesEntity();
                    JSONObject jsonObjectAppUser = new JSONObject();
                    jsonObjectAppUser = jobj1.getJSONObject("app_users");

                    JSONArray jsonArrayAppUserAddress = new JSONArray();
                    jsonArrayAppUserAddress = jobj1.getJSONArray("app_user_addresses");
                }*/
            }
        } catch (Exception e) {
            Utilities.logToFile("ResidenceUserPlaceOrderDetail", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return residenceUserPlaceOrder;

    }

    public static String get_validate_federal(final String user_id, String business_type, final String business_name, String federal_number, String ssn_number, final Context context) {
        try {
            List<Pair<String, String>> param = new ArrayList<>();
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            param.add(new Pair("token", sp.getString("api_token", "")));
            param.add(new Pair("business_type", business_type));
            param.add(new Pair("business_name", encryptMsg(business_name)));
            param.add(new Pair("federal_number", encryptMsg(federal_number)));
            param.add(new Pair("ssn", encryptMsg(ssn_number)));
            response = CustomHttpClient.executeHttpPost(main_url + Url.validate_federal_no_url, param);
            Utilities.logToFile("validate_federal", response);
        } catch (Exception e) {
            Utilities.logToFile("Cashout", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return response;

    }

    public static ArrayList<HashMap<String, String>> getservices_prefences(final String user_id, final String servicename,
                                                                           final Context context) {
        ArrayList<HashMap<String, String>> service_preferences = new ArrayList<HashMap<String, String>>();

        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.services_url + "app_user_id=" + user_id + "&category=" + servicename + getDeviceAndToken(context));
            if (response != "" || response != null) {
                JSONObject jobj1 = new JSONObject(response);
                Success = jobj1.getString("success");
                if (Success.equalsIgnoreCase("true")) {
                    JSONObject jobj = jobj1.getJSONObject("service_preference");
                    String service_name = jobj.getString("service_category_id");
                    String service_provider = jobj.getString("service_provider_id");
                    String end_date = jobj.getString("end_date");
                    String price = jobj.getString("price");
                    String is_contract = jobj.getString("is_contract");
                    String start_date = jobj.getString("start_date");
                    HashMap<String, String> profile_detail = new HashMap<String, String>();
                    profile_detail.put("service_name", service_name);
                    profile_detail.put("service_provider", service_provider);
                    profile_detail.put("end_date", end_date);
                    profile_detail.put("is_contract", is_contract);
                    profile_detail.put("price", price);
                    profile_detail.put("start_date", start_date);
                    service_preferences.add(profile_detail);
                }

            } else {
                Utilities.logToFile("Services_Prefences", response);
                Success = "";
            }

        } catch (Exception e) {
            Utilities.logToFile("Services_Prefences", response + "Crash================================\n\n" + e);
            Success = "";
            e.printStackTrace();
        }
        return service_preferences;

    }

    public static ReferralCode getreferral_code(final String user_id,
                                                final Context context) {
        ReferralCode referralCode = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.referral_code + "app_user_id=" + user_id + getDeviceAndToken(context));

            if (response != "" || response != null) {
                JSONObject jsonobj = new JSONObject(response);
                referralCode = new ReferralCode();
                referralCode.setSuccess(jsonobj.getBoolean("success"));
                if (jsonobj.getBoolean("success")) {
                    referralCode.setApp_user_code(jsonobj.getString("app_user_code"));
                    referralCode.setRefer_status(jsonobj.getBoolean("refer_status"));
                }
                if (!jsonobj.getBoolean("success")) {
                    Utilities.logToFile("Referral_Code", response);
                }
            } else {
                Utilities.logToFile("Referral_Code", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("Referral_Code", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return referralCode;

    }

    public static Internet getInternetservices_prefences(final String user_id, final String servicename,
                                                         final Context context) {
        Internet internet = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.services_url + "app_user_id=" + user_id + "&category=" + servicename + getDeviceAndToken(context));
            if (response != "" || response != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                 Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                internet = gson.fromJson(response.toString(), Internet.class);
                Utilities.logToFile("Internetservices", response);
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("Internetservices", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return internet;

    }

    public static Telephone getTelephoneservices_prefences(final String user_id, final String servicename,
                                                           final Context context) {
        Telephone telephone = null;
        try {

            response = CustomHttpClient.executeHttpGet(main_url + Url.services_url + "app_user_id=" + user_id + "&category=" + servicename + getDeviceAndToken(context));
            if (response != "" || response != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                telephone = gson.fromJson(response.toString(), Telephone.class);
                Utilities.logToFile("Telephoneservices", response);
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("Telephoneservices", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return telephone;

    }

    public static Bundle getBundleservices_prefences(final String user_id, final String servicename,
                                                     final Context context) {
        Bundle bundle = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.services_url + "app_user_id=" + user_id + "&category=" + servicename + getDeviceAndToken(context));
            if (response != "" || response != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                bundle = gson.fromJson(response.toString(), Bundle.class);
                Utilities.logToFile("Bundleservices", response);
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("Bundleservices", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return bundle;

    }

    public static Cellphone getCellphoneservices_prefences(final String user_id, final String servicename,
                                                           final Context context) {
        Cellphone cellphone = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.services_url + "app_user_id=" + user_id + "&category=" + servicename + getDeviceAndToken(context));
            if (response != "" || response != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                cellphone = gson.fromJson(response.toString(), Cellphone.class);
                Utilities.logToFile("Cellphoneservices", response);
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("Cellphoneservices", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return cellphone;

    }

    public static Cable getCableServices_Prefences(final String user_id, final String servicename,
                                                   final Context context) {
        Cable cable = null;
        try {

            response = CustomHttpClient.executeHttpGet(main_url + Url.services_url + "app_user_id=" + user_id + "&category=" + servicename + getDeviceAndToken(context));
            if (response != "" || response != null) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                cable = gson.fromJson(response.toString(), Cable.class);
                Utilities.logToFile("CableServices", response);
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("CableServices", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return cable;

    }

    public static ArrayList<String> getservices_provider(final String user_id, final String servicename,
                                                         final Context context) {
        ArrayList<String> services_provider = new ArrayList<String>();

        try {


            response = CustomHttpClient.executeHttpGet(main_url + Url.services_provider_url + "app_user_id=" + user_id + "&category=" + servicename + getDeviceAndToken(context));

            Object json = new JSONTokener(response).nextValue();
            provider_id_list = new ArrayList<>();
            services_provider.clear();
            provider_id_list.clear();

            JSONObject jsonObject = (JSONObject) json;
            Success = jsonObject.getString("success");
            if (Success.equalsIgnoreCase("true")) {
                JSONArray json1 = jsonObject.getJSONArray("service_provider");
                HashMap<String, String> provider_hashmap = new HashMap<>();
                provider_hashmap.put("id", "0");
                provider_hashmap.put("provider_name", "Select Vendor");
                provider_id_list.add(provider_hashmap);
                if (json != null) {

                    for (int i = 0; i < json1.length(); i++) {
                        HashMap<String, String> provider_hashmap1 = new HashMap<>();
                        JSONObject jobj = json1.getJSONObject(i);
                        String service_category_name = jobj.getString("name");
                        String service_provider_id = jobj.getString("id");
                        services_provider.add(service_category_name);
                        provider_hashmap1.put("id", service_provider_id);
                        provider_hashmap1.put("provider_name", service_category_name);
                        provider_id_list.add(provider_hashmap1);
                    }
                    Collections.sort(services_provider);

                    services_provider.add(0, "Select Vendor");
                    int indexof = services_provider.indexOf("Other");
                    services_provider.remove(indexof);
                    services_provider.add("Other");
                } else {

                }

            }
            if (!jsonObject.getBoolean("success")) {
                Utilities.logToFile("Services_provider", response);
            }

        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("Services_provider", response + "Crash================================\n\n" + e);
            e.printStackTrace();

        }
        return services_provider;

    }

    public static ArrayList<String> getservices_completed(final String user_id,
                                                          final Context context) {
        ArrayList<String> services_provider = new ArrayList<String>();

        try {


            response = CustomHttpClient.executeHttpGet(main_url + Url.services_completed_url + "app_user_id=" + user_id + getDeviceAndToken(context));
            Object json = new JSONTokener(response).nextValue();
            JSONObject jsonObject = (JSONObject) json;
            Success = jsonObject.getString("success");
            if (Success.equalsIgnoreCase("true")) {
                JSONArray json1 = jsonObject.getJSONArray("service_preferences");
                if (json != null) {
                    for (int i = 0; i < json1.length(); i++) {
                        JSONObject jobj = json1.getJSONObject(i);
                        String service_name = jobj.getString("service_category_id");

                        if (service_name.equalsIgnoreCase("6") || service_name.equalsIgnoreCase("7") || service_name.equalsIgnoreCase("8")) {
                        } else {
                            services_provider.add(service_name);
                        }

                    }
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return services_provider;

    }

    public static Userdata get_general_info(final String user_id,
                                            final Context context) {
        Userdata userdata = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.geneneral_info_url + "id=" + user_id + getDeviceAndToken(context));
            if (response != "" || response != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                userdata = gson.fromJson(response.toString(), Userdata.class);
                Utilities.logToFile("GetUserData", response);
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("GetUserData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return userdata;

    }


    public static BundleDetailResponseModel GetBundleDealDetail(final String deal_id,
                                                                final Context context) {
        BundleDetailResponseModel bundleDetailResponseModel = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.deal_details + "deal_id="
                    + deal_id + getDeviceAndToken(context));
            if (response != "" || response != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                bundleDetailResponseModel = gson.fromJson(response.toString(), BundleDetailResponseModel.class);
                Utilities.logToFile("GetUserData", response);
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("GetUserData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return bundleDetailResponseModel;

    }

    public static DealDeatil GetCustmizeDealDetail(final String deal_id,
                                                   final Context context) {
        DealDeatil custemizeDealDetail = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.custemizedealsdetail + "deal_id="
                    + deal_id + getDeviceAndToken(context));
            if (response != "" || response != null) {
                System.out.println("Responce" + response.toString());
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                custemizeDealDetail = gson.fromJson(response.toString(), DealDeatil.class);
                Utilities.logToFile("GetUserData", response);
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("GetUserData", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return custemizeDealDetail;

    }


    //new code

    public static CustemiseCable get_cable_deal(final String deal_id, final Context context) {
        Success = "True";
        CustemiseCable custemiseCellphone = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.custemizedealsdetail + "deal_id="
                    + deal_id + getDeviceAndToken(context));
            if (response != "" || response != null) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson businessGson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                custemiseCellphone = businessGson.fromJson(response.toString(), CustemiseCable.class);
                Utilities.logToFile("ChannelList", response);
            }

        } catch (Exception e) {
            Utilities.logToFile("ChannelList", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return custemiseCellphone;


    }

    public static CellphoneDetail get_cellphonedetail(final String deal_id, final Context context) {
        CellphoneDetail custemiseCellphone = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.cellphonedetail + getDeviceAndToken(context));
            if (response != "" || response != null) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson businessGson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                custemiseCellphone = businessGson.fromJson(response.toString(), CellphoneDetail.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return custemiseCellphone;


    }

    public static Prefrence get_prefrences(final String user_id,
                                           final Context context) {
        Prefrence prefrence = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.preferences_url + "app_user_id=" + user_id + getDeviceAndToken(context));
            if (response != "" || response != null) {
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                prefrence = gson.fromJson(response.toString(), Prefrence.class);
                Utilities.logToFile("Prefrence", response);
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("Prefrence", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return prefrence;

    }

    public static String SendDeviceInformation(final String androidid,
                                               String deviceId, String providerName, final Context context) {
        String str = "";
        try {
            List<Pair<String, String>> param = new ArrayList<>();
            param.add(new Pair("device_id", androidid));
            param.add(new Pair("imei", deviceId));
            param.add(new Pair("service_provider", "Airtel"));
            response = CustomHttpClient.executeHttpPost(main_url + Url.sendDeviceInformation, param);
            if (response != "" || response != null) {
                Success = "true";
                JSONObject jsonObject = new JSONObject(response);
                SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                        Activity.MODE_WORLD_READABLE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("androidid", androidid);
                str = jsonObject.getString("session_token");
                editor.putString("api_token", jsonObject.getString("session_token"));
                editor.commit();

            } else {
                Utilities.logToFile("SendDeviceInformation", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("SendDeviceInformation", response + "Crash================================\n\n" + e);

            e.printStackTrace();
        }
        return str;

    }

    public static String getYouSave(final String userid,
                                    final Context context) {
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.youSave + "id=" + userid + getDeviceAndToken(context));
            Utilities.logToFile("YouSave", response);
            if (response != "" || response != null) {
                JSONObject jsonObject = new JSONObject(response);
                Success = jsonObject.getString("you_save");
            } else {
                Success = "0";
            }

        } catch (JSONException e) {
            Success = "0";
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            Success = "0";

            e.printStackTrace();
        } catch (Exception e) {
            Success = "0";
            e.printStackTrace();
        }
        return Success;

    }

    public static String DestroyToken(final Context context) {
        try {
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            List<Pair<String, String>> param = new ArrayList<>();
            param.add(new Pair("device_id", sp.getString("androidid", "")));
            response = CustomHttpClient.executeHttpPost(main_url + Url.tokenDestroy, param);
            Utilities.logToFile("DestroyToken", response);
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("DestroyToken", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return null;

    }

    public static DealListItem get_deal(final String app_user_id, final String servicename,
                                        final Context context) {
        Success = "false";
        DealListItem dealListItem = new DealListItem();
        List<DealListItem.BundleDealsEntity> bundleDealsEntities = new ArrayList<>();
        List<DealListItem.BundleDealsEntity.DealAdditionalOffersEntity> dealBundleAdditionalOffersEntities = null;
        List<DealListItem.DealEntity> dealEntities = new ArrayList<>();
        List<DealListItem.DealEntity.DealAdditionalOffersEntity> dealAdditionalOffersEntities = null;
        List<DealListItem.DealEntity.DealEquipmentsEntity> equipmentsEntities = null;
        try {

            SharedPreferences sp = context.getSharedPreferences("Pref_name",
                    Context.MODE_WORLD_READABLE);
            String enterzipcode = sp.getString("enter_zipcode", "");
            String sortbyspeeds = sp.getString("sortbyflag", "");
            String deal_id = sp.getString("deal_id", "");
            String login_flag = sp.getString("login_flag", "");
            String deal_type = sp.getString("deal_type", "");
            if (!login_flag.equalsIgnoreCase("")) {
                if (DealListFragment.Best_Deal_Flag) {
                    if (deal_id.equalsIgnoreCase("")) {
                        sortbyspeeds = "price";
                        response = CustomHttpClient.executeHttpGet(main_url + Url.dashboard_url + "app_user_id=" + app_user_id + "&category=" + servicename + "&zip_code=" + enterzipcode + "&sorting_flag=" + sortbyspeeds + getDeviceAndToken(context));
                    } else {
                        response = CustomHttpClient.executeHttpGet(main_url + Url.deal_url + "app_user_id=" + app_user_id + "&service_category_id=" + servicename + "&zip_code=" + enterzipcode + getDeviceAndToken(context));
                    }
                } else {
                    response = CustomHttpClient.executeHttpGet(main_url + Url.dashboard_url + "app_user_id=" + app_user_id + "&category=" + servicename + "&zip_code=" + enterzipcode + "&sorting_flag=" + sortbyspeeds + getDeviceAndToken(context));
                }
            } else {
                if (sortbyspeeds.equalsIgnoreCase("")) {
                    sortbyspeeds = "price";
                }
                response = CustomHttpClient.executeHttpGet(main_url + Url.dashboard_url + "category=" + servicename + "&deal_type=" + deal_type + "&zip_code=" + sp.getString("guestzipcode", "75024") + "&sorting_flag=" + sortbyspeeds + getDeviceAndToken(context));
            }
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = null, jsonArrayBundle = null;
            if (response != "" || response != null) {
                Success = "true";
                if (!jsonObject.isNull("deal")) {
                    jsonArray = jsonObject.getJSONArray("deal");
                }
                if (!jsonObject.isNull("bundle_deals")) {
                    jsonArrayBundle = jsonObject.getJSONArray("bundle_deals");
                }
                compare_boolean.clear();
                if (jsonArray != null) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jobj = jsonArray.getJSONObject(i);

                        DealListItem.DealEntity dealEntity = new DealListItem.DealEntity();
                        int id = jobj.getInt("id");
                        dealEntity.setId(id);
                        String contract_period = jobj.getString("contract_period");
                        dealEntity.setContract_period(contract_period);
                        String service_category_id = jobj.getString("service_category_id").trim();
                        dealEntity.setService_category_id(service_category_id);
                        String short_description = jobj.getString("short_description").trim();
                        dealEntity.setShort_description(short_description);
                        String effective_price = jobj.getString("effective_price").trim();
                        dealEntity.setEffective_price(effective_price);
                        Boolean cutomisable = jobj.getBoolean("is_customisable");
                        dealEntity.setCustomisable(cutomisable);
                        Boolean is_order_available = jobj.getBoolean("is_order_available");
                        dealEntity.setIs_order_available(is_order_available);

                        String deal_price = jobj.getString("deal_price").trim();
                        dealEntity.setDeal_price(deal_price);
                        String start_date = jobj.getString("start_date").trim();
                        dealEntity.setStart_date(start_date);
                        String end_date = jobj.getString("end_date").trim();
                        dealEntity.setEnd_date(end_date);
                        String detail_description = jobj.getString("detail_description").trim();
                        dealEntity.setDetail_description(detail_description);
                        String deal_image_url = jobj.getString("deal_image_url").trim();
                        dealEntity.setDeal_image_url(deal_image_url);
                        String title = jobj.getString("title").trim();
                        dealEntity.setTitle(title);
                        String average_rating = jobj.getString("average_rating").trim();
                        if (average_rating.equalsIgnoreCase("0")) {
                            dealEntity.setAverage_rating("");
                        } else {
                            dealEntity.setAverage_rating(average_rating);
                        }
                        String rating_count = jobj.getString("rating_count").trim();
                        dealEntity.setRating_count(rating_count);
                        String url = jobj.getString("url").trim();
                        dealEntity.setUrl(url);
                        String service_category_name = jobj.getString("service_category_name").trim();
                        dealEntity.setService_category_name(service_category_name);
                        String service_provider_name = jobj.getString("service_provider_name").trim();
                        dealEntity.setService_provider_name(service_provider_name);
                        if (service_category_name.equalsIgnoreCase(context.getResources().getString(R.string.internet))) {
                            String download_speed = jobj.getString("download_speed");
                            String upload_speed = getString(jobj, "upload_speed");//jobj.getString("upload_speed").trim();
                            dealEntity.setDownload_speed(download_speed);
                            dealEntity.setUpload_speed(upload_speed);
                            dealEntity.setShare_url(Constant.INTERNET_SHARE_URL);
                        } else if (service_category_name.equalsIgnoreCase(context.getResources().getString(R.string.cable))) {
                            String free_channels = jobj.getString("free_channels").trim();
                            dealEntity.setFree_channels(free_channels);
                            String premium_channels
                                    = getString(jobj, "premium_channels");//jobj.getString("premium_channels").trim();
                            dealEntity.setPremium_channels(premium_channels);
                            String free_channels_list
                                    = jobj.getString("free_channels_list").trim();
                            dealEntity.setFree_channels_list(free_channels_list);
                            String premium_channels_list
                                    = getString(jobj, "premium_channels_list");//jobj.getString("premium_channels_list").trim();
                            dealEntity.setPremium_channels_list(premium_channels_list);
                            dealEntity.setShare_url(Constant.CABLE_SHARE_URL);
                        } else if (service_category_name.equalsIgnoreCase(context.getResources().getString(R.string.bundle))) {
                            String free_channels = getString(jobj, "free_channels");//jobj.getString("free_channels").trim();
                            String premium_channels
                                    = getString(jobj, "premium_channels");//jobj.getString("premium_channels").trim();
                            String domestic_call_minutes = getString(jobj, "domestic_call_minutes");//jobj.getString("domestic_call_minutes").trim();
                            String international_call_minutes = getString(jobj, "international_call_minutes");//jobj.getString("international_call_minutes").trim();
                            String download_speed = getString(jobj, "download_speed");//jobj.getString("download_speed").trim();
                            String upload_speed = getString(jobj, "upload_speed");//jobj.getString("upload_speed");
                            String free_channels_list
                                    = jobj.getString("free_channels_list").trim();
                            String premium_channels_list
                                    = getString(jobj, "premium_channels_list");//jobj.getString("premium_channels_list").trim();
                            dealEntity.setDomestic_call_minutes(domestic_call_minutes);
                            dealEntity.setInternational_call_minutes(international_call_minutes);
                            dealEntity.setFree_channels(free_channels);
                            dealEntity.setPremium_channels(premium_channels);
                            dealEntity.setFree_channels_list(free_channels_list);
                            dealEntity.setPremium_channels_list(premium_channels_list);
                            dealEntity.setDownload_speed(download_speed);
                            dealEntity.setUpload_speed(upload_speed);
                            dealEntity.setShare_url(Constant.BUNDLE_SHARE_URL);

                        } else if (service_category_name.equalsIgnoreCase(context.getResources().getString(R.string.telephone)) || service_category_name.equalsIgnoreCase(context.getResources().getString(R.string.cell_phone))) {
                            if (service_category_name.equalsIgnoreCase(context.getResources().getString(R.string.telephone))) {
                                String countries = jobj.getString("countries").trim();
                                String features = jobj.getString("features").trim();
                                dealEntity.setCountries(countries);
                                dealEntity.setFeatures(features);
                                dealEntity.setShare_url(Constant.TELEPHONE_SHARE_URL);
                            } else {
                                String noofline = jobj.getString("no_of_lines").trim();
                                dealEntity.setNo_of_lines(noofline);
                                dealEntity.setShare_url(Constant.CELLPHONE_SHARE_URL);
                            }

                            String domestic_call_minutes = jobj.getString("domestic_call_minutes").trim();
                            String international_call_minutes = jobj.getString("international_call_minutes").trim();
                            dealEntity.setDomestic_call_minutes(domestic_call_minutes);
                            dealEntity.setInternational_call_minutes(international_call_minutes);
                        }
                        JSONArray deal_additional_offers = null, deal_equipments = null;
                        dealAdditionalOffersEntities = new ArrayList<>();
                        if (!jobj.isNull("deal_additional_offers")) {
                            deal_additional_offers = jobj.getJSONArray("deal_additional_offers");
                            if (deal_additional_offers != null) {
                                for (int j = 0; deal_additional_offers.length() > j; j++) {
                                    DealListItem.DealEntity.DealAdditionalOffersEntity dealAdditionalOffersEntity = new DealListItem.DealEntity.DealAdditionalOffersEntity();
                                    JSONObject jsonObject1 = deal_additional_offers.getJSONObject(j);
                                    String id_dealAdditionalOffers = jsonObject1.getString("id");
                                    dealAdditionalOffersEntity.setId(id_dealAdditionalOffers);
                                    String deal_id_dealAdditionalOffers = jsonObject1.getString("deal_id");
                                    dealAdditionalOffersEntity.setDeal_id(deal_id_dealAdditionalOffers);
                                    String title_dealAdditionalOffers = jsonObject1.getString("title");
                                    dealAdditionalOffersEntity.setTitle(title_dealAdditionalOffers);
                                    String description = jsonObject1.getString("description");
                                    dealAdditionalOffersEntity.setDescription(description);
                                    String price = jsonObject1.getString("price");
                                    dealAdditionalOffersEntity.setPrice(price);
                                    String start_date_dealAdditionalOffers = jsonObject1.getString("start_date");
                                    dealAdditionalOffersEntity.setStart_date(start_date_dealAdditionalOffers);
                                    String end_date_dealAdditionalOffers = jsonObject1.getString("end_date");
                                    dealAdditionalOffersEntity.setEnd_date(end_date_dealAdditionalOffers);
                                    String is_nationwide = jsonObject1.getString("is_nationwide");
                                    dealAdditionalOffersEntity.setIs_nationwide(is_nationwide);
                                    String is_active = jsonObject1.getString("is_active");
                                    dealAdditionalOffersEntity.setIs_active(is_active);
                                    String created_at = jsonObject1.getString("created_at");
                                    dealAdditionalOffersEntity.setCreated_at(created_at);
                                    String updated_at = jsonObject1.getString("updated_at");
                                    dealAdditionalOffersEntity.setUpdated_at(updated_at);
                                    // String isCustomisable =jsonObject1.getString("is_customisable");
                                    dealAdditionalOffersEntities.add(dealAdditionalOffersEntity);


                                }
                            }
                        }
                        dealEntity.setDeal_additional_offers(dealAdditionalOffersEntities);
                        equipmentsEntities = new ArrayList<>();
                        if (!jobj.isNull("deal_equipments")) {
                            deal_equipments = jobj.getJSONArray("deal_equipments");
                            if (deal_equipments != null) {
                                for (int j = 0; deal_equipments.length() > j; j++) {
                                    DealListItem.DealEntity.DealEquipmentsEntity dealEquipmentsEntity = new DealListItem.DealEntity.DealEquipmentsEntity();
                                    JSONObject jsonObject1 = deal_equipments.getJSONObject(j);
                                    String id_dealAdditionalOffers = jsonObject1.getString("id");
                                    dealEquipmentsEntity.setId(id_dealAdditionalOffers);

                                    // dealEquipmentsEntity.setCellphone_deal_attribute_id(cellphone_deal_attribute_id);
                                    if (jsonObject1.has("model")) {
                                        String memory = jsonObject1.getString("memory");
                                        dealEquipmentsEntity.setMemory(memory);
                                        String model = jsonObject1.getString("model");
                                        dealEquipmentsEntity.setModel(model);
                                    } else {
                                        String name = jsonObject1.getString("name");
                                        dealEquipmentsEntity.setModel(name);
                                    }
                                    String make = jsonObject1.getString("make");
                                    dealEquipmentsEntity.setMake(make);
                                    String price = jsonObject1.getString("price");
                                    dealEquipmentsEntity.setPrice(price);

                                    String installation = jsonObject1.getString("installation");
                                    dealEquipmentsEntity.setInstallation(installation);
                                    String activation = jsonObject1.getString("activation");
                                    dealEquipmentsEntity.setActivation(activation);
                                    String offer = jsonObject1.getString("offer");
                                    dealEquipmentsEntity.setOffer(offer);
                                    String is_active = jsonObject1.getString("is_active");
                                    dealEquipmentsEntity.setIs_active(is_active);


                                    String updated_at = jsonObject1.getString("updated_at");
                                    dealEquipmentsEntity.setUpdated_at(updated_at);
                                    String created_at = jsonObject1.getString("created_at");
                                    dealEquipmentsEntity.setCreated_at(created_at);
                                    equipmentsEntities.add(dealEquipmentsEntity);
                                }
                            }
                        }
                        dealEntity.setDeal_equipments(equipmentsEntities);

                        dealEntities.add(dealEntity);
                        compare_boolean.add(false);


                    }
                    dealListItem.setDeal(dealEntities);
                   /* try {
                        if (servicename.equalsIgnoreCase("1") && Internet_deal_fragment.Best_Deal_Flag) {
                            Collections.sort(deals, new Comparator<Map<String, String>>() {
                                public int compare(final Map<String, String> o1, final Map<String, String> o2) {
                                    return Integer.parseInt(o1.get("download_speed").toString()) - (Integer.parseInt(o2.get("download_speed").toString()));
                                }
                            });


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                }

                if (jsonArrayBundle != null) {
                    for (int i = 0; i < jsonArrayBundle.length(); i++) {
                        JSONObject jobj = jsonArrayBundle.getJSONObject(i);
                        DealListItem.BundleDealsEntity bundleDealsEntity = new DealListItem.BundleDealsEntity();
                        String id = jobj.getString("id");
                        bundleDealsEntity.setId(id);
                        String contract_period = jobj.getString("contract_period");
                        bundleDealsEntity.setContract_period(contract_period);
                        String service_category_id = jobj.getString("service_category_id").trim();
                        bundleDealsEntity.setService_category_id(service_category_id);
                        String short_description = jobj.getString("short_description").trim();
                        bundleDealsEntity.setShort_description(short_description);
                        String effective_price = jobj.getString("effective_price").trim();
                        Boolean is_order_available = jobj.getBoolean("is_order_available");
                        bundleDealsEntity.setIs_order_available(is_order_available);
                        bundleDealsEntity.setEffective_price(effective_price);
                        String deal_price = jobj.getString("deal_price").trim();
                        bundleDealsEntity.setDeal_price(deal_price);
                        String start_date = jobj.getString("start_date").trim();
                        bundleDealsEntity.setStart_date(start_date);
                        String end_date = jobj.getString("end_date").trim();
                        bundleDealsEntity.setEnd_date(end_date);
                        String detail_description = jobj.getString("detail_description").trim();
                        bundleDealsEntity.setDetail_description(detail_description);
                        String deal_image_url = jobj.getString("deal_image_url").trim();
                        bundleDealsEntity.setDeal_image_url(deal_image_url);
                        String title = jobj.getString("title").trim();
                        bundleDealsEntity.setTitle(title);
                        String average_rating = jobj.getString("average_rating").trim();
                        if (average_rating.equalsIgnoreCase("0")) {
                            bundleDealsEntity.setAverage_rating("");
                        } else {
                            bundleDealsEntity.setAverage_rating(average_rating);
                        }
                        String rating_count = jobj.getString("rating_count").trim();
                        bundleDealsEntity.setRating_count(rating_count);
                        String url = jobj.getString("url").trim();
                        bundleDealsEntity.setUrl(url);
                        String bundle_combo = jobj.getString("bundle_combo").trim();
                        bundleDealsEntity.setBundle_combo(bundle_combo);
                        String service_category_name = jobj.getString("service_category_name").trim();
                        bundleDealsEntity.setService_category_name(service_category_name);
                        String service_provider_name = jobj.getString("service_provider_name").trim();
                        bundleDealsEntity.setService_provider_name(service_provider_name);

                        bundleDealsEntities.add(bundleDealsEntity);
                        //  compare_boolean.add(false);


                    }
                    dealListItem.setBundle_deals(bundleDealsEntities);
                   /* try {
                        if (servicename.equalsIgnoreCase("1") && Internet_deal_fragment.Best_Deal_Flag) {
                            Collections.sort(deals, new Comparator<Map<String, String>>() {
                                public int compare(final Map<String, String> o1, final Map<String, String> o2) {
                                    return Integer.parseInt(o1.get("download_speed").toString()) - (Integer.parseInt(o2.get("download_speed").toString()));
                                }
                            });


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }*/

                }
            } else {
                Utilities.logToFile("Referral_Code", response);
            }

        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("Referral_Code", response + "Crash================================\n\n" + e);

            e.printStackTrace();
        }
        return dealListItem;

    }


    public static ArrayList<HashMap<String, String>> get_comment(final String dealid, final String app_userid,
                                                                 final Context context) {
        ArrayList<HashMap<String, String>> comment = new ArrayList<HashMap<String, String>>();

        try {
            if (!app_userid.equalsIgnoreCase("")) {
                response = CustomHttpClient.executeHttpGet(main_url + Url.comment_url + "deal_id=" + dealid + "&app_user_id=" + app_userid + getDeviceAndToken(context));
                JSONObject jsonobj = new JSONObject(response);
                Utilities.logToFile("Comment", response);

                if (response != "" || response != null) {

                    Success = jsonobj.getString("success");


                    if (Success.equalsIgnoreCase("true")) {
                        comment_count = jsonobj.getString("comment_count");
                        average_rating = jsonobj.getString("average_rating");
                        if (jsonobj.isNull("user_comment")) {
                        } else {
                            JSONObject jobj_comment = jsonobj.getJSONObject("user_comment");
                            String app_user_id_user = jobj_comment.getString("app_user_id");
                            String deal_id_user = jobj_comment.getString("deal_id");
                            String rating_point_user = jobj_comment.getString("rating_point");
                            String comment_text_user = jobj_comment.getString("comment_text");
                            String comment_title_user = jobj_comment.getString("comment_title");
                            String app_user_name_user = jobj_comment.getString("app_user_name");
                            String app_user_image_url_user = jobj_comment.getString("app_user_image_url");

                            String comment_date_url_user = jobj_comment.getString("comment_date");
                            HashMap<String, String> profile_detail_user = new HashMap<String, String>();

                            // profile_detail_user.put("comment_count", comment_count);
                            profile_detail_user.put("comment_date", comment_date_url_user);
                            //  profile_detail_user.put("average_rating", average_rating);
                            profile_detail_user.put("comment_title", comment_title_user);
                            profile_detail_user.put("app_user_name", decryptMsg(app_user_name_user));
                            profile_detail_user.put("comment_text", comment_text_user);
                            profile_detail_user.put("app_user_id", app_user_id_user);
                            profile_detail_user.put("deal_id", deal_id_user);
                            profile_detail_user.put("rating_point", rating_point_user);
                            profile_detail_user.put("app_user_image_url", app_user_image_url_user);


                            comment.add(profile_detail_user);
                        }
                        JSONArray json = jsonobj.getJSONArray("comment");
                        //
                        if (json != null) {

                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jobj = json.getJSONObject(i);
                                String app_user_id = jobj.getString("app_user_id");
                                String deal_id = jobj.getString("deal_id");
                                String rating_point = jobj.getString("rating_point");
                                String comment_title = jobj.getString("comment_title");

                                String comment_date = jobj.getString("comment_date");
                                String app_user_name = jobj.getString("app_user_name");
                                String app_user_image_url = jobj.getString("app_user_image_url");
                                String comment_text = jobj.getString("comment_text");
                                HashMap<String, String> profile_detail = new HashMap<String, String>();
                                profile_detail.put("comment_date", comment_date);
                                profile_detail.put("comment_text", comment_text);
                                profile_detail.put("comment_title", comment_title);
                                profile_detail.put("app_user_name", decryptMsg(app_user_name));
                                profile_detail.put("app_user_id", app_user_id);
                                profile_detail.put("deal_id", deal_id);
                                profile_detail.put("rating_point", rating_point);
                                profile_detail.put("app_user_image_url", app_user_image_url);


                                comment.add(profile_detail);
                            }
                        }
                    }


                }

            } else {
                response = CustomHttpClient.executeHttpGet(main_url + Url.comment_url + "deal_id=" + dealid + getDeviceAndToken(context));
                JSONObject jsonobj = new JSONObject(response);

                Utilities.logToFile("Comment", response);


                if (response != "" || response != null) {

                    Success = jsonobj.getString("success");
                    if (Success.equalsIgnoreCase("true")) {
                        comment_count = jsonobj.getString("comment_count");
                        average_rating = jsonobj.getString("average_rating");
                        JSONArray json = jsonobj.getJSONArray("comment");
                        //
                        if (json != null) {

                            for (int i = 0; i < json.length(); i++) {

                                JSONObject jobj = json.getJSONObject(i);
                                String app_user_id = jobj.getString("app_user_id");
                                String deal_id = jobj.getString("deal_id");
                                String rating_point = jobj.getString("rating_point");
                                String comment_text = jobj.getString("comment_text");
                                String app_user_name = jobj.getString("app_user_name");
                                String app_user_image_url = jobj.getString("app_user_image_url");
                                String comment_title = jobj.getString("comment_title");

                                String comment_date = jobj.getString("comment_date");
                                HashMap<String, String> profile_detail = new HashMap<String, String>();
                                //  profile_detail.put("comment_count", comment_count);
                                profile_detail.put("comment_text", comment_text);
                                profile_detail.put("app_user_name", decryptMsg(app_user_name));
                                profile_detail.put("comment_title", comment_title);
                                profile_detail.put("comment_date", comment_date);
                                // profile_detail.put("average_rating", average_rating);
                                profile_detail.put("app_user_id", app_user_id);
                                profile_detail.put("deal_id", deal_id);
                                profile_detail.put("rating_point", rating_point);
                                profile_detail.put("app_user_image_url", app_user_image_url);


                                comment.add(profile_detail);
                            }
                        }
                    }

                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return comment;

    }

    private static String getString(JSONObject jsonObject, String key) {
        try {
            if (jsonObject.isNull(key))
                return "";
            else
                return jsonObject.getString(key);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Confirmationorder send_order_residence(String user_id, String name, String email, String billing_address1, String billing_address2,
                                                         String billing_country, String billing_zipcode, String shiping_address1, String shiping_address2,
                                                         String shiping_country, String shiping_zipcode, String mobile_no, String deal_id, String deal_price, String effective_price,
                                                         String lastname,
                                                         String primary_id, String secondary_id, String is_shipping_address_same,
                                                         String service_address1, String service_address2, String service_city, String service_zipcode,String freetext,
                                                         String is_service_address_same, String primary_id_number, String secondary_id_number, String billingState,
                                                         String serviceState, String shipingState, String billingaddressname, String shipingaddressname, String serviceaddressname, String equipementprice, String equipementcolorname, String equipementCellphoneDetailId,
                                                         String planprice, String planTitle, String planid, String ServiceId, String ServiceName, String ServicePrice, String mChannelpremiumprice, String mChalnePremiumID, String mChalneEquipmentID, String ChalneEquipmentPrice, String TvAdapterPrice, String TvAdapterId,
                                                         int providerid, Context context) {
        Confirmationorder confirmationorder = null;
        try {

            JSONObject jsonObject = new JSONObject();
            JSONObject jsonObjectOrder = new JSONObject();
            jsonObjectOrder.put("app_user_id", user_id);
            jsonObjectOrder.put("status", "new_order");
            jsonObjectOrder.put("order_type", "1");
            jsonObjectOrder.put("primary_id", primary_id);
            jsonObjectOrder.put("secondary_id", secondary_id);
            jsonObjectOrder.put("primary_id_number", primary_id_number);
            jsonObjectOrder.put("secondary_id_number", secondary_id_number);
            jsonObjectOrder.put("free_text", freetext);
            jsonObject.put("order", jsonObjectOrder);

            JSONArray jsonObjectOrederitems = new JSONArray();
            JSONObject jsonObjectOrderitems = new JSONObject();
            jsonObjectOrderitems.put("deal_id", deal_id);
            jsonObjectOrderitems.put("deal_price", deal_price);
            jsonObjectOrderitems.put("effective_price", effective_price);
            jsonObjectOrderitems.put("status", "new_order");

            jsonObjectOrederitems.put(0, jsonObjectOrderitems);
            jsonObject.put("order_items", jsonObjectOrederitems);

            JSONObject jsonObjectAppUser = new JSONObject();
            jsonObjectAppUser.put("first_name", encryptMsg(name));
            jsonObjectAppUser.put("last_name", encryptMsg(lastname));
            jsonObjectAppUser.put("is_service_address_same", is_service_address_same);
            jsonObjectAppUser.put("is_shipping_address_same", is_shipping_address_same);
            jsonObjectAppUser.put("email", email);
            jsonObjectAppUser.put("user_type", "residence");
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            jsonObjectAppUser.put("zip", encryptMsg(sp.getString(Constant.ZIPCODE, "")));
            jsonObjectAppUser.put("mobile", encryptMsg(mobile_no));
            jsonObject.put("app_user", jsonObjectAppUser);


            JSONArray jsonObjectOrederequipmentsarray = new JSONArray();

            if (providerid == 4) {

                if (PlaceOrderAndPay.custemCellphoneEquipmentsAdditionalOffers.getDevicelist() != null) {
                    for (int i = 0; PlaceOrderAndPay.custemCellphoneEquipmentsAdditionalOffers.getDevicelist().size() > i; i++) {
                        JSONObject jsonObjectOrderequipments = new JSONObject();
                        jsonObjectOrderequipments.put("equipment_price", PlaceOrderAndPay.custemCellphoneEquipmentsAdditionalOffers.getDevicelist().get(i).getPhoneprice());
                        jsonObjectOrderequipments.put("equipment_id", equipementCellphoneDetailId);/*PlaceOrderAndPay.custemCellphoneEquipmentsAdditionalOffers.getDevicelist().get(i).getPhoneid()*/
                        jsonObjectOrderequipments.put("color", PlaceOrderAndPay.custemCellphoneEquipmentsAdditionalOffers.getDevicelist().get(i).getPhonecolor());
                        jsonObjectOrederequipmentsarray.put(i, jsonObjectOrderequipments);
                    }
                    if (jsonObjectOrederequipmentsarray.length() > 0) {
                        jsonObject.put("order_equipments", jsonObjectOrederequipmentsarray);
                    }
                }


                JSONArray jsonObjectOrederattributes = new JSONArray();
                JSONObject jsonObjectOrderattributes = new JSONObject();
                jsonObjectOrderattributes.put("ref_id", planid);
                jsonObjectOrderattributes.put("ref_type", "cellphone");
                jsonObjectOrderattributes.put("price", planprice);

                jsonObjectOrederattributes.put(0, jsonObjectOrderattributes);
                if (!planprice.equalsIgnoreCase("0.0")) {
                    jsonObject.put("order_attributes", jsonObjectOrederattributes);
                }
                JSONArray jsonObjectOrederextraservices = new JSONArray();
                JSONObject jsonObjectOrderextraservices = new JSONObject();
                jsonObjectOrderextraservices.put("deal_extra_service_id", ServiceId);
                jsonObjectOrderextraservices.put("service_name", ServiceName);
                jsonObjectOrderextraservices.put("price", ServicePrice);

                jsonObjectOrederextraservices.put(0, jsonObjectOrderextraservices);
                if (!ServicePrice.equalsIgnoreCase("0.0")) {
                    jsonObject.put("order_extra_services", jsonObjectOrederextraservices);
                }
            }

            if (providerid == 3) {
            /*cable Hash start*/

                JSONArray jsonObjectOrederCableequipmentsarray = new JSONArray();
                JSONObject jsonObjectOrderCableequipments = new JSONObject();
                jsonObjectOrderCableequipments.put("equipment_id",mChalneEquipmentID );
                jsonObjectOrderCableequipments.put("equipment_price", ChalneEquipmentPrice);

                if (!ChalneEquipmentPrice.equalsIgnoreCase("")) {
                    jsonObjectOrederCableequipmentsarray.put(jsonObjectOrderCableequipments);
                    jsonObject.put("order_equipments", jsonObjectOrederCableequipmentsarray);
                }
                JSONArray jsonObjectOrederCableattributes = new JSONArray();
                JSONObject jsonObjectOrderCableextraservices = new JSONObject();
                jsonObjectOrderCableextraservices.put("ref_id", TvAdapterId);
                jsonObjectOrderCableextraservices.put("ref_type", "cable");
                jsonObjectOrderCableextraservices.put("price", TvAdapterPrice);
                JSONObject jsonObjectOrderCablChanextraservice = new JSONObject();
                jsonObjectOrderCablChanextraservice.put("ref_id", mChalnePremiumID);
                jsonObjectOrderCablChanextraservice.put("ref_type", "channel");
                jsonObjectOrderCablChanextraservice.put("price", mChannelpremiumprice);

                if (!TvAdapterPrice.equalsIgnoreCase("")) {
                    jsonObjectOrederCableattributes.put(jsonObjectOrderCableextraservices);
                }


                if (!mChannelpremiumprice.equalsIgnoreCase("")) {
                    jsonObjectOrederCableattributes.put(jsonObjectOrderCablChanextraservice);
                }
                if (!mChannelpremiumprice.equalsIgnoreCase("") || !TvAdapterPrice.equalsIgnoreCase("")) {
                    jsonObject.put("order_attributes", jsonObjectOrederCableattributes);
                }
            }
            /*cable Hash End*/

            JSONArray jsonArrayAddress = new JSONArray();
            JSONObject jsonObjectbillingAddress = new JSONObject();
            jsonObjectbillingAddress.put("address_type", 2);
            jsonObjectbillingAddress.put("address_name", billingaddressname);
            jsonObjectbillingAddress.put("zip", billing_zipcode);
            jsonObjectbillingAddress.put("state", billingState);
            jsonObjectbillingAddress.put("city", billing_country);
            jsonObjectbillingAddress.put("address1", billing_address1);
            jsonObjectbillingAddress.put("address2", billing_address2);
            jsonObjectbillingAddress.put("contact_number", mobile_no);

            JSONObject jsonObjectshipingAddress = new JSONObject();
            jsonObjectshipingAddress.put("address_type", 1);
            jsonObjectshipingAddress.put("address_name", shipingaddressname);
            jsonObjectshipingAddress.put("zip", shiping_zipcode);
            jsonObjectshipingAddress.put("city", shiping_country);
            jsonObjectshipingAddress.put("state", shipingState);
            jsonObjectshipingAddress.put("address1", shiping_address1);
            jsonObjectshipingAddress.put("address2", shiping_address2);
            jsonObjectshipingAddress.put("contact_number", mobile_no);

            JSONObject jsonObjectserviceAddress = new JSONObject();
            jsonObjectserviceAddress.put("address_type", 0);
            jsonObjectserviceAddress.put("address_name", serviceaddressname);
            jsonObjectserviceAddress.put("city", service_city);
            jsonObjectserviceAddress.put("zip", service_zipcode);
            jsonObjectserviceAddress.put("state", serviceState);
            jsonObjectserviceAddress.put("address1", service_address1);
            jsonObjectserviceAddress.put("address2", service_address2);
            jsonObjectserviceAddress.put("contact_number", mobile_no);

            jsonArrayAddress.put(0, jsonObjectbillingAddress);
            jsonArrayAddress.put(1, jsonObjectshipingAddress);
            jsonArrayAddress.put(2, jsonObjectserviceAddress);
            jsonObject.put("app_user_addresses", jsonArrayAddress);
            jsonObject.put("device_id", sp.getString("androidid", ""));
            jsonObject.put("token", sp.getString("api_token", ""));

            response = CustomHttpClient.executeHttpPostJsonObject(main_url + Url.order_url,
                    jsonObject.toString());


            if (response != "" || response != null) {
                JSONObject jobj = new JSONObject(response);
                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                confirmationorder = gson.fromJson(response.toString(), Confirmationorder.class);
                giftcardorder = gson.fromJson(response.toString(), Giftcardorder.class);
                Success = jobj.getString("success");
                if (!jobj.getBoolean("success")) {
                    Utilities.logToFile("Send_order_residence", response);
                }
            } else {
                Utilities.logToFile("Send_order_residence", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("Send_order_residence", response + "Crash================================\n\n" + e);

            e.printStackTrace();
        }
        return confirmationorder;

    }

    // ***********************************General Info*********************************************

    public static OrderDetail my_order_details(final String user_id, final String orderId, final Context context) {
        OrderDetail orderDetail = null;
        try {
            List<Pair<String, String>> param = new ArrayList<>();
            param.add(new Pair("app_user_id", user_id));
            param.add(new Pair("order_id", orderId));

            JSONObject jsonObjectOrder = new JSONObject();
            jsonObjectOrder.put("app_user_id", user_id);
            jsonObjectOrder.put("order_id", orderId);
            SharedPreferences sp = context.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            jsonObjectOrder.put("device_id", sp.getString("androidid", ""));
            jsonObjectOrder.put("token", sp.getString("api_token", ""));
            response = CustomHttpClient.executeHttpPostJsonObject(main_url + Url.my_order_details,
                    jsonObjectOrder.toString());


            if (response != "" || response != null) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
//                Gson gson = gsonBuilder.create();
                Gson gson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                // called fromJson() method and passed incoming buffer from json
                // file
                orderDetail = gson.fromJson(response.toString(), OrderDetail.class);
              /*  JSONObject myOrderDetailResponse = new JSONObject(response);
                boolean success = myOrderDetailResponse.getBoolean("success");
                if (success) {
                    orderDetail = new OrderDetail();
                    orderDetail.setSuccess(success);
                    JSONObject orderJson = myOrderDetailResponse.getJSONObject("order");
                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                    Gson orderGson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                    Order order = orderGson.fromJson(orderJson.toString(), Order.class);
                    orderDetail.setOrder(order);

                    JSONArray order_addressesJsonArray = myOrderDetailResponse.getJSONArray("order_addresses");
                    ArrayList<com.spa.model.OrderAddress> orderAddressArrayList = new ArrayList<>();
                    for (int i = 0; i < order_addressesJsonArray.length(); i++) {
                        JSONObject order_addressesJson = (JSONObject) order_addressesJsonArray.get(i);
//                      GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson orderAddressGson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                        OrderAddress orderAddress = orderAddressGson.fromJson(order_addressesJson.toString(), OrderAddress.class);
                        orderAddressArrayList.add(orderAddress);

                    }
                    orderDetail.setOrderAddresses(orderAddressArrayList);

                    JSONObject appUserJson = myOrderDetailResponse.getJSONObject("app_user");
//                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                    Gson appUserGson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                    AppUser appUser = appUserGson.fromJson(appUserJson.toString(), AppUser.class);
                    orderDetail.setAppUser(appUser);

                    JSONArray orderItemsJsonArray = myOrderDetailResponse.getJSONArray("order_items");
                    ArrayList<com.spa.model.OrderItem> orderItemArrayList = new ArrayList<>();
                    for (int i = 0; i < orderItemsJsonArray.length(); i++) {
                        JSONObject orderItemJson = (JSONObject) orderItemsJsonArray.get(i);
//                    double dealPrice=orderItemsJson.getDouble("deal_price");
//                    double effectivePrice=orderItemsJson.getDouble("effective_price");
//                    JSONObject dealJson=orderItemsJson.getJSONObject("deal");

//                    GsonBuilder gsonBuilder = new GsonBuilder();
                        gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                        Gson orderItemsGson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                        OrderItem orderItem = orderItemsGson.fromJson(orderItemJson.toString(), OrderItem.class);
                        orderItemArrayList.add(orderItem);
                    }
                    orderDetail.setOrderItems(orderItemArrayList);

                    JSONObject businessJson = myOrderDetailResponse.getJSONObject("business");
//                    GsonBuilder gsonBuilder = new GsonBuilder();
                    gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                    Gson businessGson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                    Business business = businessGson.fromJson(businessJson.toString(), Business.class);
                    orderDetail.setBusiness(business);
                }*/


                Utilities.logToFile("OrderDetail", response);
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("OrderDetail", response + "Crash================================\n\n" + e);

            e.printStackTrace();
        }
        return orderDetail;

    }

    public static Boolean EamilVerication(String userid, Context context) {
        boolean emailverifcation = false;
        try {
//            response = CustomHttpClient.executeHttpGet(main_url + Url.emailverification + "id=" + userid);
            emailverifcation=true;
//            if (response != null && response != "") {
//                JSONObject jobj = new JSONObject(response);
//                emailverifcation = jobj.getBoolean("verified");
//                if (!jobj.getBoolean("verified")) {
//                    Utilities.logToFile("EmaiVerification", response);
//                }
//            } else {
//                Utilities.logToFile("EmaiVerification", response);
//                Success = "";
//            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("EmaiVerification", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return emailverifcation;

    }


    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {

            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringAdapter();
        }
    }

    public static class StringAdapter extends TypeAdapter<String> {
        public String read(JsonReader reader) throws IOException {
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        public void write(JsonWriter writer, String value) throws IOException {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }

    public static String CalculateBandwidth(int email, int web, int video, int images, String device, int video_streming, int audio, FragmentActivity activity) {
        try {
            JSONObject jsonObjectOrder = new JSONObject();
            jsonObjectOrder.put("email", email);
            jsonObjectOrder.put("web_page", web);
            jsonObjectOrder.put("video_calling", video);
            jsonObjectOrder.put("audio_calling", audio);
            jsonObjectOrder.put("photo_upload_download", images);
            jsonObjectOrder.put("video_streaming", video_streming);
            jsonObjectOrder.put("no_of_devices", device);
            SharedPreferences sp = activity.getSharedPreferences(Constant.SHARED_PREF,
                    Activity.MODE_WORLD_READABLE);
            jsonObjectOrder.put("device_id", sp.getString("androidid", ""));
            jsonObjectOrder.put("token", sp.getString("api_token", ""));
            response = CustomHttpClient.executeHttpPostJsonObject(main_url + Url.calculate_bandwidth,
                    jsonObjectOrder.toString());

            if (response != "" || response != null) {
                JSONObject jobj = new JSONObject(response);
                Success = "Bandwidth in MB =" + jobj.getString("bandwidth_in_mb") + "\n\n" + "Bandwidth in GB =" + jobj.getString("bandwidth_in_gb");
                // bandwidth_in_mb
            } else {
                Utilities.logToFile("CalculateBandwidth", response);
                Success = "";
            }
        } catch (Exception e) {
            Success = "";
            Utilities.logToFile("CalculateBandwidth", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return Success;
    }

    public static ChannelList getChannelList(final String dealId,
                                             final Context context) {
        ChannelList channelList = null;
        try {
            response = CustomHttpClient.executeHttpGet(main_url + Url.channelList + "deal_id=" + dealId + getDeviceAndToken(context));
            if (response != "" || response != null) {

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.setDateFormat("M/d/yy hh:mm a");
                Gson businessGson = gsonBuilder.registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                channelList = businessGson.fromJson(response.toString(), ChannelList.class);
                Utilities.logToFile("ChannelList", response);
            }

        } catch (Exception e) {
            Utilities.logToFile("ChannelList", response + "Crash================================\n\n" + e);
            e.printStackTrace();
        }
        return channelList;

    }
}
