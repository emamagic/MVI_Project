package com.emamagic.moviestreaming.di

import android.content.Context
import androidx.room.Room
import com.emamagic.moviestreaming.db.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object RoomModuleTest {

    @Provides
    @Named("TEST_DB")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context ,MovieDatabase::class.java)
            .allowMainThreadQueries()
            .build()

}