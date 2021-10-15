package com.bondidos.task6.models

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.graphics.Bitmap
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bondidos.task6.BluetoothIntentListener
import com.bondidos.task6.MainActivity
import com.bondidos.task6.MediaService.MediaService
import com.bondidos.task6.R
import kotlinx.android.synthetic.main.main_fragment.*


private const val BUTTON_PLAY = 1
private const val BUTTON_STOP = 2
private const val BUTTON_PAUSE = 3
private const val BUTTON_NEXT = 4
private const val BUTTON_PREVIOUS = 5

class NowPlayingViewModel(val context: Context) : ViewModel() {
    private var mediaServiceBinder: MediaService.MediaServiceBinder? = null

    private var mediaController: MediaControllerCompat? = null
    private var callback: MediaControllerCompat.Callback? = null
    private var serviceConnection: ServiceConnection? = null
    private var bluetoothIntentListener: BluetoothIntentListener? = null
    private var callButtonEventListener: BluetoothIntentListener.CallButtonEventListener? = null

    private val _playingState: MutableLiveData<PlayingState> =  MutableLiveData<PlayingState>()
    val playingState: LiveData<PlayingState> get() = _playingState


    init {
        callback = object : MediaControllerCompat.Callback() {
            override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
                state?.let {
                    /*val playing = it.state == PlaybackStateCompat.STATE_PLAYING
                    playButton.isEnabled = !playing
                    pauseButton.isEnabled = playing
                    stopButton.isEnabled = playing*/

                    when (it.state) {
                        PlaybackStateCompat.STATE_PLAYING -> callbackPlay()
                        PlaybackStateCompat.STATE_PAUSED -> callbackPause()
                        PlaybackStateCompat.STATE_STOPPED -> callbackStop()
                        PlaybackStateCompat.STATE_SKIPPING_TO_NEXT -> callbackNext()
                        PlaybackStateCompat.STATE_SKIPPING_TO_PREVIOUS -> callbackPrev()
                        else -> callbackUnknown()
                    }
                }
            }
        }

        serviceConnection = object : ServiceConnection {

            override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
                mediaServiceBinder = service as MediaService.MediaServiceBinder
                try {
                    mediaController = MediaControllerCompat(
                        context as MainActivity,
                        mediaServiceBinder?.getMediaSessionToken()!!
                    )
                    mediaController?.registerCallback(callback as MediaControllerCompat.Callback)
                    callback?.onPlaybackStateChanged(mediaController?.playbackState)
                    mediaController?.transportControls?.play()
                } catch (e: RemoteException) {
                    mediaController = null
                }
            }

            override fun onServiceDisconnected(className: ComponentName?) {
                mediaServiceBinder = null
                if (mediaController != null) {
                    mediaController?.unregisterCallback(callback as MediaControllerCompat.Callback)
                    mediaController = null
                }
            }
        }

        context.bindService(
            Intent(context, MediaService::class.java),
            serviceConnection!!,
            Context.BIND_AUTO_CREATE
        )

        //prepare to catch bluetooth incoming call button press event
        bluetoothIntentListener = BluetoothIntentListener.getInstance(context)

        callButtonEventListener = object : BluetoothIntentListener.CallButtonEventListener {
            override fun answerOrHangoutButtonEvent() {
               // outputTextView.append(">>> Hangup of bluetooth headset pressed <<<\n")
            }
        }

        bluetoothIntentListener?.init(callButtonEventListener)
    }


    fun nextTrack() {
        mediaController?.transportControls?.skipToNext()
    }

    fun pausePlaying() {
        mediaController?.transportControls?.pause()
    }

    fun stopPlaying() {
        mediaController?.transportControls?.stop()
    }

    fun playTrack() {
        mediaController?.transportControls?.play()
    }

    fun previousTrack() {
        mediaController?.transportControls?.skipToPrevious()
    }
/**********CALLBACKS**********************************/

    private fun callbackNext() {
    val description = mediaController?.metadata?.description /*?: return
    outputTextView.append("next track ${description.title} was chosen...\n")
    cover.setImageBitmap(description.iconBitmap)
    title_track.text = description.title
    buttonChangeColor(BUTTON_NEXT)*/

        description?.let{
            _playingState.value = PlayingState(
                it.subtitle.toString(),
                it.iconBitmap,
                it.title.toString()
            )
        }

    }

    private fun callbackPause() {
        /*val description = mediaController?.metadata?.description ?: return
        outputTextView.append("track ${description.title} was paused...\n")
        buttonChangeColor(BUTTON_PAUSE)*/
    }

    private fun callbackStop() {
        /*val description = mediaController?.metadata?.description ?: return
        outputTextView.append("track ${description.title} was stopped...\n")
        buttonChangeColor(BUTTON_STOP)*/
    }

    private fun callbackPlay() {
        val description = mediaController?.metadata?.description /*?: return
        outputTextView.append("track ${description.title} is playing...\n")
        cover.setImageBitmap(description.iconBitmap)
        title_track.text = description.title
        buttonChangeColor(BUTTON_PLAY)*/

        description?.let{
            _playingState.value = PlayingState(
                it.subtitle.toString(),
                it.iconBitmap,
                it.title.toString()
            )
        }
    }

    private fun callbackPrev() {
        val description = mediaController?.metadata?.description /*?: return
        outputTextView.append("previous track ${description.title} was chosen...\n")
        cover.setImageBitmap(description.iconBitmap)
        title_track.text = description.title
        buttonChangeColor(BUTTON_PREVIOUS)*/

        description?.let{
            _playingState.value = PlayingState(
                it.subtitle.toString(),
                it.iconBitmap,
                it.title.toString()
            )
        }
    }

    private fun callbackUnknown() {
        //outputTextView.append("Unknown playback state change...\n")
    }

    /**********CALLBACKS**********************************/

   /* fun buttonChangeColor(typeButton: Int) {
        pauseButton.setBackgroundResource(android.R.drawable.btn_default)
        playButton.setBackgroundResource(android.R.drawable.btn_default)
        stopButton.setBackgroundResource(android.R.drawable.btn_default)
        nextButton.setBackgroundResource(android.R.drawable.btn_default)
        prevButton.setBackgroundResource(android.R.drawable.btn_default)
        when (typeButton) {
            BUTTON_PLAY -> playButton.setBackgroundResource(R.color.design_default_color_on_secondary)
            BUTTON_PAUSE -> pauseButton.setBackgroundResource(R.color.design_default_color_on_secondary)
            BUTTON_STOP -> stopButton.setBackgroundResource(R.color.design_default_color_on_secondary)
            BUTTON_NEXT -> nextButton.setBackgroundResource(R.color.design_default_color_on_secondary)
            BUTTON_PREVIOUS -> prevButton.setBackgroundResource(R.color.design_default_color_on_secondary)
        }
    }*/

    override fun onCleared() {
        super.onCleared()
        mediaServiceBinder = null
        if (mediaController != null) {
            mediaController?.unregisterCallback(callback as MediaControllerCompat.Callback)
            mediaController = null
        }
        context.unbindService(serviceConnection!!)
        bluetoothIntentListener?.destroy()
    }
}

data class PlayingState(
    /*val btnPlay: Boolean?,
    val btnPause: Boolean?,
    val btnStop: Boolean?,*/
    val artist: String?,
    val cover: Bitmap?,
    val title: String?
)
