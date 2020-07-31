package com.tft_mvvm.data.features.champs.di

import com.tft_mvvm.data.exception_interceptor.RemoteExceptionInterceptor
import com.tft_mvvm.data.features.champs.mapper.ChampDaoEntityMapper
import com.tft_mvvm.data.features.champs.mapper.ChampListMapper
import com.tft_mvvm.data.features.champs.repository.RepoRepositoryImpl
import com.tft_mvvm.data.features.champs.service.ApiService
import com.tft_mvvm.data.local.database.ChampRoomDatabase
import com.tft_mvvm.domain.features.champs.repository.RepoRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {

    factory {
        ChampListMapper()
    }
    factory {
        ChampDaoEntityMapper()
    }

    factory { RemoteExceptionInterceptor() }

    factory<Interceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    factory {

        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl("https://spreadsheets.google.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(ApiService::class.java) }

    single { ChampRoomDatabase.getInstance(androidContext()) }

    single { get<ChampRoomDatabase>().champDAO() }

    single<RepoRepository> {
        RepoRepositoryImpl(
            apiService = get(),
            champDAO = get(),
            remoteExceptionInterceptor = get(),
            champListMapper = get(),
            champDaoEntityMapper = get()
        )
    }
}