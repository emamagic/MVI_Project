package com.emamagic.moviestreaming.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.emamagic.moviestreaming.MainCoroutineRuleAndroid
import com.emamagic.moviestreaming.base.upsert
import com.emamagic.moviestreaming.db.MovieDatabase
import com.emamagic.moviestreaming.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.MovieWithFavorite
import com.emamagic.moviestreaming.runBlocking
import com.google.common.truth.Truth
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
class MovieDaoTest {

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
    fun insetMovie() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val id = movieDao.insert(movie)
        Truth.assertThat(id).isEqualTo(1L)
    }

    @Test
    fun insertMovies() = mainCoroutineRule.runBlocking {
        val movie1 = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie2 = MovieEntity(2 ,"hell boy2" ,"imgLink2" ,"imgVideo2" ,"videoLing2" ,"time" ,"series" ,"3")
        val result = movieDao.insert(listOf(movie1 ,movie2))
        Truth.assertThat(result).contains(1L)
        Truth.assertThat(result).contains(2L)
    }

    @Test
    fun upsertMovie_insert() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        movieDao.upsert(movie)
        val result = movieDao.getMovies().take(1).toList()
        Truth.assertThat(result[0]).contains(movie)
    }

    @Test
    fun upsertMovies_insert() = mainCoroutineRule.runBlocking {
        val movie1 = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie2 = MovieEntity(2 ,"hell boy2" ,"imgLink2" ,"imgVideo2" ,"videoLing2" ,"time2" ,"series2" ,"3")
        movieDao.upsert(listOf(movie1 ,movie2))
        val result = movieDao.getMovies().take(1).toList()
        Truth.assertThat(result[0]).contains(movie1)
        Truth.assertThat(result[0]).contains(movie2)
    }

    @Test
    fun upsertMovie_update() = mainCoroutineRule.runBlocking {
        val movieOld = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        movieDao.insert(movieOld)
        val movieNew = MovieEntity(1 ,"hell boy2" ,"imgLink2" ,"imgVideo2" ,"videoLing" ,"time" ,"series" ,"3")
        movieDao.upsert(movieNew)
        val result = movieDao.getMovies().take(1).toList()
        Truth.assertThat(result).hasSize(1)
        Truth.assertThat(result[0]).contains(movieNew)
    }

    @Test
    fun upsertMovies_update() = mainCoroutineRule.runBlocking {
        val movie1_old = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie2_old = MovieEntity(2 ,"hell boy2" ,"imgLink2" ,"imgVideo2" ,"videoLing2" ,"time" ,"series" ,"3")
        val movie3_old = MovieEntity(3 ,"hell boy3" ,"imgLink3" ,"imgVideo3" ,"videoLing3" ,"time" ,"series" ,"3")
        movieDao.insert(listOf(movie1_old ,movie2_old ,movie3_old))
        val movie1_new = MovieEntity(1 ,"bad boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie2_new = MovieEntity(2 ,"bad boy2" ,"imgLink2" ,"imgVideo2" ,"videoLing2" ,"time" ,"series" ,"3")
        val movie3_new = MovieEntity(3 ,"bad boy3" ,"imgLink3" ,"imgVideo3" ,"videoLing3" ,"time" ,"series" ,"3")
        movieDao.upsert(listOf(movie1_new, movie2_new, movie3_new))
        val result = movieDao.getMovies().take(1).toList()
        Truth.assertThat(result[0]).hasSize(3)
        Truth.assertThat(result[0]).contains(movie1_new)
        Truth.assertThat(result[0]).contains(movie2_new)
        Truth.assertThat(result[0]).contains(movie3_new)
    }


    @Test
    fun getMoviesByCategoryLimit6_ValidCategory() = mainCoroutineRule.runBlocking {
        val movie1 = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie2 = MovieEntity(2 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie3 = MovieEntity(3 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie4 = MovieEntity(4 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie5 = MovieEntity(5 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie6 = MovieEntity(6 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie7 = MovieEntity(7 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie8 = MovieEntity(8 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        movieDao.insert(listOf(movie1, movie2, movie3, movie4, movie5, movie6, movie7, movie8))
        val result = movieDao.getMoviesByCategoryLimit6("series").take(1).toList()
        Truth.assertThat(result[0]).contains(movie1)
        Truth.assertThat(result[0]).contains(movie2)
        Truth.assertThat(result[0]).contains(movie3)
        Truth.assertThat(result[0]).contains(movie4)
        Truth.assertThat(result[0]).contains(movie5)
        Truth.assertThat(result[0]).contains(movie6)
        Truth.assertThat(result[0]).doesNotContain(movie7)
        Truth.assertThat(result[0]).doesNotContain(movie8)
    }

    @Test
    fun getMoviesByCategoryLimit6_InvalidCategory() = mainCoroutineRule.runBlocking {
        val movie1 = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie2 = MovieEntity(2 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movie3 = MovieEntity(3 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        movieDao.insert(listOf(movie1, movie2, movie3))
        val result = movieDao.getMoviesByCategoryLimit6("invalid_category").take(1).toList()
        Truth.assertThat(result[0]).isEmpty()
    }

    @Test
    fun getMoviesWithFavoriteByCategory() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val favorite = FavoriteEntity(1)
        movieDao.insert(movie)
        favoriteDao.insert(favorite)
        val movieFavorite = MovieWithFavorite(movie ,favorite)
        val result = movieDao.getMoviesWithFavoriteByCategory("series").take(1).toList()
        Truth.assertThat(result[0]).contains(movieFavorite)
    }

    @Test
    fun getMoviesWithFavoriteByCategory_Not_Favorite() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val favorite = FavoriteEntity(2)
        movieDao.insert(movie)
        favoriteDao.insert(favorite)
        val result = movieDao.getMoviesWithFavoriteByCategory("series").take(1).toList()
        val movieFavorite = MovieWithFavorite(movie , null)
        Truth.assertThat(result[0]).contains(movieFavorite)
    }

    @Test
    fun getMoviesWithFavoriteByCategory_Invalid_Category() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val favorite = FavoriteEntity(1)
        movieDao.insert(movie)
        favoriteDao.insert(favorite)
        val movieFavorite = MovieWithFavorite(movie ,favorite)
        val result = movieDao.getMoviesWithFavoriteByCategory("invalid_category").take(1).toList()
        Truth.assertThat(result[0]).doesNotContain(movieFavorite)
        Truth.assertThat(result[0]).isEmpty()
    }

    @Test
    fun getMovieById() = mainCoroutineRule.runBlocking {
        val movieOne = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movieTwo = MovieEntity(2 ,"bad boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        movieDao.insert(listOf(movieOne, movieTwo))
        val resultOne = movieDao.getMovieById(1)
        val resultTwo = movieDao.getMovieById(2)
        Truth.assertThat(resultOne).isEqualTo(resultOne)
        Truth.assertThat(resultTwo).isEqualTo(resultTwo)
    }

    @Test
    fun getMovieById_Invalid_Id() = mainCoroutineRule.runBlocking {
        val movieOne = MovieEntity(1 ,"hell boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        val movieTwo = MovieEntity(2 ,"bad boy" ,"imgLink" ,"imgVideo" ,"videoLing" ,"time" ,"series" ,"3")
        movieDao.insert(listOf(movieOne, movieTwo))
        val resultOne = movieDao.getMovieById(1)
        val resultTwo = movieDao.getMovieById(3)
        Truth.assertThat(resultOne).isEqualTo(resultOne)
        Truth.assertThat(resultTwo).isNull()
    }

    @Test
    fun updateMovieDetailById() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1, "hell boy", "imgLink", "imgVideo", "videoLing", "time", "series", "3")
        movieDao.insert(movie)
        movieDao.updateMovieDetailById(1, "description", "imageUpdate", "videoLinkUpdate")
        val result = movieDao.getMovieById(1)
        Truth.assertThat(result.description).isEqualTo("description")
        Truth.assertThat(result.imageVideoLink).isEqualTo("imageUpdate")
        Truth.assertThat(result.videoLink).isEqualTo("videoLinkUpdate")
    }

    @Test
    fun updateMovieDetailById_Invalid_Id() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1, "hell boy", "imgLink", "imgVideo", "videoLing", "time", "series", "3")
        movieDao.insert(movie)
        movieDao.updateMovieDetailById(2, "description", "imageUpdate", "videoLinkUpdate")
        val result = movieDao.getMovieById(1)
        Truth.assertThat(result).isEqualTo(movie)
    }

    @Test
    fun getMoviesWithFavoriteByGenre() = mainCoroutineRule.runBlocking {
        val movie1 = MovieEntity(1, "hell boy", "imgLink", "imgVideo", "videoLing", "time", "series", "3" ,genreName = "war")
        val movie2 = MovieEntity(2, "hell boy", "imgLink", "imgVideo", "videoLing", "time", "series", "3" ,genreName = "war")
        movieDao.insert(listOf(movie1, movie2))
        val favorite1 = FavoriteEntity(1)
        val favorite2 = FavoriteEntity(2)
        favoriteDao.insert(listOf(favorite1, favorite2))
        val result1 = MovieWithFavorite(movie1 ,favorite1)
        val result2 = MovieWithFavorite(movie2 ,favorite2)
        val movies = movieDao.getMoviesWithFavoriteByGenre("war").take(1).toList()
        Truth.assertThat(movies[0]).contains(result1)
        Truth.assertThat(movies[0]).contains(result2)
    }

    @Test
    fun getFavoriteMovie() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1, "hell boy", "imgLink", "imgVideo", "videoLing", "time", "series", "3" ,genreName = "war")
        val favorite = FavoriteEntity(1)
        movieDao.insert(movie)
        favoriteDao.insert(favorite)
        val result = movieDao.getFavoriteMovie().take(1).toList()
        Truth.assertThat(result[0]).contains(movie)
    }

    @Test
    fun getFavoriteMovie_Invalid_Id() = mainCoroutineRule.runBlocking {
        val movie = MovieEntity(1, "hell boy", "imgLink", "imgVideo", "videoLing", "time", "series", "3" ,genreName = "war")
        val favorite = FavoriteEntity(2)
        movieDao.insert(movie)
        favoriteDao.insert(favorite)
        val result = movieDao.getFavoriteMovie().take(1).toList()
        Truth.assertThat(result[0]).isEmpty()
    }

}