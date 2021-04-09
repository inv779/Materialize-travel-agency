package com.romankaranchuk.musicplayer.data.db

import android.provider.BaseColumns

open class KBaseColumns {
    val _ID = "_id"
}

internal class TablesPersistenceContract private constructor() {
    internal class AlbumEntry private constructor(): BaseColumns {
        companion object : KBaseColumns() {
            const val TABLE_NAME = "albums"
            co