package com.emamagic.moviestreaming.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "table_episode" ,primaryKeys = ["id"])
data class EpisodeEntity(
    val id: Long? = null,
    @ColumnInfo(name = "id_season")
    val seasonId: Long? = null,
    val name: String? = null,
    @ColumnInfo(name = "link_img")
    val imageLink: String? = null,
    val detail: String? = null,
    val time: String? = null,
    @ColumnInfo(name = "link_play_episode")
    val videoLink: String? = null
)
