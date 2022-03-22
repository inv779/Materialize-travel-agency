package com.romankaranchuk.musicplayer.utils;

import static com.romankaranchuk.musicplayer.presentation.ui.main.MainActivity.path;

import com.romankaranchuk.musicplayer.data.Song;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class JniUtils {
    static {
        System.loadLibrary("native-lib");
    }
    public static native String stringFromJNI();
    public static native long sum(Array