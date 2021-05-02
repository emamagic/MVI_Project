package com.emamagic.moviestreaming.ui.episode_list

import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.base.BaseViewModel
import com.emamagic.moviestreaming.repository.episode_list.EpisodeListRepository
import com.emamagic.moviestreaming.ui.episode_list.contract.CurrentEpisodeListState
import com.emamagic.moviestreaming.ui.episode_list.contract.EpisodeListEffect
import com.emamagic.moviestreaming.ui.episode_list.contract.EpisodeListEvent
import com.emamagic.moviestreaming.ui.episode_list.contract.EpisodeListState
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodeListViewModel @Inject constructor(
    private val repository: EpisodeListRepository
): BaseViewModel<EpisodeListState ,EpisodeListEffect ,EpisodeListEvent>() {


    override fun createInitialState() = EpisodeListState.initialize()

    override fun handleEvent(event: EpisodeListEvent) {
        when(event) {
            is EpisodeListEvent.GetEpisodes -> getEpisodes(event.seasonId)
            is EpisodeListEvent.PlayEpisodeClicked -> playEpisodeClicked(event.videoLink)
        }.exhaustive
    }

    private fun getEpisodes(seasonId: Long) = viewModelScope.launch {
        setEffect { EpisodeListEffect.Loading(isLoading = true) }
        repository.getEpisodesById(seasonId).collect {
            when(it) {
                is ResultWrapper.Success -> setState { copy(episodes = it.data!! ,currentState = CurrentEpisodeListState.EPISODES_RECEIVED) }
                is ResultWrapper.Failed -> {
                    setEffect { EpisodeListEffect.ShowToast("${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                    setState { copy(episodes = it.data!! ,currentState = CurrentEpisodeListState.EPISODES_RECEIVED) }
                }
                is ResultWrapper.FetchLoading -> setState { copy(episodes = it.data!! ,currentState = CurrentEpisodeListState.EPISODES_RECEIVED) }
            }
            setEffect { EpisodeListEffect.Loading(isLoading = false) }
        }
    }

    private fun playEpisodeClicked(videoLink: String) = viewModelScope.launch {
        setEffect { EpisodeListEffect.Navigate(EpisodeListFragmentDirections.actionEpisodeListFragmentToFragmentVideoPlayer(videoLink)) }
    }
}