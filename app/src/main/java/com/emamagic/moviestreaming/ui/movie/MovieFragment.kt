package com.emamagic.moviestreaming.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentMovieBinding
import com.emamagic.moviestreaming.databinding.ItemTopMovieImdbCompleteBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.movie.adapter.MovieCompleteAdapter
import com.emamagic.moviestreaming.ui.movie.contract.*
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment: BaseFragment<FragmentMovieBinding ,MovieState ,MovieEffect ,MovieEvent ,MovieViewModel>() {


    override val viewModel: MovieViewModel by viewModels()
    private val args: MovieFragmentArgs by navArgs()
    private lateinit var movieAdapter: MovieCompleteAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMovieBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieAdapter = MovieCompleteAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.txtToolbar?.text = args.category
        binding?.imgBack?.setOnClickListener { findNavController().popBackStack() }
        viewModel.setEvent(MovieEvent.GetAllMovie(args.category))

    }

    override fun renderViewState(viewState: MovieState) {
        when(viewState.currentMovieState){
            CurrentMovieState.NON_STATE -> { /* Do Nothing */ }
            CurrentMovieState.RECEIVE_MOVIES -> setUpMovieRecycler(viewState.movieList)
        }
    }

    override fun renderViewEffect(viewEffect: MovieEffect) {
        when(viewEffect){
            is MovieEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            is MovieEffect.Navigate -> {}
            is MovieEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
        }.exhaustive
    }


    private fun setUpMovieRecycler(movieList: List<MovieEntity>) {
        binding?.recyclerViewTopMovieComplete?.adapter = movieAdapter
        movieAdapter.submitList(movieList)
    }


}