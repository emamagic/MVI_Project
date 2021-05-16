package com.emamagic.moviestreaming.ui.modules.episode_list

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.data.repository.episode_list.EpisodeListRepository
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import com.emamagic.moviestreaming.ui.base.BaseViewModel
import com.emamagic.moviestreaming.ui.modules.episode_list.contract.EpisodeListEvent
import com.emamagic.moviestreaming.ui.modules.episode_list.contract.EpisodeListState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    private val repository: EpisodeListRepository
): BaseViewModel<EpisodeListState, CommonEffect, EpisodeListEvent>() {


    override fun createInitialState() = EpisodeListState.initialize()

    override fun handleEvent(event: EpisodeListEvent) {
        when(event) {
            is EpisodeListEvent.GetEpisodes -> getEpisodes(event.seasonId)
            is EpisodeListEvent.PlayEpisodeClicked -> playEpisodeClicked(event.videoLink)
        }.exhaustive
    }

    private fun getEpisodes(seasonId: Long) = viewModelScope.launch {
        setEffect { CommonEffect.Loading(isLoading = true) }
        repository.getEpisodesById(seasonId).collect {
            when(it) {
                is ResultWrapper.Success -> setState { copy(episodes = it.data) }
                is ResultWrapper.Failed -> {
                    setEffect { CommonEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                    setState { copy(episodes = it.data) }
                }
                is ResultWrapper.FetchLoading -> setState { copy(episodes = it.data) }
            }
            setEffect { CommonEffect.Loading(isLoading = false) }
        }
    }

    private fun playEpisodeClicked(videoLink: String) = viewModelScope.launch {
        setEffect { CommonEffect.Navigate(EpisodeListFragmentDirections.actionEpisodeListFragmentToFragmentVideoPlayer(videoLink)) }
    }
}