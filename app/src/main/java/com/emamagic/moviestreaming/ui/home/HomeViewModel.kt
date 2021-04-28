package com.emamagic.moviestreaming.ui.home

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.repository.home.*
import com.emamagic.moviestreaming.safe.ResultWrapper
import com.emamagic.moviestreaming.ui.home.contract.CurrentHomeState
import com.emamagic.moviestreaming.ui.home.contract.HomeEffect
import com.emamagic.moviestreaming.ui.home.contract.HomeEvent
import com.emamagic.moviestreaming.ui.home.contract.HomeState
import com.emamagic.moviestreaming.util.Resource
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel<HomeState, HomeEffect, HomeEvent>() {

    override fun createInitialState() = HomeState.initial()

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetSliders -> getSliders()
            is HomeEvent.GetMovies -> getMovies(event.category)
            HomeEvent.GetGenre -> getGenre()
        }.exhaustive
    }

    private fun getSliders() = viewModelScope.launch {

        repository.getSliders().collect {
            when (it) {
                is Resource.Success -> setState { copy(sliders = it.data!! ,currentState = CurrentHomeState.SLIDER_RECEIVED) }
                is Resource.Error -> setEffect {
                    HomeEffect.ShowToast(
                        it.error?.message ?: "There is a Problem getSliders"
                    )
                }
                is Resource.Loading -> setState { copy(isLoading = false) }
            }.exhaustive
        }
    }

    private fun getMovies(category: String) = viewModelScope.launch {
        repository.getMovies(category).collect {
            when (it) {
                is ResultWrapper.Success -> setState { copy(movies = it.data ,currentState = CurrentHomeState.MOVIE_RECEIVED) }
                is ResultWrapper.Failed -> setEffect { HomeEffect.ShowToast(it.error.message ?: "There is a Problem getMovies") }
            }.exhaustive
        }
    }


    private fun getGenre() = viewModelScope.launch {
        setState { copy(isLoading = false) }
        repository.getGenre().collect {
            when (it) {
                is ResultWrapper.Success -> setState { copy(genres = it.data ,currentState = CurrentHomeState.GENRE_RECEIVE) }
                is ResultWrapper.Failed -> setEffect { HomeEffect.ShowToast(it.error.message ?: "There is a Problem getGenre") }
            }.exhaustive
        }
    }

}