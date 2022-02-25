package com.romankaranchuk.musicplayer.presentation.ui.player.lyrics

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModel
import com.romankaranchuk.musicplayer.domain.LoadTracksUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SongLyricsViewModel @Inject constructor(
    private val loadSongUseCase: LoadTracksUseCase,
    // TODO() remove context
    private val context: Context
): ViewModel(), DefaultLifecycleObserver {

    private val _state: MutableSharedFlow<State> = MutableSharedFlow()
    val state: SharedFlow<State> = _state

    override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)

        val args = (owner as? Fragment)?.arguments
        val songId = args?.getString("songId")

        if (songId == null) {
            Toast.makeText(context, "so