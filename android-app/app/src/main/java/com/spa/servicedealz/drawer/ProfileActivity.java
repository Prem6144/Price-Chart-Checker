package com.spa.servicedealz.drawer;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.spa.fragment.MainFragment;
import com.spa.servicedealz.R;
import com.spa.servicedealz.ui.SlideMenuActivity;

import co.spa.sidemenu.util.ViewAnimator;

/**
 * FileName : ProfileActivity
 * Description : Detail of login user
 * Dependencies : MainFragment
 */
public class ProfileActivity extends SlideMenuActivity implements SurfaceHolder.Callback{
    private MainFragment mMainFragment;
    Button btnGal;
    Bitmap bmp;
    public RelativeLayout rlNext;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dashboard);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        mMainFragment = MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, mMainFragment)
                .commit();
        rlNext = (RelativeLayout) findViewById(R.id.rl_next);
        findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                 mMainFragment.openPreferenceFragment();
            }
        });
        setActionBar();
        viewAnimator = new ViewAnimator<>(mActivity, mSlideMenuList, mMainFragment, mDrawerLayout, mActivity);
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case ProfileFragment.CAMERA_REQUEST:
//                Bundle extras = data.getExtras();
//                if (extras != null) {
//                    Bitmap yourImage = extras.getParcelable("data");
//                    // convert bitmap to byte
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    byte imageInByte[] = stream.toByteArray();
//                    IMAGE_BASE_64 = Base64.encodeToString(imageInByte, Base64.DEFAULT);
//                    RoundImage roundedImage = new RoundImage(yourImage);
//                    ProfileFragment.sImageViewProfile.setImageBitmap(drawableToBitmap(roundedImage));
//                }
//                break;
//            case  ProfileFragment.PICK_FROM_GALLERY:
//                if (resultCode == RESULT_OK) {
//                    if (requestCode == ProfileFragment.PICK_FROM_GALLERY) {
//                        Bitmap yourImage = null;
//                        Uri selectedImageUri = data.getData();
//                        try {
//                            if (selectedImageUri == null) {
//                                Bundle extra2 = data.getExtras();
//                                yourImage = extra2.getParcelable("data");
//                            } else {
//                                yourImage = new UserPicture(selectedImageUri, getContentResolver()).getBitmap();
//                            }
//
//                            // convert bitmap to byte
//                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                            yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                            byte imageInByte[] = stream.toByteArray();
//
//                            IMAGE_BASE_64 = Base64.encodeToString(imageInByte, Base64.DEFAULT);
//                            RoundImage roundedImage = new RoundImage(yourImage);
//                            ProfileFragment.sImageViewProfile.setImageBitmap(drawableToBitmap(roundedImage));
//                        } catch (IOException e) {
//                        }
//                        break;
//                    }
//                }
//        }
//    }
//    public static Bitmap drawableToBitmap(Drawable drawable) {
//        if (drawable instanceof BitmapDrawable) {
//            return ((BitmapDrawable) drawable).getBitmap();
//        }
//        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
//        Canvas canvas = new Canvas(bitmap);
//        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
//        drawable.draw(canvas);
//        return bitmap;
//    }

//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case ProfileFragment.CAMERA_REQUEST:
//                Bundle extras = data.getExtras();
//                if (extras != null) {
//                    Bitmap yourImage = extras.getParcelable("data");
//                    // convert bitmap to byte
//                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
//                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
//                    byte imageInByte[] = stream.toByteArray();
//                    MainFragment.IMAGE_BASE_64 = Base64.encodeToString(imageInByte, Base64.DEFAULT);
//                    RoundImage roundedImage = new RoundImage(yourImage);
//                    ProfileFragment.sImageViewProfile.setImageBitmap(ProfileFragment.drawableToBitmap(roundedImage));
//                }
//                break;
//            case ProfileFragment.PICK_FROM_GALLERY:
//                if (requestCode==1)
//                {
//                    if (data != null && resultCode == RESULT_OK)
//                    {
//
//                        Uri selectedImage = data.getData();
//
//                        String[] filePathColumn = {MediaStore.Images.Media.DATA};
//                        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
//                        cursor.moveToFirst();
//                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//                        String filePath = cursor.getString(columnIndex);
//                        cursor.close();
//
//                        if(bmp != null && !bmp.isRecycled())
//                        {
//                            bmp = null;
//                        }
//
//                        bmp = BitmapFactory.decodeFile(filePath);
//                        ProfileFragment.sImageViewProfile.setBackgroundResource(0);
//                        ProfileFragment.sImageViewProfile.setImageBitmap(bmp);
//                    }
//                    else
//                    {
//                        Log.d("Status:", "Photopicker canceled");
//                }
//                break;
////
////                if (resultCode == RESULT_OK) {
////                    if (requestCode == ProfileFragment.PICK_FROM_GALLERY) {
////
////                        Bitmap yourImage = null;
////                        Uri selectedImageUri = data.getData();
////                        try {
////                            if (selectedImageUri == null) {
////                                Bundle extra2 = data.getExtras();
////                                yourImage = extra2.getParcelable("data");
////                            } else {
////                                yourImage = new UserPicture(selectedImageUri, getContentResolver()).getBitmap();
////                            }
////
////                            // convert bitmap to byte
////                            ByteArrayOutputStream stream = new ByteArrayOutputStream();
////                            yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
////                            byte imageInByte[] = stream.toByteArray();
////
////                            MainFragment.IMAGE_BASE_64 = Base64.encodeToString(imageInByte, Base64.DEFAULT);
////                            RoundImage roundedImage = new RoundImage(yourImage);
////                            ProfileFragment.sImageViewProfile.setImageBitmap(ProfileFragment.drawableToBitmap(roundedImage));
////                        } catch (IOException e) {
////                        }
////                        break;
////
////                    }
//                }
//        }
//
//    }

    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.menuicon);
        setSupportActionBar(toolbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red_color)));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sp = getSharedPreferences("Pref_name",
                Activity.MODE_WORLD_READABLE);

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#FFFFFF\">" + "Profile" + "</font>")));
        setDrawer(toolbar);
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
