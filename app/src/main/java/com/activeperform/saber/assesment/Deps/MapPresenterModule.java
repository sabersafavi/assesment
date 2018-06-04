package com.activeperform.saber.assesment.Deps;

import com.activeperform.saber.assesment.Ui.Presenter.MapPresenter;
import com.activeperform.saber.assesment.Ui.Presenter.MapPresenterImpl;
import com.activeperform.saber.assesment.Ui.View.MapsView;

//@Module
public class MapPresenterModule {

//    @Provides
//    @Singleton
    MapPresenter providesPresenter(MapsView view) {
        return new MapPresenterImpl(view);
    }
}
