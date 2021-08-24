package com.romankaranchuk.musicplayer.presentation.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.romankaranchuk.musicplayer.R
import com.romankaranchuk.musicplayer.databinding.FragmentMainBinding
import com.romankaranchuk.musicplayer.di.util.Injectable
import com.romankaranchuk.musicplayer.presentation.navigation.Navigator
import com.romankaranchuk.musicplayer.presentation.ui.player.PlayerFragment
import com.romankaranchuk.musicplayer.presentation.ui.tracklist.TrackListViewModel
import timber.log.Timber
import javax.inject.Inject

class MainFragment : Fragment(), Injectable {

    companion object {
        fun newInstance(): MainFragment {
            return MainFragment()
        }

        const val TAG = "MainFragment"
    }

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var lastProgress = 0f
    private var fragment : Fragment? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        lifecycle.