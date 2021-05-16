package com.emamagic.moviestreaming.ui.modules.favorite

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.data.repository.favorite.FavoriteRepository
import com.emamagic.moviestreaming.ui.base.BaseViewModel
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.ui.modules.favorite.contract.FavoriteEvent
import com.emamagic.moviestreaming.ui.modules.favorite.contract.FavoriteState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val repository: FavoriteRepository
): BaseViewModel<FavoriteState, CommonEffect, FavoriteEvent>(){

    override fun createInitialState() = FavoriteState.initialize()

    override fun handleEvent(event: FavoriteEvent) {
        when(event){
            FavoriteEvent.GetFavoriteMovies -> getFavoriteMovies()
        }.exhaustive
    }

    private fun getFavoriteMovies() = viewModelScope.launch {
        setEffect { CommonEffect.Loading(isLoading = true) }
        repository.getFavoriteMovies().collect {
            setState { copy(movies = it) }
            setEffect { CommonEffect.Loading(isLoading = false) }
        }
    }
}