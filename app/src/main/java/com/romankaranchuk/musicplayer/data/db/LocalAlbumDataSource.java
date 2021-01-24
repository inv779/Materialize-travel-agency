package com.romankaranchuk.musicplayer.data.db;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.romankaranchuk.musicplayer.data.Album;
import com.romankaranchuk.musicplayer.data.Song;
import com.romankaranchuk.musicplayer.data.db.TablesPersistenceContract.SongEntry;

import java.io.IOException;
import java.util.List;

public class LocalAlbumDataSource implements ILocalAlbumDataSource {

    private static final String LOG_TAG = "DB log";
    private static LocalAlbumDataSource INSTANCE;

    private final SupportSQLiteOpenHelper mDbHelper;
    private final ILocalAlbumDataSource albumDao;

    private LocalAlbumDataSource(@NonNull AppDatabase appDatabase) {
        mDbHelper = appDatabase.getOpenHelper();
        albumDao = appDatabase.albumDao();
    }

    public static LocalAlbumDataSource getInstance(@No