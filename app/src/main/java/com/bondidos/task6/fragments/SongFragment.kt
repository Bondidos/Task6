package com.bondidos.task6.fragments

import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bondidos.task6.R
import com.bondidos.task6.data.Song
import com.bondidos.task6.databinding.SongFragmentBinding
import com.bondidos.task6.service.isPlaying
import com.bondidos.task6.utils.Status
import com.bondidos.task6.utils.Status.SUCCESS
import com.bondidos.task6.utils.playClickAnimation
import com.bondidos.task6.utils.toSong
import com.bondidos.task6.viewModel.MainViewModel
import com.bondidos.task6.viewModel.SongViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SongFragment : Fragment() {

    @Inject
    lateinit var glide: RequestManager

    private var _binding: SongFragmentBinding? = null
    private val binding: SongFragmentBinding get() = requireNotNull(_binding)

    private lateinit var mainViewModel: MainViewModel
    private val songViewModel: SongViewModel by viewModels()

    private var currPlayingSong: Song? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SongFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        subscribeToObservers()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        with(binding) {
            playPauseDetail.setOnClickListener {
                it.playClickAnimation()
                mainViewModel.playOrToggleSong(
                    currPlayingSong ?: return@setOnClickListener,
                    true)
            }
            skipPrevious.setOnClickListener {
                it.playClickAnimation()
                mainViewModel.skipToPreviousSong()
            }
            skipNext.setOnClickListener {
                it.playClickAnimation()
                mainViewModel.skipToNextSong()
            }
        }
    }

    private fun subscribeToObservers() {
        mainViewModel.mediaItems.observe(viewLifecycleOwner) {
            it?.let { result ->
                when (result.status) {
                    SUCCESS -> {
                        result.data?.let { songs ->
                            if (currPlayingSong == null && songs.isNotEmpty()) {
                                currPlayingSong = songs[0]
                                updateTitleAndSoundImage(songs[0])
                            }
                        }
                    }
                    else -> Unit
                }
            }
        }
        mainViewModel.curPlayingSong.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            currPlayingSong = it.toSong()
            updateTitleAndSoundImage(currPlayingSong!!)
        }
        mainViewModel.playbackState.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isPlaying) {
                    binding.playPauseDetail.setImageResource(R.drawable.ic_baseline_pause_24)
                } else binding.playPauseDetail.setImageResource(R.drawable.ic_baseline_play_arrow_24)
            }
        }
    }

    private fun updateTitleAndSoundImage(song: Song) {
        val title = "${song.title} - ${song.artist}"
        with(binding) {
            songName.text = title
            glide.load(song.bitmapUri).into(songImage)
        }
    }
}