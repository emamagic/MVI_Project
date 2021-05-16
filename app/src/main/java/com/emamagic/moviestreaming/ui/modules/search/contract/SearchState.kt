package com.emamagic.moviestreaming.ui.modules.search.contract

import com.emamagic.moviestreaming.ui.base.BaseState
import com.emamagic.moviestreaming.data.network.response.MovieListResponse

data class SearchState(
    @CurrentSearchState val currentState: Int,
    val movies: MovieListResponse
): BaseState {
    companion object{
        fun initialize() =
            SearchState(
                currentState = CurrentSearchState.NON_STATE,
                movies = MovieListResponse(emptyList())
            )
    }
}
