package com.emamagic.moviestreaming.util.helper.safe.error

import java.io.IOException

class NoInternetException(message: String): IOException(message)
class ServerException(message: String): IOException(message)