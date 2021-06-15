package com.example.moviedbjm.storage

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

}

fun provideWeatherDatabase(application: Application): MovieDatabase {
    val database: MovieDatabase =
        Room.databaseBuilder(application, MovieDatabase::class.java, "WeatherDatabase.db")
            .build()

    return database
}

fun provideWeatherDao(database: MovieDatabase): MovieDao = database.movieDao()