package com.emamagic.moviestreaming.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emamagic.moviestreaming.base.BaseDao
import com.emamagic.moviestreaming.db.entity.MovieEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class MovieDao: BaseDao<MovieEntity> {

    @Query("SELECT * FROM table_movie WHERE category_name = :category  LIMIT 6")
    abstract fun getMoviesByCategory(category: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM table_movie WHERE category_name = :category")
    abstract fun getAllMovie(category: String): Flow<List<MovieEntity>>

    @Query("SELECT * FROM table_movie WHERE id = :id")
    abstract suspend fun getMovieById(id: Long): MovieEntity

    @Query("UPDATE table_movie SET description = :description ,link_img_movie = :imageVideoLink ,link_video = :videoLink  WHERE id = :id")
    abstract suspend fun updateMovieDetail(id: Long, description: String? ,imageVideoLink: String? ,videoLink: String?)

    @Query("SELECT * FROM table_movie WHERE genre_name = :genre")
    abstract fun getMoviesByGenre(genre: String): Flow<List<MovieEntity>>

    @Query("UPDATE table_movie SET isFavorite = CASE WHEN isFavorite = 0 THEN 1 ELSE 0 END WHERE id = :id")
    abstract suspend fun updateFavoriteById(id: Long)

    @Query("SELECT * FROM table_movie WHERE isFavorite = 1")
    abstract fun getFavoriteMovies(): Flow<List<MovieEntity>>

}