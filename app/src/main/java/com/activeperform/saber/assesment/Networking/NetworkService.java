package com.activeperform.saber.assesment.Networking;


import com.activeperform.saber.assesment.Models.BikeStation;

import java.util.ArrayList;

import retrofit2.http.GET;
import rx.Observable;


public interface NetworkService {

    @GET("resource/tdvh-n9dv.json")
    Observable<ArrayList<BikeStation>> getBikeStationsList();

}
