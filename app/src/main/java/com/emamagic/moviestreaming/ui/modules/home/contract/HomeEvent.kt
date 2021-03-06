package com.emamagic.moviestreaming.ui.modules.home.contract

import com.emamagic.moviestreaming.ui.base.BaseEvent
import com.emamagic.moviestreaming.data.db.entity.MovieEntity

sealed class HomeEvent: BaseEvent {
    object GetSliders: HomeEvent()
    object GetMovies: HomeEvent()
    object GetGenre: HomeEvent()
    object ShouldCloseApp: HomeEvent()
    object SearchClicked: HomeEvent()
    data class MoreMovieClicked(@CategoryType val categoryType: String): HomeEvent()
    data class MovieClicked(val movie: MovieEntity): HomeEvent()
    data class GenreClicked(val genreName: String): HomeEvent()
    object SwipeRefreshed: HomeEvent()
    object FavoriteClicked: HomeEvent()
}