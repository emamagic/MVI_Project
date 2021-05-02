package com.emamagic.moviestreaming.ui.episode_list.contract

import com.emamagic.moviestreaming.base.BaseEvent

sealed class EpisodeListEvent: BaseEvent {
    data class GetEpisodes(val seasonId: Long): EpisodeListEvent()
}
