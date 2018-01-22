package com.spa.adapter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.spa.model.equipments.EqupementsListAdd;
import com.spa.servicedealz.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by T011Prakash on 5/23/2016.
 */
public class ColorListAdapter extends RecyclerView.Adapter<ColorListAdapter.VersionViewHolder> {

    List<com.spa.model.cellphondetail.AvailableColor> listEntities;
    Activity activity;
    String selectedItem = "";
    private static int counter = 0;
    ArrayList<EqupementsListAdd> equpementsListAdds = new ArrayList<>();


    public ColorListAdapter(Activity a, List<com.spa.model.cellphondetail.AvailableColor> listEntities) {

        this.listEntities = listEntities;
        this.activity = a;
        EquipmentsAdapter.quanity.clear();
        EquipmentsAdapter.colorlistcheck.clear();
        for (int i = 0; listEntities.size() > i; i++) {

            if (i == 0) {
                EquipmentsAdapter.quanity.add(1);
                EquipmentsAdapter.colorlistcheck.add(true);
            } else {
                EquipmentsAdapter.quanity.add(0);
                EquipmentsAdapter.colorlistcheck.add(false);
            }

        }


    }

    @Override
    public VersionViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view =
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_music_list, viewGroup, false);
        VersionViewHolder viewHolder = new VersionViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final VersionViewHolder versionViewHolder, int i) {

        // Set Data in your views comes from CollectionClass


//        if (i % 2 == 1) {
//            versionViewHolder.itemView.setBackgroundResource(R.drawable.rounded_list_row);
//        }
//        versionViewHolder.mCheckBoxMusic.setChecked(listEntities.get(i).getSelected());


        versionViewHolder.mTextViewMusicType.setText(listEntities.get(i).getColorName());
        if (i == 0) {
            versionViewHolder.mCheckBoxMusic.setChecked(true);
            versionViewHolder.display.setText("1");
        } else {
            versionViewHolder.mCheckBoxMusic.setChecked(false);
            versionViewHolder.display.setText("0");
        }

        versionViewHolder.mCheckBoxMusic.setTag(i);
        int color = Color.parseColor(listEntities.get(i).getColorCode());
        versionViewHolder.mTextviewCheckbox.setBackgroundColor(color);
        versionViewHolder.add.setTag(i);
        versionViewHolder.sub.setTag(i);
        versionViewHolder.mCheckBoxMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getSelectedItem();
                int position = (int) v.getTag();
                if (EquipmentsAdapter.colorlistcheck.get(position)) {
                    EquipmentsAdapter.colorlistcheck.remove(position);
                    EquipmentsAdapter.colorlistcheck.add(position, false);
                } else {
                    EquipmentsAdapter.colorlistcheck.remove(position);
                    EquipmentsAdapter.colorlistcheck.add(position, true);
                }
                CheckBox cb = (CheckBox) v;

                if (counter < 3) {
                    cb.setChecked(cb.isChecked());
                    listEntities.get(position).setSelected(cb.isChecked());

                } else {
                    cb.setChecked(false);
                    listEntities.get(position).setSelected(false);
                    versionViewHolder.display.setText("0");
                    /*versionViewHolder.mCheckBoxMusic.setChecked(false);
                    notifyDataSetChanged();*/
                }

                if(cb.isChecked())
                {
                    // section-1
                    versionViewHolder.add.setClickable(true);
                    versionViewHolder.sub.setClickable(true);
                }
                else{
                    // section-2
                    versionViewHolder.add.setClickable(false);
                    versionViewHolder.add.setFocusable(false);
                    versionViewHolder.sub.setClickable(false);
                    versionViewHolder.sub.setClickable(false);
                    versionViewHolder.display.setText("0");
                }
            }

        });

    }


    @Override
    public int getItemCount() {

        return listEntities == null ? 0 : listEntities.size();
    }


    class VersionViewHolder extends RecyclerView.ViewHolder {
        public View mView;
        TextView mTextViewMusicType, mTextviewCheckbox;
        public CheckBox mCheckBoxMusic;
        Button add, sub;
        TextView display;
        int counter;

        public VersionViewHolder(View itemView) {
            super(itemView);
            counter = 0;
            mTextviewCheckbox = (TextView) itemView.findViewById(R.id.txt_check);
            mTextViewMusicType = (TextView) itemView.findViewById(R.id.text_view_music_type);
            mCheckBoxMusic = (CheckBox) itemView.findViewById(R.id.music_type_check_box);

            add = (Button) itemView.findViewById(R.id.bAdd);
            sub = (Button) itemView.findViewById(R.id.bSub);
            display = (TextView) itemView.findViewById(R.id.tvDisplay);


            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Adds 1 to the counter
                    int pos = (int) v.getTag();
                    if (mCheckBoxMusic.isChecked()) {
                        if (counter == 4) {
                            display.setText("4");
                        } else {
                            counter = counter + 1;
                            display.setText("" + counter);

                        }
                    }
                    EquipmentsAdapter.quanity.remove(pos);
                    EquipmentsAdapter.quanity.add(pos, counter);
                }
            });

            sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Subtract 1 from counter
                    if (counter == 1) {
                        display.setText("1");
                    } else {
                        if (counter == 0) {
                            display.setText("" + 0);
                        } else {
                            counter = counter - 1;
                            display.setText("" + counter);
                        }


                    }
                    int pos = (int) v.getTag();
                    EquipmentsAdapter.quanity.remove(pos);
                    EquipmentsAdapter.quanity.add(pos, counter);
                }
            });


            mView = itemView;

        }

    }

    public String getSelectedItem() {

        counter = 0;
        SharedPreferences sp = activity.getSharedPreferences("Pref_name", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        try {
            for (com.spa.model.cellphondetail.AvailableColor item : listEntities) {


                if (item.getSelected()) {
                    if (selectedItem != null) {
                        selectedItem = selectedItem + item.getColorName() + ",";
                        editor.putString("EquipmentsColorNameList", "" + selectedItem);
                        editor.commit();
                        counter++;
                    }
                }

            }

            if (selectedItem.contains(","))
                selectedItem = selectedItem.substring(0, selectedItem.length() - 1);
            editor.putString("EquipmentsColorNameList", "" + selectedItem);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return selectedItem;
    }
}
