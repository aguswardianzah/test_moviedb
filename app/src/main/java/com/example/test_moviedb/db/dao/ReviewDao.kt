package com.example.test_moviedb.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test_moviedb.db.entities.ReviewEnt

@Dao
interface ReviewDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<ReviewEnt>)

    @Query("delete from review where movieId = :movieId")
    suspend fun clearByMovie(movieId: Int)

    @Query("select * from review where movieId = :movieId")
    fun get(movieId: Int): PagingSource<Int, ReviewEnt>
}