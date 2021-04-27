package com.emamagic.moviestreaming.db.entity

import androidx.room.Entity

@Entity(tableName = "table_slider" ,primaryKeys = ["id"])
data class SliderEntity(
    val id: Long,
    val name: String,
    val imgAddress: String? = null,
    val time: String,
    val published: String
)