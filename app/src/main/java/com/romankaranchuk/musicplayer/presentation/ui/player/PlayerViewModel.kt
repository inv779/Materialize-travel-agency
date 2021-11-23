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
//                onForwardBtnClick()
////                fastForwardButton.callOnClick()
//            }
//        }
//    private val backwardButtonFromServiceToFragmentBR: BroadcastReceiver =
//        object : BroadcastReceiver() {
//            override fun onReceive(context: Context, intent: Intent) {
//                onBackwardBtnClick()
////                fastBackwardButton.callOnClick()
//            }
//        }
//    private val playButtonFromServiceToFragmentBR: BroadcastReceiver =
//        object : BroadcastReceiver() {
//            override fun onReceive(context: Context, intent: Intent) {
////                playImageButton.setSelected(!playImageButton.isSelected())
////                setStatePlayButton()
//            }
//        }
//
//    private fun registerBroadcastReceivers(context: Context) {
//        context.registerReceiver(
//            forwardButtonFromServiceToFragmentBR,
//            IntentFilter(PlayerFragment.TAG_FORWARD_BUT_PS_TO_F_BR)
//        )
//        context.registerReceiver(
//            backwardButtonFromServiceToFragmentBR,
//            IntentFilter(PlayerFragment.TAG_BACKWARD_BUT_PS_TO_F_BR)
//        )
//        context.registerReceiver(
//            playButtonFromServiceToFragmentBR,
//            IntentFilter(PlayerFragment.TAG_PLAY_BUT_PS_TO_F_BR)
//        )
//    }

    private val _state: MutableSharedFlow<ViewState> = MutableSharedFlow()
    val state: SharedFlow<ViewState> = _state

    private var isShuffleEnabled = false
    private var seekbarCurrentProgress = 0

    private val mainHandler = Handler(Looper.getMainLooper())
    private val mUpdateSongTimerRunnable: Runnable = object : Runnable {
        override fun run() {
            val curDurationFormatted = formatDurationToTime(musicPlayer.getCurrentPosition())

            CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                _state.emit(ViewState.UpdateSongTimer(curDurationFormatted))
            }
            mainHandler.postDelayed(this, 1000)
        }
    }
    private val mUpdateSeekBarRunnable: Runnable = object : Runnable {
        override fun run() {
            CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
                _state.emit(ViewState.UpdateSongSeekbar(progress = musicPlayer.getCurrentPosition()))
            }
            mainHandler.postDelayed(this, 2000)
        }
    }

    private val songs = mutableListOf<Song>()

    fun fileCurrentSong(): File {
        return File(currentSong!!.path)
    }
    var currentSong: Song? = null
    val listRecentlySongs = LinkedList<Song>()
    private val mediaPlayerCompletionListener = MediaPlayer.OnCompletionListener { onTrackPlayingEnd() }

    override fun onCreate(owner: LifecycleOwner) {
//        if (intentPlayerService == null) {
//            intentPlayerService = Intent(MusicPlayerApp.context, PlayerService::class.java)
//        }
//        MusicPlayerApp.context.startService(intentPlayerService)
//        MusicPlayerApp.context.bindService(intentPlayerService, serviceConnection, 0)
//
//        registerBroadcastReceivers(MusicPlayerApp.context)

        val song = (owner as Fragment).arguments?.getParcelable<Song>(PlayerFragment.ARG_CURRENT_SONG)
        currentSong = song
        Timber.d("onCreate:: currentSong=${song?.name}")

        loadSongsAndPlayCurrent()

        musicPlayer.mediaPlayer.setOnCompletionListener(mediaPlayerCompletionListener)
    }

    override fun onDestroy(owner: LifecycleOwner) {
//        if (isServiceBound) {
//            MusicPlayerApp.context.unbindService(serviceConnection)
//            isServiceBound = false
//        }
//        MusicPlayerApp.context.unregisterReceiver(playButtonFromServiceToFragmentBR)
//        MusicPlayerApp.context.unregisterReceiver(forwardButtonFromServiceToFragmentBR)
//        MusicPlayerApp.context.unregisterReceiver(backwardButtonFromServiceToFragmentBR)

        // TODO() stop player until PlayerService is not implemented
        // completion listener is called on stop() is called
        musicPlayer.mediaPlayer.setOnCompletionListener(null)
        musicPlayer.mediaPlayer.setOnErrorListener { mp, what, extra ->
            Timber.d("error is happened, what=$what, extra=$extra")
            return@setOnErrorListener true
        }
        musicPlayer.stop()
        resetSongProgressUI()
    }

    private fun setupSongProgressUI() {
        mainHandler.postDelayed(mUpdateSongTimerRunnable, 10)
        mainHandler.postDelayed(mUpdateSeekBarRunnable, 10)
    }

    private fun resetSongProgressUI() {
        mainHandler.removeCallbacks(mUpdateSongTimerRunnable)
        mainHandler.removeCallbacks(mUpdateSeekBarRunnable)
    }

    private fun formatDurationToTime(curDurationInMs: Int): String {
        val curDurationInSec = curDurationInMs / 1000.0
        val minutes = (curDurationInSec / 60).toInt()
        val seconds = (curDurationInSec / 60 - minutes) * 60
        val secondsString = if (seconds <= 9) {
            "0" + String.format(Locale.getDefault(), "%01.0f", seconds).substring(0, 1)
        } else {
            String.format(Locale.getDefault(), "%02.0f", seconds).substring(0, 2)
        }
        val minutesString = String.format(Locale.getDefault(), "%d", minutes)
        return "$minutesString:$secondsString"
    }

    private fun loadSongsAndPlayCurrent() {
        if (songs.isEmpty()) {
            musicPlayer.start(fileCurrentSong().toString())
            val songs = loadTracksUseCase.loadSongs(TrackListViewModel.BY_DURATION)

            this.songs.clear()
            this.songs.addAll(songs)
        }

        val durationInMs = musicPlayer.getDuration()

        setupSongProgressUI()

//        Timberog( "currentSong=${currentSong}, loadSongsAndPlayCurrent:: ${songs}")
        val index = songs.indexOf(currentSong)
        CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
            _state.emit(
                ViewState.TracksFetched(
                    curSongListPos = index,//if (index-1 > 0) index-1 else 0,
                    songs = songs,
                    durationInMs = durationInMs,
                    durationFormatted = formatDurationToTime(durationInMs),
                    curPosition = musicPlayer.getCurrentPosition(),
                    songName = currentSong?.name ?: "unknown",
                    artistName = currentSong?.nameArtist ?: "