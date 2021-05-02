package com.emamagic.moviestreaming.ui.genre_type.contract

import com.emamagic.moviestreaming.base.BaseEvent

sealed class GenreTypeEvent: BaseEvent {
    data class GenreTypeClicked(val genreName: String): GenreTypeEvent()
    object GetAllGenreType: GenreTypeEvent()
}