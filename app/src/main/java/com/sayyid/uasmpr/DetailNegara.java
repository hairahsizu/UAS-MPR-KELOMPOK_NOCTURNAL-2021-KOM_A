package com.sayyid.uasmpr;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.sayyid.uasmpr.models.ResponseDataItem;

import java.text.MessageFormat;

public class DetailNegara extends AppCompatActivity implements OnMapReadyCallback {
    private Gson gson = new Gson();
    private ResponseDataItem responseDataItem;
    private TextView countryName;
    private TextView capital;
    private TextView latlng;

    private GoogleMap map;
    private Double latitude, longitude = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_negara);

        initialize();
        initToolbar();

        initView();
        initData();


    }
    public void initData(){
        countryName.setText(MessageFormat.format("{0} ({1})",
                responseDataItem.getName(), responseDataItem.getCountryCode()));
        capital.setText(MessageFormat.format("Ibu Kota : {0}", responseDataItem.getCapital()));
        latlng.setText(MessageFormat.format("Latitude & Longitude : {0}",
                responseDataItem.getLatlng().toString()));

        //Maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initToolbar(){
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(MessageFormat.format("Detail {0}",
                responseDataItem.getName()));
    }

    private void initialize(){
        gson = new Gson();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            gson = new Gson();
            responseDataItem = gson.fromJson(
                    extras.getString("data_negara"), ResponseDataItem.class);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        countryName = findViewById(R.id.country_name);
        capital = findViewById(R.id.capital);
        latlng = findViewById(R.id.latlng);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        map = googleMap;

        LatLng location = new LatLng(responseDataItem.getLatlng().get(0), responseDataItem.getLatlng().get(1));

        LatLng sydney = new LatLng(location.latitude, location.longitude);
        map.addMarker(new MarkerOptions()
                .position(sydney)
                .title(responseDataItem.getName()));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(location).zoom(5.0f).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
        map.moveCamera(cameraUpdate);

    }

}