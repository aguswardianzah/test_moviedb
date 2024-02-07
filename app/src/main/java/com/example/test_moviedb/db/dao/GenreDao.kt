package com.example.test_moviedb.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test_moviedb.db.entities.GenreEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface GenreDao {

    @Query("select * from genre order by name")
    fun get(): Flow<List<GenreEnt>>

    @Query("select * from genre where id = :genreId")
    fun get(genreId: Int): Flow<GenreEnt?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<GenreEnt>)

    @Query("delete from genre")
    suspend fun clear()
}