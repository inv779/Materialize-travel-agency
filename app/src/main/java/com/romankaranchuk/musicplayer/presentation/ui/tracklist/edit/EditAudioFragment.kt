package com.romankaranchuk.musicplayer.presentation.ui.tracklist.edit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment

import com.romankaranchuk.musicplayer.data.Song
import com.romankaranchuk.musicplayer.databinding.FragmentEditAudioBinding
import timber.log.Timber

class EditAudioFragment : DialogFragment() {

    private var _binding: FragmentEditAudioBinding? = n