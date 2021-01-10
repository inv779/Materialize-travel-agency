package com.romankaranchuk.musicplayer.data

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.romankaranchuk.musicplayer.utils.MusicUtils
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "songs")
@Parcelize
data class Song (
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "song_id")
    var id: String,

    @ColumnInfo(name = "song_name")
    var name: String?,

    @ColumnInfo(name = "song_path")
    var path: String?,

    @ColumnInfo(name = "song_image")
    var imagePath: String?,

    @ColumnInfo(name = "song_duration")
    var duration: Int?,

    @ColumnInfo(name = "albu