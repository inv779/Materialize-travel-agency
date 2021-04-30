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
 