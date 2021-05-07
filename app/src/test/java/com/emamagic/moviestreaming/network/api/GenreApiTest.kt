package com.emamagic.moviestreaming.network.api

import com.emamagic.moviestreaming.enqueueResponse
import com.emamagic.moviestreaming.network.dto.GenreDto
import com.emamagic.moviestreaming.network.response.GenreListResponse
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class GenreApiTest {

    private val mockWebServer = MockWebServer()
    private lateinit var api: GenreApi

    @Before
    fun setUp() {
      //  mockWebServer.start()

        val client = OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.SECONDS)
            .readTimeout(1, TimeUnit.SECONDS)
            .writeTimeout(1, TimeUnit.SECONDS)
            .build()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GenreApi::class.java)
    }


    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @Test
    fun getAllGenre_status200() {
        mockWebServer.enqueueResponse("genre_200.json" ,200)
        runBlocking {
            val actual = api.getAllGenre()

            val expected = GenreListResponse(listOf(
                GenreDto(
                    id = 1,
                    name = "comedy",
                    imageLink = "https://i.pinimg.com/originals/22/91/cb/2291cb462e2e33ece794c094b684fe5e.jpg"
                )
            ))
            assertThat(expected).isEqualTo(actual)
        }
    }


}