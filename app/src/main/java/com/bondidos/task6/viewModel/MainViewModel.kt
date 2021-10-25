package com.bondidos.task6.viewModel

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem
import android.support.v4.media.MediaMetadataCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bondidos.task6.connector.MusicServiceConnection
import com.bondidos.task6.data.Song
import com.bondidos.task6.other.constants.MEDIA_ROOT_ID
import com.bondidos.task6.other.constants.SONG_DURATION
import com.bondidos.task6.service.isPlayEnabled
import com.bondidos.task6.service.isPlaying
import com.bondidos.task6.service.isPrepared
import com.bondidos.task6.utils.Event
import com.bondidos.task6.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val musicServiceConnection: MusicServiceConnection
) : ViewModel() {

    private val _mediaItems = MutableLiveData<Resource<List<Song>>>()
    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems

    val navigateToFragment: LiveData<Event<Boolean>> get() = _navigateToFragment
    private val _navigateToFragment = MutableLiveData<Event<Boolean>>()

    val isConnected = musicServiceConnection.isConnected
    val networkError = musicServiceConnection.networkError
    val curPlayingSong = musicServiceConnection.curPlayingSong
    val playbackState = musicServiceConnection.playbackState

    init {
        _mediaItems.postValue(Resource.loading(null))
        musicServiceConnection.subscribe(
            MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {
                override fun onChildrenLoaded(
                    parentId: String,
                    children: MutableList<MediaItem>
                ) {
                    super.onChildrenLoaded(parentId, children)
                    val items = children.map {
                        Song(
                            it.description.title.toString(),
                            it.description.subtitle.toString(),
                            it.description.iconUri.toString(),
                            it.description.mediaUri.toString(),
                            it.description.extras?.getLong(SONG_DURATION) ?: 0L
                        )
                    }
                    _mediaItems.postValue(Resource.success(items))
                }
            }
        )
    }

    fun navigateToSongFragment(){
        if(_navigateToFragment.value?.getContentIfNotHandled() == true)
            _navigateToFragment.postValue(Event(false))
        else _navigateToFragment.postValue(Event(true))
    }

    fun skipToNextSong() {
        musicServiceConnection.transportControls.skipToNext()
    }

    fun skipToPreviousSong() {
        musicServiceConnection.transportControls.skipToPrevious()
    }

    fun seekTo(pos: Long) {
        musicServiceConnection.transportControls.seekTo(pos)
    }

    fun playOrToggleSong(mediaItem: Song, toggle: Boolean = false) {
        val isPrepared = playbackState.value?.isPrepared ?: false
        if (isPrepared && mediaItem.title ==
            curPlayingSong.value?.getString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID)
        ) {
            playbackState.value?.let { playbackState ->
                when {
                    playbackState.isPlaying ->
                        if (toggle) musicServiceConnection.transportControls.pause()
                    playbackState.isPlayEnabled ->
                        musicServiceConnection.transportControls.play()
                    else -> Unit
                }
            }
        } else {
            musicServiceConnection.transportControls.playFromMediaId(mediaItem.title, null)
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicServiceConnection.unsubscribe(
            MEDIA_ROOT_ID,
            object : MediaBrowserCompat.SubscriptionCallback() {})
    }
}