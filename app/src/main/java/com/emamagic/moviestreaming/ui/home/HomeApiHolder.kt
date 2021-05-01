package com.emamagic.moviestreaming.ui.home

import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.util.helper.safe.ResultWrapper

data class HomeApiHolder(
    val top: ResultWrapper<List<MovieEntity>>? = null,
    val new: ResultWrapper<List<MovieEntity>>? = null,
    val series: ResultWrapper<List<MovieEntity>>? = null,
    val popular: ResultWrapper<List<MovieEntity>>? = null,
    val animation: ResultWrapper<List<MovieEntity>>? = null,
)