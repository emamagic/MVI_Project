package com.emamagic.moviestreaming.ui.genre_list.contract

import com.emamagic.moviestreaming.base.BaseEvent

sealed class GenreListEvent: BaseEvent {
    object GetAllGenreList: GenreListEvent()
}