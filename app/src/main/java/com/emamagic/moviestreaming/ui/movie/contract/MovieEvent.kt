package com.emamagic.moviestreaming.ui.movie.contract

import com.emamagic.moviestreaming.base.BaseEvent

sealed class MovieEvent: BaseEvent {
    data class GetAllMovie(val category: String): MovieEvent()
}
