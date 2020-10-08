package com.praveen.geosuite;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class UtmcalcActivity extends AppCompatActivity {

    private float longFloat;
    private float zoneFloat;
    private int zone;
    FusedLocationProviderClient flp;
    ImageView ivResult;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_utmcalc);
        EditText editText = (EditText) findViewById(R.id.longInput);

        flp = LocationServices.getFusedLocationProviderClient(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint({"ResourceAsColor", "SetTextI18n"})
    public void calcZone(View view) {
        EditText editText = (EditText) findViewById(R.id.longInput);
        TextView zoneText = (TextView) findViewById(R.id.zoneText);
        TextView zoneT = (TextView) findViewById(R.id.zoneT);
        CardView zoneCard = (CardView) findViewById(R.id.zoneCard);
        if (editText.getText().toString().matches("")) {
            zoneText.setTextColor(ContextCompat.getColor(this, R.color.red));
            zoneText.setText("Please Enter a value");
        } else {
            InputMethodManager imManager = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
            View view1 = this.getCurrentFocus();
            if (view1 == null) {
                view1 = new View(this);
            }
            imManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
            longFloat = Float.parseFloat(editText.getText().toString());
            if (longFloat > 180.0 || longFloat < -180.0) {
                String zoneInfo = "Enter a valid Longitude value";
                zoneText.setTextColor(ContextCompat.getColor(this, R.color.red));
                zoneText.setText(zoneInfo);

            } else {
                zoneFloat = 31 + (longFloat / 6);
                zone = (int) zoneFloat;
                String zoneInfo = "The zone is " + zone;
                zoneText.setTextColor(Color.parseColor("#000000"));
                zoneText.setText(zoneInfo);
                zoneT.setText("" + zone);
                zoneCard.setVisibility(View.VISIBLE);
            }
        }
    }

    public void getLoc(View view) {
        if (ActivityCompat.checkSelfPermission(UtmcalcActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationManager lm = (LocationManager)this.getSystemService(this.LOCATION_SERVICE);
        boolean gps_en = false;
        boolean network_en = false;
        try {
            gps_en = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception ex){}
        try {
            network_en = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_en && !network_en) {
            // notify user
            new AlertDialog.Builder(this)
                    .setMessage("GPS Location service not enabled")
                    .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                            UtmcalcActivity.this.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                            .setNegativeButton("Cancel",null)
                            .show();
        }

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setInterval(60000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if (location != null) {
                        double longLoc = location.getLongitude();
                        EditText editText = (EditText) findViewById(R.id.longInput);
                        editText.setText(String.valueOf(longLoc));
                        Toast.makeText(UtmcalcActivity.this,"LocReq:"+String.valueOf(longLoc),Toast.LENGTH_SHORT).show();
                    }
                }
            }
        };
        LocationServices.getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
    }
}
