package com.emamagic.moviestreaming.ui.home

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentHomeBinding
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SliderEntity
import com.emamagic.moviestreaming.ui.adapter.GenreAdapter
import com.emamagic.moviestreaming.ui.adapter.MovieHorAdapter
import com.emamagic.moviestreaming.ui.adapter.MovieVerAdapter
import com.emamagic.moviestreaming.ui.adapter.SliderAdapter
import com.emamagic.moviestreaming.ui.home.contract.CurrentHomeState
import com.emamagic.moviestreaming.ui.home.contract.HomeEffect
import com.emamagic.moviestreaming.ui.home.contract.HomeEvent
import com.emamagic.moviestreaming.ui.home.contract.HomeState
import com.emamagic.moviestreaming.util.Const
import com.emamagic.moviestreaming.util.ToastyMode
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeState, HomeEffect, HomeEvent, HomeViewModel>() ,NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener{

    override val viewModel: HomeViewModel by viewModels()
    private lateinit var sliderAdapter: SliderAdapter
    private var timer: Timer? = null
    private var movieIMDBAdapter: MovieVerAdapter? = null
    private var movieNewAdapter: MovieVerAdapter? = null
    private var movieSeriesAdapter: MovieVerAdapter? = null
    private var moviePopularAdapter: MovieHorAdapter? = null
    private var animationAdapter: MovieHorAdapter? = null
    private var genreAdapter: GenreAdapter? = null

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieIMDBAdapter = MovieVerAdapter()
        movieNewAdapter = MovieVerAdapter()
        movieSeriesAdapter = MovieVerAdapter()
        moviePopularAdapter = MovieHorAdapter()
        animationAdapter = MovieHorAdapter()
        genreAdapter = GenreAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeOnNetworkStatusChange { isNetworkAvailable -> if (!isNetworkAvailable) toasty("you have no Internet") }

        setUpDrawer()

        setEvents()

        binding?.btnMenu?.setOnClickListener(this)
        binding?.txtMoreGenre?.setOnClickListener(this)
        binding?.swipeRefreshLayout?.setOnRefreshListener { viewModel.setEvent(HomeEvent.SwipeRefreshed) }

        onFragmentBackPressed(viewLifecycleOwner) {
            if (binding?.drawerLayout?.isDrawerOpen(GravityCompat.START)!!)
                binding?.drawerLayout?.closeDrawer(GravityCompat.START)
            else viewModel.setEvent(HomeEvent.ShouldCloseApp)
        }

    }

    override fun renderViewState(viewState: HomeState) {
     //   if (viewState.isLoading) showLoading() else hideLoading()
        when (viewState.currentState) {
            CurrentHomeState.NON_STATE -> { /* Do Nothing */ }
            CurrentHomeState.SLIDER_RECEIVED -> setUpSlider(requireContext(), viewState.sliders)
            CurrentHomeState.MOVIE_RECEIVED -> setUpMovie(viewState.movies)
            CurrentHomeState.GENRE_RECEIVE -> setUpGenreRecycler(viewState.genres)
            CurrentHomeState.CLOSE_APP -> requireActivity().finish()
        }


    }

    override fun renderViewEffect(viewEffect: HomeEffect) {
        when (viewEffect) {
            is HomeEffect.Navigate -> findNavController().navigate(viewEffect.destination)
            is HomeEffect.ShowToast -> {
                when(viewEffect.mode){
                    ToastyMode.MODE_TOAST_SUCCESS -> toasty(viewEffect.message, ToastyMode.MODE_TOAST_SUCCESS)
                    ToastyMode.MODE_TOAST_WARNING -> toasty(viewEffect.message, ToastyMode.MODE_TOAST_WARNING)
                    ToastyMode.MODE_TOAST_ERROR -> toasty(viewEffect.message, ToastyMode.MODE_TOAST_ERROR)
                    else -> toasty(viewEffect.message)
                }.exhaustive
            }
            is HomeEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            HomeEffect.DisableRefreshing -> binding?.swipeRefreshLayout?.isRefreshing = false
        }.exhaustive
    }

    private fun setUpSlider(context: Context, list: List<SliderEntity>) {
        sliderAdapter = SliderAdapter(context, list)
        binding?.slider?.adapter = sliderAdapter
        binding?.tabLayout?.setupWithViewPager(binding?.slider, true)
        if (timer == null) {
            Timber.e("call slider")
            timer = Timer()
            timer?.scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    Handler(Looper.getMainLooper()).post {
                        binding?.let {
                            if (it.slider.currentItem < list.size - 1)
                                it.slider.currentItem = it.slider.currentItem + 1
                            else it.slider.currentItem = 0
                        }
                    }
                }
            }, 3000, 3000)
        }

    }

    private fun setEvents(){
        viewModel.setEvent(HomeEvent.GetSliders)
        viewModel.setEvent(HomeEvent.GetMovies(Const.TOP_MOVIE_IMDB))
        viewModel.setEvent(HomeEvent.GetMovies(Const.NEW_MOVIE))
        viewModel.setEvent(HomeEvent.GetMovies(Const.SERIES_MOVIE))
        viewModel.setEvent(HomeEvent.GetMovies(Const.POPULAR_MOVIE))
        viewModel.setEvent(HomeEvent.GetMovies(Const.ANIMATION))
        viewModel.setEvent(HomeEvent.GetGenre)
    }

    private fun setUpMovie(list: List<MovieEntity>) {
        if (list.isNotEmpty()) {
            when (list.first().categoryName) {
                Const.TOP_MOVIE_IMDB -> setUpTopMovieRecycler(list)
                Const.NEW_MOVIE -> setUpNewMovieRecycler(list)
                Const.SERIES_MOVIE -> setUpSeriesRecycler(list)
                Const.POPULAR_MOVIE -> setUpPopularRecycler(list)
                Const.ANIMATION -> setUpAnimationRecycler(list)
            }
        }

    }

    private fun setUpGenreRecycler(list: List<GenreEntity>) {
        binding?.recyclerViewGenre?.adapter = genreAdapter
        binding?.recyclerViewGenre?.setHasFixedSize(true)
        genreAdapter?.submitList(list)

    }

    private fun setUpNewMovieRecycler(list: List<MovieEntity>) {
        binding?.recyclerViewNewMovie?.adapter = movieNewAdapter
        binding?.recyclerViewNewMovie?.setHasFixedSize(true)
        movieNewAdapter?.submitList(list)

    }

    private fun setUpTopMovieRecycler(list: List<MovieEntity>) {
        binding?.recyclerViewTopMovieImdb?.adapter = movieIMDBAdapter
        binding?.recyclerViewTopMovieImdb?.setHasFixedSize(true)
        movieIMDBAdapter?.submitList(list)

    }

    private fun setUpSeriesRecycler(list: List<MovieEntity>) {
        binding?.recyclerViewSeries?.adapter = movieSeriesAdapter
        binding?.recyclerViewSeries?.setHasFixedSize(true)
        movieSeriesAdapter?.submitList(list)

    }

    private fun setUpAnimationRecycler(list: List<MovieEntity>) {
        binding?.recyclerViewAnimation?.adapter = animationAdapter
        binding?.recyclerViewAnimation?.setHasFixedSize(true)
        animationAdapter?.submitList(list)

    }

    private fun setUpPopularRecycler(list: List<MovieEntity>) {
        binding?.recyclerViewPopularMovie?.adapter = moviePopularAdapter
        binding?.recyclerViewPopularMovie?.setHasFixedSize(true)
        moviePopularAdapter?.submitList(list)

    }

    private fun setUpDrawer() {
        binding?.navigationView?.bringToFront()
        binding?.navigationView?.setNavigationItemSelectedListener(this)
        binding?.navigationView?.setCheckedItem(R.id.nav_home)
        binding?.drawerLayout?.setScrimColor(resources.getColor(R.color.colorPrimary))
        binding?.drawerLayout?.addDrawerListener(object: DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) { slidFragment(binding?.parent!! ,drawerView ,slideOffset) }
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {}
            override fun onDrawerStateChanged(newState: Int) {}
        })
    }

    private fun slidFragment(parent: View ,drawerView: View ,slideOffset: Float) {
        val changeScaleOffSet = slideOffset * (1-0.7f)
        val OFFSET_SCALTE = 1-changeScaleOffSet
        parent.scaleX = OFFSET_SCALTE
        parent.scaleY = OFFSET_SCALTE
        val X_OFFSET = drawerView.width * slideOffset
        val X_OFFSET_CHANGE = parent.width * changeScaleOffSet / 2
        val X_TRANSLATOIN = X_OFFSET - X_OFFSET_CHANGE
        parent.translationX = X_TRANSLATOIN
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.btn_menu -> binding?.drawerLayout?.openDrawer(GravityCompat.START)
            R.id.txt_more_genre -> { viewModel.setEvent(HomeEvent.MoreGenreClicked) }
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