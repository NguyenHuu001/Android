package com.example.da;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.example.da.Map.REQUEST_LOCATION_PERMISSION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.da.dulieu.GPSTracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Map extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap myMap;
    SupportMapFragment mapFragment;
    SearchView searchView;
    static final int REQUEST_LOCATION_PERMISSION = 1;
    private int  ACCESS_LOCATION_REQUEST_CODE  = 1001;
    private Button getAddressButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        searchView = findViewById(R.id.sv_location);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                String location = searchView.getQuery().toString();
                List<Address> addressesList = null;
                if(location != null || !location.equals("")){
                    Geocoder  geocoder = new Geocoder(Map.this);
                    try{
                        addressesList = geocoder.getFromLocationName(location,1);
                    }catch (IOException e){
                        e.printStackTrace();
                    }
                    Address address = addressesList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(),address.getLongitude());
                    myMap.addMarker(new MarkerOptions().position(latLng).title(location));
                    myMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//                    float zoomLevel = 1.0f; //Độ zoom muốn hiển thị
//                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel);
//                    myMap.moveCamera(cameraUpdate);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        myMap = googleMap;
        //lệnh liên quan đến tìm kiếm
        if(ContextCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION) == getPackageManager().PERMISSION_GRANTED){
            enableuserlocation();
        }else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,ACCESS_FINE_LOCATION)){
                ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},ACCESS_LOCATION_REQUEST_CODE);
            }
            else
            {
                ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},ACCESS_LOCATION_REQUEST_CODE);
            }
        }
        //Hàm lấy vị trí hiện tại
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        LatLng currentLocation = new LatLng(latitude, longitude);
        myMap.addMarker(new MarkerOptions().position(currentLocation).title("Địa chỉ của tôi"));
//        LatLng delhi = new LatLng(-34,151);
//        myMap.addMarker(new MarkerOptions().position(delhi).title("Market in Delhi"));
//        myMap.moveCamera(CameraUpdateFactory.newLatLng(delhi));
    }

    private void enableuserlocation()
    {
        if (ActivityCompat.checkSelfPermission(this,ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ){
            return;
        }
        myMap.setMyLocationEnabled(true);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == ACCESS_LOCATION_REQUEST_CODE){
            if(grantResults.length > 0 && grantResults[0] == getPackageManager().PERMISSION_GRANTED){
                enableuserlocation();
            }
        }
    }
}
