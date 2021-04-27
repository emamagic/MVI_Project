package com.emamagic.moviestreaming.safe


sealed class ResultWrapper<T> {

    data class Success<T>(val data: T ,val code: Int? = null) : ResultWrapper<T>()

    data class Error<T>(val error: ErrorEntity) : ResultWrapper<T>()
}