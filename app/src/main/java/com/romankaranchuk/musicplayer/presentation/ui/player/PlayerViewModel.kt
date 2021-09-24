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
