package com.spa.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.ProfileActivity;
import com.spa.adapter.TabPagerAdapter;
import com.spa.utils.Constant;

import co.spa.sidemenu.interfaces.ScreenShotable;

/**
 * Created by Diwakar on 22.02.2015.
 */

/**
 * FileName : MainFragment
 * Description :
 * Dependencies : Internet
 */
public class MainFragment extends Fragment implements ScreenShotable, View.OnClickListener {

    public static final int CAMERA_REQUEST = 3;
    public static final int PICK_FROM_GALLERY = 2;
    private Button mButtonProfile, mButtonService, mButtonPreference;
    public static ViewPager Tab;
    private TabPagerAdapter TabAdapter;
    public static String IMAGE_BASE_64 = "";
    private SharedPreferences mSharedPreferences;

    public static MainFragment newInstance() {
        MainFragment contentFragment = new MainFragment();
        return contentFragment;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.main_fragment, container, false);
        TabAdapter = new TabPagerAdapter((AppCompatActivity) getActivity(), getChildFragmentManager());
        mSharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
//==================================================================================================
        mButtonProfile = (Button) rootView.findViewById(R.id.btn_general_info);
        mButtonService = (Button) rootView.findViewById(R.id.btn_services);
        mButtonPreference = (Button) rootView.findViewById(R.id.btn_preferences);
        mButtonProfile.setOnClickListener(this);
        mButtonService.setOnClickListener(this);
        mButtonPreference.setOnClickListener(this);
//==================================================================================================
        Tab = (ViewPager) rootView.findViewById(R.id.pager);
        Tab.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {

                try {
                    if (position == 1) {
                        mButtonProfile.setBackgroundResource(R.drawable.menu_bg);
                        mButtonService.setBackgroundResource(R.drawable.active_menu);
                        mButtonPreference.setBackgroundResource(R.drawable.menu_bg);
                        mButtonPreference.setTextColor(getResources().getColor(R.color.tabcolor));
                        mButtonProfile.setTextColor(getResources().getColor(R.color.tabcolor));
                        mButtonService.setTextColor(getResources().getColor(R.color.white_color));
                    } else if (position == 2) {
                        mButtonProfile.setBackgroundResource(R.drawable.menu_bg);
                        mButtonService.setBackgroundResource(R.drawable.menu_bg);
                        mButtonPreference.setBackgroundResource(R.drawable.active_menu);
                        mButtonPreference.setTextColor(getResources().getColor(R.color.white_color));
                        mButtonProfile.setTextColor(getResources().getColor(R.color.tabcolor));
                        mButtonService.setTextColor(getResources().getColor(R.color.tabcolor));
                        ((ProfileActivity) getActivity()).rlNext.setVisibility(View.GONE);
                    } else if (position == 0) {
                        mButtonPreference.setTextColor(getResources().getColor(R.color.tabcolor));
                        mButtonProfile.setTextColor(getResources().getColor(R.color.white_color));
                        mButtonService.setTextColor(getResources().getColor(R.color.tabcolor));
                        mButtonProfile.setBackgroundResource(R.drawable.active_menu);
                        mButtonService.setBackgroundResource(R.drawable.menu_bg);
                        mButtonPreference.setBackgroundResource(R.drawable.menu_bg);
                        ((ProfileActivity) getActivity()).rlNext.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

                ((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(Tab.getWindowToken(), 0);


            }
        });
        Tab.setAdapter(TabAdapter);
        Tab.setCurrentItem(0);
        return rootView;
    }

   // @Override
   /* public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CAMERA_REQUEST:
                Bundle extras = data.getExtras();
                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte imageInByte[] = stream.toByteArray();
                    IMAGE_BASE_64 = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                    RoundImage roundedImage = new RoundImage(yourImage);
                    ProfileFragment.sImageViewProfile.setImageBitmap(drawableToBitmap(roundedImage));
                }
                break;
            case PICK_FROM_GALLERY:
                if (resultCode == getActivity().RESULT_OK) {
                    if (requestCode == PICK_FROM_GALLERY) {
                        Bitmap yourImage = null;
                        Uri selectedImageUri = data.getData();
                        try {
                            if (selectedImageUri == null) {
                                Bundle extra2 = data.getExtras();
                                yourImage = extra2.getParcelable("data");
                            } else {
                                yourImage = new UserPicture(selectedImageUri, getActivity().getContentResolver()).getBitmap();
                            }

                            // convert bitmap to byte
                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
                            yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byte imageInByte[] = stream.toByteArray();

                            IMAGE_BASE_64 = Base64.encodeToString(imageInByte, Base64.DEFAULT);
                            RoundImage roundedImage = new RoundImage(yourImage);
                            ProfileFragment.sImageViewProfile.setImageBitmap(drawableToBitmap(roundedImage));
                        } catch (IOException e) {
                        }
                        break;
                    }
                }
        }
    }
*/
    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @Override
    public void takeScreenShot() {
    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        ((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(Tab.getWindowToken(), 0);
        switch (v.getId()) {
            case R.id.btn_general_info:
                Tab.setCurrentItem(0);

                try {
                    mButtonProfile.setBackgroundResource(R.drawable.active_menu);
                    mButtonService.setBackgroundResource(R.drawable.menu_bg);
                    mButtonPreference.setBackgroundResource(R.drawable.menu_bg);

                    mButtonPreference.setTextColor(getResources().getColor(R.color.tabcolor));
                    mButtonProfile.setTextColor(getResources().getColor(R.color.white_color));
                    mButtonService.setTextColor(getResources().getColor(R.color.tabcolor));
                } catch (Exception e) {

                }
                break;
            case R.id.btn_services:
                Tab.setCurrentItem(1);

                try {
                    mButtonProfile.setBackgroundResource(R.drawable.menu_bg);
                    mButtonService.setBackgroundResource(R.drawable.active_menu);
                    mButtonPreference.setBackgroundResource(R.drawable.menu_bg);

                    mButtonPreference.setTextColor(getResources().getColor(R.color.tabcolor));
                    mButtonProfile.setTextColor(getResources().getColor(R.color.tabcolor));
                    mButtonService.setTextColor(getResources().getColor(R.color.white_color));
                } catch (Exception e) {
                }
                break;
            case R.id.btn_preferences:
               // openPreferenceFragment();
                break;

        }
    }

   /* public void openPreferenceFragment() {
        Tab.setCurrentItem(2);

        try {

            mButtonProfile.setBackgroundResource(R.drawable.menu_bg);
            mButtonService.setBackgroundResource(R.drawable.menu_bg);
            mButtonPreference.setBackgroundResource(R.drawable.active_menu);
            mButtonPreference.setTextColor(getResources().getColor(R.color.white_color));
            mButtonProfile.setTextColor(getResources().getColor(R.color.tabcolor));
            mButtonService.setTextColor(getResources().getColor(R.color.tabcolor));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
}



