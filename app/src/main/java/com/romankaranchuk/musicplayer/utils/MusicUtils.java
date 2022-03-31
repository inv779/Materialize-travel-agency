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
            "https://upload.wikimedia.org/wikipedia/ru/6/62/7nationarmy.jpg",
            "http://xn--80adh8aedqi8b8f.xn--p1ai/uploads/images/l/j/a/ljapis_trubetskoj_ti_kinula.jpg",
            "http://de.redmp3.su/cover/3743068-460x460/lyapis-crew-trub-yut-vol-1.jpg",
            "https://upload.wikimedia.org/wikipedia/en/3/39/The_Weeknd_-_Starboy.png",
            "https://upload.wikimedia.org/wikipedia/ru/b/bf/На_струнах_дождя....jpg",
            "https://upload.wikimedia.org/wikipedia/en/4/4f/Cleopatra_album_cover.jpg",
            "https://upload.wikimedia.org/wikipedia/en/a/aa/Muse_hysteria_cd.jpg",
            "https://upload.wikimedia.org/wikipedia/en/7/78/Muse_stockholm.jpg",
    };
    private static int mCurrentCover = 0;

    private static final int MAX_RECENT_SONGS