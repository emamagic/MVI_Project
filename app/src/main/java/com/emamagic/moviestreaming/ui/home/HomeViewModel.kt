package com.emamagic.moviestreaming.ui.home

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.repository.home.*
import com.emamagic.moviestreaming.ui.home.contract.CurrentHomeState
import com.emamagic.moviestreaming.ui.home.contract.HomeEffect
import com.emamagic.moviestreaming.ui.home.contract.HomeEvent
import com.emamagic.moviestreaming.ui.home.contract.HomeState
import com.emamagic.moviestreaming.util.helper.safe.Resource
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import com.emamagic.moviestreaming.util.helper.safe.error.ErrorEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import timber.log.Timber
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
        }
    }

    private fun getSliders() = viewModelScope.launch {
        repository.getSliders().collect {
            when (it) {
                is ResultWrapper.Success -> setState { copy(sliders = it.data!! ,currentState = CurrentHomeState.SLIDER_RECEIVED) }
                is ResultWrapper.Failed -> {
                    /** You can error handling with ErrorEntity ->
                    when(it.error){
                        is ErrorEntity.Network -> Timber.e("network")
                        is ErrorEntity.Api -> Timber.e("api ${it.error.code}")
                        ....
                    }*/
                    setEffect { HomeEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}") }
                    setState { copy(sliders = it.data!! ,currentState = CurrentHomeState.SLIDER_RECEIVED) }
                }
                is ResultWrapper.CashLoading -> setState { copy(sliders = it.data!! ,currentState = CurrentHomeState.SLIDER_RECEIVED) }
            }
        }
    }

    private fun getMovies(category: String) = viewModelScope.launch {
        repository.getMovies(category).collect {
            when (it) {
                is Resource.Success -> setState { copy(movies = it.data!! ,currentState = CurrentHomeState.MOVIE_RECEIVED) }
                is Resource.Failed -> setEffect { HomeEffect.ShowToast(it.error?.message ?: "There is a Problem getMovies") }
                is Resource.Loading -> setState { copy(movies = it.data!! ,currentState = CurrentHomeState.MOVIE_RECEIVED) }
            }.exhaustive
        }
    }


    private fun getGenre() = viewModelScope.launch {
        setState { copy(isLoading = false) }
        repository.getGenre().collect {
            when (it) {
                is Resource.Success -> setState { copy(genres = it.data!! ,currentState = CurrentHomeState.GENRE_RECEIVE) }
                is Resource.Failed -> setEffect { HomeEffect.ShowToast(it.error?.message ?: "There is a Problem getGenre") }
                is Resource.Loading -> setState { copy(genres = it.data!! ,currentState = CurrentHomeState.GENRE_RECEIVE) }
            }.exhaustive
        }
    }

}