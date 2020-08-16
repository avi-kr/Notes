package com.abhishek.notes;

import com.abhishek.notes.di.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;

/**
 * Created by Abhishek Kumar on 16/08/20.
 * (c)2020 VMock. All rights reserved.
 */
public class BaseApplication extends DaggerApplication {

    @Override protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().application(this).build();
    }
}
