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

    public static LocalAlbumDataSource getInstance(@NonNull AppDatabase appDatabase){
        if (INSTANCE == null){
            INSTANCE = new LocalAlbumDataSource(appDatabase);
        }
        return INSTANCE;
    }

//    private boolean isEntryExist(SupportSQLiteDatabase db, String tableName, String fieldName, String entryId){
//        Cursor c = null;
//        try{
//            String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + fieldName + " = ?";
//            c = db.query(query, new String[]{entryId});
//            return c.moveToFirst() && c.getInt(0) != 0;
//        }
//        finally {
//            if (c != null){
//                c.close();
//            }
//        }
//    }

    @Override
    public void saveAlbum(@NonNull Album album, @NonNull List<Song> songs) {
        albumDao.saveAlbum(album, songs);

//        SupportSQLiteDatabase db = mDbHelper.getWritableDatabase();
//        if (isEntryExist(db, AlbumEntry.TABLE_NAME, AlbumEntry.COLUMN_NAME_ALBUM_PATH, album.getPath())) {
//            return;
//        }
//
//        ContentValues values = new ContentValues();
//        values.put(AlbumEntry.COLUMN_NAME_ENTRY_ID, a