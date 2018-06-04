package com.activeperform.saber.assesment.Ui.Interactor;

import android.util.Log;

import com.activeperform.saber.assesment.BuildConfig;
import com.activeperform.saber.assesment.Models.BikeStation;
import com.activeperform.saber.assesment.Networking.NetworkError;
import com.activeperform.saber.assesment.Networking.NetworkService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MapInteractorImpl implements MapInteractor {

    NetworkService networkService;

    public MapInteractorImpl() {
        this.networkService = provideCall().create(NetworkService.class);
    }

    @Override
    public void LoadBikeStations(final LoadStationsListener callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                networkService.getBikeStationsList()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<ArrayList<BikeStation>>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                callback.onError(new NetworkError(e));

                            }

                            @Override
                            public void onNext(final ArrayList<BikeStation> stations) {
                                callback.onSuccess(stations);

                            }
                        });

            }
        }).start();
    }

    private Retrofit provideCall() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();

                        // Customize the request
                        Request request = original.newBuilder()
                                .header("Content-Type", "application/json")
                                .removeHeader("Pragma")
                                .header("Cache-Control", String.format("max-age=%d", BuildConfig.CACHETIME))
                                .build();

                        okhttp3.Response response = chain.proceed(request);
                        response.cacheResponse();
                        // Customize or return the response
                        return response;
                    }
                })
                .build();


        return new Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .build();
    }
}
