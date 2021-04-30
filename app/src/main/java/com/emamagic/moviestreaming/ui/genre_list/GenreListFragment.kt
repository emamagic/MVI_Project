package com.emamagic.moviestreaming.ui.genre_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentGenreBinding
import com.emamagic.moviestreaming.db.entity.GenreEntity
import com.emamagic.moviestreaming.ui.genre_list.adapter.GenreListCompleteAdapter
import com.emamagic.moviestreaming.ui.genre_list.contract.CurrentGenreState
import com.emamagic.moviestreaming.ui.genre_list.contract.GenreListEffect
import com.emamagic.moviestreaming.ui.genre_list.contract.GenreListEvent
import com.emamagic.moviestreaming.ui.genre_list.contract.GenreListState
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreListFragment: BaseFragment<FragmentGenreBinding ,GenreListState ,GenreListEffect ,GenreListEvent ,GenreListViewModel>() {

    override val viewModel: GenreListViewModel by viewModels()
    private lateinit var genreListAdapter: GenreListCompleteAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGenreBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        genreListAdapter = GenreListCompleteAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setEvent(GenreListEvent.GetAllGenreList)
        binding?.imgBack?.setOnClickListener { findNavController().popBackStack() }
    }

    override fun renderViewState(viewState: GenreListState) {
        when(viewState.currentState) {
            CurrentGenreState.NON_STATE -> { /* Do Nothing */ }
            CurrentGenreState.RECEIVE_GENRES -> setUpGenreRecycler(viewState.genreList)
        }
    }

    override fun renderViewEffect(viewEffect: GenreListEffect) {
        when(viewEffect) {
            is GenreListEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
            is GenreListEffect.Navigate -> {}
            is GenreListEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
        }.exhaustive
    }


    private fun setUpGenreRecycler(genreList: List<GenreEntity>) {
        binding?.recyclerViewGenreComplete?.adapter = genreListAdapter
        binding?.recyclerViewGenreComplete?.setHasFixedSize(true)
        binding?.recyclerViewGenreComplete?.itemAnimator = null
        genreListAdapter.submitList(genreList)
    }

}