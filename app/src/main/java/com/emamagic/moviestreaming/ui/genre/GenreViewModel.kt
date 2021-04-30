package com.emamagic.moviestreaming.ui.genre

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.repository.genre.GenreRepository
import com.emamagic.moviestreaming.ui.genre.contract.CurrentGenreState
import com.emamagic.moviestreaming.ui.genre.contract.GenreEffect
import com.emamagic.moviestreaming.ui.genre.contract.GenreEvent
import com.emamagic.moviestreaming.ui.genre.contract.GenreState
import com.emamagic.moviestreaming.ui.home.contract.CurrentHomeState
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
class GenreViewModel @Inject constructor(
   private val repository: GenreRepository
): BaseViewModel<GenreState ,GenreEffect ,GenreEvent>() {

    override fun createInitialState() = GenreState.initialize()

    override fun handleEvent(event: GenreEvent) {
        when(event){
            GenreEvent.GetAllGenre -> getAllGenre()
        }.exhaustive
    }


    private fun getAllGenre() = viewModelScope.launch {
        repository.getAllGenre().onStart {
            setEffect { GenreEffect.Loading(true) }
        }.onCompletion {

        }.collect {
            when(it){
                is ResultWrapper.Success -> setState { copy(genreList = it.data!! ,currentState = CurrentGenreState.RECEIVE_GENRES) }
                is ResultWrapper.Failed -> {
                    setState { copy(genreList = it.data!! ,currentState = CurrentGenreState.RECEIVE_GENRES) }
                    setEffect { GenreEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                }
                is ResultWrapper.FetchLoading -> { }
            }.exhaustive
            setEffect { GenreEffect.Loading(false) }
        }
    }

}