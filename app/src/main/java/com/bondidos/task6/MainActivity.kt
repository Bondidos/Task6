package com.bondidos.task6

import android.content.ComponentName
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.bondidos.task6.media_service.MediaService
import com.bondidos.task6.models.MainActivityViewModel
import com.bondidos.task6.music_server_connection.MusicServiceConnection
import com.bondidos.task6.ui.fragments.NowPlayingFragment

class MainActivity : AppCompatActivity() {


    private val viewModel by viewModels<MainActivityViewModel>{
        val musicServiceConnection = MusicServiceConnection(
            applicationContext,
            ComponentName(applicationContext, MediaService::class.java))
        MainActivityViewModel.Factory(musicServiceConnection)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,NowPlayingFragment())
            .commit()
        viewModel
    }

}