package com.emamagic.moviestreaming.ui.modules.genre_list

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.data.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.data.repository.genre_list.GenreListRepository
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import com.emamagic.moviestreaming.ui.base.BaseViewModel
import com.emamagic.moviestreaming.ui.modules.genre_list.contract.GenreListEvent
import com.emamagic.moviestreaming.ui.modules.genre_list.contract.GenreListState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GenreListViewModel @Inject constructor(
    private val repository: GenreListRepository
): BaseViewModel<GenreListState, CommonEffect, GenreListEvent>() {

    override fun createInitialState() = GenreListState.initialize()

    override fun handleEvent(event: GenreListEvent) {
        when(event){
            is GenreListEvent.GetGenreListByCategory -> getGenreByCategory(event.category)
            is GenreListEvent.GenreListClicked -> genreListClicked(event.movie)
            is GenreListEvent.FavoriteClicked -> favoriteMovieClicked(event.item)
        }.exhaustive
    }

    override fun showError(errorMessage: String) {
        CommonEffect.ShowToast(errorMessage, ToastyMode.MODE_TOAST_ERROR)
    }

    private fun getGenreByCategory(category: String) = viewModelScope.launch {
        setEffect { CommonEffect.Loading(isLoading = true) }
        repository.getGenreByCategory(category).collect {
            when(it) {
                is ResultWrapper.Success -> setState { copy(genres = it.data) }
                is ResultWrapper.Failed -> {
                    setEffect { CommonEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,
                        ToastyMode.MODE_TOAST_ERROR) }
                    setState { copy(genres = it.data) }
                }
                is ResultWrapper.FetchLoading -> setState { copy(genres = it.data) }
            }
            setEffect { CommonEffect.Loading(isLoading = false) }
        }
    }


    private fun genreListClicked(movie: MovieEntity) = viewModelScope.launch {
        setEffect { CommonEffect.Navigate(GenreListFragmentDirections.actionGenreListFragmentToMovieFragment(movie)) }
    }

    private fun favoriteMovieClicked(item: FavoriteEntity) = viewModelScope.launch {
        repository.updateFavoriteById(item)
    }


}