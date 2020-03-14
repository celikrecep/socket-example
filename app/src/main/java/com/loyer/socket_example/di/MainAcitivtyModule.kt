package com.loyer.socket_example.di

import com.loyer.socket_example.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainAcitivityyModule {
    @ContributesAndroidInjector(modules = [MAFragmentsBuilderModule::class])
    abstract fun contributeMainActivity(): MainActivity
}