package com.example.test_moviedb.db.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
@Entity(tableName = "movie", indices = [Index(value = ["id"], unique = true)])
data class MovieEnt(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    val overview: String = "",
    var tagline: String = "",
    var genreId: Int = 0,
    @Json(name = "poster_path") var poster: String = "",
    @Json(name = "release_date") val release: String = "",
    var releaseYear: String = "",
    @Json(name = "vote_average") val vote: Float = 0f,
    var youtubeTrailerId: String = "",
)