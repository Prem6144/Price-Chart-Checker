package com.spa.servicedealz.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.localytics.android.Localytics;
import com.spa.adapter.OrderDealDetailListAdapter;
import com.spa.fragment.ShowDailog;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.orderdealdetail.OrderEquipment;
import com.spa.model.orderdetail.OrderDetail;
import com.spa.servicedealz.R;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;
import com.spa.utils.PaddingItemDecoration;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diwakar on 6/11/2016.
 */
public class OrderDealDetailActivity extends AppCompatActivity implements View.OnClickListener, NetworkUtil.changeNetworkInterFace {
    private Toolbar mToolbar;
    private TextView mTextViewOrderId, mTextViewOrderDateTime, mTextViewOrderDealTitle, mTextViewOrderActivationStatus, mTextViewOrderDealPrice, mTextViewOrderDealEffectivePrice,
            mTextViewProviderName, mTextViewPrividerOrderId, mTextViewOrderStatus, mTextViewUserType, mTextViewEquipmentTitle, mTextViewEquipmentFee, mTextViewEquipmentInstallation, mTextViewYouSavePrice, mTextViewYouSave, mTextViewBillingName, mTextViewShipingName, mTextViewBillingAddress, mTextViewServiceAddress,
            mTextViewShipingAddress, mTextViewApplicantName, mTextViewApplicantAddress, mTextViewManagerName, mTextViewManagerMobileNo, mTextViewLegelName,
            mTextViewLegelDeatail, mTextViewBusinessType, mTextViewShortDesc, mFreetextShow, mOrderCustoTvName, mOrderCustoTvPrice, mOrderPremiumChannelNamee, mOrderPremiumChannelPrice, mOrderEquipementChanelName, mOrderEquipementChanelPrice, mOrderPlanName, mOrderPlanPrice, mOrderExtraServiceName, mOrderExtraPrice;
    private ImageView mImageViewOrderDeal;
    private LinearLayout mLinearLayoutLegelOrder, mLinearMainEqupements, mLinearLayoutEquipments, mLinearLayoutAttributesDetail, mLinearLayoutExtraServiceDetailHeading, mLinearLayoutAttributesDetailHeding, mLinearLayoutExtraServiceDetail, mLinearCablechooseYourCustom, mLinearPremiumChannel;
    private RelativeLayout mLinearLayoutEquipementsDetail, mLinearCableChanelEquipement, mLinearChannelEquipement;
    String mChannelpremiumprice, ChalnePremiumName, ChalneEquipmentName, mChalnePremiumID, mChalneEquipmentID, ChalneEquipmentPrice, TvAdapterPrice, TvAdapterId, TvAdapterChannelName;
    private SharedPreferences mSharedPreferences;
    private OrderDetail orderDetail;
    private LinearLayout mLinearLayoutTelecomManager;
    private View mViewDivider;
    String mFreeTextString,orderstatus,providernumber;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    LinearLayout mLinearCableMain, mLinearCableChooseYourCustomTvPlan, mLinearCablePremiumChanel;
    PaddingItemDecoration addItemDecoration;

    List<OrderEquipment> orderEquipments = new ArrayList<>();

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_details);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        setActionbar();
        mapingId();
        /// setDataInView();
        mSharedPreferences = getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        new HttpGetAsyncTask().execute();

    }

    private void setDataInView() {
        try {

            String usertype = orderDetail.getOrderItems().get(0).getDeal().getDealType();
            if (!usertype.equalsIgnoreCase("residence")) {
                if (orderDetail.getBusiness().getBusinessType() == 0) {
                    mTextViewBusinessType.setText("Sole Proprietor");
                    mTextViewLegelDeatail.setText(Html.fromHtml("<font color=#919090>DOB: </font>" + Jsondata.decryptMsg(orderDetail.getBusiness().getDob()) +
                            "<br><font color=#919090>SSN Number: </font>" + Jsondata.decryptMsg(orderDetail.getBusiness().getSsn())));
                } else {
                    mTextViewLegelDeatail.setText(Html.fromHtml("<font color=#919090>DBA: </font>" + Jsondata.decryptMsg(orderDetail.getBusiness().getBusinessDba()) + "\n" +
                            "<br><font color=#919090>Federal Tax id: </font>" + Jsondata.decryptMsg(orderDetail.getBusiness().getFederalNumber()) + "<br><font color=#919090>Dan & Bradstreet Number: </font>" + Jsondata.decryptMsg(orderDetail.getBusiness().getDbNumber())));

                }
                mTextViewManagerName.setText(Html.fromHtml("<font color=#919090>Name: </font>" + orderDetail.getBusiness().getManagerName()));
                mTextViewManagerMobileNo.setText(Html.fromHtml("<font color=#919090>Contact No: </font>" + Jsondata.decryptMsg(orderDetail.getBusiness().getManagerContact())));
                mTextViewLegelName.setText(Html.fromHtml("<font color=#919090>Business Name: </font>" + Jsondata.decryptMsg(orderDetail.getBusiness().getBusinessName())));
            } else {
                mLinearLayoutTelecomManager.setVisibility(View.GONE);
                mLinearLayoutLegelOrder.setVisibility(View.GONE);
            }
            mTextViewShortDesc.setText(orderDetail.getOrderItems().get(0).getDeal().getShortDescription().toString());
            mTextViewOrderId.setText(orderDetail.getOrder().getOrderId());
            if (orderDetail.getOrderItems().get(0).getEffectivePrice().toString().equalsIgnoreCase("0.0")) {
                mTextViewOrderDealEffectivePrice.setText(("$" + String.format("%.2f", orderDetail.getOrderItems().get(0).getDealPrice())));
            } else {
                if (orderDetail.getOrderItems().get(0).getEffectivePrice().toString().equalsIgnoreCase("0")) {
                    mTextViewOrderDealEffectivePrice.setText(("$" + String.format("%.2f", orderDetail.getOrderItems().get(0).getDealPrice())));
                } else {
                    mTextViewOrderDealEffectivePrice.setText(("$" + String.format("%.2f", orderDetail.getOrderItems().get(0).getEffectivePrice())));
                }
            }

            mTextViewOrderDateTime.setText(orderDetail.getOrder().getActivationDate());
            mTextViewOrderDealTitle.setText(orderDetail.getOrderItems().get(0).getDeal().getTitle().toString());
            mTextViewOrderDealPrice.setText("$" + String.format("%.2f", orderDetail.getOrderItems().get(0).getDealPrice()));
            mTextViewOrderActivationStatus.setText(orderDetail.getOrder().getStatus().toString());
            Picasso.with(this)
                    .load(orderDetail.getOrderItems().get(0).getDeal().getDealImageUrl().toString()).placeholder(R.drawable.progress_animation)
                    .into(mImageViewOrderDeal);

            if (orderDetail.getOrder().getProviderStatus() !=null || orderDetail.getOrder().getProviderStatus().equalsIgnoreCase("")) {
                 orderstatus  = orderDetail.getOrder().getProviderStatus();
            }
            if (orderDetail.getOrder().getProviderOrderNumber() !=null){
                 providernumber =""+orderDetail.getOrder().getProviderOrderNumber();
            }
//            AT&T Order No:
            mTextViewProviderName.setText(" "+"");
            mTextViewPrividerOrderId.setText("");
            mTextViewOrderStatus.setText(orderstatus);
            mTextViewUserType.setText("");

            if (orderDetail.getOrderItems().get(0).getDeal().getDealEquipments().size() > 0) {
                mTextViewEquipmentTitle.setText(orderDetail.getOrderItems().get(0).getDeal().getDealEquipments().get(0).getMake());
                mTextViewEquipmentFee.setText("$" + orderDetail.getOrderItems().get(0).getDeal().getDealEquipments().get(0).getPrice());
                mTextViewEquipmentInstallation.setText(orderDetail.getOrderItems().get(0).getDeal().getDealEquipments().get(0).getInstallation());
            } else {
                mLinearLayoutEquipments.setVisibility(View.GONE);
            }
            mTextViewYouSavePrice.setText("$44.95");
             /*sttttttttttttttaaaaaaaaaaaaaaarrrrrrrrrrrrrrrrrttttttttttttt*/

         /*   if (orderDetail.getOrderEquipments().size() > 0) {
                for (int i=0;i <orderDetail.getOrderEquipments().size();i++){
                    mOrderEquipementCellName.setText(orderDetail.getOrderEquipments().get(i).getName());
                    mOrderEquipementBrandName.setText(orderDetail.getOrderEquipments().get(i).getBrand());
//                  mOrderEquipementQuantity.setText(orderDetail.getOrderItems().get(0).getDeal().getDealEquipments().get(0).get);
                    mOrderEquipementPrice.setText(orderDetail.getOrderEquipments().get(i).getPrice());
                    mOrderEquipementColorList.setText(orderDetail.getOrderEquipments().get(i).getColorName());


                }


            } else {
                mLinearLayoutEquipementsDetail.setVisibility(View.GONE);
            }*/
//
            if (orderDetail.getOrderEquipments().size() == 0) {
                mLinearLayoutEquipementsDetail.setVisibility(View.GONE);
            } else {
                mLinearLayoutEquipementsDetail.setVisibility(View.VISIBLE);
            }

            if (orderDetail.getOrderAttributes().getPrice() != null && orderDetail.getOrderAttributes().getTitle() != null) {
                mLinearLayoutAttributesDetailHeding.setVisibility(View.VISIBLE);
                mLinearLayoutAttributesDetail.setVisibility(View.VISIBLE);
                mOrderPlanName.setText(orderDetail.getOrderAttributes().getTitle());
                mOrderPlanPrice.setText("$" + orderDetail.getOrderAttributes().getPrice());
            } else {
                mLinearLayoutAttributesDetail.setVisibility(View.GONE);
                mLinearLayoutAttributesDetailHeding.setVisibility(View.GONE);
            }
            if (orderDetail.getOrderExtraServices().getPrice() != null && orderDetail.getOrderExtraServices().getTitle() != null) {
                mLinearLayoutExtraServiceDetail.setVisibility(View.VISIBLE);
                mLinearLayoutExtraServiceDetailHeading.setVisibility(View.VISIBLE);
                mOrderExtraServiceName.setText(orderDetail.getOrderExtraServices().getTitle());
                mOrderExtraPrice.setText("$" + orderDetail.getOrderExtraServices().getPrice());
            } else {
                mLinearLayoutExtraServiceDetail.setVisibility(View.GONE);
                mLinearLayoutExtraServiceDetailHeading.setVisibility(View.GONE);
            }

            if (orderDetail.getOrderItems().get(0).getDeal().getServiceCategoryId() == 4) {
                mLinearCableMain.setVisibility(View.GONE);
                mLinearMainEqupements.setVisibility(View.VISIBLE);

            } else if (orderDetail.getOrderItems().get(0).getDeal().getServiceCategoryId() == 3) {
                mLinearMainEqupements.setVisibility(View.GONE);
                mLinearCableMain.setVisibility(View.VISIBLE);
            } else {
                mLinearMainEqupements.setVisibility(View.GONE);
                mLinearCableMain.setVisibility(View.GONE);
            }

            //  mTextViewYouSave.setText("You save ");
     /*   ## Business address type constants
        BRANCH_ADDRESS = 0
        SHIPPING_ADDRESS = 1
        BILLING_ADDRESS = 2
        BUSINESS_ADDRESS = 3
        HOME_ADDRESS = 4*/
            mFreeTextString = orderDetail.getOrder().getFreeText();
            mFreetextShow.setText(mFreeTextString);
            mFreetextShow.setText(orderDetail.getOrder().getFreeText());
            mTextViewBillingName.setVisibility(View.GONE);//setText("Spa Systems");
            mTextViewShipingName.setVisibility(View.GONE);
            String primaryid = "", secondarynumber = "";
            if (orderDetail.getAppUser().getPrimary_id_number().contains("===")) {
                try {
                    String[] separated = orderDetail.getAppUser().getPrimary_id_number().split("===");
                    primaryid = separated[1] + "- " + separated[0];
                } catch (Exception e) {
                }

            } else {
                primaryid = orderDetail.getAppUser().getPrimary_id_number();
            }

            if (orderDetail.getAppUser().getSecondary_id_number().contains("===")) {
                try {
                    String[] separated = orderDetail.getAppUser().getSecondary_id_number().split("===");
                    secondarynumber = separated[1] + "- " + separated[0];
                } catch (Exception e) {
                }

            } else {
                secondarynumber = orderDetail.getAppUser().getSecondary_id_number();
            }

            mTextViewApplicantName.setText(Html.fromHtml("<font color=#919090>Name: </font>" + Jsondata.decryptMsg(orderDetail.getAppUser().getFirstName()) + " " + Jsondata.decryptMsg(orderDetail.getAppUser().getLastName())));
            mTextViewApplicantAddress.setText(Html.fromHtml("<font color=#919090>Contact No: </font>" + Jsondata.decryptMsg(orderDetail.getAppUser().getMobile()) + "<br>" + "<font color=#919090>Email: </font>" + orderDetail.getAppUser().getEmail() +
                    "<br><font color=#919090>Primary id: </font>" + orderDetail.getAppUser().getPrimaryId() + " -<font color=#6F98AD>" + primaryid + "</font>" + "<br><font color=#919090>Secondary id: </font>" + orderDetail.getAppUser().getSecondaryId() + " -<font color=#6F98AD>" + secondarynumber + "</font>"));
            for (int i = 0; orderDetail.getOrderAddresses().size() > i; i++) {
                if (orderDetail.getOrderAddresses().get(i).getAddressType() == 2) {
                    mTextViewBillingAddress.setText(orderDetail.getOrderAddresses().get(i).getAddress1() + " " + orderDetail.getOrderAddresses().get(i).getAddress2() + "\n" +
                            orderDetail.getOrderAddresses().get(i).getCity() + "," + orderDetail.getOrderAddresses().get(i).getState() + "," + orderDetail.getOrderAddresses().get(i).getZip());

                } else if (orderDetail.getOrderAddresses().get(i).getAddressType() == 1) {
                    if (orderDetail.getAppUser().getIsShippingAddressSame().equalsIgnoreCase("true")) {
                        mTextViewShipingAddress.setText(orderDetail.getOrderAddresses().get(i).getAddress1() + " " + orderDetail.getOrderAddresses().get(i).getAddress2() + "\n" +
                                orderDetail.getOrderAddresses().get(i).getCity() + "," + orderDetail.getOrderAddresses().get(i).getState() + "," + orderDetail.getOrderAddresses().get(i).getZip());
                    } else {
//                        mTextViewShipingAddress.setText("Same as Billing Address");
                        mTextViewShipingAddress.setText(orderDetail.getOrderAddresses().get(i).getAddress1() + " " + orderDetail.getOrderAddresses().get(i).getAddress2() + "\n" +
                                orderDetail.getOrderAddresses().get(i).getCity() + "," + orderDetail.getOrderAddresses().get(i).getState() + "," + orderDetail.getOrderAddresses().get(i).getZip());

                    }
                } else if (orderDetail.getOrderAddresses().get(i).getAddressType() == 0) {
                    if (orderDetail.getAppUser().getIsServiceAddressSame().equalsIgnoreCase("true")) {
                        mTextViewServiceAddress.setText(orderDetail.getOrderAddresses().get(i).getAddress1() + " " + orderDetail.getOrderAddresses().get(i).getAddress2() + "\n" +
                                orderDetail.getOrderAddresses().get(i).getCity() + "," + orderDetail.getOrderAddresses().get(i).getState() + "," + orderDetail.getOrderAddresses().get(i).getZip());
                    } else {
                       // mTextViewServiceAddress.setText("Same as Billing Address");
                        mTextViewServiceAddress.setText(orderDetail.getOrderAddresses().get(i).getAddress1() + " " + orderDetail.getOrderAddresses().get(i).getAddress2() + "\n" +
                                orderDetail.getOrderAddresses().get(i).getCity() + "," + orderDetail.getOrderAddresses().get(i).getState() + "," + orderDetail.getOrderAddresses().get(i).getZip());

                    }

                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mapingId() {
        mLinearLayoutTelecomManager = (LinearLayout) findViewById(R.id.ll_telecom_manager);
        mViewDivider = (View) findViewById(R.id.tdevider);

        mTextViewServiceAddress = (TextView) findViewById(R.id.txt_service_address);
        mTextViewBusinessType = (TextView) findViewById(R.id.txt_business_type);
        mTextViewShortDesc = (TextView) findViewById(R.id.txt_short_desc);
        mFreetextShow = (TextView) findViewById(R.id.freetext_show);
        mLinearCableMain = (LinearLayout) findViewById(R.id.order_cablesumerylist);
        mLinearCableChooseYourCustomTvPlan = (LinearLayout) findViewById(R.id.l1sumery_ordercableAttributes);
        mLinearCablePremiumChanel = (LinearLayout) findViewById(R.id.l1sumery_ordersubscription);
        mLinearCableChanelEquipement = (RelativeLayout) findViewById(R.id.rl_additionalattributes);
        mLinearLayoutEquipments = (LinearLayout) findViewById(R.id.ll_equipments);
        mLinearMainEqupements = (LinearLayout) findViewById(R.id.order_sumerylistDetail);
        mLinearLayoutLegelOrder = (LinearLayout) findViewById(R.id.ll_legel_order);
        mLinearLayoutEquipementsDetail = (RelativeLayout) findViewById(R.id.equipementsDetailSet);
        mLinearLayoutAttributesDetail = (LinearLayout) findViewById(R.id.l1sumery_orderplansubscription);
        mLinearLayoutExtraServiceDetail = (LinearLayout) findViewById(R.id.l1sumery_orderattrtbutes_item);
        mLinearLayoutAttributesDetailHeding = (LinearLayout) findViewById(R.id.planLayoutMain);
        mLinearLayoutExtraServiceDetailHeading = (LinearLayout) findViewById(R.id.l1sumery_orderAttributes);
        mTextViewEquipmentFee = (TextView) findViewById(R.id.txt_equipment_price);
        mTextViewEquipmentInstallation = (TextView) findViewById(R.id.txt_equipment_memory);
        mTextViewYouSave = (TextView) findViewById(R.id.txt_yousave);
        mTextViewBillingName = (TextView) findViewById(R.id.txt_biilingname);
        mTextViewShipingName = (TextView) findViewById(R.id.txt_shipingname);
        mTextViewBillingAddress = (TextView) findViewById(R.id.txt_billingaddres);
        mTextViewShipingAddress = (TextView) findViewById(R.id.txt_shipingaddress);
        mTextViewApplicantName = (TextView) findViewById(R.id.txt_applicantname);
        mTextViewApplicantAddress = (TextView) findViewById(R.id.txt_applicantaddress);
        mTextViewManagerName = (TextView) findViewById(R.id.txt_managername);
        mTextViewManagerMobileNo = (TextView) findViewById(R.id.txt_managermobileno);
        mTextViewLegelName = (TextView) findViewById(R.id.txt_legelname);

        mTextViewLegelDeatail = (TextView) findViewById(R.id.txt_legeldetail);
        mTextViewOrderId = (TextView) findViewById(R.id.txt_orderid);
        mTextViewProviderName = (TextView) findViewById(R.id.txt_providername);
        mTextViewPrividerOrderId = (TextView) findViewById(R.id.txt_providerid);
        mTextViewOrderDateTime = (TextView) findViewById(R.id.txt_datetime);
        mTextViewOrderStatus = (TextView) findViewById(R.id.txt_orderstatus);
        mTextViewUserType = (TextView) findViewById(R.id.txt_buisnessstatus);
        mTextViewOrderDealTitle = (TextView) findViewById(R.id.deal_name);
        mTextViewOrderActivationStatus = (TextView) findViewById(R.id.txt_activationstatus);
        mTextViewOrderDealPrice = (TextView) findViewById(R.id.tv_best_deal_price);
        mTextViewYouSavePrice = (TextView) findViewById(R.id.txt_yousave_price);
        mTextViewOrderDealEffectivePrice = (TextView) findViewById(R.id.tv_effetive_deal_price);
        mTextViewEquipmentTitle = (TextView) findViewById(R.id.txt_equipment_model);
        mImageViewOrderDeal = (ImageView) findViewById(R.id.deal_icon);

        /*CCCelllPhone ssssttttttttttttttaaaaaaaaarrrrrrrrrrrTTTTTTTT*/
//        LinearLayoutManager layoutManager
//                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

//        mOrderEquipementCellName = (TextView) findViewById(R.id.ordersumrytxt_ordertitleDetail);
//        mOrderEquipementBrandName = (TextView) findViewById(R.id.ordersumrytxt_expiry_dateDetail);
//        mOrderEquipementPrice = (TextView) findViewById(R.id.equipements_priceDetail);
//        mOrderEquipementColorList = (TextView) findViewById(R.id.ordersumrytxt_colornameDetail);
        mOrderPlanName = (TextView) findViewById(R.id.dealattributes_planplansubscription);
        mOrderPlanPrice = (TextView) findViewById(R.id.dealattributes_planplansubscription_price);
        mOrderExtraServiceName = (TextView) findViewById(R.id.dealattributes_plan);
        mOrderExtraPrice = (TextView) findViewById(R.id.dealattributes_plan_price);
//        mOrderEquipementQuantity = (TextView) findViewById(R.id.ordersumrytxt_order_quantityDetail);

        /*eeeeeeeeeeeeeennnnnnnnnnnnnnnnndddddddddddd*/

        SharedPreferences sp = getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        mFreeTextString = sp.getString("FreeText", null);

    }

    private void setmOrderEquipement() {
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView = (RecyclerView) findViewById(R.id.lv_additionalequipments_orderDetail);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(new OrderDealDetailListAdapter(OrderDealDetailActivity.this, orderDetail));
    }

    private void SetCableData() {
        mLinearCableMain = (LinearLayout) findViewById(R.id.order_cablesumerylist);
        mLinearCablechooseYourCustom = (LinearLayout) findViewById(R.id.l1sumery_ordercableAttributes);
        mLinearPremiumChannel = (LinearLayout) findViewById(R.id.l1sumery_ordersubscription1);
        mLinearChannelEquipement = (RelativeLayout) findViewById(R.id.rl_cableadditionalattributes);
        mOrderCustoTvName = (TextView) findViewById(R.id.dealattributescable_planname);
        mOrderCustoTvPrice = (TextView) findViewById(R.id.dealattributescable_price);
        mOrderPremiumChannelNamee = (TextView) findViewById(R.id.dealsubscription_cableplanname);
        mOrderPremiumChannelPrice = (TextView) findViewById(R.id.dealsubscription_cableprice);
        mOrderEquipementChanelName = (TextView) findViewById(R.id.dealsubscription_equipementcableplanname);
        mOrderEquipementChanelPrice = (TextView) findViewById(R.id.dealsubscription_equipementcableprice);
   /* if () {
        mOrderCustoTvName.setText(orderDetail.getOrderExtraServices().getTitle());
        mOrderCustoTvPrice.setText("$" + orderDetail.getOrderExtraServices().getPrice());
    }else{

    }if () {
        mOrderPremiumChannelNamee.setText(orderDetail.getOrderAttributes().getTitle());
        mOrderPremiumChannelPrice.setText("$" + orderDetail.getOrderAttributes().getPrice());
    }else {

    }
    if () {
        mOrderEquipementChanelName.setText(orderDetail.getOrderEquipments().get(0).getName());
        mOrderEquipementChanelPrice.setText("$" + orderDetail.getOrderEquipments().get(0).getPrice());
    }else {

    }
*/

        try {
            if (orderDetail.getOrderEquipments().size() == 0 && orderDetail.getOrderAttributes().getPrice() == null && orderDetail.getOrderExtraServices().getPrice() == null) {
                mLinearCableMain.setVisibility(View.GONE);
            } else {
                mLinearCableMain.setVisibility(View.VISIBLE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (orderDetail.getOrderExtraServices().getPrice() == null) {
            mLinearCablechooseYourCustom.setVisibility(View.GONE);

        } else {
            mOrderCustoTvName.setText(orderDetail.getOrderExtraServices().getTitle());
            mOrderCustoTvPrice.setText("$" + orderDetail.getOrderExtraServices().getPrice());
        }
        if (orderDetail.getOrderAttributes().getPrice() == null) {
            mLinearPremiumChannel.setVisibility(View.GONE);
        } else {
            mOrderPremiumChannelNamee.setText(orderDetail.getOrderAttributes().getTitle());
            mOrderPremiumChannelPrice.setText("$" + orderDetail.getOrderAttributes().getPrice());
        }
        try {
            if (orderDetail.getOrderEquipments().size() == 0) {
                mLinearChannelEquipement.setVisibility(View.GONE);
            } else {

                mOrderEquipementChanelName.setText(orderDetail.getOrderEquipments().get(0).getName());
                mOrderEquipementChanelPrice.setText("$" + orderDetail.getOrderEquipments().get(0).getPrice());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    /*method set to action bar */
    private void setActionbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        mToolbar.setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Order Details" + "</font>")));
    }


    @Override
    public void onResume() {
        super.onResume();
        Localytics.tagScreen("Order Details");
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void updateNetwork(String s) {

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

    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(OrderDealDetailActivity.this);
            pDialog.setMessage("Wait...");
            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                orderDetail = Jsondata.my_order_details("" + getIntent().getStringExtra("userid"), "" + getIntent().getIntExtra("orderid", 0), OrderDealDetailActivity.this);
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

                if (orderDetail.getSuccess()) {
                    setDataInView();
                    if (orderDetail.getOrderItems().get(0).getDeal().getServiceCategoryId() == 4) {

                        setmOrderEquipement();
                    }
                    if (orderDetail.getOrderItems().get(0).getDeal().getServiceCategoryId() == 3) {
                        SetCableData();
                    }
                } else {
                    ShowDailog.Show_Alert_Login(OrderDealDetailActivity.this, getResources().getText(R.string.server_error).toString());
                }
            } catch (Exception e) {
            }


        }
    }
}
