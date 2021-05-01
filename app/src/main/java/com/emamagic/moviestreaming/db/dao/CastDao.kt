package com.emamagic.moviestreaming.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emamagic.moviestreaming.base.BaseDao
import com.emamagic.moviestreaming.db.entity.CastEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class CastDao: BaseDao<CastEntity> {

    @Query("SELECT * FROM table_cast WHERE id_item = :itemId")
    abstract fun getCastsById(itemId: Long): Flow<List<CastEntity>>


}