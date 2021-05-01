package com.emamagic.moviestreaming.util.helper.safe

import android.database.sqlite.SQLiteException
import com.emamagic.moviestreaming.util.helper.safe.error.ErrorEntity
import com.emamagic.moviestreaming.util.helper.safe.error.ErrorHandler
import com.emamagic.moviestreaming.util.helper.safe.error.NoInternetException
import com.emamagic.moviestreaming.util.helper.safe.error.ServerException
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import java.net.SocketException
import java.net.UnknownHostException

abstract class SafeApi: ErrorHandler {

    suspend fun <T> safe(call: suspend () -> Response<T>): ResultWrapper<T> =
        withContext(Dispatchers.IO) { apiCall { call() } }

    private fun <T> handleResponse(response: Response<T>): ResultWrapper<T> {
        if (response.isSuccessful) {
            response.body()?.let {
                return ResultWrapper.Success(
                    data = it,
                    header = response.headers(),
                    code = response.code()
                )
            }
        }
        return ResultWrapper.Failed(
            error = ErrorEntity.Api(response.errorBody()?.string()),
        )
    }

    private inline fun <T> apiCall(call: () -> Response<T>): ResultWrapper<T> {
        return try {
            handleResponse(call())
        } catch (t: Throwable) {
            ResultWrapper.Failed(getError(t))
        }
    }

    override fun getError(throwable: Throwable): ErrorEntity {
        return when (throwable) {
            is IOException,
            is UnknownHostException,
            is NoInternetException,
            is ServerException,
            is SocketException -> ErrorEntity.Network(message = "${throwable.message}//${throwable.cause}")
            is SQLiteException -> ErrorEntity.Database(message = "${throwable.message}//${throwable.cause}")
            is HttpException -> ErrorEntity.Api(
                message = throwable.response()?.message(),
                code = throwable.code(),
                errorBody = throwable.response()?.errorBody()?.string()
            )
            else -> ErrorEntity.Unknown(message = "${throwable.message}//${throwable.cause}")
        }
    }

}