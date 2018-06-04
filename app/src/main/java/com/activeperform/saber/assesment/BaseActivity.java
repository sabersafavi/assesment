package com.activeperform.saber.assesment;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity  extends FragmentActivity {
    //Deps deps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        File cacheFile = new File(getCacheDir(), "responses");
        //deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();

    }

//    public Deps getDeps() {
//        return deps;
//    }
}

