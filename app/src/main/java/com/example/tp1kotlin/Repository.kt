package com.example.tp1kotlin
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: Tmdbapi,
) {
    private val api_key="474915450c136f48794281389330d269"
    private val language="en"


    suspend fun getFilms() = api.getTrendingMovies(api_key,language).results
    suspend fun getGenres() = api.getGenres(api_key, language).genres
    suspend fun searchmovies(searchText: String) = api.searchmovies(api_key,searchText,language).results
    suspend fun getMovieCast(movieId: Int) = api.getMovieCast(movieId, api_key, language).cast

    suspend fun getSeries() = api.getTrendingSeries(api_key).results
    suspend fun searchSeries(searchText: String) = api.searchSeries(api_key,searchText).results
    suspend fun getSerieCast(serieId: Int) = api.getSerieCast(serieId, api_key, language).cast

    suspend fun getActors() = api.getTrendingActors(api_key).results
    suspend fun searchActors(searchText: String) = api.searchActors(api_key,searchText).results

}
