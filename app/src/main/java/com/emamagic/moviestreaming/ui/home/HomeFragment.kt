package com.emamagic.moviestreaming.ui.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.lifecycle.findViewTreeViewModelStoreOwner
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
import com.emamagic.moviestreaming.util.onDrawerListener
import com.emamagic.moviestreaming.util.toasty
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
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
    private var shimmerCountGenre: Int = 0
    private var shimmerCountMovieTop: Int = 0
    private var shimmerCountMovieNew: Int = 0
    private var shimmerCountMoviePopular: Int = 0
    private var shimmerCountMovieSeries: Int = 0
    private var shimmerCountMovieAnim: Int = 0

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

        setUpGenreRecycler(viewModel.currentState.genres)
        setUpSlider(requireContext() ,viewModel.currentState.sliders)
        viewModel.currentState.movies.apply {
            setUpTopMovieRecycler(top?.data)
            setUpNewMovieRecycler(new?.data)
            setUpSeriesRecycler(series?.data)
            setUpPopularRecycler(popular?.data)
            setUpAnimationRecycler(animation?.data)
        }
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
            CurrentHomeState.GENRE_RECEIVE -> genreAdapter?.submitList(viewState.genres)
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
        item.top?.data?.let { movieIMDBAdapter?.submitList(it) }
        item.new?.data?.let { movieNewAdapter?.submitList(it) }
        item.series?.data?.let { movieSeriesAdapter?.submitList(it) }
        item.popular?.data?.let { animationAdapter?.submitList(it)  }
        item.animation?.data?.let { animationAdapter?.submitList(it) }
    }

    private fun setUpGenreRecycler(list: List<GenreEntity>) {
        binding.recyclerViewGenre.adapter = genreAdapter
        binding.recyclerViewGenre.setHasFixedSize(true)
        binding.recyclerViewGenre.itemAnimator = null
        if (list.isNullOrEmpty())
        genreAdapter?.submitList(list)
    }

    private fun setUpNewMovieRecycler(list: List<MovieEntity>?) {
        binding.recyclerViewNewMovie.adapter = movieNewAdapter
        binding.recyclerViewNewMovie.setHasFixedSize(true)
        binding.recyclerViewNewMovie.itemAnimator = null
        if (!list.isNullOrEmpty())
        movieNewAdapter?.submitList(list)
    }

    private fun setUpTopMovieRecycler(list: List<MovieEntity>?) {
        binding.recyclerViewTopMovieImdb.adapter = movieIMDBAdapter
        binding.recyclerViewTopMovieImdb.setHasFixedSize(true)
        binding.recyclerViewTopMovieImdb.itemAnimator = null
        if (!list.isNullOrEmpty())
        movieIMDBAdapter?.submitList(list)
    }

    private fun setUpSeriesRecycler(list: List<MovieEntity>?) {
        binding.recyclerViewSeries.adapter = movieSeriesAdapter
        binding.recyclerViewSeries.setHasFixedSize(true)
        binding.recyclerViewSeries.itemAnimator = null
        if (!list.isNullOrEmpty())
        movieSeriesAdapter?.submitList(list)
    }

    private fun setUpAnimationRecycler(list: List<MovieEntity>?) {
        binding.recyclerViewAnimation.adapter = animationAdapter
        binding.recyclerViewAnimation.setHasFixedSize(true)
        binding.recyclerViewAnimation.itemAnimator = null
        if (!list.isNullOrEmpty())
        animationAdapter?.submitList(list)
    }

    private fun setUpPopularRecycler(list: List<MovieEntity>?) {
        binding.recyclerViewPopularMovie.adapter = moviePopularAdapter
        binding.recyclerViewPopularMovie.setHasFixedSize(true)
        binding.recyclerViewPopularMovie.itemAnimator = null
        if (!list.isNullOrEmpty())
        moviePopularAdapter?.submitList(list)
    }

    private fun setUpDrawer() {
        binding.navigationView.bringToFront()
        binding.navigationView.setNavigationItemSelectedListener(this)
        binding.navigationView.setCheckedItem(R.id.nav_home)
        binding.drawerLayout.setScrimColor(resources.getColor(R.color.colorPrimary))
        binding.drawerLayout.onDrawerListener { slideOffset, drawerView -> slidFragment(binding.parent ,drawerView ,slideOffset) }
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
        when(item.itemId) {
            R.id.btn_menu -> binding.drawerLayout.openDrawer(GravityCompat.START)
            R.id.nav_genre -> viewModel.setEvent(HomeEvent.MoreMovieClicked(CategoryType.GENRE))
            R.id.nav_buy_account -> {}
            R.id.nav_favorite -> viewModel.setEvent(HomeEvent.FavoriteClicked)
            R.id.nav_profile -> {}
            R.id.nav_home -> binding.drawerLayout.closeDrawer(GravityCompat.START)
            R.id.nav_search -> viewModel.setEvent(HomeEvent.SearchClicked)
        }

        return true
    }

    override fun onClick(v: View?) {
        when(v?.id) {
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

    override fun onShimmerStopMovieVer(item: MovieEntity) {
        when(item.categoryName){
            CategoryType.TOP -> {
                shimmerCountMovieTop++
                if (shimmerCountMovieTop >= 4){
                    binding.shimmerFrameLayoutTop.stopShimmer()
                    binding.shimmerFrameLayoutTop.visibility = View.GONE
                    binding.recyclerViewTopMovieImdb.visibility = View.VISIBLE
                }
            }
            CategoryType.NEW -> {
                shimmerCountMovieNew++
                if (shimmerCountMovieNew >= 4){
                    binding.shimmerFrameLayoutNew.stopShimmer()
                    binding.shimmerFrameLayoutNew.visibility = View.GONE
                    binding.recyclerViewNewMovie.visibility = View.VISIBLE
                }
            }
            CategoryType.SERIES -> {
                shimmerCountMovieSeries++
                if (shimmerCountMovieSeries >= 4){
                    binding.shimmerFrameLayoutSeries.stopShimmer()
                    binding.shimmerFrameLayoutSeries.visibility = View.GONE
                    binding.recyclerViewSeries.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onMovieHorClicked(item: MovieEntity) {
        viewModel.setEvent(HomeEvent.MovieClicked(item))
    }

    override fun onShimmerStopMovieHor(item: MovieEntity) {
        when(item.categoryName){
            CategoryType.POPULAR -> {
                shimmerCountMoviePopular++
                if (shimmerCountMoviePopular >= 2){
                    binding.shimmerFrameLayoutPopular.stopShimmer()
                    binding.shimmerFrameLayoutPopular.visibility = View.GONE
                    binding.recyclerViewPopularMovie.visibility = View.VISIBLE
                }
            }
            CategoryType.ANIMATION -> {
                shimmerCountMovieAnim++
                if (shimmerCountMovieAnim >= 2){
                    binding.shimmerFrameLayoutAnim.stopShimmer()
                    binding.shimmerFrameLayoutAnim.visibility = View.GONE
                    binding.recyclerViewAnimation.visibility = View.VISIBLE
                }
            }

        }
    }

    override fun onGenreClicked(item: GenreEntity) {
        viewModel.setEvent(HomeEvent.GenreClicked(item.name))
    }

    override fun onShimmerStopGenre() {
        shimmerCountGenre++
        if (shimmerCountGenre >= 4) {
            binding.shimmerFrameLayoutGenre.stopShimmer()
            binding.shimmerFrameLayoutGenre.visibility = View.GONE
            binding.recyclerViewGenre.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        binding.shimmerFrameLayoutGenre.startShimmer()
        binding.shimmerFrameLayoutTop.startShimmer()
        binding.shimmerFrameLayoutNew.startShimmer()
        binding.shimmerFrameLayoutPopular.startShimmer()
        binding.shimmerFrameLayoutAnim.startShimmer()
    }

    override fun onPause() {
        binding.shimmerFrameLayoutGenre.stopShimmer()
        binding.shimmerFrameLayoutTop.stopShimmer()
        binding.shimmerFrameLayoutNew.stopShimmer()
        binding.shimmerFrameLayoutPopular.stopShimmer()
        binding.shimmerFrameLayoutAnim.stopShimmer()
        super.onPause()
    }

}