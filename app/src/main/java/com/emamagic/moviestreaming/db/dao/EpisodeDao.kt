package com.emamagic.moviestreaming.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emamagic.moviestreaming.base.BaseDao
import com.emamagic.moviestreaming.db.entity.EpisodeEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class EpisodeDao: BaseDao<EpisodeEntity> {

    @Query("SELECT * FROM table_episode WHERE id_season = :id")
    abstract fun getEpisodesById(id: Long): Flow<List<EpisodeEntity>>

}