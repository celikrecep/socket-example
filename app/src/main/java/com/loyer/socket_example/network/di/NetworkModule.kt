package com.loyer.socket_example.network.di

import com.google.gson.Gson
import com.loyer.socket_example.network.AppCallAdapterFacotry
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

private const val TIMEOUT_MILLIS = "timeout_millis"
private const val TIMEOUT_UNIT = "timeout_unit"
private const val API_URL = "api_url"
private const val JSON_CONVERTER = "gson"

@Module
class NetworkModule {
    @Provides
    @Named(API_URL)
    fun providePrimaryURl(): String = ""

    @Provides
    @Named(TIMEOUT_MILLIS)
    fun provideTimeOutMillis() = 30000L

    @Provides
    @Named(TIMEOUT_UNIT)
    fun provideTimeOutUnit() = TimeUnit.MILLISECONDS

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        return httpLoggingInterceptor.apply {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        httpInterceptor: HttpLoggingInterceptor,
        @Named(TIMEOUT_MILLIS) timeOutMillis: Long,
        @Named(TIMEOUT_UNIT) timeOutUnit: TimeUnit
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpInterceptor)
            .readTimeout(timeOutMillis, timeOutUnit)
            .writeTimeout(timeOutMillis, timeOutUnit)
            .connectTimeout(timeOutMillis, timeOutUnit)
            .build()
    }

    @Provides
    @Singleton
    @Named(JSON_CONVERTER)
    fun provideJsonConverterRetrofit(): Gson {
        return Gson().newBuilder().setLenient().create()
    }

    @Provides
    @Singleton
    fun provideJsonConverterFactory(@Named(JSON_CONVERTER) jsonConverter: Gson): Converter.Factory {
        return GsonConverterFactory.create(jsonConverter)
    }

    @Provides
    @Singleton
    fun provideRxJavaAdapterFactory(callAdapterFactory: AppCallAdapterFacotry): CallAdapter.Factory {
        return callAdapterFactory
    }

    @Provides
    @Singleton
    @Named(API_URL)
    fun provideRetrofit(
        @Named(API_URL) url: String,
        converterFactory: Converter.Factory,
        callAdapterFactory: CallAdapter.Factory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(converterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .client(client)
            .build()
    }
}