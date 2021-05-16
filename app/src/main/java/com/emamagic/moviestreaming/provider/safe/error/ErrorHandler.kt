package com.emamagic.moviestreaming.provider.safe.error

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}