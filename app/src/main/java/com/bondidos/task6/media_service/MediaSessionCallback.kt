
package com.bondidos.task6.media_service

/*
import android.content.Intent
import android.content.IntentFilter
import android.media.AudioManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource

class MediaSessionCallback(
    private val musicCatalog: TestMusicCatalog,

): MediaSessionCompat.Callback() {
    //private val mediaSessionCallback = object : MediaSessionCompat.Callback() {

        private var currentUri: Uri? = null
        private var currentState = PlaybackStateCompat.STATE_STOPPED

        override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
            playTrack(musicCatalog.getTrackByIndex(Integer.parseInt(mediaId!!)))
        }

        private fun playTrack(trackByIndex: TestMusicCatalog.JsonTrack) {
            if (!exoPlayer?.playWhenReady!!) {
                startService(Intent(applicationContext, MediaService::class.java))
                //val track = musicCatalog.currentTrack
                updateMetadataFromTrack(trackByIndex)
                prepareToPlay(Uri.parse(trackByIndex.trackUri))

                if (!audioFocusRequested) {
                    audioFocusRequested = true
                    val audioFocusResult = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        audioManager?.requestAudioFocus(audioFocusRequest!!)!!
                    } else {
                        audioManager?.requestAudioFocus(
                            this@MediaService.audioFocusChangeListener,
                            AudioManager.STREAM_MUSIC,
                            AudioManager.AUDIOFOCUS_GAIN
                        )!!
                    }
                    if (audioFocusResult != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        return
                    }
                }

                mediaSession?.isActive = true
                registerReceiver(
                    becomingNoiseReceiver,
                    IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY)
                )
                exoPlayer?.playWhenReady = true
            }

            updatePlaybackState(PlaybackStateCompat.STATE_PLAYING)

            refreshNotificationAndForegroundStatus(currentState)
        }

        override fun onPlay() {
            playTrack(musicCatalog.currentTrack)
        }

        override fun onPause() {
            if (exoPlayer?.playWhenReady!!) {
                exoPlayer?.playWhenReady = false
                unregisterReceiver(becomingNoiseReceiver)
            }

            updatePlaybackState(PlaybackStateCompat.STATE_PAUSED)

            refreshNotificationAndForegroundStatus(currentState)
        }

        override fun onStop() {
            if (exoPlayer?.playWhenReady!!) {
                exoPlayer?.playWhenReady = false
                unregisterReceiver(becomingNoiseReceiver)
            }

            if (audioFocusRequested) {
                audioFocusRequested = false

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    audioManager?.abandonAudioFocusRequest(audioFocusRequest!!)
                } else {
                    audioManager?.abandonAudioFocus(audioFocusChangeListener)
                }
            }

            mediaSession?.isActive = false

            updatePlaybackState(PlaybackStateCompat.STATE_STOPPED)

            refreshNotificationAndForegroundStatus(currentState)

            stopSelf()
        }

        override fun onSkipToNext() {
            val track = musicCatalog.next()
            updateMetadataFromTrack(track)

            mediaSession?.setPlaybackState(
                stateBuilder.setState(
                    PlaybackStateCompat.STATE_SKIPPING_TO_NEXT,
                    PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN,
                    1F
                ).build()
            )
            currentState = PlaybackStateCompat.STATE_SKIPPING_TO_NEXT

            prepareToPlay(Uri.parse(track.trackUri))

            if (exoPlayer?.playWhenReady!!) {
                updatePlaybackState(PlaybackStateCompat.STATE_PLAYING)
            } else {
                updatePlaybackState(PlaybackStateCompat.STATE_PAUSED)
            }

            refreshNotificationAndForegroundStatus(currentState)
        }

        override fun onSkipToPrevious() {
            val track = musicCatalog.previous()
            updateMetadataFromTrack(track)

            updatePlaybackState(PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS)

            prepareToPlay(Uri.parse(track.trackUri))

            if (exoPlayer?.playWhenReady!!) {
                updatePlaybackState(PlaybackStateCompat.STATE_PLAYING)
            } else {
                updatePlaybackState(PlaybackStateCompat.STATE_PAUSED)
            }

            refreshNotificationAndForegroundStatus(currentState)
        }

        private fun updatePlaybackState(playbackState: Int) {
            mediaSession?.setPlaybackState(
                stateBuilder.setState(
                    playbackState,
                    PlaybackStateCompat.PLAYBACK_POSITION_UNKNOWN,
                    1F
                ).build()
            )
            currentState = playbackState
        }

        private fun prepareToPlay(uri: Uri) {
            if (uri != currentUri) {
                currentUri = uri
                val mediaSource =
                    ProgressiveMediaSource.Factory(dataSourceFactory!!, extractorsFactory!!)
                        .createMediaSource(MediaItem.fromUri(uri))
                exoPlayer?.apply {
                    setMediaSource(mediaSource)
                    prepare()
                }
            }
        }

        private fun updateMetadataFromTrack(track: TestMusicCatalog.JsonTrack) {
            with(metadataBuilder) {
                putBitmap(
                    android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ART,
                    musicCatalog.bitmaps[track.bitmapUri]
                )
                putString(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_TITLE, track.title)
                putString(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ALBUM, track.artist)
                putString(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_ARTIST, track.artist)
                putLong(android.support.v4.media.MediaMetadataCompat.METADATA_KEY_DURATION, track.duration)
            }
            mediaSession?.setMetadata(metadataBuilder.build())
        }

        override fun onPlayFromSearch(query: String?, extras: Bundle?) {
            if (query == "play next") {
                Log.d("TAG", ">>>>>>>>>>>>> play next")
            }
            when (query) {
                "media next" -> onSkipToNext()
                "media previous" -> onSkipToPrevious()
                "media stop" -> onStop()
                "media pause" -> onPause()
                "media play" -> onPlay()
            }
            super.onPlayFromSearch(query, extras)
        }

        override fun onCustomAction(action: String?, extras: Bundle?) {
            Log.d("TAG", ">>>>>>>>>>>>> onCustomAction $action")
            super.onCustomAction(action, extras)
        }

        override fun onMediaButtonEvent(mediaButtonEvent: Intent?): Boolean {
            Log.d("TAG", ">>>>>>>>>>>>> onMediaButtonEvent ${mediaButtonEvent?.action}")
            return super.onMediaButtonEvent(mediaButtonEvent)
        }


    //}
}*/
