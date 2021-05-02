package com.emamagic.moviestreaming.ui.favorite.contract

import com.emamagic.moviestreaming.base.BaseEvent

sealed class FavoriteEvent: BaseEvent {
    object GetFavoriteMovies: FavoriteEvent()
}