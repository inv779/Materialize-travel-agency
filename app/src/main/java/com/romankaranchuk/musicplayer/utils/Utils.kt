package com.romankaranchuk.musicplayer.utils

import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import androidx.core.content.ContextCompat

fun hideSystemUI(window: Window) {
    // Enables regular immersive mode.
    