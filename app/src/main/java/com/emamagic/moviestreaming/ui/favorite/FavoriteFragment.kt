package com.emamagic.moviestreaming.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.base.CommonEffect
import com.emamagic.moviestreaming.databinding.FragmentFavoriteBinding
import com.emamagic.moviestreaming.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.favorite.adapter.FavoriteAdapter
import com.emamagic.moviestreaming.ui.favorite.contract.FavoriteEvent
import com.emamagic.moviestreaming.ui.favorite.contract.FavoriteState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment: BaseFragment<FragmentFavoriteBinding ,FavoriteState ,CommonEffect ,FavoriteEvent ,FavoriteViewModel>(),
        FavoriteAdapter.Interaction{

    override val viewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFavoriteBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoriteAdapter = FavoriteAdapter(this)
        viewModel.setEvent(FavoriteEvent.GetFavoriteMovies)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
    }

    override fun renderViewState(viewState: FavoriteState) {
        if (viewState.movies.isNotEmpty())
        setUpFavoriteRecycler(viewState.movies)
    }


    private fun setUpFavoriteRecycler(list: List<MovieEntity>) {
        binding.recyclerViewFavorite.adapter = favoriteAdapter
        binding.recyclerViewFavorite.setHasFixedSize(true)
        binding.recyclerViewFavorite.itemAnimator = null
        favoriteAdapter.submitList(list)
    }


    override fun onFavoriteItemClicked(item: MovieEntity) {}
}