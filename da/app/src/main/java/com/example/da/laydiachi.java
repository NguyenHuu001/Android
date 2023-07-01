package com.example.da;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.da.dulieu.GPSTracker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class laydiachi extends AppCompatActivity {

    private static final int REQUEST_LOCATION_PERMISSION = 1;

    private TextView addressTextView;
    private Button getAddressButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laydiachi);
        addressTextView = findViewById(R.id.addressTextView);
        getAddressButton = findViewById(R.id.getAddressButton);

        getAddressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(
                        laydiachi.this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                        PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(laydiachi.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION_PERMISSION);
                } else {
                    getCurrentAddress();
                }
            }
        });
    }
    private void getCurrentAddress() {
        GPSTracker gpsTracker = new GPSTracker(laydiachi.this);
        Location location = gpsTracker.getLocation();

        if (location != null) {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            addressTextView.setText("Latitude: "+latitude+"\nLongitude: "+longitude);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentAddress();
            }
        }
    }
}