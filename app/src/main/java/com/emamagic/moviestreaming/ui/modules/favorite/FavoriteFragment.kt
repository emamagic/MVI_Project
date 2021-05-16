package com.emamagic.moviestreaming.ui.modules.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.data.db.entity.MovieEntity
import com.emamagic.moviestreaming.ui.base.BaseFragment
import com.emamagic.moviestreaming.ui.base.CommonEffect
import com.emamagic.moviestreaming.databinding.FragmentFavoriteBinding
import com.emamagic.moviestreaming.databinding.ItemFavoriteBinding
import com.emamagic.moviestreaming.ui.base.BaseListAdapter
import com.emamagic.moviestreaming.ui.modules.favorite.adapter.FavoriteAdapter
import com.emamagic.moviestreaming.ui.modules.favorite.contract.FavoriteEvent
import com.emamagic.moviestreaming.ui.modules.favorite.contract.FavoriteState
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment :
    BaseFragment<FragmentFavoriteBinding, FavoriteState, CommonEffect, FavoriteEvent, FavoriteViewModel>(),
    FavoriteAdapter.Interaction {

    override val viewModel: FavoriteViewModel by viewModels()
    private lateinit var favoriteAdapter: FavoriteAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentFavoriteBinding.inflate(inflater, container, false)

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
//        binding.recyclerViewFavorite.apply {
//            adapter =
//                object : BaseListAdapter<MovieEntity, ItemFavoriteBinding>(
//                    bind = { item, holder, itemCount ->
//                        with(holder.itemView) {
//                            Picasso.get().load(item.imageLink).resize(400, 400).into(imgMovie)
//                            nameMovie.text = item.name
//                            nameDirector.text = item.director
//                            rateImdb.text = "IMDb:${item.imdbRate}"
//                            if (item.categoryName == com.emamagic.moviestreaming.ui.modules.home.contract.CategoryType.SERIES) {
//                                published.text = "Episodes :${item.episode}"
//                                imgHour.setImageResource(R.drawable.ic_baseline_folder_special_24)
//                            } else {
//                                published.text = "Published :${item.published}"
//                                imgHour.setImageResource(R.drawable.ic_baseline_access_time_24)
//                            }
//                        }
//                    }
//                ) {
//                    override fun inflateItemBinding(parent: ViewGroup, viewType: Int) =
//                        ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//                }.apply {
//                    setHasFixedSize(true)
//                    itemAnimator = null
//                    submitList(list)
//                }
//
//        }

    }


    override fun onFavoriteItemClicked(item: MovieEntity) {}
}