package com.example.tp1kotlin


import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.tp1kotlin.Data.AppDatabase
import com.example.tp1kotlin.Data.Converters
import com.example.tp1kotlin.Data.FilmDao
import com.squareup.moshi.Moshi
import dagger.Provides
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@HiltAndroidApp
class MyApplication : Application()

