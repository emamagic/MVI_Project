package com.emamagic.moviestreaming.ui.movie_list

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.repository.movie.MovieRepository
import com.emamagic.moviestreaming.ui.movie_list.contract.CurrentMovieState
import com.emamagic.moviestreaming.ui.movie_list.contract.MovieListEffect
import com.emamagic.moviestreaming.ui.movie_list.contract.MovieListEvent
import com.emamagic.moviestreaming.ui.movie_list.contract.MovieListState
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieRepository
): BaseViewModel<MovieListState ,MovieListEffect ,MovieListEvent>() {


    override fun createInitialState() = MovieListState.initialize()

    override fun handleEvent(listEvent: MovieListEvent) {
        when(listEvent) {
            is MovieListEvent.GetAllMovieList -> getAllMovie(listEvent.category)
        }.exhaustive
    }

    private fun getAllMovie(category: String) = viewModelScope.launch {
        repository.getAllMovie(category).onStart {
            setEffect { MovieListEffect.Loading(isLoading = true) }
        }.onCompletion {
            // does not work
        }.collect {
            when(it){
                is ResultWrapper.Success -> setState { copy(movieList = it.data!! ,currentMovieState = CurrentMovieState.RECEIVE_MOVIES) }
                is ResultWrapper.Failed -> setState { copy(movieList = it.data!! ,currentMovieState = CurrentMovieState.RECEIVE_MOVIES) }
                is ResultWrapper.FetchLoading -> {}
            }.exhaustive
            setEffect { MovieListEffect.Loading(isLoading = false) }
        }
    }


}