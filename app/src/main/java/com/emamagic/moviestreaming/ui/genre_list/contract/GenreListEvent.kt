package com.emamagic.moviestreaming.ui.genre_list.contract

import com.emamagic.moviestreaming.base.BaseEvent
import com.emamagic.moviestreaming.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.movie_list.contract.MovieListEvent

sealed class GenreListEvent: BaseEvent {
    data class GetGenreListByCategory(val category: String): GenreListEvent()
    data class GenreListClicked(val movie: MovieEntity): GenreListEvent()
    data class FavoriteClicked(val item: FavoriteEntity): GenreListEvent()
}