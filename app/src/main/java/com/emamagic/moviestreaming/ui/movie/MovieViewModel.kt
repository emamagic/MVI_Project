package com.emamagic.moviestreaming.ui.movie

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.repository.movie.MovieRepository
import com.emamagic.moviestreaming.ui.movie.contract.CurrentMovieState
import com.emamagic.moviestreaming.ui.movie.contract.MovieEffect
import com.emamagic.moviestreaming.ui.movie.contract.MovieEvent
import com.emamagic.moviestreaming.ui.movie.contract.MovieState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val repository: MovieRepository
): BaseViewModel<MovieState ,MovieEffect ,MovieEvent>() {

    override fun createInitialState() = MovieState.initialize()

    override fun handleEvent(event: MovieEvent) {
        when(event){
            is MovieEvent.GetMovie -> getMovie(event.id)
        }.exhaustive
    }


    private fun getMovie(id: Long) = viewModelScope.launch {
        val movie = repository.getMovieById(id)
        setState { copy(movie = movie ,currentState = CurrentMovieState.MOVIE_RECEIVED) }
    }
}