package com.emamagic.moviestreaming.ui.modules.movie.contract

import com.emamagic.moviestreaming.ui.base.BaseEvent

sealed class MovieEvent: BaseEvent {
    data class GetDetailMovie(val id: Long): MovieEvent()
    data class GetSeasons(val id: Long): MovieEvent()
    data class PlayVideoClicked(val videoLink: String): MovieEvent()
    data class SeasonClicked(val seasonId: Long): MovieEvent()
}