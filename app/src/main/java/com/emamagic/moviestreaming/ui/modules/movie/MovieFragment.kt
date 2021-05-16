package com.emamagic.moviestreaming.ui.modules.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.base.BaseFragment
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.databinding.FragmentShowDetailMovieBinding
import com.emamagic.moviestreaming.ui.modules.movie.adaper.CastAdapter
import com.emamagic.moviestreaming.ui.modules.movie.adaper.SeasonAdapter
import com.emamagic.moviestreaming.ui.modules.movie.contract.MovieEvent
import com.emamagic.moviestreaming.ui.modules.movie.contract.MovieState
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment: BaseFragment<FragmentShowDetailMovieBinding, MovieState, CommonEffect, MovieEvent, MovieViewModel>() ,
    SeasonAdapter.Interaction{

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
        seasonAdapter = SeasonAdapter(this)
        viewModel.setEvent(MovieEvent.GetDetailMovie(args.movie.id!!))
        if (args.movie.categoryName == com.emamagic.moviestreaming.ui.modules.home.contract.CategoryType.SERIES)
            viewModel.setEvent(MovieEvent.GetSeasons(args.movie.id!!))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnPlay.setOnClickListener { args.movie.videoLink?.let { viewModel.setEvent(MovieEvent.PlayVideoClicked(it)) } }
        binding.imgBack.setOnClickListener { findNavController().popBackStack() }
        setUpViews(viewModel.currentState.movie)
        setUpCastRecycler(viewModel.currentState.casts)
        setUpSeasonRecycler(viewModel.currentState.seasons)

    }

    override fun renderViewState(viewState: MovieState) {
        when(viewState.currentState) {
            com.emamagic.moviestreaming.ui.modules.movie.contract.CurrentMovieState.NON_STATE -> { /* Do Nothing */ }
            com.emamagic.moviestreaming.ui.modules.movie.contract.CurrentMovieState.MOVIE_RECEIVED -> setUpViews(viewState.movie)
            com.emamagic.moviestreaming.ui.modules.movie.contract.CurrentMovieState.CASTS_RECEIVED -> setUpCastRecycler(viewState.casts)
            com.emamagic.moviestreaming.ui.modules.movie.contract.CurrentMovieState.SEASONS_RECEIVED -> setUpSeasonRecycler(viewState.seasons)
        }
    }


    private fun setUpViews(movie: MovieEntity) {
        binding.apply {
            nameMovie.text = movie.name
            nameDirector.text = "Director : ${movie.director}"
            if (movie.categoryName == com.emamagic.moviestreaming.ui.modules.home.contract.CategoryType.SERIES) {
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


    private fun setUpCastRecycler(list: List<com.emamagic.moviestreaming.data.db.entity.CastEntity>) {
        binding.recyclerViewCast.adapter = castAdapter
        binding.recyclerViewCast.setHasFixedSize(true)
        binding.recyclerViewCast.itemAnimator = null
        castAdapter.submitList(list)
    }

    private fun setUpSeasonRecycler(list: List<com.emamagic.moviestreaming.data.db.entity.SeasonEntity>) {
        binding.recyclerViewSeason.adapter = seasonAdapter
        binding.recyclerViewSeason.setHasFixedSize(true)
        binding.recyclerViewSeason.itemAnimator = null
        seasonAdapter.submitList(list)
    }

    // TODO: 5/2/2021  You can write Similar Movie
    private fun setUpSimilarRecycler() {
        binding.recyclerViewSimilar.setHasFixedSize(true)
        binding.recyclerViewSimilar.itemAnimator = null
    }

    override fun onSeasonClicked(item: com.emamagic.moviestreaming.data.db.entity.SeasonEntity) {
        viewModel.setEvent(MovieEvent.SeasonClicked(item.id!!))
    }


}