package com.emamagic.moviestreaming.safe

import com.emamagic.moviestreaming.safe.ErrorEntity

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorEntity
}