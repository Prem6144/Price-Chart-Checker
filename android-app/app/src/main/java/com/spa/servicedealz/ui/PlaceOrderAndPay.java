package com.spa.servicedealz.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.localytics.android.Localytics;
import com.spa.adapter.AdditionalOfferAdapter;
import com.spa.adapter.EquipmentsAdapter;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.confirmationorder.Confirmationorder;
import com.spa.model.equipments.CellphoneEquipments;
import com.spa.model.placeorder.CustemCellphoneEquipmentsAdditionalOffers;
import com.spa.model.placeorder.business.BusinessUserPlaceOrder;
import com.spa.model.placeorder.residence.ResidenceUserPlaceOrder;
import com.spa.model.state.PrimaryAndSecondaryId;
import com.spa.model.state.State;
import com.spa.servicedealz.R;
import com.spa.utils.Constant;
import com.spa.utils.CustomizeDialog;
import com.spa.utils.Jsondata;
import com.spa.utils.PaddingItemDecoration;
import com.spa.utils.Validation;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.spa.servicedealz.R.id.chk_shipin_address;
import static com.spa.utils.MenuItemGlobal.setCapitalizeTextWatcher;

/**
 * Created by Diwakar on 6/2/2016.
 */
public class PlaceOrderAndPay extends AppCompatActivity implements View.OnClickListener {
    public static int Year, Month, Day, position, count, providerid = 0;
    static final int DATE_DIALOG_ID = 0;
    PaddingItemDecoration addItemDecoration;
    private Spinner mSpinnerPrimary, mSpinnerSecondary, mSpinnerShipingState, mSpinnerServiceState, mSpinnerBillingState, mSpinnerPrimaryState, mSpinnerSecondaryState, mSpinnerEquiments;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    Boolean yourBool;
    private LinearLayout mLinearLayoutOrderReview, mLinearLayoutPersonalInformation, mLinearLayoutAddressInformation, mLinearLayoutllBuisnessInformation, mLinearLayoutSole, mLinearLayoutREgisterd,
            mLinearLayoutShipingAddress, mLinearLayoutServiceAddress, mLinearLayoutllBuisness, mLinearLayoutPriamry, mLinearLayoutSecondary, mEquipementsOrderSumery, mEquipementsCableOrderSumery,
            mLinearCableMain, mLinearCablechooseYourCustom, mLinearPremiumChannel;
    RelativeLayout mLinearChannelEquipement;
    private TextView mTextViewPersonalNext, mTextViewAddressNext, mTextViewOrderProceed, mTextViewDealTitle, mTextViewPrice, mTextViewOrderName,
            mTextViewOrederMobileEmail, mTextViewOrderAddress, mTextViewDealDesc, mTextViewContractPeroid, mTextViewEffectivePrice,
            mTextViewBusinessNext, mTextViewShippingAddress, mTextViewBillingAddress, mTextViewBusinessTypeDetail, mTextViewBusinessType, mTextViewServiceAddress, mAttributesName, mAttributesPrice, mPlanName, mPlanPrice, mTextCableTVChanelName, mTextCableTVChanelPrice, mTextCableChanelChanelName, mTextCableChanelChanePrice, mTextCableEquipementName, mTextCableEquipementPrice;
    private ImageView mImageViewPersonal, mImageViewAddress, mImageViewOrder, mImageViewDealImage, mImageViewBusinessInformation;
    private RatingBar mRatingBar;
    private EditText mEditTextFirstName, mEditTextEamil, mEditTextBillingAddress1, mEditTextBillingAddress2, mEditTextTownCity, mEditTextBillingZipcode,
            mEditTextShipingAddress1, mEditTextShipingAddress2, mEditTextShipingTown, mEditTextShipingZipcode, mEditTextMobileNo,
            mEditTextBusinessName, mEditTextDBNumber, mEditTextFederalNumber, mEditTextSSN, mEditTextBuisnessMobileNumber, mEditTextManagerName,
            mEditTextMiddleName, mEditTextLastName, mEditTextSecondaryId, mEditTextPrimaryId, mEditTextBusinessDobNumber, mEditTextDBANumber,
            mEditTextServiceAddress1, mEditTextServiceAddress2, mEditTextBillingAddressName, mEditTextShipingAddressName, mEditTextServiceAddressName,
            mEditTextServiceCity, mEditTextServiceZipCode, mFreeTextEdit;
    private CheckBox mCheckBoxShiping, mCheckBoxService;
    private RadioButton mRadioButtonSole, mRadioButtonRegistred;
    private RadioGroup mRadioGroup;

    private String name, email, billing_address1, billing_address2, billing_country, billing_zipcode, shiping_address1, buisness_mobile_number,
            shiping_address2, shiping_country, shiping_zipcode, mobile_no, bisiness_name, db_number, federal_number, ssn_number,
            buisness_type, managername, lastname, primary_id, secondary_id, DOB, dbumber, DBA, deal_id, billingaddressname, shipingaddressname, serviceaddressname,
            is_shipping_address_same, service_address1, service_address2, service_city, service_zipcode, is_service_address_same, primary_id_number, secondary_id_number, billingState, serviceState, shipingState,
            mEquipementCellPhoneDetailId, PlanReferPrice, PlanType, mPlanRefferId, ExtraServicePrice, extraserviceId, equipementId, EquimentsPrice, freetext;
    private Boolean isInternetPresent = false;
    private BusinessUserPlaceOrder buisnessUserPlaceOrder = null;
    private ResidenceUserPlaceOrder residenceUserPlaceOrder = null;
    int keyDel, i = 0;
    JSONArray jsonObjectOrederitems = new JSONArray();
    private Cursor phones;
    private JSONObject ContactjsonObject = new JSONObject();
    private Confirmationorder confirmationorder = null;
    private State state;
    public static CustemCellphoneEquipmentsAdditionalOffers custemCellphoneEquipmentsAdditionalOffers;
    private PrimaryAndSecondaryId primaryAndSecondaryId;
    private CellphoneEquipments cellphoneEquipments;
    String EffectivePrice;
    String EquipementColorName, mEquipementsColorList, mEquipementCellPhoneName, mEquipementBrandName, mEquipementDealId;
    int EquipementCellPhoneDetailId;
    int ExtraServiceId;
    String ExtraServiceName, PlanName,InvalidFederalMessage;
    int PlanRefferId;
    int PlanTitle;
    double mEffectiveValue;
    Boolean mCustomActivity = false;
    String mCustomCableActivity = "";
    ArrayList<HashMap<String, String>> mEquipemenmapList = new ArrayList<HashMap<String, String>>();
    EquipmentsAdapter mEquipmentsAdapter;
    String mChannelpremiumprice, ChalnePremiumName, ChalneEquipmentName, mChalnePremiumID, mChalneEquipmentID, ChalneEquipmentPrice, TvAdapterPrice, TvAdapterId, TvAdapterChannelName;
//    SharedPreferences sp = getSharedPreferences("PrefChalneEquipmentName_name",
//            Activity.MODE_WORLD_READABLE);
//    SharedPreferences.Editor editor = sp.edit();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_order_and_pay);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        mSharedPreferences = getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        mEditor = mSharedPreferences.edit();
        isInternetPresent = NetworkUtil.isConnectingToInternet(this);

        final Calendar calendar = Calendar.getInstance();
        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.after(Calendar.DATE);
        setActionBar();
        MapIngId();

//        mEquipmentsAdapter =new EquipmentsAdapter(PlaceOrderAndPay.this,mEquipemenmapList);
        mCustomActivity = getIntent().getBooleanExtra("CustomActivity", false);
        mCustomCableActivity = getIntent().getStringExtra("CableActivity");
        if (mCustomActivity) {
            mSpinnerEquiments.setVisibility(View.GONE);
            if (mCustomCableActivity.equalsIgnoreCase(getResources().getString(R.string.cable))) {
                providerid = 3;
                mEquipementsOrderSumery.setVisibility(View.GONE);
                GetCableData();
                SetData();

            } else if (mCustomCableActivity.equalsIgnoreCase(getResources().getString(R.string.cell_phone))) {
                providerid = 4;
                GetCellPhoneData();
                mEquipementsCableOrderSumery.setVisibility(View.GONE);
                setEquipementsData();

            }


//            GetEquipementCellPhoneData();
//            Toast.makeText(getApplicationContext(),""+EquipementColorName+""+EquipementCellPhoneDetailId+""+EquimentsPrice,Toast.LENGTH_SHORT).show();
        } else {
            mEquipementsCableOrderSumery.setVisibility(View.GONE);
            mEquipementsOrderSumery.setVisibility(View.GONE);
        }
        // setSpinnerData();
        mLinearLayoutREgisterd.setVisibility(View.GONE);
        mLinearLayoutSole.setVisibility(View.VISIBLE);
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (mRadioButtonSole.isChecked()) {
                    mLinearLayoutREgisterd.setVisibility(View.GONE);
                    mLinearLayoutSole.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayoutSole.setVisibility(View.GONE);
                    mLinearLayoutREgisterd.setVisibility(View.VISIBLE);

                }

            }
        });

     /*   mEditTextBusinessDobNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                mEditTextBusinessDobNumber.setOnKeyListener(new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {


                        if (keyCode == KeyEvent.KEYCODE_DEL)
                            keyDel = 1;
                        return false;
                    }
                });

                if (keyDel == 0) {
                    int len = mEditTextBusinessDobNumber.getText().length();
                    if (len == 2 || len == 5) {
                        mEditTextBusinessDobNumber.setText(mEditTextBusinessDobNumber.getText() + "/");
                        mEditTextBusinessDobNumber.setSelection(mEditTextBusinessDobNumber.getText().length());
                    }
                } else {
                    keyDel = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                // TODO Auto-generated method stub
            }
        });*/

        if (isInternetPresent) {
            deal_id = "" + getIntent().getIntExtra("dealid", 0);


            new HttpGetAsyncTaskGetStae().execute();
            if (!mSharedPreferences.getString("user_type", "").equalsIgnoreCase("residence")) {
                new HttpGetAsyncTaskGetBuisness().execute();
            } else {
                new HttpGetAsyncTaskGet().execute();
            }


        } else {
            ShowDailog.Show_Alert_Dailog(this);
        }

    }

    /*
          * Mehod to show alert dialog
          *
          * @parm activity
          * @parm message
          * @parm title
          * */
    String Show_Alert_Login(final Activity activity, String message, String title) {
        try {
            AlertDialog.Builder alertbox = null;

            TextView txt_share, txt_dontshare;
            ImageView imageViewcross;
            LayoutInflater layoutInflater = LayoutInflater.from(this);

            View promptView = layoutInflater.inflate(R.layout.sharecontact, null);
            alertbox = new AlertDialog.Builder(this, R.style.DialogAnimation);
            txt_dontshare = (TextView) promptView.findViewById(R.id.txt_dontshare);
            txt_share = (TextView) promptView.findViewById(R.id.txt_share);
            imageViewcross = (ImageView) promptView.findViewById(R.id.img_close);

            // set prompts.xml to be the layout file of the alertdialog builder
            alertbox.setView(promptView);
            final AlertDialog alertD = alertbox.create();
            imageViewcross.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertD.dismiss();
                    Intent myorder = new Intent(getApplicationContext(), GiftCardActivty.class);
                    myorder.putExtra("orderid", confirmationorder.getOrder().getId());
                    myorder.putExtra("userid", mSharedPreferences.getString(Constant.APP_USER_ID, ""));
                    startActivity(myorder);
                    finish();
                }
            });
            txt_dontshare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertD.dismiss();
                    Intent myorder = new Intent(getApplicationContext(), GiftCardActivty.class);
                    myorder.putExtra("orderid", confirmationorder.getOrder().getId());
                    myorder.putExtra("userid", mSharedPreferences.getString(Constant.APP_USER_ID, ""));
                    startActivity(myorder);
                    finish();
                }
            });
            txt_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    new LoadContact().execute();
                    alertD.dismiss();

                }
            });
            alertD.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            alertD.show();

         /*
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
            alertD.show();*/
        } catch (Exception e) {
        }
        return null;
    }

    public void getContactDataFromCursor() {
        // String id = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));
        String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
        String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                .replaceAll("[^0-9]", "");
        if (phoneNumber.length() >= 10) {
            phoneNumber = phoneNumber.substring(phoneNumber.length() - 10);
        }
        String EmailAddr = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA2));
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("app_user_id", mSharedPreferences.getString(Constant.APP_USER_ID, ""));
            jsonObject.put("email_id", EmailAddr);
            jsonObject.put("mobile_no", phoneNumber);
            jsonObject.put("name", name);

            jsonObjectOrederitems.put(i, jsonObject);
            i++;
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    // Get Contact in background
    class LoadContact extends AsyncTask<Void, Void, Void> {
        String respose = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PlaceOrderAndPay.this);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // Get ContactModel list from Phone
            phones = getContentResolver().
                    query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
            if (phones != null) {
                Log.e("count", "" + phones.getCount());

                while (phones.moveToNext()) {
                    getContactDataFromCursor();
                }
                try {
                    respose = Jsondata.send_contact(ContactjsonObject.put("referred_contacts", jsonObjectOrederitems).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("Cursor close 1", "----------------");
            }


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            phones.close();
            mEditor.putString("contact_send", "yes");
            mEditor.commit();
            Intent myorder = new Intent(getApplicationContext(), GiftCardActivty.class);
            myorder.putExtra("orderid", confirmationorder.getOrder().getId());
            myorder.putExtra("userid", mSharedPreferences.getString(Constant.APP_USER_ID, ""));
            startActivity(myorder);
            finish();

        }

    }

    /*Method to set  data in view Component*/
    private void setDataInViewComponent() {

        mEditTextMobileNo.setText(Jsondata.decryptMsg(residenceUserPlaceOrder.getAppUsers().getMobile()));
        mEditTextFirstName.setText(Jsondata.decryptMsg(residenceUserPlaceOrder.getAppUsers().getFirstName()));
        mEditTextEamil.setText(residenceUserPlaceOrder.getAppUsers().getEmail());
        mEditTextLastName.setText(Jsondata.decryptMsg(residenceUserPlaceOrder.getAppUsers().getLastName()));
        if (!residenceUserPlaceOrder.getAppUsers().getPrimary_id().equalsIgnoreCase("")) {
            int index = Arrays.asList(getResources().getStringArray(R.array.primary_id)).indexOf(residenceUserPlaceOrder.getAppUsers().getPrimary_id());
            mSpinnerPrimary.setSelection(index);
            int index1 = Arrays.asList(getResources().getStringArray(R.array.secondary_id)).indexOf(residenceUserPlaceOrder.getAppUsers().getSecondary_id());
            mSpinnerSecondary.setSelection(index1);
        }

        if (residenceUserPlaceOrder.getAppUsers().getPrimary_id_number().contains("===")) {
            try {
                String[] separated = residenceUserPlaceOrder.getAppUsers().getPrimary_id_number().split("===");
                mSpinnerPrimaryState.setSelection(state.getStates().indexOf(separated[0]));
                mEditTextPrimaryId.setText(separated[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            mEditTextPrimaryId.setText(residenceUserPlaceOrder.getAppUsers().getPrimary_id_number());
        }

        if (residenceUserPlaceOrder.getAppUsers().getSecondary_id_number().contains("===")) {
            try {
                String[] separated = residenceUserPlaceOrder.getAppUsers().getSecondary_id_number().split("===");
                mSpinnerSecondaryState.setSelection(state.getStates().indexOf(separated[0]));
                mEditTextSecondaryId.setText(separated[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            mEditTextSecondaryId.setText(residenceUserPlaceOrder.getAppUsers().getSecondary_id_number());
        }
        for (int i = 0; residenceUserPlaceOrder.getAppUserAddresses().size() > i; i++) {
            if (residenceUserPlaceOrder.getAppUserAddresses().get(i).getAddressType() == 2) {
                int index = state.getStates().indexOf(residenceUserPlaceOrder.getAppUserAddresses().get(i).getState());
                mSpinnerBillingState.setSelection(index);

                mEditTextBillingAddress1.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getAddress1());
                mEditTextBillingAddress2.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getAddress2());
                mEditTextBillingZipcode.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getZip());
                mEditTextTownCity.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getCity());
                mEditTextBillingAddressName.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getAddressName());
            } else if (residenceUserPlaceOrder.getAppUserAddresses().get(i).getAddressType() == 1) {
                int index = state.getStates().indexOf(residenceUserPlaceOrder.getAppUserAddresses().get(i).getState());
                mSpinnerShipingState.setSelection(index);
                mEditTextShipingZipcode.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getZip());
                mEditTextShipingAddress1.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getAddress1());
                mEditTextShipingAddress2.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getAddress2());
                mEditTextShipingTown.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getCity());
                mEditTextShipingAddressName.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getAddressName());
            } else if (residenceUserPlaceOrder.getAppUserAddresses().get(i).getAddressType() == 0) {
                int index = state.getStates().indexOf(residenceUserPlaceOrder.getAppUserAddresses().get(i).getState());
                mSpinnerServiceState.setSelection(index);
                mEditTextServiceZipCode.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getZip());
                mEditTextServiceAddress1.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getAddress1());
                mEditTextServiceAddress2.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getAddress2());
                mEditTextServiceCity.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getCity());
                mEditTextServiceAddressName.setText(residenceUserPlaceOrder.getAppUserAddresses().get(i).getAddressName());
            }

        }
        if (!Validation.isPasswordMatch(mEditTextBillingAddress1, mEditTextShipingAddress1)) {
            if (!mEditTextBillingAddress1.getText().toString().equalsIgnoreCase("")) {
                mCheckBoxShiping.setChecked(true);
                mLinearLayoutShipingAddress.setVisibility(View.GONE);
            }
        }
        if (!Validation.isPasswordMatch(mEditTextBillingAddress1, mEditTextServiceAddress1)) {
            if (!mEditTextBillingAddress1.getText().toString().equalsIgnoreCase("")) {
                mCheckBoxService.setChecked(true);
                mLinearLayoutServiceAddress.setVisibility(View.GONE);
            }
        }

        /*if (DealListFragment.dealListItem.getDeal().get(getIntent().getIntExtra("deal_position", 0))..toString().equalsIgnoreCase("")) {
            mLinearLayoutllBuisnessInformation.setVisibility(View.GONE);
        }*/


    }


    private void MapIngId() {
        mLinearLayoutPriamry = (LinearLayout) findViewById(R.id.ll_primaryid);
        mLinearLayoutSecondary = (LinearLayout) findViewById(R.id.ll_secondaryid);

        mSpinnerPrimaryState = (Spinner) findViewById(R.id.spn_primarystate);
        mSpinnerEquiments = (Spinner) findViewById(R.id.spinner_equipments);
        mSpinnerSecondaryState = (Spinner) findViewById(R.id.spn_secondary);
        mSpinnerServiceState = (Spinner) findViewById(R.id.spn_service_state);
        mSpinnerShipingState = (Spinner) findViewById(R.id.spn_shipping_state);
        mSpinnerBillingState = (Spinner) findViewById(R.id.spn_billing_state);
        mSpinnerSecondary = (Spinner) findViewById(R.id.spinner_seconday_id);
        mSpinnerPrimary = (Spinner) findViewById(R.id.spinner_primary_id);
        mLinearLayoutllBuisness = (LinearLayout) findViewById(R.id.ll_business_information);
        mLinearLayoutllBuisnessInformation = (LinearLayout) findViewById(R.id.ll_buisness_information);
        if (!mSharedPreferences.getString("user_type", "").equalsIgnoreCase("residence")) {
            mLinearLayoutllBuisness.setVisibility(View.VISIBLE);
        }


        mEditTextBillingAddressName = (EditText) findViewById(R.id.etx_billingaddressname);
        mEditTextShipingAddressName = (EditText) findViewById(R.id.etx_shippingaddressname);
        mEditTextServiceAddressName = (EditText) findViewById(R.id.etx_service_addressname);

        mEditTextMiddleName = (EditText) findViewById(R.id.etx_m_Name);
        mEditTextLastName = (EditText) findViewById(R.id.etx_l_Name);
        mEditTextPrimaryId = (EditText) findViewById(R.id.etx_primary_id);
        mEditTextSecondaryId = (EditText) findViewById(R.id.etx_secodery_id);
        // mEditTextZipcode = (EditText) findViewById(R.id.etx_zipcode);
        mEditTextBusinessDobNumber = (EditText) findViewById(R.id.etx_buisness_dob_number);
        mEditTextDBANumber = (EditText) findViewById(R.id.etx_dba_number);
        mEditTextServiceAddress1 = (EditText) findViewById(R.id.etx_service_address1);
        mEditTextServiceAddress2 = (EditText) findViewById(R.id.etx_service_address2);
        mEditTextServiceCity = (EditText) findViewById(R.id.etx_service_city);
        mEditTextServiceZipCode = (EditText) findViewById(R.id.etx_service_zipcode);
        mFreeTextEdit = (EditText) findViewById(R.id.free_edt_text);
        mCheckBoxService = (CheckBox) findViewById(R.id.chk_service_address);

        mLinearLayoutSole = (LinearLayout) findViewById(R.id.ll_sole_business);
        mLinearLayoutREgisterd = (LinearLayout) findViewById(R.id.ll_registred_business);
        mLinearLayoutShipingAddress = (LinearLayout) findViewById(R.id.ll_shiping_addresss);
        mLinearLayoutServiceAddress = (LinearLayout) findViewById(R.id.ll_service_address);

        mTextViewDealDesc = (TextView) findViewById(R.id.txt_deal_description);
        mTextViewContractPeroid = (TextView) findViewById(R.id.txt_expiry_date);
        mTextViewEffectivePrice = (TextView) findViewById(R.id.txt_effective_deal_price);
        mTextViewBusinessNext = (TextView) findViewById(R.id.txt_business_next);

        mImageViewBusinessInformation = (ImageView) findViewById(R.id.img_business);

        mLinearLayoutOrderReview = (LinearLayout) findViewById(R.id.ll_order_review);
        mLinearLayoutPersonalInformation = (LinearLayout) findViewById(R.id.ll_personal_information);
        mLinearLayoutAddressInformation = (LinearLayout) findViewById(R.id.ll_address_information);
        mTextViewPersonalNext = (TextView) findViewById(R.id.txt_personal_next);
        mTextViewAddressNext = (TextView) findViewById(R.id.txt_address_next);
        mTextViewOrderProceed = (TextView) findViewById(R.id.txt_proceed);
        mImageViewAddress = (ImageView) findViewById(R.id.img_address);
        mImageViewOrder = (ImageView) findViewById(R.id.img_order);
        mImageViewPersonal = (ImageView) findViewById(R.id.img_personal);
        mRadioButtonSole = (RadioButton) findViewById(R.id.radioGroupButton0);
        mRadioGroup = (RadioGroup) findViewById(R.id.rg);
        mRadioButtonRegistred = (RadioButton) findViewById(R.id.radioGroupButton1);
        mTextViewDealTitle = (TextView) findViewById(R.id.txt_ordertitle);
        mTextViewPrice = (TextView) findViewById(R.id.txt_order_price);
        mTextViewOrderName = (TextView) findViewById(R.id.txt_order_name);
        mTextViewOrederMobileEmail = (TextView) findViewById(R.id.txt_mobile_email);
        mTextViewOrderAddress = (TextView) findViewById(R.id.txt_order_address);

//        Equipements cellphone summery

        mEquipementsOrderSumery = (LinearLayout) findViewById(R.id.order_sumerylist);
        mEquipementsCableOrderSumery = (LinearLayout) findViewById(R.id.order_cablesumerylist);
        mAttributesName = (TextView) findViewById(R.id.dealattributes_planname);
        mAttributesPrice = (TextView) findViewById(R.id.dealattributes_price);
        mPlanName = (TextView) findViewById(R.id.dealsubscription_planname);
        mPlanPrice = (TextView) findViewById(R.id.dealsubscription_price);

        mTextViewServiceAddress = (TextView) findViewById(R.id.txt_service_address);
        mTextViewShippingAddress = (TextView) findViewById(R.id.txt_shipping_address);
        mTextViewBillingAddress = (TextView) findViewById(R.id.txt_billing_address);
        mTextViewBusinessTypeDetail = (TextView) findViewById(R.id.txt_business_type_detail);
        mTextViewBusinessType = (TextView) findViewById(R.id.txt_registredor_sole);
        mEditTextFirstName = (EditText) findViewById(R.id.etx_F_Name);
        mEditTextMobileNo = (EditText) findViewById(R.id.etx_mobile);
        mEditTextEamil = (EditText) findViewById(R.id.etx_email);
        // mEditTextZipcode = (EditText) findViewById(R.id.etx_zipcode);
        mEditTextBusinessName = (EditText) findViewById(R.id.etx_business_name);
        mEditTextDBNumber = (EditText) findViewById(R.id.etx_db_number);
        mEditTextFederalNumber = (EditText) findViewById(R.id.etx_federal_number);
        mEditTextSSN = (EditText) findViewById(R.id.etx_ssn_number);
        mEditTextManagerName = (EditText) findViewById(R.id.etx_manager_name);
        mEditTextBuisnessMobileNumber = (EditText) findViewById(R.id.etx_buisness_mobile_number);
        mEditTextBillingAddress1 = (EditText) findViewById(R.id.etx_billingaddress1);
        mEditTextBillingAddress2 = (EditText) findViewById(R.id.etx_billingaddress2);
        mEditTextTownCity = (EditText) findViewById(R.id.etx_billingcity);
        mEditTextBillingZipcode = (EditText) findViewById(R.id.etx_billingzipcode);
        mEditTextShipingAddress1 = (EditText) findViewById(R.id.etx_shippingaddress1);
        mEditTextShipingAddress2 = (EditText) findViewById(R.id.etx_shippingaddress2);
        mEditTextShipingTown = (EditText) findViewById(R.id.etx_shippingcity);
        mEditTextShipingZipcode = (EditText) findViewById(R.id.etx_shippingzipcode);
        mCheckBoxShiping = (CheckBox) findViewById(chk_shipin_address);
        mImageViewDealImage = (ImageView) findViewById(R.id.img_provider);
        mRatingBar = (RatingBar) findViewById(R.id.ratingBar_deal);

        setCapitalizeTextWatcher(mEditTextBillingAddress1);
        setCapitalizeTextWatcher(mEditTextShipingAddress1);
        setCapitalizeTextWatcher(mEditTextServiceAddress1);
        setCapitalizeTextWatcher(mEditTextBusinessName);
        setCapitalizeTextWatcher(mEditTextFirstName);
        setCapitalizeTextWatcher(mEditTextBusinessName);
        setCapitalizeTextWatcher(mEditTextTownCity);
        setCapitalizeTextWatcher(mEditTextManagerName);
        setCapitalizeTextWatcher(mEditTextShipingTown);
        setCapitalizeTextWatcher(mEditTextServiceCity);
        setCapitalizeTextWatcher(mEditTextLastName);


        onClickListener();


    }

    private void updateDisplay() {
        if (Month >= 9 && Day >= 10) {
            mEditTextBusinessDobNumber.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append(Month + 1).append("/")

                    .append(Day).append("/").append(Year));
        } else if (Month >= 9 && Day < 10) {
            mEditTextBusinessDobNumber.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append(Month + 1).append("/")
                    .append("0").append(Day).append("/").append(Year));

        } else if (Month < 9 && Day >= 10) {
            mEditTextBusinessDobNumber.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append("0").append(Month + 1)

                    .append("/").append(Day).append("/").append(Year));
        } else {
            mEditTextBusinessDobNumber.setText(new StringBuilder()
                    // Month is 0 based so add 1

                    .append("0").append(Month + 1)
                    .append("/")
                    .append("0").append(Day).append("/").append(Year));
        }
    }

    private DatePickerDialog.OnDateSetListener DateSetListener = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            Year = year;
            Month = monthOfYear;
            Day = dayOfMonth;

            updateDisplay();
        }
    };

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                DatePickerDialog da;
                da = new DatePickerDialog(this, DateSetListener, Year, Month, Day);

                da.setCanceledOnTouchOutside(false);
                da.setCancelable(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

                    da.getDatePicker().setCalendarViewShown(false);
                    da = new DatePickerDialog(this, R.style.Base_Theme_AppCompat_Light_Dialog, DateSetListener, Year, Month, Day);

                } else {
                    da = new DatePickerDialog(this, DateSetListener, Year, Month, Day);
                }
                da.getDatePicker().setMaxDate(System.currentTimeMillis());
//                mDatePicker.getDatePicker().setMaxDate(System.currentTimeMillis());
                return da;
        }
        return null;
    }

    private void onClickListener() {
        mCheckBoxShiping.setOnClickListener(this);
        mCheckBoxService.setOnClickListener(this);
        mImageViewBusinessInformation.setOnClickListener(this);
        mTextViewBusinessNext.setOnClickListener(this);
        mEditTextBusinessDobNumber.setOnClickListener(this);
        mTextViewPersonalNext.setOnClickListener(this);
        mTextViewAddressNext.setOnClickListener(this);
        mTextViewOrderProceed.setOnClickListener(this);
        mImageViewAddress.setOnClickListener(this);
        mImageViewOrder.setOnClickListener(this);
        mImageViewPersonal.setOnClickListener(this);
        mCheckBoxShiping.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Localytics.tagScreen("PlaceOrderAndPay");
    }

    public void localyticstagEvent(String method) {
        Map<String, String> home_values = new HashMap<String, String>();

        home_values.put("Success", "Yes");
        home_values.put("Method", method);

        Localytics.tagEvent("SignUp", home_values);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (item.getItemId() == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /*set action bar*/
    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        toolbar.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Order Form" + "</font>")));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.etx_buisness_dob_number:
                showDialog(DATE_DIALOG_ID);
                break;
            case R.id.img_personal:
                if (mLinearLayoutPersonalInformation.isShown()) {
                    mImageViewPersonal.setBackgroundResource(R.drawable.arrowup);
                    mLinearLayoutAddressInformation.setVisibility(View.GONE);
                    mLinearLayoutPersonalInformation.setVisibility(View.GONE);
                    mLinearLayoutOrderReview.setVisibility(View.GONE);
                    mLinearLayoutllBuisnessInformation.setVisibility(View.GONE);
                } else {
                    mImageViewOrder.setBackgroundResource(R.drawable.arrowup);
                    mImageViewAddress.setBackgroundResource(R.drawable.arrowup);
                    mImageViewPersonal.setBackgroundResource(R.drawable.arrowdown);
                    mImageViewBusinessInformation.setBackgroundResource(R.drawable.arrowup);
                    mLinearLayoutllBuisnessInformation.setVisibility(View.GONE);
                    mLinearLayoutAddressInformation.setVisibility(View.GONE);
                    mLinearLayoutPersonalInformation.setVisibility(View.VISIBLE);
                    mLinearLayoutOrderReview.setVisibility(View.GONE);
                }
                break;
            case R.id.chk_shipin_address:
                if (mCheckBoxShiping.isChecked()) {
                    if (Validation.isFieldEmpty(mEditTextBillingAddress1) || Validation.isFieldEmpty(mEditTextTownCity) || Validation.isFieldEmpty(mEditTextBillingZipcode)) {
                        Toast.makeText(this, "Please fill Billing Address", Toast.LENGTH_LONG)
                                .show();
                        mCheckBoxShiping.setChecked(false);
                    } else {
                        mEditTextShipingAddress1.setText(mEditTextBillingAddress1.getText());
                        mEditTextShipingAddress2.setText(mEditTextBillingAddress2.getText());
                        mEditTextShipingTown.setText(mEditTextTownCity.getText());
                        mEditTextShipingZipcode.setText(mEditTextBillingZipcode.getText());
                        mLinearLayoutShipingAddress.setVisibility(View.GONE);
                    }

                } else {
                    mEditTextShipingAddress1.setText("");
                    mEditTextShipingAddress2.setText("");
                    mEditTextShipingTown.setText("");
                    mEditTextShipingZipcode.setText("");
                    mLinearLayoutShipingAddress.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.chk_service_address:
                if (mCheckBoxService.isChecked()) {
                    if (Validation.isFieldEmpty(mEditTextBillingAddress1) || Validation.isFieldEmpty(mEditTextTownCity) || Validation.isFieldEmpty(mEditTextBillingZipcode)) {
                        Toast.makeText(this, "Please fill Billing Address", Toast.LENGTH_LONG)
                                .show();
                        mCheckBoxService.setChecked(false);
                    } else {
                        mEditTextServiceAddress1.setText(mEditTextBillingAddress1.getText());
                        mEditTextServiceAddress2.setText(mEditTextBillingAddress2.getText());
                        mEditTextServiceCity.setText(mEditTextTownCity.getText());
                        mEditTextServiceZipCode.setText(mEditTextBillingZipcode.getText());
                        mLinearLayoutServiceAddress.setVisibility(View.GONE);
                    }

                } else {
                    mEditTextServiceAddress1.setText("");
                    mEditTextServiceAddress2.setText("");
                    mEditTextServiceCity.setText("");
                    mEditTextServiceZipCode.setText("");
                    mLinearLayoutServiceAddress.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.img_address:
                if (mLinearLayoutAddressInformation.isShown()) {
                    mImageViewAddress.setBackgroundResource(R.drawable.arrowup);
                    mLinearLayoutAddressInformation.setVisibility(View.GONE);
                    mLinearLayoutPersonalInformation.setVisibility(View.GONE);
                    mLinearLayoutOrderReview.setVisibility(View.GONE);
                    mLinearLayoutllBuisnessInformation.setVisibility(View.GONE);
                } else {

                    if (!mSharedPreferences.getString("user_type", "").equalsIgnoreCase("residence")) {
                        mImageViewOrder.setBackgroundResource(R.drawable.arrowup);
                        mImageViewBusinessInformation.setBackgroundResource(R.drawable.arrowup);
                        mImageViewAddress.setBackgroundResource(R.drawable.arrowdown);
                        mLinearLayoutAddressInformation.setVisibility(View.VISIBLE);
                        mLinearLayoutllBuisnessInformation.setVisibility(View.GONE);
                        mLinearLayoutOrderReview.setVisibility(View.GONE);
                    } else {
                        mImageViewOrder.setBackgroundResource(R.drawable.arrowup);
                        mImageViewPersonal.setBackgroundResource(R.drawable.arrowup);
                        mLinearLayoutllBuisnessInformation.setVisibility(View.GONE);
                        mImageViewBusinessInformation.setBackgroundResource(R.drawable.arrowup);
                        mImageViewAddress.setBackgroundResource(R.drawable.arrowdown);
                        mLinearLayoutAddressInformation.setVisibility(View.VISIBLE);
                        mLinearLayoutPersonalInformation.setVisibility(View.GONE);
                        mLinearLayoutOrderReview.setVisibility(View.GONE);

                    }
                }

                break;

            case R.id.img_business:
                if (mLinearLayoutllBuisnessInformation.isShown()) {
                    mImageViewBusinessInformation.setBackgroundResource(R.drawable.arrowup);
                    mLinearLayoutAddressInformation.setVisibility(View.GONE);
                    mLinearLayoutPersonalInformation.setVisibility(View.GONE);
                    mLinearLayoutOrderReview.setVisibility(View.GONE);
                    mLinearLayoutllBuisnessInformation.setVisibility(View.GONE);
                } else {

                    mImageViewBusinessInformation.setBackgroundResource(R.drawable.arrowdown);
                    mImageViewOrder.setBackgroundResource(R.drawable.arrowup);
                    mImageViewPersonal.setBackgroundResource(R.drawable.arrowup);
                    mImageViewAddress.setBackgroundResource(R.drawable.arrowup);
                    mLinearLayoutllBuisnessInformation.setVisibility(View.VISIBLE);
                    mLinearLayoutAddressInformation.setVisibility(View.GONE);
                    mLinearLayoutPersonalInformation.setVisibility(View.GONE);
                    mLinearLayoutOrderReview.setVisibility(View.GONE);
                }

                break;
            case R.id.img_order:
                if (mLinearLayoutOrderReview.isShown()) {
                    mImageViewOrder.setBackgroundResource(R.drawable.arrowup);
                    mLinearLayoutAddressInformation.setVisibility(View.GONE);
                    mLinearLayoutPersonalInformation.setVisibility(View.GONE);
                    mLinearLayoutOrderReview.setVisibility(View.GONE);
                    mLinearLayoutllBuisnessInformation.setVisibility(View.GONE);

                } else {
                    if (checkPersonalValidation()) {
                        if (!mSharedPreferences.getString("user_type", "").equalsIgnoreCase("residence")) {
                            if (checkBusinessValidation()) {
                                if (checkAddressValidation()) {
                                    setTextinOrderView();
                                }
                            }
                        } else {
                            if (checkAddressValidation()) {
                                setTextinOrderView();
                            }
                        }
                    }

                }
                break;
            case R.id.txt_personal_next:
                if (checkPersonalValidation()) {
                    if (!mSharedPreferences.getString("user_type", "").equalsIgnoreCase("residence")) {
                        mImageViewBusinessInformation.setBackgroundResource(R.drawable.arrowdown);
                        mImageViewPersonal.setBackgroundResource(R.drawable.arrowup);
                        mLinearLayoutllBuisnessInformation.setVisibility(View.VISIBLE);
                        mLinearLayoutPersonalInformation.setVisibility(View.GONE);
                    } else {
                        mImageViewAddress.setBackgroundResource(R.drawable.arrowdown);
                        mImageViewPersonal.setBackgroundResource(R.drawable.arrowup);
                        mLinearLayoutAddressInformation.setVisibility(View.VISIBLE);
                        mLinearLayoutPersonalInformation.setVisibility(View.GONE);

                    }
                }
                break;
            case R.id.txt_address_next:
                if (checkPersonalValidation()) {
                    if (!mSharedPreferences.getString("user_type", "").equalsIgnoreCase("residence")) {
                        if (checkBusinessValidation()) {
                            if (checkAddressValidation()) {
                                setTextinOrderView();
                            }
                        }
                    } else {
                        if (checkAddressValidation()) {
                            setTextinOrderView();
                        }
                    }
                }


                break;
            case R.id.txt_business_next:
                if (checkPersonalValidation()) {
                    if (checkBusinessValidation()) {
                        mImageViewAddress.setBackgroundResource(R.drawable.arrowdown);
                        mImageViewBusinessInformation.setBackgroundResource(R.drawable.arrowup);
                        mLinearLayoutAddressInformation.setVisibility(View.VISIBLE);
                        mLinearLayoutllBuisnessInformation.setVisibility(View.GONE);
                       /* GetTextData();
                        if (isInternetPresent) {
                            new HttpGetAsyncTaskGetValidationFederalAndSSN().execute();
                        } else {
                            ShowDailog.Show_Alert_Dailog(this);
                        }*/

                    }
                }


                break;
            case R.id.txt_proceed:


                GetTextData();
                if (isInternetPresent) {

                    new HttpGetAsyncTask().execute();
                } else {
                    ShowDailog.Show_Alert_Dailog(this);
                }
               /* Intent intent = new Intent(getApplicationContext(), GiftCardActivty.class);
                startActivity(intent);
                finish();*/
                break;
          /*  case chk_shipin_address:
                if (mCheckBoxShiping.isChecked()) {
                    mEditTextShipingAddress1.setText(mEditTextBillingAddress1.getText());
                    mEditTextShipingAddress1.setBackgroundResource(R.drawable.disable_edittext);
                    mEditTextShipingAddress1.setClickable(false);
                    mEditTextShipingAddress1.setFocusable(false);
                    mEditTextShipingAddress1.setFocusableInTouchMode(false);
                    mEditTextShipingAddress2.setText(mEditTextBillingAddress2.getText());
                    mEditTextShipingAddress2.setBackgroundResource(R.drawable.disable_edittext);
                    mEditTextShipingAddress2.setClickable(false);
                    mEditTextShipingAddress2.setFocusable(false);
                    mEditTextShipingAddress2.setFocusableInTouchMode(false);
                    mEditTextShipingTown.setText(mEditTextTownCity.getText());
                    mEditTextShipingTown.setBackgroundResource(R.drawable.disable_edittext);
                    mEditTextShipingTown.setClickable(false);
                    mEditTextShipingTown.setFocusable(false);
                    mEditTextShipingTown.setFocusableInTouchMode(false);
                    mEditTextShipingZipcode.setText(mEditTextBillingZipcode.getText());
                    mEditTextShipingZipcode.setBackgroundResource(R.drawable.disable_edittext);
                    mEditTextShipingZipcode.setClickable(false);
                    mEditTextShipingZipcode.setFocusable(false);
                    mEditTextShipingZipcode.setFocusableInTouchMode(false);
                } else {
                    mEditTextShipingAddress1.setBackgroundResource(R.drawable.order_editext_background);
                    mEditTextShipingAddress1.setClickable(true);
                    mEditTextShipingAddress1.setFocusable(true);
                    mEditTextShipingAddress1.setFocusableInTouchMode(true);
                    mEditTextShipingAddress2.setBackgroundResource(R.drawable.order_editext_background);
                    mEditTextShipingAddress2.setClickable(true);
                    mEditTextShipingAddress2.setFocusable(true);
                    mEditTextShipingAddress2.setFocusableInTouchMode(true);
                    mEditTextShipingTown.setBackgroundResource(R.drawable.order_editext_background);
                    mEditTextShipingTown.setClickable(true);
                    mEditTextShipingTown.setFocusable(true);
                    mEditTextShipingTown.setFocusableInTouchMode(true);
                    mEditTextShipingZipcode.setBackgroundResource(R.drawable.order_editext_background);
                    mEditTextShipingZipcode.setClickable(true);
                    mEditTextShipingZipcode.setFocusable(true);
                    mEditTextShipingZipcode.setFocusableInTouchMode(true);
                    mEditTextShipingAddress1.setText("");
                    mEditTextShipingAddress2.setText("");
                    mEditTextShipingTown.setText("");
                    mEditTextShipingZipcode.setText("");
                }
                break;*/
        }

    }

    class HttpGetAsyncTaskGetValidationFederalAndSSN extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PlaceOrderAndPay.this);
            pDialog.setMessage("Wait...");
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.get_validate_federal(mSharedPreferences.getString(Constant.APP_USER_ID, ""), buisness_type, bisiness_name, federal_number, ssn_number, PlaceOrderAndPay.this);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress mDialog
         * **/

        protected void onPostExecute(String response) {
            pDialog.dismiss();
            try {
                JSONObject jsonObject = new JSONObject(response);
                if (!jsonObject.getBoolean("success")) {

                    mEditTextFederalNumber.setText("");
                    mEditTextSSN.setText("");


                    InvalidFederalMessage = jsonObject.getString("message");
                    showForgotPasswordDialog();
//                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            if()


        }
    }

    private void setTextinOrderView() {
        mTextViewOrderName.setText(mEditTextFirstName.getText() + " " + mEditTextLastName.getText().toString());
        mTextViewOrederMobileEmail.setText("Mobile: " + mEditTextMobileNo.getText());
        if (mCheckBoxService.isChecked()) {
//            mTextViewServiceAddress.setText("Same as Billing Address.");
            mTextViewServiceAddress.setText(mEditTextBillingAddress1.getText() + " " + mEditTextBillingAddress2.getText() + "\n" + mEditTextTownCity.getText() + ", " + mSpinnerBillingState.getSelectedItem().toString() + ", " + mEditTextBillingZipcode.getText());
            mTextViewOrderAddress.setText("Primary Id: " + mSpinnerPrimary.getSelectedItem().toString() + " - " + mEditTextPrimaryId.getText().toString() + "\nSecondary Id: " + mSpinnerSecondary.getSelectedItem().toString() + " - " + mEditTextSecondaryId.getText().toString());
           mImageViewAddress.setBackgroundResource(R.drawable.arrowup);
        } else {
            mTextViewServiceAddress.setText(mEditTextServiceAddress1.getText() + " " + mEditTextServiceAddress2.getText() + "\n" + mEditTextServiceCity.getText() + ", " + mSpinnerServiceState.getSelectedItem().toString() + ", " + mEditTextServiceZipCode.getText());
        }
        if (mCheckBoxShiping.isChecked()) {
//            mTextViewShippingAddress.setText("Same as Billing Address.");
            mTextViewShippingAddress.setText(mEditTextBillingAddress1.getText() + " " + mEditTextBillingAddress2.getText() + "\n" + mEditTextTownCity.getText() + ", " + mSpinnerBillingState.getSelectedItem().toString() + ", " + mEditTextBillingZipcode.getText());
            mTextViewOrderAddress.setText("Primary Id: " + mSpinnerPrimary.getSelectedItem().toString() + " - " + mEditTextPrimaryId.getText().toString() + "\nSecondary Id: " + mSpinnerSecondary.getSelectedItem().toString() + " - " + mEditTextSecondaryId.getText().toString());
            mImageViewAddress.setBackgroundResource(R.drawable.arrowup);
        } else {
            mTextViewShippingAddress.setText(mEditTextShipingAddress1.getText() + " " + mEditTextShipingAddress2.getText() + "\n" + mEditTextShipingTown.getText() + ", " + mSpinnerShipingState.getSelectedItem().toString() + ", " + mEditTextShipingZipcode.getText());
        }

        mTextViewBillingAddress.setText(mEditTextBillingAddress1.getText() + " " + mEditTextBillingAddress2.getText() + "\n" + mEditTextTownCity.getText() + ", " + mSpinnerBillingState.getSelectedItem().toString() + ", " + mEditTextBillingZipcode.getText());

        mTextViewOrderAddress.setText("Primary Id: " + mSpinnerPrimary.getSelectedItem().toString() + " - " + mEditTextPrimaryId.getText().toString() + "\nSecondary Id: " + mSpinnerSecondary.getSelectedItem().toString() + " - " + mEditTextSecondaryId.getText().toString());
        mImageViewAddress.setBackgroundResource(R.drawable.arrowup);
        if (mSharedPreferences.getString("user_type", "").equalsIgnoreCase("residence")) {
            mTextViewBusinessTypeDetail.setVisibility(View.GONE);
            mTextViewBusinessType.setVisibility(View.GONE);
        }
        if (mRadioButtonSole.isChecked()) {
            mTextViewBusinessTypeDetail.setText("Business Name: " + mEditTextBusinessName.getText() + "\nSSN: " + mEditTextSSN.getText() + "\nDOB Number: " + mEditTextBusinessDobNumber.getText());
            mTextViewBusinessType.setText(mRadioButtonSole.getText().toString().trim());

        } else {

            mTextViewBusinessTypeDetail.setText("Business Name: " + mEditTextBusinessName.getText() + "\nFederal Tax Id: " + mEditTextFederalNumber.getText() + "\nDan & Bradstreet Number: " + mEditTextDBNumber.getText());
            mTextViewBusinessType.setText(mRadioButtonRegistred.getText().toString().trim());

        }
        mImageViewPersonal.setBackgroundResource(R.drawable.arrowup);
        mImageViewOrder.setBackgroundResource(R.drawable.arrowdown);
        mLinearLayoutllBuisnessInformation.setVisibility(View.GONE);
        mImageViewBusinessInformation.setBackgroundResource(R.drawable.arrowup);
        mLinearLayoutAddressInformation.setVisibility(View.GONE);
        mLinearLayoutPersonalInformation.setVisibility(View.GONE);
        mLinearLayoutOrderReview.setVisibility(View.VISIBLE);
    }

    private void setEquipementsData() {
        RecyclerView recyclerView;
        LinearLayout mLinearLayoutDealExtra, mLinearLayoutPlanSubscription;
        RelativeLayout mRelativeLayoutAdditional;

        custemCellphoneEquipmentsAdditionalOffers = new CustemCellphoneEquipmentsAdditionalOffers();
        List<CustemCellphoneEquipmentsAdditionalOffers.DevicelistEntity> devicelistEntities = new ArrayList<>();


        recyclerView = (RecyclerView) findViewById(R.id.lv_additionalequipments);
        mLinearLayoutPlanSubscription = (LinearLayout) findViewById(R.id.l1sumery_ordersubscription);
        mLinearLayoutDealExtra = (LinearLayout) findViewById(R.id.l1sumery_orderAttributes);
        mRelativeLayoutAdditional = (RelativeLayout) findViewById(R.id.rl_additionalattributes);

        try {
            addItemDecoration = new PaddingItemDecoration(this);
        } catch (Exception e) {
        }
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(addItemDecoration);
        if (EquipmentsAdapter.equipmentSelected.getListDevice() != null && EquipmentsAdapter.equipmentSelected.getListDevice().size() > 0) {
            for (int i = 0; EquipmentsAdapter.equipmentSelected.getListDevice().size() > i; i++) {
                for (int j = 0; EquipmentsAdapter.equipmentSelected.getListDevice().get(i).getDevicelist().size() > j; j++) {
                    CustemCellphoneEquipmentsAdditionalOffers.DevicelistEntity devicelistEntity = new CustemCellphoneEquipmentsAdditionalOffers.DevicelistEntity();
                    devicelistEntity.setPhoneid(EquipmentsAdapter.equipmentSelected.getListDevice().get(i).getDevicelist().get(j).getPhoneid());
                    devicelistEntity.setPhonebrand(EquipmentsAdapter.equipmentSelected.getListDevice().get(i).getDevicelist().get(j).getPhonebrand());
                    devicelistEntity.setPhonename(EquipmentsAdapter.equipmentSelected.getListDevice().get(i).getDevicelist().get(j).getPhonename());
                    devicelistEntity.setPhonecolor(EquipmentsAdapter.equipmentSelected.getListDevice().get(i).getDevicelist().get(j).getPhonecolor());
                    devicelistEntity.setPhoneprice(EquipmentsAdapter.equipmentSelected.getListDevice().get(i).getDevicelist().get(j).getPhoneprice());
                    devicelistEntity.setPhoneImage(EquipmentsAdapter.equipmentSelected.getListDevice().get(i).getDevicelist().get(j).getPhoneImageUrl());
                    devicelistEntities.add(devicelistEntity);
                }
            }
            custemCellphoneEquipmentsAdditionalOffers.setDevicelist(devicelistEntities);
            recyclerView.setAdapter(new AdditionalOfferAdapter(PlaceOrderAndPay.this, custemCellphoneEquipmentsAdditionalOffers));

        } else {
            mRelativeLayoutAdditional.setVisibility(View.GONE);
        }
        if (ExtraServicePrice.equalsIgnoreCase("0.0")) {
            mLinearLayoutDealExtra.setVisibility(View.GONE);
        }
        if (PlanReferPrice == null) {
            mLinearLayoutPlanSubscription.setVisibility(View.GONE);
        }
        if (PlanReferPrice == null && ExtraServicePrice.equalsIgnoreCase("0.0") && EquipmentsAdapter.equipmentSelected.getListDevice() == null) {
            mEquipementsOrderSumery.setVisibility(View.GONE);
        }
        mAttributesName.setText(ExtraServiceName);
        mAttributesPrice.setText("$" + ExtraServicePrice);
        mPlanName.setText(PlanName);
        mPlanPrice.setText("$" + PlanReferPrice);
    }

    private boolean checkAddressValidation() {
        Boolean status = false;
        if (Validation.isFieldEmpty(mEditTextBillingAddress1) || Validation.isFieldEmpty(mEditTextBillingZipcode)) {
            Toast.makeText(this, "Please fill Billing Address", Toast.LENGTH_LONG)
                    .show();
        } else if (Validation.isFieldEmpty(mEditTextBillingAddressName)) {
            Toast.makeText(this, "Please fill Address Name in Billing Address", Toast.LENGTH_LONG)
                    .show();
        } else if (mSpinnerBillingState.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Please Select State in Billing Address", Toast.LENGTH_LONG)
                    .show();
        } else if (!mCheckBoxShiping.isChecked()) {
            if (Validation.isFieldEmpty(mEditTextShipingAddress1) || Validation.isFieldEmpty(mEditTextShipingZipcode)) {
                Toast.makeText(this, "Please fill Shipping Address", Toast.LENGTH_LONG)
                        .show();
            } else if (Validation.isFieldEmpty(mEditTextShipingAddressName)) {
                Toast.makeText(this, "Please fill Address Name in Shiping Address", Toast.LENGTH_LONG)
                        .show();
            } else if (mSpinnerShipingState.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Please Select State in Shiping Address", Toast.LENGTH_LONG)
                        .show();
            } else if (!mCheckBoxService.isChecked()) {
                if (Validation.isFieldEmpty(mEditTextServiceAddress1) || Validation.isFieldEmpty(mEditTextServiceZipCode)) {
                    Toast.makeText(this, "Please fill Service Address", Toast.LENGTH_LONG)
                            .show();
                } else if (Validation.isFieldEmpty(mEditTextServiceAddressName)) {
                    Toast.makeText(this, "Please fill Address Name in Service Address", Toast.LENGTH_LONG)
                            .show();
                } else if (mSpinnerServiceState.getSelectedItemPosition() == 0) {
                    Toast.makeText(this, "Please Select State in Service Address", Toast.LENGTH_LONG)
                            .show();
                } else {
                    status = true;
                }

            } else {
                status = true;
            }

        } else if (!mCheckBoxService.isChecked()) {
            if (Validation.isFieldEmpty(mEditTextServiceAddress1) || Validation.isFieldEmpty(mEditTextServiceZipCode)) {
                Toast.makeText(this, "Please fill Service Address", Toast.LENGTH_LONG)
                        .show();

            } else if (Validation.isFieldEmpty(mEditTextServiceAddressName)) {
                Toast.makeText(this, "Please fill Address Name in Service Address", Toast.LENGTH_LONG)
                        .show();
            } else if (mSpinnerServiceState.getSelectedItemPosition() == 0) {
                Toast.makeText(this, "Please Select State in Service Address", Toast.LENGTH_LONG)
                        .show();
            } else {
                status = true;
            }

        } else {
            status = true;
        }

        return status;
    }

    private boolean checkPersonalValidation() {
        if (Validation.isFieldEmpty(mEditTextFirstName)) {
            Toast.makeText(this, "Please fill First Name Personal Information", Toast.LENGTH_LONG)
                    .show();
            return false;
        } else if (Validation.isFieldEmpty(mEditTextLastName)) {
            Toast.makeText(this, "Please fill Last Name in Personal Information", Toast.LENGTH_LONG)
                    .show();
            return false;
        } else if (Validation.isFieldEmpty(mEditTextMobileNo)) {
            Toast.makeText(this, "Please fill Mobile No in Personal Information", Toast.LENGTH_LONG)
                    .show();
            return false;
        } else if (Validation.isMobileValid(mEditTextMobileNo.getText().toString())) {
            Toast.makeText(this, "Please Enter Valid Mobile No in Personal Information",
                    Toast.LENGTH_LONG).show();
            return false;
        } else if (mSpinnerPrimary.getSelectedItem().toString().contains("Driving") && mSpinnerPrimaryState.getSelectedItemPosition() == 0) {

            Toast.makeText(this, "Please Select State in Personal Information", Toast.LENGTH_LONG)
                    .show();
            return false;

        } else if (mSpinnerSecondary.getSelectedItem().toString().contains("Driving") && mSpinnerSecondaryState.getSelectedItemPosition() == 0) {

            Toast.makeText(this, "Please Select State in Personal Information", Toast.LENGTH_LONG)
                    .show();
            return false;

        } else {
            return true;
        }

    }

    private boolean checkBusinessValidation() {
        if (Validation.isFieldEmpty(mEditTextBuisnessMobileNumber)) {
            Toast.makeText(this, "Please Enter Main Telephone Number",
                    Toast.LENGTH_LONG).show();
            return false;
        } else if (Validation.isMobileValid(mEditTextBuisnessMobileNumber.getText().toString())) {
            Toast.makeText(this, "Please Enter Valid Main Telephone Number",
                    Toast.LENGTH_LONG).show();
            return false;
        } else if (mRadioButtonRegistred.isChecked()) {
            if (Validation.isFieldEmpty(mEditTextFederalNumber) || mEditTextFederalNumber.getText().length() != 10) {
                Toast.makeText(this, "Please Enter Federal Tax id",
                        Toast.LENGTH_LONG).show();
                return false;
            } else {
                return true;
            }
        } else if (mRadioButtonSole.isChecked()) {
            if (mEditTextSSN.getText().length() != 11) {
                Toast.makeText(this, "Please Enter Valid SSN",
                        Toast.LENGTH_LONG).show();
                return false;
            } else {
                return true;
            }
        } else {

            return true;
        }
    }

    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PlaceOrderAndPay.this);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                if (!mSharedPreferences.getString("user_type", "").equalsIgnoreCase("residence")) {
                    if (!mCustomActivity) {

                        EffectivePrice = "" + buisnessUserPlaceOrder.getDeals().get(0).getEffective_price();
                    }
                    confirmationorder = Jsondata.send_order_buisness(mSharedPreferences.getString(Constant.APP_USER_ID, ""), name, email, billing_address1, billing_address2, billing_country, billing_zipcode
                            , shiping_address1, shiping_address2, shiping_country, shiping_zipcode, mobile_no, bisiness_name,
                            db_number, federal_number, buisness_type, ssn_number, buisness_mobile_number, deal_id, "" + buisnessUserPlaceOrder.getDeals().get(0).getPrice(), "" + EffectivePrice,
                            managername, lastname, primary_id, secondary_id, DOB, dbumber, DBA, is_shipping_address_same, service_address1, service_address2, service_city,
                            service_zipcode,freetext, is_service_address_same, buisnessUserPlaceOrder.getBusiness().getId(), primary_id_number, secondary_id_number, billingState,
                            serviceState, shipingState, billingaddressname, shipingaddressname, serviceaddressname, EquimentsPrice, EquipementColorName,
                            mEquipementCellPhoneDetailId, PlanReferPrice, PlanType, mPlanRefferId, extraserviceId, ExtraServiceName, ExtraServicePrice, mChannelpremiumprice, mChalnePremiumID, mChalneEquipmentID, ChalneEquipmentPrice, TvAdapterPrice, TvAdapterId, providerid, PlaceOrderAndPay.this);
                } else {

                    if (!mCustomActivity) {
                        EffectivePrice = "" + residenceUserPlaceOrder.getDeals().get(0).getEffective_price();
                    }
                    confirmationorder = Jsondata.send_order_residence(mSharedPreferences.getString(Constant.APP_USER_ID, ""), name, email, billing_address1, billing_address2, billing_country, billing_zipcode
                            , shiping_address1, shiping_address2, shiping_country, shiping_zipcode, mobile_no, deal_id, "" +
                                    residenceUserPlaceOrder.getDeals().get(0).getPrice(),
                            EffectivePrice, lastname, primary_id, secondary_id,
                            is_shipping_address_same, service_address1, service_address2, service_city, service_zipcode,freetext, is_service_address_same,
                            primary_id_number, secondary_id_number, billingState, serviceState, shipingState, billingaddressname, shipingaddressname, serviceaddressname, EquimentsPrice, EquipementColorName,
                            mEquipementCellPhoneDetailId, PlanReferPrice, PlanType, mPlanRefferId, extraserviceId, ExtraServiceName, ExtraServicePrice, mChannelpremiumprice, mChalnePremiumID, mChalneEquipmentID, ChalneEquipmentPrice, TvAdapterPrice, TvAdapterId, providerid, PlaceOrderAndPay.this);

                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress mDialog
         * **/

        protected void onPostExecute(String response) {
            pDialog.dismiss();
            if (confirmationorder != null) {
                if (confirmationorder.getSuccess()) {
                    String str = mSharedPreferences.getString("contact_send", "");
//                    if (str.equalsIgnoreCase("")) {
//
//                        Show_Alert_Login(PlaceOrderAndPay.this, "-> We will contact you within 24 hours.\n\n-> Provide access to your address book and get $10 from us, and help your friends & family $ave.", "Thank You!");
//                    } else {
                        // Show_Alert_Login(PlaceOrderAndPay.this, "-> We will contact you within 24 hours.\n\n-> Provide access to your address book and get $10 from us, and help your friends & family $ave.", "Thank You!");
                        Intent myorder = new Intent(getApplicationContext(), OrderDealDetailActivity.class);
                        myorder.putExtra("orderid", confirmationorder.getOrder().getId());
                        myorder.putExtra("userid", mSharedPreferences.getString(Constant.APP_USER_ID, ""));
                        startActivity(myorder);
                        finish();
//                    }

                } else {
                    ShowDailog.Show_Alert_Login(PlaceOrderAndPay.this, "Business name already taken");
                }
            } else {
                ShowDailog.Show_Alert_Login(PlaceOrderAndPay.this, getResources().getText(R.string.server_error).toString());
            }

        }
    }

    class HttpGetAsyncTaskGet extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PlaceOrderAndPay.this);
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                residenceUserPlaceOrder = Jsondata.get_user_detail_residence(mSharedPreferences.getString(Constant.APP_USER_ID, ""), deal_id, PlaceOrderAndPay.this);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress mDialog
         * **/

        protected void onPostExecute(String response) {
            pDialog.dismiss();
            try {
                if (residenceUserPlaceOrder.getSuccess()) {
                    setDataInViewComponent();
                }
            } catch (Exception e) {
                // ShowDailog.Show_Alert_Login(PlaceOrderAndPay.this, getResources().getText(R.string.server_error).toString());
                e.printStackTrace();
            }
            setDatainView();

        }
    }

    class HttpGetAsyncTaskGetStae extends AsyncTask<String, Void, String> {
        String response = "";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {
            try {

                primaryAndSecondaryId = Jsondata.getPrimaryAndSecondaryId(mSharedPreferences.getString(Constant.APP_USER_ID, ""), PlaceOrderAndPay.this);
                state = Jsondata.getState(PlaceOrderAndPay.this);
                cellphoneEquipments = Jsondata.getcellphone_equipments(PlaceOrderAndPay.this, mSharedPreferences.getString(Constant.APP_USER_ID, ""), deal_id);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress mDialog
         * **/

        protected void onPostExecute(String response) {

            try {
                List<String> spinnerArray = new ArrayList<>();
                spinnerArray.clear();
                for (int i = 0; i < cellphoneEquipments.getEquipments().size(); i++) {
                    spinnerArray.add(cellphoneEquipments.getEquipments().get(i).getModel());
                }
                ArrayAdapter<String> adapterEquipments = new ArrayAdapter<String>(PlaceOrderAndPay.this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);

                ArrayAdapter<String> adapterState = new ArrayAdapter<String>(PlaceOrderAndPay.this, android.R.layout.simple_spinner_dropdown_item, state.getStates());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(PlaceOrderAndPay.this, android.R.layout.simple_spinner_dropdown_item, primaryAndSecondaryId.getPrimaryIds());
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(PlaceOrderAndPay.this, android.R.layout.simple_spinner_dropdown_item, primaryAndSecondaryId.getSecondaryIds());
                adapterState.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                adapterEquipments.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                if (spinnerArray.size() > 0) {

                } else {
                    mSpinnerEquiments.setVisibility(View.GONE);
                }
                mSpinnerEquiments.setAdapter(adapterEquipments);

                mSpinnerPrimary.setAdapter(adapter);
                mSpinnerSecondary.setAdapter(adapter1);
                mSpinnerPrimaryState.setAdapter(adapterState);
                mSpinnerSecondaryState.setAdapter(adapterState);
                mSpinnerShipingState.setAdapter(adapterState);
                mSpinnerServiceState.setAdapter(adapterState);
                mSpinnerBillingState.setAdapter(adapterState);
                setSpinnerData();

            } catch (Exception e) {
                // ShowDailog.Show_Alert_Login(PlaceOrderAndPay.this, getResources().getText(R.string.server_error).toString());
                e.printStackTrace();
            }


        }
    }

    class HttpGetAsyncTaskGetBuisness extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(PlaceOrderAndPay.this);
            pDialog.setMessage("Wait...");
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                buisnessUserPlaceOrder = Jsondata.get_user_detail(mSharedPreferences.getString(Constant.APP_USER_ID, ""), deal_id, PlaceOrderAndPay.this);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }



       /* *
         * After completing background task Dismiss the progress mDialog
         * **/

        protected void onPostExecute(String response) {
            pDialog.dismiss();
            try {
                if (buisnessUserPlaceOrder.getSuccess()) {

                    setDataInViewComponentBusiness();
                }
            } catch (Exception e) {
                //  ShowDailog.Show_Alert_Login(PlaceOrderAndPay.this, getResources().getText(R.string.server_error).toString());
                e.printStackTrace();
            }
            //  setDatainView();
            mEditTextSSN.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    mEditTextSSN.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {


                            if (keyCode == KeyEvent.KEYCODE_DEL)
                                keyDel = 1;
                            return false;
                        }
                    });

                    if (keyDel == 0) {
                        int len = mEditTextSSN.getText().length();
                        if (len == 3 || len == 6) {
                            mEditTextSSN.setText(mEditTextSSN.getText() + "-");
                            mEditTextSSN.setSelection(mEditTextSSN.getText().length());
                        }
                    } else {
                        keyDel = 0;
                    }
                    if (mEditTextSSN.getText().length() == 11) {
                        bisiness_name = mEditTextBusinessName.getText().toString();
                        buisness_type = "0";
                        federal_number = "";
                        ssn_number = mEditTextSSN.getText().toString();
                        new HttpGetAsyncTaskGetValidationFederalAndSSN().execute();
                    }
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }
            });
            mEditTextFederalNumber.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    mEditTextFederalNumber.setOnKeyListener(new View.OnKeyListener() {
                        @Override
                        public boolean onKey(View v, int keyCode, KeyEvent event) {


                            if (keyCode == KeyEvent.KEYCODE_DEL)
                                keyDel = 1;
                            return false;
                        }
                    });

                    if (keyDel == 0) {
                        int len = mEditTextFederalNumber.getText().length();
                        if (len == 2) {
                            mEditTextFederalNumber.setText(mEditTextFederalNumber.getText() + "-");
                            mEditTextFederalNumber.setSelection(mEditTextFederalNumber.getText().length());
                        }
                    } else {
                        keyDel = 0;
                    }
                    if (mEditTextFederalNumber.getText().length() == 10) {

                        bisiness_name = mEditTextBusinessName.getText().toString();
                        buisness_type = "1";
                        ssn_number = "";
                        federal_number = mEditTextFederalNumber.getText().toString();
                        new HttpGetAsyncTaskGetValidationFederalAndSSN().execute();
                    }
                }

                @Override
                public void afterTextChanged(Editable arg0) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                }
            });
        }

    }

    private void setDatainView() {
        if (residenceUserPlaceOrder.getDeals().get(0).getAverage_rating().toString().equalsIgnoreCase("")) {
            mRatingBar.setRating((float) 0.00);
        } else {
            float f1 = Float.parseFloat(residenceUserPlaceOrder.getDeals().get(0).getAverage_rating().toString());
            mRatingBar.setRating(f1);
        }
        Picasso.with(this)
                .load(residenceUserPlaceOrder.getDeals().get(0).getDeal_image_url().toString()).placeholder(R.drawable.progress_animation)
                .into(mImageViewDealImage);
        mTextViewDealDesc.setText(residenceUserPlaceOrder.getDeals().get(0).getShortDescription().toString());
        mTextViewPrice.setText("$" + residenceUserPlaceOrder.getDeals().get(0).getPrice().toString());
        mTextViewDealTitle.setText(residenceUserPlaceOrder.getDeals().get(0).getTitle().toString());
        if (residenceUserPlaceOrder.getDeals().get(0).getContractPeriod().toString().equalsIgnoreCase("0")) {
            mTextViewContractPeroid.setText("Contract : None");
        } else {
            mTextViewContractPeroid.setText("Contract : " + residenceUserPlaceOrder.getDeals().get(0).getContractPeriod().toString() + " months");
        }
        if (mCustomActivity) {
            mTextViewEffectivePrice.setText("$" + EffectivePrice);
        } else {
            if (residenceUserPlaceOrder.getDeals().get(0).getEffective_price().toString().equalsIgnoreCase("0.00")) {

                mTextViewEffectivePrice.setText("$" + residenceUserPlaceOrder.getDeals().get(0).getPrice().toString());
            } else {
                mTextViewEffectivePrice.setText("$" + residenceUserPlaceOrder.getDeals().get(0).getEffective_price().toString());
            }
        }

        mSpinnerEquiments.setOnItemSelectedListener(OnCatSpinnerCLCellphone);
    }

    private void setDataInViewComponentBusiness() {

        if (buisnessUserPlaceOrder.getDeals().get(0).getAverage_rating().toString().equalsIgnoreCase("")) {
            mRatingBar.setRating((float) 0.00);
        } else {
            float f1 = Float.parseFloat(buisnessUserPlaceOrder.getDeals().get(0).getAverage_rating().toString());
            mRatingBar.setRating(f1);
        }
        Picasso.with(this)
                .load(buisnessUserPlaceOrder.getDeals().get(0).getDeal_image_url().toString()).placeholder(R.drawable.progress_animation)
                .into(mImageViewDealImage);
        mTextViewDealDesc.setText(buisnessUserPlaceOrder.getDeals().get(0).getShortDescription().toString());
        mTextViewPrice.setText("$" + buisnessUserPlaceOrder.getDeals().get(0).getPrice().toString());
        mTextViewDealTitle.setText(buisnessUserPlaceOrder.getDeals().get(0).getTitle().toString());


        if (buisnessUserPlaceOrder.getDeals().get(0).getContractPeriod().toString().equalsIgnoreCase("0")) {
            mTextViewContractPeroid.setText("Contract : None");
        } else {
            mTextViewContractPeroid.setText("Contract : " + buisnessUserPlaceOrder.getDeals().get(0).getContractPeriod().toString() + " months");
        }
        if (mCustomActivity) {
            mTextViewEffectivePrice.setText("$" + EffectivePrice);
        } else {
            if (buisnessUserPlaceOrder.getDeals().get(0).getEffective_price().toString().equalsIgnoreCase("0.00")) {
                mTextViewEffectivePrice.setText("$" + buisnessUserPlaceOrder.getDeals().get(0).getPrice().toString());
            } else {
                mTextViewEffectivePrice.setText("$" + buisnessUserPlaceOrder.getDeals().get(0).getEffective_price().toString());
            }
        }


        mSpinnerEquiments.setOnItemSelectedListener(OnCatSpinnerCLCellphone);
      /*  int index = Arrays.asList(getResources().getStringArray(R.array.primary_id)).indexOf(buisnessUserPlaceOrder.getAppUsers().get.getTrendingDealFrequency());
        mSpinnerPrimary.setSelection(index);*/
        if (!buisnessUserPlaceOrder.getAppUsers().getPrimary_id().equalsIgnoreCase("")) {
            try {
                int index = primaryAndSecondaryId.getPrimaryIds().indexOf(buisnessUserPlaceOrder.getAppUsers().getPrimary_id());
                mSpinnerPrimary.setSelection(index);
                int index1 = primaryAndSecondaryId.getSecondaryIds().indexOf(buisnessUserPlaceOrder.getAppUsers().getSecondary_id());
                mSpinnerSecondary.setSelection(index1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        mEditTextMobileNo.setText(Jsondata.decryptMsg(buisnessUserPlaceOrder.getAppUsers().getMobile()));
        mEditTextFirstName.setText(Jsondata.decryptMsg(buisnessUserPlaceOrder.getAppUsers().getFirstName()));
        mEditTextLastName.setText(Jsondata.decryptMsg(buisnessUserPlaceOrder.getAppUsers().getLastName()));
        mEditTextEamil.setText(buisnessUserPlaceOrder.getAppUsers().getEmail());
        if (buisnessUserPlaceOrder.getAppUsers().getPrimary_id_number().contains("===")) {
            try {
                String[] separated = buisnessUserPlaceOrder.getAppUsers().getPrimary_id_number().split("===");
                mSpinnerPrimaryState.setSelection(state.getStates().indexOf(separated[0]));
                mEditTextPrimaryId.setText(separated[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            mEditTextPrimaryId.setText(buisnessUserPlaceOrder.getAppUsers().getPrimary_id_number());
        }

        if (buisnessUserPlaceOrder.getAppUsers().getSecondary_id_number().contains("===")) {
            try {
                String[] separated = buisnessUserPlaceOrder.getAppUsers().getSecondary_id_number().split("===");
                mSpinnerSecondaryState.setSelection(state.getStates().indexOf(separated[0]));
                mEditTextSecondaryId.setText(separated[1]);
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            mEditTextSecondaryId.setText(buisnessUserPlaceOrder.getAppUsers().getSecondary_id_number());
        }

        if (buisnessUserPlaceOrder.getBusiness() != null) {
            mEditTextBusinessName.setText(Jsondata.decryptMsg(buisnessUserPlaceOrder.getBusiness().getBusinessName()));
            mEditTextManagerName.setText(buisnessUserPlaceOrder.getBusiness().getManagerName());
            mEditTextDBNumber.setText(Jsondata.decryptMsg(buisnessUserPlaceOrder.getBusiness().getDbNumber()));
            if (Jsondata.decryptMsg(buisnessUserPlaceOrder.getBusiness().getFederalNumber()).length() > 5) {
                mEditTextFederalNumber.setText(Jsondata.decryptMsg(buisnessUserPlaceOrder.getBusiness().getFederalNumber()));
                mEditTextFederalNumber.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                mEditTextFederalNumber.setClickable(false);
                mEditTextFederalNumber.setFocusable(false);
            }
            if (Jsondata.decryptMsg(buisnessUserPlaceOrder.getBusiness().getSsn()).length() > 5) {
                mEditTextSSN.setText(Jsondata.decryptMsg(buisnessUserPlaceOrder.getBusiness().getSsn()));
                mEditTextSSN.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                mEditTextSSN.setClickable(false);
                mEditTextSSN.setFocusable(false);
            }

            mEditTextBusinessDobNumber.setText(Jsondata.decryptMsg(buisnessUserPlaceOrder.getBusiness().getDob()));
            mEditTextDBANumber.setText(Jsondata.decryptMsg(buisnessUserPlaceOrder.getBusiness().getBusinessDba()));
            mEditTextBuisnessMobileNumber.setText(Jsondata.decryptMsg(buisnessUserPlaceOrder.getBusiness().getContactNumber()));
            mRadioButtonSole.setEnabled(false);
            mRadioButtonRegistred.setEnabled(false);
            if (buisnessUserPlaceOrder.getBusiness().getBusinessType() == 0) {
                mRadioButtonSole.setChecked(true);
                mLinearLayoutSole.setVisibility(View.VISIBLE);
                mLinearLayoutREgisterd.setVisibility(View.GONE);
            } else {
                mRadioButtonRegistred.setChecked(true);
                mLinearLayoutREgisterd.setVisibility(View.VISIBLE);
                mLinearLayoutSole.setVisibility(View.GONE);
            }
        }

        for (int i = 0; buisnessUserPlaceOrder.getBusinessAddresses().size() > i; i++) {
            if (buisnessUserPlaceOrder.getBusinessAddresses().get(i).getAddressType() == 2) {
                int index = state.getStates().indexOf(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getState());
                mSpinnerBillingState.setSelection(index);
                mEditTextBillingAddress1.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getAddress1());
                mEditTextBillingAddress2.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getAddress2());
                mEditTextTownCity.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getCity());
                mEditTextBillingAddressName.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getAddressName());
                mEditTextBillingZipcode.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getZip());
            } else if (buisnessUserPlaceOrder.getBusinessAddresses().get(i).getAddressType() == 1) {
                int index = state.getStates().indexOf(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getState());
                mSpinnerShipingState.setSelection(index);
                mEditTextShipingZipcode.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getZip());
                mEditTextShipingAddress1.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getAddress1());
                mEditTextShipingAddress2.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getAddress2());
                mEditTextShipingTown.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getCity());
                mEditTextShipingAddressName.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getAddressName());
            } else if (buisnessUserPlaceOrder.getBusinessAddresses().get(i).getAddressType() == 0) {
                int index = state.getStates().indexOf(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getState());
                mSpinnerServiceState.setSelection(index);
                mEditTextServiceZipCode.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getZip());
                mEditTextServiceAddress1.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getAddress1());
                mEditTextServiceAddress2.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getAddress2());
                mEditTextServiceCity.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getCity());
                mEditTextServiceAddressName.setText(buisnessUserPlaceOrder.getBusinessAddresses().get(i).getAddressName());
            }


        }
        if (!Validation.isPasswordMatch(mEditTextBillingAddress1, mEditTextShipingAddress1)) {
            if (!mEditTextBillingAddress1.getText().toString().equalsIgnoreCase("")) {
                mCheckBoxShiping.setChecked(true);
                mLinearLayoutShipingAddress.setVisibility(View.GONE);
            }
        }
        if (!Validation.isPasswordMatch(mEditTextBillingAddress1, mEditTextServiceAddress1)) {
            if (!mEditTextBillingAddress1.getText().toString().equalsIgnoreCase("")) {
                mCheckBoxService.setChecked(true);
                mLinearLayoutServiceAddress.setVisibility(View.GONE);
            }
        }

        /*if (DealListFragment.dealListItem.getDeal().get(getIntent().getIntExtra("deal_position", 0))..toString().equalsIgnoreCase("")) {
            mLinearLayoutllBuisnessInformation.setVisibility(View.GONE);
        }*/
      /*  if (DealListFragment.dealListItem.getDeal().get(getIntent().getIntExtra("deal_position", 0)).getAverage_rating().toString().equalsIgnoreCase("")) {
            mRatingBar.setRating((float) 0.00);
        } else {
            float f1 = Float.parseFloat(DealListFragment.dealListItem.getDeal().get(getIntent().getIntExtra("deal_position", 0)).getAverage_rating().toString());
            mRatingBar.setRating(f1);
        }
        Picasso.with(this)
                .load(DealListFragment.dealListItem.getDeal().get(getIntent().getIntExtra("deal_position", 0)).getDeal_image_url().toString()).placeholder(R.drawable.progress_animation)
                .into(mImageViewDealImage);
        mTextViewPrice.setText("$" + DealListFragment.dealListItem.getDeal().get(getIntent().getIntExtra("deal_position", 0)).getDeal_price().toString());
        mTextViewDealTitle.setText(DealListFragment.dealListItem.getDeal().get(getIntent().getIntExtra("deal_position", 0)).getTitle().toString());
        *//*if (DealListFragment.dealListItem.getDeal().get(getIntent().getIntExtra("deal_position", 0)).getEffective_price().toString().equalsIgnoreCase("0")) {
            mTextViewPrice.setText("$" + DealListFragment.dealListItem.getDeal().get(getIntent().getIntExtra("deal_position", 0)).getDeal_price().toString());
        } else {
            mTextViewPrice.setText("$" + DealListFragment.dealListItem.getDeal().get(getIntent().getIntExtra("deal_position", 0)).getEffective_price().toString());
        }*/

    }

    private void GetCellPhoneData() {

        SharedPreferences sp = getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        mEquipementCellPhoneDetailId = sp.getString("EquipementCellphoneDetailId", null);
        EffectivePrice = sp.getString("value", null);
        ExtraServiceId = sp.getInt("extraserviceId", 0);
        ExtraServiceName = sp.getString("ExtraserviceExtraServiceName", null);
        PlanName = sp.getString("PlanName1", null);
        ExtraServicePrice = sp.getString("ExtraServicePrice", "");
        PlanRefferId = sp.getInt("PlanId1", 0);
        PlanTitle = sp.getInt("planTitle1", 0);
        PlanReferPrice = sp.getString("planPrice1", null);
        PlanType = String.valueOf(PlanTitle);
        mPlanRefferId = String.valueOf(PlanRefferId);
        extraserviceId = String.valueOf(ExtraServiceId);
    }

    private void GetCableData() {
        SharedPreferences sp = getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        EffectivePrice = sp.getString("value", null);
        mChannelpremiumprice = sp.getString("ChalnePremiumPrice", null);
        mChalnePremiumID = sp.getString("ChalnePremiumID", null);
        ChalnePremiumName = sp.getString("ChalnePremiumName", null);
        mChalneEquipmentID = sp.getString("ChalneEquipmentID", null);
        ChalneEquipmentName = sp.getString("ChalneEquipmentName", null);
        ChalneEquipmentPrice = sp.getString("ChalneEquipmentPrice", null);
        TvAdapterChannelName = sp.getString("TvAdapterChannelName", null);
        TvAdapterPrice = sp.getString("TvAdapterPrice", null);
        TvAdapterId = sp.getString("TvAdapterId", null);
    }

    private void SetData() {
        mLinearCableMain = (LinearLayout) findViewById(R.id.order_cablesumerylist);
        mLinearCablechooseYourCustom = (LinearLayout) findViewById(R.id.l1sumery_ordercableAttributes);
        mLinearPremiumChannel = (LinearLayout) findViewById(R.id.l1sumery_ordersubscription1);
        mLinearChannelEquipement = (RelativeLayout) findViewById(R.id.rl_cableadditionalattributes);
        mTextCableTVChanelName = (TextView) findViewById(R.id.dealattributescable_planname);
        mTextCableTVChanelPrice = (TextView) findViewById(R.id.dealattributescable_price);
        mTextCableChanelChanelName = (TextView) findViewById(R.id.dealsubscription_cableplanname);
        mTextCableChanelChanePrice = (TextView) findViewById(R.id.dealsubscription_cableprice);
        mTextCableEquipementName = (TextView) findViewById(R.id.dealsubscription_equipementcableplanname);
        mTextCableEquipementPrice = (TextView) findViewById(R.id.dealsubscription_equipementcableprice);

        if (ChalneEquipmentPrice.equalsIgnoreCase("") && mChannelpremiumprice.equalsIgnoreCase("") && TvAdapterPrice.equalsIgnoreCase("")) {
            mEquipementsCableOrderSumery.setVisibility(View.GONE);
        } else {
            mEquipementsCableOrderSumery.setVisibility(View.VISIBLE);
        }

        if (TvAdapterPrice.equalsIgnoreCase("")) {
            mLinearCablechooseYourCustom.setVisibility(View.GONE);

        } else {
            mTextCableTVChanelName.setText(TvAdapterChannelName);
            mTextCableTVChanelPrice.setText("$" + TvAdapterPrice);
        }
        if (mChannelpremiumprice.equalsIgnoreCase("")) {
            mLinearPremiumChannel.setVisibility(View.GONE);
        } else {
            mTextCableChanelChanelName.setText(ChalnePremiumName);
            mTextCableChanelChanePrice.setText("$" + mChannelpremiumprice);
        }
        if (ChalneEquipmentPrice.equalsIgnoreCase("")) {
            mLinearChannelEquipement.setVisibility(View.GONE);
        } else {
            mTextCableEquipementName.setText(ChalneEquipmentName);
            mTextCableEquipementPrice.setText("$" + ChalneEquipmentPrice);
        }

    }

//    private void GetEquipementCellPhoneData(){
//        for ( HashMap<String, String> mHashMap : mEquipemenmapList)
//            for (Map.Entry<String, String> mapEntry : mHashMap.entrySet())
//            {
//                 key = mapEntry.getKey();
//                 value = mapEntry.getValue();
//            }
//    }


    private void GetTextData() {

        if (mRadioButtonSole.isChecked()) {
            buisness_type = "0";
            federal_number = "";
            DOB = mEditTextBusinessDobNumber.getText().toString();
            ssn_number = mEditTextSSN.getText().toString();
            managername = "";
            dbumber = "";
            DBA = "";
            db_number = "";
        } else {
            managername = mEditTextManagerName.getText().toString();
            dbumber = mEditTextDBNumber.getText().toString();
            DBA = mEditTextDBANumber.getText().toString();
            db_number = mEditTextDBNumber.getText().toString();
            DOB = "";
            federal_number = mEditTextFederalNumber.getText().toString();
            ssn_number = "";
            buisness_type = "1";
        }
        if (mCheckBoxShiping.isChecked()) {
            is_shipping_address_same = "1";
        } else {
            is_shipping_address_same = "0";
        }
        if (mCheckBoxService.isChecked()) {
            is_service_address_same = "1";
        } else {
            is_service_address_same = "0";
        }
        billingaddressname = mEditTextBillingAddressName.getText().toString();
        shipingaddressname = mEditTextShipingAddressName.getText().toString();
        serviceaddressname = mEditTextServiceAddressName.getText().toString();
        primary_id = mSpinnerPrimary.getSelectedItem().toString();
        secondary_id = mSpinnerSecondary.getSelectedItem().toString();
        if (primary_id.contains("Driving")) {
            primary_id_number = mSpinnerPrimaryState.getSelectedItem().toString() + "===" + mEditTextPrimaryId.getText().toString();
        } else {
            primary_id_number = mEditTextPrimaryId.getText().toString();
        }
        if (secondary_id.contains("Driving")) {
            secondary_id_number = mSpinnerSecondaryState.getSelectedItem().toString() + "===" + mEditTextSecondaryId.getText().toString();
        } else {
            secondary_id_number = mEditTextSecondaryId.getText().toString();
        }

        service_address1 = mEditTextServiceAddress1.getText().toString();
        service_address2 = mEditTextServiceAddress2.getText().toString();

        service_city = mEditTextServiceCity.getText().toString();
        service_zipcode = mEditTextServiceZipCode.getText().toString();
        freetext = mFreeTextEdit.getText().toString();
        SharedPreferences sp = getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("FreeText", "" + freetext);
        editor.commit();

        lastname = mEditTextLastName.getText().toString();


        billingState = mSpinnerBillingState.getSelectedItem().toString();
        serviceState = mSpinnerServiceState.getSelectedItem().toString();
        shipingState = mSpinnerShipingState.getSelectedItem().toString();

        name = mEditTextFirstName.getText().toString();

        email = mSharedPreferences.getString(Constant.USER_EMAIL, "");//mEditTextEamil.getText().toString();
        billing_address1 = mEditTextBillingAddress1.getText().toString();
        billing_address2 = mEditTextBillingAddress2.getText().toString();
        billing_country = mEditTextTownCity.getText().toString();
        billing_zipcode = mEditTextBillingZipcode.getText().toString();
        shiping_address1 = mEditTextShipingAddress1.getText().toString();
        shiping_address2 = mEditTextShipingAddress2.getText().toString();
        shiping_country = mEditTextShipingTown.getText().toString();
        shiping_zipcode = mEditTextShipingZipcode.getText().toString();
        mobile_no = mEditTextMobileNo.getText().toString();
        bisiness_name = mEditTextBusinessName.getText().toString();


        buisness_mobile_number = mEditTextBuisnessMobileNumber.getText().toString();


        deal_id = "" + getIntent().getIntExtra("dealid", 0);

        mEditor.putString("mEditTextFirstName", mEditTextFirstName.getText().toString());
        //  mEditor.putString("mEditTextEamil", mEditTextEamil.getText().toString());
        mEditor.putString("mEditTextBillingAddress1", mEditTextBillingAddress1.getText().toString());
        mEditor.putString("mEditTextBillingAddress2", mEditTextBillingAddress2.getText().toString());
        mEditor.putString("mEditTextTownCity", mEditTextTownCity.getText().toString());
        mEditor.putString("mEditTextBillingZipcode", mEditTextBillingZipcode.getText().toString());
        mEditor.putString("mEditTextShipingAddress1", mEditTextShipingAddress1.getText().toString());
        mEditor.putString("mEditTextShipingAddress2", mEditTextShipingAddress2.getText().toString());
        mEditor.putString("mEditTextShipingTown", mEditTextShipingTown.getText().toString());
        mEditor.putString("mEditTextShipingZipcode", mEditTextShipingZipcode.getText().toString());
        mEditor.putString("mEditTextMobileNo", mEditTextMobileNo.getText().toString());
        mEditor.commit();
    }

    private void setSpinnerData() {

        mSpinnerPrimary.setOnItemSelectedListener(OnCatSpinnerCL);
        mSpinnerSecondary.setOnItemSelectedListener(OnCatSpinnerCL1);
        mSpinnerBillingState.setOnItemSelectedListener(OnCatSpinnerCL);
        mSpinnerShipingState.setOnItemSelectedListener(OnCatSpinnerCL);
        mSpinnerServiceState.setOnItemSelectedListener(OnCatSpinnerCL);
        mSpinnerPrimaryState.setOnItemSelectedListener(OnCatSpinnerCL);
        mSpinnerSecondaryState.setOnItemSelectedListener(OnCatSpinnerCL);
    }

    private AdapterView.OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            try {
                if (mSpinnerPrimary.getSelectedItem().toString().contains("Driving")) {
                    mLinearLayoutPriamry.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayoutPriamry.setVisibility(View.GONE);

                }

                double Effective_PriceTotal = 0.0;
                if (cellphoneEquipments.getEquipments() != null && cellphoneEquipments.getEquipments().size() > 0) {
                    if (mSharedPreferences.getString("user_type", "").equalsIgnoreCase("residence")) {
                        if (residenceUserPlaceOrder.getDeals() != null) {
                            if (residenceUserPlaceOrder.getDeals().get(0).getEffective_price().toString().equalsIgnoreCase("0.00")) {
                                Effective_PriceTotal = residenceUserPlaceOrder.getDeals().get(0).getPrice() + Float.parseFloat(cellphoneEquipments.getEquipments().get(pos).getPrice());
                            } else {
                                Effective_PriceTotal = residenceUserPlaceOrder.getDeals().get(0).getEffective_price() + Float.parseFloat(cellphoneEquipments.getEquipments().get(pos).getPrice());

                            }
                        }
                    } else {
                        if (buisnessUserPlaceOrder.getDeals() != null) {
                            if (buisnessUserPlaceOrder.getDeals().get(0).getEffective_price().toString().equalsIgnoreCase("0.00")) {
                                Effective_PriceTotal = buisnessUserPlaceOrder.getDeals().get(0).getPrice() + Float.parseFloat(cellphoneEquipments.getEquipments().get(pos).getPrice());
                            } else {
                                Effective_PriceTotal = buisnessUserPlaceOrder.getDeals().get(0).getEffective_price() + Float.parseFloat(cellphoneEquipments.getEquipments().get(pos).getPrice());
                            }
                        }
                    }
                    if (!mCustomActivity) {
                        mTextViewEffectivePrice.setText("$" + Effective_PriceTotal);
                        EffectivePrice = "" + Effective_PriceTotal;

                    }
                }
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                ((TextView) parent.getChildAt(0)).setTextSize(15);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private AdapterView.OnItemSelectedListener OnCatSpinnerCLCellphone = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            try {

                double Effective_PriceTotal = 0.0;
                if (cellphoneEquipments.getEquipments() != null && cellphoneEquipments.getEquipments().size() > 0) {
                    if (mSharedPreferences.getString("user_type", "").equalsIgnoreCase("residence")) {
                        if (residenceUserPlaceOrder.getDeals().get(0).getEffective_price().toString().equalsIgnoreCase("0.00")) {
                            Effective_PriceTotal = residenceUserPlaceOrder.getDeals().get(0).getPrice() + Float.parseFloat(cellphoneEquipments.getEquipments().get(pos).getPrice());
                        } else {
                            Effective_PriceTotal = residenceUserPlaceOrder.getDeals().get(0).getEffective_price() + Float.parseFloat(cellphoneEquipments.getEquipments().get(pos).getPrice());

                        }
                    } else {
                        if (buisnessUserPlaceOrder.getDeals().get(0).getEffective_price().toString().equalsIgnoreCase("0.00")) {
                            Effective_PriceTotal = buisnessUserPlaceOrder.getDeals().get(0).getPrice() + Float.parseFloat(cellphoneEquipments.getEquipments().get(pos).getPrice());
                        } else {
                            Effective_PriceTotal = buisnessUserPlaceOrder.getDeals().get(0).getEffective_price() + Float.parseFloat(cellphoneEquipments.getEquipments().get(pos).getPrice());
                        }
                    }
                    Effective_PriceTotal = Effective_PriceTotal - Float.parseFloat(cellphoneEquipments.getEquipments().get(0).getPrice());
                    if (!mCustomActivity) {
                        EffectivePrice = "" + Effective_PriceTotal;
                        mTextViewEffectivePrice.setText("$" + EffectivePrice);
                    }
                }
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                ((TextView) parent.getChildAt(0)).setTextSize(15);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    private AdapterView.OnItemSelectedListener OnCatSpinnerCL1 = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            try {

                if (mSpinnerSecondary.getSelectedItem().toString().contains("Driving")) {
                    mLinearLayoutSecondary.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayoutSecondary.setVisibility(View.GONE);
                }
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                ((TextView) parent.getChildAt(0)).setTextSize(15);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
    public void showForgotPasswordDialog() {
        final CustomizeDialog mCustomizeDialog = new CustomizeDialog(this);
        mCustomizeDialog.setCancelable(false);
        mCustomizeDialog.setContentView(R.layout.invalid_federal_id_dialog);
        TextView textViewMessage = (TextView) mCustomizeDialog.findViewById(R.id.tv_message);
        textViewMessage.setText(InvalidFederalMessage);
        ImageButton imagebtn_Cancel = (ImageButton) mCustomizeDialog.findViewById(R.id.imgbtn_cancel);
        imagebtn_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCustomizeDialog.dismiss();
            }
        });
        mCustomizeDialog.show();
    }
}