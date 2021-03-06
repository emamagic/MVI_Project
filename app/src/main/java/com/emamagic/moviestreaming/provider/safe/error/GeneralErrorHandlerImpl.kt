package com.emamagic.moviestreaming.provider.safe.error

import android.database.sqlite.SQLiteException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketException
import java.net.UnknownHostException

abstract class GeneralErrorHandlerImpl : ErrorHandler {

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