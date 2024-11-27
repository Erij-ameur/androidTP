package com.example.tp1kotlin.Data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FilmEntity(
    @PrimaryKey val id: Int,
    val fiche: Movie,)

