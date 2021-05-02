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
class MovieListFragment : BaseFragment<FragmentMovieBinding ,MovieListState ,MovieListEffect ,MovieListEvent ,MovieListViewModel>() ,MovieListCompleteAdapter.MovieInteraction{


    override val viewModel: MovieListViewModel by viewModels()
    private val args: MovieListFragmentArgs by navArgs()
    private lateinit var movieListAdapter: MovieListCompleteAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentMovieBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        movieListAdapter = MovieListCompleteAdapter(this)
        viewModel.setEvent(MovieListEvent.GetAllMovieList(args.category))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtToolbar.text = args.category
        binding.imgBack.setOnClickListener { findNavController().popBackStack() }

    }

    override fun renderViewState(viewState: MovieListState) {
        when(viewState.currentMovieState){
            CurrentMovieListState.NON_STATE -> { /* Do Nothing */ }
            CurrentMovieListState.RECEIVE_MOVIES -> setUpMovieRecycler(viewState.movieList)
        }
    }

    override fun renderViewEffect(viewEffect: MovieListEffect) {
        when(viewEffect){
            is MovieListEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            is MovieListEffect.Navigate -> findNavController().navigate(viewEffect.navDirect)
            is MovieListEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
        }.exhaustive
    }


    private fun setUpMovieRecycler(movieList: List<MovieEntity>) {
        binding.recyclerViewTopMovieComplete.adapter = movieListAdapter
        binding.recyclerViewTopMovieComplete.setHasFixedSize(true)
        binding.recyclerViewTopMovieComplete.itemAnimator = null
        movieListAdapter.submitList(movieList)
    }

    override fun onMovieClicked(item: MovieEntity) {
        viewModel.setEvent(MovieListEvent.MovieClicked(item))
    }


}