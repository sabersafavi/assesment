package com.activeperform.saber.assesment.Ui.View;

import com.activeperform.saber.assesment.Models.BikeStation;

import java.util.List;

public interface MapsView {

    void showWait();
    void removeWait();
    void loadBikeStationsSuccess(List<BikeStation> stationsListResponse);
    void onFailure(String msg);
}
