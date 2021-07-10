package com.romankaranchuk.musicplayer.presentation.navigation

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.romankaranchuk.musicplayer.R
import com.romankaranchuk.musicplayer.data.Song
import com.romankaranchuk.musicplayer.presentation.ui.main.MainFragment
import com.romankaranchuk.musicplayer.presentation.ui.player.actions.SongActionsBottomSheetDialog
import com.romankaranchuk.musicplayer.presentation.ui.player.PlayerFragment
import com.romankaranchuk.musicplayer.presentation.ui.player.sleeptimer.SleepTimerBottomSheetDialog
import com.romankaranchuk.musicplayer.presentation.ui.player.lyrics.SongLyricsBottomSheetDialog
import com.romankaranchuk.musicplayer.presentation.ui.tracklist.edit.EditAudioActionChooserFragment
import com.romankaranchuk.musicplayer.presentation.ui.tracklist.TrackListFragment
import javax.inject.Inject

interface Navigator {
    fun openPlayer(song: Song)
    fun openEditActions(song: Song)
    fun openSongActions(songId: String)
    fun openSongLyrics(songId: String)
    fun openSleepTimer(songId: String)
    fun openTrackList()

    var activity: FragmentActivity?
}

class NavigatorImpl @Inject constructor() : Navigator {

    override var activity: FragmentActivity? = null

    override fun openPlayer(song: Song) {
        val activity = activity ?: return
        activity.supportFragmentManager.beginTransaction()
            .replace(
                R.id.ma_container,
                PlayerFragment.newInstance(song),
                PlayerFragment.PLAYER_FRAGMENT_TAG
            )
            .addToBackStack(PlayerFragment.PLAYER_FRAGMENT_TAG)
            .commit()
    }

    override fun openEditActions(song: Song) {
        val audioSettingsFragment = EditAudioActionChooserFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EditAudioActionChooserFragment.SELECTED_SONG, song);
//                putParcelableArrayList(TrackListFragment.LIST_SONGS, ArrayList(TrackListF