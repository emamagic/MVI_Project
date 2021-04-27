package com.emamagic.moviestreaming.util

import android.util.Log
import com.emamagic.moviestreaming.util.TAG
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object DoesNetworkHaveInternet {

    fun execute(): Boolean{
        return try {
            Log.e(TAG, "PINGING google")
            val socket = Socket()
            socket.connect(InetSocketAddress("8.8.8.8" ,53) ,1500)
            socket.close()
            Log.e(TAG, "PING success")
            true
        }catch (e: IOException){
            Log.e(TAG, "No internet connection -> ${e.message}")
            false
        }
    }
}