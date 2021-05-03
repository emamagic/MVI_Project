package com.emamagic.moviestreaming.db.entity

import androidx.room.Embedded
import androidx.room.Relation

data class MovieWithFavorite(
    @Embedded
    val movie: MovieEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "movieId"
    )
    val favorites: FavoriteEntity?
)