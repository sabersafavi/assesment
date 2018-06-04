package com.activeperform.saber.assesment.Ui.View;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.activeperform.saber.assesment.Adapters.AutoCompleteAdapter;
import com.activeperform.saber.assesment.BaseActivity;
import com.activeperform.saber.assesment.Models.BikeStation;
import com.activeperform.saber.assesment.R;
import com.activeperform.saber.assesment.Ui.Presenter.MapPresenter;
import com.activeperform.saber.assesment.Ui.Presenter.MapPresenterImpl;
import com.activeperform.saber.assesment.Utils.CustomInfoWindowGoogleMap;
import com.activeperform.saber.assesment.Utils.Util;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsActivity extends BaseActivity implements OnMapReadyCallback, MapsView, GoogleMap.OnMarkerClickListener {

    //    @Inject
    public MapPresenter mapPresenter;

    private GoogleMap mMap;

    ProgressBar progressBar;
    private AutoCompleteTextView actv;


    List<BikeStation> items = null;


    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Double lat = intent.getDoubleExtra("lat",0.0);  //get the type of message from MyGcmListenerService 1 - lock or 0 -Unlock
            Double lng = intent.getDoubleExtra("lng",0.0);  //get the type of message from MyGcmListenerService 1 - lock or 0 -Unlock
            String name = intent.getStringExtra("name");  //get the type of message from MyGcmListenerService 1 - lock or 0 -Unlock


            CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng),16);
            actv.setText(name);
            try{
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(actv.getWindowToken(), 0);
            }
            catch (Exception e){
                e.printStackTrace();
            }
            actv.clearFocus();
            mMap.animateCamera(cu);
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getDeps().inject(this);
        mapPresenter = new MapPresenterImpl(this);
        renderView();
        if (mapPresenter != null)
            mapPresenter.getBikeStationsList();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        //registerReceiver(statusReceiver,mIntent);
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("NOW"));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);

        super.onPause();
    }

    private void renderView() {
        setContentView(R.layout.activity_maps);

        progressBar = findViewById(R.id.progressLoading);
        actv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        if (items != null) {
            showMarkersOnMap(items);
        } else {
            LatLng sydney = new LatLng(-34, 151);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                BikeStation infoWindowData = (BikeStation) marker.getTag();
                openDirection(new LatLng(infoWindowData.getCoordinates().getLatitude(), infoWindowData.getCoordinates().getLongitude()));
            }
        },1000);
        return false;
    }

    private void openDirection(LatLng location) {
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                Uri.parse(String.format("http://maps.google.com/maps?daddr=%s,%s", location.latitude, location.longitude)));
        startActivity(intent);
    }

    //mapsView methods implementation
    @Override
    public void showWait() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void removeWait() {
        progressBar.setVisibility(View.GONE);

    }

    @Override
    public void loadBikeStationsSuccess(List<BikeStation> stationsListResponse) {
        items = stationsListResponse;
        showMarkersOnMap(items);

        //set autocomplete adapter
        ArrayAdapter adapter = new AutoCompleteAdapter(this, android.R.layout.simple_list_item_1, stationsListResponse);

        actv.setAdapter(adapter);
        actv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });
    }

    private void showMarkersOnMap(List<BikeStation> items) {


        if (mMap != null) {
            mMap.clear();


            if (items.size() == 0) return;

            LatLngBounds.Builder builder = new LatLngBounds.Builder();

            int minCount = items.get(0).getBikesCount();
            int maxCount = items.get(0).getBikesCount();

            for (BikeStation station : items) {
                if (minCount > station.getBikesCount())
                    minCount = station.getBikesCount();
                else if (maxCount < station.getBikesCount())
                    maxCount = station.getBikesCount();
            }


            for (BikeStation station : items) {

                int size = 24 + (((station.getBikesCount() - minCount) * 40) / (maxCount - minCount));

                Bitmap bhalfsize = Util.scaleImage(getResources(), R.drawable.marker2, size);
                //
                LatLng stationLocation = new LatLng(station.getCoordinates().getLatitude(), station.getCoordinates().getLongitude());
                //
                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(stationLocation)
                        .title(station.getFeaturename())
                        .icon(BitmapDescriptorFactory.fromBitmap(bhalfsize));

                CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(this);
                mMap.setInfoWindowAdapter(customInfoWindow);

                Marker m = mMap.addMarker(markerOptions);
                m.setTag(station);
//                m.showInfoWindow();


//                mMap.addMarker(new MarkerOptions()
//                        .position(stationLocation)
//                        .title(station.toString())
//                        .icon(BitmapDescriptorFactory.fromBitmap(bhalfsize))
//                );
                builder.include(stationLocation);
            }
            LatLngBounds bounds = builder.build();
            int padding = 200; // offset from edges of the map in pixels
            CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
            mMap.animateCamera(cu);
        }
    }

    @Override
    public void onFailure(String msg) {
        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mapPresenter.getBikeStationsList();
//            }
//        }, 2000);

    }
}
