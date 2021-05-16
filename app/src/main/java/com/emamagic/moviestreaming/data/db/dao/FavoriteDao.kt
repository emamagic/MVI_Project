package com.emamagic.moviestreaming.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.emamagic.moviestreaming.ui.base.BaseDao
import com.emamagic.moviestreaming.data.db.entity.FavoriteEntity

@Dao
abstract class FavoriteDao: BaseDao<FavoriteEntity>