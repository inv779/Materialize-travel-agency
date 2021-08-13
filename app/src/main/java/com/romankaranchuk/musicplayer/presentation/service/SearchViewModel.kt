
package com.romankaranchuk.musicplayer.presentation.service

import android.os.AsyncTask
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romankaranchuk.musicplayer.data.Album
import com.romankaranchuk.musicplayer.data.Song
import com.romankaranchuk.musicplayer.data.api.MusixmatchApi
import com.romankaranchuk.musicplayer.data.db.MusicRepository
import com.romankaranchuk.musicplayer.utils.MusicUtils
import com.romankaranchuk.musicplayer.utils.MusicUtils.SongInfo
import timber.log.Timber
import java.io.File
import java.util.*
import javax.inject.Inject

class SearchViewModel @Inject constructor(
    private val musicRepository: MusicRepository,
    private val musixMatchApi: MusixmatchApi
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    sealed class State {
        object OnSearchComplete: State()
    }

    private var isSearchActive = false

    fun onBind() {
        startMusicSearch()
    }

    private fun startMusicSearch() {
        if (!isSearchActive) {
            val searcher = MusicSearcher()
            Thread(searcher).start()
        }
    }

    private inner class MusicSearcher : Runnable {
        private fun iterateFiles(files: Array<File>, dirPathToFileName: HashMap<String, MutableList<String>>) {
            for (file in files) {
                if (file.isDirectory) {
                    iterateFiles(file.listFiles(), dirPathToFileName)
                } else {
                    val name = file.name
                    if (name.endsWith(".mp3")) {
                        val dirPath = file.parent
                        if (!dirPathToFileName.containsKey(dirPath)) {
                            dirPathToFileName[dirPath] = LinkedList()
                        }
                        val songNames = dirPathToFileName[dirPath]!!
                        songNames.add(name)
                    }
                }
            }
        }

        override fun run() {
//            String[] projection = new String[] {
//                    MediaStore.Audio.Media._ID,
//                    MediaStore.Audio.Media.ALBUM,
//                    MediaStore.Audio.Media.ARTIST,
//                    MediaStore.Audio.Media.TITLE,
//                    MediaStore.Audio.Media.RELATIVE_PATH
//            };
//            String selection = "";
//            String[] selectionArgs = null;
//            String sortOrder = "";
//            Cursor cursor = SearchService.this.getContentResolver().query(
//                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
//                    projection,
//                    selection,
//                    selectionArgs,
//                    sortOrder
//                    );
//            while (cursor.moveToNext()) {
//
//            }
//
            val musicFolder =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val musicFolderPath = musicFolder.absolutePath
            val searchStart = File(musicFolderPath)