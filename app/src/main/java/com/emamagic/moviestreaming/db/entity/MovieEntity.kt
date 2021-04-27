package com.emamagic.moviestreaming.db.entity

import androidx.room.Entity

@Entity(tableName = "table_movie" ,primaryKeys = ["id"])
data class MovieEntity(
    val id: Long
)
