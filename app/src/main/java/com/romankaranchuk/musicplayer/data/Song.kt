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
    @ColumnInfo(name = "son