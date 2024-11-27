package com.example.tp1kotlin.Service

import com.example.tp1kotlin.Data.FilmDao
import com.example.tp1kotlin.FakeTmdbApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Qualifier annotation class FakeApi
@Qualifier
annotation class RealApi


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @RealApi
    @Provides
    @Singleton
    fun provideTmdbApi(): Tmdbapi =
        Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(Tmdbapi::class.java)

    @FakeApi
    @Singleton
    @Provides
    fun provideFakeTmdbApi() : Tmdbapi { return FakeTmdbApi() }

    @Provides
    @Singleton
    fun provideRepository(@RealApi api: Tmdbapi, db: FilmDao): Repository = Repository(api, db)
}

