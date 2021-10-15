package com.bondidos.task6

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.os.RemoteException
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import com.bondidos.task6.MediaService.MediaService
import com.bondidos.task6.models.MainActivityViewModel
import com.bondidos.task6.ui.fragments.MainFragment
import com.bondidos.task6.ui.fragments.NowPlayingFragment
import com.bondidos.task6.ui.fragments.TrackListFragment
import kotlinx.android.synthetic.main.main_activity.*
import kotlinx.android.synthetic.main.main_fragment.*

class MainActivity : AppCompatActivity() {


   // private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.container,NowPlayingFragment())
            .commit()
    }
}