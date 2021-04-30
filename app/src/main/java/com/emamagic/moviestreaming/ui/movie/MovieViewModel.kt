package com.emamagic.moviestreaming.ui.movie

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.repository.movie.MovieRepository
import com.emamagic.moviestreaming.ui.movie.contract.CurrentMovieState
import com.emamagic.moviestreaming.ui.movie.contract.MovieEffect
import com.emamagic.moviestreaming.ui.movie.contract.MovieEvent
import com.emamagic.moviestreaming.ui.movie.contract.MovieState
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import okhttp3.internal.assertThreadHoldsLock
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
): BaseViewModel<MovieState ,MovieEffect ,MovieEvent>() {


    override fun createInitialState() = MovieState.initialize()

    override fun handleEvent(event: MovieEvent) {
        when(event) {
            is MovieEvent.GetAllMovie -> getAllMovie(event.category)
        }.exhaustive
    }

    private fun getAllMovie(category: String) = viewModelScope.launch {
        repository.getAllMovie(category).onStart {
            setEffect { MovieEffect.Loading(isLoading = true) }
        }.onCompletion {
            // does not work
        }.collect {
            when(it){
                is ResultWrapper.Success -> setState { copy(movieList = it.data!! ,currentMovieState = CurrentMovieState.RECEIVE_MOVIES) }
                is ResultWrapper.Failed -> setState { copy(movieList = it.data!! ,currentMovieState = CurrentMovieState.RECEIVE_MOVIES) }
                is ResultWrapper.FetchLoading -> {}
            }.exhaustive
            setEffect { MovieEffect.Loading(isLoading = false) }
        }
    }


}