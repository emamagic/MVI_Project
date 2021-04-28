package com.emamagic.moviestreaming.db.entity

import androidx.room.Entity

@Entity(tableName = "table_genre" ,primaryKeys = ["id"])
data class GenreEntity(
    val id: Long,
    val name: String,
    val link_img: String,
    val imgAddress: String? = null
)