package com.example.moviedbjm.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {
    @Query("SELECT * FROM MovieHistoryTable")
    suspend fun getMovieRecordsFromDb(): List<MovieEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovieRecords(entities: List<MovieEntity>)
}