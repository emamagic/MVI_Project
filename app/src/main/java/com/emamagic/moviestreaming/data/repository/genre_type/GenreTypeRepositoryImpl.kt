package com.emamagic.moviestreaming.data.repository.genre_type

import com.emamagic.moviestreaming.data.db.dao.GenreDao
import com.emamagic.moviestreaming.data.db.entity.GenreEntity
import com.emamagic.moviestreaming.data.network.api.GenreApi
import com.emamagic.moviestreaming.ui.base.upsert
import com.emamagic.moviestreaming.provider.mapper.GenreMapper
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import com.emamagic.moviestreaming.provider.safe.SafeApi
import com.emamagic.moviestreaming.provider.safe.bound.networkBoundResource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenreTypeRepositoryImpl @Inject constructor(
    private val genreApi: GenreApi,
    private val genreDao: GenreDao,
    private val genreMapper: GenreMapper
): SafeApi() ,GenreTypeRepository {

    @ExperimentalCoroutinesApi
    override fun getAllGenre(): Flow<ResultWrapper<List<GenreEntity>>> {
        return networkBoundResource(
            errorHandler = this,
            databaseQuery = { genreDao.getGenre() },
            networkCall = { genreApi.getAllGenre() },
            saveCallResult = { genreDao.upsert(genreMapper.mapFromEntityList(it.genres)) }
        )
    }
}