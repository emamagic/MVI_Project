package com.emamagic.moviestreaming.ui.home.contract

import com.emamagic.moviestreaming.base.BaseEvent

sealed class HomeEvent: BaseEvent {
    object GetSliders: HomeEvent()
    data class GetMovies(val category: String): HomeEvent()
    object GetGenre: HomeEvent()
    object ShouldCloseApp: HomeEvent()
    data class MoreGenreClicked(@CategoryType val categoryType: String): HomeEvent()
    object SwipeRefreshed: HomeEvent()
}