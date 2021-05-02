package com.emamagic.moviestreaming.ui.genre_list

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.repository.genre_list.GenreListRepository
import com.emamagic.moviestreaming.ui.genre_list.contract.CurrentGenreState
import com.emamagic.moviestreaming.ui.genre_list.contract.GenreListEffect
import com.emamagic.moviestreaming.ui.genre_list.contract.GenreListEvent
import com.emamagic.moviestreaming.ui.genre_list.contract.GenreListState
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
class GenreListViewModel @Inject constructor(
   private val listRepository: GenreListRepository
): BaseViewModel<GenreListState ,GenreListEffect ,GenreListEvent>() {

    override fun createInitialState() = GenreListState.initialize()

    override fun handleEvent(event: GenreListEvent) {
        when(event){
            GenreListEvent.GetAllGenreList -> getAllGenre()
        }.exhaustive
    }


    private fun getAllGenre() = viewModelScope.launch {
        setEffect { GenreListEffect.Loading(true) }
        listRepository.getAllGenre().collect {
            when(it){
                is ResultWrapper.Success -> setState { copy(genreList = it.data!! ,currentState = CurrentGenreState.RECEIVE_GENRES) }
                is ResultWrapper.Failed -> {
                    setState { copy(genreList = it.data!! ,currentState = CurrentGenreState.RECEIVE_GENRES) }
                    setEffect { GenreListEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                }
                is ResultWrapper.FetchLoading -> setState { copy(genreList = it.data!! ,currentState = CurrentGenreState.RECEIVE_GENRES) }
            }.exhaustive
            setEffect { GenreListEffect.Loading(false) }
        }
    }

}