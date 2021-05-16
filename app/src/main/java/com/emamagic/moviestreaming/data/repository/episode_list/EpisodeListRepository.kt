package com.emamagic.moviestreaming.data.repository.episode_list

import com.emamagic.moviestreaming.data.db.entity.EpisodeEntity
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface EpisodeListRepository {

    fun getEpisodesById(seasonId: Long): Flow<ResultWrapper<List<EpisodeEntity>>>

}