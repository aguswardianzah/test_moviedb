package com.example.test_moviedb.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.test_moviedb.db.entities.MovieEnt

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<MovieEnt>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: MovieEnt)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(movie: MovieEnt)

    @Query("delete from movie where genreId = :genreId")
    suspend fun clearByGenre(genreId: Int)

    @Query("select * from movie where genreId = :genreId")
    fun get(genreId: Int): PagingSource<Int, MovieEnt>
}