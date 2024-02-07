package com.example.test_moviedb.api.respMap

import androidx.annotation.Keep
import com.example.test_moviedb.db.entities.GenreEnt
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class RespGenre(val genres: List<GenreEnt>)