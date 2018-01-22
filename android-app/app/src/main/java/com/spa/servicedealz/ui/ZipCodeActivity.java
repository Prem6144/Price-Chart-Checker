package com.spa.servicedealz.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.IntentCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.spa.servicedealz.R;
import com.spa.servicedealz.drawer.GuestDashbaordActiviy;
import com.spa.servicedealz.services.MyLocationListener;
import com.spa.utils.Constant;
import com.spa.utils.Validation;

import java.util.List;
import java.util.Locale;

/**
 * Created by Diwakar on 4/28/2016.
 */
public class ZipCodeActivity extends Activity implements LocationListener {


    // Splash screen timer
    private SharedPreferences mSharedPreferences;
    private EditText mEditTextZipCode;
    private Button mButtonSubmit;
    private RadioButton mRadioButtonResidence, mRadioButtonBuissness;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zipcode_popup);
        int density = getResources().getDisplayMetrics().densityDpi;
        switch (density) {

            case DisplayMetrics.DENSITY_TV:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;
            default:
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                break;

        }
        // Set the criteria of what to look for

        mEditTextZipCode = (EditText) findViewById(R.id.ed_zipcode);
        mRadioButtonResidence = (RadioButton) findViewById(R.id.radioGroupButton0);
        mButtonSubmit = (Button) findViewById(R.id.btn_submit);
        mButtonSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Validation.isFieldEmpty(mEditTextZipCode)) {
                    Toast.makeText(ZipCodeActivity.this, getResources().getString(R.string.zipcode_error),
                            Toast.LENGTH_SHORT).show();
                } else if (mEditTextZipCode.getText().toString().length() != 5) {
                    Toast.makeText(ZipCodeActivity.this, getResources().getString(R.string.zipcode_valid_error),
                            Toast.LENGTH_SHORT).show();
                } else {
                    mSharedPreferences = getSharedPreferences(Constant.SHARED_PREF,
                            Activity.MODE_WORLD_READABLE);
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    if (mRadioButtonResidence.isChecked()) {
                        editor.putString("deal_type", "residence");
                    } else {
                        editor.putString("deal_type", "business");
                    }

                    editor.putString("guestzipcode", mEditTextZipCode.getText().toString());
                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), GuestDashbaordActiviy.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        });
        new HttpGetAsyncTask_Get_Data().execute();

    }

    class HttpGetAsyncTask_Get_Data extends AsyncTask<String, Void, String> {
        String response = "", postalCode = "";
        double lat,lon;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ZipCodeActivity.this);
            pDialog.setMessage(Constant.WAIT);
            pDialog.setCancelable(true);
            pDialog.show();
            MyLocationListener mylistner = new MyLocationListener(ZipCodeActivity.this);
             lat = mylistner.latitude;
             lon = mylistner.longitude;
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                try {

                    System.out.println("" + lat + lon);
                    Geocoder geocoder;
                    List<Address> addresses;
                    geocoder = new Geocoder(ZipCodeActivity.this, Locale.getDefault());
                    addresses = geocoder.getFromLocation(lat, lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                    postalCode = addresses.get(0).getPostalCode();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return response;
        }

       /* *
         * After completing background task Dismiss the progress dialog
         * **/

        protected void onPostExecute(String response) {
            pDialog.dismiss();
            mEditTextZipCode.setText(postalCode);
        }
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}