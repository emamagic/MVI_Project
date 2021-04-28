package com.emamagic.moviestreaming.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emamagic.moviestreaming.base.BaseDao
import com.emamagic.moviestreaming.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao: BaseDao<MovieEntity> {

    @Query("SELECT * FROM table_movie WHERE category_name = :category")
    abstract fun getMovie(category: String): Flow<List<MovieEntity>>

}