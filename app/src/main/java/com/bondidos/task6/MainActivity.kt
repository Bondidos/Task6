package com.bondidos.task6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bondidos.task6.data.MusicCatalog
import com.bondidos.task6.databinding.MainActivityBinding
import com.bondidos.task6.fragments.ListFragment
import com.bondidos.task6.fragments.NowPlayingFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
    }

}