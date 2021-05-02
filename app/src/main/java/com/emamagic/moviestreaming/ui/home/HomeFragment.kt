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
import com.emamagic.moviestreaming.ui.home.adapter.GenreAdapter
import com.emamagic.moviestreaming.ui.home.adapter.MovieHorAdapter
import com.emamagic.moviestreaming.ui.home.adapter.MovieVerAdapter
import com.emamagic.moviestreaming.ui.home.adapter.SliderAdapter
import com.emamagic.moviestreaming.ui.home.contract.CurrentHomeState
import com.emamagic.moviestreaming.ui.home.contract.HomeEffect
import com.emamagic.moviestreaming.ui.home.contract.HomeEvent
import com.emamagic.moviestreaming.ui.home.contract.HomeState
import com.emamagic.moviestreaming.ui.home.contract.CategoryType
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import com.google.android.material.navigation.NavigationView
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.*

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeState, HomeEffect, HomeEvent, HomeViewModel>() ,NavigationView.OnNavigationItemSelectedListener ,
    View.OnClickListener,
    MovieVerAdapter.Interaction,
    MovieHorAdapter.Interaction,
    GenreAdapter.Interaction{

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
        movieIMDBAdapter = MovieVerAdapter(this)
        movieNewAdapter = MovieVerAdapter(this)
        movieSeriesAdapter = MovieVerAdapter(this)
        moviePopularAdapter = MovieHorAdapter(this)
        animationAdapter = MovieHorAdapter(this)
        genreAdapter = GenreAdapter(this)
        setEvents()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeOnNetworkStatusChange { isNetworkAvailable -> if (!isNetworkAvailable) toasty("you have no Internet") }

        setUpGenreRecycler(viewModel.currentState.genres)
        setUpSlider(requireContext() ,viewModel.currentState.sliders)
        setUpMovie(viewModel.currentState.movies)


        setUpDrawer()

        binding.btnMenu.setOnClickListener(this)
        binding.txtMoreGenre.setOnClickListener(this)
        binding.txtMoreAnimation.setOnClickListener(this)
        binding.txtMoreNewMovie.setOnClickListener(this)
        binding.txtMorePopularMovie.setOnClickListener(this)
        binding.txtMoreTopMovieImdb.setOnClickListener(this)
        binding.txtMoreSeries.setOnClickListener(this)

        binding.swipeRefreshLayout.setOnRefreshListener { viewModel.setEvent(HomeEvent.SwipeRefreshed) }

        onFragmentBackPressed(viewLifecycleOwner) {
            if (binding.drawerLayout.isDrawerOpen(GravityCompat.START))
                binding.drawerLayout.closeDrawer(GravityCompat.START)
            else viewModel.setEvent(HomeEvent.ShouldCloseApp)
        }

    }

    override fun renderViewState(viewState: HomeState) {
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
            is HomeEffect.Navigate -> findNavController().navigate(viewEffect.navDirect)
            is HomeEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
            is HomeEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            HomeEffect.DisableRefreshing -> binding.swipeRefreshLayout.isRefreshing = false
        }.exhaustive
    }

    private fun setUpSlider(context: Context, list: List<SliderEntity>) {
        sliderAdapter = SliderAdapter(context)
        binding.sliderView.setSliderAdapter(sliderAdapter)
        sliderAdapter.renewItems(list)
        binding.sliderView.startAutoCycle()

    }

    private fun setEvents(){
        viewModel.setEvent(HomeEvent.GetSliders)
        viewModel.setEvent(HomeEvent.GetGenre)
        viewModel.setEvent(HomeEvent.GetMovies)
    }

    private fun setUpMovie(item: HomeApiHolder){
        item.top?.data?.let { setUpTopMovieRecycler(it) }
        item.new?.data?.let { setUpNewMovieRecycler(it) }
        item.series?.data?.let { setUpSeriesRecycler(it) }
        item.popular?.data?.let {setUpPopularRecycler(it)  }
        item.animation?.data?.let { setUpAnimationRecycler(it) }
    }

    private fun setUpGenreRecycler(list: List<GenreEntity>) {
        binding.recyclerViewGenre.adapter = genreAdapter
        binding.recyclerViewGenre.setHasFixedSize(true)
        binding.recyclerViewGenre.itemAnimator = null
        genreAdapter?.submitList(list)

    }

    private fun setUpNewMovieRecycler(list: List<MovieEntity>) {
        binding.recyclerViewNewMovie.adapter = movieNewAdapter
        binding.recyclerViewNewMovie.setHasFixedSize(true)
        binding.recyclerViewNewMovie.itemAnimator = null
        movieNewAdapter?.submitList(list)

    }

    private fun setUpTopMovieRecycler(list: List<MovieEntity>) {
        binding.recyclerViewTopMovieImdb.adapter = movieIMDBAdapter
        binding.recyclerViewTopMovieImdb.setHasFixedSize(true)
        binding.recyclerViewTopMovieImdb.itemAnimator = null
        movieIMDBAdapter?.submitList(list)

    }

    private fun setUpSeriesRecycler(list: List<MovieEntity>) {
        binding.recyclerViewSeries.adapter = movieSeriesAdapter
        binding.recyclerViewSeries.setHasFixedSize(true)
        binding.recyclerViewSeries.itemAnimator = null
        movieSeriesAdapter?.submitList(list)

    }

    private fun setUpAnimationRecycler(list: List<MovieEntity>) {
        binding.recyclerViewAnimation.adapter = animationAdapter
        binding.recyclerViewAnimation.setHasFixedSize(true)
        binding.recyclerViewAnimation.itemAnimator = null
        animationAdapter?.submitList(list)

    }

    private fun setUpPopularRecycler(list: List<MovieEntity>) {
        binding.recyclerViewPopularMovie.adapter = moviePopularAdapter
        binding.recyclerViewPopularMovie.setHasFixedSize(true)
        binding.recyclerViewPopularMovie.itemAnimator = null
        moviePopularAdapter?.submitList(list)

    }

    private fun setUpDrawer() {
        binding.navigationView.bringToFront()
        binding.navigationView.setNavigationItemSelectedListener(this)
        binding.navigationView.setCheckedItem(R.id.nav_home)
        binding.drawerLayout.setScrimColor(resources.getColor(R.color.colorPrimary))
        binding.drawerLayout.addDrawerListener(object: DrawerLayout.DrawerListener{
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) { slidFragment(binding.parent ,drawerView ,slideOffset) }
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
            R.id.btn_menu -> binding.drawerLayout.openDrawer(GravityCompat.START)
            R.id.txt_more_genre -> viewModel.setEvent(HomeEvent.MoreMovieClicked(CategoryType.GENRE))
            R.id.txt_more_animation -> viewModel.setEvent(HomeEvent.MoreMovieClicked(
                CategoryType.ANIMATION))
            R.id.txt_more_new_movie -> viewModel.setEvent(HomeEvent.MoreMovieClicked(
                CategoryType.NEW))
            R.id.txt_more_popular_movie -> viewModel.setEvent(HomeEvent.MoreMovieClicked(
                CategoryType.POPULAR))
            R.id.txt_more_series -> viewModel.setEvent(HomeEvent.MoreMovieClicked(CategoryType.SERIES))
            R.id.txt_more_top_movie_imdb -> viewModel.setEvent(HomeEvent.MoreMovieClicked(
                CategoryType.TOP))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        timer?.let {
            it.cancel()
            it.purge()
        }
    }

    override fun onMovieVerClicked(item: MovieEntity) {
        viewModel.setEvent(HomeEvent.MovieClicked(item))
    }

    override fun onMovieHorClicked(item: MovieEntity) {
        viewModel.setEvent(HomeEvent.MovieClicked(item))
    }

    override fun onGenreClicked(item: GenreEntity) {
        viewModel.setEvent(HomeEvent.GenreClicked(item))
    }

}