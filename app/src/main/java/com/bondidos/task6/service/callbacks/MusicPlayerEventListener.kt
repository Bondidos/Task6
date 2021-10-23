package com.bondidos.task6.service.callbacks

import android.widget.Toast
import com.bondidos.task6.service.MusicService
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.PlaybackException
import com.google.android.exoplayer2.Player

class MusicPlayerEventListener   (
    private val musicService: MusicService
) : Player.Listener {

    override fun onPlayerError(error: PlaybackException) {
        super.onPlayerError(error)
        Toast.makeText(musicService, "An unknown error occured", Toast.LENGTH_LONG).show()
    }
    //todo find solution
    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        super.onPlayerStateChanged(playWhenReady, playbackState)
        if(playbackState == Player.STATE_READY) {
            musicService.stopForeground(false)
        }
    }
//todo find solution
    /*override fun onPlaybackStateChanged(playbackState: Int) {
        super.onPlaybackStateChanged(playbackState)
        if(playbackState == Player.STATE_READY) {
            musicService.stopForeground(false)
        }
        onPlayWhenReadyChanged(true,playbackState)
    }*/
}