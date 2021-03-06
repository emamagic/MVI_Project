package com.emamagic.moviestreaming.network.api

import com.emamagic.moviestreaming.enqueueResponse
import com.emamagic.moviestreaming.data.network.dto.GenreDto
import com.emamagic.moviestreaming.network.response.GenreListResponse
import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GenreApiTest {

    private val mockWebServer = MockWebServer()
    private lateinit var genreApi: com.emamagic.moviestreaming.data.network.api.GenreApi

    @Before
    fun setUp() {
      //  mockWebServer.start()

        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        genreApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(com.emamagic.moviestreaming.data.network.api.GenreApi::class.java)
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun getAllGenre_status200() {
        mockWebServer.enqueueResponse("genre_200.json" ,200)
        runBlocking {
            val actual = genreApi.getAllGenre()

            val expected = GenreListResponse(listOf(
                com.emamagic.moviestreaming.data.network.dto.GenreDto(
                    id = 1,
                    name = "comedy",
                    imageLink = "https://i.pinimg.com/originals/22/91/cb/2291cb462e2e33ece794c094b684fe5e.jpg"
                )
            ))
            Truth.assertThat(expected).isEqualTo(actual)
        }
    }


}