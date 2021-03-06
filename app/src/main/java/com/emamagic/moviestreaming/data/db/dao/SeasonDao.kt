package com.emamagic.moviestreaming.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emamagic.moviestreaming.ui.base.BaseDao
import com.emamagic.moviestreaming.data.db.entity.SeasonEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SeasonDao: BaseDao<SeasonEntity> {

    @Query("SELECT * FROM table_season WHERE itemId = :id")
    abstract fun getSeasonById(id: Long): Flow<List<SeasonEntity>>

}