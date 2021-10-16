package com.bondidos.task6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bondidos.task6.ui.fragments.NowPlayingFragment

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