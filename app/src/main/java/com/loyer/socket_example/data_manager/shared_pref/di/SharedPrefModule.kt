package com.loyer.socket_example.data_manager.shared_pref.di

import android.content.Context
import android.content.SharedPreferences
import com.loyer.socket_example.data_manager.shared_pref.SharedPref
import com.loyer.socket_example.data_manager.shared_pref.SharedPrefImpl
import com.loyer.socket_example.di.qualifier.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

private const val SHARED_PREFERENCES = "shared_preferences"

@Module
class SharedPrefModule {

    @Named(SHARED_PREFERENCES)
    @Provides
    fun provideBaseUrl(): String = "socket-example"

    @Singleton
    @Provides
    fun provideSharedPrefenreces(
        @ApplicationContext context: Context,
        @Named(SHARED_PREFERENCES) sharedPref: String
    ): SharedPreferences {
        return context.getSharedPreferences(sharedPref, Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSharedPref(sharedPref: SharedPrefImpl): SharedPref {
        return sharedPref
    }
}