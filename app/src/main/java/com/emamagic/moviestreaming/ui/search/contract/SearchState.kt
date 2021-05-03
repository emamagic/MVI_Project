package com.emamagic.moviestreaming.ui.search.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.dao.MovieDao
import com.emamagic.moviestreaming.network.response.MovieListResponse
import com.emamagic.moviestreaming.network.response.MovieResponse

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
