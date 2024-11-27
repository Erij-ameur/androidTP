package com.example.tp1kotlin.Service

import android.content.Context
import androidx.room.Room
import com.example.tp1kotlin.Data.AppDatabase
import com.example.tp1kotlin.Data.Converters
import com.example.tp1kotlin.Data.FilmDao
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Singleton
    @Provides
    fun providerConverters() : Converters {
        val moshi = Moshi.Builder().build()
        return Converters(moshi)
    }
    @Singleton
    @Provides
    fun provideDb(@ApplicationContext context: Context, converters : Converters)
            : FilmDao =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name"
        )
            .addTypeConverter(converters)
            .fallbackToDestructiveMigration()
            .build().filmDao()


}