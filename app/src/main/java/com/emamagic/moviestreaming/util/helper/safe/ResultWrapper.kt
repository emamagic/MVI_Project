package com.emamagic.moviestreaming.util.helper.safe

import com.emamagic.moviestreaming.util.helper.safe.error.ErrorEntity


sealed class ResultWrapper<T>(
    val data: T? = null,
    val error: ErrorEntity? = null,
    val code: Int? = null,
) {
    class Success<T>(data: T, code: Int? = null) : ResultWrapper<T>(data = data, code = code)
    class Failed<T>(error: ErrorEntity, data: T? = null) : ResultWrapper<T>(data = data ,error = error)
    class CashLoading<T>(data: T? = null) : ResultWrapper<T>(data = data)
}