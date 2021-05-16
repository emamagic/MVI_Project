package com.emamagic.moviestreaming.ui.modules.search.contract

import com.emamagic.moviestreaming.ui.base.BaseEvent
import com.emamagic.moviestreaming.data.network.dto.MovieDto

sealed class SearchEvent: BaseEvent {
    data class SearchMovies(val category: String ,val query: String): SearchEvent()
    data class MovieClicked(val movie: MovieDto): SearchEvent()
}
