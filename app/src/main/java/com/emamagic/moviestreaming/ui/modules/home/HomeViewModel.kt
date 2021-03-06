package com.emamagic.moviestreaming.ui.modules.home

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.viewModelScope
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.data.repository.home.HomeRepository
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.provider.safe.ResultWrapper
import com.emamagic.moviestreaming.ui.base.BaseViewModel
import com.emamagic.moviestreaming.ui.modules.home.contract.*
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: HomeRepository
) : BaseViewModel<HomeState, HomeEffect, HomeEvent>() {

    private var shouldCloseApp: Boolean = false
    override fun createInitialState() = HomeState.initialize()

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.GetSliders -> getSliders()
            HomeEvent.GetMovies -> getMovies()
            HomeEvent.GetGenre -> getGenre()
            HomeEvent.ShouldCloseApp -> shouldCloseApp()
            is HomeEvent.MoreMovieClicked -> moreMovieClicked(event.categoryType)
            HomeEvent.SwipeRefreshed -> swipeRefreshed()
            is HomeEvent.GenreClicked -> genreClicked(event.genreName)
            is HomeEvent.MovieClicked -> movieClicked(event.movie)
            HomeEvent.SearchClicked -> searchClicked()
            HomeEvent.FavoriteClicked -> favoriteCliced()
        }.exhaustive
    }

    override fun showError(errorMessage: String) {
        setEffect { HomeEffect.ShowToast(errorMessage ,ToastyMode.MODE_TOAST_ERROR) }
    }


    private fun getSliders() = viewModelScope.launch {
        setEffect { HomeEffect.Loading(isLoading = true) }
        repository.getSliders().collect {
            when (it) {
                is ResultWrapper.Success -> setState { copy(sliders = it.data!! ,currentState = CurrentHomeState.SLIDER_RECEIVED) }
                is ResultWrapper.Failed -> {
                    /** You can error handling with ErrorEntity ->
                    when(it.error){
                        is ErrorEntity.Network -> Timber.e("network")
                        is ErrorEntity.Api -> Timber.e("api ${it.error.code}")
                        ....
                    }*/
                    setState { copy(sliders = it.data!! ,currentState = CurrentHomeState.SLIDER_RECEIVED) }
                }
                is ResultWrapper.FetchLoading -> setState { copy(sliders = it.data!! ,currentState = CurrentHomeState.SLIDER_RECEIVED) }
            }.exhaustive
        }
    }

    private fun getMovies() = viewModelScope.launch {

        combine(
            repository.getMoviesByCategory(CategoryType.TOP),
            repository.getMoviesByCategory(CategoryType.NEW),
            repository.getMoviesByCategory(CategoryType.SERIES),
            repository.getMoviesByCategory(CategoryType.POPULAR),
            repository.getMoviesByCategory(CategoryType.ANIMATION),
        ) { top, new, series, popular, animation ->
            HomeApiHolder(top,new ,series, popular, animation)
        }.collect {
            setState { copy(movies = it ,currentState = CurrentHomeState.MOVIE_RECEIVED) }
        }

/*        merge(
            repository.getMovies(CategoryType.TOP),
            repository.getMovies(CategoryType.NEW),
            repository.getMovies(CategoryType.SERIES),
            repository.getMovies(CategoryType.POPULAR),
            repository.getMovies(CategoryType.ANIMATION),
        ).onStart {
            setEffect { HomeEffect.Loading(isLoading = true) }
        }.onCompletion {
            Timber.e("end")
        }.collect {
            when(it){
                is ResultWrapper.Success -> setState { copy(movies = it.data!! ,currentState = CurrentHomeState.MOVIE_RECEIVED) }
                is ResultWrapper.Failed -> {
                    setEffect { HomeEffect.ShowToast(message = "${it.error?.message} // ${it.error?.code} // ${it.error?.errorBody}" ,ToastyMode.MODE_TOAST_ERROR) }
                    setState { copy(movies = it.data!! ,currentState = CurrentHomeState.MOVIE_RECEIVED) }
                }
                is ResultWrapper.FetchLoading -> setState { copy(movies = it.data!! ,currentState = CurrentHomeState.MOVIE_RECEIVED) }
            }.exhaustive
        }*/

    }

    private fun getGenre() = viewModelScope.launch {
        repository.getGenre().onCompletion {
            /** It is not called , that is not true!!!! */
            Timber.e("completed 2")
        }.collect {
            when (it) {
                is ResultWrapper.Success -> setState { copy(genres = it.data!! ,currentState = CurrentHomeState.GENRE_RECEIVE) }
                is ResultWrapper.Failed -> setState { copy(genres = it.data!! ,currentState = CurrentHomeState.GENRE_RECEIVE) }
               is ResultWrapper.FetchLoading -> setState { copy(genres = it.data!! ,currentState = CurrentHomeState.GENRE_RECEIVE) }
            }.exhaustive
            setEffect { HomeEffect.Loading(false) }
            // if refresh mode is enabled , we should disabled
            repository.disableRefreshModel()
            setEffect { HomeEffect.DisableRefreshing }
        }
    }


    private fun shouldCloseApp() = viewModelScope.launch {
        Handler(Looper.getMainLooper()).postDelayed({
            shouldCloseApp = false
        } ,3000)
        if (shouldCloseApp) setState { copy(closeApp = true ,currentState = CurrentHomeState.CLOSE_APP) }
        else {
            shouldCloseApp = true
            setEffect { HomeEffect.ShowToast("Please Press Back Button Again" ,ToastyMode.MODE_TOAST_WARNING) }
        }
    }


    private fun movieClicked(movie: MovieEntity) = viewModelScope.launch {
        setEffect { HomeEffect.Navigate(HomeFragmentDirections.actionHomeFragmentToMovieFragment(movie)) }
    }

    private fun genreClicked(genreName: String) = viewModelScope.launch {
        setEffect { HomeEffect.Navigate(HomeFragmentDirections.actionHomeFragmentToGenreListFragment(genreName)) }
    }


    private fun searchClicked() = viewModelScope.launch {
        setEffect { HomeEffect.Navigate(HomeFragmentDirections.actionHomeFragmentToSearchTypeFragment()) }
    }

    private fun favoriteCliced() = viewModelScope.launch {
        setEffect { HomeEffect.Navigate(HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()) }
    }


    private fun moreMovieClicked(@CategoryType categoryType: String) = viewModelScope.launch {
        when (categoryType) {
            CategoryType.TOP,
            CategoryType.NEW,
            CategoryType.SERIES,
            CategoryType.POPULAR,
            CategoryType.ANIMATION -> setEffect { HomeEffect.Navigate(HomeFragmentDirections.actionHomeFragmentToMovieListFragment(categoryType)) }
            CategoryType.GENRE -> setEffect { HomeEffect.Navigate(HomeFragmentDirections.actionHomeFragmentToGenreTypeFragment()) }
        }

    }

    private fun swipeRefreshed() = viewModelScope.launch {
        repository.enableRefreshMode()
        getSliders()
        getMovies()
        getGenre()
    }


}