package com.dasaevcompany.upax.data.database.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dasaevcompany.upax.data.database.provider.MovieDao
import com.dasaevcompany.upax.model.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(Converters::class)
abstract class RoomDatabase : RoomDatabase() {
    abstract fun movieDao() : MovieDao
}