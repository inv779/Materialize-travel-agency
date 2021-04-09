package com.romankaranchuk.musicplayer.data.db

import android.provider.BaseColumns

open class KBaseColumns {
    val _ID = "_id"
}

internal class TablesPersistenceContract private constructor() {
    internal class AlbumEntry private constructor(): BaseColumns {
        companion object : KBaseColumns() {
            const val TABLE_NAME = "albums"
            const val COLUMN_NAME_ENTRY_ID = "album_id"
            const val COLUMN_NAME_ALBUM_NAME = "album_name"
            const val COLUMN_NAME_ALBUM_ARTIST = 