package com.emamagic.moviestreaming.ui.movie_list.contract

import com.emamagic.moviestreaming.base.BaseEvent
import com.emamagic.moviestreaming.db.entity.MovieEntity

sealed class MovieListEvent: BaseEvent {
    data class GetAllMovieList(val category: String): MovieListEvent()
    data class MovieClicked(val movie: MovieEntity): MovieListEvent()
    data class FavoriteClicked(val id: Long): MovieListEvent()
}
