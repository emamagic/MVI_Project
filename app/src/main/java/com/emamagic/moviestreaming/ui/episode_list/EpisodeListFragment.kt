package com.emamagic.moviestreaming.ui.episode_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.emamagic.moviestreaming.base.BaseFragment
import com.emamagic.moviestreaming.databinding.FragmentEpisodeListBinding
import com.emamagic.moviestreaming.db.entity.EpisodeEntity
import com.emamagic.moviestreaming.ui.episode_list.adapter.EpisodeListAdapter
import com.emamagic.moviestreaming.ui.episode_list.contract.CurrentEpisodeListState
import com.emamagic.moviestreaming.ui.episode_list.contract.EpisodeListEffect
import com.emamagic.moviestreaming.ui.episode_list.contract.EpisodeListEvent
import com.emamagic.moviestreaming.ui.episode_list.contract.EpisodeListState
import com.emamagic.moviestreaming.util.exhaustive
import com.emamagic.moviestreaming.util.toasty
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class EpisodeListFragment: BaseFragment<FragmentEpisodeListBinding ,EpisodeListState ,EpisodeListEffect ,EpisodeListEvent ,EpisodeListViewModel>(),
    EpisodeListAdapter.Interaction{

    override val viewModel: EpisodeListViewModel by viewModels()
    private val args: EpisodeListFragmentArgs by navArgs()
    private lateinit var episodeAdapter: EpisodeListAdapter

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEpisodeListBinding.inflate(inflater ,container ,false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        episodeAdapter = EpisodeListAdapter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.txtSeasons.text = "Season : ${args.seasonId}"
        binding.btnBack.setOnClickListener { findNavController().popBackStack() }
        viewModel.setEvent(EpisodeListEvent.GetEpisodes(args.seasonId))
    }

    override fun renderViewState(viewState: EpisodeListState) {
        when(viewState.currentState) {
            CurrentEpisodeListState.NON_STATE -> { /* Do Nothing */ }
            CurrentEpisodeListState.EPISODES_RECEIVED -> setUpEpisodesRecycler(viewState.episodes)
        }
    }

    override fun renderViewEffect(viewEffect: EpisodeListEffect) {
        when(viewEffect) {
            is EpisodeListEffect.Navigate -> findNavController().navigate(viewEffect.navDirections)
            is EpisodeListEffect.ShowToast -> toasty(viewEffect.message ,viewEffect.mode)
            is EpisodeListEffect.Loading -> if (viewEffect.isLoading) showLoading(true) else hideLoading()
        }.exhaustive
    }

    private fun setUpEpisodesRecycler(list: List<EpisodeEntity>) {
        binding.recyclerViewEpisodes.adapter = episodeAdapter
        binding.recyclerViewEpisodes.setHasFixedSize(true)
        binding.recyclerViewEpisodes.itemAnimator = null
        episodeAdapter.submitList(list)
    }

    override fun onEpisodeClicked(item: EpisodeEntity) {
        viewModel.setEvent(EpisodeListEvent.PlayEpisodeClicked(item.videoLink!!))
    }

}