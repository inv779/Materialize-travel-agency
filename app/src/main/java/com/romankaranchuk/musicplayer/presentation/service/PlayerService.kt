package com.romankaranchuk.musicplayer.presentation.service

import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.LifecycleService
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class Player