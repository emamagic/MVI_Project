package com.emamagic.moviestreaming.ui.home.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SliderEntity

data class HomeState(
    val sliders: List<SliderEntity>,
    val movies: List<MovieEntity>,
    val genres: List<GenreEntity>,
    val isLoading: Boolean,
    @CurrentHomeState val currentState: Int
) : BaseState {
    companion object {
        fun initial() = HomeState (
            sliders = emptyList(),
            movies = emptyList(),
            genres = emptyList(),
            isLoading = false,
            CurrentHomeState.NON_STATE
        )
    }
}