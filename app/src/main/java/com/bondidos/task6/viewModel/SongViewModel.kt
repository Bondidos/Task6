package com.bondidos.task6.viewModel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bondidos.task6.connector.MusicServiceConnection
import com.bondidos.task6.other.constants.UPDATE_PLAYER_POSITION_INTERVAL
import com.bondidos.task6.service.MusicService
import com.bondidos.task6.service.currentPlaybackPosition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SongViewModel @Inject constructor(musicServiceConnection: MusicServiceConnection) : ViewModel() {

    private val playbackState = musicServiceConnection.playbackState

    private val _curSongDuration = MutableLiveData<Long>()
    val curSongDuration: LiveData<Long> get() = _curSongDuration

    private val _curPlayerPosition = MutableLiveData<Long>()
    val curPlayerPosition: LiveData<Long> get() = _curPlayerPosition

    init{
        updateCurrentPlayerPosition()
    }

    private fun updateCurrentPlayerPosition(){
        viewModelScope.launch {
            while(true){
                val pos = playbackState.value?.currentPlaybackPosition
                if(curPlayerPosition.value != pos){
                    _curPlayerPosition.postValue(pos ?: 0L)
                    _curSongDuration.postValue(MusicService.curSongDuration)
                }
                delay(UPDATE_PLAYER_POSITION_INTERVAL)
            }
        }
    }
}