package com.romankaranchuk.musicplayer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.romankaranchuk.musicplayer.data.Album
import com.romankaranchuk.musicplayer.data.Song

@Database(entities = [
    Song::class,
    Album::class
], v