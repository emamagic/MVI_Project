package com.emamagic.moviestreaming.ui.modules.episode_list.contract

import com.emamagic.moviestreaming.ui.base.BaseState
import com.emamagic.moviestreaming.data.db.entity.EpisodeEntity

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
