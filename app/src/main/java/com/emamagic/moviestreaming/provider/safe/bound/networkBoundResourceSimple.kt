package com.emamagic.moviestreaming.provider.safe.bound

import com.emamagic.moviestreaming.provider.safe.Resource
import kotlinx.coroutines.flow.*

inline fun <ResultType, RequestType> networkBoundResourceSimple(
    crossinline databaseQuery: () -> Flow<ResultType>,
    crossinline networkCall: suspend () -> RequestType,
    crossinline saveCallResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true }
) = flow {
    val data = databaseQuery().first()

    val flow = if (shouldFetch(data)) {
        emit(Resource.Loading(data))

        try {
            saveCallResult(networkCall())
            databaseQuery().map { Resource.Success(it) }
        } catch (throwable: Throwable) {
            databaseQuery().map { Resource.Failed(throwable, it) }
        }
    } else {
        databaseQuery().map { Resource.Success(it) }
    }
    emitAll(flow)
}