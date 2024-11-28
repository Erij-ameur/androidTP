package com.example.tp1kotlin.Service

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplicationtest.playlistjson
import com.example.tp1kotlin.Data.Actor
import com.example.tp1kotlin.Data.Cast
import com.example.tp1kotlin.Data.FilmEntity
import com.example.tp1kotlin.Data.Genre
import com.example.tp1kotlin.Data.Movie
import com.example.tp1kotlin.Data.Playlist
import com.example.tp1kotlin.Data.Serie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.squareup.moshi.Moshi

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
                val favoriteFilms = repo.getFavFilms().map { it.fiche.id }
                movies.value = movies.value.map { movie ->
                    movie.copy(isFav = favoriteFilms.contains(movie.id))
                }
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

    private suspend fun refreshMoviesState() {
        val favoriteFilms = repo.getFavFilms().map { it.fiche.id }
        movies.value = movies.value.map { movie ->
            movie.copy(isFav = favoriteFilms.contains(movie.id))
        }
    }

    fun addFavorite(film: Movie) {
        viewModelScope.launch {
            try {
                val filmEntity = FilmEntity(id = film.id, fiche = film)
                repo.insertFilm(filmEntity)
                refreshMoviesState()
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Erreur lors de l'ajout du film aux favoris: ${e.message}")
            }
        }
    }

    fun removeFavorite(film: Movie) {
        viewModelScope.launch {
            try {
                repo.deleteFilm(film.id)
                refreshMoviesState()
            } catch (e: Exception) {
                Log.e("MovieViewModel", "Erreur lors de la suppression du film des favoris: ${e.message}")
            }
        }
    }

    fun onFavoriteClick(movie: Movie) {
        if (movie.isFav) {
            removeFavorite(movie)
        } else {
            addFavorite(movie)
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

class PlaylistViewModel : ViewModel() {
    val playlists = MutableStateFlow<List<Playlist>>(listOf())
    fun fetchPlayList():Playlist{
        val moshi = Moshi.Builder().build()
        return moshi.adapter(Playlist::class.java).fromJson(playlistjson)!!
    }

    fun getPlaylist(){
        viewModelScope.launch {
            playlists.value = listOf(fetchPlayList())
        }
    }
}