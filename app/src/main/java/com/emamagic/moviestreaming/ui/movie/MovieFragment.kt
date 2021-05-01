package com.emamagic.moviestreaming.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentShowDetailMovieBinding
import com.emamagic.moviestreaming.db.entity.CastEntity
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.movie.adaper.CastAdapter
import com.emamagic.moviestreaming.ui.movie.contract.CurrentMovieState
import com.emamagic.moviestreaming.ui.movie.contract.MovieEffect
import com.emamagic.moviestreaming.ui.movie.contract.MovieEvent
import com.emamagic.moviestreaming.ui.movie.contract.MovieState
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment: BaseFragment<FragmentShowDetailMovieBinding ,MovieState ,MovieEffect ,MovieEvent ,MovieViewModel>() {

    override val viewModel: MovieViewModel by viewModels()
    private val args: MovieFragmentArgs by navArgs()
    private lateinit var castAdapter: CastAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentShowDetailMovieBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        castAdapter = CastAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.setEvent(MovieEvent.GetDetailMovie(args.movie.id!!))
    }

    override fun renderViewState(viewState: MovieState) {
        when(viewState.currentState) {
            CurrentMovieState.NON_STATE -> { /* Do Nothing */ }
            CurrentMovieState.MOVIE_RECEIVED -> setUpViews(viewState.movie)
            CurrentMovieState.CASTS_RECEIVED -> setUpCastRecycler(viewState.casts)
        }
    }

    override fun renderViewEffect(viewEffect: MovieEffect) {
        when(viewEffect) {
            is MovieEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
            is MovieEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
        }.exhaustive
    }

    private fun setUpViews(movie: MovieEntity) {
        binding?.apply {
            nameMovie.text = movie.name
            nameDirector.text = "Director :${movie.director}"
            published.text = "Published :${movie.published}"
            description.text = "Description :${movie.description}"
            time.text = movie.time
            rateImdb.text = "IMDb:${movie.imdbRate}"
            Picasso.get().load(movie.imageVideoLink).resize(500 ,500).into(imgMovie)
        }
    }


    private fun setUpCastRecycler(list: List<CastEntity>) {
        binding?.recyclerViewCast?.adapter = castAdapter
        binding?.recyclerViewCast?.setHasFixedSize(true)
        binding?.recyclerViewCast?.itemAnimator = null
        castAdapter.submitList(list)
    }


}