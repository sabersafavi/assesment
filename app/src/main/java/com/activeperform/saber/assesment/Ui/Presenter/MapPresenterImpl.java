package com.activeperform.saber.assesment.Ui.Presenter;

import com.activeperform.saber.assesment.Models.BikeStation;
import com.activeperform.saber.assesment.Networking.NetworkError;
import com.activeperform.saber.assesment.Networking.NetworkService;
import com.activeperform.saber.assesment.Ui.Interactor.MapInteractor;
import com.activeperform.saber.assesment.Ui.Interactor.MapInteractorImpl;
import com.activeperform.saber.assesment.Ui.View.MapsView;

import java.lang.ref.WeakReference;
import java.util.List;

import javax.inject.Inject;

public class MapPresenterImpl implements MapPresenter {

//    @Inject
    MapInteractor mapInteractor;
    private final WeakReference<MapsView> view;

    public MapPresenterImpl(MapsView view) {
        this.view = new WeakReference<MapsView>(view);
        mapInteractor = new MapInteractorImpl();
    }


    @Override
    public void getBikeStationsList() {
        if(view!= null && view.get()!= null) {
            final MapsView mapsView = view.get();

            if(mapInteractor!= null) {
                mapsView.showWait();

                mapInteractor.LoadBikeStations(new MapInteractor.LoadStationsListener() {
                    @Override
                    public void onSuccess(List<BikeStation> stationsListResponse) {
                        mapsView.removeWait();
                        mapsView.loadBikeStationsSuccess(stationsListResponse);
                    }

                    @Override
                    public void onError(NetworkError networkError) {
                        mapsView.removeWait();
                        mapsView.onFailure(networkError.getAppErrorMessage());
                    }

                });
            }
        }
    }
}
