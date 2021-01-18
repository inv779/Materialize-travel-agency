package com.romankaranchuk.musicplayer.data.db

import androidx.room.*
import com.romankaranchuk.musicplayer.data.Album
import com.romankaranchuk.musicplayer.data.Song
import java.util.ArrayList

@Dao
interface ILocalAlbumDataSource {

    @Insert(onConflict = OnConflictStrategy.REPL