package com.emamagic.moviestreaming.ui.modules.genre_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emamagic.moviestreaming.data.db.entity.FavoriteEntity
import com.emamagic.moviestreaming.data.db.entity.MovieWithFavorite
import com.emamagic.moviestreaming.ui.base.BaseFragment
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.databinding.FragmentGenreListBinding
import com.emamagic.moviestreaming.ui.modules.genre_list.adapter.GenreListAdapter
import com.emamagic.moviestreaming.ui.modules.genre_list.contract.GenreListEvent
import com.emamagic.moviestreaming.ui.modules.genre_list.contract.GenreListState
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class GenreListFragment: BaseFragment<FragmentGenreListBinding, GenreListState, CommonEffect, GenreListEvent, GenreListViewModel>() ,
    GenreListAdapter.Interaction{


    override val viewModel: GenreListViewModel by viewModels()
    private val args: GenreListFragmentArgs by navArgs()
    private lateinit var genreListAdapter: GenreListAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGenreListBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreListAdapter = GenreListAdapter(this)
        viewModel.setEvent(GenreListEvent.GetGenreListByCategory(args.genreName))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }

    override fun renderViewState(viewState: GenreListState) {
        viewState.genres?.let { setUpGenreListRecycler(it) }
    }


    private fun setUpGenreListRecycler(list: List<MovieWithFavorite>) {
        Timber.e("${list.size}")
        binding.recyclerViewShowGenre.adapter = genreListAdapter
        binding.recyclerViewShowGenre.setHasFixedSize(true)
        binding.recyclerViewShowGenre.itemAnimator = null
        genreListAdapter.submitList(list)
    }

    override fun onMovieClicked(item: MovieWithFavorite) {
        viewModel.setEvent(GenreListEvent.GenreListClicked(item.movie))
    }

    override fun favoriteClicked(id: Long) {
        viewModel.setEvent(GenreListEvent.FavoriteClicked(
            FavoriteEntity(
                movieId = id
            )
        ))
    }


}