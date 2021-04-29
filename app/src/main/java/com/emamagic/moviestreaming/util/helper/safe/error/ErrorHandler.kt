package com.emamagic.moviestreaming.util.helper.safe.error

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}