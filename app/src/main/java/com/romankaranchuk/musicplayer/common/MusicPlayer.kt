package com.romankaranchuk.musicplayer.common

import android.media.MediaPlayer
import java.io.IOException

interface MusicPlayer {
    fun resume()
    fun pause()
    fun prepare(filepath: String)
    fun start(filepath: String)
    fun stop()
    fun repeat()

    fun getCurrentPosition(): Int
    fun getDuration(): Int
    fun setOnCompletionListener(listener: MediaPlayer.OnCompletionListener?)
    fun isPlaying(): Boolean
    fun seekTo(positionInMs: Int)

    var isLooping: Boolean

    val mediaPlayer: MediaPlayer
}

class MusicPlayerImpl(
    override val mediaPlayer: MediaPlayer
) : MusicPlayer {

    override fun resume() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.p