package com.spa.adapter;

/**
 * Created by E0115Diwakar on 2/11/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;

import com.spa.fragment.ProfileFragment;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    public TabPagerAdapter(AppCompatActivity act, FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                //Fragement for Profile Tab

                return new ProfileFragment();
           /* case 1:
                //Fragment for Service Tab
                return new ServiceFragment();
            case 2:
                //Fragment for Preference Tab
                return new Preferences();*/

        }
        return null;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return 1; //No of Tabs
    }
}
