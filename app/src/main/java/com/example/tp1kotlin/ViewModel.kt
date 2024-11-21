package com.example.tp1kotlin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class MovieViewModel() : ViewModel() {
    val movies = MutableStateFlow<List<Movie>>(listOf())
    val genres = MutableStateFlow<List<Genre>>(listOf())

    val Searched_movies = MutableStateFlow<List<Movie>>(listOf())
    var searchText = MutableStateFlow("")


    val api_key="474915450c136f48794281389330d269"
    val language="en"
    val api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(MovieApi::class.java);

    fun getMovies() {
        viewModelScope.launch {
            try {
                genres.value = api.getGenres(api_key, language).genres
                movies.value = api.getTrendingMovies(api_key,language).results
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Erreur lors de la récupération des films: ${e.message}")
            }
        }
    }

    fun getSearchedMovies() {
        viewModelScope.launch {
            try {
                Searched_movies.value = api.searchmovies(api_key,searchText.value,language).results
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Erreur lors de la récupération des films: ${e.message}")
            }
        }
    }

    fun observeSearchTextChanges() {
        viewModelScope.launch {
            searchText.collect { text ->
                if (text.isNotEmpty()) {
                    getSearchedMovies()
                } else {
                    getMovies()
                }
            }
        }
    }

    fun getGenreNames(genreIds: List<Int>): List<String> {
        return genreIds.mapNotNull { genreId ->
            genres.value.find { it.id == genreId }?.name
        }
    }

    val movieCast = MutableStateFlow<List<Cast>>(emptyList())

    fun getMovieCast(movieId: Int) {
        viewModelScope.launch {
            try {
                movieCast.value = api.getMovieCast(movieId, api_key, language).cast
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching cast: ${e.message}")
            }
        }
    }

}


class SerieViewModel() : ViewModel() {
    val series = MutableStateFlow<List<Serie>>(listOf())
    val genres = MutableStateFlow<List<Genre>>(listOf())
    val Searched_series = MutableStateFlow<List<Serie>>(listOf())
    var searchText = MutableStateFlow("")

    val api_key="474915450c136f48794281389330d269"

    val api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(SerieApi::class.java);

    fun getSeries() {
        viewModelScope.launch {
            try {
                genres.value = api.getGenres(api_key, language).genres
                series.value = api.getTrendingSeries(api_key).results
            } catch (e: Exception) {
                Log.e("SerieViewModel", "Erreur lors de la récupération des series: ${e.message}")
            }
        }
    }


    fun getSearchedSeries() {
        viewModelScope.launch {
            try {
                Searched_series.value = api.searchSeries(api_key,searchText.value).results
            } catch (e: Exception) {
                Log.e("SerieViewModel", "Erreur lors de la récupération des series:  ${e.message}")
            }
        }
    }

    fun observeSearchTextChanges() {
        viewModelScope.launch {
            searchText.collect { text ->
                if (text.isNotEmpty()) {
                    getSearchedSeries()
                } else {
                    getSeries()
                }
            }
        }
    }

    fun getGenreNames(genreIds: List<Int>): List<String> {
        return genreIds.mapNotNull { genreId ->
            genres.value.find { it.id == genreId }?.name
        }
    }

    val serieCast = MutableStateFlow<List<Cast>>(emptyList())
    val language="en"

    fun getSerieCast(serieId: Int) {
        viewModelScope.launch {
            try {
                serieCast.value = api.getSerieCast(serieId, api_key, language).cast
            } catch (e: Exception) {
                Log.e("SerieViewModel", "Error fetching cast: ${e.message}")
            }
        }
    }

}


class ActorViewModel() : ViewModel() {
    val actors = MutableStateFlow<List<Actor>>(listOf())

    val Searched_actors = MutableStateFlow<List<Actor>>(listOf())
    var searchText = MutableStateFlow("")

    val api_key="474915450c136f48794281389330d269"

    val api = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(ActorApi::class.java);

    fun getActors() {
        viewModelScope.launch {
            try {
                actors.value = api.getTrendingActors(api_key).results
            } catch (e: Exception) {
                Log.e("SerieViewModel", "Erreur lors de la récupération des series: ${e.message}")
            }
        }
    }

    fun getSearchedActors() {
        viewModelScope.launch {
            try {
                Searched_actors.value = api.searchActors(api_key,searchText.value).results
            } catch (e: Exception) {
                Log.e("ActorViewModel", "Erreur lors de la récupération des acteurs:  ${e.message}")
            }
        }
    }

    fun observeSearchTextChanges() {
        viewModelScope.launch {
            searchText.collect { text ->
                if (text.isNotEmpty()) {
                    getSearchedActors()
                } else {
                    getActors()
                }
            }
        }
    }
}