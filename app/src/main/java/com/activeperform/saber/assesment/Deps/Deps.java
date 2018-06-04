package com.activeperform.saber.assesment.Deps;


import com.activeperform.saber.assesment.Networking.NetworkModule;
import com.activeperform.saber.assesment.Ui.Presenter.MapPresenter;
import com.activeperform.saber.assesment.Ui.View.MapsActivity;
import com.activeperform.saber.assesment.Ui.View.MapsView;

import javax.inject.Singleton;

import dagger.Component;

//inject network module
//@Singleton
//@Component(modules = {NetworkModule.class,MapPresenterModule.class})
public interface Deps {

    MapPresenter provideMapPresenter(MapsView view);
    void inject(MapsActivity mapsActivity);
}
