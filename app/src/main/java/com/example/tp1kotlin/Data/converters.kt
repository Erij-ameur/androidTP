package com.example.tp1kotlin.Data

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types

@ProvidedTypeConverter
class Converters(moshi: Moshi) {
    val filmJsonadapter = moshi.adapter(Movie::class.java)

    @TypeConverter
    fun StringToTmdbMovie(value: String): Movie? {
        return filmJsonadapter.fromJson(value)
    }

    @TypeConverter
    fun TmdbMovieToString(film: Movie): String {
        return filmJsonadapter.toJson(film)
    }
}

