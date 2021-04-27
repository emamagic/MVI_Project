package com.emamagic.moviestreaming.ui.home

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.repository.home.*
import com.emamagic.moviestreaming.safe.ResultWrapper
import com.emamagic.moviestreaming.ui.home.contract.HomeEffect
import com.emamagic.moviestreaming.ui.home.contract.HomeEvent
import com.emamagic.moviestreaming.ui.home.contract.HomeState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
): BaseViewModel<HomeState, HomeEffect, HomeEvent>() {
    override fun createInitialState(): HomeState {
        return HomeState(SliderStatus.EmptyList)
    }

    override fun handleEvent(event: HomeEvent) {
        when(event) {
            HomeEvent.GetSlider -> getSliders()
        }.exhaustive
    }

    private fun getSliders() = viewModelScope.launch {
        setState { copy(sliderStatus = SliderStatus.Loading(true)) }
        repository.getSliders().collect {
            when (it) {
                is ResultWrapper.Success -> setState { copy(sliderStatus = SliderStatus.FetchList(it.data)) }
                is ResultWrapper.Failed -> setEffect {
                    HomeEffect.ShowToast(
                        it.error.message ?: "There is a Problem"
                    )
                }
            }.exhaustive
        }
    }



}