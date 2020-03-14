package com.loyer.socket_example.di

import android.app.Application
import com.loyer.socket_example.BaseApplication
import com.loyer.socket_example.data_manager.shared_pref.di.SharedPrefModule
import com.loyer.socket_example.data_manager.network.di.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        MainAcitivityyModule::class,
        NetworkModule::class,
        SharedPrefModule::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(application: BaseApplication)
}