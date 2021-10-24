package com.bondidos.task6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.FragmentManager
import com.bondidos.task6.data.MusicCatalog
import com.bondidos.task6.databinding.MainActivityBinding
import com.bondidos.task6.fragments.ListFragment
import com.bondidos.task6.fragments.NowPlayingFragment
import com.bumptech.glide.RequestManager
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var glide: RequestManager

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    //binding = MainActivityBinding.inflate(layoutInflater)
    }

}