package com.emamagic.moviestreaming.ui.modules.home

import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.provider.safe.ResultWrapper

data class HomeApiHolder(
    val top: ResultWrapper<List<MovieEntity>>? = null,
    val new: ResultWrapper<List<MovieEntity>>? = null,
    val series: ResultWrapper<List<MovieEntity>>? = null,
    val popular: ResultWrapper<List<MovieEntity>>? = null,
    val animation: ResultWrapper<List<MovieEntity>>? = null,
)

