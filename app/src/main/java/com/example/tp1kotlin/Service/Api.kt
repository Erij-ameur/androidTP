package com.example.tp1kotlin.Service

import com.example.tp1kotlin.Data.ActorResult
import com.example.tp1kotlin.Data.CastResult
import com.example.tp1kotlin.Data.GenreListResponse
import com.example.tp1kotlin.Data.MovieResult
import com.example.tp1kotlin.Data.SerieResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface Tmdbapi {
    @GET("trending/movie/week")
    suspend fun getTrendingMovies(@Query("api_key") api_key: String,
                                  @Query("language") langage: String): MovieResult

    @GET("search/movie")
    suspend fun searchmovies(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String,
                             @Query("language") langage: String): MovieResult

    @GET("movie/{id}/credits")
    suspend fun getMovieCast(@Path("id") id: Int,
                             @Query("api_key") api_key: String,
                             @Query("language") langage: String): CastResult

    @GET("genre/movie/list")
    suspend fun getGenres(@Query("api_key") api_key: String,
                          @Query("language") language: String): GenreListResponse


    @GET("trending/tv/week")
    suspend fun getTrendingSeries(@Query("api_key") api_key: String): SerieResult

    @GET("search/tv")
    suspend fun searchSeries(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String): SerieResult

    @GET("tv/{id}/credits")
    suspend fun getSerieCast(@Path("id") id: Int,
                             @Query("api_key") api_key: String,
                             @Query("language") langage: String): CastResult

//    @GET("genre/tv/list")
//    suspend fun getGenres(@Query("api_key") api_key: String,
//                          @Query("language") language: String): GenreListResponse

    @GET("trending/person/week")
    suspend fun getTrendingActors(@Query("api_key") api_key: String): ActorResult

    @GET("search/person")
    suspend fun searchActors(@Query("api_key") api_key: String,
                             @Query("query") searchtext: String): ActorResult
}