package com.emamagic.moviestreaming.ui.home.contract

import com.emamagic.moviestreaming.base.BaseEvent
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity

sealed class HomeEvent: BaseEvent {
    object GetSliders: HomeEvent()
    object GetMovies: HomeEvent()
    object GetGenre: HomeEvent()
    object ShouldCloseApp: HomeEvent()
    data class MoreMovieClicked(@CategoryType val categoryType: String): HomeEvent()
    data class MovieClicked(val movie: MovieEntity): HomeEvent()
    data class GenreClicked(val genre: GenreEntity): HomeEvent()
    object SwipeRefreshed: HomeEvent()
}