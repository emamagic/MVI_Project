package com.emamagic.moviestreaming.ui.movie_list

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.repository.movie_list.MovieListRepository
import com.emamagic.moviestreaming.ui.movie_list.contract.CurrentMovieListState
import com.emamagic.moviestreaming.ui.movie_list.contract.MovieListEffect
import com.emamagic.moviestreaming.ui.movie_list.contract.MovieListEvent
import com.emamagic.moviestreaming.ui.movie_list.contract.MovieListState
import com.emamagic.moviestreaming.util.ToastyMode
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
    private val listRepository: MovieListRepository
): BaseViewModel<MovieListState ,MovieListEffect ,MovieListEvent>() {


    override fun createInitialState() = MovieListState.initialize()

    override fun handleEvent(event: MovieListEvent) {
        when(event) {
            is MovieListEvent.GetAllMovieList -> getAllMovie(event.category)
            is MovieListEvent.MovieClicked -> movieClicked(event.movie)
        }.exhaustive
    }

    private fun getAllMovie(category: String) = viewModelScope.launch {
        setEffect { MovieListEffect.Loading(isLoading = true) }
        listRepository.getAllMovie(category).collect {
            when(it){
                is ResultWrapper.Success -> setState { copy(movieList = it.data!! ,currentMovieState = CurrentMovieListState.RECEIVE_MOVIES) }
                is ResultWrapper.Failed ->  {
                    setEffect { MovieListEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                    setState { copy(movieList = it.data!! ,currentMovieState = CurrentMovieListState.RECEIVE_MOVIES) }
                }
                is ResultWrapper.FetchLoading ->  setState { copy(movieList = it.data!! ,currentMovieState = CurrentMovieListState.RECEIVE_MOVIES) }
            }.exhaustive
            setEffect { MovieListEffect.Loading(isLoading = false) }
        }
    }

    private fun movieClicked(movie: MovieEntity) = viewModelScope.launch {
        setEffect { MovieListEffect.Navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieFragment(movie)) }
    }

}