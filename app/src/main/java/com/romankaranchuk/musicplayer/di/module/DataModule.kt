package com.romankaranchuk.musicplayer.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.romankaranchuk.musicplayer.data.db.AppDatabase
import com.romankaranchuk.musicplayer.data.db.SQLiteOpenHelperImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule {

    @Singleton
    @Provides
    fun provideDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "music.db"
        ).addMigrations(object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(SQLiteOpenHelperImpl.getSqlCreateTableSongsNEW("songs_backup"))
                database.execSQL("INSERT INTO songs_backup SELECT song_id,album_id,song_name,song_image,song_duration,song_path,song_lyrics,song_year,song_date,song_language FROM songs")
                database.execSQL("DROP TABLE songs")
                database.execSQL("ALTER TABLE songs_backup RENAME TO songs")

                database.execSQL(SQLiteOpenHelperImpl.getSqlCreateTableAlbumsNEW("albums_backup"))
                database.execSQL("INSERT INTO albu