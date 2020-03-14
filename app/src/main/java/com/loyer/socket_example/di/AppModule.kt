package com.loyer.socket_example.di

import android.app.Application
import android.content.Context
import com.loyer.socket_example.di.qualifier.ApplicationContext
import dagger.Binds
import dagger.Module

@Module(includes = [ViewModelModule::class])
abstract class AppModule {

    @Binds
    @ApplicationContext
    abstract fun bindContext(application: Application): Context
}