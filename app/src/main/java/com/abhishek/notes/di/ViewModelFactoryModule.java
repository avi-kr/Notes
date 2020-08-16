package com.abhishek.notes.di;

import androidx.lifecycle.ViewModelProvider;
import com.abhishek.notes.viewmodels.ViewModelProviderFactory;
import dagger.Binds;
import dagger.Module;

/**
 * Created by Abhishek Kumar on 16/08/20.
 * (c)2020 VMock. All rights reserved.
 */

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory viewModelProviderFactory);
}