package com.emamagic.moviestreaming.ui.search.contract

import com.emamagic.moviestreaming.base.BaseEvent
import com.emamagic.moviestreaming.network.dto.MovieDto

sealed class SearchEvent: BaseEvent {
    data class SearchMovies(val category: String ,val query: String): SearchEvent()
    data class MovieClicked(val movie: MovieDto): SearchEvent()
}
