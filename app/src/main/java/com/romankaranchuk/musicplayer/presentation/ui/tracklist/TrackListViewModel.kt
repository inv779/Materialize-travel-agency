
package com.romankaranchuk.musicplayer.presentation.ui.tracklist

import androidx.lifecycle.*
import com.romankaranchuk.musicplayer.data.Song
import com.romankaranchuk.musicplayer.data.db.MusicRepositoryImpl
import com.romankaranchuk.musicplayer.domain.LoadTracksUseCase
import com.romankaranchuk.musicplayer.domain.LoadTracksUseCaseImpl
import com.romankaranchuk.musicplayer.presentation.navigation.Navigator
import java.util.*
import javax.inject.Inject

class TrackListViewModel @Inject constructor(
    private val loadTracksUseCase: LoadTracksUseCase,
    private val navigator: Navigator
) : ViewModel(), DefaultLifecycleObserver {

    companion object {
        const val BY_NAME = "0"
        const val BY_DURATION = "1"
        const val BY_YEAR = "2"
        const val BY_DATE_MODIFIED = "3"
        const val BY_FORMAT = "4"
        const val BY_LANGUAGE = "5"
    }