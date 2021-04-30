package com.emamagic.moviestreaming.ui.movie_list.contract

import com.emamagic.moviestreaming.base.BaseEvent

sealed class MovieListEvent: BaseEvent {
    data class GetAllMovieList(val category: String): MovieListEvent()
}
