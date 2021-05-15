package com.romankaranchuk.musicplayer.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romankaranchuk.musicplayer.di.util.ViewModelFactory
import com.romankaranchuk.musicplayer.di.util.ViewModelKey
import com.romankaranchuk.musicplayer.presentation.ui.main.MainViewModel
import com.romankaranchuk.musicplayer.presentation.ui.player.PlayerViewModel
import com.romankaranchuk.musicplayer.presentation.ui.player.sleeptimer.SleepTimerViewModel
import com.romankaranchuk.musicplayer.presentation.ui.player.actions.SongActionsViewModel
import com.romankaranchuk.musicplayer.presentation.ui.player.lyrics.SongLyricsViewModel
import com.romankaranchuk.musicplayer.presentation.ui.player.page.PlayerPageViewModel
import com.romankaranchuk.musicplayer.presentation.ui.tracklist.TrackListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TrackListViewModel::class)
    abstract fun bindTrackListViewModel(viewModel: TrackListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel::class)
    abstract fun bindPlayerViewModel(viewModel: PlayerViewModel): ViewModel

    @Bind