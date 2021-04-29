package com.emamagic.moviestreaming.ui.genre

import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.repository.genre.GenreRepository
import com.emamagic.moviestreaming.ui.genre.contract.GenreEffect
import com.emamagic.moviestreaming.ui.genre.contract.GenreEvent
import com.emamagic.moviestreaming.ui.genre.contract.GenreState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(
   private val repository: GenreRepository
): BaseViewModel<GenreState ,GenreEffect ,GenreEvent>() {

    override fun createInitialState() = GenreState.initialize()

    override fun handleEvent(event: GenreEvent) {
        when(event){

            else -> {}
        }.exhaustive
    }

}