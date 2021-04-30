package com.emamagic.moviestreaming.repository.genre_list

import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface GenreListRepository {

    fun getAllGenre(): Flow<ResultWrapper<List<GenreEntity>>>

}