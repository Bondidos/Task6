package com.bondidos.task6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.bondidos.task6.models.MainActivityViewModel
import com.bondidos.task6.ui.fragments.TrackListFragment

class MainActivity : AppCompatActivity() {

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        /**
         * Observe [MainActivityViewModel.navigateToFragment] for [Event]s that request a
         * fragment swap.
         */
        viewModel.navigateToFragment.observe(this) {
            it?.getContentIfNotHandled()?.let { fragmentRequest ->
                supportFragmentManager
                    .beginTransaction()
                    .replace(
                        R.id.container, fragmentRequest.fragment, fragmentRequest.tag
                    )
                if (fragmentRequest.backStack) .addToBackStack(null)
                transaction.commit()
            }
        }

        private fun navigateToMediaItem(mediaId: String) {
            var fragment: MediaItemFragment? = getBrowseFragment(mediaId)
            if (fragment == null) {
                fragment = MediaItemFragment.newInstance(mediaId)
                // If this is not the top level media (root), we add it to the fragment
                // back stack, so that actionbar toggle and Back will work appropriately:
                viewModel.showFragment(fragment, !isRootId(mediaId), mediaId)
            }
        }

        private fun isRootId(mediaId: String) = mediaId == viewModel.rootMediaId.value

        private fun getBrowseFragment(mediaId: String): MediaItemFragment? {
            return supportFragmentManager.findFragmentByTag(mediaId) as MediaItemFragment?
        }
    }
}