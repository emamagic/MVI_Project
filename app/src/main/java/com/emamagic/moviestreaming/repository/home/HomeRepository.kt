package com.emamagic.moviestreaming.repository.home

import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun getSliders(): Flow<ResultWrapper<List<SliderEntity>>>
    //  suspend fun getSliders(): ResultWrapper<SliderListResponse>

}