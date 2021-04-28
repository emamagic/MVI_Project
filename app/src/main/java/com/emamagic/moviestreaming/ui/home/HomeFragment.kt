package com.emamagic.moviestreaming.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentHomeBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.ui.adapter.MovieAdapter
import com.emamagic.moviestreaming.ui.adapter.SliderAdapter
import com.emamagic.moviestreaming.ui.home.contract.CurrentHomeState
import com.emamagic.moviestreaming.ui.home.contract.HomeEffect
import com.emamagic.moviestreaming.ui.home.contract.HomeEvent
import com.emamagic.moviestreaming.ui.home.contract.HomeState
import com.emamagic.moviestreaming.util.Const
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeState, HomeEffect, HomeEvent, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var sliderAdapter: SliderAdapter
    private var timer: Timer? = null
    var movieIMDBAdapter: MovieAdapter? = null
    var movieNewAdapter: MovieAdapter? = null
    var movieSeriesAdapter: MovieAdapter? = null

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setEvent(HomeEvent.GetSliders)
        viewModel.setEvent(HomeEvent.GetMovies(Const.TOP_MOVIE_IMDB))
        viewModel.setEvent(HomeEvent.GetMovies(Const.NEW_MOVIE))
        viewModel.setEvent(HomeEvent.GetMovies(Const.SERIES_MOVIE))

    }

    override fun renderViewState(viewState: HomeState) {
        if (viewState.isLoading) showLoading() else hideLoading()
        when (viewState.currentState) {
            CurrentHomeState.NON_STATE -> { /* Do Nothing */ }
            CurrentHomeState.SLIDER_RECEIVED -> setUpSlider(requireContext() ,viewState.sliders)
            CurrentHomeState.MOVIE_RECEIVED -> setUpMovie(viewState.movies)
        }


    }

    override fun renderViewEffect(viewEffect: HomeEffect) {
        when (viewEffect) {
            is HomeEffect.Navigate -> {  }
            is HomeEffect.ShowToast -> toasty(viewEffect.message, ToastyMode.MODE_TOAST_ERROR)
        }.exhaustive
    }


    private fun setUpSlider(context: Context, list: List<SliderEntity>) {
        sliderAdapter = SliderAdapter(context, list)
        binding?.slider?.adapter = sliderAdapter
        binding?.tabLayout?.setupWithViewPager(binding?.slider, true)
        if (timer == null){
            Timber.e("call slider")
            timer = Timer()
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    Handler(Looper.getMainLooper()).post {
                        if (binding?.slider?.currentItem!! < list.size - 1)
                            binding?.slider?.currentItem = binding?.slider?.currentItem!! + 1
                        else binding?.slider?.currentItem = 0
                    }
                }
            }, 3000, 3000)
        }

    }

    private fun setUpMovie(list: List<MovieEntity>) {
        if (list.isNotEmpty()){
            when(list[0].category_name){
                Const.TOP_MOVIE_IMDB -> {
                    if (movieIMDBAdapter == null){
                        Timber.e("call top movie")
                        movieIMDBAdapter = MovieAdapter()
                    }
                        binding?.recyclerViewTopMovieImdb?.adapter = movieIMDBAdapter
                        binding?.recyclerViewTopMovieImdb?.setHasFixedSize(true)
                        movieIMDBAdapter?.submitList(list)

                }
                Const.NEW_MOVIE -> {
                    if (movieNewAdapter == null){
                        Timber.e("call new movie")
                        movieNewAdapter = MovieAdapter()
                    }
                        binding?.recyclerViewNewMovie?.adapter = movieNewAdapter
                        binding?.recyclerViewNewMovie?.setHasFixedSize(true)
                        movieNewAdapter?.submitList(list)
                }
                Const.SERIES_MOVIE -> {
                    if (movieSeriesAdapter == null){
                        Timber.e("call series movie")
                        movieSeriesAdapter = MovieAdapter()
                    }
                        binding?.recyclerViewSeries?.adapter = movieSeriesAdapter
                        binding?.recyclerViewSeries?.setHasFixedSize(true)
                        movieSeriesAdapter?.submitList(list)

                }
            }
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        timer?.let {
            it.cancel()
            it.purge()
        }
    }

}