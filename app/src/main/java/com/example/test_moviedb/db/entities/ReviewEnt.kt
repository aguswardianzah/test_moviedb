package com.example.test_moviedb.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "review", indices = [Index(value = ["ids"], unique = true)])
data class ReviewEnt(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val ids: String = "",
    val movieId: Int = 0,
    val author: String = "",
    val content: String = "",
    @Json(name = "created_at") val createdAt: String = "",
    var photo: String = ""
)