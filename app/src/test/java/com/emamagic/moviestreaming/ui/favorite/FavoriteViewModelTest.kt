package com.emamagic.moviestreaming.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.emamagic.moviestreaming.repository.favorite.FavoriteRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest {

    @Mock
    lateinit var favoriteRepository: FavoriteRepository

    lateinit var viewModel: FavoriteViewModel


    @Before
    fun setUp() {
        viewModel = FavoriteViewModel(favoriteRepository)
    }

    @Test
    fun getFavoriteMovies() {

    }

}