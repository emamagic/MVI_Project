package com.emamagic.moviestreaming.db.entity

import androidx.room.Entity

@Entity(tableName = "table_slider" ,primaryKeys = ["id"])
data class SliderEntity(
    val id: Long,
    val name: String,
    val time: String,
    val published: String,
    val link_img: String,
    val imgAddress: String? = null
)