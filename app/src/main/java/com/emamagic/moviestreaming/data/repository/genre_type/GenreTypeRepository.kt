package com.emamagic.moviestreaming.data.repository.genre_type

import com.emamagic.moviestreaming.data.db.entity.GenreEntity
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GenreTypeRepository {

    fun getAllGenre(): Flow<ResultWrapper<List<GenreEntity>>>

}