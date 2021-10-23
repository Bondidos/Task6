package com.bondidos.task6.viewModel

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bondidos.task6.connector.MusicServiceConnection
import com.bondidos.task6.data.Song
import com.bondidos.task6.other.constants.MEDIA_ROOT_ID
import com.bondidos.task6.other.constants.SONG_DURATION
import com.bondidos.task6.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private  val musicServiceConnection: MusicServiceConnection
) {
    private val _mediaItems = MutableLiveData<Resource<List<Song>>>()
    val mediaItems: LiveData<Resource<List<Song>>> = _mediaItems

    val isConnected = musicServiceConnection.isConnected
    val networkError = musicServiceConnection.networkError
    val curPlayingSong = musicServiceConnection.curPlayingSong
    val playbackState = musicServiceConnection.playbackState

    init {
        _mediaItems.postValue(Resource.loading(null))
        musicServiceConnection.subscribe(MEDIA_ROOT_ID, object : MediaBrowserCompat.SubscriptionCallback() {
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
        })
    }
}