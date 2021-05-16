package com.emamagic.moviestreaming.ui.modules.movie_list

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.data.repository.movie_list.MovieListRepository
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import com.emamagic.moviestreaming.ui.base.BaseViewModel
import com.emamagic.moviestreaming.ui.modules.movie_list.contract.MovieListEvent
import com.emamagic.moviestreaming.ui.modules.movie_list.contract.MovieListState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val repository: MovieListRepository
): BaseViewModel<MovieListState, CommonEffect, MovieListEvent>() {


    override fun createInitialState() = MovieListState.initialize()

    override fun handleEvent(event: MovieListEvent) {
        when(event) {
            is MovieListEvent.GetAllMovieList -> getAllMovie(event.category)
            is MovieListEvent.MovieClicked -> movieClicked(event.movie)
            is MovieListEvent.FavoriteClicked -> favoriteMovieClicked(event.item)
        }.exhaustive
    }

    private fun getAllMovie(category: String) = viewModelScope.launch {
        setEffect { CommonEffect.Loading(isLoading = true) }
        repository.getAllMovie(category).collect {
            when(it){
                is ResultWrapper.Success -> setState { copy(movieList = it.data!! ,currentMovieState = com.emamagic.moviestreaming.ui.modules.movie_list.contract.CurrentMovieListState.RECEIVE_MOVIES) }
                is ResultWrapper.Failed ->  {
                    setEffect { CommonEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                    setState { copy(movieList = it.data!! ,currentMovieState = com.emamagic.moviestreaming.ui.modules.movie_list.contract.CurrentMovieListState.RECEIVE_MOVIES) }
                }
                is ResultWrapper.FetchLoading ->  setState { copy(movieList = it.data!! ,currentMovieState = com.emamagic.moviestreaming.ui.modules.movie_list.contract.CurrentMovieListState.RECEIVE_MOVIES) }
            }.exhaustive
            setEffect { CommonEffect.Loading(isLoading = false) }
        }
    }

    private fun movieClicked(movie: MovieEntity) = viewModelScope.launch {
        setEffect { CommonEffect.Navigate(MovieListFragmentDirections.actionMovieListFragmentToMovieFragment(movie)) }
    }


    private fun favoriteMovieClicked(item: com.emamagic.moviestreaming.data.db.entity.FavoriteEntity) = viewModelScope.launch {
        repository.updateFavoriteById(item)
    }

}