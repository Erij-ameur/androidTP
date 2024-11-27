package com.example.tp1kotlin

import com.example.tp1kotlin.Data.ActorResult
import com.example.tp1kotlin.Data.CastResult
import com.example.tp1kotlin.Data.Genre
import com.example.tp1kotlin.Data.GenreListResponse
import com.example.tp1kotlin.Data.MovieResult
import com.example.tp1kotlin.Data.SerieResult
import com.example.tp1kotlin.Service.Tmdbapi
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import retrofit2.http.Query

class FakeTmdbApi : Tmdbapi {
    val moshi: Moshi = Moshi.Builder().build()
    val jsonAdapter: JsonAdapter<MovieResult> = moshi.adapter(MovieResult::class.java)

    val jsonresult = "{\"page\":1,\"results\":[{\"adult\":false,\"backdrop_path\":\"/rOmUuQEZfPXglwFs5ELLLUDKodL.jpg\",\"genre_ids\":[28,35,14],\"id\":845781,\"original_language\":\"en\",\"original_title\":\"Red One\",\"overview\":\"After Santa Claus (codename: Red One) is kidnapped, the North Pole's Head of Security must team up with the world's most infamous bounty hunter in a globe-trotting, action-packed mission to save Christmas.\",\"popularity\":1046.567,\"poster_path\":\"/cdqLnri3NEGcmfnqwk2TSIYtddg.jpg\",\"release_date\":\"2024-10-31\",\"title\":\"Red One\",\"video\":false,\"vote_average\":6.6,\"vote_count\":161}],\"total_pages\":1,\"total_results\":1}"

    override suspend fun getTrendingMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String
    ): MovieResult {
        val res = jsonAdapter.fromJson(jsonresult)
        return res ?: MovieResult(
            page = 0,
            results = emptyList(),
            total_pages = 0,
            total_results = 0
        )
    }

    override suspend fun searchmovies(
        api_key: String,
        searchtext: String,
        langage: String
    ): MovieResult {
        TODO("Not yet implemented")
    }

    override suspend fun getMovieCast(id: Int, api_key: String, langage: String): CastResult {
        TODO("Not yet implemented")
    }

    override suspend fun getGenres(api_key: String, language: String): GenreListResponse {
        return GenreListResponse(
            genres = listOf(
                Genre(id = 28, name = "Action"),
                Genre(id = 35, name = "Comedy"),
                Genre(id = 18, name = "Drama"),
                Genre(id = 27, name = "Horror"),
                Genre(id = 10749, name = "Romance")
            )
        )
    }

    override suspend fun getTrendingSeries(api_key: String): SerieResult {
        TODO("Not yet implemented")
    }

    override suspend fun searchSeries(api_key: String, searchtext: String): SerieResult {
        TODO("Not yet implemented")
    }

    override suspend fun getSerieCast(id: Int, api_key: String, langage: String): CastResult {
        TODO("Not yet implemented")
    }

    override suspend fun getTrendingActors(api_key: String): ActorResult {
        TODO("Not yet implemented")
    }

    override suspend fun searchActors(api_key: String, searchtext: String): ActorResult {
        TODO("Not yet implemented")
    }
}
