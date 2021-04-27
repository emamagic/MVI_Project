package com.emamagic.moviestreaming.util

import com.emamagic.moviestreaming.safe.ResultWrapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import timber.log.Timber

fun <T, A> performOperation(
    databaseQuery: () -> Flow<T>,
    networkCall: suspend () -> ResultWrapper<A>,
    saveCallResult: suspend (A) -> Unit
): Flow<T> = flow {

    val remoteSource = networkCall.invoke()
    var localSource = databaseQuery.invoke()

    if (remoteSource is ResultWrapper.Success) {
        saveCallResult(remoteSource.data)
        localSource = databaseQuery.invoke()
        emitAll(localSource)
    } else emitAll(localSource)


}
