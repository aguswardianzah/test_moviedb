package com.example.test_moviedb.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "paging_key", indices = [Index(value = ["name"], unique = true)])
data class PagingKeyEnt(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String = "",
    var key: Int = 1
)