package com.emamagic.moviestreaming.ui.modules.genre_type.contract

import com.emamagic.moviestreaming.ui.base.BaseState
import com.emamagic.moviestreaming.data.db.entity.GenreEntity

data class GenreTypeState(
    val genreList: List<GenreEntity>?,
) : BaseState {

    companion object {
        fun initialize(): GenreTypeState =
            GenreTypeState(
                emptyList()
            )
    }
}

