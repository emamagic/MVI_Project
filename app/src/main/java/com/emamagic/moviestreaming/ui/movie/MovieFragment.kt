package com.emamagic.moviestreaming.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentShowDetailMovieBinding
import com.emamagic.moviestreaming.db.entity.CastEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.db.entity.SeasonEntity
import com.emamagic.moviestreaming.ui.home.contract.CategoryType
import com.emamagic.moviestreaming.ui.movie.adaper.CastAdapter
import com.emamagic.moviestreaming.ui.movie.adaper.SeasonAdapter
import com.emamagic.moviestreaming.ui.movie.contract.CurrentMovieState
import com.emamagic.moviestreaming.ui.movie.contract.MovieEffect
import com.emamagic.moviestreaming.ui.movie.contract.MovieEvent
import com.emamagic.moviestreaming.ui.movie.contract.MovieState
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieFragment: BaseFragment<FragmentShowDetailMovieBinding ,MovieState ,MovieEffect ,MovieEvent ,MovieViewModel>() {

    override val viewModel: MovieViewModel by viewModels()
    private val args: MovieFragmentArgs by navArgs()
    private lateinit var castAdapter: CastAdapter
    private lateinit var seasonAdapter: SeasonAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentShowDetailMovieBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        castAdapter = CastAdapter()
        seasonAdapter = SeasonAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setEvent(MovieEvent.GetDetailMovie(args.movie.id!!))
        if (args.movie.categoryName == CategoryType.SERIES)
            viewModel.setEvent(MovieEvent.GetSeasons(args.movie.id!!))

        binding.btnPlay.setOnClickListener { viewModel.setEvent(MovieEvent.PlayVideoClicked(args.movie.videoLink!!)) }
        binding.imgBack.setOnClickListener { findNavController().popBackStack() }


    }

    override fun renderViewState(viewState: MovieState) {
        when(viewState.currentState) {
            CurrentMovieState.NON_STATE -> { /* Do Nothing */ }
            CurrentMovieState.MOVIE_RECEIVED -> setUpViews(viewState.movie)
            CurrentMovieState.CASTS_RECEIVED -> setUpCastRecycler(viewState.casts)
            CurrentMovieState.SEASONS_RECEIVED -> setUpSeasonRecycler(viewState.seasons)
        }
    }

    override fun renderViewEffect(viewEffect: MovieEffect) {
        when(viewEffect) {
            is MovieEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
            is MovieEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            is MovieEffect.Navigate -> findNavController().navigate(viewEffect.navDirections)
        }.exhaustive
    }

    private fun setUpViews(movie: MovieEntity) {
        binding.apply {
            nameMovie.text = movie.name
            nameDirector.text = "Director : ${movie.director}"
            if (movie.categoryName == CategoryType.SERIES) {
                published.text = "Episodes : ${movie.episode}"
                imgHour.setImageResource(R.drawable.ic_baseline_folder_special_24)
                seriesList.visibility = View.VISIBLE
            }else {
                imgHour.setImageResource(R.drawable.ic_baseline_access_time_24)
                published.text = "Published : ${movie.published}"
                seriesList.visibility = View.GONE
            }

            description.text = movie.description
            time.text = movie.time
            rateImdb.text = "IMDb:${movie.imdbRate}"
            Picasso.get().load(movie.imageVideoLink).resize(400 ,250).into(imgMovie)
        }
    }


    private fun setUpCastRecycler(list: List<CastEntity>) {
        binding.recyclerViewCast.adapter = castAdapter
        binding.recyclerViewCast.setHasFixedSize(true)
        binding.recyclerViewCast.itemAnimator = null
        castAdapter.submitList(list)
    }

    private fun setUpSeasonRecycler(list: List<SeasonEntity>) {
        binding.recyclerViewSeason.adapter = seasonAdapter
        binding.recyclerViewSeason.setHasFixedSize(true)
        binding.recyclerViewSeason.itemAnimator = null
        seasonAdapter.submitList(list)
    }


}