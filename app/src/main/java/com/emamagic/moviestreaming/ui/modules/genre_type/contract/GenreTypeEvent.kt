package com.emamagic.moviestreaming.ui.modules.genre_type.contract

import com.emamagic.moviestreaming.ui.base.BaseEvent

sealed class GenreTypeEvent: BaseEvent {
    data class GenreTypeClicked(val genreName: String): GenreTypeEvent()
    object GetAllGenreType: GenreTypeEvent()
}