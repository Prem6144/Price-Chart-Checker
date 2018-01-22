package com.spa.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.spa.model.cellphondetail.DealDeatil;
import com.spa.model.custemizedealdetail.Deals;
import com.spa.model.custemizedealdetail.EquipmentSelected;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.CellphoneCustemDealActivity;
import com.spa.utils.CustomizeDialog;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Diwakar on 9/14/2016.
 */
public class EquipmentsAdapter extends RecyclerView.Adapter<EquipmentsAdapter.VersionViewHolder> {
    DealDeatil statusLevelListEntities;

    public static List<Integer> quanity = new ArrayList<>();
    public static List<Boolean> colorlistcheck = new ArrayList<>();
    List<Deals> dealses = new ArrayList<>();

    ColorListAdapter mColorListAdapter;

    int mSelectSpinerPosition;
    int counter;

    Activity activity;

    String mUrlDescription, mCellPhoneTitle, mEquipementColorName, mmEquipementPrice, mEquipementDealId, mCellPhoneBrandName;
    int mEquipementCellPhoneDetailId;
    double value1;
    private String otherPrice;
    List<String> stringList = new ArrayList<>();
    public static EquipmentSelected equipmentSelected;
    List<EquipmentSelected.ListDeviceEntity> listDeviceEntities = new ArrayList<EquipmentSelected.ListDeviceEntity>();

    public EquipmentsAdapter(Activity a, DealDeatil deals) {

        this.statusLevelListEntities = deals;
        this.activity = a;


    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.equipments, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);
        equipmentSelected = new EquipmentSelected();
        listDeviceEntities.clear();

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, final int i) {

        // Set Data in your views comes from CollectionClass

        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
//           versionViewHolder.mCheckBoxCompare.setChecked(statusLevelListEntities.getDeals().getDealEquipments().get(i).isSelected());
        versionViewHolder.mTextViewPrice
                .setText("$" + statusLevelListEntities.getDeals().getDealEquipments().get(i).getPrice());
        mCellPhoneTitle = statusLevelListEntities.getDeals().getDealEquipments().get(i).getCellphoneName();
        editor.putString("EquipmentsCelPhoneTitle", "" + mCellPhoneTitle);
        editor.commit();
        versionViewHolder.mTextViewDescription.setText(mCellPhoneTitle);

        mCellPhoneBrandName = statusLevelListEntities.getDeals().getDealEquipments().get(i).getBrand();
        editor.putString("EquipmentsCelPhoneBrand", "" + mCellPhoneBrandName);
        editor.commit();
        versionViewHolder.mTextViewTitle.setText(mCellPhoneBrandName);


        String mUrlDescription = statusLevelListEntities.getDeals().getDealEquipments().get(i).getDescription();

//            mColorName = versionViewHolder.statusLevelListEntities.getDeals().getDealEquipments().get(i).getAvailableColor().get(i).getColorName();

        int Size = statusLevelListEntities.getDeals().getDealEquipments().get(i).getAvailableColor().size();

        //String color = mColor.getColorName();

        stringList.clear();
        for (int j = 0; Size > j; j++) {
            //String color = mColor.getColorName();


            stringList.add(statusLevelListEntities.getDeals().getDealEquipments().get(i).getAvailableColor().get(j).getColorName());

//            ShowDailogList();
//
        }

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item, stringList);
//        versionViewHolder.mSpinerColorName.setAdapter(adapter);
        versionViewHolder.mEquipementCheckBox.setTag(i);

//
        if (statusLevelListEntities.getDeals().getDealEquipments().get(i).getAvailableColor().size() > 0) {
            mEquipementColorName = statusLevelListEntities.getDeals().getDealEquipments().get(i).getAvailableColor().get(0).getColorName();
            versionViewHolder.mTextColorName.setText(mEquipementColorName);
        }
        versionViewHolder.mTextColorName.setTag(i);
        versionViewHolder.mEquipementCheckBox.
                setTag(statusLevelListEntities.getDeals().getDealEquipments().get(i));

        versionViewHolder.mEquipementCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()) {
                    mEquipementDealId =""+statusLevelListEntities.getDeals().getDealEquipments().get(i).getId();
                    SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("EquipementCellphoneDetailId", "" + mEquipementDealId);
                    editor.commit();

                    List<com.spa.model.cellphondetail.AvailableColor> availableColors = statusLevelListEntities.getDeals().getDealEquipments().get(i).getAvailableColor();
                    ShowDailogList(availableColors, i);

                } else {
                    otherPrice = statusLevelListEntities.getDeals().getDealEquipments().get(i).getPrice();
                    value1 = Double.parseDouble(otherPrice);
                    try {


                        if (equipmentSelected.getListDevice() != null) {
                            for (int k = 0; equipmentSelected.getListDevice().size() > k; k++) {
                                if (equipmentSelected.getListDevice().get(k).getEquipmentsposition().toString().equalsIgnoreCase("" + i)) {
                                    value1 = value1 * equipmentSelected.getListDevice().get(k).getDevicelist().size();
                                    equipmentSelected.getListDevice().remove(k);

                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    value1 = Math.round(value1 * 100.0) / 100.0;

                    CellphoneCustemDealActivity.value = Math.round(CellphoneCustemDealActivity.value * 100.0) / 100.0;

                    CellphoneCustemDealActivity.value = CellphoneCustemDealActivity.value - value1;

                    DecimalFormat f = new DecimalFormat("##.00");


                    CellphoneCustemDealActivity.mTextViewEffectivePrice.setText("$" + f.format(CellphoneCustemDealActivity.value));
                }
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {

        return statusLevelListEntities == null ? 0 : statusLevelListEntities.getDeals().getDealEquipments().size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        private TextView mTextViewTitle, mTextViewDescription, mTextViewPrice, mTextViewbundleCombo, mTextViewExpiryDate,
                mTextViewSavePrice, mTextViewEffectivePrice, mTextColorName;
        private Spinner mSpinerColorName;

        private RatingBar mRatingDeal;
        private CheckBox mEquipementCheckBox;
        private LinearLayout mLinearLayoutEffective;
        private String EquipmentsPrice;
        private RelativeLayout mRelativeLayoutBeatDeal;


        public VersionViewHolder(View row) {
            super(row);
            counter = 0;


//            mSpinerColorName = (Spinner) row.findViewById(R.id.spinner);
            mTextViewTitle = (TextView) row.findViewById(R.id.text_device_name);
            mTextViewDescription = (TextView) row.findViewById(R.id.text_model_no);
            mTextViewPrice = (TextView) row.findViewById(R.id.celphone_price);
            mEquipementCheckBox = (CheckBox) row.findViewById(R.id.equipementcheckbox);
            mTextColorName = (TextView) row.findViewById(R.id.select_colorNameList);


            mTextColorName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = (int) v.getTag();
                    if (mEquipementCheckBox.isChecked()) {
                        List<com.spa.model.cellphondetail.AvailableColor> availableColors = statusLevelListEntities.getDeals().getDealEquipments().get(pos).getAvailableColor();
                        ShowDailogList(availableColors, pos);
                    }

                }


            });

//            mSpinerColorName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                @Override
//                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                    mSelectSpinerPosition = mSpinerColorName.getSelectedItemPosition();
//                }
//
//                @Override
//                public void onNothingSelected(AdapterView<?> parent) {
//
//                }
//            });

            mView = row;

        }

    }

    public void ShowDailogList(final List<com.spa.model.cellphondetail.AvailableColor> mColorName, final int position) {
        final CustomizeDialog mCustomizeDialog = new CustomizeDialog(activity);
        mCustomizeDialog.setCancelable(false);

        mCustomizeDialog.setContentView(R.layout.dialog_color_list_sorting);
        RecyclerView mRecyclerViewMusic = (RecyclerView) mCustomizeDialog.findViewById(R.id.color_list);
        Button mColorSaveBtn = (Button) mCustomizeDialog.findViewById(R.id.colorbtn);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(activity.getBaseContext());
        mRecyclerViewMusic.setLayoutManager(linearLayoutManager);
        mRecyclerViewMusic.setHasFixedSize(true);
        mColorListAdapter = new ColorListAdapter(activity, mColorName);
        mRecyclerViewMusic.setAdapter(mColorListAdapter);
        mCustomizeDialog.show();
        mColorSaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int MinuseffectivePrice = 0;
                if (equipmentSelected.getListDevice() != null) {

                    for (int k = 0; equipmentSelected.getListDevice().size() > k; k++) {
                        if (equipmentSelected.getListDevice().get(k).getEquipmentsposition().toString().equalsIgnoreCase("" + position)) {
                            MinuseffectivePrice = equipmentSelected.getListDevice().get(k).getDevicelist().size();
                            equipmentSelected.getListDevice().remove(k);
                        }
                    }
                }
                List<EquipmentSelected.ListDeviceEntity.DevicelistEntity> devicelistEntities = new ArrayList<EquipmentSelected.ListDeviceEntity.DevicelistEntity>();
                EquipmentSelected.ListDeviceEntity listDeviceEntity = new EquipmentSelected.ListDeviceEntity();
                for (int i = 0; colorlistcheck.size() > i; i++) {
                    if (colorlistcheck.get(i)) {
                        int quntit = quanity.get(i);


                        for (int j = 0; quntit > j; j++) {

                            EquipmentSelected.ListDeviceEntity.DevicelistEntity devicelistEntity = new EquipmentSelected.ListDeviceEntity.DevicelistEntity();
                            devicelistEntity.setPhonebrand(statusLevelListEntities.getDeals().getDealEquipments().get(position).getBrand());
                            devicelistEntity.setPhonecolor(mColorName.get(i).getColorName());
                            devicelistEntity.setPhoneprice(statusLevelListEntities.getDeals().getDealEquipments().get(position).getPrice());
                            String attributesId = String.valueOf(statusLevelListEntities.getDeals().getDealEquipments().get(position).getCellphoneDealAttributeId());
                            devicelistEntity.setPhoneid("" +attributesId );
                            devicelistEntity.setPhoneid("" + statusLevelListEntities.getDeals().getDealEquipments().get(position).getCellphoneDetailId());
                            devicelistEntity.setPhonename(statusLevelListEntities.getDeals().getDealEquipments().get(position).getCellphoneName());
                            devicelistEntity.setPhoneImageUrl(statusLevelListEntities.getDeals().getDealEquipments().get(position).getImage());
                            devicelistEntities.add(devicelistEntity);

                        }

                    }
                }
                setEffectivePrice(position, devicelistEntities.size(), MinuseffectivePrice);
                listDeviceEntity.setEquipmentsposition("" + position);
                listDeviceEntity.setDevicelist(devicelistEntities);
                listDeviceEntities.add(listDeviceEntity);
                equipmentSelected.setListDevice(listDeviceEntities);
                mCustomizeDialog.dismiss();
            }
        });
    }

    private void setEffectivePrice(int i, int size, int MinuseffectivePrice) {
        otherPrice = statusLevelListEntities.getDeals().getDealEquipments().get(i).getPrice();
        value1 = Double.parseDouble(otherPrice);

        value1 = Math.round(value1 * 100.0) / 100.0;
        value1 = value1 * size - value1 * MinuseffectivePrice;
        CellphoneCustemDealActivity.value = Math.round(CellphoneCustemDealActivity.value * 100.0) / 100.0;


        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
                   /* editor.putFloat("EquipmentsPrice", Double.parseDouble(otherPrice));
                    editor.commit();*/

        mEquipementCellPhoneDetailId = statusLevelListEntities.getDeals().getDealEquipments().get(i).getId();
//                Toast.makeText(activity, Price, Toast.LENGTH_SHORT).show();
        editor.putInt("EquipmentsPhoneDetailId", mEquipementCellPhoneDetailId);
        editor.commit();
        otherPrice = String.valueOf(statusLevelListEntities.getDeals().getDealEquipments().get(i).getPrice());
        editor.putString("EquipementPrice", otherPrice);
        editor.commit();

        CellphoneCustemDealActivity.value = CellphoneCustemDealActivity.value + value1;
        DecimalFormat f = new DecimalFormat("##.00");
        CellphoneCustemDealActivity.mTextViewEffectivePrice.setText("$" + f.format(CellphoneCustemDealActivity.value));

    }

}