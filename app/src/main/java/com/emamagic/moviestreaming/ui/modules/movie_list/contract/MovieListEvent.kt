package com.emamagic.moviestreaming.ui.modules.movie_list.contract

import com.emamagic.moviestreaming.ui.base.BaseEvent
import com.emamagic.moviestreaming.data.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.data.db.entity.MovieEntity

sealed class MovieListEvent: BaseEvent {
    data class GetAllMovieList(val category: String): MovieListEvent()
    data class MovieClicked(val movie: MovieEntity): MovieListEvent()
    data class FavoriteClicked(val item: FavoriteEntity): MovieListEvent()
}
