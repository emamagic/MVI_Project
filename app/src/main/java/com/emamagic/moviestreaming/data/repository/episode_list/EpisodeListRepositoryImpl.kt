package com.emamagic.moviestreaming.data.repository.episode_list

import com.emamagic.moviestreaming.data.db.dao.EpisodeDao
import com.emamagic.moviestreaming.data.db.entity.EpisodeEntity
import com.emamagic.moviestreaming.data.network.api.MovieApi
import com.emamagic.moviestreaming.ui.base.upsert
import com.emamagic.moviestreaming.provider.mapper.EpisodeMapper
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import com.emamagic.moviestreaming.provider.safe.SafeApi
import com.emamagic.moviestreaming.provider.safe.bound.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class EpisodeListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val episodeDao: EpisodeDao,
    private val episodeMapper: EpisodeMapper
) : SafeApi(), EpisodeListRepository {

    override fun getEpisodesById(seasonId: Long): Flow<ResultWrapper<List<EpisodeEntity>>> {
        return networkBoundResource(
            errorHandler = this,
            databaseQuery = { episodeDao.getEpisodesById(seasonId) },
            networkCall = { movieApi.getEpisodes(seasonId) },
            saveCallResult = { episodeDao.upsert(episodeMapper.mapFromEntityList(movieApi.getEpisodes(seasonId).episodes)) }
        )
    }


}