package com.emamagic.moviestreaming.data.db.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "table_favorite")
data class FavoriteEntity(
    @PrimaryKey
    val movieId: Long
)