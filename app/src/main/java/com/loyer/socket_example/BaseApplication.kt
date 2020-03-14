package com.loyer.socket_example

import android.app.Activity
import android.app.Application
import com.loyer.socket_example.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class BaseApplication : Application(), HasActivityInjector{
    @Inject
    lateinit var dispatchAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        AppInjector.init(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> =
        dispatchAndroidInjector
}