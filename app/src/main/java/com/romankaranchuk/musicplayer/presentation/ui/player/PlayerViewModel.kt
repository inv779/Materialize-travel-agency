package com.romankaranchuk.musicplayer.presentation.ui.player

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.romankaranchuk.musicplayer.data.Song
import com.romankaranchuk.musicplayer.domain.LoadTracksUseCase
import com.romankaranchuk.musicplayer.presentation.navigation.Navigator
import com.romankaranchuk.musicplayer.presentation.ui.tracklist.TrackListViewModel
import com.romankaranchuk.musicplayer.common.MusicPlayer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.io.File
import java.util.*
import javax.inject.Inject

class PlayerViewModel @Inject constructor(
    private val musicPlayer: MusicPlayer,
    private val loadTracksUseCase: LoadTracksUseCase,
    private val context: Context,
    private val navigator: Navigator
) : ViewModel(), DefaultLifecycleObserver {

//    private var intentPlayerService: Intent? = null
    var isServiceBound: Boolean = false

//    private val serviceConnection: ServiceConnection = object : ServiceConnection {
//        override fun onServiceConnected(name: ComponentName, binder: IBinder) {
//            _state.emit(ViewState.ServiceConnectedState(binder)
//        }
//
//        override fun onServiceDisconnected(name: ComponentName) {
//            _state.emit(ViewState.ServiceDisconnectedState
//        }
//    }

//    private val forwardButtonFromServiceToFragmentBR: BroadcastReceiver =
//        object : BroadcastReceiver() {
//            override fun onReceive(context: Context, intent: Intent) {
//