package com.example.test_moviedb.api

import com.example.test_moviedb.api.respMap.RespGenre
import com.example.test_moviedb.api.respMap.RespMovies
import com.example.test_moviedb.api.respMap.RespReview
import com.example.test_moviedb.api.respMap.RespVideos
import com.example.test_moviedb.db.entities.MovieEnt
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("genre/movie/list")
    suspend fun fetchGenre(): RespGenre

    @GET("discover/movie")
    suspend fun fetchMovies(
        @Query(value = "with_genres") genreId: Int,
        @Query(value = "page") page: Int = 1,
        @Query(value = "sort_by") sortBy: String = "popularity.desc"
    ): RespMovies

    @GET("movie/{movieId}")
    suspend fun fetchDetail(@Path(value = "movieId") movieId: Int): MovieEnt

    @GET("movie/{movieId}/videos")
    suspend fun fetchVideo(@Path(value = "movieId") movieId: Int): RespVideos

    @GET("movie/{movieId}/reviews")
    suspend fun fetchReview(
        @Path(value = "movieId") movieId: Int,
        @Query(value = "page") page: Int = 1
    ): RespReview
}