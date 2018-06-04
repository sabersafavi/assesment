package com.activeperform.saber.assesment.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeperform.saber.assesment.Models.BikeStation;
import com.activeperform.saber.assesment.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowGoogleMap  implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.layout_station_info, null);

        TextView name_tv = view.findViewById(R.id.name);


        BikeStation infoWindowData = (BikeStation) marker.getTag();
        name_tv.setText(infoWindowData.toString2());

        return view;
    }
}

