package com.example.tp1kotlin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repo: Repository

) : ViewModel() {
    val movies = MutableStateFlow<List<Movie>>(listOf())
    val genres = MutableStateFlow<List<Genre>>(listOf())

    val Searched_movies = MutableStateFlow<List<Movie>>(listOf())
    var searchText = MutableStateFlow("")


    fun getMovies() {
        viewModelScope.launch {
            try {
                genres.value = repo.getGenres()
                movies.value = repo.getFilms()
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Erreur lors de la récupération des films: ${e.message}")
            }
        }
    }

    fun getSearchedMovies() {
        viewModelScope.launch {
            try {
                Searched_movies.value = repo.searchmovies(searchText.value)
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
                movieCast.value = repo.getMovieCast(movieId)
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Error fetching cast: ${e.message}")
            }
        }
    }

}

@HiltViewModel
class SerieViewModel @Inject constructor(private val repo: Repository) : ViewModel() {
    val series = MutableStateFlow<List<Serie>>(listOf())
    val genres = MutableStateFlow<List<Genre>>(listOf())
    val Searched_series = MutableStateFlow<List<Serie>>(listOf())
    var searchText = MutableStateFlow("")

    fun getSeries() {
        viewModelScope.launch {
            try {
                genres.value = repo.getGenres()
                series.value = repo.getSeries()
            } catch (e: Exception) {
                Log.e("SerieViewModel", "Erreur lors de la récupération des series: ${e.message}")
            }
        }
    }


    fun getSearchedSeries() {
        viewModelScope.launch {
            try {
                Searched_series.value = repo.searchSeries(searchText.value)
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

    fun getSerieCast(serieId: Int) {
        viewModelScope.launch {
            try {
                serieCast.value = repo.getSerieCast(serieId)
            } catch (e: Exception) {
                Log.e("SerieViewModel", "Error fetching cast: ${e.message}")
            }
        }
    }

}


@HiltViewModel
class ActorViewModel @Inject constructor(private val repo: Repository) : ViewModel() {
    val actors = MutableStateFlow<List<Actor>>(listOf())

    val Searched_actors = MutableStateFlow<List<Actor>>(listOf())
    var searchText = MutableStateFlow("")


    fun getActors() {
        viewModelScope.launch {
            try {
                actors.value = repo.getActors()
            } catch (e: Exception) {
                Log.e("SerieViewModel", "Erreur lors de la récupération des series: ${e.message}")
            }
        }
    }

    fun getSearchedActors() {
        viewModelScope.launch {
            try {
                Searched_actors.value = repo.searchActors(searchText.value)
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