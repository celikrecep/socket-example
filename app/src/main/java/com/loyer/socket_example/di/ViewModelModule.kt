package com.loyer.socket_example.di

import androidx.lifecycle.ViewModelProvider
import com.loyer.socket_example.base.AppViewModelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindAppViewModelFactory(viewModelFactory: AppViewModelFactory): ViewModelProvider.Factory
}