package com.emamagic.moviestreaming.ui.search_type

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.emamagic.moviestreaming.R
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.base.CommonEffect
import com.emamagic.moviestreaming.databinding.FragmentSearchTypeBinding
import com.emamagic.moviestreaming.ui.search_type.contract.SearchType
import com.emamagic.moviestreaming.ui.search_type.contract.SearchTypeEvent
import com.emamagic.moviestreaming.ui.search_type.contract.SearchTypeState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchTypeFragment: BaseFragment<FragmentSearchTypeBinding ,SearchTypeState , CommonEffect,SearchTypeEvent ,SearchTypeViewModel>() ,View.OnClickListener {

    override val viewModel: SearchTypeViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentSearchTypeBinding.inflate(inflater ,container ,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.imgBack.setOnClickListener { findNavController().popBackStack() }
        binding.cardAnimation.setOnClickListener(this)
        binding.cardMovieNew.setOnClickListener(this)
        binding.cardSeries.setOnClickListener(this)
        binding.cardTopMovie.setOnClickListener(this)
    }

    override fun renderViewState(viewState: SearchTypeState) {}


    override fun onClick(v: View) {
        when(v.id){
            R.id.card_top_movie -> viewModel.setEvent(SearchTypeEvent.SearchTypeClicked(SearchType.TOP))
            R.id.card_animation -> viewModel.setEvent(SearchTypeEvent.SearchTypeClicked(SearchType.ANIMATION))
            R.id.card_series -> viewModel.setEvent(SearchTypeEvent.SearchTypeClicked(SearchType.SERIES))
            R.id.card_movie_new -> viewModel.setEvent(SearchTypeEvent.SearchTypeClicked(SearchType.NEW))
        }
    }
}