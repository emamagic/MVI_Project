package com.emamagic.moviestreaming.ui.modules.episode_list.contract

import com.emamagic.moviestreaming.ui.base.BaseEvent

sealed class EpisodeListEvent: BaseEvent {
    data class GetEpisodes(val seasonId: Long): EpisodeListEvent()
    data class PlayEpisodeClicked(val videoLink: String): EpisodeListEvent()
}
