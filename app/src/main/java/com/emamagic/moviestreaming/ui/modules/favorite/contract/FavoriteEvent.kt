package com.emamagic.moviestreaming.ui.modules.favorite.contract

import com.emamagic.moviestreaming.ui.base.BaseEvent

sealed class FavoriteEvent: BaseEvent {
    object GetFavoriteMovies: FavoriteEvent()
}