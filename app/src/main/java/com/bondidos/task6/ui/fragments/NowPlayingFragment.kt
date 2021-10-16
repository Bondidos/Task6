package com.bondidos.task6.ui.fragments

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bondidos.task6.R
import com.bondidos.task6.databinding.NowPlayingFragmentBinding
import com.bondidos.task6.databinding.TrackListFragmentBinding
import com.bondidos.task6.models.Factories.NowPlayingViewModelFactory
import com.bondidos.task6.models.NowPlayingViewModel
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.now_playing_fragment.*

const val NOW_PLAYING = "nowPlaying"

class NowPlayingFragment : Fragment() {

    private val nowPlayingViewModel by viewModels<NowPlayingViewModel> {
        NowPlayingViewModelFactory(requireContext())
    }

    private var _binding: NowPlayingFragmentBinding? = null
    private val binding: NowPlayingFragmentBinding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = NowPlayingFragmentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initListeners()
        initObservers()
    }

    private fun initListeners() {
        with(binding){
            btnPlay.setOnClickListener { nowPlayingViewModel.playTrack() }
            btnStop.setOnClickListener { nowPlayingViewModel.stopPlaying() }//todo(stand to pause instead stop)
            btnPrev.setOnClickListener { nowPlayingViewModel.previousTrack() }
            btnNext.setOnClickListener { nowPlayingViewModel.nextTrack() }
        }
    }

    private fun initObservers() {
        nowPlayingViewModel.playingState.observe(viewLifecycleOwner){
            with(binding){
                title.text = it.title
                subtitle.text = it.artist
                albumArt.setImageBitmap(it.cover)
            }
        }
    }

}