
package com.romankaranchuk.musicplayer.data.db;

import androidx.annotation.NonNull;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.romankaranchuk.musicplayer.data.Song;

import java.util.List;

// TODO() clear comment out code
public class LocalSongDataSource implements ILocalSongDataSource {

    private static LocalSongDataSource INSTANCE;

    private final SupportSQLiteOpenHelper mDbHelper;

    private final ILocalSongDataSource songDao;

    private LocalSongDataSource(@NonNull AppDatabase appDatabase) {
        mDbHelper = appDatabase.getOpenHelper();
        songDao = appDatabase.songDao();
    }

    public static LocalSongDataSource getInstance(@NonNull AppDatabase appDatabase){
        if (INSTANCE == null){
            INSTANCE = new LocalSongDataSource(appDatabase);
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
    public void saveSongs(@NonNull List<Song> songs) {
        songDao.saveSongs(songs);

//        SupportSQLiteDatabase db = mDbHelper.getWritableDatabase();
//        for (Song song : songs) {
//            if (isEntryExist(db, SongEntry.TABLE_NAME, SongEntry.COLUMN_NAME_SONG_PATH, song.getPath())) {
//                return;
//            }
//
//
//            ContentValues values = new ContentValues();
//            values.put(SongEntry.COLUMN_NAME_ENTRY_ID, song.getId());
//            values.put(SongEntry.COLUMN_NAME_ALBUM_ID, song.getAlbumId());
//            values.put(SongEntry.COLUMN_NAME_SONG_NAME, song.getName());
//            values.put(SongEntry.COLUMN_NAME_SONG_PATH, song.getPath());
//            values.put(SongEntry.COLUMN_NAME_SONG_DURATION, song.getDuration());
//            values.put(SongEntry.COLUMN_NAME_SONG_IMAGE, song.getImagePath());
//            values.put(SongEntry.COLUMN_NAME_SONG_LYRICS, song.getLyricsSong());
//            values.put(SongEntry.COLUMN_NAME_SONG_YEAR, song.getYear());
//            values.put(SongEntry.COLUMN_NAME_SONG_DATE, song.getDate());
//            values.put(SongEntry.COLUMN_NAME_SONG_LANGUAGE, song.getLanguage());
//            db.insert(SongEntry.TABLE_NAME, SQLiteDatabase.CONFLICT_REPLACE, values);
//        }
//        try {
//            db.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void deleteSong(@NonNull Song song) {
        songDao.deleteSong(song);
