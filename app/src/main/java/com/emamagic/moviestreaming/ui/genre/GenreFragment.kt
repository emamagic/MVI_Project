package com.emamagic.moviestreaming.ui.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentGenreBinding
import com.emamagic.moviestreaming.ui.genre.contract.GenreEffect
import com.emamagic.moviestreaming.ui.genre.contract.GenreEvent
import com.emamagic.moviestreaming.ui.genre.contract.GenreState
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GenreFragment: BaseFragment<FragmentGenreBinding ,GenreState ,GenreEffect ,GenreEvent ,GenreViewModel>() {

    override val viewModel: GenreViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentGenreBinding.inflate(inflater ,container ,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun renderViewState(viewState: GenreState) {

    }

    override fun renderViewEffect(viewEffect: GenreEffect) {

    }


}