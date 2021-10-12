package com.bondidos.task6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bondidos.task6.ui.fragments.TrackListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, TrackListFragment.newInstance())
                .commitNow()
        }
    }
}