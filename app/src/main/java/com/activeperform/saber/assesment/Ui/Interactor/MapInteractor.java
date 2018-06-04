package com.activeperform.saber.assesment.Ui.Interactor;

import com.activeperform.saber.assesment.Models.BikeStation;
import com.activeperform.saber.assesment.Networking.NetworkError;

import java.util.List;

public interface MapInteractor {

    interface LoadStationsListener{
        void onSuccess(List<BikeStation> countries);

        void onError(NetworkError networkError);
    }

    void LoadBikeStations(LoadStationsListener callback);

}
