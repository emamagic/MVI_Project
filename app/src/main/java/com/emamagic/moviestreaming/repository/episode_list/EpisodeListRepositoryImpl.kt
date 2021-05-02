package com.emamagic.moviestreaming.repository.episode_list

import com.emamagic.moviestreaming.base.upsert
import com.emamagic.moviestreaming.db.dao.EpisodeDao
import com.emamagic.moviestreaming.db.entity.EpisodeEntity
import com.emamagic.moviestreaming.mapper.EpisodeMapper
import com.emamagic.moviestreaming.network.api.MovieApi
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import com.emamagic.moviestreaming.util.helper.safe.SafeApi
import com.emamagic.moviestreaming.util.helper.safe.networkBoundResource
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