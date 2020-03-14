package com.loyer.socket_example.di

import com.loyer.socket_example.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MAFragmentsBuilderModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment
}