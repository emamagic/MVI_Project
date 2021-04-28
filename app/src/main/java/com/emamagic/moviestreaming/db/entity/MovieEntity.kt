package com.emamagic.moviestreaming.db.entity

import androidx.room.Entity

@Entity(tableName = "table_movie" ,primaryKeys = ["id"])
data class MovieEntity(
    val id: Long,
    val name: String,
    val link_img: String,
    val time: String,
    val category_name: String,
    val rank: String,
    val rate_imdb: String,
    val published: String,
    val director: String,
    val imgAddress: String? = null
)
