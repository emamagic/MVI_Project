package com.emamagic.moviestreaming.ui.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentMovieBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.movie_list.adapter.MovieListCompleteAdapter
import com.emamagic.moviestreaming.ui.movie_list.contract.*
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieBinding ,MovieListState ,MovieListEffect ,MovieListEvent ,MovieListViewModel>() {


    override val viewModel: MovieListViewModel by viewModels()
    private val args: MovieListFragmentArgs by navArgs()
    private lateinit var movieListAdapter: MovieListCompleteAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMovieBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieListAdapter = MovieListCompleteAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.txtToolbar?.text = args.category
        binding?.imgBack?.setOnClickListener { findNavController().popBackStack() }
        viewModel.setEvent(MovieListEvent.GetAllMovieList(args.category))

    }

    override fun renderViewState(viewListState: MovieListState) {
        when(viewListState.currentMovieState){
            CurrentMovieState.NON_STATE -> { /* Do Nothing */ }
            CurrentMovieState.RECEIVE_MOVIES -> setUpMovieRecycler(viewListState.movieList)
        }
    }

    override fun renderViewEffect(viewListEffect: MovieListEffect) {
        when(viewListEffect){
            is MovieListEffect.Loading -> if (viewListEffect.isLoading) showLoading(true) else hideLoading()
            is MovieListEffect.Navigate -> {}
            is MovieListEffect.ShowToast -> toasty(viewListEffect.message ,viewListEffect.mode)
        }.exhaustive
    }


    private fun setUpMovieRecycler(movieList: List<MovieEntity>) {
        binding?.recyclerViewTopMovieComplete?.adapter = movieListAdapter
        movieListAdapter.submitList(movieList)
    }



}