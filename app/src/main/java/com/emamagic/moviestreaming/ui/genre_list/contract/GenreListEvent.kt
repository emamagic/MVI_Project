package com.emamagic.moviestreaming.ui.genre_list.contract

import com.emamagic.moviestreaming.base.BaseEvent
import com.emamagic.moviestreaming.db.entity.MovieEntity

sealed class GenreListEvent: BaseEvent {
    data class GetGenreListByCategory(val category: String): GenreListEvent()
    data class GenreListClicked(val movie: MovieEntity): GenreListEvent()
}