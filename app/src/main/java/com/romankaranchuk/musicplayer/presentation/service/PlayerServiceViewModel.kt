
package com.romankaranchuk.musicplayer.presentation.service

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Build
import android.os.Parcelable
import android.widget.RemoteViews
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.TaskStackBuilder
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romankaranchuk.musicplayer.R
import com.romankaranchuk.musicplayer.data.Song
import com.romankaranchuk.musicplayer.presentation.ui.main.MainActivity
import com.romankaranchuk.musicplayer.common.MusicPlayer
import com.romankaranchuk.musicplayer.presentation.ui.player.PlayerFragment
import com.romankaranchuk.musicplayer.utils.MathUtils
import com.squareup.picasso.Picasso
import com.squareup.picasso.Picasso.LoadedFrom
import com.squareup.picasso.Target
import java.io.File
import java.io.IOException
import javax.inject.Inject

class PlayerServiceViewModel @Inject constructor(
    private val nm: NotificationManager,
    private val wallpaperManager: WallpaperManager,
    private val picasso: Picasso,
    @Deprecated("make private after refactoring") val musicPlayer: MusicPlayer,
    @Deprecated("remove after refactoring") private val context: Context
) : ViewModel() {

    private lateinit var remoteViewsContent: RemoteViews
    private lateinit var remoteViewsBigContent: RemoteViews
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private val TAG_CANCEL_BIG = "cancelNotifPlayerReceiver"
    private val TAG_FORWARD = "forwardNotifPlayerReceiver"
    private val TAG_PLAY = "playNotifPlayerReceiver"
    private val TAG_BACKWARD = "backwardNotifPlayerReceiver"
    private val TAG_PLAY_BUT_PS_TO_F_BR = "playButtonFromPStoFragmentBR"
    private val TAG_FORWARD_BUT_PS_TO_F_BR = "forwardButtonFromPStoFragmentBR"
    private val TAG_BACKWARD_BUT_PS_TO_F_BR = "backwardButtonFromPStoFragmentBR"
    private val TAG_RESTORE_FPF_PS_TO_F_BR = "restoreFpfFromPStoF"

    private val oldWallpaper: Bitmap? = null
    private var cancelNotifPlayerReceiver: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context, "notification big was deleted", Toast.LENGTH_SHORT).show()
            if (isPlaying) playNotifPlayerReceiver?.onReceive(context, intent)
            nm.cancel(1)
            _state.value = State.OnCancel
        }
    }
    private var playNotifPlayerReceiver: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            Toast.makeText(context, "play notification big was clicked", Toast.LENGTH_SHORT)
                .show()
            if (intent.getParcelableExtra<Parcelable?>("currentSong") != null) {
                currentSong = intent.getParcelableExtra<Song>("currentSong")
                isPlaying = intent.getBooleanExtra("isPlaying", false)
            }
            changePlayButtonImage(remoteViewsContent, remoteViewsBigContent)
            if (intent.getStringExtra("fpfCall") == null) {
                handlePlayerLogic()
                context.sendBroadcast(Intent(TAG_PLAY_BUT_PS_TO_F_BR))
            }
        }
    }
    private var forwardNotifPlayerReceiver: BroadcastReceiver? = null
    private var backwardNotifPlayerReceiver: BroadcastReceiver? = null

    private var isPlaying = false
    private val path: File? = null
    private var smallIcon = 0
    private var currentSong: Song? = null
    private var oldAlbumCoverResource: String? = null

    private var mBuilder: NotificationCompat.Builder? = null

    fun UpdateLockscreen() {
        if (currentSong != null && currentSong!!.imagePath != oldAlbumCoverResource) {
            picasso.load(currentSong!!.imagePath).into(setLockscreenTarget)
            oldAlbumCoverResource = currentSong!!.imagePath
        }
    }

    private inner class SetLockscreen : AsyncTask<Bitmap?, Int?, Int?>() {
        @Deprecated("Deprecated in Java")
        override fun doInBackground(vararg bitmap: Bitmap?): Int? {
            if (bitmap.isEmpty()) {
                return null
            }
            val curBitmap = bitmap[0] ?: return null
            val widthBitmap = curBitmap.width
            val heightBitmap = curBitmap.height
            //            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
//            DisplayMetrics dm = new DisplayMetrics();
//            windowManager.getDefaultDisplay().getMetrics(dm);
            try {
                val visibleCropHint = Rect()
                visibleCropHint[(widthBitmap / 4.6).toInt(), 0, widthBitmap] = heightBitmap
                wallpaperManager.setBitmap(
                    curBitmap,
                    visibleCropHint,
                    false,
                    WallpaperManager.FLAG_LOCK
                )
            } catch (e: IOException) {
                e.printStackTrace()
            }
            //            Timber.d("bm="+String.valueOf(widthBitmap)+","+String.valueOf(heightBitmap)+
//                    ", dm="+String.valueOf(dm.widthPixels)+","+String.valueOf(dm.heightPixels));
            return null
        }
    }

    val setLockscreenTarget: Target = object : Target {
        override fun onBitmapFailed(e: java.lang.Exception, errorDrawable: Drawable) {}
        override fun onBitmapLoaded(bitmap: Bitmap, from: LoadedFrom) {
//            new SetLockscreen().execute(bitmap);
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable) {}
    }

    fun onCreate() {
        if (currentSong == null) {