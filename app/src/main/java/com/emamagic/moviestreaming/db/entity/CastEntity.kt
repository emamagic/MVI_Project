package com.emamagic.moviestreaming.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "table_cast", primaryKeys = ["id"])
data class CastEntity(
    val id: Long,
    val name: String,
    @ColumnInfo(name = "link_img")
    val imageLink: String,
    @ColumnInfo(name = "id_item")
    val itemId: Long
)