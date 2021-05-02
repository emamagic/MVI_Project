package com.emamagic.moviestreaming.repository.episode_list

import com.emamagic.moviestreaming.db.entity.EpisodeEntity
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface EpisodeListRepository {

    fun getEpisodesById(seasonId: Long): Flow<ResultWrapper<List<EpisodeEntity>>>

}