package com.emamagic.moviestreaming.base

import androidx.room.*
import com.emamagic.moviestreaming.db.entity.SliderEntity
import kotlinx.coroutines.flow.Flow

interface BaseDao<ENTITY> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: ENTITY): Long

    @Insert
    suspend fun insertAll(items: List<ENTITY>)

    @Delete
    suspend fun delete(item: ENTITY)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(item: ENTITY)

}
@Transaction
suspend inline fun <reified ENTITY> BaseDao<ENTITY>.upsert(item: ENTITY) {
    if (insert(item) != -1L) return
    update(item)
}