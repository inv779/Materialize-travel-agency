package com.romankaranchuk.musicplayer.common

import android.media.MediaPlayer
import java.io.IOException

interface MusicPlayer {
    fun resume()
    fun pause()
    fun prepare(filepath: String)
    fun start(filepath: String)
    fun stop()
    fun repeat