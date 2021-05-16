package com.emamagic.moviestreaming.ui.modules.genre_list.contract

import com.emamagic.moviestreaming.ui.base.BaseEvent
import com.emamagic.moviestreaming.data.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.data.db.entity.MovieEntity

sealed class GenreListEvent: BaseEvent {
    data class GetGenreListByCategory(val category: String): GenreListEvent()
    data class GenreListClicked(val movie: MovieEntity): GenreListEvent()
    data class FavoriteClicked(val item: FavoriteEntity): GenreListEvent()
}