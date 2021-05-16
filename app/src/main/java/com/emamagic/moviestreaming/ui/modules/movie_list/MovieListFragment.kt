package com.emamagic.moviestreaming.ui.modules.movie_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emamagic.moviestreaming.ui.base.BaseFragment
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.databinding.FragmentMovieBinding
import com.emamagic.moviestreaming.ui.modules.movie_list.adapter.MovieListCompleteAdapter
import com.emamagic.moviestreaming.ui.modules.movie_list.contract.MovieListEvent
import com.emamagic.moviestreaming.ui.modules.movie_list.contract.MovieListState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MovieListFragment : BaseFragment<FragmentMovieBinding, MovieListState, CommonEffect, MovieListEvent, MovieListViewModel>() ,
    MovieListCompleteAdapter.MovieInteraction{


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

        setUpMovieRecycler()
        binding.txtToolbar.text = args.category
        binding.imgBack.setOnClickListener { findNavController().popBackStack() }

    }

    override fun renderViewState(viewState: MovieListState) {
        when(viewState.currentMovieState){
            com.emamagic.moviestreaming.ui.modules.movie_list.contract.CurrentMovieListState.NON_STATE -> { /* Do Nothing */ }
            com.emamagic.moviestreaming.ui.modules.movie_list.contract.CurrentMovieListState.RECEIVE_MOVIES -> {
                Timber.e("${viewState.movieList}")
                movieListAdapter.submitList(viewState.movieList)
            }
        }
    }


    private fun setUpMovieRecycler() {
        binding.recyclerViewTopMovieComplete.adapter = movieListAdapter
        binding.recyclerViewTopMovieComplete.setHasFixedSize(true)
        binding.recyclerViewTopMovieComplete.itemAnimator = null
    }

    override fun onMovieClicked(item: com.emamagic.moviestreaming.data.db.entity.MovieWithFavorite) {
        viewModel.setEvent(MovieListEvent.MovieClicked(item.movie))
    }

    override fun favoriteClicked(id: Long) {
        viewModel.setEvent(MovieListEvent.FavoriteClicked(
            com.emamagic.moviestreaming.data.db.entity.FavoriteEntity(
                movieId = id
            )
        ))
    }


}