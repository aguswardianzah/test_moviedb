package com.example.test_moviedb.db.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
@Entity(tableName = "genre", indices = [Index(value = ["id"], unique = true)])
data class GenreEnt(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = ""
)