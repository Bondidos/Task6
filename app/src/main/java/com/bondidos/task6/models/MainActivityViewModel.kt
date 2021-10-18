package com.bondidos.task6.models

import androidx.lifecycle.*
import com.bondidos.task6.music_server_connection.MusicServiceConnection

class MainActivityViewModel(val musicServiceConnection: MusicServiceConnection) : ViewModel() {

    //    val navigateToTrackItem: LiveData<Event<String>> get() = _navigateToTrackItem
//    private val _navigateToTrackItem = MutableLiveData<Event<String>>()
//
//    fun showFragment(fragment: Fragment, backStack: Boolean = true){
//        _navigateToFragment.value = Event(FragmentNavigationRequest(fragment, backStack, null))
//    }
    fun play() {
        if (musicServiceConnection.isConnected.value != null &&
            musicServiceConnection.isConnected.value == true)
            musicServiceConnection.transportControls.prepare()
            musicServiceConnection.transportControls.play()
    }


    class Factory(
        private val musicServiceConnection: MusicServiceConnection
    ) : ViewModelProvider.NewInstanceFactory() {

        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return MainActivityViewModel(musicServiceConnection) as T
        }
    }
}
