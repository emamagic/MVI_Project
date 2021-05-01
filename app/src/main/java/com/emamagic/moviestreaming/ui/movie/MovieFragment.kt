package com.emamagic.moviestreaming.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentShowDetailMovieBinding
import com.emamagic.moviestreaming.ui.movie.contract.CurrentMovieState
import com.emamagic.moviestreaming.ui.movie.contract.MovieEffect
import com.emamagic.moviestreaming.ui.movie.contract.MovieEvent
import com.emamagic.moviestreaming.ui.movie.contract.MovieState
import com.emamagic.moviestreaming.util.exhaustive
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment: BaseFragment<FragmentShowDetailMovieBinding ,MovieState ,MovieEffect ,MovieEvent ,MovieViewModel>() {

    override val viewModel: MovieViewModel by viewModels()

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentShowDetailMovieBinding.inflate(inflater ,container ,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun renderViewState(viewState: MovieState) {
        when(viewState.currentState) {
            CurrentMovieState.NON_STATE -> { /* Do Nothing */ }
            CurrentMovieState.MOVIE_RECEIVED -> {}
        }
    }

    override fun renderViewEffect(viewEffect: MovieEffect) {
        when(viewEffect) {
            is MovieEffect.ShowToast -> TODO()
        }.exhaustive
    }
}