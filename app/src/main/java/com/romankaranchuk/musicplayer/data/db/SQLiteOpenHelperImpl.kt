package com.romankaranchuk.musicplayer.data.db

import android.content.Context
import android.database.sqlite.SQLiteOpenHelper
import com.romankaranchuk.musicplayer.data.db.SQLiteOpenHelperImpl
import android.database.sqlite.SQLiteDatabase
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.romankaranchuk.musicplayer.data.db.TablesPersistenceContract.SongEntry
import com.romankaranchuk.musicplayer.data.db.TablesPersistenceContract.AlbumEntry


class SQLiteOpenHelperImpl internal constructor(
    context: Context?
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_SONGS)
        db.execSQL(SQL_CREATE_TABLE_ALBUMS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + SongEntry.TABLE_NAME)
        db.