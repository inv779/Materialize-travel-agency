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

    private var _binding: FragmentEditAudioBinding? = null
    private val binding get() = _binding!!

    companion object {
        private val SELECTED_SONG = "selectedSong"
        private var selectedSong: Song? = null

        fun newInstance(selectedSong: Song): EditAudioFragment {
            return EditAudioFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(SELECTED_SONG, selectedSong)
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.d("onCreateView")
        _bind