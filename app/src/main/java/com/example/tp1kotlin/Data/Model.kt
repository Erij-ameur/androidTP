package com.example.tp1kotlin.Data

data class MovieResult(
    val page: Int,
    val results: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)

data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val media_type: String,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int,
    val isFav: Boolean = false
)

data class Genre(
    val id: Int,
    val name: String
)

data class GenreListResponse(
    val genres: List<Genre>
)


data class CastResult(
    val cast: List<Cast>,
    val id: Int
)

data class Cast(
    val adult: Boolean,
    val character: String,
    val credit_id: String,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val name: String,
    val order: Int,
    val original_name: String,
    val popularity: Double,
    val profile_path: String
)

data class SerieResult(
    val page: Int,
    val results: List<Serie>,
    val total_pages: Int,
    val total_results: Int
)

data class Serie(
    val adult: Boolean,
    val backdrop_path: String,
    val first_air_date: String,
    val genre_ids: List<Int>,
    val id: Int,
    val media_type: String,
    val name: String,
    val origin_country: List<String>,
    val original_language: String,
    val original_name: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val vote_average: Double,
    val vote_count: Int,
    val isFav: Boolean = false
)

data class ActorResult(
    val page: Int,
    val results: List<Actor>,
    val total_pages: Int,
    val total_results: Int
)

data class Actor(
    val adult: Boolean,
    val gender: Int,
    val id: Int,
    val known_for_department: String,
    val media_type: String,
    val name: String,
    val original_name: String,
    val popularity: Double,
    val profile_path: String,
    val isFav: Boolean = false
)


data class Playlist(
    val checksum: String,
    val collaborative: Boolean,
    val cover: String,
    val creation_date: String,
    val creator: Creator,
    val description: String,
    val duration: Int,
    val fans: Int,
    val id: Int,
    val is_loved_track: Boolean,
    val link: String,
    val md5_image: String,
    val nb_tracks: Int,
    val picture_type: String,
    val `public`: Boolean,
    val share: String,
    val title: String,
    val tracklist: String,
    val tracks: Tracks,
    val type: String
)

data class Creator(
    val id: Int,
    val name: String,
    val tracklist: String,
    val type: String
)

data class Artist(
    val id: Int,
    val link: String,
    val name: String,
    val tracklist: String,
    val type: String
)


data class Album(
    val cover: String,
    val id: Int,
    val md5_image: String,
    val title: String,
    val tracklist: String,
    val type: String,
    val upc: String
)

data class Data(
    val album: Album,
    val artist: Artist,
    val duration: Int,
    val explicit_content_cover: Int,
    val explicit_content_lyrics: Int,
    val explicit_lyrics: Boolean,
    val id: Long,
    val isrc: String,
    val link: String,
    val md5_image: String,
    val preview: String,
    val rank: Int,
    val readable: Boolean,
    val time_add: Int,
    val title: String,
    val title_short: String,
    val title_version: String,
    val type: String
)


data class Tracks(
    val checksum: String,
    val `data`: List<Data>
)