package com.bondidos.task6.fragments

import android.os.Bundle
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bondidos.task6.R
import com.bondidos.task6.data.Song
import com.bondidos.task6.databinding.SongFragmentBinding
import com.bondidos.task6.service.isPlaying
import com.bondidos.task6.utils.Status.SUCCESS
import com.bondidos.task6.utils.playClickAnimation
import com.bondidos.task6.utils.toSong
import com.bondidos.task6.viewModel.MainViewModel
import com.bondidos.task6.viewModel.SongViewModel
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.absoluteValue

@AndroidEntryPoint
class SongFragment : Fragment() {

    @Inject
    lateinit var glide: RequestManager

    private var _binding: SongFragmentBinding? = null
    private val binding: SongFragmentBinding get() = requireNotNull(_binding)

    private lateinit var mainViewModel: MainViewModel
    private val songViewModel: SongViewModel by viewModels()

    private var currPlayingSong: Song? = null

    private var playbackState: PlaybackStateCompat? = null

    private var shouldUpdateSeekbar = true

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
        setOnClickListeners()
        subscribeToObservers()
    }

    private fun setOnClickListeners() {
        with(binding) {
            playPauseDetail.setOnClickListener { view ->
                view.playClickAnimation()
                currPlayingSong?.let {
                    mainViewModel.playOrToggleSong(
                        it,
                        true)
                }

            }
            skipPrevious.setOnClickListener {
                it.playClickAnimation()
                mainViewModel.skipToPreviousSong()
            }
            skipNext.setOnClickListener {
                it.playClickAnimation()
                mainViewModel.skipToNextSong()
            }
            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seeKBar_: SeekBar?, progress: Int, fromUser: Boolean) {
                    if(fromUser){
                        setCurrentPlayerTimeToTextView(progress.toLong())
                    }
                }

                override fun onStartTrackingTouch(seeKBar_: SeekBar?) {
                    shouldUpdateSeekbar = false
                }

                override fun onStopTrackingTouch(seeKBar_: SeekBar?) {
                    seeKBar_?.let {
                        mainViewModel.seekTo(it.progress.toLong())
                        shouldUpdateSeekbar = true
                    }
                }
            })
        }
    }

    private fun subscribeToObservers() {
 /*       mainViewModel.mediaItems.observe(viewLifecycleOwner) {
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
        }*/
        mainViewModel.curPlayingSong.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            currPlayingSong = it.toSong()
            if(currPlayingSong?.duration == -1L) return@observe
            currPlayingSong?.let {
                updateTitleAndSoundImage(it)
            }
        }

        mainViewModel.playbackState.observe(viewLifecycleOwner) {
            playbackState = it
            it?.let {
                if (playbackState?.isPlaying == true) {
                    binding.playPauseDetail.setImageResource(R.drawable.ic_baseline_pause_24)
                } else binding.playPauseDetail.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                binding.seekBar.progress = it.position.toInt()
            }
        }

        songViewModel.curPlayerPosition.observe(viewLifecycleOwner){

            if(shouldUpdateSeekbar){
                binding.seekBar.progress = it.toInt()
                setCurrentPlayerTimeToTextView(it)
            }
        }

        songViewModel.curSongDuration.observe(viewLifecycleOwner){
           // binding.seekBar.max = it.toInt()
           // val dataFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
            //binding.songDuration.text = dataFormat.format(it)
        }
    }

    private fun setCurrentPlayerTimeToTextView(ms: Long?) {
        val dataFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
        binding.curTime.text = dataFormat.format(ms)
    }

    private fun updateTitleAndSoundImage(song: Song) {
        val title = "${song.title} - ${song.artist}"
        with(binding) {
            songName.text = title
            glide.load(song.bitmapUri).into(songImage)
            Log.d("Long", song.duration.toString())
            seekBar.max = song.duration.toInt()
            val dataFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
            songDuration.text = dataFormat.format(song.duration)
        }
    }
}