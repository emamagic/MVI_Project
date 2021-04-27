package com.emamagic.moviestreaming.safe

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}