package com.emamagic.moviestreaming.ui.search.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.dao.MovieDao
import com.emamagic.moviestreaming.network.response.MovieListResponse
import com.emamagic.moviestreaming.network.response.MovieResponse

class SearchState(
    @CurrentSearchState val currentState: Int,
    val movies: MovieListResponse
): BaseState {
    companion object{
        fun initialize() =
            SearchState(
                CurrentSearchState.NON_STATE,
                MovieListResponse(emptyList())
            )
    }
}
