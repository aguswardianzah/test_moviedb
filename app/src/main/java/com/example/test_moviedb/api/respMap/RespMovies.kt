package com.example.test_moviedb.api.respMap

import androidx.annotation.Keep
import com.example.test_moviedb.db.entities.MovieEnt
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class RespMovies(
    val page: Int = 0,
    val results: List<MovieEnt> = emptyList(),
    @Json(name = "total_pages") val totalPages: Int = 0
)

@Keep
@JsonClass(generateAdapter = true)
data class RespVideos(
    val results: List<Videos> = emptyList(),
)

@Keep
@JsonClass(generateAdapter = true)
data class Videos(
    val site: String = "",
    val type: String = "",
    val key: String = ""
)

