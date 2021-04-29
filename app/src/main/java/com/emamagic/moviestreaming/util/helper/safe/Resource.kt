package com.emamagic.moviestreaming.util.helper.safe

sealed class Resource<T>(
    val data: T? = null,
    val error: Throwable? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Failed<T>(throwable: Throwable, data: T? = null) : Resource<T>(data, throwable)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}