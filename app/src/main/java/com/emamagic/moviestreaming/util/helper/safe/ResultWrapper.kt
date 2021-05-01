package com.emamagic.moviestreaming.util.helper.safe

import com.emamagic.moviestreaming.util.helper.safe.error.ErrorEntity
import okhttp3.Headers


sealed class ResultWrapper<T>(
    val data: T? = null,
    val error: ErrorEntity? = null,
    val header: Headers? = null,
    val code: Int? = null,
) {
    class Success<T>(data: T, header: Headers? = null ,code: Int? = null) : ResultWrapper<T>(data = data, header = header ,code = code)
    class Failed<T>(error: ErrorEntity, data: T? = null) : ResultWrapper<T>(data = data ,error = error)
    class FetchLoading<T>(data: T? = null) : ResultWrapper<T>(data = data)

    override fun toString(): String {
        return when(this) {
            is Success -> "Success [data=$data]"
            is Failed -> "Error[exception=${error?.message}]"
            is FetchLoading -> "Fetching Loading"
        }
    }
}
val ResultWrapper<*>.succeeded
    get() = this is ResultWrapper.Success && data != null