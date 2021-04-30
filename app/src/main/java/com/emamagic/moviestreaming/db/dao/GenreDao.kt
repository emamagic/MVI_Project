package com.emamagic.moviestreaming.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emamagic.moviestreaming.base.BaseDao
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class GenreDao: BaseDao<GenreEntity> {

    @Query("SELECT * FROM table_genre LIMIT 6")
    abstract fun getGenre(): Flow<List<GenreEntity>>

    @Query("SELECT * FROM table_genre")
    abstract fun getAllGenre(): Flow<List<GenreEntity>>
}