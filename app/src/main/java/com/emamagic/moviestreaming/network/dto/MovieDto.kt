package com.emamagic.moviestreaming.network.dto

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String,
    @SerializedName("link_img")
    val imageLink: String,
    @SerializedName("time")
    val time: String,
    @SerializedName("category_name")
    val categoryName: String,
    @SerializedName("rank")
    val rank: String,
    @SerializedName("rate_imdb")
    val rate_imdb: String,
    @SerializedName("published")
    val published: String,
    @SerializedName("director")
    val director: String,
    @SerializedName("episode")
    val episode: String
)