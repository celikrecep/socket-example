package com.loyer.socket_example.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.loyer.socket_example.MainViewModel
import com.loyer.socket_example.base.AppViewModelFactory
import com.loyer.socket_example.di.qualifier.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    abstract fun bindAppViewModelFactory(viewModelFactory: AppViewModelFactory): ViewModelProvider.Factory
}