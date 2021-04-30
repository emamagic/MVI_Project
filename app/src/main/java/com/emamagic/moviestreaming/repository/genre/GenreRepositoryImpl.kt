package com.emamagic.moviestreaming.repository.genre

import com.emamagic.moviestreaming.base.upsert
import com.emamagic.moviestreaming.db.dao.GenreDao
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.mapper.GenreMapper
import com.emamagic.moviestreaming.network.api.GenreApi
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import com.emamagic.moviestreaming.util.helper.safe.error.GeneralErrorHandlerImpl
import com.emamagic.moviestreaming.util.helper.safe.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GenreRepositoryImpl @Inject constructor(
    private val genreApi: GenreApi,
    private val genreDao: GenreDao,
    private val genreMapper: GenreMapper
): GeneralErrorHandlerImpl() ,GenreRepository {

    override fun getAllGenre(): Flow<ResultWrapper<List<GenreEntity>>> {
        return networkBoundResource(
            errorHandler = this,
            databaseQuery = { genreDao.getGenre() },
            networkCall = { genreApi.getAllGenre() },
            saveCallResult = { genreDao.upsert(genreMapper.mapFromEntityList(it.genres)) }
        )
    }
}