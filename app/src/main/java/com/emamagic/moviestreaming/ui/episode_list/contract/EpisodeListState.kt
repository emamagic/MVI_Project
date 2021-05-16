package com.emamagic.moviestreaming.ui.episode_list.contract

import com.emamagic.moviestreaming.base.BaseState
import com.emamagic.moviestreaming.db.entity.EpisodeEntity

data class EpisodeListState(
    val episodes: List<EpisodeEntity>?
) : BaseState {

    companion object {

        fun initialize() =
            EpisodeListState(
                episodes = emptyList()
            )
    }

}
