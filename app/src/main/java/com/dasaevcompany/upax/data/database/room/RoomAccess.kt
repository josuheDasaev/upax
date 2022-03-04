package com.dasaevcompany.upax.data.database.room

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomAccess {

    @Provides
    @Singleton
    fun room (application: Application): RoomDatabase {
        return Room.databaseBuilder(application, RoomDatabase::class.java,"Upax")
            .build()
    }
}