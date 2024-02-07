package com.example.test_moviedb.di

import android.content.Context
import androidx.room.Room
import com.example.test_moviedb.db.TestDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    fun dbInstance(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, TestDb::class.java, "test.db")
            .fallbackToDestructiveMigration()
            .build()
}