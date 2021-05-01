package com.emamagic.moviestreaming.ui.movie

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.repository.movie.MovieRepository
import com.emamagic.moviestreaming.ui.movie.contract.CurrentMovieState
import com.emamagic.moviestreaming.ui.movie.contract.MovieEffect
import com.emamagic.moviestreaming.ui.movie.contract.MovieEvent
import com.emamagic.moviestreaming.ui.movie.contract.MovieState
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
) : BaseViewModel<MovieState, MovieEffect, MovieEvent>() {

    override fun createInitialState() = MovieState.initialize()

    override fun handleEvent(event: MovieEvent) {
        when (event) {
            is MovieEvent.GetDetailMovie -> getDetailMovie(event.id)
            is MovieEvent.GetSeasons -> getSeasons(event.id)
            is MovieEvent.PlayVideoClicked -> playVideoClicked(event.videoLink)
        }.exhaustive
    }


    private fun getDetailMovie(id: Long) = viewModelScope.launch {
        setEffect { MovieEffect.Loading(true) }
        val movie = repository.getMovieById(id)
        setState { copy(movie = movie, currentState = CurrentMovieState.MOVIE_RECEIVED) }
        repository.getCastsById(id).collect {
            when(it) {
                is ResultWrapper.Success -> setState { copy(casts = it.data!! ,currentState = CurrentMovieState.CASTS_RECEIVED) }
                is ResultWrapper.Failed -> {
                    setState { copy(casts = it.data!! ,currentState = CurrentMovieState.CASTS_RECEIVED) }
                    setEffect { MovieEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                }
                is ResultWrapper.FetchLoading -> setState { copy(casts = it.data!! ,currentState = CurrentMovieState.CASTS_RECEIVED) }
            }.exhaustive
            setEffect { MovieEffect.Loading(false) }
        }
    }


    private fun getSeasons(id: Long) = viewModelScope.launch{
        repository.getSeasonById(id).collect {
            when(it) {
                is ResultWrapper.Success -> setState { copy(seasons = it.data!! ,currentState = CurrentMovieState.SEASONS_RECEIVED) }
                is ResultWrapper.Failed -> {
                    setState { copy(seasons = it.data!! ,currentState = CurrentMovieState.SEASONS_RECEIVED) }
                    setEffect { MovieEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                }
                is ResultWrapper.FetchLoading -> setState { copy(seasons = it.data!! ,currentState = CurrentMovieState.SEASONS_RECEIVED) }
            }.exhaustive
        }
    }


    private fun playVideoClicked(videoLink: String) = viewModelScope.launch {
        setEffect { MovieEffect.Navigate(MovieFragmentDirections.actionMovieFragmentToFragmentVideoPlayer(videoLink)) }
    }

}