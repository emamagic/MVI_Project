package com.emamagic.moviestreaming.repository.genre_type

import com.emamagic.moviestreaming.base.upsert
import com.emamagic.moviestreaming.db.dao.GenreDao
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.mapper.GenreMapper
import com.emamagic.moviestreaming.network.api.GenreApi
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import com.emamagic.moviestreaming.util.helper.safe.SafeApi
import com.emamagic.moviestreaming.util.helper.safe.networkBoundResource
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