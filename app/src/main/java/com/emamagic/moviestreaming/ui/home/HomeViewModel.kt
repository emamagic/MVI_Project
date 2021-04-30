package com.emamagic.moviestreaming.ui.home

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.repository.home.*
import com.emamagic.moviestreaming.ui.home.contract.CurrentHomeState
import com.emamagic.moviestreaming.ui.home.contract.HomeEffect
import com.emamagic.moviestreaming.ui.home.contract.HomeEvent
import com.emamagic.moviestreaming.ui.home.contract.HomeState
import com.emamagic.moviestreaming.ui.home.contract.CategoryType
import com.emamagic.moviestreaming.util.Const
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel<HomeState, HomeEffect, HomeEvent>() {

    private var shouldCloseApp: Boolean = false
    override fun createInitialState() = HomeState.initialize()

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetSliders -> getSliders()
            is HomeEvent.GetMovies -> getMovies(event.category)
            HomeEvent.GetGenre -> getGenre()
            HomeEvent.ShouldCloseApp -> shouldCloseApp()
            is HomeEvent.MoreGenreClicked -> moreItemClicked(event.categoryType)
            HomeEvent.SwipeRefreshed -> swipeRefreshed()
        }.exhaustive
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
                    setState { copy(sliders = it.data!! ,currentState = CurrentHomeState.SLIDER_RECEIVED) }
                    setEffect { HomeEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                }
                is ResultWrapper.FetchLoading -> setState { copy(sliders = it.data!! ,currentState = CurrentHomeState.SLIDER_RECEIVED) }
            }.exhaustive
        }
    }

    private fun getMovies(category: String) = viewModelScope.launch {
        repository.getMovies(category).onStart { setEffect { HomeEffect.Loading(true) } }
            .onCompletion { Timber.e("completed 1") }.collect {
            when (it) {
                is ResultWrapper.Success -> setState { copy(movies = it.data!! ,currentState = CurrentHomeState.MOVIE_RECEIVED) }
                is ResultWrapper.Failed -> {
                    setState { copy(movies = it.data!! ,currentState = CurrentHomeState.MOVIE_RECEIVED) }
                    setEffect { HomeEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                }
                is ResultWrapper.FetchLoading ->  setState { copy(movies = it.data!! ,currentState = CurrentHomeState.MOVIE_RECEIVED) }
            }.exhaustive
        }
    }


    private fun getGenre() = viewModelScope.launch {
        repository.getGenre().onCompletion {
            /** It is not called , that is not true!!!! */
            Timber.e("completed 2")
        }.collect {
            when (it) {
                is ResultWrapper.Success -> setState { copy(genres = it.data!! ,currentState = CurrentHomeState.GENRE_RECEIVE) }
                is ResultWrapper.Failed -> {
                    setState { copy(genres = it.data!! ,currentState = CurrentHomeState.GENRE_RECEIVE) }
                    setEffect { HomeEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                }
               is ResultWrapper.FetchLoading -> setState { copy(genres = it.data!! ,currentState = CurrentHomeState.GENRE_RECEIVE) }
            }.exhaustive
            setEffect { HomeEffect.Loading(false) }
            // if refresh mode is enabled , we should disabled
            repository.disableRefreshModel()
            setEffect { HomeEffect.DisableRefreshing }
        }
    }


    private fun shouldCloseApp() = viewModelScope.launch {
        Handler(Looper.getMainLooper()).postDelayed({
            shouldCloseApp = false
        } ,3000)
        if (shouldCloseApp) setState { copy(closeApp = true ,currentState = CurrentHomeState.CLOSE_APP) }
        else {
            shouldCloseApp = true
            setEffect { HomeEffect.ShowToast("Please Press Back Button Again" ,ToastyMode.MODE_TOAST_WARNING) }
        }
    }

    private fun moreItemClicked(@CategoryType categoryType: String) = viewModelScope.launch {
        when(categoryType) {
            CategoryType.TOP ,
            CategoryType.NEW ,
            CategoryType.SERIES ,
            CategoryType.POPULAR ,
            CategoryType.ANIMATION -> setEffect { HomeEffect.Navigate(R.id.action_homeFragment_to_movieFragment ,categoryType) }
            CategoryType.GENRE -> setEffect { HomeEffect.Navigate(R.id.action_homeFragment_to_genreFragment ,categoryType) }
        }

    }


    private fun swipeRefreshed() = viewModelScope.launch {
        repository.enableRefreshMode()
        getSliders()
        getMovies(Const.TOP_MOVIE_IMDB)
        getMovies(Const.NEW_MOVIE)
        getMovies(Const.SERIES_MOVIE)
        getMovies(Const.POPULAR_MOVIE)
        getMovies(Const.ANIMATION)
        getGenre()
    }


}