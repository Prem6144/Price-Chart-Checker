package com.spa.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CaptureRequest;
import android.media.ImageReader;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.IntentCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.siyamed.shapeimageview.CircularImageView;
import com.localytics.android.Localytics;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.spa.internet_connectivity.NetworkUtil;
import com.spa.model.profile.Userdata;
import com.spa.model.state.PrimaryAndSecondaryId;
import com.spa.model.state.State;
import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.DashboardActivity;
import com.spa.utils.Constant;
import com.spa.utils.Jsondata;
import com.spa.utils.RoundImage;
import com.spa.utils.UserPicture;
import com.spa.utils.Validation;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.spa.utils.MenuItemGlobal.setCapitalizeTextWatcher;

/**
 * FileName : ProfileFragment
 * Description :
 * Dependencies : Internet
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    private Spinner mSpinnerState, mSpinnerPrimaryId, mSpinnerSecondaryId, mSpinnerPrimaryState, mSpinnerSecondaryState;
    private Button mButtonUpdate;
    private EditText mEditTextFirstName, mEditTextFederalNumber, mEditTextSSN, mEditTextLastName, mEditTextEmail, mEditTextAddress, mEditTextCity, mEditTextZipCode, mEditTextBuisnessName,
            mEditTextMobileNumber, mEditTextPrimaryIdNumber, mEditTextSecondaryNumber, mEditTextAddress1, mEditTextAddress2,
            mEditTextAddressName, mEditTextTownCity, mEditTextAddressZipcode;

    private Boolean isInternetPresent = false;
    public static CircularImageView sImageViewProfile;
    //    public static ImageView sImageViewProfile;
    private ImageView sImageViewProfileEdit;
    private TextView mTextViewZipCode, mTextViewLastName, mTextViewCity, mTextViewState, mTextViewEmail, mTextViewFirstName, mTextViewBuisnessName, mTextViewSsn, mTextVIewFedrel;
    private AlertDialog mDialog;
    public static final int CAMERA_REQUEST = 3;
    public static final int PICK_FROM_GALLERY = 2;
    private String mFirstName, mLastName, mEmail, addressname, mSate, mCity, mZipCode, mAddress, UserType, BuisnessName, buisness_type, ssn_number, federal_number, mobileNumber, primarynumber, secondarynumber, address1, address2, towncity, primaryid, secondaryid, addressZipcode;
    private SharedPreferences mSharedPreferences;
    private Userdata userdata;
    private RadioButton mRadioButtonResidence, mRadioButtonBuissness, mRadioButtonSole, mRadioButtonRegistred;
    private RadioGroup mRadioGroup, mRadioGroupBusinessType;
    private LinearLayout mLinearLayoutBuisness, mLinearLayoutSole, mLinearLayoutREgisterd, mLinearLayoutAddress, mLinearLayoutPersonalInformation, mLinearLayoutPriamry, mLinearLayoutSecondary;
    private SharedPreferences.Editor editor;
    int keyDel;
    private State state;
    private PrimaryAndSecondaryId primaryAndSecondaryId;

    private TextureView textureView;
    private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

    static {
        ORIENTATIONS.append(Surface.ROTATION_0, 90);
        ORIENTATIONS.append(Surface.ROTATION_90, 0);
        ORIENTATIONS.append(Surface.ROTATION_180, 270);
        ORIENTATIONS.append(Surface.ROTATION_270, 180);
    }

    private String cameraId;
    protected CameraDevice cameraDevice;
    protected CameraCaptureSession cameraCaptureSessions;
    protected CaptureRequest captureRequest;
    protected CaptureRequest.Builder captureRequestBuilder;
    private Size imageDimension;
    private ImageReader imageReader;
    private File file;
    private static final int REQUEST_CAMERA_PERMISSION = 200;
    private boolean mFlashSupported;
    private Handler mBackgroundHandler;
    private HandlerThread mBackgroundThread;
    public static String IMAGE_BASE_64 = "";

    public static ProfileFragment newInstance() {
        ProfileFragment contentFragment = new ProfileFragment();
        return contentFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.general_info, container, false);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        mSharedPreferences = getActivity().getSharedPreferences(Constant.SHARED_PREF,
                Activity.MODE_WORLD_READABLE);
        editor = mSharedPreferences.edit();
        MapFindId(view);
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
        CallCameraAndGallery();
        setCapitalizeTextWatcher(mEditTextBuisnessName);
        setCapitalizeTextWatcher(mEditTextFirstName);
        setCapitalizeTextWatcher(mEditTextLastName);
        mLinearLayoutREgisterd.setVisibility(View.GONE);
        mLinearLayoutSole.setVisibility(View.GONE);

        isInternetPresent = NetworkUtil.isConnectingToInternet(getActivity());
        if (isInternetPresent) {
            new HttpGetAsyncTaskGetStae().execute();
            new HttpGetAsyncTask_get().execute();
        } else {
            ShowDailog.Show_Alert_Dailog(getActivity());

        }
        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (mRadioButtonResidence.isChecked()) {
                    mLinearLayoutBuisness.setVisibility(View.GONE);
                } else {
                    mLinearLayoutBuisness.setVisibility(View.VISIBLE);
                }

            }
        });
        mButtonUpdate.setOnClickListener(this);


        return view;
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
                primaryAndSecondaryId = Jsondata.getPrimaryAndSecondaryId(mSharedPreferences.getString(Constant.APP_USER_ID, ""), getActivity());
                state = Jsondata.getState(getActivity());
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


                ArrayAdapter<String> adapterState = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, state.getStates());
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, primaryAndSecondaryId.getPrimaryIds());
                ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, primaryAndSecondaryId.getSecondaryIds());
                adapterState.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
                adapter1.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);

                mSpinnerPrimaryId.setAdapter(adapter);
                mSpinnerSecondaryId.setAdapter(adapter1);
                mSpinnerState.setAdapter(adapterState);
                mSpinnerPrimaryState.setAdapter(adapterState);
                mSpinnerSecondaryState.setAdapter(adapterState);

                mSpinnerPrimaryState.setOnItemSelectedListener(OnCatSpinnerCL);
                mSpinnerSecondaryState.setOnItemSelectedListener(OnCatSpinnerCL);
                mSpinnerState.setOnItemSelectedListener(OnCatSpinnerCL);
                mSpinnerSecondaryId.setOnItemSelectedListener(OnCatSpinnerCL);
                mSpinnerPrimaryId.setOnItemSelectedListener(OnCatSpinnerCL1);


            } catch (Exception e) {
                // ShowDailog.Show_Alert_Login(PlaceOrderAndPay.this, getResources().getText(R.string.server_error).toString());
                e.printStackTrace();
            }


        }
    }

    private AdapterView.OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            try {
                if (mSpinnerSecondaryId.getSelectedItem().toString().contains("Driving")) {
                    mLinearLayoutSecondary.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayoutSecondary.setVisibility(View.GONE);
                }

                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                ((TextView) parent.getChildAt(0)).setTextSize(14);

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

                if (mSpinnerPrimaryId.getSelectedItem().toString().contains("Driving")) {
                    mLinearLayoutPriamry.setVisibility(View.VISIBLE);
                } else {
                    mLinearLayoutPriamry.setVisibility(View.GONE);
                }
                ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.textcolor));
                ((TextView) parent.getChildAt(0)).setTextSize(14);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };

    /*
    * Mehod to call camera and gallery
    * */
    private void CallCameraAndGallery() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(),
                android.R.layout.select_dialog_item, getResources().getStringArray(R.array.camera_gallery));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(Constant.SELECT_OPTIONS);
        builder.setAdapter(adapter1, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                Log.e("Selected Item", String.valueOf(which));
                if (which == 0) {
                    callCamera();
                }
                if (which == 1) {
                    callGallery();
//                    openGallery();
                }

            }
        });
        mDialog = builder.create();
    }


    private void openGallery() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 1);
    }


    /*
       * Mehod to find ids of view
       * */
    private void MapFindId(final View view) {

        mEditTextFederalNumber = (EditText) view.findViewById(R.id.etx_federal_number);
        mEditTextSSN = (EditText) view.findViewById(R.id.etx_ssn_number);
        mEditTextAddressName = (EditText) view.findViewById(R.id.etx_billingaddressname);
        mEditTextAddressZipcode = (EditText) view.findViewById(R.id.etx_addresszipcode);
        mLinearLayoutSole = (LinearLayout) view.findViewById(R.id.ll_sole_business);
        mLinearLayoutREgisterd = (LinearLayout) view.findViewById(R.id.ll_registred_business);


        mRadioButtonSole = (RadioButton) view.findViewById(R.id.radioGroupButton_sole);
        mRadioGroup = (RadioGroup) view.findViewById(R.id.rg);
        mRadioButtonRegistred = (RadioButton) view.findViewById(R.id.radioGroupButton_registered);
        mLinearLayoutBuisness = (LinearLayout) view.findViewById(R.id.ll_buisness);
        mLinearLayoutPersonalInformation = (LinearLayout) view.findViewById(R.id.ll_personalInformation);
        mLinearLayoutAddress = (LinearLayout) view.findViewById(R.id.ll_address_information);
        mRadioButtonResidence = (RadioButton) view.findViewById(R.id.radioGroupButton0);
        mRadioGroupBusinessType = (RadioGroup) view.findViewById(R.id.rg_business_type);
        mRadioButtonBuissness = (RadioButton) view.findViewById(R.id.radioGroupButton1);
        mSpinnerState = (Spinner) view.findViewById(R.id.spn_city);
        mSpinnerPrimaryId = (Spinner) view.findViewById(R.id.spinner_primary_id);
        mSpinnerSecondaryId = (Spinner) view.findViewById(R.id.spinner_seconday_id);
        mLinearLayoutPriamry = (LinearLayout) view.findViewById(R.id.ll_primaryid);
        mLinearLayoutSecondary = (LinearLayout) view.findViewById(R.id.ll_secondaryid);
        mSpinnerPrimaryState = (Spinner) view.findViewById(R.id.spn_primarystate);
        mSpinnerSecondaryState = (Spinner) view.findViewById(R.id.spn_secondary);

        mEditTextMobileNumber = (EditText) view.findViewById(R.id.etx_mobile);
        mEditTextPrimaryIdNumber = (EditText) view.findViewById(R.id.etx_primary_id);
        mEditTextSecondaryNumber = (EditText) view.findViewById(R.id.etx_secodery_id);
        mEditTextAddress1 = (EditText) view.findViewById(R.id.etx_billingaddress1);
        mEditTextAddress2 = (EditText) view.findViewById(R.id.etx_billingaddress2);
        mEditTextTownCity = (EditText) view.findViewById(R.id.etx_billingcity);
        mEditTextFirstName = (EditText) view.findViewById(R.id.etx_F_Name);


        mEditTextBuisnessName = (EditText) view.findViewById(R.id.etx_buisness);
        mButtonUpdate = (Button) view.findViewById(R.id.btn_update);
        mEditTextLastName = (EditText) view.findViewById(R.id.etx_L_Name);
        mEditTextEmail = (EditText) view.findViewById(R.id.etx_e_Name);
        mEditTextAddress = (EditText) view.findViewById(R.id.etx_add_Name);
        mEditTextCity = (EditText) view.findViewById(R.id.etx_state_Name);
        mEditTextZipCode = (EditText) view.findViewById(R.id.etx_zipcode);
        // mEditTextZipCode.setSelection(mEditTextZipCode.getText().toString().length());
        // mEditTextZipCode.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        sImageViewProfile = (CircularImageView) view.findViewById(R.id.img_user1);
//        sImageViewProfile = (ImageView) view.findViewById(R.id.img_user1);
        sImageViewProfileEdit = (ImageView) view.findViewById(R.id.img_edit);
        mTextViewZipCode = (TextView) view.findViewById(R.id.txt_zipcode);
        mTextViewCity = (TextView) view.findViewById(R.id.txt_city);
        mTextViewLastName = (TextView) view.findViewById(R.id.txt_lastname);

        mTextViewSsn = (TextView) view.findViewById(R.id.txt_ssn_number);
        mTextVIewFedrel = (TextView) view.findViewById(R.id.txt_federal_number);

        mTextViewState = (TextView) view.findViewById(R.id.txt_state);
        mTextViewEmail = (TextView) view.findViewById(R.id.txt_email);
        mTextViewFirstName = (TextView) view.findViewById(R.id.txt_firstname);
        mTextViewBuisnessName = (TextView) view.findViewById(R.id.txt_buisnessname);
        mEditTextCity.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    ((InputMethodManager) getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(view.getWindowToken(), 0);
                    mSpinnerState.requestFocus();
                    mSpinnerState.performClick();

                    // mEditTextZipCode.requestFocus();
                    // mEditTextZipCode.setFocusable(true);
                }
                return true;
            }
        });
        if (mSharedPreferences.getBoolean("fullProfile", false)) {
            editor.putBoolean("fullProfile", false);
            editor.commit();
            mLinearLayoutAddress.setVisibility(View.VISIBLE);
            mLinearLayoutPersonalInformation.setVisibility(View.VISIBLE);

        }

    }

    /*
   * Method to check Login account
   * */

    @Override
    public void onResume() {
        super.onResume();
        Localytics.tagScreen("ProfileFragment");
    }

    private void SetLogin() {

        final String login_with = mSharedPreferences.getString(Constant.LOGIN_ACCOUNT_FLAG, "");
        sImageViewProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login_with.equalsIgnoreCase(Constant.GOOGLE_PLUS))

                {

                } else if (login_with.equalsIgnoreCase(Constant.FACEBOOK))

                {
                } else {
                    mDialog.show();

                }
            }
        });
        if (login_with.equalsIgnoreCase(Constant.SIGNIN))

        {
            Sign_In();
        } else if (login_with.equalsIgnoreCase(Constant.FACEBOOK))

        {
            sImageViewProfileEdit.setVisibility(View.GONE);
            Facebook_Login();

        } else if (login_with.equalsIgnoreCase(Constant.SIGNUP))

        {
            mEditTextEmail.setText(mSharedPreferences.getString(Constant.SIGNUP_EMAIL, ""));
        } else if (login_with.equalsIgnoreCase(Constant.GOOGLE_PLUS))

        {
            sImageViewProfileEdit.setVisibility(View.GONE);
            GPlus_Login();
        }

    }

    /*
       * Mehod to get value from edittext
       * */
    private void GetTextValue() {
        addressZipcode = mEditTextAddressZipcode.getText().toString();
        mFirstName = mEditTextFirstName.getText().toString();
        mLastName = mEditTextLastName.getText().toString();
        mEmail = mEditTextEmail.getText().toString();
        mSate = mSpinnerState.getSelectedItem().toString();
        mCity = mEditTextCity.getText().toString();
        mZipCode = mEditTextZipCode.getText().toString();
        mAddress = mEditTextAddress.getText().toString();
        mobileNumber = mEditTextMobileNumber.getText().toString();
        addressname = mEditTextAddressName.getText().toString();
        address1 = mEditTextAddress1.getText().toString();
        address2 = mEditTextAddress2.getText().toString();
        towncity = mEditTextTownCity.getText().toString();
        try {


            primaryid = mSpinnerPrimaryId.getSelectedItem().toString();
            secondaryid = mSpinnerSecondaryId.getSelectedItem().toString();

            if (primaryid.contains("Driving")) {
                primarynumber = mSpinnerPrimaryState.getSelectedItem().toString() + "===" + mEditTextPrimaryIdNumber.getText().toString();
            } else {
                primarynumber = mEditTextPrimaryIdNumber.getText().toString();
            }
            if (secondarynumber.contains("Driving")) {
                secondarynumber = mSpinnerSecondaryState.getSelectedItem().toString() + "===" + mEditTextSecondaryNumber.getText().toString();
            } else {
                secondarynumber = mEditTextSecondaryNumber.getText().toString();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mRadioButtonResidence.isChecked()) {
            UserType = "residence";
            buisness_type = "";
            federal_number = "";
            ssn_number = "";
            BuisnessName = "";

            editor.putString("BuisnessName", mFirstName + " " + mLastName);
        } else {
            BuisnessName = mEditTextBuisnessName.getText().toString();
            UserType = "business";
            editor.putString("BuisnessName", BuisnessName);
            if (mRadioButtonSole.isChecked()) {

                buisness_type = "0";
                federal_number = "";
                ssn_number = mEditTextSSN.getText().toString();

            } else {
                buisness_type = "1";
                ssn_number = "";
                federal_number = mEditTextFederalNumber.getText().toString();
            }
        }

        editor.commit();
    }

    private void Sign_In() {


        String etx_email = mSharedPreferences.getString(Constant.USER_EMAIL, "");
        mEditTextEmail.setText(etx_email);

    }

    /*
       * Mehod to call camera
       * */
    public void callCamera() {
        Intent cameraIntent = new Intent(
                MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);

    }

    class HttpGetAsyncTaskGetValidationFederalAndSSN extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {
                response = Jsondata.get_validate_federal(mSharedPreferences.getString(Constant.APP_USER_ID, ""), buisness_type, BuisnessName, federal_number, ssn_number, getActivity());
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
                if (mRadioButtonSole.isChecked() && !jsonObject.getBoolean("success"))
                    Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                else
                    new HttpGetAsyncTask().execute();
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            if()


        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == PICK_FROM_GALLERY && resultCode == getActivity().RESULT_OK && null != data) {
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
                sImageViewProfile.setImageBitmap(drawableToBitmap(roundedImage));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            if (requestCode == CAMERA_REQUEST && resultCode == getActivity().RESULT_OK && null != data) {
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
                    sImageViewProfile.setImageBitmap(drawableToBitmap(roundedImage));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * open gallery method
     */

    public void callGallery() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("image/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PICK_FROM_GALLERY);
//        startActivityForResult(intent, PICK_FROM_GALLERY);

    }

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

    /*
       * Mehod to login with Facebook
       * */
    private void Facebook_Login() {

        String facebook_email = mSharedPreferences.getString(Constant.FACEBOOK_EMAIL, "");
        String first_name = mSharedPreferences.getString(Constant.FIRST_NAME, "");
        String last_name = mSharedPreferences.getString(Constant.LAST_NAME, "");
        String id = mSharedPreferences.getString(Constant.FACEBOOK_ID, "");
        mEditTextFirstName.setText(first_name);
        mEditTextLastName.setText(last_name);
        mEditTextEmail.setText(facebook_email);
        int rounded_value = 200;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true)
                .displayer(new RoundedBitmapDisplayer(rounded_value))
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getBaseContext()).defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().displayImage("http://graph.facebook.com/" + id + "/picture?type=large", sImageViewProfile, options);
    }

    /*
       * Mehod to Login with Googleplus
       * */
    private void GPlus_Login() {

        String gplus_name = mSharedPreferences.getString(Constant.FIRST_NAME, "");
        String gplus_email = mSharedPreferences.getString(Constant.FACEBOOK_EMAIL, "");
        String gplus_image = mSharedPreferences.getString(Constant.GOOGLE_PLUS_IMAGE, "");
        mEditTextFirstName.setText(gplus_name);
        mEditTextEmail.setText(gplus_email);
        int rounded_value = 200;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheInMemory(true).cacheOnDisc(true)
                .displayer(new RoundedBitmapDisplayer(rounded_value))
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getBaseContext()).defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
        ImageLoader.getInstance().displayImage(gplus_image, sImageViewProfile, options);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                if (Validation.isFieldEmpty(mEditTextFirstName)) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.first_name_error),
                            Toast.LENGTH_SHORT).show();
                } else if (Validation.isFieldEmpty(mEditTextLastName)) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.last_name_error),
                            Toast.LENGTH_SHORT).show();
                } else if (mRadioButtonBuissness.isChecked() && mEditTextBuisnessName.getText().toString().trim().equalsIgnoreCase("")) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.buisness_name_error),
                            Toast.LENGTH_SHORT).show();
                }
                 /*else if (Validation.isFieldEmpty(mEditTextCity)) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.city_name_error),
                            Toast.LENGTH_SHORT).show();
                }*/ /*else if (mSpinnerState.getSelectedItem().equals("Select")) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.state_name_error),
                            Toast.LENGTH_SHORT).show();
                }*/
                else if (Validation.isFieldEmpty(mEditTextZipCode)) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.zipcode_error),
                            Toast.LENGTH_SHORT).show();
                } else if (mEditTextZipCode.getText().toString().length() != 5) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.zipcode_valid_error),
                            Toast.LENGTH_SHORT).show();
                } else {
                    GetTextValue();
                    if (isInternetPresent) {
                        new HttpGetAsyncTask().execute();
                       /* if (mRadioButtonResidence.isChecked())
                          //  new HttpGetAsyncTask().execute();
                        else {
                            if (mEditTextSSN.getText().toString().equalsIgnoreCase("") && mEditTextFederalNumber.getText().toString().equalsIgnoreCase("")) {
                                new HttpGetAsyncTask().execute();
                            } else {
                                if (mRadioButtonSole.isChecked() && mEditTextSSN.getText().length() != 11)
                                    Toast.makeText(getActivity(), "Enter Valid SSN Number",
                                            Toast.LENGTH_SHORT).show();
                                else if (mRadioButtonRegistred.isChecked() && mEditTextFederalNumber.getText().length() != 10)
                                    Toast.makeText(getActivity(), "Enter Valid Federal Tax Id ",
                                            Toast.LENGTH_SHORT).show();
                                else {
                                    if (userdata.getBusiness().getFederalNumber().equalsIgnoreCase("") && userdata.getBusiness().getSsn().equalsIgnoreCase("")) {
                                        new HttpGetAsyncTaskGetValidationFederalAndSSN().execute();

                                    } else {
                                        new HttpGetAsyncTask().execute();
                                    }

                                }

                        }
                        }*/
                    } else {
                        ShowDailog.Show_Alert_Dailog(getActivity());
                    }
                }
                break;
        }
    }


    class HttpGetAsyncTask extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                response = Jsondata.getgeneralinfo(mSharedPreferences.getString(Constant.APP_USER_ID, ""), mFirstName, mLastName, mEmail, mSate, mCity, mZipCode, mAddress, getActivity(), IMAGE_BASE_64, UserType, BuisnessName, buisness_type, federal_number, ssn_number, mobileNumber, primarynumber, secondarynumber, address1, address2, towncity, primaryid, secondaryid, addressZipcode, addressname);

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
            if (response.equalsIgnoreCase("true")) {

                editor.putString(Constant.ZIPCODE,
                        (mEditTextZipCode.getText().toString()));
                editor.putString(Constant.ZIPCODE_FLAG,
                        Constant.YES_FLAG);
                editor.putString(Constant.SERVICE_FLAG, Constant.YES_FLAG);

                editor.putString("user_type", UserType);
                editor.putString(Constant.USER_DASHBOARD_FLAG, Constant.YES_FLAG);
                editor.commit();
                localyticstagEvent("ProfileSummit");
                Intent HOME = new Intent(getActivity(), DashboardActivity.class);
                HOME.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(HOME);
                getActivity().finish();
            } else {
                ShowDailog.Show_Alert_Login(getActivity(), getResources().getText(R.string.server_error).toString());
            }


        }
    }

    class HttpGetAsyncTask_get extends AsyncTask<String, Void, String> {
        String response = "";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Wait...");

            pDialog.setCancelable(true);
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {


                userdata = Jsondata.get_general_info(mSharedPreferences.getString(Constant.APP_USER_ID, ""), getActivity());

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
                pDialog.dismiss();
                if (userdata.getSuccess()) {

                    // byte[] data = Base64.decode(userdata.getAppUser().getFirstName(), Base64.DEFAULT);
                    String text = Jsondata.decryptMsg(userdata.getAppUser().getFirstName());// new String(data, "UTF-8");
                    mEditTextFirstName.setText(Jsondata.decryptMsg(userdata.getAppUser().getFirstName()));
                    mEditTextLastName.setText(Jsondata.decryptMsg(userdata.getAppUser().getLastName()));
                    mEditTextCity.setText(userdata.getAppUser().getCity());


                   /* if (userdata.getAppUser().getZip().equals("") || userdata.getAppUser().getZip().equals(null)) {
                        mEditTextZipCode.setText(mSharedPreferences.getString("guestzipcode", ""));
                    } else {
                        mEditTextZipCode.setText(userdata.getAppUser().getZip());
                    }*/

                    mEditTextAddress.setText(userdata.getAppUser().getAddress());
                    int rounded_value = 200;
                    DisplayImageOptions options = new DisplayImageOptions.Builder()
                            .cacheInMemory(true).cacheOnDisc(true)
                            .displayer(new RoundedBitmapDisplayer(rounded_value))
                            .build();
                    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                            getActivity().getBaseContext()).defaultDisplayImageOptions(options)
                            .build();
                    ImageLoader.getInstance().init(config);

                    if (!userdata.getAppUser().getAvatarUrl().equalsIgnoreCase("")) {
                        ImageLoader.getInstance().displayImage(userdata.getAppUser().getAvatarUrl(), sImageViewProfile, options);
                    }

                    if (userdata.getAppUser().getUserType().equalsIgnoreCase("residence")) {
                        mLinearLayoutBuisness.setVisibility(View.GONE);
                        mRadioButtonResidence.setChecked(true);
                    } else {
                        mLinearLayoutBuisness.setVisibility(View.VISIBLE);
                        mRadioButtonBuissness.setChecked(true);
                    }

                   /* mRadioGroupBusinessType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup radioGroup, int i) {
                            if (mRadioButtonSole.isChecked()) {
                                mLinearLayoutREgisterd.setVisibility(View.GONE);
                                mLinearLayoutSole.setVisibility(View.GONE);

                            } else {
                                mLinearLayoutSole.setVisibility(View.GONE);
                                mLinearLayoutREgisterd.setVisibility(View.GONE);

                            }

                        }
                    });*/
                    if (!userdata.getAppUser().getZip().equalsIgnoreCase("")) {
                        mRadioButtonResidence.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                        mRadioButtonResidence.setClickable(false);
                        mRadioButtonResidence.setFocusable(false);
                        mRadioButtonBuissness.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                        mRadioButtonBuissness.setClickable(false);
                        mRadioButtonBuissness.setFocusable(false);
                        mEditTextBuisnessName.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                        mEditTextBuisnessName.setClickable(false);
                        mEditTextBuisnessName.setFocusable(false);
                        mEditTextFederalNumber.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                        mEditTextFederalNumber.setClickable(false);
                        mEditTextFederalNumber.setFocusable(false);
                        mEditTextSSN.setFocusableInTouchMode(false); // user touches widget on phone with touch screen
                        mEditTextSSN.setClickable(false);
                        mEditTextSSN.setFocusable(false);
                        mRadioButtonRegistred.setEnabled(false);
                        mRadioButtonSole.setEnabled(false);
                    }
                    if (userdata.getBusiness() != null) {
                        String ssn = Jsondata.decryptMsg(userdata.getBusiness().getSsn());
                        mEditTextBuisnessName.setText(Jsondata.decryptMsg(userdata.getBusiness().getBusinessName()));
                        if (ssn.length() > 5) {
                            mLinearLayoutSole.setVisibility(View.VISIBLE);
                            mLinearLayoutREgisterd.setVisibility(View.GONE);
                            mEditTextSSN.setText(Jsondata.decryptMsg(userdata.getBusiness().getSsn()));
                        }
                        if (Jsondata.decryptMsg(userdata.getBusiness().getFederalNumber()).length() > 5) {
                            mLinearLayoutSole.setVisibility(View.GONE);
                            mLinearLayoutREgisterd.setVisibility(View.VISIBLE);
                            mEditTextFederalNumber.setText(Jsondata.decryptMsg(userdata.getBusiness().getFederalNumber()));
                        }
                        if (userdata.getBusiness().getBusinessType() == 0) {
                            mRadioButtonSole.setChecked(true);

                        } else {
                            mRadioButtonRegistred.setChecked(true);
                        }

                    }
//                    if (text.equalsIgnoreCase("")) {
                    SetLogin();
//                    }


                    mEditTextMobileNumber.setText(Jsondata.decryptMsg(userdata.getAppUser().getMobile()));

                    if (userdata.getAppUser().getPrimaryIdNumber().contains("===")) {
                        try {
                            String[] separated = userdata.getAppUser().getPrimaryIdNumber().split("===");
                            mSpinnerPrimaryState.setSelection(state.getStates().indexOf(separated[0]));
                            mEditTextPrimaryIdNumber.setText(separated[1]);
                        } catch (Exception e) {
                        }

                    } else {
                        mEditTextPrimaryIdNumber.setText(userdata.getAppUser().getPrimaryIdNumber());
                    }

                    if (userdata.getAppUser().getSecondaryIdNumber().contains("===")) {
                        try {
                            String[] separated = userdata.getAppUser().getSecondaryIdNumber().split("===");
                            mSpinnerSecondaryState.setSelection(state.getStates().indexOf(separated[0]));
                            mEditTextSecondaryNumber.setText(separated[1]);
                        } catch (Exception e) {
                        }

                    } else {
                        mEditTextSecondaryNumber.setText(userdata.getAppUser().getSecondaryIdNumber());
                    }

                    if (userdata.getAppUserAddresses() != null) {
                        mEditTextZipCode.setText(Jsondata.decryptMsg(userdata.getAppUserAddresses().getZip()));
                        mEditTextAddress1.setText(userdata.getAppUserAddresses().getAddress1());
                        mEditTextAddress2.setText(userdata.getAppUserAddresses().getAddress2());
                        mEditTextTownCity.setText(userdata.getAppUserAddresses().getCity());
                        mEditTextAddressName.setText(userdata.getAppUserAddresses().getAddressName());
                        mEditTextAddressZipcode.setText(Jsondata.decryptMsg(userdata.getAppUserAddresses().getZip()));
                        mSpinnerState.setSelection(state.getStates().indexOf(userdata.getAppUserAddresses().getState()));
                    } else {

                    }
                    mEditTextZipCode.setText(Jsondata.decryptMsg(userdata.getAppUser().getZip()));
                    mEditTextEmail.setText(userdata.getAppUser().getEmail());
                    mSpinnerPrimaryId.setSelection(primaryAndSecondaryId.getPrimaryIds().indexOf(userdata.getAppUser().getPrimaryId()));
                    mSpinnerSecondaryId.setSelection(primaryAndSecondaryId.getSecondaryIds().indexOf(userdata.getAppUser().getSecondaryId()));

                }


            } catch (Exception e)

            {
                e.printStackTrace();
            }
            //  mEditTextZipCode.setSelection(mEditTextZipCode.getText().toString().length());
            // mEditTextZipCode.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        }
    }

    public void localyticstagEvent(String method) {
        Map<String, String> home_values = new HashMap<String, String>();

        home_values.put("Success", "Yes");
        home_values.put("Method", method);

        Localytics.tagEvent("Profile", home_values);

    }

}



