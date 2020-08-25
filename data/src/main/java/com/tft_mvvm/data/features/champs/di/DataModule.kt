package com.tft_mvvm.data.features.champs.di

import com.tft_mvvm.data.exception_interceptor.RemoteExceptionInterceptor
import com.tft_mvvm.data.features.champs.mapper.*
import com.tft_mvvm.data.features.champs.repository.RepoRepositoryImpl
import com.tft_mvvm.data.features.champs.service.ApiService
import com.tft_mvvm.data.local.database.ChampRoomDatabase
import com.tft_mvvm.domain.features.repository.RepoRepository
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun dataModule(baseUrl: String) = module {

    factory {
        ChampListMapper()
    }
    factory {
        ChampDaoEntityMapper()
    }
    factory {
        TeamDaoEntityMapper()
    }

    factory {
        TeamListMapper()
    }

    factory {
        ClassAndOriginDaoEntityMapper()
    }

    factory {
        RemoteExceptionInterceptor()
    }

    factory {
        ItemDaoEntityMapper()
    }

    factory {
        ItemListMapper()
    }

    factory<Interceptor> {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {

        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .build()
    }

    factory {
        Retrofit.Builder()
            .client(get())
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    factory { get<Retrofit>().create(ApiService::class.java) }

    single { ChampRoomDatabase.getInstance(androidContext()) }

    single { get<ChampRoomDatabase>().champDAO() }

    single { get<ChampRoomDatabase>().teamDAO() }

    single { get<ChampRoomDatabase>().classAndOriginDAO() }

    single { get<ChampRoomDatabase>().itemDAO() }

    single<RepoRepository> {
        RepoRepositoryImpl(
            apiService = get(),
            champDAO = get(),
            teamListMapper = get(),
            teamDaoEntityMapper = get(),
            remoteExceptionInterceptor = get(),
            champListMapper = get(),
            teamDAO = get(),
            classAndOriginDAO = get(),
            classAndOriginDaoEntityMapper = get(),
            itemDAO = get(),
            itemDaoEntityMapper = get(),
            itemListMapper = get(),
            champDaoEntityMapper = get()
        )
    }
}