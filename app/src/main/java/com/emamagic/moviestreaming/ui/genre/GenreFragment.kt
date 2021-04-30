package com.emamagic.moviestreaming.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentGenreBinding
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.ui.genre.adapter.GenreCompleteAdapter
import com.emamagic.moviestreaming.ui.genre.contract.CurrentGenreState
import com.emamagic.moviestreaming.ui.genre.contract.GenreEffect
import com.emamagic.moviestreaming.ui.genre.contract.GenreEvent
import com.emamagic.moviestreaming.ui.genre.contract.GenreState
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment: BaseFragment<FragmentGenreBinding ,GenreState ,GenreEffect ,GenreEvent ,GenreViewModel>() {

    override val viewModel: GenreViewModel by viewModels()
    private lateinit var genreAdapter: GenreCompleteAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGenreBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreAdapter = GenreCompleteAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setEvent(GenreEvent.GetAllGenre)
        binding?.imgBack?.setOnClickListener { findNavController().popBackStack() }
    }

    override fun renderViewState(viewState: GenreState) {
        when(viewState.currentState) {
            CurrentGenreState.NON_STATE -> { /* Do Nothing */ }
            CurrentGenreState.RECEIVE_GENRES -> setUpGenreRecycler(viewState.genreList)
        }
    }

    override fun renderViewEffect(viewEffect: GenreEffect) {
        when(viewEffect) {
            is GenreEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            is GenreEffect.Navigate -> {}
            is GenreEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
        }.exhaustive
    }


    private fun setUpGenreRecycler(genreList: List<GenreEntity>) {
        binding?.recyclerViewGenreComplete?.adapter = genreAdapter
        genreAdapter.submitList(genreList)
    }

}