package com.emamagic.moviestreaming.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "table_season" ,primaryKeys = ["id"])
data class SeasonEntity(
    val id: Long? = null,
    val itemId: Long?,
    @ColumnInfo(name = "number_season")
    val season: Int?,
    @ColumnInfo(name  = "count_episodes")
    val episode: Int?,
    @ColumnInfo(name = "link_img")
    val imageLink: String?
)
