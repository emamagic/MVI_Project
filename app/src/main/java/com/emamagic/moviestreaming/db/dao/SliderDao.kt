package com.emamagic.moviestreaming.db.dao

import androidx.room.Dao
import androidx.room.Query
import com.emamagic.moviestreaming.base.BaseDao
import com.emamagic.moviestreaming.db.entity.SliderEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class SliderDao: BaseDao<SliderEntity> {

    @Query("SELECT * FROM table_slider")
    abstract fun getAllSlider(): Flow<List<SliderEntity>>








}