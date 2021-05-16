package com.emamagic.moviestreaming.provider.safe.bound

import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import com.emamagic.moviestreaming.provider.safe.error.ErrorEntity
import com.emamagic.moviestreaming.provider.safe.error.ErrorHandler
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.collect


@ExperimentalCoroutinesApi
inline fun <ResultType, RequestType> networkBoundResource(
    errorHandler: ErrorHandler,
    crossinline databaseQuery: () -> Flow<ResultType>,
    crossinline networkCall: suspend () -> RequestType,
    crossinline saveCallResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline onFetchSuccess: () -> Unit = { },
    crossinline onFetchFailed: (ErrorEntity) -> Unit = { }
) = channelFlow {
    val data = databaseQuery().first()

    if (shouldFetch(data)) {
        val loading = launch {
            databaseQuery().collect { send(ResultWrapper.FetchLoading(it)) }
        }

        try {
            saveCallResult(networkCall())
            onFetchSuccess()
            loading.cancel()
            databaseQuery().collect { send(ResultWrapper.Success(it)) }
        } catch (t: Throwable) {
            onFetchFailed(errorHandler.getError(t))
            loading.cancel()
            databaseQuery().collect { send(ResultWrapper.Failed(errorHandler.getError(t), it)) }
        }
    } else {
        databaseQuery().collect { send(ResultWrapper.Success(it)) }
    }
}