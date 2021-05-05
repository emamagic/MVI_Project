package com.emamagic.moviestreaming.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.emamagic.moviestreaming.MainCoroutineRuleAndroid
import com.emamagic.moviestreaming.db.dao.FavoriteDao
import com.emamagic.moviestreaming.db.dao.MovieDao
import com.emamagic.moviestreaming.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.MovieWithFavorite
import com.emamagic.moviestreaming.runBlocking
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class MovieDatabaseTest{

    @Inject
    @Named("TEST_DB")
    lateinit var db: MovieDatabase
    private lateinit var movieDao: MovieDao
    private lateinit var favoriteDao: FavoriteDao

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    val hiltAndroidRule = HiltAndroidRule(this)
    @get:Rule
    var mainCoroutineRule = MainCoroutineRuleAndroid()

    @Before
    fun setUp(){
        hiltAndroidRule.inject()
        movieDao = db.movieDao()
        favoriteDao = db.favoriteDao()

    }

    @After
    fun tearDown(){
        db.close()
    }

    ////////////////////////////// MovieDao ///////////////////////////////////

    @Test
    fun getMovieWithFavorite() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val favorite = FavoriteEntity(1)
        movieDao.insert(movie)
        favoriteDao.insert(favorite)
        val movieFavorite = MovieWithFavorite(movie ,favorite)
        val result = movieDao.getAllMovie("series").take(1).toList()
        assertThat(result[0]).contains(movieFavorite)
    }

    @Test
    fun getMovieByCategory() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        movieDao.insert(movie)
        val result = movieDao.getMoviesByCategory("series").take(1).toList()
        assertThat(result[0]).contains(movie)
    }


    @Test
    fun getMovieById() = mainCoroutineRule.runBlocking {
        val movieOne = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movieTwo = MovieEntity(2 ,"bad boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        movieDao.insert(movieOne)
        movieDao.insert(movieTwo)
        val resultOne = movieDao.getMovieById(1)
        val resultTwo = movieDao.getMovieById(2)
        assertThat(resultOne).isEqualTo(resultOne)
        assertThat(resultTwo).isEqualTo(resultTwo)
    }

    @Test
    fun updateMovie() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1, "hell boy", "imgLink", "imgVideo", "videoLing", "time", "series", "3")
        movieDao.insert(movie)
        movieDao.updateMovieDetail(1, "description", "imageUpdate", "videoLinkUpdate")
        val result = movieDao.getMovieById(1)
        assertThat(result.description).isEqualTo("description")
        assertThat(result.imageVideoLink).isEqualTo("imageUpdate")
        assertThat(result.videoLink).isEqualTo("videoLinkUpdate")
    }

    @Test
    fun getMovieByGenre() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1, "hell boy", "imgLink", "imgVideo", "videoLing", "time", "series", "3" ,genreName = "war")
        movieDao.insert(movie)
        val favorite = FavoriteEntity(1)
        favoriteDao.insert(favorite)
        val result = MovieWithFavorite(movie ,favorite)
        val movies = movieDao.getMoviesByGenre("war").take(1).toList()
        assertThat(movies[0]).contains(result)
    }

    @Test
    fun getFavorite() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1, "hell boy", "imgLink", "imgVideo", "videoLing", "time", "series", "3" ,genreName = "war")
        val favorite = FavoriteEntity(1)
        movieDao.insert(movie)
        favoriteDao.insert(favorite)
        val result = movieDao.getFavoriteMovie().take(1).toList()
        assertThat(result[0]).contains(movie)
    }

}