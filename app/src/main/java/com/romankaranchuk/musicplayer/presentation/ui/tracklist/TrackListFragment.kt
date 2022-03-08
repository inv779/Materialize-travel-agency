
package com.romankaranchuk.musicplayer.presentation.ui.tracklist

import android.annotation.TargetApi
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romankaranchuk.musicplayer.R
import com.romankaranchuk.musicplayer.data.Song
import com.romankaranchuk.musicplayer.di.util.Injectable
import com.romankaranchuk.musicplayer.presentation.navigation.Navigator
import com.romankaranchuk.musicplayer.presentation.ui.main.MainActivity
import timber.log.Timber
import javax.inject.Inject

class TrackListFragment : Fragment(), Injectable {

    @Inject lateinit var navigator: Navigator
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: TrackListViewModel by viewModels { viewModelFactory }
    private lateinit var trackListAdapter: TrackListAdapter

    private var updateSongsBroadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            viewModel.loadSongs(TrackListViewModel.BY_DURATION)
        }
    }

    private val mSongItemClickListener: (Song) -> Unit = { song ->
        viewModel.songClicked(song)
    }

    private val mSongItemLongClickListener: (Song) -> Unit = { song ->
        viewModel.songLongClicked(song)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        context!!.registerReceiver(updateSongsBroadcastReceiver, IntentFilter(UPDATE_SONG_BROADCAST))

        //        ArrayList<Integer> durations = JniUtils.printAllSongs(TrackListFragment.getSongs());
        //        ArrayList<Integer> durations = mRepository.printAllSongs(); //NDK
        //        JniUtils.checkJNI(durations); //NDK
        Timber.d("onCreate")