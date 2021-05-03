package com.emamagic.moviestreaming.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.emamagic.moviestreaming.base.BaseDao
import com.emamagic.moviestreaming.db.entity.FavoriteEntity

@Dao
abstract class FavoriteDao: BaseDao<FavoriteEntity>