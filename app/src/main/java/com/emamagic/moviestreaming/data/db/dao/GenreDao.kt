package com.emamagic.moviestreaming.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emamagic.moviestreaming.ui.base.BaseDao
import com.emamagic.moviestreaming.data.db.entity.GenreEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class GenreDao: BaseDao<GenreEntity> {

    @Query("SELECT * FROM table_genre LIMIT 6")
    abstract fun getGenre(): Flow<List<GenreEntity>>

    @Query("SELECT * FROM table_genre")
    abstract fun getAllGenre(): Flow<List<GenreEntity>>
}