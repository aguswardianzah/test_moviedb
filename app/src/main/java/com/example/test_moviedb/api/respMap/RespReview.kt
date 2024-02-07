package com.example.test_moviedb.api.respMap

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class RespReview(
    val page: Int = 1,
    val results: List<Review> = emptyList(),
    @Json(name = "total_pages") val totalPages: Int = 1
)

@Keep
@JsonClass(generateAdapter = true)
data class Review(
    val id: String = "",
    val author: String = "",
    val content: String = "",
    @Json(name = "created_at") val createdAt: String = "",
    @Json(name = "author_details") val authorDetail: Author = Author()
)

@Keep
@JsonClass(generateAdapter = true)
data class Author(
    @Json(name = "avatar_path") val avatar: String? = ""
)

