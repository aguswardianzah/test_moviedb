package com.example.test_moviedb.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.test_moviedb.db.entities.PagingKeyEnt

@Dao
interface PagingKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: PagingKeyEnt)

    @Query("select * from paging_key where name = :name")
    suspend fun get(name: String): PagingKeyEnt?
}