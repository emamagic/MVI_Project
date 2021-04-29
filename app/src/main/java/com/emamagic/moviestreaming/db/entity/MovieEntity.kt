package com.emamagic.moviestreaming.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "table_movie" ,primaryKeys = ["id"])
data class MovieEntity(
    val id: Long,
    val name: String,
    @ColumnInfo(name = "link_img")
    val imageLink: String,
    val time: String,
    @ColumnInfo(name = "category_name")
    val categoryName: String,
    val rank: String,
    @ColumnInfo(name = "rate_imdb")
    val imdbRate: String,
    val published: String,
    val director: String,
    @ColumnInfo(name = "address_img")
    val imageAddress: String? = null,
    val updatedAt: Long = System.currentTimeMillis()
)
