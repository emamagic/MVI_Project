package com.emamagic.moviestreaming.ui.genre_type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentGenreTypeBinding
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.ui.genre_type.adapter.GenreTypeCompleteAdapter
import com.emamagic.moviestreaming.ui.genre_type.contract.CurrentGenreTypeState
import com.emamagic.moviestreaming.ui.genre_type.contract.GenreTypeEffect
import com.emamagic.moviestreaming.ui.genre_type.contract.GenreTypeEvent
import com.emamagic.moviestreaming.ui.genre_type.contract.GenreTypeState
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class GenreTypeFragment: BaseFragment<FragmentGenreTypeBinding ,GenreTypeState ,GenreTypeEffect ,GenreTypeEvent ,GenreTypeViewModel>() ,
        GenreTypeCompleteAdapter.Interaction{

    override val viewModel: GenreTypeViewModel by viewModels()
    private lateinit var genreTypeAdapter: GenreTypeCompleteAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGenreTypeBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreTypeAdapter = GenreTypeCompleteAdapter(this)
        viewModel.setEvent(GenreTypeEvent.GetAllGenreType)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBack.setOnClickListener { findNavController().popBackStack() }
    }

    override fun renderViewState(viewState: GenreTypeState) {
        when(viewState.currentState) {
            CurrentGenreTypeState.NON_STATE -> { /* Do Nothing */ }
            CurrentGenreTypeState.RECEIVE_GENRES -> setUpGenreRecycler(viewState.genreList)
        }
    }

    override fun renderViewEffect(viewEffect: GenreTypeEffect) {
        when(viewEffect) {
            is GenreTypeEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            is GenreTypeEffect.Navigate -> findNavController().navigate(viewEffect.navDirections)
            is GenreTypeEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
        }.exhaustive
    }


    private fun setUpGenreRecycler(genreList: List<GenreEntity>) {
        binding.recyclerViewGenreComplete.adapter = genreTypeAdapter
        binding.recyclerViewGenreComplete.setHasFixedSize(true)
        binding.recyclerViewGenreComplete.itemAnimator = null
        genreTypeAdapter.submitList(genreList)
    }

    override fun onGenreClicked(item: GenreEntity) {
        viewModel.setEvent(GenreTypeEvent.GenreTypeClicked(item.name))
    }

}