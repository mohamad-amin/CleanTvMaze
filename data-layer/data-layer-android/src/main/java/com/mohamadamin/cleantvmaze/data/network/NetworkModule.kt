package com.mohamadamin.cleantvmaze.data.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author MohamadAmin Mohamadi (mohammadi.mohamadamin@gmail.com) on 5/1/17.
 * A Module for providing Network Apis and Gson
 */
@Singleton
@Module
class NetworkModule {

    val URL = "http://api.tvmaze.com/"
    private val GSON_VERSION = 1.0

    @Provides
    fun provideShowsAPI(retrofit: Retrofit): ShowsAPI {
        return retrofit.create(ShowsAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(gson: Gson): Converter.Factory {
        return GsonConverterFactory.create(gson)
    }

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setVersion(GSON_VERSION)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()
    }

}