package com.romankaranchuk.musicplayer.utils;

import android.media.MediaMetadataRetriever;

import com.romankaranchuk.musicplayer.data.Song;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;



public final class MusicUtils {

    private static final String mCovers[] = {
            "https://lastfm-img2.akamaized.net/i/u/ar0/00f1113ffd274a7d82acc626ae886b26",
            "https://upload.wikimedia.org/wikipedia/en/7/7d/Blurryface_by_Twenty_One_Pilots.png",
            "ht