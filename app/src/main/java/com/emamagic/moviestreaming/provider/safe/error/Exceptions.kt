package com.emamagic.moviestreaming.provider.safe.error

import java.io.IOException

class NoInternetException(message: String): IOException(message)
class ServerException(message: String): IOException(message)