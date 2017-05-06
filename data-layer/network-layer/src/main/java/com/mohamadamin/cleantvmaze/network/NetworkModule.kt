package com.mohamadamin.cleantvmaze.data.network

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mohamadamin.cleantvmaze.domain.repository.datasource.RetrieveShowDataSource
import com.mohamadamin.cleantvmaze.domain.utils.Constants
import com.mohamadamin.cleantvmaze.network.api.ShowsAPI
import com.mohamadamin.cleantvmaze.network.datasource.NetworkShowDatasource
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
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
    @Named(Constants.DATASOURCE_NETWORK)
    fun provideNetworkShowDataSource(showsApi: ShowsAPI): RetrieveShowDataSource =
            NetworkShowDatasource(showsApi)

    @Provides
    fun provideShowsAPI(retrofit: Retrofit): ShowsAPI =
            retrofit.create(ShowsAPI::class.java)

    @Provides
    fun provideRetrofit(converterFactory: Converter.Factory): Retrofit =
            Retrofit.Builder()
                .baseUrl(URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(converterFactory)
                .build()

    @Provides
    fun provideConverterFactory(gson: Gson): Converter.Factory =
            GsonConverterFactory.create(gson)

    @Provides
    fun provideGson(): Gson =
            GsonBuilder().setVersion(GSON_VERSION)
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create()

}