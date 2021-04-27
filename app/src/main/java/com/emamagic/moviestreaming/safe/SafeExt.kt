package com.emamagic.moviestreaming.safe

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import timber.log.Timber

fun <T> Response<T>.toResult(errorHandler: ErrorHandler): ResultWrapper<T> {
    try {
        if (isSuccessful) {
            body()?.let {
                return ResultWrapper.Success(
                    data = it,
                    code = code()
                )
            }
        }
        return ResultWrapper.Failed(
            ErrorEntity.Api(
                message = message(), code = code(), errorBody = errorBody()?.string()
                    ?: "error body is null"
            )
        )
    } catch (t: Throwable) {
        return ResultWrapper.Failed(errorHandler.getError(t))
    }
}

fun <T> LiveData<T>.toResult(errorHandler: ErrorHandler): LiveData<ResultWrapper<T>> = map {

    try {
        ResultWrapper.Success(it)
    } catch (t: Throwable) {
        ResultWrapper.Failed(errorHandler.getError(t))
    }
}


fun <T> Flow<T>.toResult(errorHandler: ErrorHandler): Flow<ResultWrapper<T>> = map {
    try {
        ResultWrapper.Success(it)
    } catch (t: Throwable) {
        ResultWrapper.Failed(errorHandler.getError(t))
    }
}


