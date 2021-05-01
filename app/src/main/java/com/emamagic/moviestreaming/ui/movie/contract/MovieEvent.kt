package com.emamagic.moviestreaming.ui.movie.contract

import com.emamagic.moviestreaming.base.BaseEvent

sealed class MovieEvent: BaseEvent {
    data class GetMovie(val id: Long): MovieEvent()
}