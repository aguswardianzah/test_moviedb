package com.example.test_moviedb.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.test_moviedb.db.dao.GenreDao
import com.example.test_moviedb.db.dao.MovieDao
import com.example.test_moviedb.db.dao.PagingKeyDao
import com.example.test_moviedb.db.dao.ReviewDao
import com.example.test_moviedb.db.entities.GenreEnt
import com.example.test_moviedb.db.entities.MovieEnt
import com.example.test_moviedb.db.entities.PagingKeyEnt
import com.example.test_moviedb.db.entities.ReviewEnt

@Database(
    version = 6,
    exportSchema = false,
    entities = [GenreEnt::class, MovieEnt::class, PagingKeyEnt::class, ReviewEnt::class]
)
abstract class TestDb : RoomDatabase() {

    abstract fun genre(): GenreDao

    abstract fun movie(): MovieDao

    abstract fun review(): ReviewDao

    abstract fun pagingKey(): PagingKeyDao

}